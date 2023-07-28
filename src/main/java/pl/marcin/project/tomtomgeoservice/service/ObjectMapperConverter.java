package pl.marcin.project.tomtomgeoservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperConverter {
    private ObjectMapper objectMapper = getDefaultObjectMapper();

    private static ObjectMapper getDefaultObjectMapper() {
        ObjectMapper defaultObjectMapper = new ObjectMapper();
        defaultObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return defaultObjectMapper;

    }

    public <T> T convertFromJsonToObject(JsonNode node, Class<T> objectClass) throws JsonProcessingException {
        return objectMapper.treeToValue(node, objectClass);
    }

    public JsonNode parser(String jSon) throws Exception {
        return objectMapper.readTree(jSon);

    }
}
