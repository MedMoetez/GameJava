package main.java.fr.ul.acl.Mazing.TheMazingGame.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction.Hero;
import main.java.fr.ul.acl.Mazing.TheMazingGame.start.Game;
import static main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction.Constants.Directions.*;
public class Fireball {
	
	private Hero hero;
	private Rectangle2D.Float hitbox;
	private int x,y;
	private int dir;
	private float fireballHeight = 65;
	private float fireballWidth = 75;
	private int speed = 5;
	private int fireballRange = 500;
	private boolean active = false;
	
	private Rectangle2D.Float heroHitbox;
	private BufferedImage fireballSpriteSheet;
	private BufferedImage[] fireballAnimation;
	private int aniTick = 0;
	private int aniSpeed = 15;
	private int aniIndex = 0;
	private float yOffset = 30;
	
	public Fireball(Hero hero) {
		this.hero = hero;
		heroHitbox = hero.getHitbox();
		x = (int)(heroHitbox.x + heroHitbox.width);
		y = (int)(heroHitbox.y + (heroHitbox.height/2));
		hitbox = new Rectangle2D.Float(x,y,fireballWidth,fireballHeight);
		setDirection();
		loadFireballSpriteSheet();
		loadAnimations();
	}
	
	public void setDirection() {
		int heroDir = hero.getHeroDir();
		if (heroDir == RIGHT)
			dir = 1;
		else
			dir = -1;
	}
	
	public void updatePos() {
		if (active) {
			if (checkFireballRange())
				hitbox.x += (dir * speed);
			else {
				active = false;
				hero.setAttack(false);
			}
		}
	}
	
	public boolean checkFireballRange() {
		return (Math.abs(x - hitbox.x)<= fireballRange);
	}
	
	public void setPos(int x, int y) {
		hitbox.x = x;
		hitbox.y = y;
	}
	public void loadFireballSpriteSheet() {
		try {
			fireballSpriteSheet = ImageIO.read(new File("resources/Objects/fireball_spritesheet.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadAnimations() {
		fireballAnimation = new BufferedImage[6];
		for (int i=0; i< fireballAnimation.length; i++) {
				fireballAnimation[i] = getFireballIcon(i);
			}	
	}
	
	public BufferedImage getFireballIcon(int column) {
		BufferedImage fireballIcon;
		fireballIcon = fireballSpriteSheet.getSubimage(column * 512, 0, 512,512); // each sprite is 512x512 px
		return fireballIcon;
	}
	
	
	
	public void updateAnimationTick() {
		aniTick ++;
		if (aniTick > aniSpeed) {
			aniTick = 0;
			aniIndex ++;
			
			if (aniIndex >= fireballAnimation.length ) {
				aniIndex = 0;

			}
		}
	}
	
	public void update() {
		if (active) {
		updatePos();
		updateAnimationTick();
		}
	}
	
	public void drawFireball(Graphics g) {
		//System.out.println(active);
		if (active) {
			update();
			if (dir == 1)
				g.drawImage(fireballAnimation[aniIndex], (int) (hitbox.x), (int) (hitbox.y - yOffset ), (int) (fireballWidth * dir),(int) fireballHeight, null);
			else
				g.drawImage(fireballAnimation[aniIndex], (int) (hitbox.x + hitbox.width), (int) (hitbox.y - yOffset ), (int) (fireballWidth * dir),(int) fireballHeight, null);
			//drawHitbox(g);
		}
	}
	private void drawHitbox(Graphics g) {
		g.setColor(Color.RED);
		g.drawRect((int) hitbox.x , (int) (hitbox.y - yOffset ) , (int) (hitbox.width ) ,(int) hitbox.height);
		
	}
	
	
	
	
	public Rectangle2D.Float getHitbox() {
		return hitbox;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
		resetFireball();
	}
	private void resetFireball() {
		x = (int)(heroHitbox.x + heroHitbox.width);
		y = (int)(heroHitbox.y + (heroHitbox.height/2));
		hitbox = new Rectangle2D.Float(x,y,fireballWidth,fireballHeight);
		setDirection();
		aniTick = 0;
		aniIndex = 0;
	}
	
	
	

}
