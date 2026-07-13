package com.cardcric.domain.valueobject;

public record BallResult(
    int runsScored,
    boolean isWicket,
    WicketType wicketType
) {
    public enum WicketType {
        BOWLED,
        CAUGHT,
        LBW,
        RUN_OUT,
        STUMPED,
        HIT_WICKET
    }

    public BallResult {
        if (runsScored < 0 || runsScored > 6 || runsScored == 5) {
            throw new IllegalArgumentException("runsScored must be 0, 1, 2, 3, 4, or 6");
        }
        if (isWicket && wicketType == null) {
            throw new IllegalArgumentException("wicketType required when isWicket is true");
        }
        if (!isWicket && wicketType != null) {
            throw new IllegalArgumentException("wicketType must be null when isWicket is false");
        }
    }

    public static BallResult dotBall() {
        return new BallResult(0, false, null);
    }

    public static BallResult boundary() {
        return new BallResult(4, false, null);
    }

    public static BallResult six() {
        return new BallResult(6, false, null);
    }

    public static BallResult wicket(WicketType type) {
        return new BallResult(0, true, type);
    }
}
