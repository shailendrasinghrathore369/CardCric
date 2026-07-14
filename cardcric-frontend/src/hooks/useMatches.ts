import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import * as matchesApi from '../api/matches';
import { useMatchStore } from '../stores/useMatchStore';

export function useMatchState(matchId: string | null) {
  return useQuery({
    queryKey: ['match', matchId],
    queryFn: () => matchesApi.getMatchState(matchId!),
    enabled: !!matchId,
    refetchInterval: false,
  });
}

export function useCreateMatch() {
  const queryClient = useQueryClient();
  const setCurrentMatchId = useMatchStore((s) => s.setCurrentMatchId);

  return useMutation({
    mutationFn: matchesApi.createMatch,
    onSuccess: (data) => {
      setCurrentMatchId(data.matchId);
      queryClient.setQueryData(['match', data.matchId], data);
    },
  });
}

export function useProcessToss() {
  const queryClient = useQueryClient();
  const currentMatchId = useMatchStore((s) => s.currentMatchId);

  return useMutation({
    mutationFn: (body: { tossWinnerId: string; electedToBat: boolean }) =>
      matchesApi.processToss(currentMatchId!, body),
    onSuccess: (data) => {
      queryClient.setQueryData(['match', data.matchId], data);
    },
  });
}

export function useBowlBall() {
  const queryClient = useQueryClient();
  const currentMatchId = useMatchStore((s) => s.currentMatchId);
  const addBallToHistory = useMatchStore((s) => s.addBallToHistory);

  return useMutation({
    mutationFn: (body: { bowlerCardId: string; batsmanCardId: string }) =>
      matchesApi.bowlBall(currentMatchId!, body),
    onSuccess: (data) => {
      queryClient.setQueryData(['match', data.newState.matchId], data.newState);
      addBallToHistory({
        over: data.overNumber,
        ball: data.ballNumber,
        runs: data.ballResult.runsScored,
        wicket: data.ballResult.wicket,
      });
    },
  });
}
