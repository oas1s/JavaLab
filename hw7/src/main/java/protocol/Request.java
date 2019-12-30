package protocol;

import dto.Dto;

public class Request<T extends Dto> {
    private String header;
    private T payload;
    private String token;

    public Request() {
    }

    public Request(String header, T payload, String token) {
        this.header = header;
        this.payload = payload;
        this.token = token;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
