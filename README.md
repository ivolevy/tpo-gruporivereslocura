# Trabajo Práctico Obligatorio - Programación II

**Grupo:** River es locura

**Integrantes del grupo:** 
- Enzo Mussi
- Mateo Magallanes
- Ivan Levy

**Alternativa elegida:** 
Alternativa A: Ecosistema de Red Social Profesional

**Estructuras de datos utilizadas:**
- **Diccionario:** Implementado para registrar y recuperar perfiles de usuario de forma instantánea en O(1) mediante un ID único, evitando las búsquedas secuenciales.
- **Grafo:** Modelado para la red de contactos, utilizando el algoritmo de recorrido por amplitud (BFS) para explorar niveles de conexión y sugerir contactos recomendados (amigos de amigos).
- **Árbol Binario de Búsqueda:** Utilizado para organizar las competencias laborales de los usuarios de forma jerárquica (ej. Tecnología > Desarrollo > Java) y agilizar las búsquedas.
- **Cola (FIFO):** Implementada para procesar las postulaciones y solicitudes de empleo asegurando estrictamente el orden de llegada.
- **Pila (LIFO):** Utilizada para guardar el historial de modificaciones del perfil, permitiendo revertir y "deshacer" la última actualización realizada.
- **Conjunto:** Implementado con su operación de intersección para lograr búsquedas combinadas y encontrar a los candidatos ideales en base a múltiples habilidades requeridas.

**Funcionalidades implementadas en esta segunda etapa:**
1. Identificación inmediata y carga de perfiles mediante identificador único.
2. Motor de recomendación de red de contactos calculando el nivel 2 de separación (BFS).
3. Búsqueda por especialidad y filtro de usuarios a través de la jerarquía de habilidades.
4. Gestión de postulaciones a empleos procesadas correctamente bajo la regla del primero en entrar, primero en salir.
5. Historial de acciones funcional con capacidad de restaurar el estado previo del perfil del usuario.

**Link del repositorio:**
https://github.com/ivolevy/tpo-gruporivereslocura

**Actividades realizadas por cada integrante:**
- **Enzo Mussi:** Implementación del TDA Grafo con el algoritmo de recorrido por amplitud (BFS) para la sugerencia de contactos de Nivel 2, y desarrollo de la estructura Cola (FIFO) para el procesamiento ordenado de postulaciones de empleo [1, 2].
- **Mateo Magallanes:** Diseño e implementación de la Interfaz Gráfica principal de la aplicación, y desarrollo completo del TDA Pila (LIFO) utilizado para el sistema de historial de modificaciones y la función "Deshacer" del perfil [1, 2].
- **Ivan Levy:** Construcción del TDA Árbol Binario de Búsqueda para organizar la jerarquía de competencias, implementación del TDA Diccionario en arreglos para la recuperación inmediata de usuarios en tiempo O(1), y desarrollo del TDA Conjunto con su operación de intersección matemática para filtrar candidatos [1-4].
