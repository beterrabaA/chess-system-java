package boardgame;

public class Position {
    private int row;
    private int colum;

    public Position(int row, int colum) {
        this.row = row;
        this.colum = colum;
    }

    public int getRow() {
        return row;
    }

    public int getColum() {
        return colum;
    }

    @Override
    public String toString() {
        return row + ", " + colum;
    }
}
