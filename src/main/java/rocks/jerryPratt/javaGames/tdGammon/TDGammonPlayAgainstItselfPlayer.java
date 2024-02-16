package rocks.jerryPratt.javaGames.tdGammon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

public class TDGammonPlayAgainstItselfPlayer implements BackgammonPlayer
{
	private final Random random = new Random();
	private final TDGammon0InputVectorMapper mapper = new TDGammon0InputVectorMapper();
	
	private final TDGammonNeuralNetwork network;
	private boolean learningOn = true;
	
	int inputUnits = 198;
	int hiddenUnits = 40;
	
	private double alpha = 0.01; // Learning rate.
	private final double gamma = 1.0; // Reward discount rate. 1.0 since episodic.
	private final double lambda = 0.9; // Eligibility discount rate. The longer away a move happened, the less to blame or reward it for an outcome.
		
	public TDGammonPlayAgainstItselfPlayer(double alpha)
	{
		this.alpha = alpha;
		network = new TDGammonNeuralNetwork(alpha, lambda, gamma, inputUnits, hiddenUnits);
	}
	
	public TDGammonNeuralNetwork getNeuralNetwork()
	{
		return network;
	}
	
	public void setAlpha(double alpha)
	{
		this.alpha = alpha;
		network.setAlpha(alpha);
	}
	
	public void setLearningOn(boolean learningOn) 
	{
		this.learningOn = learningOn;
	}
	
	private void updateNeuralNetwork(BackgammonBoard board, BackgammonBoard nextBoard)
	{
		double nextBoardValue = computeEstimatedChanceOfWhiteWinning(nextBoard);		
		double bootstrapValue = gamma * nextBoardValue; // Reward is in computeBoardScore()...
		
		double[] stateVector = computeInputVector(board);
		network.updateNeuralNetwork(stateVector, bootstrapValue);
	}
	
	@Override
	public void informThatOpponentMadeMove(BackgammonBoard originalBoard, BackgammonMove opponentMove, BackgammonBoard resultantBoard) 
	{
//		updateNeuralNetwork(originalBoard, resultantBoard);
	}
	
	public double computeEstimatedChanceOfWhiteWinning(BackgammonBoard backgammonBoard) 
	{	   
		if (backgammonBoard.isGameFinished())
		{
		   // This is where the reward is:
			if (backgammonBoard.getWinner() == BackgammonColor.White)
			{
				return 1.0;
			}
			else if (backgammonBoard.getWinner() == BackgammonColor.Black)
			{
				return 0.0;
			}
			else
			{
				throw new RuntimeException("Shouldn't get here!");
			}
		}
		else
		{
			double[] inputVector = computeInputVector(backgammonBoard);
			return network.computeValue(inputVector);
		}
	}

	private double[] computeInputVector(BackgammonBoard backgammonBoard) 
	{
		return mapper.generateInputVector(backgammonBoard);
	}
	
	
	@Override
	public void startANewGame(BackgammonBoard startingBoard)
	{
		if (this.learningOn)
		{
			network.resetEligibilityTraces();
		}
	}

	@Override
	public BackgammonMove chooseAMove(DiceRoll diceRoll, BackgammonBoard boardBeforeMyMove) 
	{
		BackgammonColor color = boardBeforeMyMove.getWhoseTurn();
		
//		if (boardBeforeMyMove.getWhoseTurn() != color) throw new RuntimeException("It's not " + color + " turn!");
		ArrayList<BackgammonMove> movesToPack = new ArrayList<>();
		boardBeforeMyMove.getAllPossibleMoves(diceRoll, movesToPack);

		if (movesToPack.isEmpty()) return new BackgammonMove(color, diceRoll);
				
		BackgammonMove bestMove = null;
		double bestScore = Double.NEGATIVE_INFINITY;
		
		for (BackgammonMove move : movesToPack)
		{
			BackgammonBoard newBoard = new BackgammonBoard(boardBeforeMyMove);
			newBoard.makeMove(move);
			
			double score = computeEstimatedChanceOfWhiteWinning(newBoard);
			if (color == BackgammonColor.Black) 
			{
				score = 1.0 - score;
			}
			
			if (score > bestScore)
			{
				bestScore = score;
				bestMove = move;
			}
		}
		
		BackgammonBoard boardAfterMyMove = new BackgammonBoard(boardBeforeMyMove);
		boardAfterMyMove.makeMove(bestMove);
		
		if (this.learningOn)
		{
			this.updateNeuralNetwork(boardBeforeMyMove, boardAfterMyMove);
		}

		return bestMove;
	}

