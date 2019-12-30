package models;

public class Pagination {
    private int start;
    private int end;

    public Pagination(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public Pagination() {
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }
}
