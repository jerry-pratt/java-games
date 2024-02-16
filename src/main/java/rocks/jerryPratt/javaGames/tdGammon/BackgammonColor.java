package rocks.jerryPratt.javaGames.tdGammon;

public enum BackgammonColor 
{
	White, Black;

	public BackgammonColor getOppositeColor() 
	{
		if (this == White) return Black;
		return White;
	}

	public String getLetter() 
	{
		if (this == White) return "W";
		return "B";
	}

	public static BackgammonColor parseColor(String string) 
	{
		if (string.startsWith("W")) return White;
		else if (string.startsWith("B")) return Black;
		
		else throw new RuntimeException();
	}
}
