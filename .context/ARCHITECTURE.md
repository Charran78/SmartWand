# Arquitectura del Sistema SmartWand

## 1. Modelo Arquitectónico Principal: Arquitectura en Capas
El sistema SmartWand se organiza en una arquitectura de capas bien definida para promover la modularidad, escalabilidad y mantenibilidad.

### 1.1. Capa Física (Muleta/Bastón)
*   **Descripción:** Componentes de hardware de la muleta. Encargada de la interacción directa con el entorno y las necesidades básicas del usuario.
*   **Componentes Clave:** Sensores (botones, acelerómetros - planificado), Actuadores (LEDs, Vibrador - planificado), Microcontrolador (ESP32 - planificado), Batería 18650.

### 1.2. Capa de Interfaz (Wear OS)
*   **Descripción:** Dispositivos Wear OS que proporcionan una interfaz de usuario minimalista y controles secundarios.
*   **Componentes Clave:** Smartwatch Wear OS, `smartwand-wear` (App Companion).

### 1.3. Capa de Inteligencia (Teléfono)
*   **Descripción:** El "cerebro" central del sistema. Contiene la lógica principal, la coordinación de componentes y la integración con servicios inteligentes.
*   **Componentes Clave:** `smartwand-app` (App Android Principal), Crutch Genie (Motor de IA/Asistente), Módulo de Integración con APIs.

### 1.4. Capa de Servicios
*   **Descripción:** Servicios externos que proporcionan capacidades de inteligencia, contextualización y utilidad.
*   **Componentes Clave:** Google Maps Platform, Gemini API / Nano, Google Assistant SDK.

## 2. Comunicación entre Capas
*   **Física <-> Inteligencia:** Comunicación inalámbrica, principalmente vía Bluetooth Low Energy (BLE) utilizando el ESP32 como puente (planificado).
*   **Interfaz (Wear OS) <-> Inteligencia:** Vía BLE o Internet (a través del smartphone).
*   **Inteligencia <-> Servicios:** A través de SDKs específicos de las APIs de Google y llamadas REST.

## 3. Stack Tecnológico Clave
*   **Lenguajes de Programación:** Kotlin / Java (para Android y Wear OS).
*   **Plataformas y Frameworks:** Android SDK (Android 8.0+), Wear OS SDK (Wear OS 3+).
*   **Herramientas de Desarrollo:** Android Studio (Flamingo+), Git.
*   **APIs y Servicios:** Google Maps Platform, Gemini API / Nano, Google Assistant SDK.
*   **Hardware / Embedded (Planificado):** ESP32 DevKit, Batería 18650 con protección TP4056.

## 4. Patrones de Diseño
*   **Arquitectura en Capas:** Estricta separación de responsabilidades para facilitar el mantenimiento y la evolución.
*   **MVVM (Model-View-ViewModel):** Se adoptará para el desarrollo de las aplicaciones Android y Wear OS, garantizando una clara separación de la lógica de negocio, la UI y el estado, mejorando la testabilidad y escalabilidad.
*   **Inyección de Dependencias (DI):** Se considerará el uso de frameworks de DI para gestionar dependencias y modularidad.
*   **Single Activity Architecture:** Recomendado para `smartwand-app` para simplificar la navegación y el manejo del estado general de la aplicación.

## 5. Gestión de Secretos y Configuración
*   **`secrets.properties`:** Las claves de API sensibles (ej. `GOOGLE_MAPS_API_KEY`) se almacenarán en este archivo, ubicado en la raíz del módulo `smartwand-app`.
*   **Control de Versiones:** El archivo `secrets.properties` debe estar explícitamente listado en `.gitignore` y **nunca** ser versionado.
*   **Carga de Claves:** Las claves serán accesibles en tiempo de compilación a través de Gradle, sin ser expuestas directamente en el código fuente.
*   **Variables de Entorno:** Para configuraciones sensibles en entornos de CI/CD, se explorará el uso de variables de entorno del sistema.