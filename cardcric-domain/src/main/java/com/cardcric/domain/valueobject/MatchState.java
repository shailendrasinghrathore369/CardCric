package com.cardcric.domain.valueobject;

import com.cardcric.domain.enums.MatchPhase;
import java.util.UUID;

public record MatchState(
    UUID matchId,
    MatchPhase phase,
    UUID battingPlayerId,
    UUID bowlingPlayerId,
    Score score,
    Overs overs,
    UUID strikerCardId,
    UUID nonStrikerCardId,
    UUID bowlerCardId,
    int totalOvers
) {
    public boolean isInningsOver() {
        return score.isAllOut() || overs.totalBalls() >= totalOvers * 6;
    }
}
