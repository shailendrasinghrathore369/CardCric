import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import * as playersApi from '../api/players';

export function usePlayerProfile(playerId: string | null) {
  return useQuery({
    queryKey: ['player', playerId],
    queryFn: () => playersApi.getPlayerProfile(playerId!),
    enabled: !!playerId,
  });
}

export function useRegisterPlayer() {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: playersApi.registerPlayer,
    onSuccess: (data) => {
      queryClient.setQueryData(['player', data.id], data);
    },
  });
}
