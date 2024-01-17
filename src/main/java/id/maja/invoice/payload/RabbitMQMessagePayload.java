package id.maja.invoice.payload;

import lombok.Data;

@Data
public class RabbitMQMessagePayload <T> {
    private String id;
    private String pattern;
    private T data;
}
