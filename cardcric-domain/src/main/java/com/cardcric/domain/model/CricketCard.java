package com.cardcric.domain.model;

import com.cardcric.domain.enums.PlayerRole;
import java.util.UUID;

public record CricketCard(
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
    public CricketCard {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Card name must not be blank");
        }
        if (battingAverage < 0 || battingAverage > 100) {
            throw new IllegalArgumentException("Batting average must be between 0 and 100");
        }
        if (bowlingAverage < 0 || bowlingAverage > 100) {
            throw new IllegalArgumentException("Bowling average must be between 0 and 100");
        }
        if (strikeRate < 0 || strikeRate > 500) {
            throw new IllegalArgumentException("Strike rate must be between 0 and 500");
        }
        if (economyRate < 0 || economyRate > 30) {
            throw new IllegalArgumentException("Economy rate must be between 0 and 30");
        }
        if (fieldingRating < 0 || fieldingRating > 100) {
            throw new IllegalArgumentException("Fielding rating must be between 0 and 100");
        }
    }

    public boolean isBatsman() {
        return role == PlayerRole.BATSMAN || role == PlayerRole.ALL_ROUNDER;
    }

    public boolean isBowler() {
        return role == PlayerRole.BOWLER || role == PlayerRole.ALL_ROUNDER;
    }

    public boolean isWicketKeeper() {
        return role == PlayerRole.WICKET_KEEPER;
    }
}
