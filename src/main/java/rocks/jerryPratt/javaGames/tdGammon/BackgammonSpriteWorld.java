package rocks.jerryPratt.javaGames.tdGammon;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;
import java.util.Random;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import us.ihmc.javaSpriteWorld.SampleSprites;
import us.ihmc.javaSpriteWorld.SampleStageBackdrops;
import us.ihmc.javaSpriteWorld.Sprite;
import us.ihmc.javaSpriteWorld.SpriteMouseListener;
import us.ihmc.javaSpriteWorld.SpriteStage;
import us.ihmc.javaSpriteWorld.SpriteWorld;
import us.ihmc.javaSpriteWorld.SpriteWorldViewer;
import us.ihmc.javaSpriteWorld.SpriteWorldViewerUsingJavaFX;
import us.ihmc.javaSpriteWorld.StageBackdrop;

public class BackgammonSpriteWorld
{
	private BackgammonBoard boardToDraw;
	private EnumMap<BackgammonColor, DiceRoll> diceRolls = new EnumMap<>(BackgammonColor.class);

	private final SpriteWorld spriteWorld;
//   private final SpriteWorldViewer viewer = new SpriteWorldViewerUsingSwing("Backgammon");
   private final SpriteWorldViewer viewer = new SpriteWorldViewerUsingJavaFX("Backgammon");

   private EnumMap<BackgammonColor, ArrayList<BackgammonCheckerSprite>> checkers = new EnumMap<>(BackgammonColor.class);
   private EnumMap<BackgammonColor, ArrayList<Sprite>> dice = new EnumMap<>(BackgammonColor.class);

	private final double width = 1.0;
	private final double height = 1.0/1.4;

	private boolean animateDiceRolls = true;

	public BackgammonSpriteWorld()
	{
		spriteWorld = new SpriteWorld();
		spriteWorld.setLeftBorderX(0.0);
		spriteWorld.setTopBorderY(0.0);
		spriteWorld.setRightBorderX(width);
		spriteWorld.setBottomBorderY(height);

		for (BackgammonColor color : BackgammonColor.values())
		{
			ArrayList<BackgammonCheckerSprite> sprites = new ArrayList<>();
			ArrayList<Sprite> diceList = new ArrayList<>();

			for (int i=0; i<15; i++)
			{
				ArrayList<Sprite> checkerList = new ArrayList<>();

				BackgammonCheckerSprite sprite = BackgammonPieceSprite.createChecker(color);
				sprites.add(sprite);
				sprite.setWidth(0.068 * height);
				sprite.setHeight(0.068 * height);

				sprite.setX(width/2.0);
				sprite.setY(height/2.0);
				spriteWorld.addSprite(sprite);

				checkerList.add(sprite);
			}

			for (int i=0; i<2; i++)
			{
				Sprite sprite;
				if (color == BackgammonColor.White)
				{
					sprite = SampleSprites.createSixSidedRedPipsOnWhiteDie();
				}
				else
				{
					sprite = SampleSprites.createSixSidedBlackPipsOnWhiteDie();
				}

				sprite.setWidth(0.04 * height);
				sprite.setHeight(0.04 * height);

				sprite.setX(width/3.0);
				sprite.setY(height/2.0);
				sprite.hide();

				spriteWorld.addSprite(sprite);

				diceList.add(sprite);
			}

         checkers.put(color, sprites);
         dice.put(color, diceList);
		}

		SpriteStage stage = new SpriteStage("Backgammon Stage");
		StageBackdrop backgammonBoardBackdrop = SampleStageBackdrops.getBackgammonBoard();
		backgammonBoardBackdrop = addNumbersToBackdrop(backgammonBoardBackdrop);
		stage.addBackdrop(backgammonBoardBackdrop);

		spriteWorld.setStage(stage, true);

		viewer.setSpriteWorld(spriteWorld);

		int pixelWidth = 1200;

		viewer.setPreferredSizeInPixels(pixelWidth, ((int) (pixelWidth * height)));
		viewer.setResizable(false);
		viewer.createAndDisplayWindow();

		setBoardToDraw(new BackgammonBoard());
	}

