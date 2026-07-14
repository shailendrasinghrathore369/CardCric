package com.cardcric.application.command;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record BowlBallCommand(
    @NotNull UUID matchId,
    @NotNull UUID bowlerCardId,
    @NotNull UUID batsmanCardId
) {}
