package main.java.fr.ul.acl.Mazing.TheMazingGame.objects;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import main.java.fr.ul.acl.Mazing.TheMazingGame.start.Game;
import static main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction.Constants.ObjectConstants.*;
import static main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction.Constants.TileC.*;
public class ObjectManager {
	
	private Game game ;
	private BufferedImage[] potionImgs;
	private ArrayList<Potion> potions;
	private Portail portail;
	private Key key;
	private int difficulty;
	private int kill;
	private int numberOfMonsters;
	
	public ObjectManager(Game game) {
		this.game = game;
		potions = new ArrayList<>();
		loadImgs();
		this.difficulty = game.getDificulty();
		initPortal();
		key = new Key(game,10* mazeBlockSize,(int)(2* mazeBlockSize),KEY);
		generateObjects();
		
	}
	
	private void initPortal() {
		portail = new Portail(game,10* mazeBlockSize,(int)(2.5* mazeBlockSize),PORTAIL);
	}
	
	public void generateObjects() {
		
		switch(this.difficulty) {
		case 0:
			
			potions.add(new Potion(75,130,RED_POTION));
			potions.add(new Potion(1080,540,GREEN_POTION));
		   // potions.add(new Potion(300,300,PINK_POTION));
		
			portail.getHitbox().x = 10* mazeBlockSize;
			portail.getHitbox().y = (float) 2.6* mazeBlockSize;
			
			key.getHitbox().x = 31* mazeBlockSize;
			key.getHitbox().y = (float) 10* mazeBlockSize;
			break;
		case 1:
			potions.add(new Potion(60,140,RED_POTION));
			potions.add(new Potion(460,140,GREEN_POTION));
			potions.add(new Potion(520,380,GREEN_POTION));
			//potions.add(new Potion(300,300,PINK_POTION));
			portail.getHitbox().x = 29* mazeBlockSize;
			portail.getHitbox().y = (float) (6.6* mazeBlockSize);
			//portail = new Portail(game,29* mazeBlockSize,(int)(6.5* mazeBlockSize),PORTAIL);
			key.getHitbox().x = 2* mazeBlockSize;
			key.getHitbox().y = (float) 11* mazeBlockSize;
			break;
		case 2:
			potions.add(new Potion(1210,540,RED_POTION));
			potions.add(new Potion(50,380,GREEN_POTION));
			potions.add(new Potion(1210,220,GREEN_POTION));
			//potions.add(new Potion(300,300,PINK_POTION));
			portail.getHitbox().x = 16* mazeBlockSize;
			portail.getHitbox().y = (float) (0.6* mazeBlockSize);
			//portail = new Portail(game,16* mazeBlockSize,(int)(0.5* mazeBlockSize),PORTAIL);
			key.getHitbox().x = 8* mazeBlockSize;
			key.getHitbox().y = (float) 15* mazeBlockSize;
			break;
		case 3:
			potions.add(new Potion(600,450,RED_POTION));
			potions.add(new Potion(600,135,GREEN_POTION));
			potions.add(new Potion(600,18,RED_POTION));
			//potions.add(new Potion(300,300,PINK_POTION));

			portail.getHitbox().x = 29* mazeBlockSize;
			portail.getHitbox().y = (float) (17.7* mazeBlockSize);

			//portail = new Portail(game,10* mazeBlockSize,(int)(2.5* mazeBlockSize),PORTAIL);
			key.getHitbox().x = (float) 30.3* mazeBlockSize;
			key.getHitbox().y = (float) 7* mazeBlockSize;
			break;
		}	
	}
	public void checkObjectTouched(Rectangle2D.Float hitbox) {
		for (Potion p : potions) {
			if (p.isActive()) {
				if (hitbox.intersects(p.getHitbox())) {
					p.setActive(false);
					applyEffectToPlayer(p);
				}
			}
		}
		kill = game.getHero().getKill();
		numberOfMonsters = game.getMonsterManager().getMonsters().size();
		if (kill == numberOfMonsters) {
			this.key.setKeyState(true);
			if (key.isActive()) {
				if (hitbox.intersects(key.getHitbox())) {
					this.key.setActive(false);
					portail.setPortalState(true);
				}
			}
		}
	}

	private void applyEffectToPlayer(Potion p) {
		if (p.getObjType() == GREEN_POTION) {
			game.getHero().changeHP(GREEN_POTION_VALUE);
		}
		else if (p.getObjType() == RED_POTION) {
			game.getHero().changeDmg(RED_POTION_VALUE);
		}
	}
	private void loadImgs() {
		try {
			potionImgs = new BufferedImage[3];
			potionImgs[0]= ImageIO.read(new File("resources/Objects/Icon48.png"));//RED POTION
			potionImgs[1]= ImageIO.read(new File("resources/Objects/Icon43.png"));//PINK Potion
			potionImgs[2]= ImageIO.read(new File("resources/Objects/Icon31.png"));//Green potion
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		public void update() {
			for (Potion p : potions) {
				if (p.isActive()) {
					p.update();
				}
			}
			
		}
		public void draw(Graphics g) {
			drawPotions(g);
			portail.drawPortail(g);
			//game.checkKeyState();
			key.drawKey(g);
		}

		private void drawPotions(Graphics g) {
			for (Potion p : potions)
				if (p.isActive()) {
					
					int type = 0;
					if (p.getObjType() == PINK_POTION)
						type = 1;
					else if(p.getObjType() == GREEN_POTION)
						type = 2;
					g.drawImage(potionImgs[type],(int) (p.hitbox.x -p.getxDrawOffset()),(int) (p.hitbox.y -p.getyDrawOffset()),POTION_WIDTH,POTION_HEIGHT,null);
					//p.drawHitbox(g);
					p.update();
					
				}
		}
		
	public void resetAllObjects() {
		difficulty = game.getDificulty();
		potions.clear();
		generateObjects();
		portail.reset();
		key.reset();
	}
	public Portail getPortail() {
		return portail;
	}
	
	public Key getKey() {
		return key;
	}
	

}


