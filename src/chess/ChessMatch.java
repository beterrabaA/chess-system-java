package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.exceptions.ChessException;
import chess.pieces.King;
import chess.pieces.Rook;

import java.util.ArrayList;
import java.util.List;

public class ChessMatch {
    private int turn;
    private boolean isCheck;
    private boolean isCheckMate;
    private Color currentPlayer;
    private Board board;
    private List<Piece> piecesOnTheBoard = new ArrayList<>();
    private List<Piece> capturedPieces = new ArrayList<>();

    public ChessMatch() {
        this.board = new Board(8,8);
        this.turn = 1;
        this.currentPlayer =  Color.WHITE;
        initialSetup();
    }

    public int getTurn() {
        return turn;
    }

    public Color getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public boolean isCheckMate() {
        return isCheckMate;
    }

    public ChessPiece[][] getPieces() {
        ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {
                mat[i][j] = (ChessPiece) board.piece(i,j);
            }
        }
        return mat;
    }

    public boolean[][] possibleMoves(ChessPosition sourcePosition) {
        Position position = sourcePosition.toPosition();
        validateSourcePosition(position);
        return board.piece(position).possibleMoves();
    }

    public ChessPiece performChessMove(ChessPosition sourcePosition,ChessPosition targetPosition) {
        Position source = sourcePosition.toPosition();
        Position target = targetPosition.toPosition();

        validateSourcePosition(source);
        validateTargetPosition(source,target);
        Piece capturedPiece = makeMove(source,target);

        if (testCheck(currentPlayer)) {
            undoMove(source,target,capturedPiece);
            throw new ChessException("You can't put yourself in check!");
        }

        isCheck = testCheck(opponent(currentPlayer));

        if(testCheckMate(opponent(currentPlayer))) {
            isCheckMate = true;
        } else {
            nextTurn();
        }
        return (ChessPiece) capturedPiece;
    }

    private void initialSetup() {
        // white
        placeNewPiece('a',1,new Rook(board,Color.WHITE));
        placeNewPiece('h',1,new Rook(board,Color.WHITE));
        placeNewPiece('d',1,new King(board,Color.WHITE));

        // black
        placeNewPiece('a',8,new Rook(board,Color.BLACK));
        placeNewPiece('h',8,new Rook(board,Color.BLACK));
        placeNewPiece('d',8,new King(board,Color.BLACK));
    }

    private ChessPiece king(Color color) {
        List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).toList();
        for (Piece piece : list) {
            if (piece instanceof King) {
                return (ChessPiece)piece;
            }
        }
        throw new IllegalStateException("There is no " + color + " king on the board");
    }

    private Piece makeMove(Position sourcePosition,Position targetPosition) {
        ChessPiece currentSource = (ChessPiece)board.removePiece(sourcePosition);
        currentSource.increaseMoveCount();
        Piece currentTarget = board.removePiece(targetPosition);
        board.placePiece(currentSource,targetPosition);

        if (currentTarget != null) {
            piecesOnTheBoard.remove(currentTarget);
            capturedPieces.add(currentSource);
        }
        return currentTarget;
    }

    private void nextTurn() {
        currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
        turn++;
    }

    private Color opponent(Color color) {
        return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    private void placeNewPiece(char colum,int row,ChessPiece piece) {
        board.placePiece(piece,new  ChessPosition(colum, row).toPosition());
        piecesOnTheBoard.add(piece);
    }

    private boolean testCheck(Color color) {
        Position kingPosition = king(color).getChessPosition().toPosition();
        List<Piece> opponentPieceList = piecesOnTheBoard
                .stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).toList();
        for (Piece currP : opponentPieceList) {
            boolean[][] mat = currP.possibleMoves();
            if (mat[kingPosition.getRow()][kingPosition.getColum()]) {
                return true;
            }
        }
        return false;
    }

    private boolean testCheckMate(Color color) {
        if (!testCheck(color)) return false;
        List<Piece> list = piecesOnTheBoard
                .stream().filter(x -> ((ChessPiece)x).getColor() == color).toList();
        for (Piece piece : list) {
            boolean[][] mat = piece.possibleMoves();
            for (int i = 0; i < board.getRows(); i++) {
                for (int j = 0; j < board.getColumns(); j++) {
                    if (mat[i][j]) {
                        Position source = ((ChessPiece)piece).getChessPosition().toPosition();
                        Position target = new Position(i, j);
                        Piece capturedPiece = makeMove(source,target);
                        boolean testCheck = testCheck(color);
                        undoMove(source, target, capturedPiece);
                        if (!testCheck) {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    private void undoMove(Position source,Position targetPosition,Piece captured) {
        ChessPiece currentTarget = (ChessPiece)board.removePiece(targetPosition);
        currentTarget.decreaseMoveCount();
        board.placePiece(currentTarget,source);

        if (captured != null) {
            board.placePiece(captured,targetPosition);
            piecesOnTheBoard.add(captured);
            capturedPieces.remove(captured);
        }
    }

    private void validateSourcePosition(Position position) {
        if (!board.thereIsAPiece(position)) throw new ChessException("There's no piece on source position");
        if (currentPlayer != ((ChessPiece)board.piece(position)).getColor()) throw new ChessException("Current player isn't allowed to move that piece");
        if (!board.piece(position).isThereAnyPossibleMove()) throw new ChessException("There is no possible moves for the chosen piece");
    }

    private void validateTargetPosition(Position source, Position target) {
        if (!board.piece(source).possibleMove(target)) throw new ChessException("The chosen piece can't move to target position");
    }
}
