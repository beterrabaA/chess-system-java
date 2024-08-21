package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Knight extends ChessPiece {
    public Knight(Board board, Color color) {
        super(board, color);
    }

    private boolean canMove(Position position) {
        ChessPiece piece = (ChessPiece)getBoard().piece(position);
        return piece == null || piece.getColor() != getColor();
    }

    @Override
    public String toString() {
        return "N";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
        Position knightPosition = new Position(0, 0);

        knightPosition.setValues(position.getRow() - 1, position.getColum() - 2);
        if (getBoard().positionExists(knightPosition) && canMove(knightPosition)) {
            mat[knightPosition.getRow()][knightPosition.getColum()] = true;
        }

        knightPosition.setValues(position.getRow() - 2, position.getColum() - 1);
        if (getBoard().positionExists(knightPosition) && canMove(knightPosition)) {
            mat[knightPosition.getRow()][knightPosition.getColum()] = true;
        }

        knightPosition.setValues(position.getRow() - 2, position.getColum() + 1);
        if (getBoard().positionExists(knightPosition) && canMove(knightPosition)) {
            mat[knightPosition.getRow()][knightPosition.getColum()] = true;
        }

        knightPosition.setValues(position.getRow() - 1, position.getColum() + 2);
        if (getBoard().positionExists(knightPosition) && canMove(knightPosition)) {
            mat[knightPosition.getRow()][knightPosition.getColum()] = true;
        }

        knightPosition.setValues(position.getRow() + 1, position.getColum() + 2);
        if (getBoard().positionExists(knightPosition) && canMove(knightPosition)) {
            mat[knightPosition.getRow()][knightPosition.getColum()] = true;
        }

        knightPosition.setValues(position.getRow() + 2, position.getColum() + 1);
        if (getBoard().positionExists(knightPosition) && canMove(knightPosition)) {
            mat[knightPosition.getRow()][knightPosition.getColum()] = true;
        }

        knightPosition.setValues(position.getRow() + 2, position.getColum() - 1);
        if (getBoard().positionExists(knightPosition) && canMove(knightPosition)) {
            mat[knightPosition.getRow()][knightPosition.getColum()] = true;
        }

        knightPosition.setValues(position.getRow() + 1, position.getColum() - 2);
        if (getBoard().positionExists(knightPosition) && canMove(knightPosition)) {
            mat[knightPosition.getRow()][knightPosition.getColum()] = true;
        }

        return mat;
    }
}
