package main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction;


import main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction.Constants.HeroC;
import main.java.fr.ul.acl.Mazing.TheMazingGame.start.Game;




import static main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction.Constants.HeroC.*;
import static main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction.Constants.TileC.*;
import static main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction.Constants.Directions.*;
import static main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction.Constants.MonsterC;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;



public class Monster extends Entity{

	private BufferedImage monsterSpriteSheetLeft, monsterSpriteSheetRight, monsterIcon;
	private int [] monsterSizeL = {139,93}; //Each sprite is an 139 x 124 images
	private boolean moving = false;
	private boolean attacking = false;
	protected boolean inAir =true;
	protected float fallSpeed;
	protected float gravity = 0.06f * Game.SCALE;	
	private int attackType;
	private BufferedImage [][] animationsLeft, animationsRight;

	private int[][] maze ;

	private int monsterAction = MonsterC.STANDING;
	private float x, y;
	private BufferedImage[][] animationsMonster;
	private int aniTick = 0;
	private int aniSpeed = 15;
	private int aniIndex;
	private int[][] mazeMatrix;
	private Hero hero;
	private int monsterDir = LEFT;
	private int tileY  = (int)(y/mazeBlockSize);
	private int attackDistance = 2*mazeBlockSize;
	private float yDrawoffset = 50;
	private float xDrawoffset = 57;
	protected Rectangle2D.Float attackBox;
	private int maxHp = 30;
	private int currentHp = maxHp;
	private int monsterDmg = 10;
	protected boolean active = true;
	private boolean attackChecked;
	private String monsterString = "monster";
	
	private Game game;
	File file = new File("Level_Up.wav");
	




	public Monster(Game game,float x, float y ,int entity_width,int entity_height){
		super(x,y,entity_width,entity_height);
		this.x = x;
		this.y = y;
		this.game = game;
		monsterSpriteSheetLeft = this.setMonsterSpriteSheetLeft();
		monsterSpriteSheetRight = this.setMonsterSpriteSheetRight();
		this.animationsLeft = loadAnimationsMonsterLeft();
		this.animationsRight = loadAnimationsMonsterRight();
		initHitbox(x,y,entity_width, entity_height);
		mazeMatrix = game.getMaze().getMazeMatrix();
		hero = game.getHero();
		
		initAttackBox();
	}


	private void initAttackBox() {
		// TODO Auto-generated method stub
		attackBox = new Rectangle2D.Float(x,y,attackDistance  , entity_height);
	}


