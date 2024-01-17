package id.maja.invoice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Value("${spring.rabbitmq.host}")
    private String RABBITMQ_HOST;
    @Value("${spring.rabbitmq.port}")
    private String RABBITMQ_PORT;
    @Value("${spring.rabbitmq.username}")
    private String RABBITMQ_USERNAME;
    @Value("${spring.rabbitmq.password}")
    private String RABBITMQ_PASSWORD;
    @Value("${maja.rabbitmq.queue.merchant}")
    private String QUEUE_MERCHANT;
    @Value("${maja.rabbitmq.queue.invoice}")
    private String QUEUE_INVOICE;
    @Value("${maja.rabbitmq.queue.reply-to}")
    private String QUEUE_REPLY_TO;
    @Value("${spring.rabbitmq.template.exchange}")
    private String EXCHANGE;

    @Bean("rabbitMQConnectionFactory")
    CachingConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(RABBITMQ_HOST);
        cachingConnectionFactory.setUsername(RABBITMQ_USERNAME);
        cachingConnectionFactory.setPassword(RABBITMQ_PASSWORD);
        cachingConnectionFactory.setConnectionTimeout(10000);
        return cachingConnectionFactory;
    }
    @Bean
    MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    Queue merchantQueue() {
        return new Queue(QUEUE_MERCHANT, true);
    }

    @Bean
    Queue invoiceQueue() {
        return new Queue(QUEUE_INVOICE, true);
    }

    @Bean
    Queue replyToQueue() {
        return new Queue(QUEUE_REPLY_TO, true);
    }

    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    Binding merchantBinding() {
        return BindingBuilder.bind(merchantQueue())
                .to(topicExchange())
                .with(QUEUE_MERCHANT);
    }

    @Bean
    Binding invoiceBinding() {
        return BindingBuilder.bind(invoiceQueue())
                .to(topicExchange())
                .with(QUEUE_INVOICE);
    }

    @Bean
    Binding replyToBinding() {
        return BindingBuilder.bind(replyToQueue())
                .to(topicExchange())
                .with(QUEUE_REPLY_TO);
    }

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setReplyAddress(QUEUE_REPLY_TO);
        rabbitTemplate.setReplyTimeout(20000);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    SimpleMessageListenerContainer orderRegistrationReplyContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(QUEUE_REPLY_TO);
        container.setReceiveTimeout(20000);
        container.setMessageListener(rabbitTemplate(connectionFactory));
        return container;
    }
}
