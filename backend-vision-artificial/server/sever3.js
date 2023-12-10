const express = require('express');
const bodyParser = require('body-parser');
const app = express();
const port = 3000;

app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

let datosRecibidos = null;

app.post('/', (req, res) => {
  datosRecibidos = req.body;
  console.log('Datos recibidos:', datosRecibidos);
  res.send('Datos recibidos con éxito');
});

// Ruta para obtener datos cada 3 segundos
app.get('/datos', (req, res) => {
  // Envia los datos almacenados al cliente
  res.json(datosRecibidos);
});

// Establece un intervalo para enviar datos cada 3 segundos
setInterval(() => {
  // Aquí podrías realizar alguna lógica para procesar o actualizar los datos antes de enviarlos
  console.log('Enviando datos al cliente:', datosRecibidos);
}, 1000);

app.listen(port, () => {
  console.log(`Servidor escuchando en http://localhost:${port}`);
});
