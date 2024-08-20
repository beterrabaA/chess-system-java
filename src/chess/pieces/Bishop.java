package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Bishop extends ChessPiece {

    public Bishop(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "B";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
        Position bishopPosition = new Position(0, 0);

        // nw
        bishopPosition.setValues(position.getRow() - 1, position.getColum() - 1);
        while (getBoard().positionExists(bishopPosition) && !getBoard().thereIsAPiece(bishopPosition)) {
            mat[bishopPosition.getRow()][bishopPosition.getColum()] = true;
            bishopPosition.setValues(bishopPosition.getRow() - 1, bishopPosition.getColum() - 1);
        }
        if (getBoard().positionExists(bishopPosition) && isThereOpponentPiece(bishopPosition)) {
            mat[bishopPosition.getRow()][bishopPosition.getColum()] = true;
        }

        // ne
        bishopPosition.setValues(position.getRow() - 1, position.getColum() + 1);
        while (getBoard().positionExists(bishopPosition) && !getBoard().thereIsAPiece(bishopPosition)) {
            mat[bishopPosition.getRow()][bishopPosition.getColum()] = true;
            bishopPosition.setValues(bishopPosition.getRow() - 1, bishopPosition.getColum() + 1);
        }
        if (getBoard().positionExists(bishopPosition) && isThereOpponentPiece(bishopPosition)) {
            mat[bishopPosition.getRow()][bishopPosition.getColum()] = true;
        }

        // sw
        bishopPosition.setValues(position.getRow() + 1, position.getColum() + 1);
        while (getBoard().positionExists(bishopPosition) && !getBoard().thereIsAPiece(bishopPosition)) {
            mat[bishopPosition.getRow()][bishopPosition.getColum()] = true;
            bishopPosition.setValues(bishopPosition.getRow() + 1, bishopPosition.getColum() + 1);
        }
        if (getBoard().positionExists(bishopPosition) && isThereOpponentPiece(bishopPosition)) {
            mat[bishopPosition.getRow()][bishopPosition.getColum()] = true;
        }

        // se
        bishopPosition.setValues(position.getRow() + 1, position.getColum() - 1);
        while (getBoard().positionExists(bishopPosition) && !getBoard().thereIsAPiece(bishopPosition)) {
            mat[bishopPosition.getRow()][bishopPosition.getColum()] = true;
            bishopPosition.setValues(bishopPosition.getRow() + 1, bishopPosition.getColum() - 1);
        }
        if (getBoard().positionExists(bishopPosition) && isThereOpponentPiece(bishopPosition)) {
            mat[bishopPosition.getRow()][bishopPosition.getColum()] = true;
        }
        return mat;
    }
}
