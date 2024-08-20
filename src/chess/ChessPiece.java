package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece {
    private final Color color;
    private int moveCount;

    public ChessPiece(Board board, Color color) {
        super(board);
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public ChessPosition getChessPosition() {
        return ChessPosition.fromPosition(position);
    }

    protected void decreaseMoveCount() {
        moveCount--;
    }

    protected void increaseMoveCount() {
        moveCount++;
    }

    protected boolean isThereOpponentPiece(Position position) {
        ChessPiece currentPiece = (ChessPiece)getBoard().piece(position);
        return currentPiece != null && currentPiece.getColor() != color;
    }
}