	public void attachSpriteMouseListenerToCheckers(BackgammonColor color, SpriteMouseListener spriteMouseListener)
	{
		ArrayList<BackgammonCheckerSprite> coloredCheckers = this.checkers.get(color);

		for (BackgammonCheckerSprite sprite : coloredCheckers)
		{
			sprite.attachSpriteMouseListener(spriteMouseListener);
		}
	}


	public int getBoardSpaceFromWorldPosition(double xWorld, double yWorld)
	{
//		if (xWorld < 0.02) return -1;
		if (xWorld > width * 0.98) return -1;
		if (yWorld < 0.05) return -1;
		if (yWorld > height * 0.95) return -1;

		int spaceToReturn = -1;

		double barWidth = 0.05;

		if (yWorld > 0.5 * height)
		{
			if (xWorld < 0.5 - barWidth/2.0)
			{
				spaceToReturn = (int) (Math.round((xWorld - 0.065) / 0.0745 + 1.0));
			}
			else if (xWorld > 0.5 + barWidth/2.0)
			{
				spaceToReturn =  (int) (Math.round((xWorld - 0.562) / 0.0745 + 7.0));
			}
			else
			{
				spaceToReturn = 25;
			}
		}

		else if (yWorld < 0.5 * height)
		{
			if (xWorld < 0.5 - barWidth/2.0)
			{
				spaceToReturn =  (int) (Math.round((0.438 - xWorld) / 0.0745 + 19.0));
			}
			else if (xWorld > 0.5 + barWidth/2.0)
			{
				spaceToReturn =  (int) (Math.round((0.935 - xWorld) / 0.0745 + 13.0));
			}

			else
			{
				spaceToReturn = 25;
			}
		}

		if (spaceToReturn < 1) spaceToReturn = 0;
		if (spaceToReturn > 25) spaceToReturn = -1;

		return spaceToReturn;
	}

	public double getWorldXPositionFromBoardSpace(int boardPosition)
	{
		double xPosition;
		if (boardPosition <= 6) xPosition = 0.065 + ((double) (boardPosition - 1)) * 0.0745;
		else if (boardPosition <= 12) xPosition = 0.562 + ((double) (boardPosition - 7)) * 0.0745;
		else if (boardPosition <= 18) xPosition = 0.935 - ((double) (boardPosition - 13)) * 0.0745;
		else xPosition = 0.438 - ((double) (boardPosition - 19)) * 0.0745;

		return xPosition;
	}

	public double getWorldYPositionForBackdropFromBoardSpace(int boardPosition)
	{
		double yPosition;
		if (boardPosition <= 12) yPosition = 0.992;
		else yPosition = 0.028;

		return yPosition;
	}


	private StageBackdrop addNumbersToBackdrop(StageBackdrop backdrop)
	{
		Image image = backdrop.getImage();
		BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);

		Graphics graphics = bufferedImage.getGraphics();

		for (int boardPosition = 1; boardPosition < 25; boardPosition++)
		{
			double worldXPositionToDraw = getWorldXPositionFromBoardSpace(boardPosition);
			double worldYPositionToDraw = getWorldYPositionForBackdropFromBoardSpace(boardPosition);
			drawPositionNumber(bufferedImage, graphics, boardPosition, worldXPositionToDraw, worldYPositionToDraw);
		}

		image = SwingFXUtils.toFXImage(bufferedImage, null);
		
