package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {

    private ChessMatch match;

    public King(Board board, Color color, ChessMatch match) {
        super(board, color);
        this.match = match;
    }

    private boolean canMove(Position position) {
        ChessPiece currentPosition = (ChessPiece)getBoard().piece(position);
        return currentPosition == null || currentPosition.getColor() != getColor();
    }

    private boolean testRookCastling(Position position) {
        ChessPiece rookPiece = (ChessPiece)getBoard().piece(position);
        return rookPiece instanceof Rook && rookPiece.getColor() == getColor() && rookPiece.getMoveCount() == 0;
    }

    @Override
    public String toString() {
        return "K";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
        Position kingPosition = new Position(0, 0);

        // above
        kingPosition.setValues(position.getRow() - 1, position.getColum());
        if (getBoard().positionExists(kingPosition) && canMove(kingPosition)) {
            mat[kingPosition.getRow()][kingPosition.getColum()] = true;
        }

        // below
        kingPosition.setValues(position.getRow() + 1, position.getColum());
        if (getBoard().positionExists(kingPosition) && canMove(kingPosition)) {
            mat[kingPosition.getRow()][kingPosition.getColum()] = true;
        }

        // left
        kingPosition.setValues(position.getRow(), position.getColum() - 1);
        if (getBoard().positionExists(kingPosition) && canMove(kingPosition)) {
            mat[kingPosition.getRow()][kingPosition.getColum()] = true;
        }

        // right
        kingPosition.setValues(position.getRow(), position.getColum() + 1);
        if (getBoard().positionExists(kingPosition) && canMove(kingPosition)) {
            mat[kingPosition.getRow()][kingPosition.getColum()] = true;
        }

        // nw
        kingPosition.setValues(position.getRow() - 1, position.getColum() - 1);
        if (getBoard().positionExists(kingPosition) && canMove(kingPosition)) {
            mat[kingPosition.getRow()][kingPosition.getColum()] = true;
        }

        // ne
        kingPosition.setValues(position.getRow() - 1, position.getColum() + 1);
        if (getBoard().positionExists(kingPosition) && canMove(kingPosition)) {
            mat[kingPosition.getRow()][kingPosition.getColum()] = true;
        }

        // sw
        kingPosition.setValues(position.getRow() + 1, position.getColum() - 1);
        if (getBoard().positionExists(kingPosition) && canMove(kingPosition)) {
            mat[kingPosition.getRow()][kingPosition.getColum()] = true;
        }

        // se
        kingPosition.setValues(position.getRow() + 1, position.getColum() + 1);
        if (getBoard().positionExists(kingPosition) && canMove(kingPosition)) {
            mat[kingPosition.getRow()][kingPosition.getColum()] = true;
        }

        // #specialmove castling
        if (getMoveCount() == 0 && !match.isCheck()) {
            // #specialmove castling king side rook
            Position rookPosition1 = new Position(position.getRow(), position.getColum() - 3);
            if (testRookCastling(rookPosition1)) {
                Position position1 = new Position(position.getRow(), position.getColum() - 1);
                Position position2 = new Position(position.getRow(), position.getColum() - 2);
                if (getBoard().piece(position1) == null && getBoard().piece(position2) == null) {
                    mat[position.getRow()][position.getColum() - 2] = true;
                }
            }
            // #specialmove castling queen side rook
            Position rookPosition2 = new Position(position.getRow(), position.getColum() + 4);
            if (testRookCastling(rookPosition2)) {
                Position position1 = new Position(position.getRow(), position.getColum() + 1);
                Position position2 = new Position(position.getRow(), position.getColum() + 2);
                Position position3 = new Position(position.getRow(), position.getColum() + 3);
                if (getBoard().piece(position1) == null && getBoard().piece(position2) == null && getBoard().piece(position3) == null) {
                    mat[position.getRow()][position.getColum() + 2] = true;
                }
            }
        }

        return mat;
    }
}
