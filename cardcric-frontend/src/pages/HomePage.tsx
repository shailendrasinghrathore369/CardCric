import { Link } from 'react-router-dom';

const QUICK_LINKS = [
  {
    to: '/matches',
    title: 'Matches',
    desc: 'Create a match, bowl balls, and track scores in real time.',
    icon: '🏏',
  },
  {
    to: '/cards',
    title: 'Cards',
    desc: 'Browse and create cricket player cards with custom stats.',
    icon: '🃏',
  },
  {
    to: '/players',
    title: 'Players',
    desc: 'Register players and manage profiles.',
    icon: '👤',
  },
];

export function HomePage() {
  return (
    <div className="space-y-8">
      <section className="text-center">
        <h1 className="text-4xl font-bold text-gray-900">CardCric</h1>
        <p className="mt-2 text-lg text-gray-500">
          Multiplayer Cricket Card Game
        </p>
      </section>

      <section className="grid gap-6 sm:grid-cols-3">
        {QUICK_LINKS.map(({ to, title, desc, icon }) => (
          <Link
            key={to}
            to={to}
            className="group rounded-xl border border-gray-200 bg-white p-6 shadow-sm transition-all hover:-translate-y-0.5 hover:shadow-md"
          >
            <div className="mb-3 text-3xl">{icon}</div>
            <h2 className="text-lg font-semibold text-gray-800 group-hover:text-pitch-600">
              {title}
            </h2>
            <p className="mt-1 text-sm text-gray-400">{desc}</p>
          </Link>
        ))}
      </section>

      <section className="rounded-xl border border-gray-200 bg-white p-6">
        <h2 className="mb-2 text-lg font-semibold text-gray-800">How to Play</h2>
        <ol className="ml-5 list-decimal space-y-1 text-sm text-gray-500">
          <li>Register one or more players.</li>
          <li>Create cricket cards with batting and bowling stats.</li>
          <li>Start a match by assigning teams and choosing the number of overs.</li>
          <li>Win the toss and decide to bat or bowl.</li>
          <li>Bowl ball-by-ball &mdash; the engine simulates results based on card stats.</li>
        </ol>
      </section>
    </div>
  );
}
