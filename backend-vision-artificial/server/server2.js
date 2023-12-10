const net = require('net');

const server = net.createServer((socket) => {
  console.log('Cliente conectado');

  // Envía datos al cliente cada 3 segundos
  setInterval(() => {
    const data = { foo: 'bar' };
    socket.write(JSON.stringify(data));
  }, 3000);

  // Maneja los datos recibidos del cliente
  socket.on('data', (data) => {
    console.log(`Datos recibidos: ${data}`);
  });

  // Maneja la desconexión del cliente
  socket.on('end', () => {
    console.log('Cliente desconectado');
  });
});

server.listen(8080, () => {
  console.log('Servidor escuchando en http://localhost:3000');
});