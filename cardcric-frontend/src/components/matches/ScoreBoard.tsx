import type { MatchState } from '../../models/Match';

interface ScoreBoardProps {
  state: MatchState;
}

export function ScoreBoard({ state }: ScoreBoardProps) {
  const phaseLabel = (() => {
    switch (state.phase) {
      case 'TOSS':
        return 'Toss Pending';
      case 'INNINGS_ONE':
        return 'Innings 1';
      case 'INNINGS_TWO':
        return 'Innings 2';
      case 'COMPLETED':
        return 'Match Over';
    }
  })();

  return (
    <div className="rounded-xl bg-white p-6 shadow-md">
      <div className="mb-4 flex items-center justify-between">
        <h2 className="text-lg font-semibold text-gray-700">{phaseLabel}</h2>
        <span className="rounded-full bg-gray-100 px-3 py-1 text-sm text-gray-500">
          {state.oversDisplay} / {state.totalOvers} ov
        </span>
      </div>

      <div className="flex items-baseline gap-2">
        <span className="text-5xl font-bold tabular-nums text-gray-900">
          {state.runs}/{state.wickets}
        </span>
        <span className="text-lg text-gray-400">
          {state.inningsOver ? '(all out)' : ''}
        </span>
      </div>

      {state.phase !== 'TOSS' && state.phase !== 'COMPLETED' && (
        <div className="mt-4 grid grid-cols-2 gap-4 text-sm">
          <div>
            <span className="text-gray-400">Striker</span>
            <p className="font-mono text-xs text-gray-600">{state.strikerCardId.slice(0, 8)}...</p>
          </div>
          <div>
            <span className="text-gray-400">Bowler</span>
            <p className="font-mono text-xs text-gray-600">{state.bowlerCardId.slice(0, 8)}...</p>
          </div>
        </div>
      )}
    </div>
  );
}
