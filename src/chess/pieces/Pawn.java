package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {

    private ChessMatch match;

    public Pawn(Board board, Color color,ChessMatch match) {
        super(board, color);
        this.match = match;
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

            // #specialmove en passant white
            if (position.getRow() == 3) {
                Position left = new Position(position.getRow(), position.getColum() - 1);
                if (getBoard().positionExists(left) && isThereOpponentPiece(left) && getBoard().piece(left) == match.getEnPassantVulnerable()) {
                    mat[left.getRow() - 1][left.getColum()] = true;
                }
                Position right = new Position(position.getRow(), position.getColum() + 1);
                if (getBoard().positionExists(right) && isThereOpponentPiece(right) && getBoard().piece(right) == match.getEnPassantVulnerable()) {
                    mat[right.getRow() - 1][right.getColum()] = true;
                }
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

            // #specialmove en passant black
            if (position.getRow() == 4) {
                Position left = new Position(position.getRow(), position.getColum() - 1);
                if (getBoard().positionExists(left) && isThereOpponentPiece(left) && getBoard().piece(left) == match.getEnPassantVulnerable()) {
                    mat[left.getRow() + 1][left.getColum()] = true;
                }
                Position right = new Position(position.getRow(), position.getColum() + 1);
                if (getBoard().positionExists(right) && isThereOpponentPiece(right) && getBoard().piece(right) == match.getEnPassantVulnerable()) {
                    mat[right.getRow() + 1][right.getColum()] = true;
                }
            }

        }

            return mat;
    }
}
