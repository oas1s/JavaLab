package dto;

public class DtoProduct implements Dto {
    private int id;
    private String name;
    private int coast;

    public DtoProduct() {
    }

    public DtoProduct(int id, String name, int coast) {
        this.id = id;
        this.name = name;
        this.coast = coast;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCoast() {
        return coast;
    }

    public void setCoast(int coast) {
        this.coast = coast;
    }
}
