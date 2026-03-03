# Guía de Hardware DIY para SmartWand

Este documento describe los componentes básicos y la configuración para construir el prototipo de hardware de SmartWand. La filosofía es usar componentes reciclados y de bajo coste siempre que sea posible.

## Componentes Esenciales

1.  **Fuente de Alimentación**:
    *   **Batería de Litio 18650**: Una sola celda, preferiblemente reciclada de un portátil viejo o un power bank.
    *   **Módulo de Carga TP4056**: Un módulo económico y seguro para cargar la batería 18650 vía micro-USB o USB-C. Proporciona protección contra sobrecarga y descarga.

2.  **Actuadores (Salidas)**:
    *   **Linterna/Baliza LED**: Un LED de alta luminosidad (blanco para la linterna, rojo para la baliza trasera). Se recomienda usar un pequeño driver de corriente constante o una resistencia adecuada para no quemar el LED.
    *   **Bocina (Buzzer)**: Un buzzer activo de 5V para generar sonidos de alerta.

3.  **Cerebro del Hardware (Microcontrolador)**:
    *   **ESP32 DevKitC**: Un microcontrolador potente con Wi-Fi y Bluetooth Low Energy (BLE) integrados. Será el encargado de recibir comandos desde la app Android y controlar los actuadores.

4.  **Componentes Adicionales**:
    *   **Interruptor**: Un interruptor deslizante para encender y apagar el circuito.
    *   **Cables, soldador y estaño**: Para realizar las conexiones.
    *   **Carcasa impresa en 3D (Opcional)**: Para proteger los componentes y montarlos de forma segura en la muleta.

## Conexión Básica (Sin ESP32)

En su forma más simple, los componentes se pueden conectar directamente para validarlos:

*   La salida de la batería (a través del TP4056) se conecta a un interruptor general.
*   El interruptor alimenta directamente los LEDs (con su resistencia) y la bocina a través de pulsadores manuales.

## Integración con ESP32 y la App Android

El objetivo final es que el ESP32 controle los actuadores. La aplicación Android se comunicará con el ESP32 a través de BLE usando una interfaz de servicios y características bien definida.

### Interfaz de Comunicación BLE

El firmware del ESP32 debe implementar el siguiente servicio y características para ser compatible con la aplicación Android:

*   **Servicio Principal de SmartWand**:
    *   **UUID**: `6a80b7d8-8255-42f2-9576-903b145b23e1`

*   **Características (dentro del servicio principal)**:
    1.  **Control del LED**:
        *   **UUID**: `6a80b7d8-8255-42f2-9576-903b145b23e2`
        *   **Permisos**: Escritura (Write).
        *   **Payload**: `0x01` para encender, `0x00` para apagar.
    2.  **Control de la Bocina**:
        *   **UUID**: `6a80b7d8-8255-42f2-9576-903b145b23e3`
        *   **Permisos**: Escritura (Write).
        *   **Payload**: Enviar `0x01` para activar la bocina de forma momentánea.

El código fuente de la aplicación Android ya utiliza estas constantes, que se pueden encontrar en `app/src/main/java/com/bdw/smartwand/data/ble/SmartWandUuids.kt`.
