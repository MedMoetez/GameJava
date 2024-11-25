package main.java.fr.ul.acl.Mazing.TheMazingGame.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import main.java.fr.ul.acl.Mazing.TheMazingGame.start.Game;

public class GameObject {
	
	protected int x,y,objType;
	protected Rectangle2D.Float hitbox;

	protected boolean active =true;;
	protected int xDrawOffset ,yDrawOffset;
	
	public GameObject(int x, int y,int objType) {
		this.x = x;
		this.y = y;
		this.objType = objType;
	}
	
	protected void initHitbox(int width, int height ) {
		hitbox =new Rectangle2D.Float(x,y,width*Game.SCALE,height*Game.SCALE);
		
	}
	protected void drawHitbox(Graphics g) {
		g.setColor(Color.PINK);
		g.drawRect((int) hitbox.x , (int) hitbox.y , (int) hitbox.width ,(int) hitbox.height);
	}
	public void reset() {
		active = true;
		//TODO : add IF (red potion,blue potion ,
	}

	public int getObjType() {
		return objType;
	}

	public Rectangle2D.Float getHitbox() {
		return hitbox;
	}
	public int getxDrawOffset() {
		return xDrawOffset;
	}
	public int getyDrawOffset() {
		return yDrawOffset;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active =active;
	}
	public void setHitbox(Rectangle2D.Float hitbox) {
		this.hitbox = hitbox;
	}


	
}
