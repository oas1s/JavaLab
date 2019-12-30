package dto;

public class DtoString implements Dto{
    private String string;

    public DtoString() {
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public DtoString(String string) {
        this.string = string;
    }
}
