import type { Card } from '../../models/Card';
import { CardItem } from './CardItem';

interface CardGridProps {
  cards: Card[];
  selectedIds?: string[];
  onToggle?: (id: string) => void;
}

export function CardGrid({ cards, selectedIds = [], onToggle }: CardGridProps) {
  if (cards.length === 0) {
    return (
      <p className="py-8 text-center text-sm text-gray-400">
        No cards yet. Create one to get started.
      </p>
    );
  }

  return (
    <div className="grid grid-cols-1 gap-3 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4">
      {cards.map((card) => (
        <CardItem
          key={card.id}
          card={card}
          selected={selectedIds.includes(card.id)}
          onToggle={onToggle}
        />
      ))}
    </div>
  );
}
