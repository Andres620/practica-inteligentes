package com.backinteligente;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Arrays;

import com.sun.net.httpserver.HttpServer;
import com.backinteligente.controllers.AgentController;

/**
 * Hello world!
 *
 */
public class App {

    private static char[][] currentBoard;

    public static void main(String[] args) throws IOException {
        int port = 8081; // Puerto en el que se ejecutará el servidor
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", new AgentController());
        server.setExecutor(null); // Usar el valor predeterminado
        System.out.println("Iniciando servidor en el puerto " + port);
        server.start();
    }
}
