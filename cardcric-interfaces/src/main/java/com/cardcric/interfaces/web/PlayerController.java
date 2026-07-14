package com.cardcric.interfaces.web;

import com.cardcric.application.command.RegisterPlayerCommand;
import com.cardcric.application.dto.PlayerProfileDTO;
import com.cardcric.application.port.input.PlayerUseCase;
import com.cardcric.application.query.GetPlayerProfileQuery;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    private final PlayerUseCase playerUseCase;

    public PlayerController(PlayerUseCase playerUseCase) {
        this.playerUseCase = playerUseCase;
    }

    @PostMapping
    public ResponseEntity<PlayerProfileDTO> registerPlayer(@Valid @RequestBody RegisterPlayerCommand command) {
        var result = playerUseCase.registerPlayer(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/{playerId}")
    public ResponseEntity<PlayerProfileDTO> getPlayerProfile(@PathVariable UUID playerId) {
        var result = playerUseCase.getPlayerProfile(new GetPlayerProfileQuery(playerId));
        return ResponseEntity.ok(result);
    }
}
