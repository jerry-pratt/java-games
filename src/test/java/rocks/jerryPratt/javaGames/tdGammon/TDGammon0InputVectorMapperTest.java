package rocks.jerryPratt.javaGames.tdGammon;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TDGammon0InputVectorMapperTest 
{

	@Test
	public void testInputMapping() 
	{
		// Black is going to the right here, white is going to the left.
		// Both bars are on the left and both finished spots are on the right.

		String string = "1B/2W  ___ 1W  1W  1W  ___ 3W  1B  1W  ___ ___ ___ 7B  ___  ___ ___ ___ 1B  ___ 2B  1W  2W  1B  ___ ___ 2B/3W  ";

		BackgammonBoard board = new BackgammonBoard(BackgammonColor.Black, string);

		TDGammon0InputVectorMapper mapper = new TDGammon0InputVectorMapper();

		double[] inputVector = mapper.generateInputVector(board);

		double epsilon = 1e-7;

		int index = 0;
		assertEquals(inputVector[index++], 3.0/15.0, epsilon); // White finished n/15
		assertEquals(inputVector[index++], 2.0/15.0, epsilon); // Black finished n/15

		//////////////////////////////////////////////////////////////////
		assertEquals(inputVector[index++], 0.0, epsilon); // Any white.
		assertEquals(inputVector[index++], 0.0, epsilon); // 2 or more white.
		assertEquals(inputVector[index++], 0.0, epsilon); // 3 or more white.
		assertEquals(inputVector[index++], 0.0, epsilon); // 4 or more white. (n-3)/2

		assertEquals(inputVector[index++], 0.0, epsilon); // Any black.
		assertEquals(inputVector[index++], 0.0, epsilon); // 2 or more black.
		assertEquals(inputVector[index++], 0.0, epsilon); // 3 or more black.
		assertEquals(inputVector[index++], 0.0, epsilon); // 4 or more black. (n-3)/2
		//////////////////////////////////////////////////////////////////
		assertEquals(inputVector[index++], 1.0, epsilon); // Any white.
		assertEquals(inputVector[index++], 0.0, epsilon); // 2 or more white.
		assertEquals(inputVector[index++], 0.0, epsilon); // 3 or more white.
		assertEquals(inputVector[index++], 0.0, epsilon); // 4 or more white. (n-3)/2

		assertEquals(inputVector[index++], 0.0, epsilon); // Any black.
		assertEquals(inputVector[index++], 0.0, epsilon); // 2 or more black.
		assertEquals(inputVector[index++], 0.0, epsilon); // 3 or more black.
		assertEquals(inputVector[index++], 0.0, epsilon); // 4 or more black. (n-3)/2
		//////////////////////////////////////////////////////////////////
		assertEquals(inputVector[index++], 1.0, epsilon); // Any white.
		assertEquals(inputVector[index++], 0.0, epsilon); // 2 or more white.
		assertEquals(inputVector[index++], 0.0, epsilon); // 3 or more white.
		assertEquals(inputVector[index++], 0.0, epsilon); // 4 or more white. (n-3)/2

		assertEquals(inputVector[index++], 1.0, epsilon); // Any black.
		assertEquals(inputVector[index++], 0.0, epsilon); // 2 or more black.
		assertEquals(inputVector[index++], 0.0, epsilon); // 3 or more black.
		assertEquals(inputVector[index++], 0.0, epsilon); // 4 or more black. (n-3)/2
		//////////////////////////////////////////////////////////////////
		assertEquals(inputVector[index++], 1.0, epsilon); // Any white.
		assertEquals(inputVector[index++], 0.0, epsilon); // 2 or more white.
		assertEquals(inputVector[index++], 0.0, epsilon); // 3 or more white.
		assertEquals(inputVector[index++], 0.0, epsilon); // 4 or more white. (n-3)/2

		assertEquals(inputVector[index++], 0.0, epsilon); // Any black.
		assertEquals(inputVector[index++], 0.0, epsilon); // 2 or more black.
		assertEquals(inputVector[index++], 0.0, epsilon); // 3 or more black.
		assertEquals(inputVector[index++], 0.0, epsilon); // 4 or more black. (n-3)/2
		//////////////////////////////////////////////////////////////////
		assertEquals(inputVector[index++], 0.0, epsilon); // Any white.
		assertEquals(inputVector[index++], 0.0, epsilon); // 2 or more white.
		assertEquals(inputVector[index++], 0.0, epsilon); // 3 or more white.
		assertEquals(inputVector[index++], 0.0, epsilon); // 4 or more white. (n-3)/2

		assertEquals(inputVector[index++], 0.0, epsilon); // Any black.
		assertEquals(inputVector[index++], 0.0, epsilon); // 2 or more black.
		assertEquals(inputVector[index++], 0.0, epsilon); // 3 or more black.
		assertEquals(inputVector[index++], 0.0, epsilon); // 4 or more black. (n-3)/2
		//////////////////////////////////////////////////////////////////
		assertEquals(inputVector[index++], 1.0, epsilon); // Any white.
		assertEquals(inputVector[index++], 1.0, epsilon); // 2 or more white.
		assertEquals(inputVector[index++], 1.0, epsilon); // 3 or more white.
		assertEquals(inputVector[index++], 0.0, epsilon); // 4 or more white. (n-3)/2

		assertEquals(inputVector[index++], 1.0, epsilon); // Any black.
		assertEquals(inputVector[index++], 1.0, epsilon); // 2 or more black.
		assertEquals(inputVector[index++], 0.0, epsilon); // 3 or more black.
		assertEquals(inputVector[index++], 0.0, epsilon); // 4 or more black. (n-3)/2
		//////////////////////////////////////////////////////////////////
		assertEquals(inputVector[index++], 0.0, epsilon); // Any white.
		assertEquals(inputVector[index++], 0.0, epsilon); // 2 or more white.
		assertEquals(inputVector[index++], 0.0, epsilon); // 3 or more white.
		assertEquals(inputVector[index++], 0.0, epsilon); // 4 or more white. (n-3)/2

		assertEquals(inputVector[index++], 0.0, epsilon); // Any black.
		assertEquals(inputVector[index++], 0.0, epsilon); // 2 or more black.
		assertEquals(inputVector[index++], 0.0, epsilon); // 3 or more black.
		assertEquals(inputVector[index++], 0.0, epsilon); // 4 or more black. (n-3)/2
		//////////////////////////////////////////////////////////////////
		assertEquals(inputVector[index++], 1.0, epsilon); // Any white.
		assertEquals(inputVector[index++], 0.0, epsilon); // 2 or more white.
		assertEquals(inputVector[index++], 0.0, epsilon); // 3 or more white.
		assertEquals(inputVector[index++], 0.0, epsilon); // 4 or more white. (n-3)/2

		assertEquals(inputVector[index++], 1.0, epsilon); // Any black.
		assertEquals(inputVector[index++], 0.0, epsilon); // 2 or more black.
		assertEquals(inputVector[index++], 0.0, epsilon); // 3 or more black.
		assertEquals(inputVector[index++], 0.0, epsilon); // 4 or more black. (n-3)/2
		//////////////////////////////////////////////////////////////////
		assertEquals(inputVector[index++], 0.0, epsilon); // Any white.
		assertEquals(inputVector[index++], 0.0, epsilon); // 2 or more white.
		assertEquals(inputVector[index++], 0.0, epsilon); // 3 or more white.
		assertEquals(inputVector[index++], 0.0, epsilon); // 4 or more white. (n-3)/2

		assertEquals(inputVector[index++], 0.0, epsilon); // Any black.
		assertEquals(inputVector[index++], 0.0, epsilon); // 2 or more black.
		assertEquals(inputVector[index++], 0.0, epsilon); // 3 or more black.
		assertEquals(inputVector[index++], 0.0, epsilon); // 4 or more black. (n-3)/2
		//////////////////////////////////////////////////////////////////
		assertEquals(inputVector[index++], 0.0, epsilon); // Any white.
		assertEquals(inputVector[index++], 0.0, epsilon); // 2 or more white.
		assertEquals(inputVector[index++], 0.0, epsilon); // 3 or more white.
		assertEquals(inputVector[index++], 0.0, epsilon); // 4 or more white. (n-3)/2

		assertEquals(inputVector[index++], 0.0, epsilon); // Any black.
		assertEquals(inputVector[index++], 0.0, epsilon); // 2 or more black.
		assertEquals(inputVector[index++], 0.0, epsilon); // 3 or more black.
		assertEquals(inputVector[index++], 0.0, epsilon); // 4 or more black. (n-3)/2
		//////////////////////////////////////////////////////////////////
		assertEquals(inputVector[index++], 0.0, epsilon); // Any white.
		assertEquals(inputVector[index++], 0.0, epsilon); // 2 or more white.
		assertEquals(inputVector[index++], 0.0, epsilon); // 3 or more white.
		assertEquals(inputVector[index++], 0.0, epsilon); // 4 or more white. (n-3)/2

		assertEquals(inputVector[index++], 0.0, epsilon); // Any black.
		assertEquals(inputVector[index++], 0.0, epsilon); // 2 or more black.
		assertEquals(inputVector[index++], 0.0, epsilon); // 3 or more black.
		assertEquals(inputVector[index++], 0.0, epsilon); // 4 or more black. (n-3)/2
		//////////////////////////////////////////////////////////////////
		assertEquals(inputVector[index++], 0.0, epsilon); // Any white.
		assertEquals(inputVector[index++], 0.0, epsilon); // 2 or more white.
		assertEquals(inputVector[index++], 0.0, epsilon); // 3 or more white.
		assertEquals(inputVector[index++], 0.0, epsilon); // 4 or more white. (n-3)/2

		assertEquals(inputVector[index++], 0.0, epsilon); // Any black.
		assertEquals(inputVector[index++], 0.0, epsilon); // 2 or more black.
		assertEquals(inputVector[index++], 0.0, epsilon); // 3 or more black.
		assertEquals(inputVector[index++], 0.0, epsilon); // 4 or more black. (n-3)/2
		//////////////////////////////////////////////////////////////////
		assertEquals(inputVector[index++], 0.0, epsilon); // Any white.
		assertEquals(inputVector[index++], 0.0, epsilon); // 2 or more white.
		assertEquals(inputVector[index++], 0.0, epsilon); // 3 or more white.
		assertEquals(inputVector[index++], 0.0, epsilon); // 4 or more white. (n-3)/2

		assertEquals(inputVector[index++], 1.0, epsilon); // Any black.
		assertEquals(inputVector[index++], 1.0, epsilon); // 2 or more black.
		assertEquals(inputVector[index++], 1.0, epsilon); // 3 or more black.
		assertEquals(inputVector[index++], 2.0, epsilon); // 4 or more black. (n-3)/2
		//////////////////////////////////////////////////////////////////
		assertEquals(inputVector[index++], 0.0, epsilon); // Any white.
		assertEquals(inputVector[index++], 0.0, epsilon); // 2 or more white.
		assertEquals(inputVector[index++], 0.0, epsilon); // 3 or more white.
		assertEquals(inputVector[index++], 0.0, epsilon); // 4 or more white. (n-3)/2

		assertEquals(inputVector[index++], 0.0, epsilon); // Any black.
		assertEquals(inputVector[index++], 0.0, epsilon); // 2 or more black.
		assertEquals(inputVector[index++], 0.0, epsilon); // 3 or more black.
		assertEquals(inputVector[index++], 0.0, epsilon); // 4 or more black. (n-3)/2
		//////////////////////////////////////////////////////////////////
		assertEquals(inputVector[index++], 0.0, epsilon); // Any white.
		assertEquals(inputVector[index++], 0.0, epsilon); // 2 or more white.
		assertEquals(inputVector[index++], 0.0, epsilon); // 3 or more white.
		assertEquals(inputVector[index++], 0.0, epsilon); // 4 or more white. (n-3)/2

		assertEquals(inputVector[index++], 0.0, epsilon); // Any black.
		assertEquals(inputVector[index++], 0.0, epsilon); // 2 or more black.
		assertEquals(inputVector[index++], 0.0, epsilon); // 3 or more black.
		assertEquals(inputVector[index++], 0.0, epsilon); // 4 or more black. (n-3)/2
		//////////////////////////////////////////////////////////////////
		assertEquals(inputVector[index++], 0.0, epsilon); // Any white.
		assertEquals(inputVector[index++], 0.0, epsilon); // 2 or more white.
		assertEquals(inputVector[index++], 0.0, epsilon); // 3 or more white.
		assertEquals(inputVector[index++], 0.0, epsilon); // 4 or more white. (n-3)/2

		assertEquals(inputVector[index++], 0.0, epsilon); // Any black.
		assertEquals(inputVector[index++], 0.0, epsilon); // 2 or more black.
		assertEquals(inputVector[index++], 0.0, epsilon); // 3 or more black.
		assertEquals(inputVector[index++], 0.0, epsilon); // 4 or more black. (n-3)/2
		//////////////////////////////////////////////////////////////////
		assertEquals(inputVector[index++], 0.0, epsilon); // Any white.
		assertEquals(inputVector[index++], 0.0, epsilon); // 2 or more white.
		assertEquals(inputVector[index++], 0.0, epsilon); // 3 or more white.
		assertEquals(inputVector[index++], 0.0, epsilon); // 4 or more white. (n-3)/2

		assertEquals(inputVector[index++], 0.0, epsilon); // Any black.
		assertEquals(inputVector[index++], 0.0, epsilon); // 2 or more black.
		assertEquals(inputVector[index++], 0.0, epsilon); // 3 or more black.
		assertEquals(inputVector[index++], 0.0, epsilon); // 4 or more black. (n-3)/2
		//////////////////////////////////////////////////////////////////
		assertEquals(inputVector[index++], 0.0, epsilon); // Any white.
		assertEquals(inputVector[index++], 0.0, epsilon); // 2 or more white.
		assertEquals(inputVector[index++], 0.0, epsilon); // 3 or more white.
		assertEquals(inputVector[index++], 0.0, epsilon); // 4 or more white. (n-3)/2

		assertEquals(inputVector[index++], 1.0, epsilon); // Any black.
		assertEquals(inputVector[index++], 0.0, epsilon); // 2 or more black.
		assertEquals(inputVector[index++], 0.0, epsilon); // 3 or more black.
		assertEquals(inputVector[index++], 0.0, epsilon); // 4 or more black. (n-3)/2
		//////////////////////////////////////////////////////////////////
		assertEquals(inputVector[index++], 0.0, epsilon); // Any white.
		assertEquals(inputVector[index++], 0.0, epsilon); // 2 or more white.
		assertEquals(inputVector[index++], 0.0, epsilon); // 3 or more white.
		assertEquals(inputVector[index++], 0.0, epsilon); // 4 or more white. (n-3)/2

		assertEquals(inputVector[index++], 0.0, epsilon); // Any black.
		assertEquals(inputVector[index++], 0.0, epsilon); // 2 or more black.
		assertEquals(inputVector[index++], 0.0, epsilon); // 3 or more black.
		assertEquals(inputVector[index++], 0.0, epsilon); // 4 or more black. (n-3)/2
		//////////////////////////////////////////////////////////////////
		assertEquals(inputVector[index++], 1.0, epsilon); // Any white.
		assertEquals(inputVector[index++], 0.0, epsilon); // 2 or more white.
		assertEquals(inputVector[index++], 0.0, epsilon); // 3 or more white.
		assertEquals(inputVector[index++], 0.0, epsilon); // 4 or more white. (n-3)/2

		assertEquals(inputVector[index++], 0.0, epsilon); // Any black.
		assertEquals(inputVector[index++], 0.0, epsilon); // 2 or more black.
		assertEquals(inputVector[index++], 0.0, epsilon); // 3 or more black.
		assertEquals(inputVector[index++], 0.0, epsilon); // 4 or more black. (n-3)/2
		//////////////////////////////////////////////////////////////////
		assertEquals(inputVector[index++], 1.0, epsilon); // Any white.
		assertEquals(inputVector[index++], 1.0, epsilon); // 2 or more white.
		assertEquals(inputVector[index++], 0.0, epsilon); // 3 or more white.
		assertEquals(inputVector[index++], 0.0, epsilon); // 4 or more white. (n-3)/2

		assertEquals(inputVector[index++], 0.0, epsilon); // Any black.
		assertEquals(inputVector[index++], 0.0, epsilon); // 2 or more black.
		assertEquals(inputVector[index++], 0.0, epsilon); // 3 or more black.
		assertEquals(inputVector[index++], 0.0, epsilon); // 4 or more black. (n-3)/2
		//////////////////////////////////////////////////////////////////
		assertEquals(inputVector[index++], 0.0, epsilon); // Any white.
		assertEquals(inputVector[index++], 0.0, epsilon); // 2 or more white.
		assertEquals(inputVector[index++], 0.0, epsilon); // 3 or more white.
		assertEquals(inputVector[index++], 0.0, epsilon); // 4 or more white. (n-3)/2

		assertEquals(inputVector[index++], 0.0, epsilon); // Any black.
		assertEquals(inputVector[index++], 0.0, epsilon); // 2 or more black.
		assertEquals(inputVector[index++], 0.0, epsilon); // 3 or more black.
		assertEquals(inputVector[index++], 0.0, epsilon); // 4 or more black. (n-3)/2
		//////////////////////////////////////////////////////////////////
		assertEquals(inputVector[index++], 0.0, epsilon); // Any white.
		assertEquals(inputVector[index++], 0.0, epsilon); // 2 or more white.
		assertEquals(inputVector[index++], 0.0, epsilon); // 3 or more white.
		assertEquals(inputVector[index++], 0.0, epsilon); // 4 or more white. (n-3)/2

		assertEquals(inputVector[index++], 0.0, epsilon); // Any black.
		assertEquals(inputVector[index++], 0.0, epsilon); // 2 or more black.
		assertEquals(inputVector[index++], 0.0, epsilon); // 3 or more black.
		assertEquals(inputVector[index++], 0.0, epsilon); // 4 or more black. (n-3)/2
		//////////////////////////////////////////////////////////////////
		assertEquals(inputVector[index++], 0.0, epsilon); // Any white.
		assertEquals(inputVector[index++], 0.0, epsilon); // 2 or more white.
		assertEquals(inputVector[index++], 0.0, epsilon); // 3 or more white.
		assertEquals(inputVector[index++], 0.0, epsilon); // 4 or more white. (n-3)/2

		assertEquals(inputVector[index++], 0.0, epsilon); // Any black.
		assertEquals(inputVector[index++], 0.0, epsilon); // 2 or more black.
		assertEquals(inputVector[index++], 0.0, epsilon); // 3 or more black.
		assertEquals(inputVector[index++], 0.0, epsilon); // 4 or more black. (n-3)/2
		//////////////////////////////////////////////////////////////////

		
		assertEquals(inputVector[index++], 1.0, epsilon); // White on bar n/2
		assertEquals(inputVector[index++], 0.5, epsilon); // Black on bar n/2
		
		assertEquals(inputVector[index++], 0.0, epsilon); // White turn
		assertEquals(inputVector[index++], 1.0, epsilon); // Black turn

		assertEquals(198, index);
	}

}
