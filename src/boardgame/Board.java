package boardgame;

import boardgame.exceptions.BoardException;

public class Board {
    private final int rows;
    private final int columns;
    private Piece[][] pieces;

    public Board(int rows, int columns) {
        if (rows < 1 || columns < 1) throw new BoardException("Error creating board: there must be at least 1 row and 1 colum");
        this.rows = rows;
        this.columns = columns;
        pieces = new Piece[rows][columns];
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public Piece piece(int row,int colum) {
        if (!positionExists(row,colum)) throw new BoardException("Error accessing position on the board:Position not on the board");
        return pieces[row][colum];
    }

    public Piece piece(Position position) {
        if (!positionExists(position)) throw new BoardException("Error accessing position on the board:Position not on the board");
        return pieces[position.getRow()][position.getColum()];
    }

    public void placePiece(Piece piece,Position position) {
        if (thereIsAPiece(position)) throw new BoardException("Error placing a piece:There's already a piece placed at: " + position);
        pieces[position.getRow()][position.getColum()] = piece;
        piece.position = position;
    }

    private boolean positionExists(int row,int colum) {
        return row >= 0 && row < rows && colum >= 0 && colum < columns;
    }

    public boolean positionExists(Position position) {
        return positionExists(position.getRow(),position.getColum());
    }

    public boolean thereIsAPiece(Position position) {
        if (!positionExists(position)) throw new BoardException("Error accessing position on the board:Position not on the board");
        return piece(position) != null;
    }
}
