package com.cardcric.application.command;

import com.cardcric.domain.enums.PlayerRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateCardCommand(
    @NotBlank String name,
    @NotNull PlayerRole role,
    int battingAverage,
    int bowlingAverage,
    int strikeRate,
    int economyRate,
    int fieldingRating,
    String imageUrl
) {}
