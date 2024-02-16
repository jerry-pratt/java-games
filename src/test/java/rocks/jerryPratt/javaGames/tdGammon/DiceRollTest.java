package rocks.jerryPratt.javaGames.tdGammon;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Test;

public class DiceRollTest 
{
	@Test
	public void testStringConstructor() 
	{
		DiceRoll diceRoll = new DiceRoll("63");
		
		assertEquals(6, diceRoll.getDiceOne());
		assertEquals(3, diceRoll.getDiceTwo());
		assertFalse(diceRoll.rolledDoubles());

		LinkedList<Integer> availableMoves = diceRoll.getAvailableMoves();
		assertEquals(2, availableMoves.size());
		assertEquals(6, availableMoves.get(0).intValue());
		assertEquals(3, availableMoves.get(1).intValue());
		
		diceRoll = new DiceRoll(" 45 ");
		
		assertEquals(4, diceRoll.getDiceOne());
		assertEquals(5, diceRoll.getDiceTwo());
		assertFalse(diceRoll.rolledDoubles());

		availableMoves = diceRoll.getAvailableMoves();
		assertEquals(2, availableMoves.size());
		assertEquals(4, availableMoves.get(0).intValue());
		assertEquals(5, availableMoves.get(1).intValue());
		
		diceRoll = diceRoll.reverseOrder();
		assertEquals(5, diceRoll.getDiceOne());
		assertEquals(4, diceRoll.getDiceTwo());
		assertFalse(diceRoll.rolledDoubles());

		availableMoves = diceRoll.getAvailableMoves();
		assertEquals(2, availableMoves.size());
		assertEquals(5, availableMoves.get(0).intValue());
		assertEquals(4, availableMoves.get(1).intValue());
		
		diceRoll = new DiceRoll(" 33 ");
		
		assertEquals(3, diceRoll.getDiceOne());
		assertEquals(3, diceRoll.getDiceTwo());
		assertTrue(diceRoll.rolledDoubles());
		
		availableMoves = diceRoll.getAvailableMoves();
		assertEquals(4, availableMoves.size());
		assertEquals(3, availableMoves.get(0).intValue());
		assertEquals(3, availableMoves.get(1).intValue());
		assertEquals(3, availableMoves.get(2).intValue());
		assertEquals(3, availableMoves.get(3).intValue());
	}
	
	@Test
   public void testExceptionsInStringConstructor() 
   {
	   try
	   {
	       DiceRoll diceRoll = new DiceRoll("73");
	       fail("Should throw exception if more than 6");
	   }
	   catch(RuntimeException e)
	   {
	   }
	   
	   try
	   {
	      DiceRoll diceRoll = new DiceRoll("50");
	      fail("Should throw exception if less than 1");
	   }
	   catch(RuntimeException e)
	   {
	   }
   }
}
