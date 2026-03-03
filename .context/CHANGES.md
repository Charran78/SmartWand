# Registro de Cambios

## 0.1.1 - 2024-02-27
### Corregido
- Error de compilación `';' expected` en `BuildConfig.java` causado por comillas duplicadas en las claves de API.
- Inestabilidad en el script `build.gradle.kts` al procesar archivos de propiedades.

### Añadido
- Sistema robusto de carga de secretos con limpieza automática de caracteres (`trim`) para `GEMINI_API_KEY` y `GOOGLE_MAPS_API_KEY`.
- Actualización exhaustiva del `README.md` con el nuevo Roadmap y estado de módulos.
- Sincronización de archivos de contexto (`STATUS.md`) para reflejar la estabilidad del núcleo.

## 0.1.0 - 2024-02-26
### Añadido
- Establecimiento inicial del repositorio del proyecto `Charran78/SmartWand`.
- Definición de la filosofía de "Senior Partnership" y "Kaizen".
- Arquitectura de sistema inicial en capas.
- Stack tecnológico: Kotlin, Compose, ESP32, Gemini API, Maps API.
- Configuración inicial de Git y licencias.
