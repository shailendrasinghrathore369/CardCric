package com.cardcric.application.dto;

import com.cardcric.domain.enums.PlayerRole;
import com.cardcric.domain.model.CricketCard;
import java.util.UUID;

public record CardDTO(
    UUID id,
    String name,
    PlayerRole role,
    int battingAverage,
    int bowlingAverage,
    int strikeRate,
    int economyRate,
    int fieldingRating,
    String imageUrl
) {
    public static CardDTO from(CricketCard card) {
        return new CardDTO(
            card.id(),
            card.name(),
            card.role(),
            card.battingAverage(),
            card.bowlingAverage(),
            card.strikeRate(),
            card.economyRate(),
            card.fieldingRating(),
            card.imageUrl()
        );
    }
}
