package rocks.jerryPratt.javaGames.tdGammon;

import org.junit.runners.Suite;
import org.junit.runner.RunWith;



@RunWith(Suite.class)
@Suite.SuiteClasses({
   TDGammon0InputVectorMapperTest.class, 
   TDGammonNeuralNetworkTest.class,
   BackgammonGameTest.class, 
   BackgammonBoardTest.class, 
   BackgammonMoveTest.class, 
   BackgammonIndividualPieceMoveTest.class, 
   DiceRollTest.class, 
   BackgammonColorTest.class})

public class BackgammonTestSuite
{
}

