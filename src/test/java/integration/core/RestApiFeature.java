package integration.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Component
public class RestApiFeature {

    private ThreadLocal<ResponseResults> lastResponse = new ThreadLocal<>();

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${application.integration.base-url}")
    private String baseUri;

    public void getStore() {

        String url = this.baseUri + "/store";

        ResponseResultErrorHandler errorHandler = new ResponseResultErrorHandler();
        RestTemplate restTemplate = restTemplateBuilder.errorHandler(errorHandler).build();
        ResponseResults results = restTemplate.execute(url, HttpMethod.GET, null, response -> {
            if (errorHandler.hadError) {
                return (errorHandler.getResults());
            } else {
                return (new ResponseResults(response, objectMapper));
            }
        });
        lastResponse.set(results);

    }

    public ResponseResults getLastResponse() {
        return lastResponse.get();
    }

    private class ResponseResultErrorHandler implements ResponseErrorHandler {

        private ResponseResults results = null;
        private Boolean hadError = false;

        private ResponseResults getResults() {
            return results;
        }

        @Override
        public boolean hasError(ClientHttpResponse response) throws IOException {
            hadError = response.getRawStatusCode() >= 400;
            return hadError;
        }

        @Override
        public void handleError(ClientHttpResponse response) throws IOException {
            results = new ResponseResults(response, objectMapper);
        }
    }

}
