package rocks.jerryPratt.javaGames.tdGammon;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class BackgammonBoardTest 
{
	@Test
	public void testBackgammonBoardInitialization() 
	{
		BackgammonBoard board = new BackgammonBoard();
		assertEquals(BackgammonColor.White, board.getWhoseTurn());
		
		assertEquals(2, board.getPieces(BackgammonColor.Black, 24));
		assertEquals(5, board.getPieces(BackgammonColor.Black, 13));
		assertEquals(3, board.getPieces(BackgammonColor.Black, 8));
		assertEquals(5, board.getPieces(BackgammonColor.Black, 6));
		
		assertEquals(2, board.getPieces(BackgammonColor.White, 24));
		assertEquals(5, board.getPieces(BackgammonColor.White, 13));
		assertEquals(3, board.getPieces(BackgammonColor.White, 8));
		assertEquals(5, board.getPieces(BackgammonColor.White, 6));
		
		assertEquals(0, board.getPiecesOnBar(BackgammonColor.White));
		assertEquals(0, board.getPiecesFinished(BackgammonColor.White));
		assertEquals(0, board.getPiecesOnBar(BackgammonColor.Black));
		assertEquals(0, board.getPiecesFinished(BackgammonColor.Black));
		
		assertEquals(15, board.getPiecesNotFinished(BackgammonColor.White));
		assertEquals(15, board.getPiecesNotFinished(BackgammonColor.Black));
		
		board.checkRepInvariant();
	}
	
	@Test
	public void testAFewSimpleBackgammonMoves() 
	{
		BackgammonBoard board = new BackgammonBoard();
		
		BackgammonMove backgammonMove = new BackgammonMove(BackgammonColor.White, new DiceRoll(4, 3));
		backgammonMove.addMove(24, 20);
		backgammonMove.addMove(24, 21);
		
		board.makeMove(backgammonMove);
		assertEquals(0, board.getPieces(BackgammonColor.White, 24));
		assertEquals(1, board.getPieces(BackgammonColor.White, 20));
		assertEquals(1, board.getPieces(BackgammonColor.White, 21));
				
		backgammonMove = new BackgammonMove(BackgammonColor.Black, new DiceRoll(1, 3));
		backgammonMove.addMove(6, 5);
		backgammonMove.addMove(8, 5);
		
		board.makeMove(backgammonMove);
		assertEquals(2, board.getPieces(BackgammonColor.Black, 5));
		assertEquals(4, board.getPieces(BackgammonColor.Black, 6));
		assertEquals(2, board.getPieces(BackgammonColor.Black, 8));
		assertEquals(0, board.getPieces(BackgammonColor.White, 20));
		assertEquals(1, board.getPiecesOnBar(BackgammonColor.White));
		
		backgammonMove = new BackgammonMove(BackgammonColor.White, new DiceRoll(2, 2));
		backgammonMove.addMove(25, 23);
		backgammonMove.addMove(6, 4);
		backgammonMove.addMove(6, 4);
		backgammonMove.addMove(8, 6);
		
		board.makeMove(backgammonMove);
		assertEquals(0, board.getPieces(BackgammonColor.White, 24));
		assertEquals(1, board.getPieces(BackgammonColor.White, 23));
		assertEquals(0, board.getPieces(BackgammonColor.White, 22));
		assertEquals(1, board.getPieces(BackgammonColor.White, 21));
		assertEquals(0, board.getPieces(BackgammonColor.White, 20));
		assertEquals(0, board.getPiecesOnBar(BackgammonColor.White));
		assertEquals(2, board.getPieces(BackgammonColor.White, 8));
		assertEquals(4, board.getPieces(BackgammonColor.White, 6));
		assertEquals(2, board.getPieces(BackgammonColor.White, 4));
	}
	
	@Test
	public void testCopy() 
	{
		BackgammonBoard board = new BackgammonBoard();
		board = new BackgammonBoard(board);
		
		BackgammonMove backgammonMove = new BackgammonMove(BackgammonColor.White, new DiceRoll(4, 3));
		backgammonMove.addMove(24, 20);
		backgammonMove.addMove(24, 21);
		
		assertTrue(board.isMoveValid(backgammonMove));
		board.makeMove(backgammonMove);
		assertEquals(0, board.getPieces(BackgammonColor.White, 24));
		assertEquals(1, board.getPieces(BackgammonColor.White, 20));
		assertEquals(1, board.getPieces(BackgammonColor.White, 21));
		
		board = new BackgammonBoard(board);
		backgammonMove = new BackgammonMove(BackgammonColor.Black, new DiceRoll(1, 3));
		backgammonMove.addMove(6, 5);
		backgammonMove.addMove(8, 5);
		
		board.makeMove(backgammonMove);
		assertEquals(2, board.getPieces(BackgammonColor.Black, 5));
		assertEquals(4, board.getPieces(BackgammonColor.Black, 6));
		assertEquals(2, board.getPieces(BackgammonColor.Black, 8));
		assertEquals(0, board.getPieces(BackgammonColor.White, 20));
		assertEquals(1, board.getPiecesOnBar(BackgammonColor.White));
		
		board = new BackgammonBoard(board);
		backgammonMove = new BackgammonMove(BackgammonColor.White, new DiceRoll(2, 2));
		backgammonMove.addMove(25, 23);
		backgammonMove.addMove(6, 4);
		backgammonMove.addMove(6, 4);
		backgammonMove.addMove(8, 6);
		
		board = new BackgammonBoard(board);
		board.makeMove(backgammonMove);
		assertEquals(0, board.getPieces(BackgammonColor.White, 24));
		assertEquals(1, board.getPieces(BackgammonColor.White, 23));
		assertEquals(0, board.getPieces(BackgammonColor.White, 22));
		assertEquals(1, board.getPieces(BackgammonColor.White, 21));
		assertEquals(0, board.getPieces(BackgammonColor.White, 20));
		assertEquals(0, board.getPiecesOnBar(BackgammonColor.White));
		assertEquals(2, board.getPieces(BackgammonColor.White, 8));
		assertEquals(4, board.getPieces(BackgammonColor.White, 6));
		assertEquals(2, board.getPieces(BackgammonColor.White, 4));
	}
	
	@Test
	public void testAttemptToMoveToDefendersBlot()
	{
		BackgammonBoard board = new BackgammonBoard();
		BackgammonMove backgammonMove = new BackgammonMove(BackgammonColor.White, new DiceRoll(5, 1));

		backgammonMove.addMove(24, 23);
		backgammonMove.addMove(24, 19);
		 
		try
		{
			board.makeMove(backgammonMove);
			fail("Can't move to a defenders blot!");
		}
		catch(Exception e)
		{
		}
	}
	
	@Test
	public void testMovingOffAtTheEnd()
	{
		BackgammonBoard board = BackgammonBoard.createEndGameOneBoard();
		
		DiceRoll diceRoll = new DiceRoll(2, 1);
		ArrayList<BackgammonMove> movesToPack = new ArrayList<>();
		board.getAllPossibleMoves(diceRoll , movesToPack);
		
		assertEquals(1, movesToPack.size());
		
		BackgammonMove backgammonMove = new BackgammonMove(BackgammonColor.White, new DiceRoll(1, 3));

		backgammonMove.addMove(1, 0);
		board.makeMove(backgammonMove);
		
		assertTrue(board.isGameFinished());
	}
	
//	board.whitePieces[finishedPosition] = 10;
//	board.blackPieces[finishedPosition] = 0;
//
//	board.whitePieces[1] = 1;
//	board.whitePieces[3] = 2;
//	board.whitePieces[6] = 2;
	
	@Test
	public void testMovingOffAtTheEndTwo()
	{
		BackgammonBoard board = BackgammonBoard.createEndGameTwoBoard();
		
		DiceRoll diceRoll = new DiceRoll(4, 1);
		ArrayList<BackgammonMove> movesToPack = new ArrayList<>();
		board.getAllPossibleMoves(diceRoll , movesToPack);
		
//		assertEquals(2, movesToPack.size());
		
		BackgammonMove backgammonMove = new BackgammonMove(BackgammonColor.White, new DiceRoll(1, 4));
		assertTrue(board.areAnyExactMovesPossible(backgammonMove.getDiceRoll().getAvailableMoves()));
		assertTrue(board.areAnyExactMovesPossible(new DiceRoll(2, 2).getAvailableMoves()));

		
		// Having rolled a 4 and 1, need to first move the piece at 6 or 3.
		backgammonMove.addMove(1, 0);
		backgammonMove.addMove(3, 0);
		
		assertTrue(board.areAnyExactMovesPossible(backgammonMove.getDiceRoll().getAvailableMoves()));
		assertFalse(board.isMoveValid(backgammonMove));
		
		try
		{
			board.makeMove(backgammonMove);
			fail("Must move high numbers if possible before bearing off low numbers with too high a roll");
		}
		catch(Exception e)
		{

		}
	}
	
	@Test
	public void testAttemptToMoveOffTooEarly()
	{
		BackgammonBoard board = new BackgammonBoard();
		BackgammonMove backgammonMove = new BackgammonMove(BackgammonColor.White, new DiceRoll(6, 6));

		backgammonMove.addMove(6, 0);
		backgammonMove.addMove(6, 0);
		backgammonMove.addMove(6, 0);
		backgammonMove.addMove(6, 0);
		 
		try
		{
			board.makeMove(backgammonMove);
			fail("Can't move off when still have pieces in the 7 spot or higher!");
		}
		catch(Exception e)
		{
		}
	}
	
	@Test
	public void testAttemptToMoveWhenHaveNoPiece()
	{
		BackgammonBoard board = new BackgammonBoard();
		BackgammonMove backgammonMove = new BackgammonMove(BackgammonColor.White, new DiceRoll(2, 1));

		backgammonMove.addMove(24, 23);
		backgammonMove.addMove(22, 20);
		 
		try
		{
			board.makeMove(backgammonMove);
			fail("Can't move when you don't have a piece!");
		}
		catch(Exception e)
		{
		}
	}
	
	@Test
	public void testInvalidMoveColor() 
	{
		BackgammonBoard board = new BackgammonBoard();
		
		BackgammonMove backgammonMove = new BackgammonMove(BackgammonColor.Black, new DiceRoll(1, 3));
		
		try
		{
			board.makeMove(backgammonMove);
			fail("Wrong color first");
		}
		catch(Exception e)
		{
		}
	}
	
	@Test
	public void testInvalidMove() 
	{
		BackgammonBoard board = new BackgammonBoard();
		BackgammonMove backgammonMove = new BackgammonMove(BackgammonColor.White, new DiceRoll(5, 2));
		backgammonMove.addMove(24, 19);
		
		try
		{
			board.makeMove(backgammonMove);
			fail("Can't land on a defending point");
		}
		catch(Exception e)
		{
		}
	}
	
	@Test
	public void testInvalidDoubles() 
	{
		BackgammonBoard board = new BackgammonBoard();
		BackgammonMove backgammonMove = new BackgammonMove(BackgammonColor.White, new DiceRoll(2, 2));
		backgammonMove.addMove(19, 17);
		backgammonMove.addMove(19, 17);
		try
		{
			backgammonMove.addMove(19, 16);
			fail("If you get a doubles, must have all the same number.");
		}
		catch(Exception e)
		{
		}
	}
	
	@Test
	public void testInvalidDice() 
	{
		BackgammonBoard board = new BackgammonBoard();
		BackgammonMove backgammonMove = new BackgammonMove(BackgammonColor.White, new DiceRoll(2, 4));
		
		try
		{
			backgammonMove.addMove(19, -1);
			fail("Invalid dice");
		}
		catch(Exception e)
		{
		}
		
		board = new BackgammonBoard();
		
		try
		{
			backgammonMove = new BackgammonMove(BackgammonColor.White, new DiceRoll(8, 2));
			fail("Invalid dice");
		}
		catch(Exception e)
		{
		}
	}
	
	@Test
	public void testInvalidMoveWhenNeedToBarOff() 
	{
		BackgammonBoard board = new BackgammonBoard();
		BackgammonMove backgammonMove = new BackgammonMove(BackgammonColor.White, new DiceRoll(3, 4));
		
		backgammonMove.addMove(24, 21);
		backgammonMove.addMove(24, 20);
		
		assertTrue(board.areAnyExactMovesPossible(backgammonMove.getDiceRoll().getAvailableMoves()));
		board.makeMove(backgammonMove);
		
		backgammonMove = new BackgammonMove(BackgammonColor.Black, new DiceRoll(1, 3));
		backgammonMove.addMove(6, 5);
		backgammonMove.addMove(8, 5);
		board.makeMove(backgammonMove);
		
		assertEquals(1, board.getPiecesOnBar(BackgammonColor.White));

		backgammonMove = new BackgammonMove(BackgammonColor.White, new DiceRoll(3, 1));
		backgammonMove.addMove(17, 14);
		backgammonMove.addMove(19, 18);
		
		try
		{
			board.makeMove(backgammonMove);
			fail("Must bar off first");
		}
		catch(RuntimeException e)
		{
		}
	}
	
	
	@Test
	public void testAGrandmasterGame() 
	{
		// From http://www.hardyhuebener.de/downloads/2011_mc_final_suzuki_vs_gullota.txt
			
		BackgammonBoard board = new BackgammonBoard();
		
		board.makeMove(BackgammonColor.White, "53: 6/3 8/3");
		board.makeMove(BackgammonColor.Black, "31: 6/5 8/5");
		board.makeMove(BackgammonColor.White, "62: 13/11 24/18");
		board.makeMove(BackgammonColor.Black, "53: 13/10 10/5");
		board.makeMove(BackgammonColor.White, "43: 18/15 15/11");
		board.makeMove(BackgammonColor.Black, "42: 6/4 8/4");
		board.makeMove(BackgammonColor.White, "62: 24/22 22/16");
		board.makeMove(BackgammonColor.Black, "21: 24/23 23/21");
		board.makeMove(BackgammonColor.White, "33: 16/13 13/10 13/10 13/10");
		board.makeMove(BackgammonColor.Black, "44: 13/9 9/5 5/1 5/1");
		board.makeMove(BackgammonColor.White, "41: 11/7 7/6");
		board.makeMove(BackgammonColor.Black, "52: 21/16 16/14");
		board.makeMove(BackgammonColor.White, "63: 25/22 22/16");
		board.makeMove(BackgammonColor.Black, "21: 14/13 6/4");
		board.makeMove(BackgammonColor.White, "64: 10/6 16/10");
		board.makeMove(BackgammonColor.Black, "63: 13/10 13/7");
		board.makeMove(BackgammonColor.White, "63: 10/7 13/7");
		board.makeMove(BackgammonColor.Black, "31: 10/7 13/12");
		board.makeMove(BackgammonColor.White, "62: 25/23 23/17");
		board.makeMove(BackgammonColor.Black, "32: 25/23 12/9");
		board.makeMove(BackgammonColor.White, "52: 17/12 12/10");
		board.makeMove(BackgammonColor.Black, "41: 25/21 24/23");
		board.makeMove(BackgammonColor.White, "63: 10/4 7/4");
		board.makeMove(BackgammonColor.Black, "51: 25/24 23/18");
		board.makeMove(BackgammonColor.White, "62: 25/23 23/17");
		board.makeMove(BackgammonColor.Black, "21: 9/8 8/6");
		
		ArrayList<BackgammonMove> movesToPack = new ArrayList<>();
		board.getAllPossibleMoves(new DiceRoll(6, 1), movesToPack);
		assertTrue(movesToPack.isEmpty());
		
		board.makeMove(BackgammonColor.White, "61:");
		board.makeMove(BackgammonColor.Black, "44: 24/20 18/14 7/3 7/3");
		
		movesToPack.clear();
		board.getAllPossibleMoves(new DiceRoll(6, 4), movesToPack);
		assertTrue(movesToPack.isEmpty());
		
		assertFalse(board.canMakeAMove(new DiceRoll(6, 4).getAvailableMoves()));
		board.makeMove(BackgammonColor.White, "64:");
		board.makeMove(BackgammonColor.Black, "31: 14/13 23/20");
		board.makeMove(BackgammonColor.White, "52: 25/23 23/18");
		board.makeMove(BackgammonColor.Black, "52: 13/8 8/6");
		board.makeMove(BackgammonColor.White, "33: 18/15 6/3 4/1 4/1");
		board.makeMove(BackgammonColor.Black, "65: 20/14 14/9");
		board.makeMove(BackgammonColor.White, "43: 15/11 11/8");
		board.makeMove(BackgammonColor.Black, "33: 9/6 6/3 6/3 6/3");
		board.makeMove(BackgammonColor.White, "61: 3/2 8/2");
		board.makeMove(BackgammonColor.Black, "11: 3/2 3/2 3/2 2/1");
		board.makeMove(BackgammonColor.White, "32: 8/5 5/3");
		board.makeMove(BackgammonColor.Black, "44: 25/21 21/17 17/13 13/9");
		
		movesToPack.clear();
		board.getAllPossibleMoves(new DiceRoll(3, 3), movesToPack);
		assertTrue(movesToPack.isEmpty());
		
		board.makeMove(BackgammonColor.White, "33:");
		board.makeMove(BackgammonColor.Black, "65: 9/4 6/0");
		board.makeMove(BackgammonColor.White, "64: 25/19 19/15");
		board.makeMove(BackgammonColor.Black, "43: 25/21 21/18");
		board.makeMove(BackgammonColor.White, "41: 6/5 6/2");
		board.makeMove(BackgammonColor.Black, "62: 18/16 16/10");
		
		movesToPack.clear();
		board.getAllPossibleMoves(new DiceRoll(4, 3), movesToPack);
		assertTrue(movesToPack.isEmpty());
		
		board.makeMove(BackgammonColor.White, "43:");
		assertTrue(board.isMoveValid(new BackgammonMove(BackgammonColor.Black, "66: 10/4 5/0 5/0 4/0")));
		board.makeMove(BackgammonColor.Black, "66: 10/4 5/0 5/0 4/0");
		
		movesToPack.clear();
		board.getAllPossibleMoves(new DiceRoll(2, 1), movesToPack);
		assertTrue(movesToPack.isEmpty());
		
		board.makeMove(BackgammonColor.White, "21:");
		board.makeMove(BackgammonColor.Black, "61: 4/3 4/0");
		board.makeMove(BackgammonColor.White, "53: 25/20 20/17");
		board.makeMove(BackgammonColor.Black, "21: 1/0 2/0");
		
		assertTrue(board.canMakeAMove(new DiceRoll(5, 3).getAvailableMoves()));
		board.makeMove(BackgammonColor.White, "53: 17/14 14/9");
		board.makeMove(BackgammonColor.Black, "31: 1/0 3/0");
		board.makeMove(BackgammonColor.White, "65: 10/5 10/4");
		board.makeMove(BackgammonColor.Black, "55: 4/0 4/0 3/0 3/0");
		board.makeMove(BackgammonColor.White, "41: 9/5 1/0");
		
		assertTrue(board.canMakeAMove(new DiceRoll(2, 1).getAvailableMoves()));
		board.makeMove(BackgammonColor.Black, "21: 1/0 2/0");
		
		assertEquals(0, board.getPiecesNotFinished(BackgammonColor.Black));
		assertEquals(15, board.getPiecesFinished(BackgammonColor.Black));
		
		assertEquals(14, board.getPiecesNotFinished(BackgammonColor.White));
		assertEquals(1, board.getPiecesFinished(BackgammonColor.White));
	}
	
	@Test
	public void testGetAllPossibleMoves()
	{
		BackgammonBoard board = new BackgammonBoard();
		
		ArrayList<BackgammonMove> movesToPack = new ArrayList<>();
		DiceRoll diceRoll = new DiceRoll(1, 2);
		board.getAllPossibleMoves(diceRoll, movesToPack);
		
		assertEquals(30, movesToPack.size());
		
		for (BackgammonMove backgammonMove : movesToPack)
		{
			boolean moveValid = board.isMoveValid(backgammonMove);
			if (!moveValid)
			{
				System.out.println("Move isn't valid: " + backgammonMove);
			}
			assertTrue(moveValid);
		}
	}
	
	@Test
	public void testGetAllPossibleMovesTwo()
	{
		BackgammonBoard board = new BackgammonBoard(BackgammonColor.Black, "1B/0W  ___ 1W  ___ ___ 1B  7W  1W  ___ 1B  1W  1W  5B  1W  ___ ___ ___ 2B  1W  2B  1B  1W  1W  2B  ___ 0B/0W");
		
		ArrayList<BackgammonMove> movesToPack = new ArrayList<>();
		DiceRoll diceRoll = new DiceRoll(5, 3);
		board.getAllPossibleMoves(diceRoll, movesToPack);
		
		assertEquals(12, movesToPack.size());
		
		for (BackgammonMove backgammonMove : movesToPack)
		{
			boolean moveValid = board.isMoveValid(backgammonMove);
			if (!moveValid)
			{
				System.out.println("Move isn't valid: " + backgammonMove);
			}
			assertTrue(moveValid);
		}
	}
	
	@Test
	public void testGetAllPossibleMovesThree()
	{
		BackgammonBoard board = new BackgammonBoard(BackgammonColor.Black, "0B/0W  ___ 2W  ___ ___ 1W  ___ ___ ___ ___ ___ ___ ___ 2W  2W  1W  ___ ___ ___ 3W  1W  3W  6B  4B  5B  0B/0W  ");
		
		ArrayList<BackgammonMove> movesToPack = new ArrayList<>();
		DiceRoll diceRoll = new DiceRoll(5, 3);
		board.getAllPossibleMoves(diceRoll, movesToPack);
		
		assertEquals(3, movesToPack.size());

		for (BackgammonMove backgammonMove : movesToPack)
		{
			boolean moveValid = board.isMoveValid(backgammonMove);
			if (!moveValid)
			{
				System.out.println("Move isn't valid: " + backgammonMove);
			}
			assertTrue(moveValid);
		}
	}
	
	@Test
	public void testGetAllPossibleMovesFour()
	{
		BackgammonBoard board = new BackgammonBoard(BackgammonColor.White, "1W/0B  6B  2W  ___ 2B  2W  2B  ___ 2B  ___ ___ ___ ___ 1W  ___ ___ ___ 1B  ___ 5W  ___ ___ 1W  2B  3W  0W/0B ");
		
		ArrayList<BackgammonMove> movesToPack = new ArrayList<>();
		DiceRoll diceRoll = new DiceRoll(5, 3);
		board.getAllPossibleMoves(diceRoll, movesToPack);
		
		assertEquals(7, movesToPack.size());

		for (BackgammonMove backgammonMove : movesToPack)
		{
			boolean moveValid = board.isMoveValid(backgammonMove);
			if (!moveValid)
			{
				System.out.println("Move isn't valid: " + backgammonMove);
			}
			assertTrue(moveValid);
		}
	}
	
	@Test
	public void testGetAllPossibleMovesFive()
	{
		BackgammonBoard board = new BackgammonBoard(BackgammonColor.White, "1W/0B  1W  6B  2W  1B  1B  2B  ___ ___ 2B  ___ 1B  ___ 1B  ___ ___ 1W  1W  ___ 6W  1B  1W  ___ 2W  ___ 0W/0B ");
		
		ArrayList<BackgammonMove> movesToPack = new ArrayList<>();
		DiceRoll diceRoll = new DiceRoll(3, 5);
		board.getAllPossibleMoves(diceRoll, movesToPack);
		
		assertEquals(10, movesToPack.size());

		for (BackgammonMove backgammonMove : movesToPack)
		{
			boolean moveValid = board.isMoveValid(backgammonMove);
			if (!moveValid)
			{
				System.out.println("Move isn't valid: " + backgammonMove);
			}
			assertTrue(moveValid);
		}
	}
	
	@Test
	public void testGetAllPossibleMovesSix()
	{
		BackgammonBoard board = new BackgammonBoard(BackgammonColor.White, "1W/0B  3B  ___ 2B  1B  1W  2B  ___ 1B  ___ ___ ___ 5W  4B  ___ ___ ___ 2W  ___ 2W  ___ ___ 1W  3W  2B  0W/0B ");
		
		ArrayList<BackgammonMove> movesToPack = new ArrayList<>();
		DiceRoll diceRoll = new DiceRoll(2, 1);
		board.getAllPossibleMoves(diceRoll, movesToPack);
		
		assertEquals(3, movesToPack.size());

		for (BackgammonMove backgammonMove : movesToPack)
		{
//			System.out.println(backgammonMove);

			boolean moveValid = board.isMoveValid(backgammonMove);
			if (!moveValid)
			{
				System.err.println("Move isn't valid: " + backgammonMove);
				System.err.println(board);
			}
			assertTrue(moveValid);
		}
	}
	
	@Test
	public void testTroublesomeMove()
	{
		BackgammonBoard board = new BackgammonBoard(BackgammonColor.Black, "0B/0W  ___ 2W  ___ ___ 1W  ___ ___ ___ ___ ___ ___ ___ 2W  2W  1W  ___ ___ ___ 3W  1W  3W  6B  4B  5B  0B/0W  ");
		
		DiceRoll diceRoll = new DiceRoll(2, 1);
		BackgammonMove backgammonMove = new BackgammonMove(BackgammonColor.Black, diceRoll);
		backgammonMove.addMove(2, 1);
		backgammonMove.addMove(1, 0);
		
		boolean moveValid = board.isMoveValid(backgammonMove);
		assertFalse(moveValid);
		
		try
		{
			board.makeMove(backgammonMove);
			fail("Need to make exact moves while we can");
		}
		catch(Exception e)
		{
			
		}
	}
	
	
	@Test
   public void testTroublesomeMoveAtEnd()
   {
      BackgammonBoard board = new BackgammonBoard(BackgammonColor.White, "0W/0B  ___ ___ 3B  1B  ___ ___ ___ ___ ___ ___ ___ ___ ___ ___ ___ ___ ___ ___ ___ ___ ___ 2W  7W  4W  2W/11B");
      
      DiceRoll diceRoll = new DiceRoll(4, 4);
      BackgammonMove backgammonMove = new BackgammonMove(BackgammonColor.White, diceRoll);
      backgammonMove.addMove(5, 1);
      backgammonMove.addMove(3, 0);
      backgammonMove.addMove(3, 0);
      backgammonMove.addMove(3, 0);
      
      boolean moveValid = board.isMoveValid(backgammonMove);
      assertFalse(moveValid);
      
      try
      {
         board.makeMove(backgammonMove);
         fail("Need to make exact moves while we can");
      }
      catch(Exception e)
      {
         
      }
   }
	
	
	@Test 
	public void testThatPlayerMakesAMoveIfTheyCan()
	{
		BackgammonBoard board = new BackgammonBoard();

		BackgammonMove backgammonMove = new BackgammonMove(BackgammonColor.White, new DiceRoll(3, 4));
		assertFalse(board.isMoveValid(backgammonMove));
		
		backgammonMove.addMove(24, 21);
		assertFalse(board.isMoveValid(backgammonMove));
		
		backgammonMove.addMove(24, 20);
		assertTrue(board.isMoveValid(backgammonMove));
	}
	
	@Test
	public void testStringConstructor()
	{
		// Black is going to the right here, white is going to the left.
		// Both bars are on the left and both finished spots are on the right.
		String string = "1B/0W  ___ 1W  1W  1W  ___ 3W  1B  1W  ___ ___ ___ 4B  4W  ___ ___ ___ 4B  ___ 2B  1W  2W  1B  ___ ___ 2B/1W  ";
		
		BackgammonBoard board = new BackgammonBoard(BackgammonColor.Black, string);
//		System.out.println(board);

		assertEquals(1, board.getPiecesFinished(BackgammonColor.White));
		assertEquals(2, board.getPiecesFinished(BackgammonColor.Black));
		
		assertEquals(0, board.getPiecesOnBar(BackgammonColor.White));
		assertEquals(1, board.getPiecesOnBar(BackgammonColor.Black));

		assertEquals(1, board.getPieces(BackgammonColor.White, 3));
		assertEquals(2, board.getPieces(BackgammonColor.Black, 6));
		
		assertEquals(string, board.toStringWithoutLabel());
	}
	
}
