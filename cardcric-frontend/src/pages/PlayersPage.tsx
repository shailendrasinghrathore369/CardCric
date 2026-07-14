import { useState } from 'react';
import { usePlayerProfile } from '../hooks/usePlayers';
import { PlayerForm } from '../components/players/PlayerForm';

export function PlayersPage() {
  const [lookupId, setLookupId] = useState('');
  const [searchId, setSearchId] = useState<string | null>(null);
  const { data: profile, isLoading } = usePlayerProfile(searchId);

  return (
    <div className="space-y-8">
      <section>
        <h2 className="mb-4 text-xl font-bold text-gray-800">Register Player</h2>
        <div className="max-w-md">
          <PlayerForm />
        </div>
      </section>

      <section>
        <h2 className="mb-4 text-xl font-bold text-gray-800">Lookup Player</h2>
        <div className="max-w-md space-y-4">
          <div className="flex gap-2">
            <input
              type="text"
              placeholder="Enter Player ID"
              value={lookupId}
              onChange={(e) => setLookupId(e.target.value)}
              className="flex-1 rounded-md border border-gray-300 px-3 py-2 text-sm focus:border-pitch-500 focus:outline-none focus:ring-1 focus:ring-pitch-500"
            />
            <button
              onClick={() => setSearchId(lookupId)}
              disabled={!lookupId}
              className="rounded-md bg-pitch-600 px-4 py-2 text-sm font-medium text-white hover:bg-pitch-700 disabled:cursor-not-allowed disabled:opacity-50"
            >
              Search
            </button>
          </div>

          {isLoading && <p className="text-sm text-gray-400">Loading...</p>}

          {profile && (
            <div className="rounded-lg border border-gray-200 bg-white p-4">
              <h3 className="font-semibold text-gray-800">{profile.displayName}</h3>
              <p className="text-xs text-gray-400">@{profile.username}</p>
              <p className="mt-2 text-xs text-gray-500">
                ID: <span className="font-mono">{profile.id}</span>
              </p>
              <p className="text-xs text-gray-500">
                Cards: {profile.cardIds.length === 0 ? 'none' : profile.cardIds.length}
              </p>
            </div>
          )}
        </div>
      </section>
    </div>
  );
}
