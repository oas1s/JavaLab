package dto;

import java.util.List;

public class DtoProductList implements Dto {
  private   List<DtoProduct> list;

    public DtoProductList() {
    }

    public DtoProductList(List<DtoProduct> list) {
        this.list = list;
    }

    public List<DtoProduct> getList() {
        return list;
    }

    public void setList(List<DtoProduct> list) {
        this.list = list;
    }
}
