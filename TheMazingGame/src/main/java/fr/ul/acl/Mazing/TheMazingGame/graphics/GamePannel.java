package main.java.fr.ul.acl.Mazing.TheMazingGame.graphics;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

import main.java.fr.ul.acl.Mazing.TheMazingGame.environment.EnvironmentManager;
import main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction.GameOverOverlay;
import main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction.Hero;
import main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction.Maze;
import main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction.Monster;
import main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction.MonsterManager;
import main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction.TileManager;
import main.java.fr.ul.acl.Mazing.TheMazingGame.inputs.KeyboardInput;
import main.java.fr.ul.acl.Mazing.TheMazingGame.inputs.MouseInput;
import main.java.fr.ul.acl.Mazing.TheMazingGame.objects.Fireball;
import main.java.fr.ul.acl.Mazing.TheMazingGame.objects.ObjectManager;
import main.java.fr.ul.acl.Mazing.TheMazingGame.start.Game;
import static main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction.Constants.GamePannelC.*;

public  class GamePannel extends JPanel{

	private TileManager tileM;
	private Maze maze;
	private MouseInput mouseInput;
	private KeyboardInput keyboardInput;
	private Hero hero;
	private ObjectManager objectManager;
	private EnvironmentManager eManager;
	private int difficulty;
	

	private Game game;
	private GameOverOverlay gameOverOverlay;
	private MonsterManager monsterManager;

	

	public GamePannel(Game game) {
		this.game = game;
		mouseInput = new MouseInput(game);
		keyboardInput = new KeyboardInput(game);
		this.setPanelSize();
		tileM = game.getTileM();
		objectManager =game.getObjectManager();
		hero = game.getHero();
		maze = game.getMaze();
		monsterManager = game.getMonsterManager();
		gameOverOverlay = game.getGameOverOverlay();
		addKeyListener(keyboardInput);
		addMouseListener(mouseInput);
		addMouseMotionListener(mouseInput);
		//eManager =  game.geteManager();
		//eManager.setup(hero);
		difficulty = Game.getDificulty();
		
		

		
	}
			

	private void setPanelSize() {
		Dimension size = new Dimension(WINDOW_HEIGHT,WINDOW_WIDTH);
		setMinimumSize(size);
		setPreferredSize(size);
		setMaximumSize(size);	
	}

	//public abstract boolean gameStarted();
	//public abstract boolean gameOver();

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		tileM.drawBackground(g2);
		maze.drawMaze(g2);
		//tileM.drawFrame(g2);
		objectManager.draw(g);
		hero.drawHero(g);
		monsterManager.draw(g);
		//if (difficulty ==3 && !game.getGameOver())  {
		if (difficulty ==3 && !game.getGameOver() ) {
			//eManager.draw(g2);
		}
		if (game.getGameOver()) {
			gameOverOverlay.draw(g);
		}
		g2.dispose();

	}
	
}
