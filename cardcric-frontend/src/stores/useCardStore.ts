import { create } from 'zustand';

interface CardStore {
  selectedPlayerOneCardIds: string[];
  selectedPlayerTwoCardIds: string[];
  setPlayerOneCardIds: (ids: string[]) => void;
  setPlayerTwoCardIds: (ids: string[]) => void;
  reset: () => void;
}

export const useCardStore = create<CardStore>((set) => ({
  selectedPlayerOneCardIds: [],
  selectedPlayerTwoCardIds: [],
  setPlayerOneCardIds: (ids) => set({ selectedPlayerOneCardIds: ids }),
  setPlayerTwoCardIds: (ids) => set({ selectedPlayerTwoCardIds: ids }),
  reset: () => set({ selectedPlayerOneCardIds: [], selectedPlayerTwoCardIds: [] }),
}));
