package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Queen extends ChessPiece {
    public Queen(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "Q";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
        Position queenPosition = new Position(0, 0);

        // above
        queenPosition.setValues(position.getRow() - 1, position.getColum());
        while (getBoard().positionExists(queenPosition) && !getBoard().thereIsAPiece(queenPosition)) {
            mat[queenPosition.getRow()][queenPosition.getColum()] = true;
            queenPosition.setRow(queenPosition.getRow() - 1);
        }
        if (getBoard().positionExists(queenPosition) && isThereOpponentPiece(queenPosition)) {
            mat[queenPosition.getRow()][queenPosition.getColum()] = true;
        }

        // left
        queenPosition.setValues(position.getRow(), position.getColum() - 1);
        while (getBoard().positionExists(queenPosition) && !getBoard().thereIsAPiece(queenPosition)) {
            mat[queenPosition.getRow()][queenPosition.getColum()] = true;
            queenPosition.setColumn(queenPosition.getColum() - 1);
        }
        if (getBoard().positionExists(queenPosition) && isThereOpponentPiece(queenPosition)) {
            mat[queenPosition.getRow()][queenPosition.getColum()] = true;
        }

        // right
        queenPosition.setValues(position.getRow(), position.getColum() + 1);
        while (getBoard().positionExists(queenPosition) && !getBoard().thereIsAPiece(queenPosition)) {
            mat[queenPosition.getRow()][queenPosition.getColum()] = true;
            queenPosition.setColumn(queenPosition.getColum() + 1);
        }
        if (getBoard().positionExists(queenPosition) && isThereOpponentPiece(queenPosition)) {
            mat[queenPosition.getRow()][queenPosition.getColum()] = true;
        }

        // below
        queenPosition.setValues(position.getRow() + 1, position.getColum());
        while (getBoard().positionExists(queenPosition) && !getBoard().thereIsAPiece(queenPosition)) {
            mat[queenPosition.getRow()][queenPosition.getColum()] = true;
            queenPosition.setRow(queenPosition.getRow() + 1);
        }
        if (getBoard().positionExists(queenPosition) && isThereOpponentPiece(queenPosition)) {
            mat[queenPosition.getRow()][queenPosition.getColum()] = true;
        }

        // nw
        queenPosition.setValues(position.getRow() - 1, position.getColum() - 1);
        while (getBoard().positionExists(queenPosition) && !getBoard().thereIsAPiece(queenPosition)) {
            mat[queenPosition.getRow()][queenPosition.getColum()] = true;
            queenPosition.setValues(queenPosition.getRow() - 1, queenPosition.getColum() - 1);
        }
        if (getBoard().positionExists(queenPosition) && isThereOpponentPiece(queenPosition)) {
            mat[queenPosition.getRow()][queenPosition.getColum()] = true;
        }

        // ne
        queenPosition.setValues(position.getRow() - 1, position.getColum() + 1);
        while (getBoard().positionExists(queenPosition) && !getBoard().thereIsAPiece(queenPosition)) {
            mat[queenPosition.getRow()][queenPosition.getColum()] = true;
            queenPosition.setValues(queenPosition.getRow() - 1, queenPosition.getColum() + 1);
        }
        if (getBoard().positionExists(queenPosition) && isThereOpponentPiece(queenPosition)) {
            mat[queenPosition.getRow()][queenPosition.getColum()] = true;
        }

        // sw
        queenPosition.setValues(position.getRow() + 1, position.getColum() + 1);
        while (getBoard().positionExists(queenPosition) && !getBoard().thereIsAPiece(queenPosition)) {
            mat[queenPosition.getRow()][queenPosition.getColum()] = true;
            queenPosition.setValues(queenPosition.getRow() + 1, queenPosition.getColum() + 1);
        }
        if (getBoard().positionExists(queenPosition) && isThereOpponentPiece(queenPosition)) {
            mat[queenPosition.getRow()][queenPosition.getColum()] = true;
        }

        // se
        queenPosition.setValues(position.getRow() + 1, position.getColum() - 1);
        while (getBoard().positionExists(queenPosition) && !getBoard().thereIsAPiece(queenPosition)) {
            mat[queenPosition.getRow()][queenPosition.getColum()] = true;
            queenPosition.setValues(queenPosition.getRow() + 1, queenPosition.getColum() - 1);
        }
        if (getBoard().positionExists(queenPosition) && isThereOpponentPiece(queenPosition)) {
            mat[queenPosition.getRow()][queenPosition.getColum()] = true;
        }

        return mat;
    }
}
