package rocks.jerryPratt.javaGames.tdGammon;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class BackgammonIndividualPieceMoveTest
{

   @Test
   public void testIndividualPieceMove()
   {
      int fromPosition = 5;
      int toPosition = 7;
      BackgammonIndividualPieceMove move = new BackgammonIndividualPieceMove(fromPosition, toPosition);
      
      assertEquals(fromPosition, move.getFromPosition());
      assertEquals(toPosition, move.getToPosition());
      
      BackgammonIndividualPieceMove moveCopy = new BackgammonIndividualPieceMove(move);
      assertEquals(moveCopy.getFromPosition(), move.getFromPosition());
      assertEquals(moveCopy.getToPosition(), move.getToPosition());
      
   }

}
