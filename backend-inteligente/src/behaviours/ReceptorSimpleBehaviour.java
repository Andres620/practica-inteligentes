package behaviours;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import algorithm.Minimax;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;

public class ReceptorSimpleBehaviour extends SimpleBehaviour{
    
    Agent myAgent;
    private int[] bestMove;

    public ReceptorSimpleBehaviour(Agent myAgent) {
        this.myAgent = myAgent;
    }

    @Override
    public void action() {
        ACLMessage message=this.myAgent.blockingReceive();
        if (message!=null) {
            try {
                bestMove = (int[]) message.getContentObject();
                System.out.println("Coordenadas: " + bestMove[0] + ", " + bestMove[1]);

                // Realizar el POST al servidor de texto a voz
                postToTextToSpeechServer("Mi movimiento es en la columna" + bestMove[1]);
                
            } catch (Exception e) {
            }
        }else{
            System.out.println("Receptor no me ha llegado mensaje");
        }
    }


    @Override
    public boolean done() {
        return true;
    }

    private void postToTextToSpeechServer(String text) {
        try {
            // URL del servidor de texto a voz
            String url = "http://localhost:4000/generate-voice";

            // Configuración de la solicitud POST
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString("{\"message\": \"" + text + "\"}"))
                    .build();

            // Enviar la solicitud y obtener la respuesta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Imprimir la respuesta del servidor
            System.out.println("Respuesta del servidor: " + response.body());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
