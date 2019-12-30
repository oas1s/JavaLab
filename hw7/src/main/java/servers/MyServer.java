package servers;

import protocol.RequestsHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.List;

public class MyServer {
    // список клиентов
    private List<ClientHandler> clients;
    private RequestsHandler requestsHandler;

    public MyServer(RequestsHandler requestsHandler,List<ClientHandler> clients) {
        // Список для работы с многопоточностью
        this.clients = clients;
        this.requestsHandler = requestsHandler;
    }

    public void start(int port) {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        // запускаем бесконечный цикл
        while (true) {
            try {
                // запускаем обработчик сообщений для каждого подключаемого клиента
                new ClientHandler(serverSocket.accept(),clients,requestsHandler).start();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
    }

}
