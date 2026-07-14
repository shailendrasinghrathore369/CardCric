import { useState, type FormEvent } from 'react';
import { useCards, useCreateCard } from '../hooks/useCards';
import { CardGrid } from '../components/cards/CardGrid';
import { ROLES } from '../utils/constants';

export function CardsPage() {
  const { data: cards, isLoading } = useCards();
  const { mutate: createCard, isPending, error } = useCreateCard();

  return (
    <div className="space-y-8">
      <section>
        <h2 className="mb-4 text-xl font-bold text-gray-800">Cards</h2>
        {isLoading ? (
          <p className="text-sm text-gray-400">Loading...</p>
        ) : (
          <CardGrid cards={cards ?? []} />
        )}
      </section>

      <section>
        <h2 className="mb-4 text-xl font-bold text-gray-800">Create Card</h2>
        <CardForm onSubmit={createCard} isPending={isPending} error={error?.message} />
      </section>
    </div>
  );
}

function CardForm({
  onSubmit,
  isPending,
  error,
}: {
  onSubmit: (body: {
    name: string;
    role: string;
    battingAverage: number;
    bowlingAverage: number;
    strikeRate: number;
    economyRate: number;
    fieldingRating: number;
  }) => void;
  isPending: boolean;
  error?: string;
}) {
  const [name, setName] = useState('');
  const [role, setRole] = useState<string>('BATSMAN');
  const [batAvg, setBatAvg] = useState(50);
  const [bowlAvg, setBowlAvg] = useState(50);
  const [sr, setSr] = useState(120);
  const [econ, setEcon] = useState(7);
  const [field, setField] = useState(70);

  const handleSubmit = (e: FormEvent) => {
    e.preventDefault();
    onSubmit({ name, role, battingAverage: batAvg, bowlingAverage: bowlAvg, strikeRate: sr, economyRate: econ, fieldingRating: field });
  };

  return (
    <form onSubmit={handleSubmit} className="max-w-lg space-y-4 rounded-lg border border-gray-200 bg-white p-6">
      <div className="grid gap-4 sm:grid-cols-2">
        <div>
          <label className="block text-xs font-medium text-gray-500">Name</label>
          <input
            type="text"
            required
            value={name}
            onChange={(e) => setName(e.target.value)}
            className="mt-1 w-full rounded-md border border-gray-300 px-3 py-2 text-sm focus:border-pitch-500 focus:outline-none focus:ring-1 focus:ring-pitch-500"
          />
        </div>
        <div>
          <label className="block text-xs font-medium text-gray-500">Role</label>
          <select
            value={role}
            onChange={(e) => setRole(e.target.value)}
            className="mt-1 w-full rounded-md border border-gray-300 px-3 py-2 text-sm focus:border-pitch-500 focus:outline-none focus:ring-1 focus:ring-pitch-500"
          >
            {ROLES.map((r) => (
              <option key={r} value={r}>
                {r.replace('_', ' ')}
              </option>
            ))}
          </select>
        </div>
        <div>
          <label className="block text-xs font-medium text-gray-500">Batting Avg (0–100)</label>
          <input
            type="range"
            min={0}
            max={100}
            value={batAvg}
            onChange={(e) => setBatAvg(Number(e.target.value))}
            className="mt-1 w-full"
          />
          <span className="text-xs text-gray-400">{batAvg}</span>
        </div>
        <div>
          <label className="block text-xs font-medium text-gray-500">Bowling Avg (0–100)</label>
          <input
            type="range"
            min={0}
            max={100}
            value={bowlAvg}
            onChange={(e) => setBowlAvg(Number(e.target.value))}
            className="mt-1 w-full"
          />
          <span className="text-xs text-gray-400">{bowlAvg}</span>
        </div>
        <div>
          <label className="block text-xs font-medium text-gray-500">Strike Rate (0–500)</label>
          <input
            type="range"
            min={0}
            max={500}
            value={sr}
            onChange={(e) => setSr(Number(e.target.value))}
            className="mt-1 w-full"
          />
          <span className="text-xs text-gray-400">{sr}</span>
        </div>
        <div>
          <label className="block text-xs font-medium text-gray-500">Economy (0–30)</label>
          <input
            type="range"
            min={0}
            max={30}
            value={econ}
            onChange={(e) => setEcon(Number(e.target.value))}
            className="mt-1 w-full"
          />
          <span className="text-xs text-gray-400">{econ}</span>
        </div>
        <div className="sm:col-span-2">
          <label className="block text-xs font-medium text-gray-500">Fielding (0–100)</label>
          <input
            type="range"
            min={0}
            max={100}
            value={field}
            onChange={(e) => setField(Number(e.target.value))}
            className="mt-1 w-full"
          />
          <span className="text-xs text-gray-400">{field}</span>
        </div>
      </div>

      <button
        type="submit"
        disabled={isPending}
        className="w-full rounded-md bg-pitch-600 px-4 py-2 text-sm font-medium text-white hover:bg-pitch-700 disabled:cursor-not-allowed disabled:opacity-50"
      >
        {isPending ? 'Creating...' : 'Create Card'}
      </button>

      {error && <p className="text-sm text-red-600">{error}</p>}
    </form>
  );
}