	// importation du spriteSheet : 
	public BufferedImage setMonsterSpriteSheetLeft() {
		
		try {
			monsterSpriteSheetLeft = ImageIO.read(new File("resources/monster_spritesheet/Bringer-of-Death-SpritSheet_no-Effect_left.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return monsterSpriteSheetLeft;
		
	}
	public BufferedImage setMonsterSpriteSheetRight() {
		
		try {
			monsterSpriteSheetRight = ImageIO.read(new File("resources/monster_spritesheet/Bringer-of-Death-SpritSheet_no-effect_right.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return monsterSpriteSheetRight;
		
	}
	
	// obtention de l'icon d'un héro : 
	public BufferedImage getMonsterIcon(int line, int column, BufferedImage monsterSpriteSheet) {
		monsterIcon = monsterSpriteSheet.getSubimage(column * monsterSizeL[0], line * monsterSizeL[1], monsterSizeL[0], monsterSizeL[1]);
		return monsterIcon;
	}

// création des matrices de l'animation de droite et gauche :
	public BufferedImage[][] loadAnimationsMonsterLeft() {
		animationsMonster = new BufferedImage[8][8];
		for (int i=0; i< animationsMonster.length; i++) {
			for (int j=0; j < animationsMonster[i].length; j++) {
				animationsMonster[i][j] = this.getMonsterIcon(i, j, monsterSpriteSheetLeft);
			}	
		}
		return animationsMonster;
	}
	public BufferedImage[][] loadAnimationsMonsterRight() {
		animationsMonster = new BufferedImage[8][8];
		for (int i=0; i< animationsMonster.length; i++) {
			for (int j=0; j < animationsMonster[i].length; j++) {
				animationsMonster[i][animationsMonster[i].length-1-j] = this.getMonsterIcon(i, j, monsterSpriteSheetRight);
			}	
		}
		return animationsMonster;
	}
	
// mise a jour de l'indice de la matrice d'animation	
	
	public void updateAnimationTickMonster() {
		aniTick ++;
		if (aniTick > aniSpeed) {
			aniTick = 0;
			aniIndex ++;
			if (monsterAction == MonsterC.DIE){
				if (aniIndex >= animationsMonster[0].length - 1 ) {
					aniIndex = 0;
					active = false;
					return;
				}
			}
			if (aniIndex >= animationsMonster[0].length) {
				aniIndex = 0;
				if (monsterAction == MonsterC.ATTACK)
					monsterAction = MonsterC.STANDING;
				/*if (monsterAction ==MonsterC.HIT)
					monsterAction = MonsterC.STANDING;*/
				
			}
				
		}
	}
	
	
	public void setAnimation() { // détermination de l'animation : 
		if (monsterAction != MonsterC.DIE) {
			if (moving)
				monsterAction = MonsterC.RUNNING;
			else 
				monsterAction = MonsterC.STANDING;
			if (attacking)
				monsterAction = MonsterC.ATTACK;
		}

				
	}
	public void update() {
		//System.out.println(moving);
		if (!game.getGameOver()) {
			if (active) {
			updatePos();
			setAnimation();
			updateAnimationTickMonster();
			
			updateAttackBox();
			}
		}
	}
	
	private void updateAttackBox() {
		if (monsterDir == RIGHT) {
			attackBox.x = hitbox.x  + (int) hitbox.width;
		}
		else if (monsterDir == LEFT) {
			attackBox.x = hitbox.x - attackDistance ;
		}
		attackBox.y = hitbox.y ;
		
	}
	protected void updateInAir(int[][] lvlData) {
		if (collision_check.CanMoveHere(hitbox.x, hitbox.y + fallSpeed, hitbox.width, hitbox.height, lvlData)) {
			hitbox.y += fallSpeed;
			fallSpeed += gravity;
		} else {
			inAir = false;
			hitbox.y = getEntityYPosUnderRoofOrAboveFloor(hitbox, fallSpeed,monsterString);
			fallSpeed = 0;
			tileY = (int) (hitbox.y / mazeBlockSize);
		}
	}
		


	// dessin du héro : 
	public void drawMonster(Graphics g)  {
		update();
		//System.out.println(fallSpeed);
		if (active) {
			if (monsterDir == LEFT) {
		g.drawImage(animationsLeft[monsterAction][aniIndex],(int)(hitbox.x - xDrawoffset ), (int)(hitbox.y - yDrawoffset), (int) monsterSizeL[0] ,(int)  monsterSizeL[1] , null);
			}
			else {
				g.drawImage(animationsRight[monsterAction][aniIndex],(int)(hitbox.x - xDrawoffset), (int)(hitbox.y - yDrawoffset), (int) monsterSizeL[0]  ,(int)  monsterSizeL[1] , null);
			}

		//drawHitbox(g,(int) hitbox.x,(int)hitbox.y);
		//drawAttackBox(g);
		}
	}
	
	private void drawAttackBox(Graphics g) {
		
		g.setColor(Color.blue);
		g.drawRect((int)(attackBox.x) , (int)attackBox.y , (int)attackBox.width,(int)attackBox.height);
	}


	public boolean isMovingMonster() {
		return moving;
	}

	public void setMovingMonster(boolean moving) {
		this.moving = moving;
	}
	public void setDirectionMonster(int direction) { 
		setMovingMonster(true);
		this.monsterDir = direction;
		
	}
	
	public void setAttackMonster(boolean attack) {
		this.attacking = attack;
		
	}
	public void chooseAttackMonster(int attack) {
		this.attackType = attack;
	}
	
	private void updatePos()  {
		int xSpeed = 2;
		updateMove(mazeMatrix, hero);
		if (inAir) {
			updateInAir(mazeMatrix);
		}
		else {
		if (moving) {
			if (monsterDir == RIGHT) 
				xSpeed = 2;
			else
				xSpeed = -2;
			if (collision_check.CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, mazeMatrix))
				if (collision_check.IsFloor(hitbox, xSpeed, mazeMatrix)) {
					hitbox.x += xSpeed;
					return;
				}
			changeMonsterDir();
			
		}
		
		}
	}

	private void changeMonsterDir() {
		if(monsterDir == LEFT)
			monsterDir = RIGHT;
		else if (monsterDir == RIGHT)
			monsterDir = LEFT;
		
		
	}


	private void updateMove(int[][] mazeMatrix, Hero hero) {
		//zigzag();
		/*switch (monsterAction) {
		case(MonsterC.RUNNING):*/
			//aniTick = 0;
			//aniIndex = 0;
			if (canSeeHero(mazeMatrix, hero)) {
				//System.out.println("can see");
				turnTowardsHero(hero);
				if (isHeroCloseForAttack(hero)) {
					attacking = true;
					moving = false;
					}
				else 
					attacking = false;		
			}
			else {
				attacking = false;
				moving = true;
				}
			if (attacking) {
				if (aniIndex == 0)
					setAttackChecked(false);
				
				if (aniIndex == 5 && !attackChecked)

					game.getAttack().checkHeroHit(hero, this);
			}
			
	}
	
	


	public int flipX() {
		if (monsterDir == RIGHT)
			return monsterSizeL[1];
		else 
			return 0;
			
	}

	public int flipW() {
		if (monsterDir == RIGHT)
			return -1;
		else 
			return 1;
	}
	
	protected void turnTowardsHero(Hero hero) {
		if (hero.getHitbox().x > hitbox.x)
			setDirectionMonster(RIGHT);
		else
			setDirectionMonster(LEFT);
			
	}
	
	public void zigzag() {
		if (monsterDir == RIGHT)
			setDirectionMonster(LEFT);
		else
			setDirectionMonster(RIGHT);	
		}			
	
	
				

	

	public int [] getIconSize() {
		return monsterSizeL;
	}
	
	/////////////////// combat methods ///////////////////
	
	// the monster can see the hero
	protected boolean canSeeHero(int[][] mazeMatrix , Hero hero) {
		int heroTileY = (int)(hero.getHitbox().y / mazeBlockSize);
		tileY = (int)(hitbox.y / mazeBlockSize);
		//System.out.println("heroHitbox: " + heroTileY);
		//System.out.println("monsterHitbox: " + tileY);
		//System.out.println(heroTileY == tileY);
		if ((Math.abs(heroTileY - tileY))<2 ) {
			if (isHeroInRange(hero)) {
				if (isSightClear(mazeMatrix, hitbox, hero.getHitbox(),tileY))
					//System.out.println(true);
					return true;
			}
		}
		return false;
	}

	private boolean isHeroInRange(Hero hero) {
		int distance = (int) Math.abs(hero.getHitbox().x - hitbox.x);
		//System.out.println("distance est : "+distance);
		return (distance <= attackDistance *2);
	}
	
	protected boolean isHeroCloseForAttack(Hero hero) {
		int distance = (int) Math.abs(hero.getHitbox().x - hitbox.x);
		
		return (distance <= attackDistance);
	}


	private boolean isSightClear(int[][] mazeMatrix, Rectangle2D.Float hitbox1, Rectangle2D.Float hitbox2, int tileY) {
		int tileX1 = (int) (hitbox1.x / mazeBlockSize);
		int tileX2 = (int) (hitbox2.x / mazeBlockSize);
		if (tileX1 < tileX2) {
			for (int i = 0 ; i< tileX2 - tileX1; i++) {
				if (isTileSolid(tileX1 + i, tileY, mazeMatrix))
						return false;
			}
		}
		else {
			for (int i = 0 ; i< tileX1 - tileX2; i++) {
				if (isTileSolid(tileX2 + i, tileY, mazeMatrix))
						return false;
			}
		}
		return true;
	}
	
	public boolean isTileSolid(int xTile, int yTile, int[][] mazeMatrix) {
		int value = mazeMatrix[yTile][xTile];
		return (value == 1);
	}
	
	protected void hurt(int value) {
		currentHp -= value;
		if (currentHp <= 0) {
			hero.setKill(hero.getKill()+1);
			monsterAction = MonsterC.DIE;
		}
		else
			monsterAction = MonsterC.HIT;
		
	}
	
	public boolean isActive() {
		return active;
	}
	
	public Rectangle2D.Float getAttackBox() {
		return attackBox;
	}


	public void resetMonster() {
		hitbox.x = x;
		hitbox.y = y;
		//firstUpdate = true;
		currentHp = maxHp;
		active =true;
		monsterAction = MonsterC.RUNNING;
		moving = true;
		fallSpeed = 0;
		inAir = true;
		
	}


	public int getMonsterDmg() {
		return monsterDmg;
	}


	public void setMonsterDmg(int monsterDmg) {
		this.monsterDmg = monsterDmg;
	}

	public void setAttackChecked(boolean attackChecked) {
		this.attackChecked = attackChecked;
	}



	

}