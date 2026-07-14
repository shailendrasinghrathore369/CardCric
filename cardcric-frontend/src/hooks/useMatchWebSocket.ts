import { useEffect, useRef } from 'react';
import { Client } from '@stomp/stompjs';
import { useQueryClient } from '@tanstack/react-query';
import { useMatchStore } from '../stores/useMatchStore';
import type { BowlBallResult, MatchState } from '../models/Match';

export function useMatchWebSocket(matchId: string | null) {
  const clientRef = useRef<Client | null>(null);
  const queryClient = useQueryClient();
  const addBallToHistory = useMatchStore((s) => s.addBallToHistory);
  const setConnected = useMatchStore((s) => s.setConnected);

  useEffect(() => {
    if (!matchId) return;

    const protocol = window.location.protocol === 'https:' ? 'wss:' : 'ws:';
    const brokerURL = `${protocol}//${window.location.host}/ws`;

    const client = new Client({
      brokerURL,
      reconnectDelay: 5000,
      onConnect: () => {
        setConnected(true);

        client.subscribe(`/topic/matches/${matchId}/balls`, (message) => {
          const result: BowlBallResult = JSON.parse(message.body);
          queryClient.setQueryData(['match', matchId], result.newState);
          addBallToHistory({
            over: result.overNumber,
            ball: result.ballNumber,
            runs: result.ballResult.runsScored,
            wicket: result.ballResult.wicket,
          });
        });

        client.subscribe(`/topic/matches/${matchId}/state`, (message) => {
          const state: MatchState = JSON.parse(message.body);
          queryClient.setQueryData(['match', matchId], state);
        });
      },
      onDisconnect: () => {
        setConnected(false);
      },
      onStompError: () => {
        setConnected(false);
      },
    });

    client.activate();
    clientRef.current = client;

    return () => {
      setConnected(false);
      client.deactivate();
    };
  }, [matchId, queryClient, addBallToHistory, setConnected]);
}
