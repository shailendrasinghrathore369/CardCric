package com.cardcric.application.query;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record GetMatchStateQuery(
    @NotNull UUID matchId
) {}
