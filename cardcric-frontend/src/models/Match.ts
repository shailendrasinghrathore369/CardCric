export type MatchPhase = 'TOSS' | 'INNINGS_ONE' | 'INNINGS_TWO' | 'COMPLETED';

export interface MatchState {
  matchId: string;
  phase: MatchPhase;
  battingPlayerId: string;
  bowlingPlayerId: string;
  runs: number;
  wickets: number;
  overs: number;
  ballsInCurrentOver: number;
  oversDisplay: string;
  strikerCardId: string;
  nonStrikerCardId: string;
  bowlerCardId: string;
  totalOvers: number;
  inningsOver: boolean;
}

export interface BallResult {
  runsScored: number;
  wicket: boolean;
  wicketType: string | null;
}

export interface BowlBallResult {
  ballResult: BallResult;
  newState: MatchState;
  bowlerCardId: string;
  batsmanCardId: string;
  overNumber: number;
  ballNumber: number;
}
