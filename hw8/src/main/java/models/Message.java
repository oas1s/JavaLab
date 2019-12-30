package models;

import java.sql.Date;

public class Message {
    private int id;
    private int from_id;
    private String text;

    public Message(int id, int from_id, String text) {
        this.id = id;
        this.from_id = from_id;
        this.text = text;
    }

    public Message() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFrom_id() {
        return from_id;
    }

    public void setFrom_id(int from_id) {
        this.from_id = from_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
