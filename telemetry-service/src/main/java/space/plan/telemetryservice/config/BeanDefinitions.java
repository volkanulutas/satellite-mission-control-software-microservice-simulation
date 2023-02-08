package space.plan.telemetryservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import space.plan.telemetryservice.websocket.ServerWebSocketHandler;

@Configuration
public class BeanDefinitions {
    @Bean("websocketTelemetryHandler")
    public ServerWebSocketHandler websocketNotificationHandler() {
        return new ServerWebSocketHandler();
    }
}