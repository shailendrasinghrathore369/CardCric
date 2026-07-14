import { create } from 'zustand';

interface MatchStore {
  currentMatchId: string | null;
  ballHistory: { over: number; ball: number; runs: number; wicket: boolean }[];
  isConnected: boolean;
  setCurrentMatchId: (id: string | null) => void;
  addBallToHistory: (entry: { over: number; ball: number; runs: number; wicket: boolean }) => void;
  clearHistory: () => void;
  setConnected: (connected: boolean) => void;
  reset: () => void;
}

export const useMatchStore = create<MatchStore>((set) => ({
  currentMatchId: null,
  ballHistory: [],
  isConnected: false,
  setCurrentMatchId: (id) => set({ currentMatchId: id }),
  addBallToHistory: (entry) =>
    set((state) => {
      const key = `${entry.over}.${entry.ball}`;
      const exists = state.ballHistory.some(
        (b) => `${b.over}.${b.ball}` === key
      );
      if (exists) return state;
      return { ballHistory: [...state.ballHistory, entry] };
    }),
  clearHistory: () => set({ ballHistory: [] }),
  setConnected: (connected) => set({ isConnected: connected }),
  reset: () => set({ currentMatchId: null, ballHistory: [], isConnected: false }),
}));
