export type PlayerRole = 'BATSMAN' | 'BOWLER' | 'ALL_ROUNDER' | 'WICKET_KEEPER';

export interface Card {
  id: string;
  name: string;
  role: PlayerRole;
  battingAverage: number;
  bowlingAverage: number;
  strikeRate: number;
  economyRate: number;
  fieldingRating: number;
  imageUrl: string;
}
