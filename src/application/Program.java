package application;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.exceptions.ChessException;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ChessMatch match = new ChessMatch();
        List<ChessPiece> chessPiecesCaptured = new ArrayList<>();

        while (!match.isCheckMate()) {
            try {
            // pre-set king source using check logic
            UI.clearScreen();
            UI.printMatch(match,chessPiecesCaptured);
            System.out.println();
            System.out.print("Source: ");
            ChessPosition source = UI.readChessPosition(sc);

            boolean[][] possibleMoves = match.possibleMoves(source);
            UI.clearScreen();
            UI.printBoard(match.getPieces(),possibleMoves);

            System.out.println();
            System.out.print("Target: ");
            ChessPosition target = UI.readChessPosition(sc);

            ChessPiece capturedPiece = match.performChessMove(source,target);

            if (capturedPiece != null) {
                chessPiecesCaptured.add(capturedPiece);
            }
            } catch (ChessException | InputMismatchException chessException) {
                System.out.println(chessException.getMessage());
                sc.nextLine();
            }

        }
        UI.clearScreen();
        UI.printMatch(match,chessPiecesCaptured);
    }
}
