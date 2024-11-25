package main.java.fr.ul.acl.Mazing.TheMazingGame.start;



import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import main.java.fr.ul.acl.Mazing.TheMazingGame.environment.EnvironmentManager;
import main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction.Attack;
import main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction.GameOverOverlay;
import main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction.Hero;
import main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction.Maze;
import main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction.Monster;
import main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction.MonsterManager;
import main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction.TileManager;
import main.java.fr.ul.acl.Mazing.TheMazingGame.graphics.GamePannel;
import main.java.fr.ul.acl.Mazing.TheMazingGame.graphics.GameWindow;
import main.java.fr.ul.acl.Mazing.TheMazingGame.objects.Fireball;
import main.java.fr.ul.acl.Mazing.TheMazingGame.objects.Key;
import main.java.fr.ul.acl.Mazing.TheMazingGame.objects.ObjectManager;

import static main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction.Constants.MonsterC.*;
import static main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction.Constants.ObjectConstants.KEY;
import static main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction.Constants.TileC.mazeBlockSize;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction.Constants.HeroC.*;
public class Game implements Runnable {
	private GameWindow gameWindow;
	private GamePannel gamePannel;
	private Thread gameThread;
	private GameOverOverlay gameOverOverlay;
	private TileManager tileM;
	private Hero hero;
	private Monster monster;
	private MonsterManager monsterManager;
	private Monster monster1;
	private ObjectManager objectManager;
	private static int dificulty =0;
	private final int FPS_SET = 120;
	private Maze maze;
	private Attack attack;
	public int currentLevel = 0;
	private boolean transition = false;
	private boolean gameWin = false;
	private boolean gameOver = false;
	private Fireball fireball;
	private EnvironmentManager eManager;
	public final static float SCALE = 1f;
	

	public Game() {
		
	}
	
	protected void startGame() {
		initClasses();
		gamePannel = new GamePannel(this);
		gameWindow = new GameWindow(gamePannel);
		gamePannel.requestFocus();
		gamePannel.requestFocus();
		gamePannel.setFocusable(true);
        gamePannel.requestFocusInWindow();
		int difficulty = Game.getDificulty();
		startGameLoop();
	}
	
	public void initClasses() {
		maze = new Maze(this);
		tileM = new TileManager(this);
		hero = initHero(); //70 =( hero_height - xDrawoffset)*zoom
		monsterManager = new MonsterManager(this);
		objectManager =new ObjectManager(this);
		gameOverOverlay = new GameOverOverlay(this);
		//eManager =  new EnvironmentManager(this);
		attack = new Attack(this);

	}
	
	private Hero initHero() {
		hero = new Hero(this,heroX0 ,heroY0,20,32);
		hero.spawn(dificulty);
		return hero;
		
	}
	
	//////////////  création de la boucle : /////////////////
	private void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
		
	}
	@Override
	public void run() {
		double timeFrame = 1000000000.0/ FPS_SET; //in nanosec
		long lastFrame = System.nanoTime();
		long NOW = System.nanoTime();
		
		int frames = 0;
		long lastCheck = System.currentTimeMillis();
		while (true) {
			NOW = System.nanoTime();
			if ( NOW - lastFrame > timeFrame ) {
				gamePannel.repaint();
				lastFrame = NOW;
				frames++;
			}
			if (System.currentTimeMillis() - lastCheck >= 1000 ) {
				lastCheck = System.currentTimeMillis();
				System.out.println("FPS : " + frames);
				frames = 0;	
			}
		}	
	}
	
	public void resetAll() {
		// reset hero, monster,potions
		gameOver = false;
		maze.reset();
		hero.resetAll();
		monsterManager.resetMonsters(); //replace it with monsterManager
		objectManager.resetAllObjects();
		tileM.resetTileManager();
		transition = false;
		
	}
	
	public int getFPS_SET() {
		return FPS_SET;
	}
	public boolean isTransition() {
		return transition;
	}

	public TileManager getTileM() {
		return this.tileM;
	}

	public Hero getHero() {
		return this.hero;
	}
	public Monster getMonster() {
		return this.monster;
	}
	public Monster getMonster1() {
		return monster1;
	}
	public Maze getMaze() {
		return this.maze;
	}
	public Attack getAttack() {
		return this.attack;
	}
	public ObjectManager getObjectManager() {
		return this.objectManager;
	}

	public boolean getGameOver() {
		return gameOver;
	}
	public EnvironmentManager geteManager() {
		return eManager;
	}
	public void checkTouched(Rectangle2D.Float hitbox) {
		objectManager.checkObjectTouched(hitbox);
	}
	

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public GameOverOverlay getGameOverOverlay() {
		return gameOverOverlay;
	}

	public static int getDificulty() {
		return dificulty;
	}

	public void setDificulty(int dificulty) {
		this.dificulty = dificulty;
	}

	public MonsterManager getMonsterManager() {
		return monsterManager;
	}

	public Fireball getFireball() {
		// TODO Auto-generated method stub
		return fireball;
	}

	public void setTransition(boolean transition) {
		this.transition = transition;
	}

	public boolean isGameWin() {
		return gameWin;
	}

	public void setGameWin(boolean gameWin) {
		this.gameWin = gameWin;
	}
	

}

