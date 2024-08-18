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
        Position currPosition = new Position(0,0);

        // above
        currPosition.setValues(position.getRow() - 1,position.getColum());
        while (getBoard().positionExists(currPosition) && !getBoard().thereIsAPiece(currPosition)) {
            mat[currPosition.getRow()][currPosition.getColum()] = true;
            currPosition.setRow(position.getRow() - 1);
        }
        if (getBoard().positionExists(currPosition) && isThereOpponentPiece(currPosition)) {
            mat[currPosition.getRow()][currPosition.getColum()] = true;
        }

        // below
        currPosition.setValues(position.getRow() + 1,position.getColum());
        while (getBoard().positionExists(currPosition) && !getBoard().thereIsAPiece(currPosition)) {
            mat[currPosition.getRow()][currPosition.getColum()] = true;
            currPosition.setRow(position.getRow() + 1);
        }
        if (getBoard().positionExists(currPosition) && isThereOpponentPiece(currPosition)) {
            mat[currPosition.getRow()][currPosition.getColum()] = true;
        }

        // left
        currPosition.setValues(position.getRow(),position.getColum() -1);
        while (getBoard().positionExists(currPosition) && !getBoard().thereIsAPiece(currPosition)) {
            mat[currPosition.getRow()][currPosition.getColum()] = true;
            currPosition.setColum(position.getColum() -1);
        }
        if (getBoard().positionExists(currPosition) && isThereOpponentPiece(currPosition)) {
            mat[currPosition.getRow()][currPosition.getColum()] = true;
        }

        // right
        currPosition.setValues(position.getRow(),position.getColum() + 1);
        while (getBoard().positionExists(currPosition) && !getBoard().thereIsAPiece(currPosition)) {
            mat[currPosition.getRow()][currPosition.getColum()] = true;
            currPosition.setColum(position.getColum() + 1);
        }
        if (getBoard().positionExists(currPosition) && isThereOpponentPiece(currPosition)) {
            mat[currPosition.getRow()][currPosition.getColum()] = true;
        }
        return mat;
    }
}
