package com.cardcric.application.dto;

import com.cardcric.domain.enums.MatchPhase;
import com.cardcric.domain.valueobject.MatchState;
import java.util.UUID;

public record MatchStateDTO(
    UUID matchId,
    MatchPhase phase,
    UUID battingPlayerId,
    UUID bowlingPlayerId,
    int runs,
    int wickets,
    int overs,
    int ballsInCurrentOver,
    String oversDisplay,
    UUID strikerCardId,
    UUID nonStrikerCardId,
    UUID bowlerCardId,
    int totalOvers,
    boolean isInningsOver
) {
    public static MatchStateDTO from(MatchState state) {
        return new MatchStateDTO(
            state.matchId(),
            state.phase(),
            state.battingPlayerId(),
            state.bowlingPlayerId(),
            state.score().runs(),
            state.score().wickets(),
            state.overs().overs(),
            state.overs().ballsInCurrentOver(),
            state.overs().display(),
            state.strikerCardId(),
            state.nonStrikerCardId(),
            state.bowlerCardId(),
            state.totalOvers(),
            state.isInningsOver()
        );
    }
}
