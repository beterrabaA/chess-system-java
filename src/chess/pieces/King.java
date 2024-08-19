package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {

    public King(Board board, Color color) {
        super(board, color);
    }

    private boolean canMove(Position position) {
        ChessPiece currentPosition = (ChessPiece)getBoard().piece(position);
        return currentPosition == null || currentPosition.getColor() != getColor();
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
        return mat;
    }
}
