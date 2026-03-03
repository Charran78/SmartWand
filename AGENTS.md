## Contexto del proyecto
Antes de realizar cualquier cambio, lee:
1. .context/STATUS.md — estado actual y bloqueadores
2. .context/SPECS.md — requisitos
3. .context/SOW.md — alcance
4. .context/ARCHITECTURE.md — decisiones clave
5. contract.md — constitución del proyecto (reglas de gobernanza)

## Roles y Responsabilidades
*   **Mantenedor Principal (Pedro - @Charran78)**: Define la visión global, la arquitectura de alto nivel y la hoja de ruta. Responsable de la integración final y la toma de decisiones críticas. Es el punto de contacto para cualquier duda fundamental sobre la dirección del proyecto.
*   **Contribuidores**: Cualquier miembro del equipo o externo que proponga y desarrolle funcionalidades, corrija errores o mejore la documentación. Se espera que los contribuidores sigan estrictamente las convenciones y directrices establecidas en `contract.md` y los estándares de conocimiento persistente definidos en `.context/`. Su autonomía viene acompañada de la responsabilidad de adherirse a los principios del proyecto.

## Filosofía de Trabajo
*   **Senior Partnership**: Reconocemos a cada miembro como un socio clave. Se fomenta la proactividad, la autonomía en la ejecución y la responsabilidad compartida sobre el éxito del proyecto.
*   **Kaizen**: Aplicamos la mejora incremental constante. Preferimos pequeños cambios bien implementados y probados sobre grandes reescrituras disruptivas. El feedback continuo es un pilar esencial de nuestra operativa.
*   **Reutilización y Adaptabilidad**: Nuestra máxima es: "Si funciona, no es obsoleto. El código y las soluciones a cosas cotidianas están en todas partes, solo hay que saber mirar." Valoramos la adaptabilidad de soluciones existentes y la optimización de recursos.

## Protocolo de Resumen
Para mantener la claridad y eficiencia, cualquier Pull Request importante, revisión de sprint/fase o decisión arquitectónica clave debe incluir un resumen conciso y detallado:
*   **PRs**: Deben seguir la convención de Conventional Commits en el título, con una descripción que detalle el "Qué" (cambios implementados) y el "Por Qué" (motivación, problema resuelto), enlaces a issues relacionados y un checklist de "Definición de Hecho".
*   **Documentos de Fase/ADRs (Architectural Decision Records)**: Para decisiones clave o cierres de fase, se requiere una estructura que cubra el Problema/Objetivo, Contexto, Decisión tomada, Alternativas consideradas (y por qué se descartaron), Impacto y Consecuencias, y Acciones Pendientes/Próximos Pasos. Esto asegura que el razonamiento detrás de las decisiones quede documentado.