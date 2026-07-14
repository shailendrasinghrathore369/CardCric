import type { BallResult } from '../../models/Match';

interface BallResultViewProps {
  result: BallResult;
}

export function BallResultView({ result }: BallResultViewProps) {
  const color = result.wicket
    ? 'bg-red-100 border-red-300 text-red-800'
    : result.runsScored >= 4
      ? 'bg-green-100 border-green-300 text-green-800'
      : 'bg-gray-100 border-gray-300 text-gray-700';

  const label = result.wicket
    ? `WICKET (${result.wicketType})`
    : result.runsScored === 0
      ? 'DOT BALL'
      : `${result.runsScored} RUN${result.runsScored > 1 ? 'S' : ''}`;

  return (
    <div className={`inline-flex items-center gap-1 rounded-lg border px-3 py-1 text-sm font-semibold ${color}`}>
      {result.wicket ? '⚡' : result.runsScored === 0 ? '●' : result.runsScored >= 6 ? '🎯' : '◆'}
      <span>{label}</span>
    </div>
  );
}
