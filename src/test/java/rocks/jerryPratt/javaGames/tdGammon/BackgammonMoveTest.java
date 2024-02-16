package rocks.jerryPratt.javaGames.tdGammon;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.Map;

import org.junit.Test;

public class BackgammonMoveTest 
{

	@Test
	public void testSomeMoves() 
	{
		BackgammonMove backgammonMove = new BackgammonMove(BackgammonColor.White, new DiceRoll(3, 4));
		assertEquals(BackgammonColor.White, backgammonMove.getColor());

		assertEquals(0, backgammonMove.getNumberOfPieceMoves());

		backgammonMove.addMove(24, 21);
		assertEquals(1, backgammonMove.getNumberOfPieceMoves());

		backgammonMove.addMove(18, 14);
		assertEquals(2, backgammonMove.getNumberOfPieceMoves());
		
		LinkedList<BackgammonIndividualPieceMove> individualMoves = backgammonMove.getPieceMoves();
		assertEquals(individualMoves.get(0).getFromPosition(), 24);
		assertEquals(individualMoves.get(0).getToPosition(), 21);
		
		assertEquals(individualMoves.get(1).getFromPosition(), 18);
		assertEquals(individualMoves.get(1).getToPosition(), 14);
	}
	
	@Test
	public void testMoveString()
	{
		BackgammonMove backgammonMove = new BackgammonMove(BackgammonColor.White, "31: 14/13 23/20");
		
		assertEquals(3, backgammonMove.getDiceRoll().getDiceOne());
		assertEquals(1, backgammonMove.getDiceRoll().getDiceTwo());
		
		assertEquals(2, backgammonMove.getNumberOfPieceMoves());
		LinkedList<BackgammonIndividualPieceMove> pieceMoves = backgammonMove.getPieceMoves();
		assertEquals(14, pieceMoves.get(0).getFromPosition());
		assertEquals(13, pieceMoves.get(0).getToPosition());
		assertEquals(23, pieceMoves.get(1).getFromPosition());
		assertEquals(20, pieceMoves.get(1).getToPosition());
		
		backgammonMove = new BackgammonMove(BackgammonColor.White, "54:");
		assertEquals(5, backgammonMove.getDiceRoll().getDiceOne());
		assertEquals(4, backgammonMove.getDiceRoll().getDiceTwo());
		assertEquals(0, backgammonMove.getNumberOfPieceMoves());
	}
	
	@Test
   public void testGetMapFromFinalPositionToNetGain()
   {
      BackgammonMove backgammonMove = new BackgammonMove(BackgammonColor.White, "66: 14/8 14/8 6/0 7/1");
      Map<Integer, Integer> mapFromFinalPositionToNumberMovedThere = backgammonMove.getMapFromFinalPositionToNetGain();
      
      Integer count = mapFromFinalPositionToNumberMovedThere.get(8);
      assertEquals(2, count.intValue());
      
      count = mapFromFinalPositionToNumberMovedThere.get(14);
      assertEquals(-2, count.intValue());
      
      count = mapFromFinalPositionToNumberMovedThere.get(0);
      assertEquals(1, count.intValue());
      
      count = mapFromFinalPositionToNumberMovedThere.get(1);
      assertEquals(1, count.intValue());
      
      count = mapFromFinalPositionToNumberMovedThere.get(6);
      assertEquals(-1, count.intValue());
      
      count = mapFromFinalPositionToNumberMovedThere.get(7);
      assertEquals(-1, count.intValue());
      
      count = mapFromFinalPositionToNumberMovedThere.get(13);
      assertNull(count);
   }
	
	
	@Test
	public void testSomeMoveExceptions()
	{
	      BackgammonMove backgammonMove = new BackgammonMove(BackgammonColor.White, new DiceRoll(3, 3));
	      backgammonMove.addMove(12, 9);
	      
	      try
	      {
	         backgammonMove.addMove(11, 9);
	         fail("Not a valid move from the roll.");
	      }
	      catch(RuntimeException e)
	      {
	         
	      }
	      

	      assertEquals(1, backgammonMove.getNumberOfPieceMoves());
	}
	
}
