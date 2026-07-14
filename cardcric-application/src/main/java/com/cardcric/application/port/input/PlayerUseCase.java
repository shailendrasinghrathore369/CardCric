package com.cardcric.application.port.input;

import com.cardcric.application.command.RegisterPlayerCommand;
import com.cardcric.application.dto.PlayerProfileDTO;
import com.cardcric.application.query.GetPlayerProfileQuery;

public interface PlayerUseCase {
    PlayerProfileDTO registerPlayer(RegisterPlayerCommand command);
    PlayerProfileDTO getPlayerProfile(GetPlayerProfileQuery query);
}
