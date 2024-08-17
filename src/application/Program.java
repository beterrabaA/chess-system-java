package application;

import chess.ChessMatch;

public class Program {
    public static void main(String[] args) {
        System.out.println("Hello world");
        ChessMatch match = new ChessMatch();
        UI.printBoard(match.getPieces());
    }
}
