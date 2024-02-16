package rocks.jerryPratt.javaGames.tdGammon;

public interface BackgammonPlayer 
{
	public abstract BackgammonMove chooseAMove(DiceRoll diceRoll, BackgammonBoard board);
	public abstract void startANewGame(BackgammonBoard startingBoard);
	public abstract void informThatOpponentMadeMove(BackgammonBoard originalBoard, BackgammonMove opponentMove, BackgammonBoard resultantBoard);
}
