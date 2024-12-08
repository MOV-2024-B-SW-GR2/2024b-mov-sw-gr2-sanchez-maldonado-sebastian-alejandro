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

## Historia de Usuario 4: Ver Información Adicional sobre Héroes
**Como** jugador de Dota 2,  
**quiero** ver más información sobre los héroes recomendados,  
**para** entender por qué son opciones viables contra los picks enemigos.

### Criterios de Aceptación:
- El usuario puede hacer clic o tocar un héroe recomendado para expandir información adicional:
  - Roles del héroe.
  - Lista de habilidades.
  - Porcentaje de efectividad como counter de los héroes enemigos seleccionados.
- La interfaz debe permitir cerrar la información adicional para volver a la lista principal.

---

## Historia de Usuario 5: Filtrar Recomendaciones por Rol
**Como** jugador de Dota 2,  
**quiero** filtrar las recomendaciones según mi rol preferido,  
**para** que las sugerencias sean más relevantes a mi estilo de juego.

### Criterios de Aceptación:
- El sistema debe recalcular las recomendaciones si el usuario cambia de rol.
- Solo se mostrarán héroes con tasas de victoria significativas en el rol seleccionado.

---

## Historia de Usuario 6: Manejar Errores en la Selección
**Como** usuario de la aplicación,  
**quiero** recibir mensajes claros si hago una selección inválida,  
**para** saber cómo corregir el error.

### Criterios de Aceptación:
- Si el usuario intenta continuar sin seleccionar héroes o rol, se muestra un mensaje de error claro.
- El mensaje debe indicar específicamente qué acción debe tomar el usuario para corregir el problema.
- Ejemplo: "Por favor, selecciona al menos un héroe enemigo antes de continuar."

---

Estas historias de usuario son suficientes para construir el núcleo funcional de la aplicación. ¿Quieres agregar algo más, como el diseño de la interfaz o flujos específicos? 
