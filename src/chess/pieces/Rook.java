package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Rook extends ChessPiece {
    public Rook(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "R";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
        Position rookPosition = new Position(0, 0);

// above
        rookPosition.setValues(position.getRow() - 1, position.getColum());
        while (getBoard().positionExists(rookPosition) && !getBoard().thereIsAPiece(rookPosition)) {
            mat[rookPosition.getRow()][rookPosition.getColum()] = true;
            rookPosition.setRow(rookPosition.getRow() - 1);
        }
        if (getBoard().positionExists(rookPosition) && isThereOpponentPiece(rookPosition)) {
            mat[rookPosition.getRow()][rookPosition.getColum()] = true;
        }

        // left
        rookPosition.setValues(position.getRow(), position.getColum() - 1);
        while (getBoard().positionExists(rookPosition) && !getBoard().thereIsAPiece(rookPosition)) {
            mat[rookPosition.getRow()][rookPosition.getColum()] = true;
            rookPosition.setColumn(rookPosition.getColum() - 1);
        }
        if (getBoard().positionExists(rookPosition) && isThereOpponentPiece(rookPosition)) {
            mat[rookPosition.getRow()][rookPosition.getColum()] = true;
        }

        // right
        rookPosition.setValues(position.getRow(), position.getColum() + 1);
        while (getBoard().positionExists(rookPosition) && !getBoard().thereIsAPiece(rookPosition)) {
            mat[rookPosition.getRow()][rookPosition.getColum()] = true;
            rookPosition.setColumn(rookPosition.getColum() + 1);
        }
        if (getBoard().positionExists(rookPosition) && isThereOpponentPiece(rookPosition)) {
            mat[rookPosition.getRow()][rookPosition.getColum()] = true;
        }

        // below
        rookPosition.setValues(position.getRow() + 1, position.getColum());
        while (getBoard().positionExists(rookPosition) && !getBoard().thereIsAPiece(rookPosition)) {
            mat[rookPosition.getRow()][rookPosition.getColum()] = true;
            rookPosition.setRow(rookPosition.getRow() + 1);
        }
        if (getBoard().positionExists(rookPosition) && isThereOpponentPiece(rookPosition)) {
            mat[rookPosition.getRow()][rookPosition.getColum()] = true;
        }
        return mat;
    }
}
