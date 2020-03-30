package servers;

import com.fasterxml.jackson.databind.ObjectMapper;
import protocol.Request;
import protocol.RequestsHandler;
import protocol.Response;
import utills.JsonCreator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ClientHandler extends Thread {
    // связь с одним клиентом
    private Socket clientSocket;
    private BufferedReader in;
    private int id;
    private ObjectMapper mapper;
    private PrintWriter out;
    private List<ClientHandler> clients;
    private RequestsHandler requestsHandler;


    ClientHandler(Socket socket, List<ClientHandler> clients,RequestsHandler requestsHandler) {
        this.clientSocket = socket;
        // добавляем текущее подключение в список
        clients.add(this);
        System.out.println("New client");
        this.clients = clients;
        this.requestsHandler = requestsHandler;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void run() {
        mapper = new ObjectMapper();
        try {
            // получем входной поток для конкретного клиента
            in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                Request request = mapper.readValue(inputLine, Request.class);
                Response response = requestsHandler.handleRequest(request);
                JsonCreator<Response> jsonCreator = new JsonCreator<>();
                String json = jsonCreator.createJSON(response);
                out.println(json);
                System.out.println(json);
                System.out.println(inputLine);
            }
            in.close();
            clientSocket.close();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
