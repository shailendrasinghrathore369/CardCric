package com.cardcric.interfaces.web.request;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record ProcessTossRequest(
    @NotNull UUID tossWinnerId,
    boolean electedToBat
) {}
