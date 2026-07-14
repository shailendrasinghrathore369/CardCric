package com.cardcric.application.command;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record ProcessTossCommand(
    @NotNull UUID matchId,
    @NotNull UUID tossWinnerId,
    boolean electedToBat
) {}
