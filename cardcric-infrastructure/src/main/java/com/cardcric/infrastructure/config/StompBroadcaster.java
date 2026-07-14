package com.cardcric.infrastructure.config;

import com.cardcric.application.dto.BowlBallResultDTO;
import com.cardcric.application.dto.MatchStateDTO;
import com.cardcric.application.port.output.MatchEventBroadcaster;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class StompBroadcaster implements MatchEventBroadcaster {

    private final SimpMessagingTemplate messagingTemplate;

    public StompBroadcaster(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void broadcastBallResult(UUID matchId, BowlBallResultDTO result) {
        messagingTemplate.convertAndSend("/topic/matches/" + matchId + "/balls", result);
    }

    @Override
    public void broadcastMatchState(UUID matchId, MatchStateDTO state) {
        messagingTemplate.convertAndSend("/topic/matches/" + matchId + "/state", state);
    }
}
