import { useState, type FormEvent } from 'react';
import { useCards } from '../hooks/useCards';
import { useCreateMatch, useProcessToss, useBowlBall, useMatchState } from '../hooks/useMatches';
import { useMatchStore } from '../stores/useMatchStore';
import { ScoreBoard } from '../components/matches/ScoreBoard';
import { BallResultView } from '../components/matches/BallResultView';
import { CardGrid } from '../components/cards/CardGrid';
import type { MatchPhase } from '../models/Match';

export function MatchesPage() {
  const { currentMatchId, ballHistory, reset } = useMatchStore();
  const { data: matchState, isLoading } = useMatchState(currentMatchId);
  const { mutate: createMatch, isPending: isCreating } = useCreateMatch();
  const { mutate: processToss, isPending: isTossing } = useProcessToss();
  const { mutate: bowlBall, isPending: isBowling, data: lastResult } = useBowlBall();

  if (currentMatchId && matchState) {
    return (
      <GameView
        state={matchState}
        matchId={currentMatchId}
        ballHistory={ballHistory}
        lastResult={lastResult ?? null}
        onToss={(winnerId, bat) => processToss({ tossWinnerId: winnerId, electedToBat: bat })}
        onBowl={(bowlerId, batsmanId) => bowlBall({ bowlerCardId: bowlerId, batsmanCardId: batsmanId })}
        isTossing={isTossing}
        isBowling={isBowling}
        onNewGame={() => { reset(); }}
      />
    );
  }

  if (isLoading) {
    return <p className="py-8 text-center text-gray-400">Loading match...</p>;
  }

  return <CreateMatchForm onCreate={createMatch} isPending={isCreating} />;
}

function CreateMatchForm({
  onCreate,
  isPending,
}: {
  onCreate: (body: {
    playerOneId: string;
    playerTwoId: string;
    playerOneCardIds: string[];
    playerTwoCardIds: string[];
    totalOvers: number;
  }) => void;
  isPending: boolean;
}) {
  const [p1, setP1] = useState('');
  const [p2, setP2] = useState('');
  const [p1Cards, setP1Cards] = useState<string[]>([]);
  const [p2Cards, setP2Cards] = useState<string[]>([]);
  const [overs, setOvers] = useState(5);
  const [activePlayer, setActivePlayer] = useState<1 | 2>(1);
  const { data: allCards } = useCards();

  const handleSubmit = (e: FormEvent) => {
    e.preventDefault();
    onCreate({
      playerOneId: p1,
      playerTwoId: p2,
      playerOneCardIds: p1Cards,
      playerTwoCardIds: p2Cards,
      totalOvers: overs,
    });
  };

  return (
    <form onSubmit={handleSubmit} className="mx-auto max-w-2xl space-y-6">
      <h2 className="text-xl font-bold text-gray-800">New Match</h2>

      <div className="grid gap-4 sm:grid-cols-2">
        <div>
          <label className="block text-sm font-medium text-gray-600">Player 1 ID</label>
          <input
            type="text"
            required
            value={p1}
            onChange={(e) => setP1(e.target.value)}
            className="mt-1 w-full rounded-md border border-gray-300 px-3 py-2 text-sm focus:border-pitch-500 focus:outline-none focus:ring-1 focus:ring-pitch-500"
          />
        </div>
        <div>
          <label className="block text-sm font-medium text-gray-600">Player 2 ID</label>
          <input
            type="text"
            required
            value={p2}
            onChange={(e) => setP2(e.target.value)}
            className="mt-1 w-full rounded-md border border-gray-300 px-3 py-2 text-sm focus:border-pitch-500 focus:outline-none focus:ring-1 focus:ring-pitch-500"
          />
        </div>
      </div>

      <div>
        <label className="block text-sm font-medium text-gray-600">Total Overs</label>
        <input
          type="number"
          min={1}
          max={50}
          value={overs}
          onChange={(e) => setOvers(Number(e.target.value))}
          className="mt-1 w-32 rounded-md border border-gray-300 px-3 py-2 text-sm focus:border-pitch-500 focus:outline-none focus:ring-1 focus:ring-pitch-500"
        />
      </div>

      <div>
        <div className="mb-2 flex items-center gap-3">
          <span className="text-sm font-medium text-gray-600">Select Cards</span>
          <button
            type="button"
            onClick={() => setActivePlayer(1)}
            className={`rounded-md px-3 py-1 text-xs font-medium ${
              activePlayer === 1 ? 'bg-pitch-600 text-white' : 'bg-gray-100 text-gray-600'
            }`}
          >
            Player 1 ({p1Cards.length})
          </button>
          <button
            type="button"
            onClick={() => setActivePlayer(2)}
            className={`rounded-md px-3 py-1 text-xs font-medium ${
              activePlayer === 2 ? 'bg-pitch-600 text-white' : 'bg-gray-100 text-gray-600'
            }`}
          >
            Player 2 ({p2Cards.length})
          </button>
        </div>

        <div className="max-h-64 overflow-y-auto rounded-lg border border-gray-200 p-2">
          {allCards ? (
            <CardGrid
              cards={allCards}
              selectedIds={activePlayer === 1 ? p1Cards : p2Cards}
              onToggle={(id) => {
                if (activePlayer === 1) {
                  setP1Cards((prev) =>
                    prev.includes(id) ? prev.filter((x) => x !== id) : [...prev, id],
                  );
                } else {
                  setP2Cards((prev) =>
                    prev.includes(id) ? prev.filter((x) => x !== id) : [...prev, id],
                  );
                }
              }}
            />
          ) : (
            <p className="py-4 text-center text-sm text-gray-400">Loading cards...</p>
          )}
        </div>
      </div>

      <button
        type="submit"
        disabled={isPending || p1Cards.length === 0 || p2Cards.length === 0}
        className="w-full rounded-md bg-pitch-600 px-4 py-3 text-sm font-medium text-white hover:bg-pitch-700 disabled:cursor-not-allowed disabled:opacity-50"
      >
        {isPending ? 'Creating...' : 'Create Match'}
      </button>
    </form>
  );
}

