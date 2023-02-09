package space.plan.satelliteioservice.listener;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import space.plan.satelliteioservice.config.rabbitmq.RabbitMqConfiguration;
import space.plan.satelliteioservice.config.rabbitmq.RabbitMqManager;
import space.plan.satelliteioservice.config.rabbitmq.RabbitMqQueueProperties;
import space.plan.satelliteioservice.data.dto.BeaconMessageDto;
import space.plan.satelliteioservice.service.SatelliteIoService;

import java.io.IOException;

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
        byte[] body = delivery.getBody();
        System.out.println("delivery: " + body);
        Base64 base64 = new Base64();
        byte[] decode = base64.decode(body);
        String message = new String(decode);
        BeaconMessageDto messageDto = new ObjectMapper().readValue(message, new TypeReference<>() {});
        System.out.println("");
    }
}