import { Link, useLocation } from 'react-router-dom';

const NAV = [
  { to: '/', label: 'Home' },
  { to: '/matches', label: 'Matches' },
  { to: '/cards', label: 'Cards' },
  { to: '/players', label: 'Players' },
];

export function Header() {
  const { pathname } = useLocation();

  return (
    <header className="bg-pitch-700 text-white shadow-lg">
      <div className="mx-auto flex max-w-6xl items-center justify-between px-4 py-3">
        <Link to="/" className="flex items-center gap-2 text-xl font-bold tracking-tight">
          <span className="text-2xl">🏏</span>
          <span>CardCric</span>
        </Link>
        <nav className="flex gap-1">
          {NAV.map(({ to, label }) => {
            const active = pathname === to;
            return (
              <Link
                key={to}
                to={to}
                className={`rounded-md px-3 py-1.5 text-sm font-medium transition-colors ${
                  active
                    ? 'bg-pitch-600 text-white'
                    : 'text-pitch-100 hover:bg-pitch-600 hover:text-white'
                }`}
              >
                {label}
              </Link>
            );
          })}
        </nav>
      </div>
    </header>
  );
}
