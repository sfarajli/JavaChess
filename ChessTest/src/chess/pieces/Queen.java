package chess.pieces;

import chess.Color;
import chess.board.Board;
import chess.board.BoardData;
import chess.board.Move;
import chess.board.Square;

import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece
{
    private final static int[] PRESET_VECTOR_COORDS={-9,-8,-7,-1,1,7,8,9};
    public Queen(final int pieceCoord, final Color pieceColor)
    {
        super(PieceType.QUEEN,pieceCoord, pieceColor);
    }

    @Override
    public List<Move> getLegalMoves(final Board board)
    {
        final List<Move> legalMoves=new ArrayList<>();

        for(final int currentOffset:PRESET_VECTOR_COORDS)
        {
            int possibleDestinationCoord=this.pieceCoord;
            while(BoardData.isValidSquareCoord(possibleDestinationCoord))
            {
                if(isFirstCol(possibleDestinationCoord,currentOffset)||isEighthCol(possibleDestinationCoord,currentOffset))
                {
                    break;
                }
                possibleDestinationCoord+=currentOffset;
                if (BoardData.isValidSquareCoord(possibleDestinationCoord))
                {
                    final Square possibleDestinationSquare = board.getSquare(possibleDestinationCoord);
                    if (!possibleDestinationSquare.isOccupied())
                    {
                        legalMoves.add(new Move.MajorPieceRegularMove(board,this,possibleDestinationCoord));
                    }
                    else
                    {
                        final Piece pieceAtDestination = possibleDestinationSquare.getPiece();
                        final Color pieceColor = pieceAtDestination.getPieceColor();
                        if (this.pieceColor != pieceColor)
                        {
                            legalMoves.add(new Move.AttackMove(board,this,pieceAtDestination,possibleDestinationCoord));
                        }
                        break;
                    }
                }
            }
        }

        return legalMoves;
    }
    private static boolean isFirstCol(final int currentCoord,final int offset)
    {
        return BoardData.FIRST_COL[currentCoord] && ((offset==-9)||(offset==7)||(offset==-1));
    }
    private static boolean isEighthCol(final int currentCoord,final int offset)
    {
        return BoardData.EIGHTH_COL[currentCoord] && ((offset==9)||(offset==-7) ||(offset==1));
    }
    @Override
    public Piece movePiece(final Move move)
    {
        return new Queen(move.getDestinationCoord(), move.getMovedPiece().getPieceColor());
    }

    @Override
    public String toString()
    {
        return PieceType.QUEEN.toString();
    }
}