	public void setWeightsToPreviouslyLearnedWeights()
	{
		// These weights were learned over the course of 200,000 games using the following parameters:
		// inputUnits = 198;
		// hiddenUnits = 40;

		// alpha = 0.01; // Learning rate. Multiplied by 0.9 every 500 games.
		// gamma = 1.0; // Reward discount rate. 1.0 since episodic.
		// lambda = 0.9; // Eligibility discount rate. The longer away a move happened, the less to blame or reward it for an outcome.

		this.network.setFirstLayerWeights(TDGammonLearnedWeights.getLearnedFirstLayerWeights());
		this.network.setFirstLayerBiases(TDGammonLearnedWeights.getLearnedFirstLayerBiases());

      this.network.setSecondLayerWeights(TDGammonLearnedWeights.getLearnedSecondLayerWeights());
      this.network.setSecondLayerBias(TDGammonLearnedWeights.getLearnedSecondLayerBias());
	}

	public static void main(String[] args) 
	{		
		double alpha = 0.01;
		TDGammonPlayAgainstItselfPlayer bothSidesPlayer = new TDGammonPlayAgainstItselfPlayer(alpha);
		
		// Comment out these lines to load from previously learned weights;
//		bothSidesPlayer.setWeightsToPreviouslyLearnedWeights();
//		bothSidesPlayer.setLearningOn(false);
		
		String string = "1B/0W  ___ ___  ___ ___  ___ ___  1B  ___  ___ ___ ___ 7B  ___  ___ ___ ___ 1B  ___ 2B  ___  ___  1B  ___ 1W 2B/14W  ";
		
		BackgammonBoard startingBoard = new BackgammonBoard();
		BackgammonBoard clearlyWhiteWinBoard = new BackgammonBoard(BackgammonColor.White, string);

		int gameNumber = 0;
		
		while(true)
		{
			BackgammonGame game = new BackgammonGame();
			game.setWhitePlayer(bothSidesPlayer);
			game.setBlackPlayer(bothSidesPlayer);
			
			boolean printGame = false;
			if (gameNumber % 1000 == 0) printGame = true;
			
			BackgammonColor winner = game.playAGame(false);
			gameNumber++;
			
			if (gameNumber % 100 == 0) System.out.print(gameNumber + " ");
			
			if (printGame)
			{
				double whiteWinPercentageOnStartingBoard = bothSidesPlayer.computeEstimatedChanceOfWhiteWinning(startingBoard);
				double whiteWinPercentageOnObviousWhiteWin = bothSidesPlayer.computeEstimatedChanceOfWhiteWinning(clearlyWhiteWinBoard);
				
				bothSidesPlayer.getNeuralNetwork().printWeights();

				System.out.println("Winner: " + winner);
				System.out.println("White Percentage on starting board = " + whiteWinPercentageOnStartingBoard);
				System.out.println("White Percentage on obvious white win = " + whiteWinPercentageOnObviousWhiteWin);
				
				System.out.println("alpha = " + alpha);
				alpha = alpha * 0.9;
				
				
//				TDGammonPlayAgainstItselfPlayer tunedPlayer = new TDGammonPlayAgainstItselfPlayer(0.0);
//				tunedPlayer.setWeightsToPreviouslyLearnedWeights();
//				tunedPlayer.setLearningOn(false);
//				
//				playSeriesAgainstARandomPlayer(tunedPlayer);
				
				bothSidesPlayer.setLearningOn(false);
//				bothSidesPlayer.setWeightsToPreviouslyLearnedWeights();
				playSeriesAgainstARandomPlayer(bothSidesPlayer);
				bothSidesPlayer.setLearningOn(true);
			}
		}
	}
	
	
	private static BackgammonSpriteWorld spriteWorldForEvaluation = null;
	private static void playSeriesAgainstARandomPlayer(BackgammonPlayer playerToEvaluate)
	{
		if (spriteWorldForEvaluation == null) spriteWorldForEvaluation = new BackgammonSpriteWorld();
				
		BackgammonGame game = new BackgammonGame();
		game.setSpriteWorld(spriteWorldForEvaluation);
		
		spriteWorldForEvaluation.setAnimateDiceRolls(false);
		game.setWhitePlayer(playerToEvaluate);
		
		RandomBackgammonPlayer randomPlayer = new RandomBackgammonPlayer(BackgammonColor.Black);
		game.setBlackPlayer(randomPlayer);
		
		int numberToAverage = 100;
		int numberWonAgainstRandom = 0;
		for (int i=0; i<numberToAverage; i++)
		{
			game.reset();
			
			BackgammonColor winnerAgainstRandom = game.playAGame(false);
			if (winnerAgainstRandom == BackgammonColor.White)
			{
				numberWonAgainstRandom++;
			}
			
			try 
			{
				Thread.sleep(10);
			} catch (InterruptedException e) 
			{
			}
		}
		
		System.out.println("Won " + numberWonAgainstRandom + " out of " + numberToAverage + " games against random player!");		
	}

}




