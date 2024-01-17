package id.maja.invoice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RabbitMQResponse<T> {
    @JsonProperty("response")
    private T response;
    @JsonProperty("isDisposed")
    private boolean isDisposed;
    @JsonProperty("error")
    private RabbitMQErrorResponse error;
}