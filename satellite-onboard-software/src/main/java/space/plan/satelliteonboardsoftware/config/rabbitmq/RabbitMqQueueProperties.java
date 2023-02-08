package space.plan.satelliteonboardsoftware.config.rabbitmq;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class RabbitMqQueueProperties {
    private String queueName;

    private String exchangeName;

    private String routingKey;

    private String consumerTag;

    private boolean isDurable;

    private boolean isExclusive;

    private boolean isAutoDelete;
}