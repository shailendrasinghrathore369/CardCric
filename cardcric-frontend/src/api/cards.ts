import { api } from './client';
import type { Card } from '../models/Card';

export function createCard(body: {
  name: string;
  role: string;
  battingAverage: number;
  bowlingAverage: number;
  strikeRate: number;
  economyRate: number;
  fieldingRating: number;
  imageUrl?: string;
}) {
  return api.post<Card>('/cards', body);
}

export function getCard(cardId: string) {
  return api.get<Card>(`/cards/${cardId}`);
}

export function listCards() {
  return api.get<Card[]>('/cards');
}
