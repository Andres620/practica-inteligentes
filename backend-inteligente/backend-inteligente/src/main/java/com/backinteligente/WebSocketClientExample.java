package com.backinteligente;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class WebSocketClientExample extends WebSocketClient {

    public WebSocketClientExample(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("Conexión abierta con el servidor");
    }

    @Override
    public void onMessage(String message) {
        System.out.println("Mensaje recibido: " + message);
        // Aquí puedes realizar la lógica para manejar los datos recibidos del servidor
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Conexión cerrada con el servidor");
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }

    public static void main(String[] args) throws URISyntaxException {
        String serverUri = "ws://localhost:3000";  // Reemplaza con la dirección de tu servidor WebSocket
        WebSocketClientExample client = new WebSocketClientExample(new URI(serverUri));
        
        // Establece la conexión con el servidor
        client.connect();
    }
}