function GameView({
  state,
  matchId,
  ballHistory,
  lastResult,
  onToss,
  onBowl,
  isTossing,
  isBowling,
  onNewGame,
}: {
  state: { matchId: string; phase: MatchPhase; inningsOver: boolean };
  matchId: string;
  ballHistory: { over: number; ball: number; runs: number; wicket: boolean }[];
  lastResult: { ballResult: { runsScored: number; wicket: boolean; wicketType: string | null } } | null;
  onToss: (winnerId: string, electedToBat: boolean) => void;
  onBowl: (bowlerCardId: string, batsmanCardId: string) => void;
  isTossing: boolean;
  isBowling: boolean;
  onNewGame: () => void;
}) {
  const [tossWinner, setTossWinner] = useState('');
  const [electToBat, setElectToBat] = useState(true);
  const [bowlerId, setBowlerId] = useState('');
  const [batsmanId, setBatsmanId] = useState('');

  return (
    <div className="mx-auto max-w-2xl space-y-6">
      <div className="flex items-center justify-between">
        <h2 className="text-xl font-bold text-gray-800">
          Match <span className="font-mono text-sm text-gray-400">{matchId.slice(0, 8)}...</span>
        </h2>
        <button
          onClick={onNewGame}
          className="rounded-md border border-gray-300 px-3 py-1 text-xs text-gray-500 hover:bg-gray-50"
        >
          New Match
        </button>
      </div>

      <ScoreBoard state={state as Parameters<typeof ScoreBoard>[0]['state']} />

      {lastResult && (
        <div className="flex justify-center">
          <BallResultView result={lastResult.ballResult} />
        </div>
      )}

      {state.phase === 'TOSS' && (
        <TossSection
          tossWinner={tossWinner}
          setTossWinner={setTossWinner}
          electToBat={electToBat}
          setElectToBat={setElectToBat}
          onToss={() => onToss(tossWinner, electToBat)}
          isPending={isTossing}
        />
      )}

      {(state.phase === 'INNINGS_ONE' || state.phase === 'INNINGS_TWO') && !state.inningsOver && (
        <BowlSection
          bowlerId={bowlerId}
          setBowlerId={setBowlerId}
          batsmanId={batsmanId}
          setBatsmanId={setBatsmanId}
          onBowl={() => onBowl(bowlerId, batsmanId)}
          isPending={isBowling}
        />
      )}

      {ballHistory.length > 0 && (
        <div>
          <h3 className="mb-2 text-sm font-semibold text-gray-600">Ball-by-Ball</h3>
          <div className="flex flex-wrap gap-1.5">
            {ballHistory.map((b, i) => (
              <span
                key={i}
                className={`inline-flex h-7 w-7 items-center justify-center rounded-full text-xs font-bold ${
                  b.wicket
                    ? 'bg-red-100 text-red-700'
                    : b.runs === 0
                      ? 'bg-gray-200 text-gray-500'
                      : b.runs >= 4
                        ? 'bg-green-100 text-green-700'
                        : 'bg-blue-100 text-blue-700'
                }`}
                title={`Over ${b.over}.${b.ball}`}
              >
                {b.wicket ? 'W' : b.runs}
              </span>
            ))}
          </div>
        </div>
      )}

      <div className="rounded-lg border border-gray-200 bg-gray-50 p-4 text-xs text-gray-400">
        <p>Match ID: <span className="font-mono">{matchId}</span></p>
        <p>Phase: {state.phase}</p>
      </div>
    </div>
  );
}

