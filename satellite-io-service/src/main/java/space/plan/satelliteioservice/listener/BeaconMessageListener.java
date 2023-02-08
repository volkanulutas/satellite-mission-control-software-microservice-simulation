package space.plan.satelliteioservice.listener;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import space.plan.satelliteioservice.config.rabbitmq.RabbitMqConfiguration;
import space.plan.satelliteioservice.config.rabbitmq.RabbitMqManager;
import space.plan.satelliteioservice.config.rabbitmq.RabbitMqQueueProperties;
import space.plan.satelliteioservice.data.dto.BeaconMessageDto;
import space.plan.satelliteioservice.service.SatelliteIoService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Component
public class BeaconMessageListener implements DeliverCallback {
    private final SatelliteIoService satelliteIoService;

    private final RabbitMqManager rabbitMqManager;

    private final RabbitMqConfiguration rabbitMqConfiguration;

    @Autowired
    public BeaconMessageListener(SatelliteIoService satelliteIoService, RabbitMqConfiguration rabbitMqConfiguration,
                                 RabbitMqManager rabbitMqManager) {
        this.satelliteIoService = satelliteIoService;
        this.rabbitMqManager = rabbitMqManager;
        this.rabbitMqConfiguration = rabbitMqConfiguration;
        RabbitMqQueueProperties properties = RabbitMqQueueProperties.builder().queueName(rabbitMqConfiguration.getBeaconMessageQueueName())
                .exchangeName(RabbitMqConfiguration.DEFAULT_EXCHANGE).routingKey(RabbitMqConfiguration.RK_BEACON_MESSAGE).isDurable(false)
                .isExclusive(false).isAutoDelete(true).build();
        this.rabbitMqManager.declareQueue(properties);
        this.rabbitMqManager.attachToQueue(rabbitMqConfiguration.getBeaconMessageQueueName(), this);
    }

    @Override
    public void handle(String consumerTag, Delivery delivery) throws IOException {
        String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
        byte[] encodedBeaconMessage = new ObjectMapper().readValue(message, new TypeReference<>() {});
        satelliteIoService.processRawTelemetry(encodedBeaconMessage);
    }
}