# Especificaciones del Proyecto SmartWand

## 1. Requisitos Funcionales

### 1.1. Módulo Hardware Básico (McGyver Crutches)
*   **Iluminación:** Integración de linterna LED frontal.
*   **Seguridad:** Baliza trasera para visibilidad.
*   **Utilidad:** Pinza porta-bolsas y llave magnética integrada.
*   **Alerta:** Bocina audible.
*   **Alimentación:** Sistema de energía autónomo con batería 18650 reciclada (con protección TP4056).

### 1.2. Módulo Interfaz Smartwatch Genérico
*   **Control de Música:** Funcionalidad para controlar la reproducción de música desde el smartwatch.
*   **Notificaciones:** Recepción y gestión de notificaciones del teléfono en el smartwatch.
*   **Llamadas:** Capacidad para gestionar llamadas (responder/rechazar) desde el smartwatch.

### 1.3. Módulo Núcleo de App Android (`smartwand-app`)
*   **Centralización Lógica:** Actuar como el "cerebro" central del sistema, integrando y coordinando todos los componentes.
*   **Conectividad:** Gestión de la conexión con el hardware de la muleta (vía BLE/ESP32 en fases futuras) y el smartwatch.
*   **Gestión de APIs:** Orquestación y consumo de APIs externas:
    *   **Google Maps Platform:** Para navegación contextual y servicios de localización.
    *   **Gemini API / Nano:** Motor de inteligencia artificial para el asistente "Crutch Genie".
    *   **Google Assistant SDK:** Para capacidades de asistente de voz.
*   **Interfaz de Usuario:** Proporcionar una interfaz intuitiva para la configuración, control y visualización de información relevante.

### 1.4. Módulo Asistente "Crutch Genie" (IA - Planificado)
*   **Navegación Contextual:** Ofrecer asistencia de navegación basada en la ubicación actual y destino del usuario.
*   **Respuestas Inteligentes:** Proporcionar respuestas contextuales a preguntas y comandos del usuario, utilizando la IA.

### 1.5. Módulo App Companion Wear OS (`smartwand-wear` - Planificado)
*   **Control por Gestos:** Habilitar funcionalidades de control mediante gestos específicos en el smartwatch.
*   **Interfaz Mínima:** Mostrar información clave y controles esenciales de forma concisa.

### 1.6. Módulo Comunicación BLE con ESP32 (Planificado)
*   **Conectividad Robusta:** Establecer un canal de comunicación inalámbrico (Bluetooth Low Energy) entre la muleta (ESP32) y la `smartwand-app`.
*   **Programabilidad:** Permitir la configuración y el control programático de los sensores y actuadores de la muleta.

## 2. Requisitos No Funcionales

### 2.1. Rendimiento
*   **Respuesta:** Tiempos de respuesta rápidos para las interacciones con el asistente y las funciones de control de la aplicación.
*   **Consumo de Batería:** Optimización del consumo de energía en la `smartwand-app` y `smartwand-wear` para prolongar la autonomía del smartphone y smartwatch.

### 2.2. Seguridad
*   **Gestión de Secretos:** Todas las claves de API y credenciales sensibles deben almacenarse en `secrets.properties` y excluirse del control de versiones (`.gitignore`).
*   **Protección de Datos:** Implementar prácticas seguras para el manejo de datos de usuario (si aplica) y la comunicación con APIs.

### 2.3. Escalabilidad y Mantenibilidad
*   **Arquitectura Modular:** Diseño en capas con módulos independientes para facilitar la adición de nuevas funcionalidades y el mantenimiento.
*   **Cohesión y Acoplamiento:** Alta cohesión dentro de los módulos y bajo acoplamiento entre ellos.
*   **Documentación:** Comentarios claros en el código y documentación actualizada del proyecto (README, guías).

### 2.4. Compatibilidad
*   **Android:** `smartwand-app` compatible con Android 8.0+ (recomendado Android 10+).
*   **Wear OS:** `smartwand-wear` compatible con Wear OS 3+.
*   **Hardware:** Compatibilidad con ESP32 DevKit y componentes estándar (batería 18650).

### 2.5. Calidad de Código
*   **Estilo de Código:** Adherencia a las guías de estilo de Kotlin/Java de Android.
*   **Linting y Formateo:** Uso de herramientas de linting (ej. ktlint para Kotlin) y formateadores automáticos.
*   **Tamaño de Funciones:** La mayoría de las funciones no deben exceder las 30-40 líneas de código (excluyendo comentarios e importaciones).
*   **Tests:**
    *   **Unitarios:** Cobertura mínima del 70% para la lógica de negocio crítica.
    *   **Integración:** Tests para componentes que interactúan con APIs o subsistemas clave.
    *   **UI/Instrumentación:** Tests para flujos de usuario importantes en `smartwand-app` y `smartwand-wear` (ej. Espresso).
*   **Code Review:** Todas las contribuciones deben pasar por una revisión de código por pares.
*   **Convención de Commits:** Uso de [Conventional Commits](https://www.conventionalcommits.org/).
*   **Integración Continua:** El código debe pasar todas las fases de CI (build, tests, lint) sin errores (planificado).