package main.java.fr.ul.acl.Mazing.TheMazingGame.environment;

import java.awt.Graphics;
import java.awt.Graphics2D;

import main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction.Hero;
import main.java.fr.ul.acl.Mazing.TheMazingGame.graphics.GamePannel;
import main.java.fr.ul.acl.Mazing.TheMazingGame.start.Game;


public class EnvironmentManager {
	private Game game;
	private Lighting lighting;
	private Hero hero;

	public EnvironmentManager(Game game){
		this.game=game;
		hero = game.getHero();
		setup(hero);
	}
	public void setup(Hero hero) {

		lighting = new Lighting(hero);
	}
	public void draw(Graphics2D g2) {
		lighting.draw(g2);
		
	}

}
