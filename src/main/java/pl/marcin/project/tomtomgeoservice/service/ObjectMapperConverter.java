package pl.marcin.project.tomtomgeoservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ObjectMapperConverter {
    private final ObjectMapper objectMapper;

    @Autowired
    public ObjectMapperConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public <T> T convertFromJsonToObject(JsonNode node, Class<T> objectClass) throws JsonProcessingException {
        return objectMapper.treeToValue(node, objectClass);
    }

    public JsonNode parser(String jSon) throws Exception {
        return objectMapper.readTree(jSon);

    }
}
