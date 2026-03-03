package com.bdw.smartwand.data.ia

import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GenieService(apiKey: String) {

    // Initialize the generative model with the provided API key
    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = apiKey
    )

    /**
     * Sends a prompt to the Gemini API and returns the response as a Flow.
     */
    fun getResponse(prompt: String): Flow<String> = flow {
        try {
            val response = generativeModel.generateContent(prompt)
            emit(response.text ?: "No response text available.")
        } catch (e: Exception) {
            emit("Error: ${e.message}")
        }
    }
}
