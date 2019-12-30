package dto;

public class DtoPagination implements Dto {
    private int start;
    private int end;

    public DtoPagination(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public DtoPagination() {
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
