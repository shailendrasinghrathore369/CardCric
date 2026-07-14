package com.cardcric.application.port.input;

import com.cardcric.application.command.BowlBallCommand;
import com.cardcric.application.command.CreateMatchCommand;
import com.cardcric.application.command.ProcessTossCommand;
import com.cardcric.application.dto.BowlBallResultDTO;
import com.cardcric.application.dto.MatchStateDTO;
import com.cardcric.application.query.GetMatchStateQuery;

public interface MatchUseCase {
    MatchStateDTO createMatch(CreateMatchCommand command);
    MatchStateDTO processToss(ProcessTossCommand command);
    BowlBallResultDTO bowlBall(BowlBallCommand command);
    MatchStateDTO getMatchState(GetMatchStateQuery query);
}
