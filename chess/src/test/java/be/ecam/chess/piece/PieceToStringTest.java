package be.ecam.chess.piece;

import be.ecam.chess.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PieceToStringTest {

    @Test
    void testPawn() {
        Piece whitePawn = new Pawn(Color.WHITE);
        assertEquals("♙", whitePawn.toEmoticon());
        assertEquals("P", whitePawn.toString());
        Piece blackPawn = new Pawn(Color.BLACK);
        assertEquals("♟", blackPawn.toEmoticon());
        assertEquals("p", blackPawn.toString());
    }

    @Test
    void testRook() {
        Piece whiteRook = new Rook(Color.WHITE);
        assertEquals("♖", whiteRook.toEmoticon());
        assertEquals("R", whiteRook.toString());
        Piece blackRook = new Rook(Color.BLACK);
        assertEquals("♜", blackRook.toEmoticon());
        assertEquals("r", blackRook.toString());
    }

    @Test
    void testKnight() {
        Piece whiteKnight = new Knight(Color.WHITE);
        assertEquals("♘", whiteKnight.toEmoticon());
        assertEquals("N", whiteKnight.toString());
        Piece blackKnight = new Knight(Color.BLACK);
        assertEquals("♞", blackKnight.toEmoticon());
        assertEquals("n", blackKnight.toString());
    }

    @Test
    void testBishop() {
        Piece whiteBishop = new Bishop(Color.WHITE);
        assertEquals("♗", whiteBishop.toEmoticon());
        assertEquals("B", whiteBishop.toString());
        Piece blackBishop = new Bishop(Color.BLACK);
        assertEquals("♝", blackBishop.toEmoticon());
        assertEquals("b", blackBishop.toString());
    }

    @Test
    void testKing() {
        Piece whiteKing = new King(Color.WHITE);
        assertEquals("♔", whiteKing.toEmoticon());
        assertEquals("K", whiteKing.toString());
        Piece blackKing = new King(Color.BLACK);
        assertEquals("♚", blackKing.toEmoticon());
        assertEquals("k", blackKing.toString());
    }

    @Test
    void testQueen() {
        Piece whiteQueen = new Queen(Color.WHITE);
        assertEquals("♕", whiteQueen.toEmoticon());
        assertEquals("Q", whiteQueen.toString());
        Piece blackQueen = new Queen(Color.BLACK);
        assertEquals("♛", blackQueen.toEmoticon());
        assertEquals("q", blackQueen.toString());
    }
}
