import { api } from './client';
import type { MatchState, BowlBallResult } from '../models/Match';

export function createMatch(body: {
  playerOneId: string;
  playerTwoId: string;
  playerOneCardIds: string[];
  playerTwoCardIds: string[];
  totalOvers: number;
}) {
  return api.post<MatchState>('/matches', body);
}

export function processToss(matchId: string, body: { tossWinnerId: string; electedToBat: boolean }) {
  return api.post<MatchState>(`/matches/${matchId}/toss`, body);
}

export function bowlBall(matchId: string, body: { bowlerCardId: string; batsmanCardId: string }) {
  return api.post<BowlBallResult>(`/matches/${matchId}/balls`, body);
}

export function getMatchState(matchId: string) {
  return api.get<MatchState>(`/matches/${matchId}`);
}
