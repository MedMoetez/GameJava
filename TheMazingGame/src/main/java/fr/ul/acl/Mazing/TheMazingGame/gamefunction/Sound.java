package main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

import main.java.fr.ul.acl.Mazing.TheMazingGame.start.Game;

public class Sound extends Monster{
public Sound(Game game, float x, float y, int entity_width, int entity_height) {
		super(game, x, y, entity_width, entity_height);
		// TODO Auto-generated constructor stub
	}

public static void playSound(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException{
		
		
		File file = new File("Knife Stab Sound Effect.wav");
		AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
		Clip clip = AudioSystem.getClip();
		clip.open(audioStream);
		
}
}