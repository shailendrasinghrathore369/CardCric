package com.cardcric.domain.valueobject;

public record Overs(int totalBalls) {
    private static final int BALLS_PER_OVER = 6;

    public Overs {
        if (totalBalls < 0) {
            throw new IllegalArgumentException("Total balls cannot be negative");
        }
    }

    public int overs() {
        return totalBalls / BALLS_PER_OVER;
    }

    public int ballsInCurrentOver() {
        return totalBalls % BALLS_PER_OVER;
    }

    public String display() {
        return overs() + "." + ballsInCurrentOver();
    }

    public Overs addBall() {
        return new Overs(totalBalls + 1);
    }

    public boolean isOverComplete() {
        return ballsInCurrentOver() == 0 && totalBalls > 0;
    }

    public Overs nextOver() {
        int ballsToNextOver = BALLS_PER_OVER - ballsInCurrentOver();
        return new Overs(totalBalls + ballsToNextOver);
    }

    public static Overs fromDisplay(String display) {
        String[] parts = display.split("\\.");
        int overs = Integer.parseInt(parts[0]);
        int balls = parts.length > 1 ? Integer.parseInt(parts[1]) : 0;
        if (balls >= BALLS_PER_OVER) {
            throw new IllegalArgumentException("Balls in over must be less than " + BALLS_PER_OVER);
        }
        return new Overs(overs * BALLS_PER_OVER + balls);
    }
}
