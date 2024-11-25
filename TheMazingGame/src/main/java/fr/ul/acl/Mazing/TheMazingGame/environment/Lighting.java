package main.java.fr.ul.acl.Mazing.TheMazingGame.environment;
import main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction.Entity;
import main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction.Hero;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import main.java.fr.ul.acl.Mazing.TheMazingGame.graphics.GamePannel;
import main.java.fr.ul.acl.Mazing.TheMazingGame.start.Game;

import static main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction.Constants.GamePannelC.*;
import main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction.Constants.TileC;
import static main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction.Constants.HeroC.*;

public class Lighting {
	private GamePannel gp;
	private BufferedImage darknessFilter;
	private Hero hero;
	RadialGradientPaint gPaint;
	Color color[] = new Color[12];
	float fraction[] = new float[12];
	int centerX , centerY;
	private static final int circleSize=300;
	private Color semiBlack = new Color(0,0,0,200);
	private Area screenArea;
	//private Game game;

   public Lighting(Hero hero) {
	   this.hero=hero;
	   darknessFilter = new BufferedImage(WINDOW_HEIGHT,WINDOW_WIDTH , BufferedImage.TYPE_INT_ARGB);
	   createGradiationEffects();
	   
	   
   }
   public void createl(Graphics2D g2 ) { 

	// Create a buffered image
	   
	//Graphics2D g2 = (Graphics2D)darknessFilter.getGraphics();
			
	// Get the center x and y of the light circle
//int centerX = (int) (Entity.getHitbox().getX() + (TileC.frame_blockSize)/2);
	//int centerY = (int) (Entity.getHitbox().getY()+ (TileC.frame_blockSize)/2);
	centerX = (int) (hero.getHitbox().x);
	centerY = (int) (hero.getHitbox().y);
	gPaint = new RadialGradientPaint(centerX, centerY, (circleSize/2), fraction, color);

	// Create a gradation paint settings
	//gPaint = new RadialGradientPaint(centerX, centerY, (circleSize/2), fraction, color);
	
	//gPaint.getCenterPoint().setLocation(centerX, centerY);
	// Set the gradient data on g2
	g2.setPaint(gPaint);
	//g2.setColor(semiBlack);
	g2.fillRect(0,0,WINDOW_HEIGHT,WINDOW_WIDTH);
	
	//System.out.println(rectFilled);
	//g2.dispose();
   }
   
   public void createGradiationEffects() {
	// Create a gradation effect
	   	color[0] = new Color(0,0,0,0.1f);
		color[1] = new Color(0,0,0,0.42f);
		color[2] = new Color(0,0,0,0.52f);
		color[3] = new Color(0,0,0,0.61f);
		color[4] = new Color(0,0,0,0.69f);
		color[5] = new Color(0,0,0,0.76f);
		color[6] = new Color(0,0,0,0.82f);
		color[7] = new Color(0,0,0,0.87f);
		color[8] = new Color(0,0,0,0.91f);
		color[9] = new Color(0,0,0,0.94f);
		color[10] = new Color(0,0,0,0.96f);
		color[11] = new Color(0,0,0,0.98f);
		
		fraction[0] = 0f;
		fraction[1] = 0.4f;
		fraction[2] = 0.5f;
		fraction[3] = 0.6f;
		fraction[4] = 0.65f;
		fraction[5] = 0.7f;
		fraction[6] = 0.75f;
		fraction[7] = 0.8f;
		fraction[8] = 0.85f;
		fraction[9] = 0.9f;
		fraction[10] = 0.95f;
		fraction[11] = 1f;
   }
   
	
   public void draw(Graphics2D g2) {
	 createl(g2);
	 g2.drawImage(darknessFilter,0,0,null);  

	   
   }

}