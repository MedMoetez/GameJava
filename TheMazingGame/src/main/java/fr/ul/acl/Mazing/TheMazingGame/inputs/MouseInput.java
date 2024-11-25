package main.java.fr.ul.acl.Mazing.TheMazingGame.inputs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction.Constants.HeroC;
import main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction.Hero;
import main.java.fr.ul.acl.Mazing.TheMazingGame.graphics.GamePannel;
import main.java.fr.ul.acl.Mazing.TheMazingGame.start.Game;

public class MouseInput implements MouseListener , MouseMotionListener{ 
	
	private Hero hero;
	private Game game;
	
	public MouseInput(Game game) {
		
		this.game = game;
		this.hero = game.getHero();
		
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if (!game.getGameOver()) {
			switch (e.getButton()) {
			case MouseEvent.BUTTON1:
				hero.setAttack(true);
				hero.chooseAttack(HeroC.ATTACK);
				break;
			case MouseEvent.BUTTON3:
				hero.setAttack(true);
				hero.chooseAttack(HeroC.DOUBLE_ATTACK);
				break;
			case MouseEvent.BUTTON2:
				hero.setAttack(true);
				hero.chooseAttack(HeroC.PROJECTILE);
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		if (!game.getGameOver()) {
			switch (e.getButton()) {
			case MouseEvent.BUTTON1:
			case MouseEvent.BUTTON3:
			case MouseEvent.BUTTON2:
				hero.setAttack(false);
				break;
			}
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	

}
