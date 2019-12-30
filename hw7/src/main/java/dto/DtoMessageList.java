package dto;

import java.util.List;

public class DtoMessageList implements Dto {
    List<DtoMessage> messages;

    public DtoMessageList() {
    }

    public List<DtoMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<DtoMessage> messages) {
        this.messages = messages;
    }

    public DtoMessageList(List<DtoMessage> messages) {
        this.messages = messages;
    }
}
