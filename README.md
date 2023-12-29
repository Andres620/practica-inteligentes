# práctica-inteligentes

El triki ortogonal gravitatorio consiste en ubicar el marcador "cuadrado" o "triangulo" de manera estratégica de tal modo que queden alineados 3 marcadores del mismo tipo de manera "ortogonal" esto quiere decir alineación horizontal o vertical (no se permiten en diagonal). Gravitatorio se refiere a que las fichas tienen una "gravedad" que las hace caer hacia abajo en el tablero. Es decir, si una casilla debajo de una ficha está vacía, entonces la ficha caerá automáticamente a esa casilla. Esto hace que el juego sea un poco más desafiante y estratégico.

En esta práctica usted deberá:

1. Implementar el algoritmo minimax con poda alfa beta hasta el segundo nivel de bifurcación. Para esto debe utilizar un agente con comportamiento simple el cual es notificado mediante http de la actualización del tablero. A este componente se le llamará backend “inteligente”. 

   Nota: Tenga como referencia el siguiente código <https://github.com/fbc-sistemas-inteligentes-1/recursos-jade-y-http> 

2. El estado del tablero está siendo monitoreado por una cámara la cual pasa la imagen por un algoritmo de visión artificial que obtiene cada una de las posiciones del tablero, usted debe implementar un algoritmo tal que analice cada una de las casillas y determine el estado del tablero.

   Este debe exponer su funcionalidad mediante un servidor web (se recomienda utilizar Flask) el cual tiene un endpoint denominado “/get-game-state”. A este se le llamará backend “visión artificial”

      Por ejemplo, si el estado del juego está así
    
      ||||
      | - | - | - |
      ||Cuadrado||
      |Triangulo|Cuadrado||
      
      La respuesta en JSON debe ser la siguiente:
      
      [
      
      `    `[" "," "," "],
      
      `    `[" ","C"," "],
      
      `    `["T","C"," "]
      
      ]
      
      **Nota:** Tenga como referencia los siguientes scripts
      
      - <https://github.com/fbc-sistemas-inteligentes-1/recursos-practica-vision-y-voz-artificial/blob/main/analisisTablero.py>
      - <https://github.com/fbc-sistemas-inteligentes-1/recursos-practica-vision-y-voz-artificial/blob/main/deteccionPoligono.py> 

3. Cada vez que se detecte un nuevo movimiento se debe enviar la notificación al backend “inteligente” para que lleve a cabo el cálculo del nuevo movimiento. Para detectar cambios puede utilizar como base el siguiente código: <https://github.com/fbc-sistemas-inteligentes-1/recursos-practica-vision-y-voz-artificial/blob/main/resta.py> 

4. Una vez el backend inteligente determine el movimiento de la ficha, debe llamar al backend “voz artificial” el cual debe decir textualmente. “Mi movimiento es en la columna X” donde X es un número entre 1 y 3.

    **Nota:** Tenga como referencia los siguientes scripts

    - <https://github.com/fbc-sistemas-inteligentes-1/recursos-practica-vision-y-voz-artificial/blob/main/audio.py> 

**Diagrama Arquitectónico**

![Arquitectura](/images/arquitectura.png)


