package be.ecam.chess.rule;

public interface MoveIterator {
    /**
     * Wals to the next step and returns it.
     * @return A cordinate pair [x,y] or null if no more steps are available.
     */
    int[] nextStep();


}
