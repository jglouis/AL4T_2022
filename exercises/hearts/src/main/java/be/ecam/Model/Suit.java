package be.ecam.Model;

public enum Suit {
    CLUB,
    SPADE,
    DIAMOND,
    HEART;

    @Override
    public String toString() {
        return switch (this) {
            case HEART -> "Heart";
            case DIAMOND -> "Diamond";
            case SPADE -> "Spade";
            case CLUB -> "Club";
        };
    }
}
