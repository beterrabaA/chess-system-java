package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.exceptions.ChessException;
import chess.pieces.*;

import java.util.ArrayList;
import java.util.List;

public class ChessMatch {
    private int turn;
    private boolean isCheck;
    private boolean isCheckMate;
    private Color currentPlayer;
    private ChessPiece enPassantVulnerable;
    private Board board;
    private List<Piece> piecesOnTheBoard = new ArrayList<>();
    private List<Piece> capturedPieces = new ArrayList<>();
    private ChessPiece promoted;

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

    public ChessPiece getEnPassantVulnerable() {
        return enPassantVulnerable;
    }

    public ChessPiece getPromoted(){
        return promoted;
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

        ChessPiece movedPiece = (ChessPiece)board.piece(target);

        // #specialmove promotion
        promoted = null;
        if (movedPiece instanceof Pawn) {
            if ((movedPiece.getColor() == Color.WHITE && target.getRow() == 0) || (movedPiece.getColor() == Color.BLACK && target.getRow() == 7)) {
                promoted = (ChessPiece)board.piece(target);
                promoted = replacePromotedPiece("Q");
            }
        }

        isCheck = testCheck(opponent(currentPlayer));

        if(testCheckMate(opponent(currentPlayer))) {
            isCheckMate = true;
        } else {
            nextTurn();
        }

        // special move en passant
        if (movedPiece instanceof Pawn && (target.getRow() == source.getRow() - 2 || target.getRow() == source.getRow() + 2)) {
            enPassantVulnerable = movedPiece;
        }
        else {
            enPassantVulnerable = null;
        }

        return (ChessPiece) capturedPiece;
    }

    public ChessPiece replacePromotedPiece(String type) {
        if (promoted == null) throw new IllegalStateException("There's no piece to be promoted");
        if (!type.equals("B") && !type.equals("N") && !type.equals("R") & !type.equals("Q")) {
            return promoted;
        }

        Position promotedPiecePosition = promoted.getChessPosition().toPosition();
        Piece piece = board.removePiece(promotedPiecePosition);
        piecesOnTheBoard.remove(piece);

        ChessPiece newPiece = newPiece(type,promoted.getColor());
        board.placePiece(newPiece,promotedPiecePosition);
        piecesOnTheBoard.add(newPiece);

        return newPiece;
    }

