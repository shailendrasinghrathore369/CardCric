package com.cardcric.application.dto;

import com.cardcric.domain.service.GameEngine;
import com.cardcric.domain.valueobject.BallResult;
import java.util.UUID;

public record BowlBallResultDTO(
    BallResult ballResult,
    MatchStateDTO newState,
    UUID bowlerCardId,
    UUID batsmanCardId,
    int overNumber,
    int ballNumber
) {
    public static BowlBallResultDTO from(GameEngine.BowlResult result) {
        return new BowlBallResultDTO(
            result.event().result(),
            MatchStateDTO.from(result.newState()),
            result.event().bowlerCardId(),
            result.event().batsmanCardId(),
            result.event().overNumber(),
            result.event().ballNumber()
        );
    }
}
