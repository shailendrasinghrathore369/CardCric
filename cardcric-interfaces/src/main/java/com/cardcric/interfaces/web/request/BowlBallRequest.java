package com.cardcric.interfaces.web.request;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record BowlBallRequest(
    @NotNull UUID bowlerCardId,
    @NotNull UUID batsmanCardId
) {}
