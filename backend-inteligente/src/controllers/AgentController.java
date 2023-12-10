/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import algorithm.Minimax;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.ContainerController;
import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;
import models.BoardResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

/**
 *
 * @author Asus
 */
public class AgentController implements HttpHandler {

    private ContainerController container;

    public AgentController() {
        // Inicializa la plataforma JADE
        jade.core.Runtime rt = jade.core.Runtime.instance();

        // Permite configurar los perfiles según tus necesidades
        Profile profile = new ProfileImpl();

        // Crea un contenedor
        container = rt.createMainContainer(profile);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        if ("GET".equals(exchange.getRequestMethod())) {
            // Manejar solicitudes GET
            String response = executeGETRequest("http://localhost:3000/datos");

            // Imprimir el resultado en la consola
            // Parsear la respuesta JSON
            System.out.println("Resultado obtenido con GET: " + response);

            // Parsear la respuesta JSON con Gson
            try {
                Gson gson = new Gson();
                BoardResponse boardResponse = gson.fromJson(response, BoardResponse.class);

                // Verificar si el objeto BoardResponse no es nulo y tiene datos
                if (boardResponse != null && boardResponse.getData() != null) {
                    // Ahora 'boardResponse.getData()' es la matriz que necesitas
                    char[][] matrix = boardResponse.getData();
                    Minimax.setBoard(matrix);
                    System.out.println("Resultado despues parsear: " + matrix);
                }

            } catch (Exception e) {
                e.printStackTrace();
                // Manejar el caso en el que la respuesta no sea un JSON válido
            }
            // char[][] updatedBoard = parseJSONResponse(response);

            // Minimax.setBoard(updatedBoard);

            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();

            // Iniciar el agente solo cuando se recibe la primera solicitud GET
            if (!agentExists("Robot1")) {
                initAgent();
            }
        } else if ("POST".equals(exchange.getRequestMethod())) {
            // Procesar la solicitud POST
            InputStreamReader isr = new InputStreamReader(exchange.getRequestBody());
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            String postData = sb.toString();
            // Aquí puedes procesar los datos POST, por ejemplo, almacenarlos en una base de
            // datos
            System.out.println("Datos POST recibidos: " + postData);
            String response = "{\"message\": \"Datos POST recibidos\"}";
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } else {
            // Manejar otros métodos (por ejemplo, PUT, DELETE, etc.) aquí
            String response = "{\"message\": \"Solicitud no admitida\"}";
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(405, response.length()); // 405 Method Not Allowed
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    private String executeGETRequest(String url) {
        try {
            // URL de la API
            URL apiUrl = new URL(url);

            // Abrir una conexión HTTP
            HttpURLConnection con = (HttpURLConnection) apiUrl.openConnection();

            // Configurar la solicitud GET
            con.setRequestMethod("GET");

            // Leer la respuesta
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            // Cerrar la conexión
            con.disconnect();

            // Retornar la respuesta del servidor
            return content.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "{\"error\": \"Error en la solicitud GET\"}";
        }
    }

    private void initAgent() {
        try {
            // Crea un agente
            jade.wrapper.AgentController agent = container.createNewAgent("Robot1", "agents.Robot", null);

            // Inicializa el agente
            System.out.println("Iniciando agente");
            agent.start();
        } catch (StaleProxyException e) {
            e.printStackTrace();
        }
    }

    private boolean agentExists(String agentName) {
        try {
            // Intentar obtener el agente con el nombre dado
            jade.wrapper.AgentController agent = container.getAgent(agentName);
            return agent != null;
        } catch (ControllerException e) {
            // Manejar la excepción si ocurre un problema con el controlador
            e.printStackTrace();
            return false;
        }
    }

}
