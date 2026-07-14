import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import * as cardsApi from '../api/cards';

export function useCards() {
  return useQuery({
    queryKey: ['cards'],
    queryFn: cardsApi.listCards,
  });
}

export function useCard(cardId: string | null) {
  return useQuery({
    queryKey: ['card', cardId],
    queryFn: () => cardsApi.getCard(cardId!),
    enabled: !!cardId,
  });
}

export function useCreateCard() {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: cardsApi.createCard,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['cards'] });
    },
  });
}
