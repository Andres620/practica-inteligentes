package com.backinteligente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class SocketClient {
    public static void main(String[] args) throws IOException {
        String hostName = "localhost"; // Cambia esto por la dirección IP del servidor Node.js
        int portNumber = 8080; // Cambia esto por el puerto del servidor Node.js

        try (Socket socket = new Socket(hostName, portNumber);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine); // Aquí puedes procesar los datos recibidos
            }
        } catch (IOException e) {
            System.err.println("No se pudo conectar al servidor Node.js en " + hostName + ":" + portNumber);
            System.exit(1);
        }
    }
}