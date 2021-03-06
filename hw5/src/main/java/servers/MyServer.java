package servers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Message;
import models.Pagination;
import models.User;
import protocol.Login;
import protocol.Request;
import repositories.MessageRepositoryImpl;
import repositories.UserRepositoryImpl;
import utills.JsonCreator;
import utills.MD5Utill;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public class MyServer {
    // список клиентов
    private List<ClientHandler> clients;

    public MyServer() {
        // Список для работы с многопоточностью
        clients = new CopyOnWriteArrayList<>();
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
                new ClientHandler(serverSocket.accept()).start();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    private class ClientHandler extends Thread {
        // связь с одним клиентом
        private Socket clientSocket;
        private BufferedReader in;
        private int id;
        private ObjectMapper mapper;
        private PrintWriter out;


        ClientHandler(Socket socket) {
            this.clientSocket = socket;
            // добавляем текущее подключение в список
            clients.add(this);
            System.out.println("New client");
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
                    String header = request.getHeader();
                    if (header.equals("Login")){
                        UserRepositoryImpl userRepository = new UserRepositoryImpl();
                        Request<Login> loginRequest = mapper.readValue(inputLine, new TypeReference<Request<Login>>() {});
                        User user = new User();
                        user.setEmail(loginRequest.getPayload().getEmail());
                        user.setPassword(loginRequest.getPayload().getPassword());
                        System.out.println(user.getEmail());
                         if (userRepository.findByLogin(user.getEmail()).isPresent()) {
                             id = user.getId();
                         } else {
                             userRepository.save(user);
                         }
                        out.println("reg success");
                    } else if (header.equals("Message")){
                        MessageRepositoryImpl messageRepository = new MessageRepositoryImpl();
                        Request<Message> messageRequest = mapper.readValue(inputLine, new TypeReference<Request<Message>>() {});
                        Message message = new Message();
                        message.setText(messageRequest.getPayload().getText());
                        message.setFrom_id(id);
                        messageRepository.save(message);
                        Request request1 = new Request();
                        request1.setPayload(message);
                        request1.setHeader("Message");
                        JsonCreator<Request<Message>> jsonCreator = new JsonCreator<>();
                        String json = jsonCreator.createJSON(request1);
                        for (ClientHandler client : clients) {
                            PrintWriter out = new PrintWriter(client.clientSocket.getOutputStream(), true);
                            out.println(json);
                        }
                    }
                    else if (header.equals("Pagination")) {
                        MessageRepositoryImpl messageRepository = new MessageRepositoryImpl();
                        Request<Pagination> messageRequest = mapper.readValue(inputLine, new TypeReference<Request<Pagination>>() {});
                        Pagination pagination = messageRequest.getPayload();
                        List<Message> list = messageRepository.findByStartAndEnd(pagination.getStart(),pagination.getEnd());
                        Request request1 = new Request();
                        request1.setHeader("Pagination");
                        request1.setPayload(list);
                        JsonCreator<Request<List<Message>>> jsonCreator = new JsonCreator<>();
                        String json = jsonCreator.createJSON(request1);
                        out.println(json);
                    }
                    System.out.println(inputLine);
                }
                in.close();
                clientSocket.close();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
