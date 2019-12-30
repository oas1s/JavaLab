package protocol;

import dto.Dto;

public class Response<T extends Dto> {
    private T response;

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }
}
