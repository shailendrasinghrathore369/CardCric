import { useState, type FormEvent } from 'react';
import { useRegisterPlayer } from '../../hooks/usePlayers';

export function PlayerForm() {
  const [username, setUsername] = useState('');
  const [displayName, setDisplayName] = useState('');
  const { mutate, isPending, data, error } = useRegisterPlayer();

  const handleSubmit = (e: FormEvent) => {
    e.preventDefault();
    mutate({ username, displayName });
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-4 rounded-lg border border-gray-200 bg-white p-6">
      <h3 className="text-lg font-semibold text-gray-800">Register Player</h3>

      <div>
        <label className="block text-sm font-medium text-gray-600">Username</label>
        <input
          type="text"
          required
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          className="mt-1 w-full rounded-md border border-gray-300 px-3 py-2 text-sm focus:border-pitch-500 focus:outline-none focus:ring-1 focus:ring-pitch-500"
        />
      </div>

      <div>
        <label className="block text-sm font-medium text-gray-600">Display Name</label>
        <input
          type="text"
          required
          value={displayName}
          onChange={(e) => setDisplayName(e.target.value)}
          className="mt-1 w-full rounded-md border border-gray-300 px-3 py-2 text-sm focus:border-pitch-500 focus:outline-none focus:ring-1 focus:ring-pitch-500"
        />
      </div>

      <button
        type="submit"
        disabled={isPending}
        className="w-full rounded-md bg-pitch-600 px-4 py-2 text-sm font-medium text-white hover:bg-pitch-700 disabled:cursor-not-allowed disabled:opacity-50"
      >
        {isPending ? 'Registering...' : 'Register'}
      </button>

      {error && (
        <p className="text-sm text-red-600">{error.message}</p>
      )}

      {data && (
        <div className="rounded-md bg-green-50 p-3 text-sm text-green-700">
          Player created: <span className="font-mono">{data.id}</span>
        </div>
      )}
    </form>
  );
}
