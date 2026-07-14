import { Link } from 'react-router-dom';

export function NotFoundPage() {
  return (
    <div className="flex flex-col items-center justify-center py-20">
      <span className="text-6xl">🏏</span>
      <h1 className="mt-4 text-2xl font-bold text-gray-800">404 — Not Found</h1>
      <p className="mt-2 text-gray-400">This page does not exist.</p>
      <Link
        to="/"
        className="mt-6 rounded-md bg-pitch-600 px-4 py-2 text-sm font-medium text-white hover:bg-pitch-700"
      >
        Go Home
      </Link>
    </div>
  );
}
