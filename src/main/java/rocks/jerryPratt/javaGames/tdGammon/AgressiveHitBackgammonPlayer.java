package rocks.jerryPratt.javaGames.tdGammon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

public class AgressiveHitBackgammonPlayer implements BackgammonPlayer
{
	private final Random random = new Random();
	private final BackgammonColor color;
	
	public AgressiveHitBackgammonPlayer(BackgammonColor color)
	{
		this.color = color;
	}
	
	@Override
	public BackgammonMove chooseAMove(DiceRoll diceRoll, BackgammonBoard board) 
	{
		if (board.getWhoseTurn() != color) throw new RuntimeException("It's not " + color + " turn!");
		ArrayList<BackgammonMove> movesToPack = new ArrayList<>();
		board.getAllPossibleMoves(diceRoll, movesToPack);
		
		Collections.shuffle(movesToPack, random);
		
		BackgammonMove moveToReturn = null;
		
		if (!movesToPack.isEmpty())
		{
			for (BackgammonMove move : movesToPack)
			{
				LinkedList<BackgammonIndividualPieceMove> pieceMoves = move.getPieceMoves();
				for (BackgammonIndividualPieceMove pieceMove : pieceMoves) 
				{
					int toPosition = pieceMove.getToPosition();
					// If you hit the opponent, take the move.
					if (board.getPieces(color.getOppositeColor(), 25-toPosition) == 1) return move;
				}
			}
		}
		
		if (!movesToPack.isEmpty())
		{
			moveToReturn = movesToPack.get(0);
		}
		
		else moveToReturn = new BackgammonMove(color, diceRoll);
		
//		System.out.println(color + " Move " + moveToReturn);
		return moveToReturn;
	}

	public BackgammonColor getColor() 
	{
		return color;
	}

	@Override
	public void startANewGame(BackgammonBoard startingBoard) 
	{		
	}

	@Override
	public void informThatOpponentMadeMove(BackgammonBoard originalBoard, BackgammonMove opponentMove, BackgammonBoard resultantBoard) 
	{		
	}

}
