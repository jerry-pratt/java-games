package rocks.jerryPratt.javaGames.tdGammon;

import java.util.LinkedList;
import java.util.Random;

public class DiceRoll 
{
	private final int diceOne, diceTwo;
	
	public DiceRoll(int diceOne, int diceTwo)
	{   
		this.diceOne = diceOne;
		this.diceTwo = diceTwo;

		checkValidity();
	}
	
	public DiceRoll(DiceRoll diceToCopy)
	{
		this.diceOne = diceToCopy.diceOne;
		this.diceTwo = diceToCopy.diceTwo;

		checkValidity();
	}
	
	public DiceRoll(Random random)
	{
		this.diceOne = random.nextInt(6) + 1;
		this.diceTwo = random.nextInt(6) + 1;

		checkValidity();
	}

	public DiceRoll(String rollDigits) 
	{
		rollDigits = rollDigits.replaceAll(" ", "");
		diceOne = Integer.parseInt(rollDigits.substring(0, 1));
		diceTwo = Integer.parseInt(rollDigits.substring(1, 2));

		checkValidity();
	}
	
	private void checkValidity()
	{
	   if (diceOne < 1) throw new RuntimeException();
      if (diceOne > 6) throw new RuntimeException();
      
      if (diceTwo < 1) throw new RuntimeException();
      if (diceTwo > 6) throw new RuntimeException();
	}
	
	@Override
	public boolean equals(Object object)
	{
		if (object == null) return false;
		if (!(object instanceof DiceRoll)) return false;
		
		DiceRoll diceRoll = (DiceRoll) object;
		if ((diceRoll.diceOne == this.diceOne) && (diceRoll.diceTwo == this.diceTwo)) return true;
		if ((diceRoll.diceOne == this.diceTwo) && (diceRoll.diceTwo == this.diceOne)) return true;
		
		return false;
	}

	public int getDiceOne() 
	{
		return diceOne;
	}

	public int getDiceTwo() 
	{
		return diceTwo;
	}
	
	public boolean rolledDoubles()
	{
		return (diceOne == diceTwo);
	}
	
	public String toString()
	{
		return diceOne + "-" + diceTwo;
	}

	public LinkedList<Integer> getAvailableMoves() 
	{
		LinkedList<Integer> availableMoves = new LinkedList<>();
		
		if (rolledDoubles())
		{
			availableMoves.add(diceOne);
			availableMoves.add(diceOne);
			availableMoves.add(diceTwo);
			availableMoves.add(diceTwo);
		}
		else
		{
			availableMoves.add(diceOne);
			availableMoves.add(diceTwo);

		}

		return availableMoves;
	}

	public DiceRoll reverseOrder() 
	{
		return new DiceRoll(this.diceTwo, this.diceOne);
	}

}
