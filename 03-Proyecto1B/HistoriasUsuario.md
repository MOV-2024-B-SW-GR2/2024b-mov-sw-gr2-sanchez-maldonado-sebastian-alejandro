# Historias de Usuario - Aplicación Dotapicker Simplificada

## Historia de Usuario 1: Seleccionar Picks Oponentes
**Como** jugador de Dota 2,  
**quiero** seleccionar de 1 a 5 héroes oponentes,  
**para** que la aplicación analice las estadísticas y me recomiende los mejores picks.

### Criterios de Aceptación:
- El usuario puede elegir entre 1 y 5 héroes enemigos desde una lista desplegable.
- Los héroes seleccionados deben aparecer visualmente destacados en la interfaz.
- No se pueden repetir los héroes elegidos, al seleccionar uno se marcará de manera distintiva y bloqueará una nueva elección del mismo.

---

## Historia de Usuario 2: Elegir Rol para el Pick
**Como** jugador de Dota 2,  
**quiero** seleccionar el rol en el que planeo jugar,  
**para** recibir recomendaciones de héroes que se desempeñen bien en ese rol.

### Criterios de Aceptación:
- El usuario puede seleccionar un rol (e.g., Carry, Support, Offlane) desde una lista desplegable.
- El sistema debe filtrar las recomendaciones en función del rol seleccionado.
- Si el usuario no selecciona un rol, la aplicación mostrará un mensaje que indique que el rol es obligatorio.

---

## Historia de Usuario 3: Recibir Recomendaciones de Héroes
**Como** jugador de Dota 2,  
**quiero** recibir una lista de héroes recomendados,  
**para** tomar una decisión informada basada en estadísticas generales del juego.

### Criterios de Aceptación:
- El sistema debe mostrar una lista de héroes sugeridos en orden de mayor a menor tasa de victoria.
- Cada recomendación debe incluir:
  - Nombre del héroe.
  - Tasa de victoria general en el rol seleccionado.
- Si no hay héroes recomendados (por falta de datos o incompatibilidad), el sistema debe notificarlo al usuario.

---
