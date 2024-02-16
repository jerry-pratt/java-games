package rocks.jerryPratt.javaGames.tdGammon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class BackgammonBoard 
{
	private static final int barPosition = 25;
	private static final int finishedPosition = 0;
	
	private final EnumMap<BackgammonColor, int[]> pieces = new EnumMap<>(BackgammonColor.class);
	
	private BackgammonColor whoseTurn = BackgammonColor.White;
	
	private final int totalPieces;
	
	public BackgammonBoard()
	{
		int[] whitePieces = new int[26];
		int[] blackPieces = new int[26];
		
		pieces.put(BackgammonColor.White, whitePieces);
		pieces.put(BackgammonColor.Black, blackPieces);
		
		whitePieces[24] = 2;
		whitePieces[13] = 5;
		whitePieces[8] = 3;
		whitePieces[6] = 5;
		
		blackPieces[24] = 2;
		blackPieces[13] = 5;
		blackPieces[8] = 3;
		blackPieces[6] = 5;
		
		totalPieces = 15;
	}
	
	public BackgammonBoard(BackgammonColor whoseTurn, String backgammonString)
	{
		this.whoseTurn = whoseTurn;
				
		int[] whitePieces = new int[26];
		int[] blackPieces = new int[26];
		
		pieces.put(BackgammonColor.White, whitePieces);
		pieces.put(BackgammonColor.Black, blackPieces);
		
		StringTokenizer tokenizer = new StringTokenizer(backgammonString, " ");
		
		String barToken = tokenizer.nextToken();
		EnumMap<BackgammonColor, Integer> decode = decode(barToken);
		
		pieces.get(BackgammonColor.White)[barPosition] = decode.get(BackgammonColor.White);
		pieces.get(BackgammonColor.Black)[barPosition] = decode.get(BackgammonColor.Black);
		
		int position = 24;
		while(tokenizer.hasMoreTokens() && position > 0)
		{
			String nextToken = tokenizer.nextToken();

			if (nextToken.contains("_")) 
			{
//				System.out.println(position);
				position--;
				continue;
			}

			int numberAt = parseNextInteger(nextToken);
			BackgammonColor colorAt = parseNextColor(nextToken);
			
			if (colorAt == whoseTurn)
			{
				pieces.get(colorAt)[position] = numberAt;
			}
			
			else
			{
				pieces.get(colorAt)[25 - position] = numberAt;
			}
			
//			System.out.println(position + " : " + numberAt + " " + colorAt);
			position--;
		}
		
		String finishedToken = tokenizer.nextToken();

		decode = decode(finishedToken);
		
		pieces.get(BackgammonColor.White)[finishedPosition] = decode.get(BackgammonColor.White);
		pieces.get(BackgammonColor.Black)[finishedPosition] = decode.get(BackgammonColor.Black);
		
		totalPieces = 15;
		
		checkRepInvariant();
	}
	
	private EnumMap<BackgammonColor, Integer> decode(String barToken) 
	{
		EnumMap<BackgammonColor, Integer> decoded = new EnumMap<BackgammonColor, Integer>(BackgammonColor.class);

		StringTokenizer tokenizer = new StringTokenizer(barToken, "/");
		String nextToken = tokenizer.nextToken();

		int numberAt = parseNextInteger(nextToken);
		BackgammonColor colorAt = parseNextColor(nextToken);
		
		decoded.put(colorAt, numberAt);
		
		nextToken = tokenizer.nextToken();
		
		numberAt = parseNextInteger(nextToken);
		colorAt = parseNextColor(nextToken);
		
		decoded.put(colorAt, numberAt);
		
		return decoded;
	}
	
	private int parseNextInteger(String string)
	{
		int numberOfDigits = getNumberOfStartingDigits(string);
		
		int numberAt = Integer.parseInt(string.substring(0, numberOfDigits));
		return numberAt;
	}

	private int getNumberOfStartingDigits(String string) 
	{
		int numberOfDigits = 0;
		
		if (Character.isDigit(string.charAt(0))) numberOfDigits++;
		else throw new RuntimeException("Should start with a digit!");
		
		if (Character.isDigit(string.charAt(1))) numberOfDigits++;
		return numberOfDigits;
	}
	
	private BackgammonColor parseNextColor(String string)
	{
		int numberOfDigits = getNumberOfStartingDigits(string);

		BackgammonColor colorAt = BackgammonColor.parseColor(string.substring(numberOfDigits, numberOfDigits+1));
		
		return colorAt;
	}

	public BackgammonBoard(BackgammonBoard backgammonBoard) 
	{
		int[] whitePieces = new int[26];
		int[] blackPieces = new int[26];
		
		pieces.put(BackgammonColor.White, whitePieces);
		pieces.put(BackgammonColor.Black, blackPieces);
		
		whoseTurn = backgammonBoard.whoseTurn;
		
		for (BackgammonColor color : BackgammonColor.values())
		{
			int[] newPieces = pieces.get(color);
			int[] piecesToCopy = backgammonBoard.pieces.get(color);
			
			for (int i=0; i<newPieces.length; i++)
			{
				newPieces[i] = piecesToCopy[i];
			}
		}
		
		totalPieces = backgammonBoard.totalPieces;
	}
	
	public static BackgammonBoard createEndGameOneBoard() 
	{
		BackgammonBoard board = new BackgammonBoard();
		
		int[] whitePieces = board.pieces.get(BackgammonColor.White);
		int[] blackPieces = board.pieces.get(BackgammonColor.Black);
	
		for (int i=0; i<26; i++)
		{
			whitePieces[i] = 0;
			blackPieces[i] = 0;
		}
		
		whitePieces[finishedPosition] = 14;
		blackPieces[finishedPosition] = 14;

		whitePieces[1] = 1;
		blackPieces[1] = 1;
		
		return board;
	}
	
	public static BackgammonBoard createEndGameTwoBoard() 
	{
		BackgammonBoard board = new BackgammonBoard();
		int[] whitePieces = board.pieces.get(BackgammonColor.White);
		int[] blackPieces = board.pieces.get(BackgammonColor.Black);
		
		for (int i=0; i<26; i++)
		{
			whitePieces[i] = 0;
			blackPieces[i] = 0;
		}
		
		whitePieces[finishedPosition] = 10;
		blackPieces[finishedPosition] = 0;

		whitePieces[1] = 1;
		whitePieces[3] = 2;
		whitePieces[6] = 2;
		
		blackPieces[1] = 3;
		blackPieces[2] = 3;
		blackPieces[3] = 5;
		blackPieces[6] = 3;
		blackPieces[7] = 1;
		
		return board;
	}

	public BackgammonColor getWhoseTurn()
	{
		return whoseTurn;
	}

	public int[] getPieces(BackgammonColor color)
	{
		return pieces.get(color);
	}
	
	public int getPieces(BackgammonColor color, int boardPosition)
	{
		return pieces.get(color)[boardPosition];
	}
	
	public int getPiecesOnBar(BackgammonColor color)
	{
		return getPieces(color, barPosition);
	}
	
	public int getPiecesFinished(BackgammonColor color) 
	{
		return pieces.get(color)[finishedPosition];
	}
	
	public int getPiecesNotFinished(BackgammonColor color) 
	{
		int piecesNotFinished = 0;
		
		int[] pieces = this.pieces.get(color);
		
		for (int i=1; i<pieces.length; i++)
		{
			piecesNotFinished+= pieces[i];
		}
		return piecesNotFinished;
	}
	
	public void checkRepInvariant()
	{
		int totalWhitePieces = getPiecesFinished(BackgammonColor.White) + getPiecesNotFinished(BackgammonColor.White);
		int totalBlackPieces = getPiecesFinished(BackgammonColor.Black) + getPiecesNotFinished(BackgammonColor.Black);
		
		if (totalWhitePieces != totalPieces) throw new RuntimeException();
		if (totalBlackPieces != totalPieces) throw new RuntimeException();
		
		int[] whitePieces = pieces.get(BackgammonColor.White);
		int[] blackPieces = pieces.get(BackgammonColor.Black);
		
		for (int i=1; i<whitePieces.length-1; i++)
		{
			if ((whitePieces[i] != 0) && (blackPieces[25-i] != 0)) throw new RuntimeException();
			
		}
	}

	public void makeMove(BackgammonMove backgammonMove) 
	{		
		if (!isMoveInExactDiceOrderValid(backgammonMove))
		{
			backgammonMove = backgammonMove.reverseDiceRoll();
			
			if (!isMoveInExactDiceOrderValid(backgammonMove))
				throwExceptionForInvalidMove(backgammonMove);
		}
		
		BackgammonColor movingColor = backgammonMove.getColor();
		if (movingColor != whoseTurn) throw new RuntimeException("Invalid Move. Not " + movingColor + " turn.");
		
		DiceRoll diceRoll = backgammonMove.getDiceRoll();
		
		int[] movingPieces = pieces.get(movingColor);
		int[] otherPieces = pieces.get(movingColor.getOppositeColor());
		
		int numberOfPieceMoves = backgammonMove.getNumberOfPieceMoves();
		LinkedList<BackgammonIndividualPieceMove> pieceMoves = backgammonMove.getPieceMoves();
		
		for (BackgammonIndividualPieceMove pieceMove : pieceMoves)
		{			
			int fromPosition = pieceMove.getFromPosition();
			int toPosition = pieceMove.getToPosition();
			
			makeSingleMove(movingColor, fromPosition, toPosition);
		}
		
		this.whoseTurn = whoseTurn.getOppositeColor();
	}
	
	

	public boolean isMoveValid(BackgammonMove backgammonMove) 
	{
		if (isMoveInExactDiceOrderValid(backgammonMove)) return true;
		return (isMoveInExactDiceOrderValid(backgammonMove.reverseDiceRoll()));
	}
	
	public boolean isMoveInExactDiceOrderValid(BackgammonMove backgammonMove)
	{
		BackgammonColor movingColor = backgammonMove.getColor();
		if (movingColor != whoseTurn) return false;
		
		DiceRoll diceRoll = backgammonMove.getDiceRoll();
		LinkedList<Integer> remainingDice = diceRoll.getAvailableMoves();
		
		int numberOfPieceMoves = backgammonMove.getNumberOfPieceMoves();
		LinkedList<BackgammonIndividualPieceMove> pieceMoves = backgammonMove.getPieceMoves();
		
		if (numberOfPieceMoves > remainingDice.size()) return false; // Too many moves for what you rolled.
		
		return recursiveIsMoveValid(this, remainingDice, pieceMoves);
	}
	
	private void throwExceptionForInvalidMove(BackgammonMove backgammonMove) 
	{
		BackgammonColor movingColor = backgammonMove.getColor();
		if (movingColor != whoseTurn) throw new RuntimeException("Invalid Move. Not " + movingColor + " turn.");
		
		DiceRoll diceRoll = backgammonMove.getDiceRoll();
		LinkedList<Integer> remainingDice = diceRoll.getAvailableMoves();
		
		int numberOfPieceMoves = backgammonMove.getNumberOfPieceMoves();
		LinkedList<BackgammonIndividualPieceMove> pieceMoves = backgammonMove.getPieceMoves();
		
		if (numberOfPieceMoves > remainingDice.size()) throw new RuntimeException("Too many moves for what you rolled.");
		
		 recursiveThrowExceptionForInvalidMove(this, remainingDice, pieceMoves);
		
	}

	private static LinkedList<Integer> copyOne(LinkedList<Integer> listToCopy)
	{
		LinkedList<Integer> listToReturn = new LinkedList<>();
		
		for (Integer integer : listToCopy)
		{
			listToReturn.add(integer);
		}
		
		return listToReturn;
	}
	
	private static LinkedList<BackgammonIndividualPieceMove> copyTwo(LinkedList<BackgammonIndividualPieceMove> movesToCopy)
	{
		LinkedList<BackgammonIndividualPieceMove> listToReturn = new LinkedList<>();
		
		for (BackgammonIndividualPieceMove moveToCopy : movesToCopy)
		{
			BackgammonIndividualPieceMove integerArray = new BackgammonIndividualPieceMove(moveToCopy);
			listToReturn.add(integerArray);
		}
		
		return listToReturn;
	}
	
	public static boolean recursiveIsMoveValid(BackgammonBoard board, LinkedList<Integer> remainingDiceExactUseOrder, LinkedList<BackgammonIndividualPieceMove> remainingPieceMoves)
	{
		remainingDiceExactUseOrder = copyOne(remainingDiceExactUseOrder);
		remainingPieceMoves = copyTwo(remainingPieceMoves);
		
		if (remainingPieceMoves.isEmpty())
		{
			if (remainingDiceExactUseOrder.isEmpty()) return true;
			
			else
			{
				// Check that unused dice cannot be used.
				if (board.canMakeAMove(remainingDiceExactUseOrder)) return false;
				return true;
			}
		}
		
		BackgammonIndividualPieceMove pieceMove = remainingPieceMoves.removeFirst();
		int fromPosition = pieceMove.getFromPosition();
		int toPosition = pieceMove.getToPosition();
		
		int diceToUse = remainingDiceExactUseOrder.getFirst();

		// Check to make sure that a die can make that move validly:
		int moveDistance = fromPosition - toPosition;

		boolean canMakeMoveExactly = (diceToUse == moveDistance);

		// Can only make a move with the wrong number if bearing off, and if it is greater than what is needed, and if no other moves are possible first.
		if (!canMakeMoveExactly) 
		{
			if (toPosition != finishedPosition) return false;
			if (moveDistance > diceToUse) return false;
			if (board.areAnyExactMovesPossible(remainingDiceExactUseOrder)) return false;
		}
			

		if (!isSingleMoveValid(board, board.getWhoseTurn(), fromPosition, toPosition)) return false;
		
		board = new BackgammonBoard(board);
		board.makeSingleMove(board.getWhoseTurn(), fromPosition, toPosition);
		
		remainingDiceExactUseOrder.removeFirst();
		return recursiveIsMoveValid(board, remainingDiceExactUseOrder, remainingPieceMoves);
	}
	
	private static void recursiveThrowExceptionForInvalidMove(BackgammonBoard board, LinkedList<Integer> remainingDiceExactUseOrder, LinkedList<BackgammonIndividualPieceMove> remainingPieceMoves) 
	{
		if (remainingPieceMoves.isEmpty())
		{
			if (remainingDiceExactUseOrder.isEmpty()) throw new RuntimeException("This move is not invalid!");
			
			else
			{
				// Check that unused dice cannot be used.
				if (board.canMakeAMove(remainingDiceExactUseOrder)) throw new RuntimeException("Possible Move Exists!");
				return;
			}
		}
		
		BackgammonIndividualPieceMove pieceMove = remainingPieceMoves.removeFirst();
		int fromPosition = pieceMove.getFromPosition();
		int toPosition = pieceMove.getToPosition();
		
		int diceToUse = remainingDiceExactUseOrder.removeFirst();

		// Check to make sure that a die can make that move validly:
		int moveDistance = fromPosition - toPosition;

		boolean canMakeMoveExactly = remainingDiceExactUseOrder.contains(moveDistance);

		// Can only make a move with the wrong number if bearing off, and if it is greater than what is needed.
		if (!canMakeMoveExactly) 
		{
			if (toPosition != finishedPosition) throw new RuntimeException("Must make a move exactly unless it is to the finished position!");
			if (moveDistance > diceToUse) throw new RuntimeException("Not high enough of a number to bear off!");
			if (board.areAnyExactMovesPossible(remainingDiceExactUseOrder)) throw new RuntimeException("Other moves are possible before bearing off without the exact number!");
		}

		if (!isSingleMoveValid(board, board.getWhoseTurn(), fromPosition, toPosition))
		{
			throwExceptionForInvalidSingleMove(board, board.getWhoseTurn(), fromPosition, toPosition);
		}
		
		board = new BackgammonBoard(board);
		board.makeSingleMove(board.getWhoseTurn(), fromPosition, toPosition);
		recursiveThrowExceptionForInvalidMove(board, remainingDiceExactUseOrder, remainingPieceMoves);
	}

	public boolean canMakeAMove(LinkedList<Integer> diceAvailable) 
	{
		for (int dice : diceAvailable)
		{
			for (int fromPosition = 1; fromPosition<26; fromPosition++)
			{
				int toPosition = fromPosition - dice;
				if (toPosition < 0) toPosition = 0;
				
				if (BackgammonBoard.isSingleMoveValid(this, this.getWhoseTurn(), fromPosition, toPosition)) return true;
			}
		}
		
		return false;
	}

	public boolean areAnyExactMovesPossible(LinkedList<Integer> remainingDiceExactUseOrder) 
	{
		int[] myPieces = pieces.get(whoseTurn);
		int[] opposingPieces = pieces.get(whoseTurn.getOppositeColor());
		
		for (int dice : remainingDiceExactUseOrder)
		{
			for (int fromPosition = 1; fromPosition<25; fromPosition++)
			{
				if (myPieces[fromPosition] < 1) continue;
				
				int toPosition = fromPosition - dice;
				if (toPosition < 0) continue;
				
				if (toPosition == 0) return true;
				if (opposingPieces[25-toPosition] < 2) return true;
			}
		}
		
		return false;
	}

	private void makeSingleMove(BackgammonColor movingColor, int fromPosition, int toPosition) 
	{
		if (isSingleMoveValid(this, movingColor, fromPosition, toPosition))
		{		
			int[] movingPieces = pieces.get(movingColor);
			int[] otherPieces = pieces.get(movingColor.getOppositeColor());
			
			movingPieces[fromPosition]--;
			movingPieces[toPosition]++;
			
			if (otherPieces[25-toPosition] == 1)
			{
				otherPieces[25-toPosition] = 0;
				otherPieces[barPosition]++;
			}
		}
		
		else
		{
			throwExceptionForInvalidSingleMove(this, movingColor, fromPosition, toPosition);
		}
	}

	public static boolean isSingleMoveValid(BackgammonBoard board, BackgammonColor movingColor, int fromPosition, int toPosition)
	{ 
		if (movingColor != board.getWhoseTurn()) return false;

		int[] movingPieces = board.pieces.get(movingColor);
		int[] otherPieces = board.pieces.get(movingColor.getOppositeColor());
		
		if (fromPosition < 1) return false;
		if (toPosition < 0) return false;
		if (fromPosition > 25) return false;
		if (toPosition > 25) return false;
		
		int numberOfSpaces = fromPosition - toPosition;
		if (numberOfSpaces < 1) return false;
		if (numberOfSpaces > 6) return false;
		
		if ((movingPieces[barPosition] != 0) && fromPosition != barPosition) return false;
		if (movingPieces[fromPosition] < 1) return false;
		if ((toPosition != 0) && (otherPieces[25-toPosition] > 1)) return false;
		
		if (toPosition == 0)
		{
			for (int i=7; i<26; i++)
			{
				if (movingPieces[i] > 0) return false; // Can't move off until all of your pieces are in the first 6 spaces.
			}
		}
		
		return true;
	}
	
	public static void throwExceptionForInvalidSingleMove(BackgammonBoard board, BackgammonColor movingColor, int fromPosition, int toPosition)
	{		
		if (movingColor != board.getWhoseTurn()) throw new RuntimeException("Invalid Move. Not " + movingColor + " turn. It is " + board.getWhoseTurn() + " turn");

		int[] movingPieces = board.pieces.get(movingColor);
		int[] otherPieces = board.pieces.get(movingColor.getOppositeColor());
		
		if (fromPosition < 1) throw new RuntimeException("From Position must be >= 1.");
		if (toPosition < 0) throw new RuntimeException("To Position must be >= 0");
		if (fromPosition > 25) throw new RuntimeException("From Position must be <= 25");
		if (toPosition > 25) throw new RuntimeException("To Position must be <= 25");
		
		int numberOfSpaces = fromPosition - toPosition;
		if (numberOfSpaces < 1) throw new RuntimeException("Must roll at least a 1");
		if (numberOfSpaces > 6) throw new RuntimeException("Must roll less than a 6");
		
		if ((movingPieces[barPosition] != 0) && fromPosition != barPosition) throw new RuntimeException("Must bar off first!");
		if (movingPieces[fromPosition] < 1) throw new RuntimeException("No piece to move from that position!");
		if (otherPieces[25-toPosition] > 1) throw new RuntimeException("Cannot move to a defenders blot!");
		
		if (toPosition == 0)
		{
			for (int i=7; i<26; i++)
			{
				if (movingPieces[i] > 0) throw new RuntimeException("Can't move off until all of your pieces are in the first 6 spaces.");
			}
		}
	}

	public void makeMove(BackgammonColor color, String moveString) 
	{
		BackgammonMove backgammonMove = new BackgammonMove(color, moveString);		
		this.makeMove(backgammonMove);
	}
	
	public void getAllPossibleMoves(DiceRoll diceRoll, ArrayList<BackgammonMove> movesToPack)
	{
		LinkedList<Integer> availableMoves = diceRoll.getAvailableMoves();
		if (diceRoll.rolledDoubles())
		{
			getAllPossibleMovesRecursively(this, new BackgammonMove(whoseTurn, diceRoll), movesToPack, availableMoves);
		}
		else
		{
			// When the dice are different, the dice ordering sometimes matter. Let's just assume it always matters, even if the board state ends up being identical in the end...
			getAllPossibleMovesRecursively(this, new BackgammonMove(whoseTurn, diceRoll), movesToPack, availableMoves);
			LinkedList<Integer> reversedAvailableMoves = new LinkedList<Integer>();
			reversedAvailableMoves.addAll(availableMoves);
			Collections.reverse(reversedAvailableMoves);
			
			getAllPossibleMovesRecursively(this, new BackgammonMove(whoseTurn, diceRoll), movesToPack, reversedAvailableMoves);
		}
	}

	private static void getAllPossibleMovesRecursively(BackgammonBoard backgammonBoard, BackgammonMove moveToThisPoint, 
			ArrayList<BackgammonMove> movesToPack, LinkedList<Integer> remainingDiceExactUseOrder) 
	{		
		if (remainingDiceExactUseOrder.isEmpty())
		{
			if (moveToThisPoint.getNumberOfPieceMoves() > 0)
			{
				movesToPack.add(moveToThisPoint);
			}
			return;
		}
				
		BackgammonColor movingColor = backgammonBoard.whoseTurn;
		int[] movingPieces = backgammonBoard.pieces.get(movingColor);
			
		boolean canMakeAMove = false;
		
		int dieToUse = remainingDiceExactUseOrder.getFirst();
		
		for (int fromPosition=1; fromPosition<26; fromPosition++)
		{
			// If you need to bar off, you must do that first...
			if ((movingPieces[barPosition] > 0) && (fromPosition != barPosition)) continue;
			
			if (movingPieces[fromPosition] > 0)
			{
				int toPosition = fromPosition - dieToUse;
				if (toPosition < 0)
				{
					if (backgammonBoard.areAnyExactMovesPossible(remainingDiceExactUseOrder)) continue;
					toPosition = 0;
				}


				if (isSingleMoveValid(backgammonBoard, movingColor, fromPosition, toPosition))
				{
					BackgammonBoard newBoard = new BackgammonBoard(backgammonBoard);
					BackgammonMove newMoveToThisPoint = new BackgammonMove(moveToThisPoint);
					newMoveToThisPoint.addMove(fromPosition, toPosition);
					newBoard.makeSingleMove(movingColor, fromPosition, toPosition);

					
					LinkedList<Integer> newRemainingDiceExactUseOrder = new LinkedList<>();
					newRemainingDiceExactUseOrder.addAll(remainingDiceExactUseOrder);
					newRemainingDiceExactUseOrder.removeFirst();

					canMakeAMove = true;
					getAllPossibleMovesRecursively(newBoard, newMoveToThisPoint, movesToPack, newRemainingDiceExactUseOrder);
				}
			}
		}
		
		if (!canMakeAMove)
		{
			// Check that unused dice cannot be used.
			if (backgammonBoard.canMakeAMove(remainingDiceExactUseOrder)) return;
			
			// If you can't make a move with one of the dice, contiue, but make sure you can't make a move with any of the other dice...
			BackgammonBoard newBoard = new BackgammonBoard(backgammonBoard);
			BackgammonMove newMoveToThisPoint = new BackgammonMove(moveToThisPoint);
			
			LinkedList<Integer> newRemainingDiceExactUseOrder = new LinkedList<>();
			newRemainingDiceExactUseOrder.addAll(remainingDiceExactUseOrder);
			newRemainingDiceExactUseOrder.removeFirst();

			getAllPossibleMovesRecursively(newBoard, newMoveToThisPoint, movesToPack, newRemainingDiceExactUseOrder);
		}
	}

	public boolean isGameFinished() 
	{
		if (getPiecesFinished(BackgammonColor.White) == totalPieces) return true;
		if (getPiecesFinished(BackgammonColor.Black) == totalPieces) return true;
		
		return false;
	}
	
	public BackgammonColor getWinner() 
	{
		if (getPiecesFinished(BackgammonColor.White) == totalPieces) return BackgammonColor.White;
		if (getPiecesFinished(BackgammonColor.Black) == totalPieces) return BackgammonColor.Black;
		
		return null;
	}
	
	public String toStringWithoutLabel()
	{
		int[] myPieces = pieces.get(whoseTurn);
		int[] opponentsPieces = pieces.get(whoseTurn.getOppositeColor());

		String myLetter = whoseTurn.getLetter();
		String opponentsLetter = whoseTurn.getOppositeColor().getLetter();
		
		String ret = myPieces[barPosition] + myLetter + "/" + opponentsPieces[barPosition] + opponentsLetter + "  ";
		
		for (int i=24; i>0; i--)
		{
			if (myPieces[i] > 0)
			{
				ret = ret + myPieces[i] + myLetter + "  ";
			}
			
			else if (opponentsPieces[25 - i] > 0)
			{
				ret = ret + opponentsPieces[25 - i] + opponentsLetter + "  ";
			}
			
			else ret = ret + "___ ";
		}
		
		ret = ret + myPieces[finishedPosition] + myLetter + "/" + opponentsPieces[finishedPosition] + opponentsLetter + "  ";

		
		return ret;
	}

	public String toString() 
	{
		String ret = toStringWithoutLabel();
		ret = ret + "\n BAR   24  23  22  21  20  19  18  17  16  15  14  13  12  11  10   9   8   7   6   5   4   3   2   1   OFF";

		return ret;
	}

	

	
}
