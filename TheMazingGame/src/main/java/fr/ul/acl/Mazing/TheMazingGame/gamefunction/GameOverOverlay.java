package main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction;


import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import main.java.fr.ul.acl.Mazing.TheMazingGame.start.Game;
import static main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction.Constants.GamePannelC.*;

public class GameOverOverlay {
	
	private Game game;
	
	public GameOverOverlay(Game game) {
		this.game = game;
	}
	
	public void draw(Graphics g) {
		//g.setColor(new Color(0,0,0,200));
		//g.fillRect(0, 0, WINDOW_HEIGHT, WINDOW_WIDTH);
		//g.setColor(Color.white);
		//g.drawString("GAME OVER", WINDOW_HEIGHT/2 -50, WINDOW_WIDTH/2 );
		//g.drawString("Press Esc to restart", WINDOW_HEIGHT/2 -65 , WINDOW_WIDTH/2 + 50);
		g.setColor(new Color(0,0,0,200));
		g.fillRect(0, 0, WINDOW_HEIGHT, WINDOW_WIDTH);
		String text;
		if (game.isGameWin())
			text="VICTORY";
		else
			text="GAME OVER";
		g.setFont(g.getFont().deriveFont(Font.BOLD, 110f));
		g.setColor(Color.white);
		drawCenteredString(text,WINDOW_HEIGHT,300,g);
		g.setFont(g.getFont().deriveFont(Font.BOLD, 30f));
		drawCenteredString("Press the button Esc to restart",WINDOW_HEIGHT,800,g);

		
	}
	  public void drawCenteredString(String s, int w, int h, Graphics g) {
		    FontMetrics fm = g.getFontMetrics();
		    int x = (w - fm.stringWidth(s)) / 2;
		    int y = (fm.getAscent() + (h - (fm.getAscent() + fm.getDescent())) / 2);
		    g.drawString(s, x, y);
		  }
	
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			//game.setDificulty(0);
			game.resetAll();
		}
		
	}
	
	
}
