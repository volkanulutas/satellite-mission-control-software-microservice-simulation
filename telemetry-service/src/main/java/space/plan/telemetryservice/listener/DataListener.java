package space.plan.telemetryservice.listener;

import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import space.plan.telemetryservice.config.rabbitmq.RabbitMqConfiguration;
import space.plan.telemetryservice.config.rabbitmq.RabbitMqManager;
import space.plan.telemetryservice.config.rabbitmq.RabbitMqQueueProperties;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class DataListener implements DeliverCallback {
    private final RabbitMqManager rabbitMqManager;

    private final RabbitMqConfiguration rabbitMqConfiguration;

    @Autowired
    public DataListener(RabbitMqConfiguration rabbitMqConfiguration, RabbitMqManager rabbitMqManager) {
        this.rabbitMqManager = rabbitMqManager;
        this.rabbitMqConfiguration = rabbitMqConfiguration;
        RabbitMqQueueProperties properties = RabbitMqQueueProperties.builder().queueName(rabbitMqConfiguration.getDataQueueName())
                .exchangeName(RabbitMqConfiguration.DEFAULT_EXCHANGE).routingKey(RabbitMqConfiguration.RK_DATA).isDurable(false).isExclusive(false)
                .isAutoDelete(true).build();
        this.rabbitMqManager.declareQueue(properties);
        this.rabbitMqManager.attachToQueue(rabbitMqConfiguration.getDataQueueName(), this);
    }

    @Override
    public void handle(String consumerTag, Delivery delivery) throws IOException {
        String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
        System.err.println("message: " + message);
        // NotificationDto notificationDto = new ObjectMapper().readValue(message, new TypeReference<>() {});
    }
}