package com.cardcric.application.command;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;
import java.util.UUID;

public record CreateMatchCommand(
    @NotNull UUID playerOneId,
    @NotNull UUID playerTwoId,
    @NotEmpty List<UUID> playerOneCardIds,
    @NotEmpty List<UUID> playerTwoCardIds,
    @Positive int totalOvers
) {}
