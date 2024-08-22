package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {

    public Pawn(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "P";
    }
    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
        Position pawnPosition = new Position(0, 0);

        if (getColor() == Color.WHITE) {
            pawnPosition.setValues(position.getRow() - 1, position.getColum());
            if (getBoard().positionExists(pawnPosition) && !getBoard().thereIsAPiece(pawnPosition)) {
                mat[pawnPosition.getRow()][pawnPosition.getColum()] = true;
            }
            pawnPosition.setValues(position.getRow() - 2, position.getColum());
            Position pawnPosition2 = new Position(position.getRow() - 1, position.getColum());
            if (getBoard().positionExists(pawnPosition) && !getBoard().thereIsAPiece(pawnPosition) && getBoard().positionExists(pawnPosition2) && !getBoard().thereIsAPiece(pawnPosition2) && getMoveCount() == 0) {
                mat[pawnPosition.getRow()][pawnPosition.getColum()] = true;
            }
            pawnPosition.setValues(position.getRow() - 1, position.getColum() - 1);
            if (getBoard().positionExists(pawnPosition) && isThereOpponentPiece(pawnPosition)) {
                mat[pawnPosition.getRow()][pawnPosition.getColum()] = true;
            }
            pawnPosition.setValues(position.getRow() - 1, position.getColum() + 1);
            if (getBoard().positionExists(pawnPosition) && isThereOpponentPiece(pawnPosition)) {
                mat[pawnPosition.getRow()][pawnPosition.getColum()] = true;
            }

        }
        else {
            pawnPosition.setValues(position.getRow() + 1, position.getColum());
            if (getBoard().positionExists(pawnPosition) && !getBoard().thereIsAPiece(pawnPosition)) {
                mat[pawnPosition.getRow()][pawnPosition.getColum()] = true;
            }
            pawnPosition.setValues(position.getRow() + 2, position.getColum());
            Position pawnPosition2 = new Position(position.getRow() + 1, position.getColum());
            if (getBoard().positionExists(pawnPosition) && !getBoard().thereIsAPiece(pawnPosition) && getBoard().positionExists(pawnPosition2) && !getBoard().thereIsAPiece(pawnPosition2) && getMoveCount() == 0) {
                mat[pawnPosition.getRow()][pawnPosition.getColum()] = true;
            }
            pawnPosition.setValues(position.getRow() + 1, position.getColum() - 1);
            if (getBoard().positionExists(pawnPosition) && isThereOpponentPiece(pawnPosition)) {
                mat[pawnPosition.getRow()][pawnPosition.getColum()] = true;
            }
            pawnPosition.setValues(position.getRow() + 1, position.getColum() + 1);
            if (getBoard().positionExists(pawnPosition) && isThereOpponentPiece(pawnPosition)) {
                mat[pawnPosition.getRow()][pawnPosition.getColum()] = true;
            }

        }

            return mat;
    }
}
