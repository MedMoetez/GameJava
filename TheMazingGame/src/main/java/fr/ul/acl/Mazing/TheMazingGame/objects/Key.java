package main.java.fr.ul.acl.Mazing.TheMazingGame.objects;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction.Hero;
import main.java.fr.ul.acl.Mazing.TheMazingGame.start.Game;

public class Key extends GameObject{
	
		private BufferedImage[][] keyAnimation ;
		private BufferedImage keySprite;
		private int[] keySize = {182,206};
		private boolean keyState = false;
		private boolean active = true;
		
		
		
		private float zoom = 0.5f;
		private Game game;
		private Hero hero;
		
		private int aniTick;
		private int aniSpeed = 15;
		private int aniIndex; 
	
		private float hoverOffset;
		private int maxHoverOffset,hoverDir = 1;
		
		public Key(Game game, int x, int y, int objType) {
			super(x, y, objType);
			this.game = game;
			hero = game.getHero();
			initHitbox((int)(keySize[0] * zoom - 10),(int)(keySize[1] * zoom ));
			this.hitbox.height = 20;
			this.hitbox.width =20;
			xDrawOffset = (int) (Game.SCALE * 15);
			yDrawOffset = (int) (Game.SCALE * 15); 
			keySprite = setKeySprite();
			keyAnimation = loadKeyAnimation();
		}
		public BufferedImage setKeySprite() {
			
			try {
				//portailSprite = ImageIO.read(new File("resources/Objects/portail-removebg-preview.png"));
				keySprite = ImageIO.read(new File("resources/key.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return keySprite;
		
	}
	
	public BufferedImage[][] loadKeyAnimation() {
		keyAnimation = new BufferedImage[4][4];
		for (int i=0; i< keyAnimation.length; i++) {
			for (int j=0; j < keyAnimation[i].length; j++) {
				keyAnimation[i][j] = this.getKeyIcon(i, j,keySprite);
			}	
		}
		return keyAnimation;
	}
	public BufferedImage getKeyIcon(int line, int column, BufferedImage heroSpriteSheet) {
		BufferedImage keyIcon;
		keyIcon = keySprite.getSubimage(column * keySize[0], line * keySize[1], keySize[0], keySize[1]);
		return keyIcon;
	}
	public void updateAnimationTick() {
		aniTick ++;
		if (aniTick > aniSpeed) {
			aniTick = 0;
			aniIndex ++;
			
			if (aniIndex >= keyAnimation[0].length ) {
				aniIndex = 0;

			}
		}
	}
	
	 public void update() {
		 if (keyState)
			 updateAnimationTick();
		 
		 
	 }
		public void drawKey(Graphics g) {
			if (keyState && active) {
				update();
				g.drawImage(keyAnimation[0][aniIndex],(int)(hitbox.x - xDrawOffset ), (int)(hitbox.y - yDrawOffset),(int)  (keySize[0] * zoom)/2, (int) (keySize[1] * zoom)/2 , null);
			}
				//drawHitbox(g);
			
		}
	
	/*public void checkKeyState() {
		if (hero.getKill() == (game.getMonsterManager().getMonsters()).size()) {
			keyState = true;
		
		} */
	

	

	public void setKeyState(boolean keyState) {
		this.keyState = keyState;
}
	
	public boolean isKeyState() {
		return keyState;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}

	public void reset() {
		keyState = false;
		active = true;
	}

}

