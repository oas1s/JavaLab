package dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "@type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = DtoUser.class, name = "user"),
        @JsonSubTypes.Type(value = DtoProduct.class, name = "product"),
        @JsonSubTypes.Type(value = DtoMessage.class, name = "message"),
        @JsonSubTypes.Type(value = DtoPagination.class, name = "pagination"),
        @JsonSubTypes.Type(value = DtoProductList.class, name = "dtoProductList"),
        @JsonSubTypes.Type(value = DtoMessageList.class, name = "dtoMessageList"),
        @JsonSubTypes.Type(value = DtoString.class, name = "dtoString")
})
public interface Dto {
}
