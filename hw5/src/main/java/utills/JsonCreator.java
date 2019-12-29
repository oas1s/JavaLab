package utills;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonCreator<T> {
    private ObjectMapper mapper;

    public JsonCreator() {
        mapper = new ObjectMapper();
    }

    public String createJSON(T t) {

        ObjectMapper mapper = new ObjectMapper();

        String jsonValue = null;

        try {
            jsonValue = mapper.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            System.out.println("Error in creating JSON");
            e.printStackTrace();
        }

        return jsonValue;
    }
}
