package chess;

import boardgame.Position;
import chess.exceptions.ChessException;

public class ChessPosition {
    private char colum;
    private int row;

    public ChessPosition(char colum, int row) {
        if (colum < 'a' || colum > 'h' || row < 1 || row > 8) throw new ChessException("Error instantiating position: Invalid values");
        this.colum = colum;
        this.row = row;
    }

    public char getColum() {
        return colum;
    }

    public int getRow() {
        return row;
    }

    protected Position toPosition() {
        return new Position(8 - row,colum - 'a');
    }

    protected static ChessPosition fromPosition(Position position) {
        return new ChessPosition((char)( 'a' + position.getColum()), 8 - position.getRow());
    }

    @Override
    public String toString() {
        return "" + colum + row;
    }
}
