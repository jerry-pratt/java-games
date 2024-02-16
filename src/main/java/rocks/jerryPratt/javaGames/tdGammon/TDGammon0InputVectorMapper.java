package rocks.jerryPratt.javaGames.tdGammon;

public class TDGammon0InputVectorMapper 
{
	private static final int finishedPosition = 0;
	private static final int barPosition = 25;

	public double[] generateInputVector(BackgammonBoard board)
	{
		// This is the mapping described in Introduction to Reinforcement Learning.
		// Note that the positions are in terms of the colors positions. So 1 is 1 away from finished for both 
		// Black and white. 

		double[] inputVector = new double[198];
		int index = 0;

		inputVector[index++] = ((double) board.getPiecesFinished(BackgammonColor.White)) / 15.0;
		inputVector[index++] = ((double) board.getPiecesFinished(BackgammonColor.Black)) / 15.0;

		for (int boardPosition=1; boardPosition<barPosition; boardPosition++)
		{
			for (BackgammonColor color : BackgammonColor.values())
			{
				int pieceCount = board.getPieces(color, boardPosition);

				if (pieceCount > 0)
				{
					inputVector[index++] = 1.0;
				}
				else
				{
					inputVector[index++] = 0.0;
				}

				if (pieceCount > 1)
				{
					inputVector[index++] = 1.0;
				}
				else
				{
					inputVector[index++] = 0.0;
				}

				if (pieceCount > 2)
				{
					inputVector[index++] = 1.0;
				}
				else
				{
					inputVector[index++] = 0.0;
				}

				if (pieceCount > 3)
				{
					inputVector[index++] = (((double) pieceCount) - 3.0)/2.0;
				}
				else
				{
					inputVector[index++] = 0.0;
				}
			}
		}

		inputVector[index++] = board.getPiecesOnBar(BackgammonColor.White)/2.0;
		inputVector[index++] = board.getPiecesOnBar(BackgammonColor.Black)/2.0;

		BackgammonColor whoseTurn = board.getWhoseTurn();

		if (whoseTurn == BackgammonColor.White)
		{
			inputVector[index++] = 1.0;
		}
		else
		{
			inputVector[index++] = 0.0;
		}

		if (whoseTurn == BackgammonColor.Black)
		{
			inputVector[index++] = 1.0;
		}
		else
		{
			inputVector[index++] = 0.0;
		}

		if (index != inputVector.length) throw new RuntimeException("index != inputVector.length");
		return inputVector;
	}
}
