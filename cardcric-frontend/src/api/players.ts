import { api } from './client';
import type { PlayerProfile } from '../models/Player';

export function registerPlayer(body: { username: string; displayName: string }) {
  return api.post<PlayerProfile>('/players', body);
}

export function getPlayerProfile(playerId: string) {
  return api.get<PlayerProfile>(`/players/${playerId}`);
}
