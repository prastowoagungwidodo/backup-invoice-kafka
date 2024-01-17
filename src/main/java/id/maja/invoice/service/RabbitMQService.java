package id.maja.invoice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.maja.invoice.payload.RabbitMQMessagePayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;

@Service
public class RabbitMQService {
    private final Logger logger = LoggerFactory.getLogger(RabbitMQService.class);

    @Value("${spring.rabbitmq.template.exchange}")
    private String RABBITMQ_EXCHANGE;

    private RabbitTemplate rabbitTemplate;
    @Autowired
    public void setRabbitTemplateBean(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    private ObjectMapper objectMapper;
    @Autowired
    public void setObjectMapperBean(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public <S> S send(String queue, String pattern, Object msg, Class<S> clazz) throws Exception {
        try {
            logger.debug("Sending message to RMQ");
            final String correlationId = UUID.randomUUID().toString();
            RabbitMQMessagePayload<Object> message = new RabbitMQMessagePayload<>();
            message.setId(correlationId);
            message.setPattern(pattern);
            message.setData(msg);
            String messageString = objectMapper.writeValueAsString(message);
            logger.debug("Message to RMQ: " + messageString);

            Message rpcMessage = MessageBuilder.withBody(messageString.getBytes())
                    .setCorrelationId(correlationId)
                    .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                    .build();
            rabbitTemplate.setReplyTimeout(60000);
            Message rpcResponse = rabbitTemplate
                    .sendAndReceive(RABBITMQ_EXCHANGE, queue, rpcMessage);
            if (rpcResponse != null) {
                logger.debug("Got response from RMQ");
                //logger.debug("Checking correlation id: " + correlationId);
                logger.debug(rpcResponse.toString());
                String msgId = rpcResponse.getMessageProperties().getCorrelationId();
                //logger.debug("Got correlation id from RMQ: " + msgId);
                if (msgId.equals(correlationId)) {
                    //logger.debug("Correlation id match!");
                    String responseAsString = new String(rpcResponse.getBody(), StandardCharsets.UTF_8);
                    logger.debug("Response from RMQ: " + responseAsString);
                    Map<String, Object> res = objectMapper.readValue(rpcResponse.getBody(), Map.class);
                    if (res.get("err") != null) {
                        Map<String, String> error = (Map<String, String>) res.get("err");
                        throw new Exception(error.get("message"));
                    }
                    Object dataObj = res.get("response");
                    return objectMapper.convertValue(dataObj, clazz);
                }
                logger.error("Correlation ID: " + correlationId + " is not equal to " + msgId);
                throw new Exception("Invalid correlation id. please check RMQ Request!");
            }
            logger.error("Empty Response from RMQ");
            throw new Exception("Empty Response from RMQ");
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }
}