    private void initialSetup() {
        // white
        placeNewPiece('a',1,new Rook(board,Color.WHITE));
        placeNewPiece('b',1,new Knight(board,Color.WHITE));
        placeNewPiece('c',1,new Bishop(board,Color.WHITE));
        placeNewPiece('d',1,new King(board,Color.WHITE,this));
        placeNewPiece('e',1,new Queen(board,Color.WHITE));
        placeNewPiece('f',1,new Bishop(board,Color.WHITE));
        placeNewPiece('g',1,new Knight(board,Color.WHITE));
        placeNewPiece('h',1,new Rook(board,Color.WHITE));
        // white pawn
        placeNewPiece('a',2,new Pawn(board,Color.WHITE,this));
        placeNewPiece('b',2,new Pawn(board,Color.WHITE,this));
        placeNewPiece('c',2,new Pawn(board,Color.WHITE,this));
        placeNewPiece('d',2,new Pawn(board,Color.WHITE,this));
        placeNewPiece('e',2,new Pawn(board,Color.WHITE,this));
        placeNewPiece('f',2,new Pawn(board,Color.WHITE,this));
        placeNewPiece('g',2,new Pawn(board,Color.WHITE,this));
        placeNewPiece('h',2,new Pawn(board,Color.WHITE,this));

        // black
        placeNewPiece('a',8,new Rook(board,Color.BLACK));
        placeNewPiece('b',8,new Knight(board,Color.BLACK));
        placeNewPiece('c',8,new Bishop(board,Color.BLACK));
        placeNewPiece('d',8,new King(board,Color.BLACK,this));
        placeNewPiece('e',8,new Queen(board,Color.BLACK));
        placeNewPiece('f',8,new Bishop(board,Color.BLACK));
        placeNewPiece('g',8,new Knight(board,Color.BLACK));
        placeNewPiece('h',8,new Rook(board,Color.BLACK));
        // black pawn
        placeNewPiece('a',7,new Pawn(board,Color.BLACK,this));
        placeNewPiece('b',7,new Pawn(board,Color.BLACK,this));
        placeNewPiece('c',7,new Pawn(board,Color.BLACK,this));
        placeNewPiece('d',7,new Pawn(board,Color.BLACK,this));
        placeNewPiece('e',7,new Pawn(board,Color.BLACK,this));
        placeNewPiece('f',7,new Pawn(board,Color.BLACK,this));
        placeNewPiece('g',7,new Pawn(board,Color.BLACK,this));
        placeNewPiece('h',7,new Pawn(board,Color.BLACK,this));
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

        // #specialmove castling king side rook
        if (currentSource instanceof King && targetPosition.getColum() == sourcePosition.getColum() - 2) {
            Position rookSourceP = new Position(sourcePosition.getRow(), sourcePosition.getColum() - 3);
            Position rookTargetP = new Position(sourcePosition.getRow(), sourcePosition.getColum() - 1);

            ChessPiece rook = (ChessPiece)board.removePiece(rookSourceP);
            board.placePiece(rook, rookTargetP);
            rook.decreaseMoveCount();
        }

        // #specialmove castling queen side rook
        if (currentSource instanceof King && targetPosition.getColum() == sourcePosition.getColum() + 2) {
            Position rookSourceP = new Position(sourcePosition.getRow(), sourcePosition.getColum() + 4);
            Position rookTargetP = new Position(sourcePosition.getRow(), sourcePosition.getColum() + 1);

            ChessPiece rook = (ChessPiece)board.removePiece(rookSourceP);
            board.placePiece(rook, rookTargetP);
            rook.decreaseMoveCount();
        }

        // #specialmove pawn passant
        if (currentSource instanceof Pawn) {
            if (sourcePosition.getColum() != targetPosition.getColum() && currentTarget == null) {
                Position pawnPosition;
                if (currentSource.getColor() == Color.WHITE) {
                    pawnPosition = new Position(targetPosition.getRow() + 1, targetPosition.getColum());
                } else {
                    pawnPosition = new Position(targetPosition.getRow() - 1, targetPosition.getColum());
                }

                currentTarget = board.removePiece(pawnPosition);
            }
        }

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

    private ChessPiece newPiece(String type,Color color) {
        switch (type) {
            case "B" -> {
                return new Bishop(board, color);
            }
            case "N" -> {
                return new Knight(board, color);
            }
            case "Q" -> {
                return new Queen(board, color);
            }default -> {
                return new Rook(board,color);
            }
        }
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

        // #specialmove castling king side rook
        if (currentTarget instanceof King && targetPosition.getColum() == source.getColum() - 2) {
            Position rookSourceP = new Position(source.getRow(), source.getColum() - 3);
            Position rookTargetP = new Position(source.getRow(), source.getColum() - 1);

            ChessPiece rook = (ChessPiece)board.removePiece(rookTargetP);
            board.placePiece(rook, rookSourceP);
            rook.decreaseMoveCount();
        }

        // #specialmove castling queen side rook
        if (currentTarget instanceof King && targetPosition.getColum() == source.getColum() + 2) {
            Position rookSourceP = new Position(source.getRow(), source.getColum() + 4);
            Position rookTargetP = new Position(source.getRow(), source.getColum() + 1);

            ChessPiece rook = (ChessPiece)board.removePiece(rookTargetP);
            board.placePiece(rook, rookSourceP);
            rook.decreaseMoveCount();
        }

        // #specialmove en passant
        if (currentTarget instanceof Pawn) {
            if (source.getColum() != targetPosition.getColum() && captured == enPassantVulnerable) {
                ChessPiece pawn = (ChessPiece)board.removePiece(targetPosition);
                Position pawnPosition;
                if (currentTarget.getColor() == Color.WHITE) {
                    pawnPosition = new Position(3, targetPosition.getColum());
                }
                else {
                    pawnPosition = new Position(4, targetPosition.getColum());
                }
                board.placePiece(pawn, pawnPosition);
            }
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
