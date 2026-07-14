package com.cardcric.application.port.input;

import com.cardcric.application.command.CreateCardCommand;
import com.cardcric.application.dto.CardDTO;
import com.cardcric.application.query.GetCardQuery;
import com.cardcric.application.query.ListCardsQuery;
import java.util.List;

public interface CardUseCase {
    CardDTO createCard(CreateCardCommand command);
    CardDTO getCard(GetCardQuery query);
    List<CardDTO> listCards(ListCardsQuery query);
}
