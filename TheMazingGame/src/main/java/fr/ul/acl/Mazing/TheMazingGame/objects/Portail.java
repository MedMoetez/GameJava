package main.java.fr.ul.acl.Mazing.TheMazingGame.objects;

import static main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction.Constants.Directions.LEFT;
import static main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction.Constants.ObjectConstants.KEY;
import static main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction.Constants.TileC.mazeBlockSize;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction.Constants.HeroC;
import main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction.Hero;
import main.java.fr.ul.acl.Mazing.TheMazingGame.start.Game;
public class Portail extends GameObject{
	
	private int aniTick;
	private int aniSpeed = 15;
	private int aniIndex;
	private BufferedImage[][] portailAnimation ;
	private BufferedImage portailSprite;
	private BufferedImage portalStatic;
	//private int[] portailSize = {158,131};
	private int[] portailSize = {153,131};
	private float zoom = 0.5f;
	private boolean portalState = false;
	
	private Game game;
	private Hero hero;
	
	private boolean portalOpen = false;
	
	
	public Portail(Game game,int x, int y, int objType) {
		super(x, y, objType);
		this.game = game;
		hero = game.getHero();
		initHitbox((int)(portailSize[0] * zoom - 10),(int)(portailSize[1] * zoom ));
		xDrawOffset = (int) (Game.SCALE * 0);
		yDrawOffset = (int) (Game.SCALE * 0); 
		portailSprite = setPortailSprite();
		portailAnimation = loadPortailAnimation();
	}
	public BufferedImage setPortailSprite() {
		
			try {
				//portailSprite = ImageIO.read(new File("resources/Objects/portail-removebg-preview.png"));
				portailSprite = ImageIO.read(new File("resources/portal.png"));
				portalStatic = ImageIO.read(new File("resources/portal_static.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return portailSprite;
		
	}
	
	public BufferedImage[][] loadPortailAnimation() {
		portailAnimation = new BufferedImage[2][6];
		for (int i=0; i< portailAnimation.length; i++) {
			for (int j=0; j < portailAnimation[i].length; j++) {
				portailAnimation[i][j] = this.getPortailIcon(i, j,portailSprite);
			}	
		}
		return portailAnimation;
	}
	public BufferedImage getPortailIcon(int line, int column, BufferedImage heroSpriteSheet) {
		BufferedImage portailIcon;
		portailIcon = portailSprite.getSubimage(column * portailSize[0], line * portailSize[1], portailSize[0], portailSize[1]);
		return portailIcon;
	}
	public void updateAnimationTick() {
		aniTick ++;
		if (aniTick > aniSpeed) {
			aniTick = 0;
			aniIndex ++;
			
			if (aniIndex >= portailAnimation[0].length ) {
				aniIndex = 0;

			}
		}
	}
	
	 public void update() {
		 if (portalState)
			 updateAnimationTick();
		 checkPortalReached(hero);
		 
	 }
	public void drawPortail(Graphics g) {
		update();
		if (!portalState)
			g.drawImage(portalStatic,(int)(hitbox.x - xDrawOffset), (int)(hitbox.y - yDrawOffset),(int)  (portailSize[0] * zoom), (int) (portailSize[1] * zoom) , null);
		else
			g.drawImage(portailAnimation[0][aniIndex],(int)(hitbox.x - xDrawOffset), (int)(hitbox.y - yDrawOffset),(int)  (portailSize[0] * zoom), (int) (portailSize[1] * zoom) , null);
		//drawHitbox(g);
	}
	
	public void checkPortalReached(Hero hero) {
		if (hitbox.intersects(hero.getHitbox()) && portalOpen ) {
			if (game.getDificulty()==3) {
				game.setGameWin(true);
			    game.setGameOver(true);
			}
			else {
			game.setDificulty(game.getDificulty() + 1); // Mettre à jour le niveau de difficulté
            game.setTransition(true); 
			game.resetAll();
            // Réinitialiser le jeu pour le nouveau niveau
			}
		}
	}
	
	public void keyPressed(KeyEvent e) {
		if (!game.getObjectManager().getKey().isActive()) { 
			portalState = true;
			if (e.getKeyCode() == KeyEvent.VK_F)
				portalOpen = true;
		}
	}
	
	public void reset() {
		portalOpen = false;
		portalState = false;
	}
	
	public void setPortalState(boolean portalState) {
		this.portalState = portalState;
	}

}
