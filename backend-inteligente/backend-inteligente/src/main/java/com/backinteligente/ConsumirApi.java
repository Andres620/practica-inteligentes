package com.backinteligente;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConsumirApi {

    public static void main(String[] args) {
        try {
            // URL de la API
            URL url = new URL("http://localhost:3000");

            // Abrir una conexión HTTP
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

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

            // Imprimir la respuesta
            System.out.println("Respuesta del servidor:");
            System.out.println(content.toString());

            // Cerrar la conexión
            con.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
