package com.smartwand.ui.screens.genie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.generationConfig
import com.smartwand.data.local.dao.GenieMessageDao
import com.smartwand.data.model.GenieMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

/**
 * ViewModel for Genie (AI Assistant) Screen.
 * 
 * Manages chat with Gemini AI and conversation history.
 */
@HiltViewModel
class GenieViewModel @Inject constructor(
    private val genieMessageDao: GenieMessageDao
) : ViewModel() {
    
    /** Current session ID */
    private val sessionId = UUID.randomUUID().toString()
    
    /** Chat messages for current session */
    val messages: StateFlow<List<GenieMessage>> = genieMessageDao
        .getMessagesBySession(sessionId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    
    /** UI state */
    private val _uiState = MutableStateFlow(GenieUiState())
    val uiState: StateFlow<GenieUiState> = _uiState.asStateFlow()
    
    /** Gemini model instance - null until API key is configured */
    private var generativeModel: GenerativeModel? = null
    
    init {
        // Add welcome message
        viewModelScope.launch {
            val welcomeMessage = GenieMessage(
                content = """¡Hola! Soy tu Crutch Genie 🧞‍♂️

Puedo ayudarte con:
• Rutas accesibles
• Control de dispositivos
• Información sobre tu entorno

¿En qué te puedo ayudar?""",
                isUser = false,
                sessionId = sessionId
            )
            genieMessageDao.insertMessage(welcomeMessage)
        }
    }
    
    /**
     * Initialize Gemini model with API key.
     * 
     * Call this when API key is available (from user settings or BuildConfig).
     */
    fun initializeGemini(apiKey: String) {
        if (apiKey.isBlank()) return
        
        generativeModel = GenerativeModel(
            modelName = "gemini-1.5-flash",
            apiKey = apiKey,
            generationConfig = generationConfig {
                temperature = 0.7f
                topP = 0.9f
                maxOutputTokens = 1024
            },
            systemInstruction = com.google.ai.client.generativeai.type.content {
                text(GENIE_SYSTEM_PROMPT)
            }
        )
    }
    
    /**
     * Send a message to Genie.
     */
    fun sendMessage(userInput: String) {
        if (userInput.isBlank()) return
        
        viewModelScope.launch {
            // Save user message
            val userMessage = GenieMessage(
                content = userInput,
                isUser = true,
                sessionId = sessionId
            )
            genieMessageDao.insertMessage(userMessage)
            
            // Set loading state
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            try {
                val response = if (generativeModel != null) {
                    // Use Gemini API
                    val result = generativeModel!!.generateContent(userInput)
                    result.text ?: "No pude generar una respuesta."
                } else {
                    // Fallback response when API is not configured
                    generateFallbackResponse(userInput)
                }
                
                // Save Genie response
                val genieResponse = GenieMessage(
                    content = response,
                    isUser = false,
                    sessionId = sessionId
                )
                genieMessageDao.insertMessage(genieResponse)
                
            } catch (e: Exception) {
                val errorMessage = GenieMessage(
                    content = "Lo siento, hubo un error: ${e.message}",
                    isUser = false,
                    sessionId = sessionId
                )
                genieMessageDao.insertMessage(errorMessage)
            } finally {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }
    
    /**
     * Generate a fallback response when Gemini is not configured.
     */
    private fun generateFallbackResponse(input: String): String {
        val lowercaseInput = input.lowercase()
        return when {
            lowercaseInput.contains("hola") || lowercaseInput.contains("buenos") ->
                "¡Hola! Estoy aquí para ayudarte. ¿Qué necesitas?"
            
            lowercaseInput.contains("ruta") || lowercaseInput.contains("camino") ->
                "Para encontrar rutas accesibles, necesito configurar la API de Gemini. " +
                "Ve a Ajustes → API para configurarla."
            
            lowercaseInput.contains("luz") || lowercaseInput.contains("linterna") ->
                "Puedo encender la linterna de tu muleta. " +
                "Usa el botón de linterna en el Dashboard o di 'encender linterna'."
            
            lowercaseInput.contains("emergencia") || lowercaseInput.contains("ayuda") ->
                "Para emergencias, usa el botón rojo en la pantalla Emergency. " +
                "¿Estás bien? ¿Necesitas que active el protocolo de emergencia?"
            
            lowercaseInput.contains("gracias") ->
                "¡De nada! Estoy aquí para lo que necesites. 😊"
            
            else ->
                "Entiendo. Para respuestas más inteligentes, configura la API de Gemini " +
                "en Ajustes. Mientras tanto, puedo ayudarte con comandos básicos como " +
                "'encender linterna' o 'llamar emergencia'."
        }
    }
    
    /** Start new conversation */
    fun startNewSession() {
        viewModelScope.launch {
            // Session ID change will trigger new conversation
            // Old messages stay in database for history
        }
    }
    
    /** Clear error */
    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
    
    companion object {
        private const val GENIE_SYSTEM_PROMPT = """
Eres "Crutch Genie", un asistente de IA especializado integrado en SmartWand, 
una muleta inteligente para personas con movilidad reducida.

TU PERSONALIDAD:
- Eres amable, paciente y empático
- Usas un tono conversacional pero profesional
- Priorizas SIEMPRE la seguridad del usuario
- Eres conciso pero completo en tus respuestas

TUS CAPACIDADES:
1. RUTAS ACCESIBLES: Puedes sugerir rutas que eviten escaleras, tengan rampas, etc.
2. CONTROL DE DISPOSITIVOS: Puedes ayudar a controlar luces y otros dispositivos IoT
3. EMERGENCIAS: Puedes activar el protocolo de emergencia si el usuario lo necesita
4. INFORMACIÓN LOCAL: Puedes dar información sobre lugares accesibles cercanos

CONTEXTO IMPORTANTE:
- El usuario usa muletas permanentemente
- Prioriza siempre opciones accesibles
- Si detectas señales de emergencia (caída, dolor, angustia), pregunta si necesita ayuda
- Responde en el mismo idioma que te escriban (español/inglés)

FORMATO:
- Usa emojis con moderación para ser amigable
- Usa listas cuando des múltiples opciones
- Sé conciso en pantallas pequeñas
"""
    }
}

data class GenieUiState(
    val isLoading: Boolean = false,
    val isVoiceInputActive: Boolean = false,
    val error: String? = null
)
