# Alcance del Trabajo (Statement of Work) para SmartWand

## 1. Objetivo General
Transformar una muleta o bastón en un asistente inteligente y conectado, aprovechando hardware reciclado y servicios gratuitos de un teléfono Android, con el fin de aumentar la autonomía y seguridad del usuario.

## 2. Fases del Proyecto y Alcance Específico
El proyecto se estructura en fases incrementales, permitiendo la entrega de valor de forma continua y la adaptación a nuevos requisitos.

### 2.1. Fase 1: Núcleo de la App Android (Fase Actual: En Desarrollo)
*   **Objetivo:** Establecer el "cerebro" del sistema a través de la aplicación Android principal, integrando funcionalidades básicas y sentando las bases para futuras expansiones.
*   **Alcance:**
    *   Desarrollo de la aplicación Android principal (`smartwand-app`) con arquitectura MVVM.
    *   Implementación de la lógica central para la orquestación de componentes.
    *   Configuración y gestión de las integraciones iniciales con Google Maps Platform, Gemini API / Nano y Google Assistant SDK.
    *   Diseño y desarrollo de una interfaz de usuario inicial para la configuración y control básico de la muleta y el smartwatch.
    *   Integración y control de las funcionalidades del Hardware Básico (linterna, baliza, bocina, etc.) a través de la aplicación.
    *   Conectividad y control de las funciones básicas del Smartwatch Genérico (música, notificaciones, llamadas).
*   **Excluido de esta Fase:**
    *   Funcionalidad completa del asistente "Crutch Genie" (IA avanzada).
    *   Implementación de la App Companion para Wear OS (`smartwand-wear`).
    *   Comunicación BLE con ESP32 para el hardware de la muleta (se asume conexión cableada/manual para el prototipo inicial).
*   **Entregables:**
    *   Código fuente de `smartwand-app` (`main` branch).
    *   Build APK de la `smartwand-app` funcional para pruebas en dispositivos Android 8.0+.
    *   Documentación técnica de la integración de APIs y de la lógica central.

### 2.2. Fase 2: Asistente "Crutch Genie" y Wear OS Companion (Planificado)
*   **Objetivo:** Incorporar la inteligencia artificial avanzada y una interfaz dedicada para dispositivos Wear OS.
*   **Alcance:**
    *   Desarrollo completo del motor "Crutch Genie" aprovechando Gemini API para capacidades de navegación contextual y respuestas inteligentes.
    *   Diseño e implementación de la `smartwand-wear` (App Companion Wear OS) para control por gestos y una interfaz de usuario mínima y eficiente.
    *   Mejora de la interacción y sincronización de datos entre `smartwand-app` y `smartwand-wear`.
*   **Entregables:**
    *   Funcionalidad de asistente inteligente "Crutch Genie" integrada en la `smartwand-app`.
    *   Aplicación `smartwand-wear` funcional y desplegable en Wear OS 3+.

### 2.3. Fase 3: Comunicación BLE con ESP32 (Planificado)
*   **Objetivo:** Modernizar la conectividad del hardware de la muleta a través de Bluetooth Low Energy (BLE) para una comunicación robusta y programable.
*   **Alcance:**
    *   Desarrollo del firmware para el ESP32 DevKit para gestionar los sensores y actuadores de la muleta (LEDs, vibrador, botones).
    *   Implementación de la capa de comunicación BLE en la `smartwand-app` para interactuar con el ESP32.
    *   Integración completa del ESP32 con la lógica del "cerebro" de la aplicación Android.
*   **Entregables:**
    *   Firmware ESP32 funcional para la muleta.
    *   Funcionalidad BLE en la `smartwand-app` para control y lectura de la muleta.
    *   Prototipo de muleta con comunicación BLE operativa.

## 3. Criterios de "Hecho" (Definition of Done - DoD)
Una tarea, funcionalidad o corrección se considera "Hecha" cuando cumple con los siguientes criterios (referencia a `contract.md`):
1.  **Funcionalidad Completa:** La característica implementada cumple con los requisitos definidos.
2.  **Código Limpio y Consistente:** Sigue la guía de estilo, está formateado, sin warnings del linter y respeta el tamaño de funciones (max 30-40 líneas).
3.  **Testing Abarcativo:** Pasa todos los tests unitarios (cobertura mínima del 70% para lógica crítica), de integración y de UI/instrumentación.
4.  **Documentación Adecuada:** Comentarios claros en el código y documentación del proyecto actualizada.
5.  **Revisión por Pares (Code Review):** El código ha sido revisado y aprobado por al menos un compañero.
6.  **Convención de Commits:** Los commits siguen la convención de [Conventional Commits](https://www.conventionalcommits.org/).
7.  **Integración Continua (CI):** Pasa todas las fases de CI (build, tests, lint) sin errores (planificado).
8.  **Pruebas Manuales (si aplica):** La funcionalidad ha sido probada manualmente en un dispositivo real o emulador y funciona como se espera.