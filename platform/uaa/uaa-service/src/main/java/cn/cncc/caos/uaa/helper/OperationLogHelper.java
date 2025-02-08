package cn.cncc.caos.uaa.helper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OperationLogHelper {

    public static String jsonFormatting(String json){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
           return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
        } catch (Exception e) {
           return json;
        }
    }

}
