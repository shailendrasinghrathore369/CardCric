package com.cardcric.interfaces.web;

import com.cardcric.application.command.BowlBallCommand;
import com.cardcric.application.command.CreateMatchCommand;
import com.cardcric.application.command.ProcessTossCommand;
import com.cardcric.application.dto.BowlBallResultDTO;
import com.cardcric.application.dto.MatchStateDTO;
import com.cardcric.application.port.input.MatchUseCase;
import com.cardcric.application.query.GetMatchStateQuery;
import com.cardcric.interfaces.web.request.BowlBallRequest;
import com.cardcric.interfaces.web.request.ProcessTossRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/matches")
public class MatchController {

    private final MatchUseCase matchUseCase;

    public MatchController(MatchUseCase matchUseCase) {
        this.matchUseCase = matchUseCase;
    }

    @PostMapping
    public ResponseEntity<MatchStateDTO> createMatch(@Valid @RequestBody CreateMatchCommand command) {
        var result = matchUseCase.createMatch(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PostMapping("/{matchId}/toss")
    public ResponseEntity<MatchStateDTO> processToss(
            @PathVariable UUID matchId,
            @Valid @RequestBody ProcessTossRequest request) {
        var command = new ProcessTossCommand(matchId, request.tossWinnerId(), request.electedToBat());
        var result = matchUseCase.processToss(command);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{matchId}/balls")
    public ResponseEntity<BowlBallResultDTO> bowlBall(
            @PathVariable UUID matchId,
            @Valid @RequestBody BowlBallRequest request) {
        var command = new BowlBallCommand(matchId, request.bowlerCardId(), request.batsmanCardId());
        var result = matchUseCase.bowlBall(command);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{matchId}")
    public ResponseEntity<MatchStateDTO> getMatchState(@PathVariable UUID matchId) {
        var result = matchUseCase.getMatchState(new GetMatchStateQuery(matchId));
        return ResponseEntity.ok(result);
    }
}
