package main.java.fr.ul.acl.Mazing.TheMazingGame.graphics;

import javax.swing.JFrame;

public class GameWindow  {
	private JFrame jframe;
	
	public GameWindow(GamePannel gamePannel) {
		jframe = new JFrame();
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.add(gamePannel);
		//jframe.setLocationRelativeTo(null);
		jframe.setResizable(false); // the window is not resizable	
		jframe.pack();
		
		
		jframe.setVisible(true);
	}

}
