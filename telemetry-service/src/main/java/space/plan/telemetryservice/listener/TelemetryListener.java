package space.plan.telemetryservice.listener;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import space.plan.telemetryservice.config.rabbitmq.RabbitMqConfiguration;
import space.plan.telemetryservice.config.rabbitmq.RabbitMqManager;
import space.plan.telemetryservice.config.rabbitmq.RabbitMqQueueProperties;
import space.plan.telemetryservice.data.dto.BeaconMessageDto;
import space.plan.telemetryservice.data.dto.TelemetryMessageDto;
import space.plan.telemetryservice.service.TelemetryService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class TelemetryListener implements DeliverCallback {
    private final RabbitMqManager rabbitMqManager;

    private final RabbitMqConfiguration rabbitMqConfiguration;

    private final TelemetryService telemetryService;

    @Autowired
    public TelemetryListener(RabbitMqConfiguration rabbitMqConfiguration, RabbitMqManager rabbitMqManager, TelemetryService telemetryService) {
        this.rabbitMqManager = rabbitMqManager;
        this.rabbitMqConfiguration = rabbitMqConfiguration;
        this.telemetryService = telemetryService;
        RabbitMqQueueProperties properties = RabbitMqQueueProperties.builder().queueName(rabbitMqConfiguration.getTelemetryQueueName())
                .exchangeName(RabbitMqConfiguration.DEFAULT_EXCHANGE).routingKey(RabbitMqConfiguration.RK_TELEMETRY_MESSAGE).isDurable(false)
                .isExclusive(false).isAutoDelete(true).build();
        this.rabbitMqManager.declareQueue(properties);
        this.rabbitMqManager.attachToQueue(rabbitMqConfiguration.getTelemetryQueueName(), this);
    }

    @Override
    public void handle(String consumerTag, Delivery delivery) throws IOException {
        String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
        System.err.println("message: " + message);
        TelemetryMessageDto telemetryMessage = new ObjectMapper().readValue(message, new TypeReference<>() {});
        BeaconMessageDto beaconMessageDto = (BeaconMessageDto) telemetryMessage;
        telemetryService.processTelemetry(beaconMessageDto);
    }
}