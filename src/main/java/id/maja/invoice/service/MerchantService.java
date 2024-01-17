package id.maja.invoice.service;

import id.maja.invoice.model.Merchant;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@Service
public class MerchantService {
    private final Logger logger = LoggerFactory.getLogger(MerchantService.class);

    @Value("${maja.rabbitmq.queue.merchant}")
    private String RABBITMQ_QUEUE;

    private final RabbitMQService rabbitMQService;

    MerchantService(RabbitMQService rabbitMQService) {
        this.rabbitMQService = rabbitMQService;
    }

    public Merchant findByCode(String merchantCode) {
        try {
            return this.rabbitMQService.send(RABBITMQ_QUEUE, "merchant.findByCode", merchantCode, Merchant.class);
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            return null;
        }
    }
}
