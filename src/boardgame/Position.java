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

    public void setRow(int row) {
        this.row = row;
    }

    public int getColum() {
        return colum;
    }

    public void setColumn(int column) {
        this.colum = column;
    }

    public void setValues(int row, int column) {
        this.row = row;
        this.colum = column;
    }

    @Override
    public String toString() {
        return row + ", " + colum;
    }
}
