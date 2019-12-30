package services;

import com.fasterxml.jackson.core.type.TypeReference;
import context.Component;
import dto.Dto;
import dto.DtoMessage;
import dto.DtoString;
import models.Message;
import models.User;
import protocol.Request;
import protocol.Response;
import repositories.MessageRepositoryImpl;
import servers.ClientHandler;
import servers.MyServer;
import utills.JsonCreator;
import utills.TokenizeUser;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class MessageService implements Component {

    private List<ClientHandler> clients;

//    public MessageService(List<ClientHandler> clients) {
//        this.clients = clients;
//    }

    public MessageService() {
    }

    public void setClients(List<ClientHandler> clients) {
        this.clients = clients;
    }

    @Override
    public String getName() {
        return "MessageService";
    }
    public Response message(Request request) {
        MessageRepositoryImpl messageRepository = new MessageRepositoryImpl();
        Request<DtoMessage> messageRequest = request;
        Message message = new Message();
        message.setText(messageRequest.getPayload().getText());
        String token = messageRequest.getToken();
        TokenizeUser tokenizeUser = new TokenizeUser();
        User user = tokenizeUser.decodeJwt(token);
        message.setFrom_id(user.getId());
        messageRepository.save(message);
        Response response = new Response();
//                        request1.setPayload(message);
        response.setResponse(request.getPayload());
        JsonCreator<Response<DtoMessage>> jsonCreator = new JsonCreator<>();
        String json = jsonCreator.createJSON(response);
        for (ClientHandler client : clients) {
            PrintWriter out = null;
            try {
                out = new PrintWriter(client.getClientSocket().getOutputStream(), true);
                out.println(json);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return response;
    }
}
