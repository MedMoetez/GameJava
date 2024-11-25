package main.java.fr.ul.acl.Mazing.TheMazingGame.objects;

import main.java.fr.ul.acl.Mazing.TheMazingGame.start.Game;

public class Potion extends GameObject{
	
	private float hoverOffset;
	private int maxHoverOffset,hoverDir = 1;
	
	public Potion(int x, int y, int objType) {
		super(x, y, objType);
		initHitbox(20,40);
		xDrawOffset = (int) (Game.SCALE * 0);
		yDrawOffset = (int) (Game.SCALE * 0); 

		maxHoverOffset = (int) (20*Game.SCALE);
	}
	public void update() {
		updateHover();
		
	}
	private void updateHover() {
		hoverOffset += (0.1f * Game.SCALE * hoverDir);
		
		if (hoverOffset >=maxHoverOffset)
			hoverDir = -1;
		else if (hoverOffset<0)
			hoverDir = 1;
		
		hitbox.y = y + hoverOffset;
	}
	
	public void resetPotion() {
		
	}
	

}
