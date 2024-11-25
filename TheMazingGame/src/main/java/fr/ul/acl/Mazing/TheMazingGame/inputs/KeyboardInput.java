package main.java.fr.ul.acl.Mazing.TheMazingGame.inputs;
import main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction.GameOverOverlay;
import main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction.Hero;
import main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction.Constants.HeroC;
import main.java.fr.ul.acl.Mazing.TheMazingGame.graphics.GamePannel;
import main.java.fr.ul.acl.Mazing.TheMazingGame.objects.Portail;
import main.java.fr.ul.acl.Mazing.TheMazingGame.start.Game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import static main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction.Constants.Directions.*;

public class KeyboardInput implements KeyListener  {
	
	private Hero hero;
	private Game game;
	private GameOverOverlay gameOverOverlay;
	private Portail portail;
	public KeyboardInput(Game game) {
		this.game = game;
		this.hero = game.getHero();
		gameOverOverlay = game.getGameOverOverlay();
		portail = game.getObjectManager().getPortail();
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (game.getGameOver()) {
			gameOverOverlay.keyPressed(e);
		}
		else {
			portail.keyPressed(e);
			switch (e.getKeyCode()) {

			case KeyEvent.VK_LEFT:
				hero.setLeft(true);
				break;
			case KeyEvent.VK_DOWN:
				hero.setDown(true);
				break;
			case KeyEvent.VK_RIGHT:
				hero.setRight(true);
				break;
			case KeyEvent.VK_SPACE:
			case KeyEvent.VK_UP:
				hero.setJump(true);
				break;
			case KeyEvent.VK_R:
				hero.setAttack(true);
				hero.setFireballKeyPressed(true);
				hero.chooseAttack(HeroC.PROJECTILE);
				break;
			case KeyEvent.VK_Z:
				hero.setAttack(true);
				hero.chooseAttack(HeroC.ATTACK);
				break;
			case KeyEvent.VK_E:
				hero.setAttack(true);
				hero.chooseAttack(HeroC.DOUBLE_ATTACK);
				break;
				
			}
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		if (!game.getGameOver()) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				hero.setLeft(false);
				break;
			case KeyEvent.VK_DOWN:
				hero.setDown(false);
				break;
			case KeyEvent.VK_RIGHT:
				hero.setRight(false);
				break;
			case KeyEvent.VK_SPACE:
			case KeyEvent.VK_UP:
				hero.setJump(false);
				break;
			case KeyEvent.VK_Z:
			case KeyEvent.VK_E:
				hero.setAttack(false);
				break;
			case KeyEvent.VK_R:
				hero.setFireballKeyPressed(false);
				break;
			}
		}
	}

}
