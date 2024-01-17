package id.maja.invoice.model;

import lombok.Data;

@Data
public class RabbitMQErrorResponse {
    private String error;
    private String message;
}
