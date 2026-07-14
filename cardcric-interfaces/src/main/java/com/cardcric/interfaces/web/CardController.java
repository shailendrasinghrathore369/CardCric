package com.cardcric.interfaces.web;

import com.cardcric.application.command.CreateCardCommand;
import com.cardcric.application.dto.CardDTO;
import com.cardcric.application.port.input.CardUseCase;
import com.cardcric.application.query.GetCardQuery;
import com.cardcric.application.query.ListCardsQuery;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    private final CardUseCase cardUseCase;

    public CardController(CardUseCase cardUseCase) {
        this.cardUseCase = cardUseCase;
    }

    @PostMapping
    public ResponseEntity<CardDTO> createCard(@Valid @RequestBody CreateCardCommand command) {
        var result = cardUseCase.createCard(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/{cardId}")
    public ResponseEntity<CardDTO> getCard(@PathVariable UUID cardId) {
        var result = cardUseCase.getCard(new GetCardQuery(cardId));
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<List<CardDTO>> listCards() {
        var result = cardUseCase.listCards(new ListCardsQuery());
        return ResponseEntity.ok(result);
    }
}
