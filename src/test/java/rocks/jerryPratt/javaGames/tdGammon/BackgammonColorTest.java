package rocks.jerryPratt.javaGames.tdGammon;

import static org.junit.Assert.*;

import org.junit.Test;

public class BackgammonColorTest
{

   @Test
   public void testBackgammonColor()
   {
      BackgammonColor white = BackgammonColor.White;
      BackgammonColor black = BackgammonColor.Black;
      
      assertEquals(white, black.getOppositeColor());
      assertEquals(black, white.getOppositeColor());
     
      assertEquals(black, BackgammonColor.parseColor(black.getLetter()));
      assertEquals(white, BackgammonColor.parseColor(white.getLetter()));
      
      try
      {
         BackgammonColor.parseColor("p");
         fail("Should have thrown exception");
      }
      catch (Exception e)
      {
         
      }
   }

}
