/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testjade;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import jade.core.Profile;
import jade.core.ProfileImpl;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import jade.core.Runtime;
import controllers.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

/**
 *
 * @author Asus
 */
public class TestJade {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        int port = 8081; // Puerto en el que se ejecutará el servidor
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", new AgentController());
        server.setExecutor(null); // Usar el valor predeterminado
        System.out.println("Iniciando servidor en el puerto " + port);
        server.start();
    }
}