		StageBackdrop newBackdrop = new StageBackdrop(image);
//		backdrop.setImage(image);
		return newBackdrop;
	}

	private final Font font = new Font("Arial", Font.BOLD, 30);

	private void drawPositionNumber(BufferedImage bufferedImage, Graphics graphics, int boardPosition, double percentXCenter, double percentY)
	{
		int widthToDraw = (int) (bufferedImage.getWidth() * percentXCenter) - 10;
		int heightToDraw = (int) (bufferedImage.getHeight() * percentY);

		graphics.setColor(Color.BLACK);
		graphics.setFont(font);
		graphics.drawString("" + boardPosition, widthToDraw, heightToDraw);
	}


	public void setBoardToDraw(BackgammonBoard board)
	{
		this.boardToDraw = new BackgammonBoard(board);
	}

	public void setAnimateDiceRolls(boolean animateDiceRolls)
	{
	   this.animateDiceRolls = animateDiceRolls;
	}

	public void update()
	{
		BackgammonColor whoseTurn = boardToDraw.getWhoseTurn();

		EnumMap<BackgammonColor, Integer> checkerIndex = new EnumMap<>(BackgammonColor.class);
		checkerIndex.put(BackgammonColor.White, 0);
		checkerIndex.put(BackgammonColor.Black, 0);

		for (int boardPosition = 1; boardPosition < 25; boardPosition++)
		{
			double worldXPositionToDraw = getWorldXPositionFromBoardSpace(boardPosition);

			double yPositionToDraw;
			if (boardPosition <= 12) yPositionToDraw = height - 0.05;
			else yPositionToDraw = 0.05;

			boolean top;
			if (boardPosition <= 12) top = false;
			else top = true;

			for (BackgammonColor color : BackgammonColor.values())
			{
				int boardPositionForColor = boardPosition;
				if (color == BackgammonColor.Black) boardPositionForColor = 25 - boardPositionForColor;

				int numberOfPieces = boardToDraw.getPieces(color, boardPositionForColor);

				if (numberOfPieces > 0)
				{
					placeCheckers(checkerIndex, worldXPositionToDraw, yPositionToDraw, numberOfPieces, color, top, boardPositionForColor);
				}

			}

//			if (boardPosition <= 12) yPositionToDraw = 0.995;
//			else yPositionToDraw = 0.025;

//			drawPositionNumber(graphics, boardPosition, xPositionToDraw, yPositionToDraw);
		}

		int numberOfWhiteOnBar = boardToDraw.getPiecesOnBar(BackgammonColor.White);
		int numberOfBlackOnBar = boardToDraw.getPiecesOnBar(BackgammonColor.Black);

		double xPositionToDraw = width/2.0;
		double yPositionToDraw = height/2.0;

		placeCheckers(checkerIndex, xPositionToDraw, yPositionToDraw, numberOfWhiteOnBar, BackgammonColor.White, false, 25);
		placeCheckers(checkerIndex, xPositionToDraw, yPositionToDraw, numberOfBlackOnBar, BackgammonColor.Black, true, 25);

		for (BackgammonColor color : BackgammonColor.values())
      {
		   Integer index = checkerIndex.get(color);

		   for (int i=index; i<15; i++)
		   {
	         Sprite backgammonPiece = getCheckerSpriteAndIncrementIndex(checkerIndex, color);
	         backgammonPiece.hide();
		   }
      }

	   // Dice
		for (BackgammonColor color : BackgammonColor.values())
		{
			DiceRoll diceRoll = diceRolls.get(color);
			if (diceRoll == null) continue;

			int diceOne = diceRoll.getDiceOne();
			int diceTwo = diceRoll.getDiceTwo();

			double yToDraw = height/2.0;
			double xToDraw;

			if (color == BackgammonColor.White)
			{
				xToDraw = 3.0*width/4.0;
			}
			else
			{
				xToDraw = width/4.0;
			}

			ArrayList<Sprite> diceList = dice.get(color);
			Sprite diceSpriteOne = diceList.get(0);
			Sprite diceSpriteTwo = diceList.get(1);

			diceSpriteOne.setCostume(diceOne - 1);
			diceSpriteTwo.setCostume(diceTwo - 1);

			diceSpriteOne.setX(xToDraw);
			diceSpriteOne.setY(yToDraw + height/8.0);

			diceSpriteTwo.setX(xToDraw + width/8.0);
			diceSpriteTwo.setY(yToDraw);
		}

		viewer.update();
	}

	private BackgammonCheckerSprite getCheckerSpriteAndIncrementIndex(EnumMap<BackgammonColor, Integer> checkerIndex, BackgammonColor color)
	{
		BackgammonCheckerSprite backgammonSprite = checkers.get(color).get(checkerIndex.get(color));
		checkerIndex.put(color, checkerIndex.get(color) + 1);
		return backgammonSprite;
	}

   private void placeCheckers(EnumMap<BackgammonColor, Integer> checkerIndex, double xLocation, double yLocationOfFirstChecker, int numberOfPieces, BackgammonColor color, boolean topRow, int boardSpace)
	{
		double yLocationOfChecker = yLocationOfFirstChecker;

		for (int pieceNumber = 0; pieceNumber < numberOfPieces; pieceNumber++)
		{
			BackgammonCheckerSprite backgammonPiece = getCheckerSpriteAndIncrementIndex(checkerIndex, color);
			backgammonPiece.setBoardSpace(boardSpace);

			if (!backgammonPiece.getDragging())
			{
				backgammonPiece.setX(xLocation);
				backgammonPiece.setY(yLocationOfChecker);
			}

			if (topRow)
			{
				yLocationOfChecker += backgammonPiece.getHeight();
			}
			else
			{
				yLocationOfChecker -= backgammonPiece.getHeight();
			}
		}
	}

	public void setBackgammonBoard(BackgammonBoard board)
	{
		this.boardToDraw = new BackgammonBoard(board);
		this.update();

		for (BackgammonColor color : BackgammonColor.values())
		{
			ArrayList<BackgammonCheckerSprite> pieces = checkers.get(color);
			for (BackgammonCheckerSprite piece : pieces)
			{
				piece.show();
			}
		}
		this.update();
	}

	private final Random random = new Random();

	public void setDiceRoll(BackgammonColor whoseTurn, DiceRoll diceRoll)
	{
		ArrayList<Sprite> diceList = dice.get(whoseTurn);
      Sprite dice0 = diceList.get(0);
      Sprite dice1 = diceList.get(1);

      dice0.show();
      dice1.show();

      if (animateDiceRolls)
      {
         for (int i=0; i<6; i++)
         {
//            dice0.setCostume(random.nextInt(6));
//            dice1.setCostume(random.nextInt(6));

            this.diceRolls.put(whoseTurn, new DiceRoll(random));

            try
            {
               Thread.sleep(200L);
            }
            catch (InterruptedException e)
            {
            }

            this.update();
         }
      }

      this.diceRolls.put(whoseTurn, new DiceRoll(diceRoll));

      this.update();
	}

	public static void main(String[] args)
	{
		BackgammonSpriteWorld spriteWorld = new BackgammonSpriteWorld();
		spriteWorld.setAnimateDiceRolls(false);
		spriteWorld.update();

		TDGammonPlayAgainstItselfPlayer whitePlayer = new TDGammonPlayAgainstItselfPlayer(0.0);
      whitePlayer.setWeightsToPreviouslyLearnedWeights();

      BackgammonPlayer blackPlayer = new RandomBackgammonPlayer(BackgammonColor.Black);

      while(true)
      {
         BackgammonGame game = new BackgammonGame();
         game.setWhitePlayer(whitePlayer);
         game.setBlackPlayer(blackPlayer);

         game.setSpriteWorld(spriteWorld);

         boolean printOut = false;
         game.playAGame(printOut);

         try
         {
            Thread.sleep(1000);
         }
         catch (InterruptedException e)
         {
         }
      }
	}

	public void stopDraggingCheckersAndUpdate(BackgammonColor color)
	{
		ArrayList<BackgammonCheckerSprite> checkersOfColor = checkers.get(color);

		for (BackgammonCheckerSprite checker : checkersOfColor)
		{
			checker.setDragging(false);
			checker.setCostume(0);
		}

		this.update();
	}

   public void markCheckersRecentlyMoved(BackgammonMove recentMove)
   {
      BackgammonColor color = recentMove.getColor();
      ArrayList<BackgammonCheckerSprite> checkerList = checkers.get(color);

      Map<Integer, Integer> pieceMoves = recentMove.getMapFromFinalPositionToNetGain();

      for (int i = checkerList.size() - 1; i>=0; i--)
      {
         BackgammonCheckerSprite sprite = checkerList.get(i);

         int boardSpace = sprite.getBoardSpace();

         Integer count = pieceMoves.get(boardSpace);

         if ((count != null) && (count > 0))
         {
            sprite.setCostume(1);
            pieceMoves.put(boardSpace, count - 1);
         }
      }

   }

}
