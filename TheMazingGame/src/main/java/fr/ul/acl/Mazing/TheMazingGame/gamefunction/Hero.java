package main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction;

import main.java.fr.ul.acl.Mazing.TheMazingGame.start.Game;
import main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction.Constants.HeroC;
import main.java.fr.ul.acl.Mazing.TheMazingGame.objects.Fireball;
import main.java.fr.ul.acl.Mazing.TheMazingGame.start.Game;

import static main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction.Constants.Directions.*;
import static main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction.Constants.TileC.*;
import static main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction.Constants.HeroC.*;




import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Hero extends Entity {
	
	private Game game;
	private BufferedImage heroSpriteSheet;
	private int [] heroSize = {50,37}; //Each sprite is an 50 x 37 images

	private int heroDir = -1;


	private boolean moving = false;
	private boolean attacking = false;
	private int attackType;
	private BufferedImage [][] animationsRight, animationsLeft;
	private int heroAction = HeroC.STANDING;
	private int[][] maze ;
	private int zoom = 2;
	private float xDrawoffset = 21*zoom ;
	private float yDrawoffset = 15*zoom ;
	private int aniTick;
	private int aniSpeed = 15;
	private int aniIndex;  

	private float playerSpeed = 3.0f;
	
	//gravity and jump
	private boolean left, up, right, down, jump;
	private boolean inAir = true;
	private float airSpeed = 0f;
	private float gravity = 0.05f * zoom;
	private float jumpSpeed = -2.15f *zoom;
	private float fallSpeedAfterCollision = 0.5f * zoom;
	private String heroString = "hero";
	private Fireball fireball;


	//HealthBar attributes : 
	private BufferedImage statusBarImg;

	private int statusBarWidth = (int) (192 * Game.SCALE);
	private int statusBarHeight = (int) (58 * Game.SCALE);
	private int statusBarX = (int) (10 * Game.SCALE);
	private int statusBarY = (int) (10 * Game.SCALE);

	private int healthBarWidth = (int) (150 * Game.SCALE);
	private int healthBarHeight = (int) (4 * Game.SCALE);
	private int healthBarXStart = (int) (34 * Game.SCALE);
	private int healthBarYStart = (int) (14 * Game.SCALE);
	
	private int attackBarWidth = (int) (102 * Game.SCALE);
	private int attackBarHeight = (int) (3 * Game.SCALE);
	private int attackBarXStart = (int) (55 * Game.SCALE);
	private int attackBarYStart = (int) (44 * Game.SCALE);
	
	private int maxHP = 100;
	private int currentHP = maxHP;
	private int healthWidth = healthBarWidth;
	private int updateDmgTime;
	private int dmgTick = 0;
	private int maxDmg = 30;
	private int currentDmg = (int)(maxDmg/2);
	private int dmgBeforeFireball = currentDmg;
	private int attackWidth = attackBarWidth;

	//AttackBox
	private Rectangle2D.Float attackBox;

	


	private int flipX = 0;
	private int flipW = 1;
	private boolean attackChecked;
	private int attackDistance = (int) (1.3 * mazeBlockSize);
	
	private int kill =0;
	private int dmgPerSec = 2;
	private boolean FireballDmgUpdate = false;
	private boolean fireballKeyPressed = false;


	public Hero(Game game,float x, float y ,int entity_width,int entity_height){
		super(x,y,entity_width,entity_height);
		this.game = game;
		updateDmgTime = 2 * game.getFPS_SET();
		this.animationsRight = loadAnimationsRight();
		this.animationsLeft = loadAnimationsLeft();
		loadHealthBar();
		initHitbox(x,y,20, 32);
		initAttackBox();
		fireball = new Fireball(this);
		 //zone solide dans le hero :
	}

	

	// importation du spriteSheet : 
	public BufferedImage setHeroSpriteSheetRight() {
		
		try {
			heroSpriteSheet = ImageIO.read(new File("resources/hero_spritesheet/adventurer_Sheet.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return heroSpriteSheet;
		
	}
	public BufferedImage setHeroSpriteSheetLeft() {
		
		try {
			heroSpriteSheet = ImageIO.read(new File("resources/hero_spritesheet/adventurer_Sheet_Left.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return heroSpriteSheet;
		
	}
	
	// obtention de l'icon d'un héro : 
	
	
	public BufferedImage getHeroIcon(int line, int column, BufferedImage heroSpriteSheet) {
		BufferedImage heroIcon;
		heroIcon = heroSpriteSheet.getSubimage(column * heroSize[0], line * heroSize[1], heroSize[0], heroSize[1]);
		return heroIcon;
	}

	// création des matrices de l'animation de droite et gauche :
		public BufferedImage[][] loadAnimationsRight() {
			animationsRight = new BufferedImage[11][7];
			for (int i=0; i< animationsRight.length; i++) {
				for (int j=0; j < animationsRight[i].length; j++) {
					animationsRight[i][j] = this.getHeroIcon(i, j, this.setHeroSpriteSheetRight());
				}	
			}
			return animationsRight;
		}
		public BufferedImage[][] loadAnimationsLeft() {
			animationsLeft = new BufferedImage[11][7];
			for (int i=0; i< animationsLeft.length; i++) {
				for (int j=0; j < animationsLeft[i].length; j++) {
					animationsLeft[i][animationsLeft[i].length - 1-j] = this.getHeroIcon(i, j,this.setHeroSpriteSheetLeft());
				}	
			}
			return animationsLeft;
		}
		public void updateAnimationTick() {
			aniTick ++;
			if (aniTick > aniSpeed) {
				aniTick = 0;
				aniIndex ++;
				if (getHeroAction() == HeroC.DIE) {
					if (aniIndex >= animationsRight[0].length - 1) {
						aniIndex = animationsRight[0].length - 2;
						game.setGameOver(true);
						return;
						
					}
					
					
				}
				if (heroAction == HeroC.PROJECTILE ) {
					//System.out.println("fireball ready");
					//System.out.println("aniIndex" + aniIndex);
					if( aniIndex == 1) {
						//fireball = game.getFireball();
						//System.out.println("truueee");
						fireball.setActive(true);
					}
						
				}
				if (aniIndex >= animationsRight[0].length ) {
					aniIndex = 0;
					attackChecked = false;
				}
			}
		}
		// détermination de l'animation : 
	public void setAnimation() { 
		 
		int startAni = heroAction;
		if (heroAction != HeroC.DIE) {
			
			if (attacking ) {
				heroAction = attackType;
				if (heroAction == HeroC.PROJECTILE) {
					if (!fireballKeyPressed) {
						if (moving)
							heroAction = HeroC.RUNNING;
						else 
							heroAction = HeroC.STANDING;
					}
						
				}
			}
				
			
			else {
				if (moving)
					heroAction = HeroC.RUNNING;
				else 
					heroAction = HeroC.STANDING;
			}
		}
		if (startAni != heroAction) {
			aniTick = 0;
			if (heroAction == HeroC.PROJECTILE) {
				aniIndex = 3;
				FireballDmgUpdate = false;
			}
			else if (heroAction == HeroC.DOUBLE_ATTACK)
				aniIndex = 3;
			else
				aniIndex = 0;
		}
			
	}

	



	public void updateDmgFireball() {
		
		if (fireball.isActive() && !FireballDmgUpdate) {
			if (currentDmg <(int) (maxDmg/2) ) {
				fireball.setActive(false);
				//attacking = false;
				//System.out.println("not enough");
				return;
			}
				dmgBeforeFireball = currentDmg;
				changeDmg(-(int) (maxDmg/2));
				dmgTick = 0;
				//System.out.println(currentDmg);
				FireballDmgUpdate = true;
		}
	}
	public void update() {

	
		if (!game.getGameOver()) {

			updateHP();
			updateAttackBox();
			updatePos();
			
			if (moving)
				checkTouched();
			if (attacking) {
				updateAttackBox();
				checkAttack();
			}
			setAnimation();
			updateAnimationTick();
			updateDmgPerSec();
			updateDmg();
			updateDmgFireball();
		}
	}
	
	public void updateDmgPerSec() {
		dmgTick ++;
		if (dmgTick >= updateDmgTime) {
			
			if (currentDmg <= (int) (maxDmg / 2)) {
				changeDmg(dmgPerSec);
			}
			
			dmgTick = 0;
		}	
	}

	

	// dessin du héro : 
	public void drawHero(Graphics g)  {
		update();

		if (heroDir == LEFT)
			g.drawImage(animationsLeft[heroAction][aniIndex],(int)(hitbox.x - xDrawoffset), (int)(hitbox.y - yDrawoffset), (int) (heroSize[0] * zoom),(int)  (heroSize[1] * zoom) , null);
		else 
			g.drawImage(animationsRight[heroAction][aniIndex],(int)(hitbox.x - xDrawoffset), (int)(hitbox.y - yDrawoffset),(int)  (heroSize[0] * zoom), (int) (heroSize[1] * zoom) , null);
		drawHealthBar(g);
		drawDamageBar(g);
		fireball.drawFireball(g);
		//drawHitbox(g,(int) hitbox.x,(int)hitbox.y);
		//drawAttackBox(g);
	}
	
	
	
		
	private void checkTouched() {
		game.checkTouched(hitbox);
	}
	

	private void checkAttack() {
		if (!fireball.isActive()) {
			if (attackChecked || aniIndex!= 2 )
				return;
		}
		game.getAttack().checkMonstersHit(this.attackBox);
		attackChecked = true;
		
	}



	private void updateAttackBox() {
		if (getFireball().isActive()) {
			Rectangle2D.Float fireballHitbox = getFireball().getHitbox();
			attackBox.x = fireballHitbox.x;
			attackBox.y = fireballHitbox.y;
			attackBox.width =fireballHitbox.width;
			attackBox.height = fireballHitbox.height;
		}
			
		else {
			if (right) {
				attackBox.x = hitbox.x  + (int) hitbox.width;
			}
			else if (left) {
				attackBox.x = hitbox.x - attackDistance;
			}
			attackBox.y = hitbox.y;
			attackBox.width = attackDistance;
			attackBox.height = hitbox.height;
		}
		
	}



	private void updateHP() {
		healthWidth = (int) ((currentHP/(float) maxHP) * healthBarWidth);
		
	}
	
	public void changeHP(int value) {
		currentHP += value;
		if (currentHP > maxHP)
			currentHP = maxHP;
		else if (currentHP <= 0) {
			currentHP = 0;
			heroAction = HeroC.DIE;
			//gameOver();
		}
	}
	
	public void changeDmg(int value) {
		currentDmg += value;
		if (currentDmg > maxDmg)
			currentDmg = maxDmg;
		if (currentDmg <=0)
			currentDmg = 0;
	}
	
	private void updateDmg() {
		attackWidth = (int) ((getCurrentDmg()/ (float) getMaxDmg()) * attackBarWidth);
	}
	
	private void initAttackBox() {
		attackBox = new Rectangle2D.Float(x,y, attackDistance ,entity_height );
		
	}

	// dessin du héro : 
	
	
	
	private void drawHealthBar(Graphics g) {
		g.drawImage(statusBarImg, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
		g.setColor(Color.red);
		g.fillRect(healthBarXStart + statusBarX, healthBarYStart + statusBarY, healthWidth, healthBarHeight);
	}
	
	private void drawDamageBar(Graphics g) {
		g.setColor(Color.YELLOW);
		g.fillRect(attackBarXStart, attackBarYStart, attackWidth , attackBarHeight);
		
	}
	private void drawAttackBox(Graphics g) {
		g.setColor(Color.blue);
		g.drawRect((int)(attackBox.x) , (int)attackBox.y , (int)attackBox.width,(int)attackBox.height);
		
	}



	//////////////////////// Méthode de déplacement du héros : /////////////////// 
	
	public boolean isMoving() {
		return moving;
	}

	
	public void setAttack(boolean attack) {
		this.attacking = attack;
	}
	public void chooseAttack(int attack) {
		this.attackType = attack;
	}
	
	private void updatePos() {
		
		maze = game.getMaze().getMazeMatrix();
		moving = false;
		
		
		if(jump) {
			jump();
			inAir = true;
			}
		
		if(!left && !right && !inAir)
			return;
		
		float xSpeed = 0;
		
		if(left) {
			xSpeed -= playerSpeed ;
			moving = true;}
		
		if (right) {
			xSpeed += playerSpeed;
			moving = true;
			}
		
		if (!inAir)
			if (!IsEntityOnFloor(hitbox, maze))
				inAir = true;
			
		
		
		
		if(inAir) {
			if(collision_check.CanMoveHere( hitbox.x, hitbox.y + airSpeed , hitbox.width , hitbox.height,maze)){
				hitbox.y += airSpeed;
				airSpeed += gravity;
				updateXpos(xSpeed);
				
			}else {
				hitbox.y = getEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed,heroString);
				if(airSpeed >0)
					resetInAir();
				else {
					airSpeed = fallSpeedAfterCollision;
				updateXpos(xSpeed);
			}
			
			}	
		}else 
			updateXpos(xSpeed);
		moving = true;
		
	}
		
		


	
	private void jump() {
		if(inAir)
			return;
		inAir = true;
		airSpeed = jumpSpeed;
	}

	
	
	private void resetInAir() {
		inAir = false;
		airSpeed = 0;
	}

	

		
		
	private void updateXpos(float xSpeed) {
		
		if(collision_check.CanMoveHere( hitbox.x + xSpeed, hitbox.y , hitbox.width , hitbox.height,maze))
			hitbox.x += xSpeed;
	else{
		hitbox.x = getEntityXPosNextToWall(hitbox, xSpeed);
	}
	}
	//importation bar de vie :
	private void loadHealthBar() {
		try {
			statusBarImg = ImageIO.read(new File("resources/health_power_bar.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	


	public int [] getIconSize() {
		return heroSize;
	}
	public Rectangle2D.Float getAttackBox() {
		return attackBox;
	}
	public int flipW() {
		if (heroDir == RIGHT)
			return 1;
		else 
			return -1; 
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
		heroDir = LEFT;
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
		heroDir = RIGHT;
	}


	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}
	
	public void setJump(boolean jump) {
		this.jump =  jump;
	}
	
	public boolean isFireballKeyPressed() {
		return fireballKeyPressed;
	}



	public void setFireballKeyPressed(boolean fireballKeyPressed) {
		this.fireballKeyPressed = fireballKeyPressed;
	}



	public int getHeroDmg() {
		return getCurrentDmg();
	}
	public int getHeroDir() {
		return heroDir;
	}
	public void setHeroDmg(int heroDmg) {
		this.setCurrentDmg(heroDmg);
	}
	public void setHeroDir(int heroDir) {
		this.heroDir = heroDir;
	}



	public void resetAll() {
		//inAir = false
		attacking = false;
		moving = false;
		heroAction = HeroC.STANDING;
		if (!game.isTransition())
			currentHP = maxHP;
		int difficulty = game.getDificulty();
		spawn(difficulty);
		inAir = true;
		kill =0;
		
	}

	public int getHeroAction() {
		return heroAction;
	}


	public Fireball getFireball() {
		return fireball;
	}
	
	
	public int getKill() {
		return kill;
	}

	public void setKill(int kill) {
		this.kill = kill;
	}
	public void spawn(int difficulty) {
		switch(difficulty) {
		case 0:
			hitbox.x = heroX0;
			hitbox.y = heroY0;
			break;
		case 1:
			hitbox.x = heroX1;
			hitbox.y = heroY1;
			break;
			
		case 2:
			hitbox.x = heroX2;
			hitbox.y = heroY2;
			break;
			
		case 3:
			hitbox.x = heroX3;
			hitbox.y = heroY3;
			break;
		default:
			hitbox.x = heroX0;
			hitbox.y = heroY0;
		}
	}



	public int getMaxDmg() {
		return maxDmg;
	}



	public int getCurrentDmg() {
		return currentDmg;
	}



	public void setCurrentDmg(int currentDmg) {
		this.currentDmg = currentDmg;
	}



	public int getDmgBeforeFireball() {
		return dmgBeforeFireball;
	}
}