function TossSection({
  tossWinner,
  setTossWinner,
  electToBat,
  setElectToBat,
  onToss,
  isPending,
}: {
  tossWinner: string;
  setTossWinner: (v: string) => void;
  electToBat: boolean;
  setElectToBat: (v: boolean) => void;
  onToss: () => void;
  isPending: boolean;
}) {
  return (
    <div className="rounded-lg border border-gray-200 bg-white p-4">
      <h3 className="mb-3 text-sm font-semibold text-gray-700">Toss</h3>
      <div className="space-y-3">
        <div>
          <label className="block text-xs font-medium text-gray-500">Toss Winner ID</label>
          <input
            type="text"
            required
            value={tossWinner}
            onChange={(e) => setTossWinner(e.target.value)}
            className="mt-1 w-full rounded-md border border-gray-300 px-3 py-2 text-sm focus:border-pitch-500 focus:outline-none focus:ring-1 focus:ring-pitch-500"
          />
        </div>
        <div className="flex gap-4">
          <label className="flex items-center gap-2 text-sm text-gray-600">
            <input
              type="radio"
              checked={electToBat}
              onChange={() => setElectToBat(true)}
            />
            Elect to Bat
          </label>
          <label className="flex items-center gap-2 text-sm text-gray-600">
            <input
              type="radio"
              checked={!electToBat}
              onChange={() => setElectToBat(false)}
            />
            Elect to Bowl
          </label>
        </div>
        <button
          onClick={onToss}
          disabled={isPending || !tossWinner}
          className="w-full rounded-md bg-yellow-500 px-4 py-2 text-sm font-medium text-white hover:bg-yellow-600 disabled:cursor-not-allowed disabled:opacity-50"
        >
          {isPending ? 'Processing...' : 'Confirm Toss'}
        </button>
      </div>
    </div>
  );
}

function BowlSection({
  bowlerId,
  setBowlerId,
  batsmanId,
  setBatsmanId,
  onBowl,
  isPending,
}: {
  bowlerId: string;
  setBowlerId: (v: string) => void;
  batsmanId: string;
  setBatsmanId: (v: string) => void;
  onBowl: () => void;
  isPending: boolean;
}) {
  return (
    <div className="rounded-lg border border-gray-200 bg-white p-4">
      <h3 className="mb-3 text-sm font-semibold text-gray-700">Bowl a Ball</h3>
      <div className="grid gap-3 sm:grid-cols-2">
        <div>
          <label className="block text-xs font-medium text-gray-500">Bowler Card ID</label>
          <input
            type="text"
            required
            value={bowlerId}
            onChange={(e) => setBowlerId(e.target.value)}
            className="mt-1 w-full rounded-md border border-gray-300 px-3 py-2 text-sm focus:border-pitch-500 focus:outline-none focus:ring-1 focus:ring-pitch-500"
          />
        </div>
        <div>
          <label className="block text-xs font-medium text-gray-500">Batsman Card ID</label>
          <input
            type="text"
            required
            value={batsmanId}
            onChange={(e) => setBatsmanId(e.target.value)}
            className="mt-1 w-full rounded-md border border-gray-300 px-3 py-2 text-sm focus:border-pitch-500 focus:outline-none focus:ring-1 focus:ring-pitch-500"
          />
        </div>
      </div>
      <button
        onClick={onBowl}
        disabled={isPending || !bowlerId || !batsmanId}
        className="mt-3 w-full rounded-md bg-pitch-600 px-4 py-2 text-sm font-medium text-white hover:bg-pitch-700 disabled:cursor-not-allowed disabled:opacity-50"
      >
        {isPending ? 'Bowling...' : 'Bowl!'}
      </button>
    </div>
  );
}
