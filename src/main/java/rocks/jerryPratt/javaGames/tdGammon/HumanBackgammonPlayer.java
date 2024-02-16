package rocks.jerryPratt.javaGames.tdGammon;

import java.util.ArrayList;

import us.ihmc.javaSpriteWorld.Sprite;
import us.ihmc.javaSpriteWorld.SpriteMouseListener;
import us.ihmc.javaSpriteWorld.SpriteWorldViewer;

public class HumanBackgammonPlayer implements BackgammonPlayer, SpriteMouseListener
{
	private BackgammonColor color;
	private BackgammonSpriteWorld backgammonSpriteWorld;
	
	private boolean currentlyChoosingAMove = false;
	
	private DiceRoll playerDiceRoll = null;
	private BackgammonMove playerMove = null;
	private boolean needToCheckMove = false;
	
	private int boardSpacePressed = -1;
	private int boardSpaceReleased = -1;
	
	public HumanBackgammonPlayer(BackgammonColor color)
	{
		this.color = color;
		
		backgammonSpriteWorld = new BackgammonSpriteWorld();
		backgammonSpriteWorld.attachSpriteMouseListenerToCheckers(color, this);
	}
	
	public synchronized boolean addIndividualMoveToPlayerMove(int fromPosition, int toPosition)
	{
		try
		{
			playerMove.addMove(fromPosition, toPosition);
		}
		catch(Exception e)
		{
			System.err.println("Individual move not valid: " + fromPosition + "/" + toPosition + ". Move currently is " + playerMove);
			return false;
		}
		needToCheckMove = true;
		return true;
	}
	
	public synchronized void clearPlayerMoveAndTryAgain()
	{
		playerMove = new BackgammonMove(color, playerDiceRoll);
		needToCheckMove = false;
	}
	
	public synchronized boolean getAndResetNeedToCheckMove()
	{
		boolean ret = needToCheckMove;
		needToCheckMove = false;
		
		return ret;
	}

	@Override
	public BackgammonMove chooseAMove(DiceRoll diceRoll, BackgammonBoard board) 
	{
		playerDiceRoll = new DiceRoll(diceRoll);
		playerMove = new BackgammonMove(color, playerDiceRoll);

		boardSpacePressed = -1;
		boardSpaceReleased = -1;
		
		currentlyChoosingAMove = true;
		
		backgammonSpriteWorld.setDiceRoll(board.getWhoseTurn(), diceRoll);
		backgammonSpriteWorld.setBackgammonBoard(board);
		
      ArrayList<BackgammonMove> movesToPack = new ArrayList<>();
      stopDraggingCheckersAndUpdate();

		while(true)
		{
			System.out.println(board);
			System.out.println("Dice Roll =  " + diceRoll);

			movesToPack.clear();
			board.getAllPossibleMoves(diceRoll, movesToPack);
			
         if (!movesToPack.isEmpty())
         {
            while (!this.getAndResetNeedToCheckMove())
            {
               //             System.out.print(".");
               try 
               {
                  Thread.sleep(100L);
               } 
               catch (InterruptedException e) 
               {
               }
            }
         }
         
         else System.out.println("No moves avaialable with that roll! Skipping your turn!");

//			System.out.println("Checking Move " + playerMove);
			
			if (board.isMoveValid(playerMove))
			{
//				System.out.println("Valid Move!");

				currentlyChoosingAMove = false;

				BackgammonMove moveToReturn = playerMove;

				playerMove = null;
				boardSpacePressed = -1;
				boardSpaceReleased = -1;

				BackgammonBoard newBoard = new BackgammonBoard(board);
				newBoard.makeMove(moveToReturn);
				
				backgammonSpriteWorld.setBackgammonBoard(newBoard);

				stopDraggingCheckersAndUpdate();
				backgammonSpriteWorld.stopDraggingCheckersAndUpdate(color.getOppositeColor());

				markCheckersRecentlyMoved(moveToReturn);
				
				return moveToReturn;
			}

			if (playerMove.getNumberOfPieceMoves() >= diceRoll.getAvailableMoves().size()) 
			{
				System.err.println("Move " + playerMove + " is invalid. Try again!");
				playerMove = new BackgammonMove(color, diceRoll);
				stopDraggingCheckersAndUpdate();
			}
		}
	}

	@Override
	public void startANewGame(BackgammonBoard startingBoard) 
	{
	     backgammonSpriteWorld.setBackgammonBoard(startingBoard);
	}

