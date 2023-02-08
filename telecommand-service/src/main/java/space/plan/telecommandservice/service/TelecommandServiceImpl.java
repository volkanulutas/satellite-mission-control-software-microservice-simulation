package space.plan.telecommandservice.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import space.plan.telecommandservice.config.rabbitmq.RabbitMqConfiguration;
import space.plan.telecommandservice.data.dto.TelecommandDto;

@Service
public class TelecommandServiceImpl implements TelecommandService {
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public TelecommandServiceImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendData() {
        rabbitTemplate.convertAndSend(RabbitMqConfiguration.DEFAULT_EXCHANGE, RabbitMqConfiguration.RK_DATA, "data");
    }

    @Override
    public void sendTelecommad(TelecommandDto telecommand) {
        // TODO: feign ile satellite-io ya gonder.
    }
}