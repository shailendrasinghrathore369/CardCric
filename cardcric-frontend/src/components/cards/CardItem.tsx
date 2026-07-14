import type { Card } from '../../models/Card';
import { ROLE_COLORS } from '../../utils/constants';

interface CardItemProps {
  card: Card;
  selected?: boolean;
  onToggle?: (id: string) => void;
}

export function CardItem({ card, selected, onToggle }: CardItemProps) {
  const roleClass = ROLE_COLORS[card.role] ?? 'bg-gray-100 text-gray-700';

  return (
    <button
      type="button"
      onClick={() => onToggle?.(card.id)}
      className={`w-full rounded-lg border-2 p-4 text-left transition-all ${
        selected
          ? 'border-pitch-500 bg-pitch-50 shadow-md'
          : 'border-gray-200 bg-white hover:border-gray-300 hover:shadow-sm'
      }`}
    >
      <div className="mb-2 flex items-start justify-between">
        <span className="font-semibold text-gray-900">{card.name}</span>
        <span className={`rounded-full px-2 py-0.5 text-xs font-medium ${roleClass}`}>
          {card.role.replace('_', ' ')}
        </span>
      </div>

      <div className="grid grid-cols-2 gap-x-4 gap-y-1 text-xs text-gray-500">
        <span>Bat: {card.battingAverage}</span>
        <span>SR: {card.strikeRate}</span>
        <span>Bowl: {card.bowlingAverage}</span>
        <span>Econ: {card.economyRate}</span>
        <span>Field: {card.fieldingRating}</span>
      </div>
    </button>
  );
}