	@Override
	public void informThatOpponentMadeMove(BackgammonBoard originalBoard, BackgammonMove opponentMove, BackgammonBoard resultantBoard) 
	{
		backgammonSpriteWorld.setBackgammonBoard(originalBoard);
		backgammonSpriteWorld.setDiceRoll(originalBoard.getWhoseTurn(), opponentMove.getDiceRoll());
		
		try 
		{
			Thread.sleep(5000L);
		} 
		catch (InterruptedException e) 
		{
		}
		
		backgammonSpriteWorld.setBackgammonBoard(resultantBoard);
      backgammonSpriteWorld.stopDraggingCheckersAndUpdate(originalBoard.getWhoseTurn());
      markCheckersRecentlyMoved(opponentMove);
	}

	private void markCheckersRecentlyMoved(BackgammonMove recentMove)
   {
      backgammonSpriteWorld.markCheckersRecentlyMoved(recentMove);
   }

   @Override
   public void spriteClicked(SpriteWorldViewer viewer, Sprite sprite, double xWorld, double yWorld, int clickCount)
	{
	}
	
	@Override
   public void spritePressed(SpriteWorldViewer viewer, Sprite sprite, double xWorld, double yWorld)
	{
		if (!currentlyChoosingAMove) return;

		boardSpacePressed = backgammonSpriteWorld.getBoardSpaceFromWorldPosition(xWorld, yWorld);
		if (boardSpacePressed == -1) return;
	}
	
	@Override
   public void spriteReleased(SpriteWorldViewer viewer, Sprite sprite, double xWorld, double yWorld)
	{
		if (!currentlyChoosingAMove) return;
		
		BackgammonCheckerSprite backgammonCheckerSprite = (BackgammonCheckerSprite) sprite;
		boardSpaceReleased = backgammonSpriteWorld.getBoardSpaceFromWorldPosition(xWorld, yWorld);
		
		if ((boardSpaceReleased >= 0) && (boardSpaceReleased <=25))
		{
			boolean successfulIndividualMove = addIndividualMoveToPlayerMove(boardSpacePressed, boardSpaceReleased);
			if (!successfulIndividualMove) backgammonCheckerSprite.setDragging(false);
			backgammonSpriteWorld.update();
		}
		else
		{
			boardSpacePressed = -1;
			boardSpaceReleased = -1;
			
			clearPlayerMoveAndTryAgain();
			stopDraggingCheckersAndUpdate();
			return;
		}	
	}

	@Override
   public void spriteDragged(SpriteWorldViewer viewer, Sprite sprite, double xWorld, double yWorld)
	{
		if (!currentlyChoosingAMove) return;
		
		BackgammonCheckerSprite checker = (BackgammonCheckerSprite) sprite;
		
		checker.setDragging(true);
		
		checker.setX(xWorld);
		checker.setY(yWorld);
		
		backgammonSpriteWorld.update();
	}
	
	public void stopDraggingCheckersAndUpdate()
	{
		backgammonSpriteWorld.stopDraggingCheckersAndUpdate(color);
	}
	
	public static void main(String[] args) 
   {
      HumanBackgammonPlayer humanPlayer = new HumanBackgammonPlayer(BackgammonColor.White);
      TDGammonPlayAgainstItselfPlayer computerPlayer = new TDGammonPlayAgainstItselfPlayer(0.0);

      computerPlayer.setLearningOn(false);
      computerPlayer.setWeightsToPreviouslyLearnedWeights();

//      BackgammonGame game = new BackgammonGame(BackgammonColor.White, "0W/1B  ___ 2B  ___ 2B  ___ 3B  2B  3B  ___ ___ ___ ___ 2B  ___ ___ ___ ___ ___ ___ ___ 4W  5W  2W  4W  0W/0B");
//    BackgammonGame game = new BackgammonGame(BackgammonColor.White, "1W/0B  2B 2B  2B 2B  2B 3B  ___  ___  ___ ___ ___ ___ ___  ___ ___ 1B ___ ___ 1B ___ 4W  5W  2W  3W  0W/0B");
    
      BackgammonGame game = new BackgammonGame();
    
      game.setWhitePlayer(humanPlayer);
      game.setBlackPlayer(computerPlayer);

      while(true)
      {
          BackgammonColor winner = game.playAGame(false);
          System.out.println(winner + " won!!  Play again?");
          game.reset();
      }
   }

}
