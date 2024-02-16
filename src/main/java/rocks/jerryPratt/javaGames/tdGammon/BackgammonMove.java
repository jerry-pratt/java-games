package rocks.jerryPratt.javaGames.tdGammon;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.StringTokenizer;

public class BackgammonMove 
{
	private final BackgammonColor color;
	private DiceRoll diceRoll;
	private LinkedList<BackgammonIndividualPieceMove> individualPieceMoves = new LinkedList<BackgammonIndividualPieceMove>();
	
	int numberOfPieceMoves = 0;
	
	public BackgammonMove(BackgammonColor color, DiceRoll diceRoll) 
	{
		this.diceRoll = new DiceRoll(diceRoll);
		this.color = color;
	}
	
	public BackgammonMove(BackgammonMove moveToCopy) 
	{
		this.color = moveToCopy.color;
		this.numberOfPieceMoves = moveToCopy.numberOfPieceMoves;
		this.diceRoll = new DiceRoll(moveToCopy.diceRoll);
		
		for (BackgammonIndividualPieceMove fromPositionToPositionToCopy : moveToCopy.individualPieceMoves)
		{
		   BackgammonIndividualPieceMove individualMove = new BackgammonIndividualPieceMove(fromPositionToPositionToCopy);
			this.individualPieceMoves.add(individualMove);
		}
	}

	public BackgammonMove(BackgammonColor color, String moveString) 
	{
		this.color = color;
		
		int indexOfColon = moveString.indexOf(":");

//		System.out.println(indexOfColon);

		String rollDigits = moveString.substring(0, indexOfColon);
		DiceRoll diceRoll = new DiceRoll(rollDigits);
		this.diceRoll = diceRoll;
		
		moveString = moveString.substring(indexOfColon + 1);

		addMovesFromString(moveString);
	}

	public BackgammonMove(BackgammonColor color, DiceRoll diceRoll, String moveString) 
	{
		this.color = color;
		this.diceRoll = diceRoll;
		
		addMovesFromString(moveString);
	}
	
	private void addMovesFromString(String moveString)
	{
		StringTokenizer tokenizer = new StringTokenizer(moveString, " /");

		while(tokenizer.hasMoreTokens())
		{
			int fromPosition = Integer.parseInt(tokenizer.nextToken());
			int toPosition = Integer.parseInt(tokenizer.nextToken());

			addMove(fromPosition, toPosition);
		}
	}

	public void addMove(int fromPosition, int toPosition) 
	{
		if (fromPosition < 1) throw new RuntimeException("fromPosition must be at least 1");
		if (fromPosition > 25) throw new RuntimeException("fromPosition must be no more than 25");
		
		if (toPosition < 0) throw new RuntimeException("toPosition must be at least 0");
		if (toPosition > 25) throw new RuntimeException("toPosition must be no more than 25");

		int numberOfSpaces = fromPosition - toPosition;
		if ((toPosition != 0) && (numberOfSpaces != diceRoll.getDiceOne()) && (numberOfSpaces != diceRoll.getDiceTwo()))
		   throw new RuntimeException("numberOfSpaces doesn't match available dice roll");
		
		if (numberOfSpaces < 1) throw new RuntimeException("numberOfSpaces must be at least 1");
		if (numberOfSpaces > 6) throw new RuntimeException("numberOfSpaces must be no more than 6");
		
		BackgammonIndividualPieceMove moveToAdd = new BackgammonIndividualPieceMove(fromPosition, toPosition);
		individualPieceMoves.add(moveToAdd);
				
		numberOfPieceMoves++;
		
		if (numberOfPieceMoves > 2)
		{
			int numberOfSpacesToCheck = -1;
			
			for (BackgammonIndividualPieceMove move : individualPieceMoves)
			{
				int fromPositionToCheck = move.getFromPosition();
				int toPositionToCheck = move.getToPosition();

				if (toPositionToCheck != 0)
				{
					if (numberOfSpacesToCheck == -1)
					{
						numberOfSpacesToCheck = fromPositionToCheck - toPositionToCheck;
					}
					
					else if (fromPositionToCheck - toPositionToCheck != numberOfSpacesToCheck)
					{
						numberOfPieceMoves--;
						individualPieceMoves.removeLast();
						throw new RuntimeException("If you get doubles, all pieces must move the same number");
					}
				}
			}
		}
	}

	public int getNumberOfPieceMoves() 
	{
		return numberOfPieceMoves;
	}

	public LinkedList<BackgammonIndividualPieceMove> getPieceMoves() 
	{
		return individualPieceMoves;
	}
	
	public BackgammonColor getColor()
	{
		return color;
	}

	public String toString()
	{
		String returnString = color.toString() + ", " + diceRoll.toString() + " ";
		
		for (BackgammonIndividualPieceMove move : individualPieceMoves)
		{
			int fromPosition = move.getFromPosition();
			int toPosition = move.getToPosition();
			
			returnString += " " + fromPosition + "/" + toPosition; 
		}
		
		return returnString;
	}

	public DiceRoll getDiceRoll() 
	{
		return diceRoll;
	}

	public BackgammonMove reverseDiceRoll() 
	{
		BackgammonMove moveToReturn = new BackgammonMove(this);
		moveToReturn.diceRoll = this.diceRoll.reverseOrder();
		
		return moveToReturn;
	}

   public Map<Integer, Integer> getMapFromFinalPositionToNetGain()
   {
      HashMap<Integer, Integer> returnedMap = new HashMap<>();
      
      for (BackgammonIndividualPieceMove individualMove : individualPieceMoves)
      {
         int fromPosition = individualMove.getFromPosition();
         int toPosition = individualMove.getToPosition();
         
         Integer count = returnedMap.get(fromPosition);
         if (count == null) count = 0;
         count--;
         returnedMap.put(fromPosition, count);
         
         count = returnedMap.get(toPosition);
         if (count == null) count = 0;
         count++;
         returnedMap.put(toPosition, count);
      }
      
      return returnedMap;
   }
	
}
