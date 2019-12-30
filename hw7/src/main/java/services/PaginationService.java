package services;

import com.fasterxml.jackson.core.type.TypeReference;
import context.Component;
import dto.DtoMessage;
import dto.DtoMessageList;
import dto.DtoPagination;
import models.Message;
import models.Pagination;
import protocol.Request;
import protocol.Response;
import repositories.MessageRepositoryImpl;
import utills.JsonCreator;

import java.util.ArrayList;
import java.util.List;

public class PaginationService implements Component {
    public PaginationService() {
    }

    @Override
    public String getName() {
        return "PaginationService";
    }
    public Response paginate(Request request) {
        MessageRepositoryImpl messageRepository = new MessageRepositoryImpl();
        Request<DtoPagination> messageRequest = request;
        Pagination pagination = new Pagination();
        DtoPagination dtoPagination = messageRequest.getPayload();
        pagination.setEnd(dtoPagination.getEnd());
        pagination.setStart(dtoPagination.getStart());
        List<Message> list = messageRepository.findByStartAndEnd(pagination.getStart(),pagination.getEnd());
        Response response = new Response();
        List<DtoMessage> dtoMessages = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Message message = new Message();
            message = list.get(i);
            DtoMessage dtoMessage = new DtoMessage();
            dtoMessage.setText(message.getText());
            dtoMessage.setFrom_id(message.getFrom_id());
            dtoMessage.setId(message.getId());
            dtoMessages.add(dtoMessage);
        }
        response.setResponse(new DtoMessageList(dtoMessages));
        return response;
    }
}
