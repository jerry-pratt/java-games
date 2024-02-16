package rocks.jerryPratt.javaGames.tdGammon;

import us.ihmc.javaSpriteWorld.Sprite;

public class BackgammonCheckerSprite extends Sprite
{
	private boolean dragging = false;
	private int boardSpace = -1;
	
	public BackgammonCheckerSprite(String name)
	{
		super(name);
	}

	public void setDragging(boolean dragging) 
	{
		this.dragging = dragging;
	}
	
	public boolean getDragging()
	{
		return this.dragging;
	}

	public int getBoardSpace() 
	{
		return boardSpace;
	}
	
	public void setBoardSpace(int boardSpace)
	{
		this.boardSpace = boardSpace;
	}
}
