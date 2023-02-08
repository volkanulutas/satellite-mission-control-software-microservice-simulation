/*
 * (C)2022 Esen System Integration
 * The copyright to the computer program(s) herein is the property of Esen System Integration.
 * The program(s) may be used and/or copied only with the written permission of
 * Esen System Integration or in accordance with the terms and conditions stipulated
 * in the agreement/contract under which the program(s) have been supplied.
 */

package space.plan.telemetryservice.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.SubProtocolCapable;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.util.HtmlUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Slf4j
public class ServerWebSocketHandler extends TextWebSocketHandler implements SubProtocolCapable {
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        try {
            log.info("Server connection opened");
            sessions.add(session);
            TextMessage message = new TextMessage("one-time message from server");
            log.info("Server sends: {}", message);
            session.sendMessage(message);
        } catch (Exception ex) {
            log.error("Error is occurred while opening websocket connection!");
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        try {
            log.info("Server connection closed: {}", status);
            sessions.remove(session);
        } catch (Exception ex) {
            log.error("Error is occurred while closing websocket connection!");
        }
    }

    public <T> void sendMessages(T message) throws IOException {
        try {
            for (WebSocketSession session : sessions) {
                if (session.isOpen()) {
                    JSONObject jo = new JSONObject(message);
                    session.sendMessage(new TextMessage(jo.toString()));
                }
            }
        } catch (Exception ex) {
            log.error("Error is occurred while sending websocket messages!");
        }
    }

    public <T> void sendListMessages(List<?> messages) throws IOException {
        try {
            String jsonArray = objectMapper.writeValueAsString(messages);
            for (WebSocketSession session : sessions) {
                if (session.isOpen()) {
                    session.sendMessage(new TextMessage(jsonArray));
                }
            }
        } catch (Exception ex) {
            log.error("Error is occurred while sending websocket messages!");
        }
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
            String request = message.getPayload();
            log.info("Server received: {}", request);
            String response = String.format("response from server to '%s'", HtmlUtils.htmlEscape(request));
            log.info("Server sends: {}", response);
            session.sendMessage(new TextMessage(response));
        } catch (Exception ex) {
            log.error("Error is occurred while sending websocket message!");
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        log.info("Server transport error: {}", exception.getMessage());
    }

    @Override
    public List<String> getSubProtocols() {
        return Collections.singletonList("subprotocol.demo.websocket");
    }
}