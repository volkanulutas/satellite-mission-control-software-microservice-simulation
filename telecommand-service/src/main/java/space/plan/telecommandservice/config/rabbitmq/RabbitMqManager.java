package space.plan.telecommandservice.config.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import space.plan.telecommandservice.config.appconfig.AppProperty;

import java.io.IOException;
import java.net.ConnectException;
import java.util.concurrent.TimeoutException;

@Component
public class RabbitMqManager {
    private static final boolean AUTO_ACK = true;

    private static final Logger log = LoggerFactory.getLogger(RabbitMqManager.class);

    private Channel channel;

    @Autowired
    public RabbitMqManager(AppProperty appProperty, PlanSCredentialsProvider planSCredentialsProvider) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(appProperty.getRabbitMqHost());
        connectionFactory.setPort(appProperty.getRabbitMqPort());
        connectionFactory.setCredentialsProvider(planSCredentialsProvider);
        Connection connection = null;
        while (connection == null || !connection.isOpen()) {
            try {
                connection = connectionFactory.newConnection();
                if (connection != null) {
                    channel = connection.createChannel();
                    try {
                        channel.exchangeDeclare(RabbitMqConfiguration.DEFAULT_EXCHANGE, "direct", true);
                    } catch (Exception ex) {
                        log.info(RabbitMqConfiguration.DEFAULT_EXCHANGE + " is not created.");
                    }
                }
            } catch (ConnectException e) {
                log.error("Trying to connect to RabbitMQ Server");
            } catch (IOException | TimeoutException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void closeChannel() {
        try {
            if (channel.isOpen()) {
                channel.close();
            }
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    public void declareQueue(RabbitMqQueueProperties queueProperties) {
        try {
            channel.queueDeclare(queueProperties.getQueueName(), queueProperties.isDurable(), queueProperties.isExclusive(),
                    queueProperties.isAutoDelete(), null);
            log.info("Queue {} declared.", queueProperties.getQueueName());
            // If exchange name is equal to "" it will be automatically bind itself. (RabbitMQ behaviour)
            if (!"".equals(queueProperties.getExchangeName())) {
                channel.queueBind(queueProperties.getQueueName(), queueProperties.getExchangeName(), queueProperties.getRoutingKey());
                log.info(
                        "Binding " + queueProperties.getQueueName() + " to " + queueProperties.getExchangeName() + " with " + queueProperties.getRoutingKey());
            }
        } catch (IOException ex) {
            log.error("Error is occurred while declaring or binding queue. Detail: ", ex);
        }
    }

    public String attachToQueue(String queueName, DeliverCallback callback) {
        try {
            log.info("Consuming started for " + queueName);
            return channel.basicConsume(queueName, AUTO_ACK, callback, consumerTag -> {
            });
        } catch (IOException e) {
            log.error("Error is occurred while attaching queue {}", queueName);
        }
        return null;
    }

    public void detachToQueue(String queueName, String consumerTag) {
        try {
            log.info("Consuming stopped for " + queueName);
            channel.basicCancel(consumerTag);
            channel.queueDelete(queueName);
        } catch (IOException e) {
            log.error("Error is occurred while detaching queue {}", queueName);
        }
    }
}