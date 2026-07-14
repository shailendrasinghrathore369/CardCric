package com.cardcric.application.command;

import jakarta.validation.constraints.NotBlank;

public record RegisterPlayerCommand(
    @NotBlank String username,
    @NotBlank String displayName
) {}
