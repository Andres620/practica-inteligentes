const express = require('express');
const bodyParser = require('body-parser');
const app = express();
const port = 3000;

// Middleware para analizar el cuerpo de las solicitudes POST
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

let datosRecibidos = null; // Variable de nivel superior para almacenar los datos

// Ruta POST para recibir datos
app.post('/', (req, res) => {
  // Almacena los datos recibidos en la variable de nivel superior
  datosRecibidos = req.body;

  // Imprime los datos en la consola (puedes realizar otras operaciones aquí)
  console.log('Datos recibidos:', datosRecibidos);

  // Envía una respuesta al cliente
  res.send('Datos recibidos con éxito');
});



app.listen(port, () => {
  console.log(`Servidor escuchando en http://localhost:${port}`);
});


/*// Ruta GET para mostrar los datos recibidos
app.get('/', (req, res) => {
  if (datosRecibidos) {
    // Si hay datos almacenados, envíalos como respuesta al cliente
    res.json(datosRecibidos);
  } else {
    // Si no hay datos, responde con un mensaje indicando que no hay datos
    res.send('No hay datos recibidos aún');
  }
}); */