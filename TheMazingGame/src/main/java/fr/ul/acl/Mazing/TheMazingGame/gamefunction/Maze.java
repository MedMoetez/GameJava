package main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction;

import static main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction.Constants.TileC.mazeBlockSize;

import main.java.fr.ul.acl.Mazing.TheMazingGame.graphics.GamePannel;
import main.java.fr.ul.acl.Mazing.TheMazingGame.graphics.GameWindow;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.annotation.processing.SupportedSourceVersion;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import main.java.fr.ul.acl.Mazing.TheMazingGame.start.Game;


public class Maze {
	// creation d'un ArrayList qui contient les indexes
	//public ArrayList<Integer> difficulte = new ArrayList<Integer>(Arrays.asList(0,1,2));
		public  int mazeNumero;
		
		// creation d'un ArrayList which contains the paths of the different mazes
		public ArrayList<String> adressesMazes = new ArrayList<String>(Arrays.asList("maze1","maze2","maze3","maze4"));
		public String adresseChoisie = new String();
		private int difficulty = 0; 


	private int [][] mazeMatrix;
	private Game game;
		



	public Maze(Game game) {
		this.game = game;
		//niveau_dif();
		adresseChoisie = adressesMazes.get(mazeNumero);
		this.mazeMatrix=LoadMaze("resources/Mazes/" + adresseChoisie + ".txt");
	}
		/*mazeNumero = getRandomMaze();
		mazeNumero = getRandomMaze();
		adresseChoisie = adressesMazes.get(mazeNumero);
		this.mazeMatrix=LoadMaze("resources/Mazes/" + adresseChoisie + ".txt");

}
	/* Methode qui choisis un random maze  */
	/*public int getRandomMaze() {
		
		// Collections.shuffle melange les elements de adressesMazes
		Collections.shuffle(mazeIndex);
		Collections.shuffle(mazeIndex);
		
		
		return mazeIndex.get(0);
	}*/
	// importation du labyrinthe avec un fichier txt String mapPath: 
	public int[][] LoadMaze(String mapPath) { //  pour appeler la fonction : Maze.LoadMaze("resources\\Mazes\\maze1.txt")
		        File file = new File(mapPath);
		        BufferedReader bufferedReader = null;
		        String line;
		        String mapCase[];
		        int col = 0;
		        int row = 0;

		        try {
		            FileReader fileReader = new FileReader(file);
		            bufferedReader = new BufferedReader(fileReader);
		            mazeMatrix = new int[20][32]; // 

		            while ((line = bufferedReader.readLine()) != null) {
		                for (col = 0; col < 32; col++) {
		                    mapCase = line.split(" ");
		                    mazeMatrix[row][col] = Integer.parseInt(mapCase[col]);
		                }
		                row++;
		            }
		        } catch (IOException e) {
		            e.printStackTrace();
		        }

		        try {
		            bufferedReader.close();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		       return mazeMatrix;
	}
	
	public void drawMaze(Graphics2D g2) {
		int tile_indice= 0;
		switch (game.getDificulty()){
		case 0:
			tile_indice = 9;
			break;
		case 1:
			tile_indice = 11;
			break;
		case 2:
			tile_indice = 13;
			break;
		case 3:
			tile_indice = 4;
			break;
		
		}
		Tile mazeTile = game.getTileM().getTile()[tile_indice];
		BufferedImage block = mazeTile.image;
		for (int i=0; i<mazeMatrix.length;i++) {
			for (int j=0; j<mazeMatrix[0].length;j++) {
				if (mazeMatrix[i][j] == 1) //line
					g2.drawImage(block,j* mazeBlockSize, i * mazeBlockSize ,mazeBlockSize , mazeBlockSize, null);	
				
			}	
		}
	}
	public void niveau_dif() {
		 String[] options = {"Facile", "Amateur", "Moyen","Difficile"};
	        int response = JOptionPane.showOptionDialog(null, "Choisissez le niveau de difficulté :", "Choix du Niveau",
	                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
	        switch (response) {
	            case 0:
	               this.mazeNumero=0;
	                break;
	            case 1:
	            	this.mazeNumero=1;
	                break;
	            case 2:
	            	 this.mazeNumero=2;
	             	break;
	            case 3:
	            	 this.mazeNumero=3;
	            	 break;
	            default:
	            	response = 0;
	            	this.mazeNumero=0;
	        }
	        game.setDificulty(response);
	    }
	
		
	public static boolean obstacle() {
		System.out.println("code a fournir");
		return true;
	}
	public boolean positionAdmissible() {
		System.out.println("");
		return true;
	}
	
	public int[][] getMazeMatrix() {
		return mazeMatrix;
	}
	public void setMazeMatrix(int[][] mazeMatrix) {
	    this.mazeMatrix = mazeMatrix;
	}
	public void reset() {
		adresseChoisie = adressesMazes.get(game.getDificulty());
		this.mazeMatrix=LoadMaze("resources/Mazes/" + adresseChoisie + ".txt");
	}
}


  
