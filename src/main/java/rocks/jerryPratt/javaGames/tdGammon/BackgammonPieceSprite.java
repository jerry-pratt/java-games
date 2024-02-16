package rocks.jerryPratt.javaGames.tdGammon;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import us.ihmc.javaSpriteWorld.SpriteCostume;

public class BackgammonPieceSprite 
{
	public static BackgammonCheckerSprite createChecker(BackgammonColor color) 
	{
		BackgammonCheckerSprite sprite = new BackgammonCheckerSprite("Backgammon Checker");

		BufferedImage bufferedImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics2D = bufferedImage.createGraphics();
		
		drawFilledCircle(color, graphics2D);
		graphics2D.dispose();
		
		Image fxImage = SwingFXUtils.toFXImage(bufferedImage, null);
		SpriteCostume costume = new SpriteCostume(fxImage);
		costume.setXReferencePercent(0.5);
		costume.setYReferencePercent(0.5);
		sprite.addCostume(costume);
		
		bufferedImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
		graphics2D = bufferedImage.createGraphics();

		drawFilledCircle(color, graphics2D);
		drawOutlineCircle(color, graphics2D);
		graphics2D.dispose();
 
	   fxImage = SwingFXUtils.toFXImage(bufferedImage, null);
		costume = new SpriteCostume(fxImage);
		costume.setXReferencePercent(0.5);
		costume.setYReferencePercent(0.5);
		sprite.addCostume(costume);
				
		return sprite;
	}

	private static final float dash1[] = {10.0f};
   private static final BasicStroke dashed = new BasicStroke(4.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);
   
   private static void drawOutlineCircle(BackgammonColor color, Graphics2D graphics2D)
   {
      if (color == BackgammonColor.White)
		{
		   graphics2D.setColor(Color.GREEN);
		}
		else
		{
		   graphics2D.setColor(Color.GREEN);
		}

      graphics2D.setStroke(dashed);
		graphics2D.drawOval(0, 0, 100,   100);
   }

   private static void drawFilledCircle(BackgammonColor color, Graphics2D graphics2D)
   {
      if (color == BackgammonColor.White)
		{
			graphics2D.setColor(Color.gray);
		}
		else
		{
			graphics2D.setColor(Color.BLACK);
		}
		
		graphics2D.fillOval(0, 0, 100,	100);
   }
	
			
}
