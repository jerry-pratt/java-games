package rocks.jerryPratt.javaGames.tdGammon;

import java.util.EnumMap;
import java.util.Random;

public class BackgammonGame 
{
	private final Random random = new Random();
	private EnumMap<BackgammonColor, BackgammonPlayer> players = new EnumMap<>(BackgammonColor.class);
	
	private BackgammonBoard board;
	private int numberOfMovesTaken = 0;
	
	private BackgammonSpriteWorld spriteWorld;
	
	public BackgammonGame()
	{
		this.board = new BackgammonBoard();
	}
	
	public BackgammonGame(BackgammonColor color, String boardString)
	{
		this.board = new BackgammonBoard(color, boardString);
	}
	
	public BackgammonBoard getBoard() 
	{
		return board;
	}

	public BackgammonPlayer getWhitePlayer() 
	{
		return players.get(BackgammonColor.White);
	}
	
	public void reset()
	{
		this.board = new BackgammonBoard();
		
		if (spriteWorld != null) spriteWorld.setBackgammonBoard(board);
	}

	public void setWhitePlayer(BackgammonPlayer whitePlayer) 
	{
		players.put(BackgammonColor.White, whitePlayer);
	}

	public BackgammonPlayer getBlackPlayer() 
	{
		return players.get(BackgammonColor.Black);
	}

	public void setBlackPlayer(BackgammonPlayer blackPlayer) 
	{
	    players.put(BackgammonColor.Black, blackPlayer);
	}
	
	public BackgammonColor playAGame(boolean printOut)
	{				
		getWhitePlayer().startANewGame(new BackgammonBoard(board));
		getBlackPlayer().startANewGame(new BackgammonBoard(board));
		
		int printEveryN = 1; //10000;
		int count = 0;
				
      BackgammonColor color = BackgammonColor.White;

      while(true)
      {		   
    	  if (spriteWorld != null) spriteWorld.setBackgammonBoard(board);

    	  DiceRoll diceRoll = new DiceRoll(random);
    	  if (spriteWorld != null) spriteWorld.setDiceRoll(color, diceRoll);

    	  BackgammonBoard boardBeforeMove = new BackgammonBoard(board);
    	  BackgammonMove playerMove = players.get(color).chooseAMove(diceRoll, boardBeforeMove);
    	  if (!playerMove.getDiceRoll().equals(diceRoll)) throw new RuntimeException("Illegally changed dice roll!!");

    	  count++;
    	  if ((count >= printEveryN) && printOut)
    	  {
    		  count = 0;
    		  System.out.println("\n" + board);
    		  System.out.println("Roll: " + diceRoll);
    		  System.out.println("Player Move: " + playerMove);
    		  System.out.println("Total Moves: " + numberOfMovesTaken);
    	  }

    	  if (!board.isMoveValid(playerMove))
    	  {
    		  System.err.println("Move isn't valid! : " + playerMove);
    		  System.err.println("Board : \n" + board);
    		  System.err.flush();
    		  throw new RuntimeException();
    	  }

    	  BackgammonBoard boardAfterMove = new BackgammonBoard(board);
    	  boardAfterMove.makeMove(playerMove);
    	  board = new BackgammonBoard(boardAfterMove);
    	  if (spriteWorld != null) spriteWorld.setBackgammonBoard(board);

    	  players.get(color.getOppositeColor()).informThatOpponentMadeMove(boardBeforeMove, playerMove, boardAfterMove);

    	  numberOfMovesTaken++;
    	  if (board.isGameFinished()) return color;

    	  color = color.getOppositeColor();
      }
	}
	
	public int getNumberOfMovesTaken()
	{
		return numberOfMovesTaken;
	}
	
	public static void main(String[] args) 
	{
		BackgammonGame game = new BackgammonGame();
		game.setBlackPlayer(new RandomBackgammonPlayer(BackgammonColor.Black));
		game.setWhitePlayer(new RandomBackgammonPlayer(BackgammonColor.White));
		
		game.playAGame(true);
		game.reset();
	}

   public void setSpriteWorld(BackgammonSpriteWorld spriteWorld)
   {
      this.spriteWorld = spriteWorld;
   }


}
