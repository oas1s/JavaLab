package users;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Message;
import models.Pagination;
import protocol.Login;
import protocol.Request;
import protocol.Response;
import utills.JsonCreator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;


public class SocketClient {
    // поле, содержащее сокет-клиента
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    // начало сессии - получаем ip-сервера и его порт
    public void startConnection(String ip, int port) {
        try {
            // создаем подключение
            clientSocket = new Socket(ip, port);
            // получили выходной поток
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            // входной поток
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            // запустили слушателя сообщений
            new Thread(receiverMessagesTask).start();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    private Runnable receiverMessagesTask = new Runnable() {
        @Override
        public void run() {
            Scanner sc = new Scanner(System.in);
            ObjectMapper mapper = new ObjectMapper();

            while (true) {
                try {
                String messageToServer = sc.nextLine();
                if (messageToServer.startsWith("reg")) {
                    Login login = new Login();
                    login.setEmail(messageToServer.split(" ")[1]);
                    login.setPassword(messageToServer.split(" ")[2]);
                    Request<Login> request = new Request<>();
                    request.setHeader("Login");
                    request.setPayload(login);
                    JsonCreator<Request<Login>> jsonCreator = new JsonCreator<>();
                    String json = jsonCreator.createJSON(request);
                    sendMessage(json);
                    String status = in.readLine();
                    System.out.println(status);
                }
                else if (messageToServer.startsWith("pag")) {
                    Pagination pagination = new Pagination();
                    int arg1 = Integer.parseInt(messageToServer.split(" ")[1]);
                    int arg2 = Integer.parseInt(messageToServer.split(" ")[2]);
                    pagination.setStart(arg1);
                    pagination.setEnd(arg2);
                    Request<Pagination> request = new Request<>();
                    request.setHeader("Pagination");
                    request.setPayload(pagination);
                    JsonCreator<Request<Pagination>> jsonCreator = new JsonCreator<>();
                    String json = jsonCreator.createJSON(request);
                    sendMessage(json);
                    String inputLine = in.readLine();
                    Request response = mapper.readValue(inputLine, Request.class);
                    String header = response.getHeader();
                    if (response != null) {
                        Request<List<Message>> messageRequest = mapper.readValue(inputLine, new TypeReference<Request<List<Message>>>() {});
                        List<Message> messages = messageRequest.getPayload();
                        for (int i = 0; i <messages.size(); i++) {
                            System.out.println(messages.get(i).getText());
                        }
                    }

                }
                else {
                    Message message = new Message();
                    message.setText(messageToServer);
                    Request<Message> request = new Request<>();
                    request.setHeader("Message");
                    request.setPayload(message);
                    JsonCreator<Request<Message>> jsonCreator = new JsonCreator<>();
                    String json = jsonCreator.createJSON(request);
                    sendMessage(json);
//                    System.out.println(json);
                    String inputLine = in.readLine();
                    Request response = mapper.readValue(inputLine, Request.class);
                    String header = response.getHeader();
                    if (response != null) {
                            Request<Message> messageRequest = mapper.readValue(inputLine, new TypeReference<Request<Message>>() {});
                            Message message2 = new Message();
                            message2.setText(messageRequest.getPayload().getText());
                            System.out.println(message2.getText());

                    }
                }
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }
            }
        }
    };

    public void stopConnection() {
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
