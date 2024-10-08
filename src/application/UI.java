package application;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class UI {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void printBoard(ChessPiece[][] pieces) {
        for (int i = 0; i < pieces.length; i++) {
            System.out.print((8-i) + " ");
            for (int j = 0; j < pieces.length; j++) {
                printPiece(pieces[i][j],false);
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h");
    }

    public static void printBoard(ChessPiece[][] pieces,boolean[][] possibleMoves) {
        for (int i = 0; i < pieces.length; i++) {
            System.out.print((8-i) + " ");
            for (int j = 0; j < pieces.length; j++) {
                printPiece(pieces[i][j],possibleMoves[i][j]);
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h");
    }

    public static void printMatch(ChessMatch match,List<ChessPiece> capturedList) {
        printBoard(match.getPieces());
        System.out.println();
        printCapturedPieces(capturedList);
        System.out.println();
        System.out.println("Turn: " + match.getTurn());
        if (!match.isCheckMate()) {
        System.out.println("Waiting player: " + match.getCurrentPlayer());
        if (match.isCheck()) System.out.println("!!!CHECK!!!");
        } else {
            System.out.println("!!!CHECKMATE!!!");
            System.out.println("Winner: " + match.getCurrentPlayer());
        }

    }

    public static ChessPosition readChessPosition(Scanner sc) {
        try {
            String inputText = sc.nextLine();
            char colum = inputText.charAt(0);
            int row = Integer.parseInt(inputText.substring(1));
            return new ChessPosition(colum,row);
        } catch (RuntimeException e) {
            throw new InputMismatchException("Error reading chess position:Invalid values found.");
        }

    }

    private static void printCapturedPieces(List<ChessPiece> pieceList) {
        List<ChessPiece> white = pieceList.stream().filter(x -> x.getColor() == Color.WHITE).toList();
        List<ChessPiece> black = pieceList.stream().filter(x -> x.getColor() == Color.BLACK).toList();

        System.out.println("Captured pieces:");
        System.out.print("White: ");
        System.out.print(ANSI_WHITE);
        System.out.println(Arrays.toString(white.toArray()));
        System.out.print(ANSI_RESET);
        System.out.print("Black: ");
        System.out.print(ANSI_YELLOW);
        System.out.println(Arrays.toString(black.toArray()));
        System.out.print(ANSI_RESET);
    }

    private static void printPiece(ChessPiece piece,boolean bg) {
        if (bg) System.out.print(ANSI_BLUE_BACKGROUND);
        if (piece == null) {
            System.out.print("-" + ANSI_RESET);
        } else {
            if (piece.getColor() == Color.WHITE) {
                System.out.print(ANSI_WHITE + piece + ANSI_RESET);
            } else {
                System.out.print(ANSI_YELLOW + piece + ANSI_RESET);
            }
        }
        System.out.print(" ");
    }
}
