package main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction;

import static main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction.Constants.TileC.mazeBlockSize;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

public class Entity {
	protected float x,y ;
	protected float entity_height ,entity_width;
	protected Rectangle2D.Float hitbox;
	public Entity() {
		
	}
	public Entity(float x,float y , float entity_width ,float entity_height) {
		this.x = x;
		this.y = y ;
		this.entity_width = entity_width ;
		this.entity_height = entity_height ;
		
	}
	protected void drawHitbox(Graphics g, int x, int y) {
		g.setColor(Color.PINK);
		g.drawRect(x , y , (int)entity_width ,(int) entity_height);
	}
	protected void initHitbox(float x, float y , float entity_width , float entity_height) {
		hitbox =new Rectangle2D.Float(x,y,entity_width,entity_height);
		
	}

	public Rectangle2D.Float getHitbox() {
		return hitbox;
	}
	public float getEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed) {
		int currentTile = (int) (hitbox.x / mazeBlockSize);
		if (xSpeed > 0) {
			// Right
			int tileXPos = currentTile * mazeBlockSize;
			int xOffset = (int) (mazeBlockSize - hitbox.width);
			return tileXPos + xOffset - 1;
		} else
			// Left
			return currentTile * mazeBlockSize;
	}
	public static boolean IsEntityOnFloor(Rectangle2D.Float hitbox, int[][] maze) {
		// Check the pixel below bottomleft and bottomright
		if (!collision_check.IsSolid(hitbox.x, hitbox.y + hitbox.height + 1, maze))
			if (!collision_check.IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, maze))
				return false;

		return true;

	}
	protected float getEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed, String characterString) {
		int currentTile = (int) (hitbox.y / mazeBlockSize);
		if (airSpeed > 0) {
				
			int tileYPos = currentTile * mazeBlockSize;
			int yOffset;
			if (characterString == "hero")
				yOffset = (int)(mazeBlockSize - hitbox.height );
			else
				yOffset = (int)(2*mazeBlockSize - hitbox.height );
			return tileYPos + yOffset - 1;
				
		}else 
			return currentTile * mazeBlockSize;
	}
}
