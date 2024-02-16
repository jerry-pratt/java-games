package rocks.jerryPratt.javaGames.tdGammon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class RandomBackgammonPlayer implements BackgammonPlayer
{
	private final Random random = new Random();
	private final BackgammonColor color;
	
	public RandomBackgammonPlayer(BackgammonColor color)
	{
		this.color = color;
	}
	
	@Override
	public BackgammonMove chooseAMove(DiceRoll diceRoll, BackgammonBoard board) 
	{
//		System.out.println("Choosing a move for " + color);
		if (board.getWhoseTurn() != color) throw new RuntimeException("It's not " + color + " turn!");
		ArrayList<BackgammonMove> movesToPack = new ArrayList<>();
		board.getAllPossibleMoves(diceRoll, movesToPack);
		
		Collections.shuffle(movesToPack, random);
		
		BackgammonMove moveToReturn = null;
		
		if (!movesToPack.isEmpty()) moveToReturn = movesToPack.get(0);
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
