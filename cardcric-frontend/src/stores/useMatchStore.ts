import { create } from 'zustand';

interface MatchStore {
  currentMatchId: string | null;
  ballHistory: { over: number; ball: number; runs: number; wicket: boolean }[];
  setCurrentMatchId: (id: string | null) => void;
  addBallToHistory: (entry: { over: number; ball: number; runs: number; wicket: boolean }) => void;
  clearHistory: () => void;
  reset: () => void;
}

export const useMatchStore = create<MatchStore>((set) => ({
  currentMatchId: null,
  ballHistory: [],
  setCurrentMatchId: (id) => set({ currentMatchId: id }),
  addBallToHistory: (entry) =>
    set((state) => ({ ballHistory: [...state.ballHistory, entry] })),
  clearHistory: () => set({ ballHistory: [] }),
  reset: () => set({ currentMatchId: null, ballHistory: [] }),
}));
