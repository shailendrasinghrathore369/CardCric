package com.cardcric.domain.valueobject;

public record Score(int runs, int wickets) {
    public Score {
        if (runs < 0) {
            throw new IllegalArgumentException("Runs cannot be negative");
        }
        if (wickets < 0 || wickets > 10) {
            throw new IllegalArgumentException("Wickets must be between 0 and 10");
        }
    }

    public boolean isAllOut() {
        return wickets >= 10;
    }

    public Score addRuns(int additionalRuns) {
        return new Score(this.runs + additionalRuns, this.wickets);
    }

    public Score addWicket() {
        return new Score(this.runs, this.wickets + 1);
    }
}
