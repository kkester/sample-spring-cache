package integration.core;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class ResponseResults {

    private ClientHttpResponse response;

    private JsonNode body;

    public ResponseResults(ClientHttpResponse response, ObjectMapper objectMapper) throws IOException {
        this.response = response;
        body = objectMapper.readTree(response.getBody());
    }

    public ClientHttpResponse getResponse() {
        return response;
    }

    public ObjectNode getBodyJsonObject() {
        return (ObjectNode)body;
    }

}
