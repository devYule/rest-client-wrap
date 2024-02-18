package my.test.restclientwrapclass.api.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;


@Component
@RequiredArgsConstructor
public class ApiRequester {

    private final ObjectMapper om;

    // get
    public <T> T get(String baseUrl, String headerKey, String headerValue, String subUri, Class<T> responseEntityType) {
        RestClient restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .build();
        RestClient.RequestHeadersSpec<?> spec = restClient.get()
                .uri(subUri == null ? "" : subUri);
        if (headerKey != null && headerValue != null) {
            spec = spec.header(headerKey, headerValue);
        }
        String response = spec.retrieve().body(String.class);
        try {
            return om.readValue(response, responseEntityType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T get(String baseUrl, String headerKey, String headerValue, Class<T> responseEntityType) {
        return get(baseUrl, headerKey, headerValue, null, responseEntityType);
    }

    public <T> T get(String baseUrl, Class<T> responseEntityType) {
        return get(baseUrl, null, null, null, responseEntityType);
    }

    // post
    public <T, R> T post(String baseUrl, String headerKey, String headerValue, String subUri, R body,
                         Class<T> responseEntityType) {
        RestClient restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .build();
        RestClient.RequestHeadersSpec<?> spec = restClient.post()
                .uri(subUri == null ? "" : subUri)
                .body(body);
        if (headerKey != null && headerValue != null) {
            spec = spec.header(headerKey, headerValue);
        }
        String response = spec.retrieve().body(String.class);
        try {
            return om.readValue(response, responseEntityType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public <T, R> T post(String baseUrl, R body, Class<T> responseEntityType) {
        return post(baseUrl, null, null, null, body, responseEntityType);
    }

    public <T, R> T post(String baseUrl,String subUri, R body, Class<T> responseEntityType) {
        return post(baseUrl, null, null, subUri, body, responseEntityType);
    }
}
