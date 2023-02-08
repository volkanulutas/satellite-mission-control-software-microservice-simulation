package space.plan.telemetryservice.config.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import space.plan.telemetryservice.websocket.ServerWebSocketHandler;

@Configuration
@EnableWebSocket
public class ServerWebSocketConfig implements WebSocketConfigurer, WebMvcConfigurer {
    private ServerWebSocketHandler telemetryWebSocketHandler;

    @Autowired
    public void setWebsocketHandlerMapMap(@Qualifier("websocketTelemetryHandler") ServerWebSocketHandler telemetryWebSocketHandler) {
        this.telemetryWebSocketHandler = telemetryWebSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(telemetryWebSocketHandler, "/telemetry/").setAllowedOrigins("*");
    }
}