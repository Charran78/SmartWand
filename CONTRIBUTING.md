# Guía de Contribución para SmartWand

¡Gracias por tu interés en contribuir a SmartWand! Toda ayuda es bienvenida, desde la corrección de errores y la mejora de la documentación hasta el desarrollo de nuevas funcionalidades y el diseño de hardware.

## Filosofía

Nos guiamos por los principios de **Senior Partnership**, **Kaizen (Mejora Continua)** y **Reutilización**. Valoramos la proactividad, la comunicación y las contribuciones incrementales y bien documentadas.

## Cómo Contribuir

### 1. Busca un Área para Ayudar

*   **Issues**: Revisa la [pestaña de Issues](https://github.com/Charran78/SmartWand/issues) en busca de tareas abiertas, bugs o `feature-requests`.
*   **Ideas**: ¿Tienes una idea para una nueva funcionalidad? ¡Abre un nuevo Issue para discutirla! Esto nos permite alinear la visión antes de que inviertas tiempo en el desarrollo.

### 2. Flujo de Trabajo de Git y Pull Requests (PRs)

Seguimos un flujo de trabajo estándar basado en Forks y Pull Requests.

1.  **Haz un Fork** del repositorio a tu propia cuenta de GitHub.
2.  **Clona tu Fork** en tu máquina local:
    ```bash
    git clone https://github.com/TU_USUARIO/SmartWand.git
    ```
3.  **Crea una nueva Rama** para tus cambios. Nómbrala de forma descriptiva (ej. `feature/control-de-luz-avanzado` o `fix/error-de-conexion-ble`).
    ```bash
    git checkout -b nombre-de-tu-rama
    ```
4.  **Realiza tus Cambios** en el código, la documentación o los diseños de hardware.
5.  **Realiza Commits Descriptivos**. Es obligatorio seguir la convención de [Conventional Commits](https://www.conventionalcommits.org/).

### 3. Convención de Commits

Cada commit debe tener un mensaje claro que siga este formato:

```
<tipo>[ámbito opcional]: <descripción>

[cuerpo opcional]

[pie opcional]
```

*   **Tipos principales**:
    *   `feat`: Una nueva funcionalidad para el usuario.
    *   `fix`: Una corrección de un error.
    *   `docs`: Cambios en la documentación.
    *   `style`: Cambios que no afectan al significado del código (espacios, formato, etc.).
    *   `refactor`: Un cambio en el código que no corrige un error ni añade una funcionalidad.
    *   `test`: Añadir o corregir tests.
    *   `chore`: Cambios en el proceso de build o herramientas auxiliares.

*   **Ejemplo de un buen commit**:
    ```
    feat(ble): Añadir soporte para leer el nivel de batería

    Se implementa la característica `BATTERY_LEVEL` en el `BleScanner` y se expone en el `MainViewModel`. La UI ahora muestra el nivel de batería cuando el servicio está disponible.

    Resuelve: #42
    ```

### 4. Envía tu Pull Request (PR)

*   Una vez que tus cambios estén listos, súbelos a tu fork:
    ```bash
    git push origin nombre-de-tu-rama
    ```
*   Ve a la página principal del repositorio de SmartWand y verás un botón para **crear un Pull Request** desde tu rama.
*   En la descripción del PR, detalla **qué problema resuelves** y **cómo lo has hecho**. Si tu PR está relacionado con un Issue, menciónalo (ej. `Resuelve #42`).

### 5. Revisión de Código

*   El mantenedor del proyecto revisará tu PR. Puede que se soliciten cambios.
*   Una vez aprobado, tu contribución se fusionará con la rama principal. ¡Felicidades y gracias!

## Contribuciones de Hardware

Si tienes ideas para mejorar el hardware, nuevos sensores o diseños de carcasas, ¡genial! Abre un Issue para discutirlo y comparte tus diseños, esquemas o fotos. La comunidad se beneficiará enormemente de tus creaciones.

## Guía de Estilo de Código

*   **Kotlin**: Seguimos la [guía de estilo oficial de Kotlin para Android](https://developer.android.com/kotlin/style-guide).
*   **Jetpack Compose**: Nos adherimos a las [mejores prácticas de Compose](https://developer.android.com/jetpack/compose/documentation#best-practices).
*   **Linting**: El proyecto utiliza `ktlint` y el linter de Android. Asegúrate de que tu código no introduce nuevas advertencias antes de enviar un PR.
