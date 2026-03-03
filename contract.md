# contract.md

## 🤝 Filosofía y Roles

En SmartWand operamos bajo una filosofía de Senior Partnership y mejora continua Kaizen. Valoramos la reutilización y la adaptabilidad: “Si funciona, no es obsoleto. El código y las soluciones a cosas cotidianas están en todas partes, solo hay que saber mirar.”

- Visión de Partnership: Cada miembro del equipo, y cada contribuidor externo, es un socio clave. Fomentamos proactividad, autonomía y responsabilidad compartida.
- Filosofía Kaizen: Preferimos cambios pequeños, bien implementados y probados, frente a reescrituras disruptivas. El feedback continuo es esencial.
- Roles y responsabilidades:
  - Mantenedor Principal (Pedro - @Charran78): Define visión, arquitectura de alto nivel y hoja de ruta. Responsable de integración final y decisiones críticas.
  - Contribuidores: Proponer y desarrollar funcionalidades, corregir errores, mejorar documentación. Se espera seguir las directrices y convenciones.

## 🏗️ Arquitectura y Límites

### Stack Tecnológico Detectado

- Lenguajes:
  - Kotlin / Java (Android / Wear OS).
- Plataformas y frameworks:
  - Android SDK (smartwand-app, recomendado Android 10+; mínimo 8.0).
  - Wear OS SDK (smartwand-wear, Wear OS 3+).
- Herramientas:
  - Android Studio (Flamingo o superior).
  - Git.
- APIs y servicios externos:
  - Google Maps Platform (Maps SDK for Android).
  - Gemini API / Nano.
  - Google Assistant SDK.
- Hardware / Embedded (planificado):
  - ESP32 DevKit (BLE).
  - Batería 18650 (con protección TP4056).

### Estructura de Módulos / Directorios Clave

- smartwand-app/: Lógica central de la aplicación Android principal.
- smartwand-wear/: (Planificado) App companion para Wear OS.
- genie_banner.png: Recurso gráfico principal.
- LICENSE: Licencia (MIT).
- secrets.properties: No versionado; claves de API.

### Arquitectura de Sistema (Capas)

1. Capa Física (Muleta/Bastón): Sensores, actuadores (LEDs, vibrador), ESP32 (previsto).
2. Capa de Interfaz (Wear OS): Smartwatch Wear OS, app companion mínima.
3. Capa de Inteligencia (Teléfono): App Android (smartwand-app), Crutch Genie, integración con APIs.
4. Capa de Servicios: Google Maps Platform, Gemini API / Nano, Google Assistant.

### Patrones de Diseño

- Arquitectura en Capas: Separación estricta entre hardware, interfaz, negocio y servicios.
- MVVM en apps Android/Wear: Separación de UI, lógica y datos.
- Tamaño Máximo de Funciones: Preferentemente ≤ 30–40 líneas (excluye imports y comentarios). Funciones más largas requieren justificación en PR.

## 🛡️ Auditoría y Seguridad

### Reglas de Calidad de Código

- Linter/Formateador: Adoptar ktlint para Kotlin y estilos de Android Studio. Integrar en CI.
- Estilo y estructura: Seguir las guías definidas en .context y respetar MVVM.
- Revisiones: PRs pequeños, enfocados y revisados. Preferir cambios incrementales.

### Manejo de Secretos y Variables de Entorno

- secrets.properties: Claves sensibles (p. ej., GOOGLE_MAPS_API_KEY) en la raíz de smartwand-app.
- Control de Versiones: secrets.properties debe estar en .gitignore y nunca subirse.
- Acceso a Claves: Carga en tiempo de compilación vía Gradle; no exponer en código.
- Variables de Entorno: Para CI/CD o desarrollo local, usar variables del sistema o local.properties cuando aplique, siguiendo buenas prácticas.

## 🔄 Gestión de Contexto

- Cuándo compactar contexto:
  - Antes de PRs importantes: incluir resumen de cambios, decisiones y posibles impactos.
  - Revisiones de sprint/fase: cierre con lecciones aprendidas y actualizaciones de estado.
  - Cambios arquitectónicos: registrar decisiones (ADRs) y consecuencias.
- Qué compactar:
  - Resumen de cambios y motivación.
  - Decisiones clave y alternativas evaluadas.
  - Impactos, riesgos y mitigaciones.
- Dónde registrar:
  - .context/CHANGES.md: Registro cronológico de cambios.
  - .context/ARCHITECTURE.md: Decisiones y diagramas.
  - .context/STATUS.md: Progreso, bloqueadores y próximos pasos.

## 🧾 Convenciones de Commits

Se exige el uso de “Conventional Commits” (especificación 1.0.0) para todos los commits. Estructura:

<tipo>(opcional-escopo)?: breve descripción

[cuerpo opcional]

[pie(s) opcional(es)]

- Tipos principales:
  - feat: nueva funcionalidad.
  - fix: corrección de bug.
  - docs, style, refactor, perf, test, build, ci, chore, revert: tareas de mantenimiento y otros cambios.
- Alcance (scope) opcional entre paréntesis para contextualizar, p. ej., feat(api): …
- Breaking changes:
  - Indicar con ! tras el tipo/scope, p. ej., feat(api)!: …
  - O bien con el pie BREAKING CHANGE: descripción del cambio mayor.
- SemVer:
  - feat → MINOR, fix → PATCH, breaking → MAJOR.
- Reglas de redacción:
  - Descripción en imperativo, concisa.
  - Línea de asunto ≤ 72 caracteres recomendados.
  - Cuerpo y pies separados por una línea en blanco.
  - Pies en formato token: valor, p. ej., Reviewed-by:, Refs: #123.

Ejemplos:

- feat(app): inicializar módulo de navegación
- fix(api): evitar NPE en gestor de ubicaciones
- feat(api)!: migrar geocodificador a nueva API

BREAKING CHANGE: endpoints antiguos de geocodificación quedan obsoletos

- chore!: dejar de soportar Android 7

BREAKING CHANGE: uso de APIs no disponibles en Android 7

## ✔️ Definición de Hecho (DoD)

- Funcionalidad completa y probada manualmente.
- Tests automatizados actualizados/pasando (unitarios/integración/UI cuando aplique).
- Linting y build sin errores.
- Documentación actualizada (.context y README/guías afectadas).
- Seguridad verificada: sin secretos expuestos; manejo correcto de permisos.
- Revisión por pares cuando corresponda.

## 🚀 Flujo de Trabajo

- Commits pequeños y frecuentes; mensajes siguiendo Convenciones de Commits.
- PRs enfocados con descripción, impacto y checklist DoD.
- Integración continua: ejecutar build, lint y tests en cada PR.
