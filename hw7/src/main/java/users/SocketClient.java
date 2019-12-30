package users;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.*;
import models.Message;
import models.Pagination;
import models.Product;
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
            String token = null;
            while (true) {
                try {
                String messageToServer = sc.nextLine();
                if (messageToServer.startsWith("reg")) {
                    DtoUser dtoUser = new DtoUser();
                    dtoUser.setEmail(messageToServer.split(" ")[1]);
                    dtoUser.setPassword(messageToServer.split(" ")[2]);
                    Request<DtoUser> request = new Request<>();
                    request.setHeader("Login");
                    request.setPayload(dtoUser);
                    JsonCreator<Request<DtoUser>> jsonCreator = new JsonCreator<>();
                    String json = jsonCreator.createJSON(request);
                    sendMessage(json);
                    String line = in.readLine();
                    Response<DtoString> response = mapper.readValue(line, new TypeReference<Response<DtoString>>() {});
                    if (response!= null) {
                        token = response.getResponse().getString();
                    }
                }
                else if (messageToServer.startsWith("pag")) {
                    DtoPagination pagination = new DtoPagination();
                    int arg1 = Integer.parseInt(messageToServer.split(" ")[1]);
                    int arg2 = Integer.parseInt(messageToServer.split(" ")[2]);
                    pagination.setStart(arg1);
                    pagination.setEnd(arg2);
                    Request<DtoPagination> request = new Request<>();
                    request.setHeader("Pagination");
                    request.setPayload(pagination);
                    JsonCreator<Request<DtoPagination>> jsonCreator = new JsonCreator<>();
                    String json = jsonCreator.createJSON(request);
                    sendMessage(json);
                    String inputLine = in.readLine();
                    Response<DtoMessageList> messageRequest = mapper.readValue(inputLine, Response.class);
                    if (messageRequest != null) {
                        List<DtoMessage> messages = messageRequest.getResponse().getMessages();
                        for (int i = 0; i <messages.size(); i++) {
                            System.out.println(messages.get(i).getText());
                        }
                    }
                } else if (messageToServer.startsWith("products")) {
                    DtoPagination pagination = new DtoPagination();
                    int arg1 = Integer.parseInt(messageToServer.split(" ")[1]);
                    int arg2 = Integer.parseInt(messageToServer.split(" ")[2]);
                    pagination.setStart(arg1);
                    pagination.setEnd(arg2);
                    Request<DtoPagination> request = new Request<>();
                    request.setHeader("GetProducts");
                    request.setPayload(pagination);
                    JsonCreator<Request<DtoPagination>> jsonCreator = new JsonCreator<>();
                    String json = jsonCreator.createJSON(request);
                    sendMessage(json);
                    String inputLine = in.readLine();
                    Response<DtoProductList> messageRequest = mapper.readValue(inputLine, new TypeReference<Response<DtoProductList>>() {});
                    if (messageRequest != null) {
                        List<DtoProduct> products = messageRequest.getResponse().getList();
                        for (int i = 0; i <products.size(); i++) {
                            System.out.println(products.get(i).getId() + " " +products.get(i).getName() + " " +  products.get(i).getCoast());
                        }
                    }
                }
                else if (messageToServer.startsWith("delete")) {
                    DtoProduct product = new DtoProduct();
                    int arg1 = Integer.parseInt(messageToServer.split(" ")[1]);
                    product.setId(arg1);
                    Request<DtoProduct> request = new Request<>();
                    request.setHeader("DeleteProduct");
                    request.setPayload(product);
                    request.setToken(token);
                    JsonCreator<Request<DtoProduct>> jsonCreator = new JsonCreator<>();
                    String json = jsonCreator.createJSON(request);
                    sendMessage(json);
                    String inputLine = in.readLine();
                    Response<DtoString> response = mapper.readValue(inputLine, Response.class);
                    if (response!= null) {
                        System.out.println(response.getResponse().getString());
                    }
                } else if (messageToServer.startsWith("add")) {
                    DtoProduct product = new DtoProduct();
                    String arg1 = messageToServer.split(" ")[1];
                    int arg2 = Integer.parseInt(messageToServer.split(" ")[2]);
                    product.setName(arg1);
                    product.setCoast(arg2);
                    Request<DtoProduct> request = new Request<>();
                    request.setHeader("AddProduct");
                    request.setPayload(product);
                    request.setToken(token);
                    JsonCreator<Request<DtoProduct>> jsonCreator = new JsonCreator<>();
                    String json = jsonCreator.createJSON(request);
                    sendMessage(json);
                    String inputLine = in.readLine();
                    Response<DtoString> response = mapper.readValue(inputLine, Response.class);
                    if (response!= null) {
                        System.out.println(response.getResponse().getString());
                    }
                }
                else {
                    DtoMessage message = new DtoMessage();
                    message.setText(messageToServer);
                    Request<DtoMessage> request = new Request<>();
                    request.setHeader("Message");
                    request.setPayload(message);
                    request.setToken(token);
                    JsonCreator<Request<DtoMessage>> jsonCreator = new JsonCreator<>();
                    String json = jsonCreator.createJSON(request);
                    sendMessage(json);
//                    System.out.println(json);
                    String inputLine = in.readLine();
                    Response<DtoMessage> response = mapper.readValue(inputLine, new TypeReference<Response<DtoMessage>>() {});
                    if (response != null) {
                            Message message2 = new Message();
                            message2.setText(response.getResponse().getText());
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
