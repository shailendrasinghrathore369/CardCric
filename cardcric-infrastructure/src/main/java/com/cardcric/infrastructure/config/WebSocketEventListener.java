package com.cardcric.infrastructure.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {

    private static final Logger log = LoggerFactory.getLogger(WebSocketEventListener.class);

    @EventListener
    public void handleConnect(SessionConnectedEvent event) {
        log.info("WebSocket client connected");
    }

    @EventListener
    public void handleDisconnect(SessionDisconnectEvent event) {
        log.info("WebSocket client disconnected");
    }
}
