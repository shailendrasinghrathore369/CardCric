package com.cardcric.application.port.output;

import com.cardcric.application.dto.BowlBallResultDTO;
import com.cardcric.application.dto.MatchStateDTO;

import java.util.UUID;

public interface MatchEventBroadcaster {
    void broadcastBallResult(UUID matchId, BowlBallResultDTO result);
    void broadcastMatchState(UUID matchId, MatchStateDTO state);
}
