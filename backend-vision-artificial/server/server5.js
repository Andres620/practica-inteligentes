const express = require('express');
const bodyParser = require('body-parser');
const http = require('http');
const WebSocket = require('ws');

const app = express();
const server = http.createServer(app);
const wss = new WebSocket.Server({ server });

app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

let datosRecibidos = null;
let datosPrevios = null;

app.post('/', (req, res) => {
  datosRecibidos = req.body;
  console.log('Datos recibidos:', datosRecibidos);
  res.send('Datos recibidos con éxito');
});

// Ruta para obtener datos
app.get('/datos', (req, res) => {
  // Retorna los datos almacenados
  res.json(datosRecibidos);
});

// WebSocket
wss.on('connection', (ws) => {
  // Enviar datos a clientes conectados solo si hay cambios
  setInterval(() => {
    if (datosRecibidos && JSON.stringify(datosRecibidos) !== JSON.stringify(datosPrevios)) {
      ws.send(JSON.stringify(datosRecibidos));
      datosPrevios = JSON.parse(JSON.stringify(datosRecibidos)); // Actualiza los datos previos
    }
  }, 1000); // Verifica los cambios cada segundo (ajusta según tus necesidades)
});

server.listen(3000, () => {
  console.log('Servidor escuchando en http://localhost:3000');
});
