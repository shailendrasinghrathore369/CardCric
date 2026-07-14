export const ROLES = ['BATSMAN', 'BOWLER', 'ALL_ROUNDER', 'WICKET_KEEPER'] as const;

export const ROLE_COLORS: Record<string, string> = {
  BATSMAN: 'bg-orange-100 text-orange-800',
  BOWLER: 'bg-blue-100 text-blue-800',
  ALL_ROUNDER: 'bg-purple-100 text-purple-800',
  WICKET_KEEPER: 'bg-red-100 text-red-800',
};

export const WICKET_TYPES = [
  'BOWLED',
  'CAUGHT',
  'LBW',
  'RUN_OUT',
  'STUMPED',
  'HIT_WICKET',
] as const;
