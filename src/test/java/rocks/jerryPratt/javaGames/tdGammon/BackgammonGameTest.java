package rocks.jerryPratt.javaGames.tdGammon;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class BackgammonGameTest 
{
	@Test
	public void testSomeGamesBetweenTwoRandomPlayers() 
	{
		int numberOfGames = 1000;
		ArrayList<Integer> numberOfMovesTaken = new ArrayList<>();
		
		BackgammonPlayer blackPlayer = new RandomBackgammonPlayer(BackgammonColor.Black);
		BackgammonPlayer whitePlayer = new RandomBackgammonPlayer(BackgammonColor.White);
//		game.setWhitePlayer(new RandomBackgammonPlayer(BackgammonColor.White));

		int whiteWins = playSomeGamesReturnWhiteWins(numberOfGames, whitePlayer, blackPlayer, numberOfMovesTaken);
		
		double whiteWinPercentage = (((double) whiteWins) / ((double) numberOfGames));
		
		// For a lot of random games, random play should be about 50/50...
		assertTrue(whiteWinPercentage > 0.4);
		assertTrue(whiteWinPercentage < 0.6);
		
		int maxNumberOfMovesTaken = 0;
		int totalNumberOfMovesTaken = 0;
		
		for (int numberOfMoves : numberOfMovesTaken)
		{
			totalNumberOfMovesTaken+= numberOfMoves;
			if (numberOfMoves > maxNumberOfMovesTaken) maxNumberOfMovesTaken = numberOfMoves;
		}
		
//		int averageNumberOfMovesTaken = totalNumberOfMovesTaken / numberOfGames;
//		
//		System.out.println("whiteWinPercentage = " + whiteWinPercentage);
//		System.out.println("averageNumberOfMovesTaken = " + averageNumberOfMovesTaken);
//		System.out.println("maxNumberOfMovesTaken = " + maxNumberOfMovesTaken);
	}
	
	@Test
	public void testSomeGamesWhiteAgressiveBlackRandom() 
	{
		int numberOfGames = 100;
		ArrayList<Integer> numberOfMovesTaken = new ArrayList<>();
		
		BackgammonPlayer blackPlayer = new RandomBackgammonPlayer(BackgammonColor.Black);
		BackgammonPlayer whitePlayer = new AgressiveHitBackgammonPlayer(BackgammonColor.White);

		int whiteWins = playSomeGamesReturnWhiteWins(numberOfGames, whitePlayer, blackPlayer, numberOfMovesTaken);
		
		double whiteWinPercentage = (((double) whiteWins) / ((double) numberOfGames));
//		System.out.println("whiteWinPercentage = " + whiteWinPercentage);

		// For a lot of games of aggressive against random, white play should be about 90 percent...
		assertTrue(whiteWinPercentage > 0.85);
		
		int maxNumberOfMovesTaken = 0;
		int totalNumberOfMovesTaken = 0;
		
		for (int numberOfMoves : numberOfMovesTaken)
		{
			totalNumberOfMovesTaken+= numberOfMoves;
			if (numberOfMoves > maxNumberOfMovesTaken) maxNumberOfMovesTaken = numberOfMoves;
		}
		
		int averageNumberOfMovesTaken = totalNumberOfMovesTaken / numberOfGames;
		
//		System.out.println("averageNumberOfMovesTaken = " + averageNumberOfMovesTaken);
//		System.out.println("maxNumberOfMovesTaken = " + maxNumberOfMovesTaken);
	}

	private int playSomeGamesReturnWhiteWins(int numberOfGames, BackgammonPlayer whitePlayer, BackgammonPlayer blackPlayer, ArrayList<Integer> numberOfMovesTaken) 
	{
		int whiteWins = 0;
		
		for (int i=0; i<numberOfGames; i++)
		{
			BackgammonGame game = new BackgammonGame();
			game.setWhitePlayer(whitePlayer);
			game.setBlackPlayer(blackPlayer);

			BackgammonColor winner = game.playAGame(false);
			
			assertNotNull(winner);
			BackgammonBoard board = game.getBoard();
			
			assertEquals(0, board.getPiecesNotFinished(winner));
			assertTrue(board.getPiecesNotFinished(winner.getOppositeColor()) > 0);
			
			if (winner == BackgammonColor.White) whiteWins++;
			
			numberOfMovesTaken.add(game.getNumberOfMovesTaken());
		}
		
		return whiteWins;
		
	}
}
