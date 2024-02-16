package rocks.jerryPratt.javaGames.tdGammon;

public class BackgammonIndividualPieceMove
{
   private final int fromPosition, toPosition;

   public BackgammonIndividualPieceMove(int fromPosition, int toPosition)
   {
      this.fromPosition = fromPosition;
      this.toPosition = toPosition;
   }

   public BackgammonIndividualPieceMove(BackgammonIndividualPieceMove moveToCopy)
   {
      this.fromPosition = moveToCopy.fromPosition;
      this.toPosition = moveToCopy.toPosition;
   }

   public int getFromPosition()
   {
      return fromPosition;
   }

   public int getToPosition()
   {
      return toPosition;
   }
}
