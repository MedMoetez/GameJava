package main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction;

import static main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction.Constants.GamePannelC.*;
import static main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction.Constants.TileC.*;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


import javax.imageio.ImageIO;


import main.java.fr.ul.acl.Mazing.TheMazingGame.start.Game;


public class TileManager {
	

	private Tile[] tile;
	private int [][] MazeFrameMatrix;
	private Game game;
	private int[][] background;
	
	public TileManager(Game game) {
		this.game = game;
		MazeFrameMatrix = new int[(int)(WINDOW_HEIGHT/blockSize)][(int)(WINDOW_WIDTH/blockSize)];
		getTileImage();
		
		createMazeFrameMatrix();
		background = loadBackground();
	}
	//importation des blocks de dessin : 
	public void getTileImage() {
		try {
			tile = new Tile[14];
			
			//Arriere plan 1
			tile[0] = new Tile();
			BufferedImage background = ImageIO.read(new File("resources/tiles_images/bricks01.png"));
			tile[0].image = background.getSubimage(0*64, 1*64, 64, 64);
			
			//block du labyrinthe 1:
			tile[1] = new Tile();
			BufferedImage mazeBlock1 = ImageIO.read(new File("resources/tiles_images/bricks01.png"));
			tile[1].image = mazeBlock1.getSubimage(2*64, 1*64, 64, 64);
			
			// Arriere plan 2 
			tile[2] = new Tile();
			tile[2].image = ImageIO.read(new File("resources/tiles_images/scary_background_400x167.jpg"));
			
			// Cadre du jeu 3 : 
			tile[3] = new Tile();
			tile[3].image = ImageIO.read(new File("resources/tiles_images/goldFrame.jpg"));
			
			//block du labyrinthe 2 : 
			tile[4] = new Tile();
			BufferedImage mazeBlock2 = ImageIO.read(new File("resources/tiles_images/bricksFrame.jpg"));
			tile[4].image = mazeBlock2.getSubimage(0, 0,750 , 750);
			
			tile[5] = new Tile();
			tile[5].image = ImageIO.read(new File("resources/tiles_images/tree.png"));
			
			// Cadre du jeu 2 : 
			tile[6] = new Tile();
			BufferedImage frameBlock2 = ImageIO.read(new File("resources/tiles_images/bricksFrame.jpg"));
			//tile[6].image = frameBlock2.getSubimage(0 * blockSize, 1 * blockSize, blockSize, blockSize);
			tile[6].image = frameBlock2;
			
			// Cadre du jeu 1 :
			tile[7] = new Tile();
			BufferedImage frameBlock1 = ImageIO.read(new File("resources/tiles_images/cadreMaze.jpeg"));
			tile[7].image = frameBlock1.getSubimage(0 * blockSize, 1 * blockSize, blockSize, blockSize);
			
			tile[8] = new Tile();
			BufferedImage background2 = ImageIO.read(new File("resources/tiles_images/back_ground_forest.jpg"));
			tile[8].image = background2.getSubimage(0,0, background_width_blockSize, background_height_blockSize);
			
			tile[9] = new Tile();
			BufferedImage wall2 = ImageIO.read(new File("resources/tiles_images/violet_wall_2.jpg"));
			tile[9].image = wall2.getSubimage(0,0, wall_width_blockSize_violet, wall_height_blockSize_violet);
			
			tile[10] = new Tile();
			BufferedImage background3 = ImageIO.read(new File("resources/tiles_images/maze2_background.jpg"));
			tile[10].image = background3.getSubimage(0,0, background_width_blockSize2, background_height_blockSize2);
			
			tile[11] = new Tile();
			BufferedImage wall3 = ImageIO.read(new File("resources/tiles_images/red_wall_2.jpg"));
			tile[11].image = wall3.getSubimage(0,0, wall_width_blockSize_red, wall_height_blockSize_red);
			
			tile[12] = new Tile();
			BufferedImage background4 = ImageIO.read(new File("resources/tiles_images/lava_background.jpg"));
			tile[12].image = background4.getSubimage(0,0, background_width_blockSize3, background_height_blockSize3);
			
			tile[13] = new Tile();
			BufferedImage wall4 = ImageIO.read(new File("resources/tiles_images/lava_wall.jpg"));
			tile[13].image = wall4.getSubimage(0,0, lava_wall_width_blockSize, lava_wall_height_blockSize);
					
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	// création + dessin arrière plan : 
	public int[][] loadBackground() {
		int backgroundsize_width=0 ,backgroundsize_height=0;
		switch (game.getDificulty()) {
        case 0:
       	 backgroundsize_width = backgroundsize_width_blockSize;
       	 backgroundsize_height = backgroundsize_height_blockSize;
            break;
        case 1:
          	 backgroundsize_width = backgroundsize_width_blockSize2 ;
           	 backgroundsize_height = backgroundsize_height_blockSize2;
            break;
        case 2:
          	 backgroundsize_width = backgroundsize_width_blockSize3 ;
           	 backgroundsize_height = backgroundsize_height_blockSize3;
         	 break;
        case 3:
          	 backgroundsize_width = backgroundsize_width_blockSize4 ;
           	 backgroundsize_height = backgroundsize_height_blockSize4;
        	 break;
		}
		int [][] maptilename = new int[(int)(WINDOW_WIDTH/backgroundsize_width)+1][(int)(WINDOW_HEIGHT/backgroundsize_height)+1];
		for (int i=0;i<maptilename.length;i++) {
			for (int j=0;j<maptilename[0].length;j++) {
				maptilename[i][j] = 0;
			}
		}
		return maptilename;
		
	}
	
	public void drawBackground(Graphics2D g2 ){
		int col = 0 ;
		int row = 0 ;
		int x = 0 ;
		int y = 0 ;
		int backgroundsize_width=0 ,backgroundsize_height=0;
		int tile_indice = 2;
		switch (game.getDificulty()) {
        case 0:
       	 backgroundsize_width = backgroundsize_width_blockSize ;
       	 backgroundsize_height = backgroundsize_height_blockSize ;
       	 tile_indice = 8 ;
            break;
        case 1:
          	 backgroundsize_width = backgroundsize_width_blockSize2 ;
           	 backgroundsize_height = backgroundsize_height_blockSize2;
           	 tile_indice =10 ;
            break;
        case 2:
          	 backgroundsize_width = backgroundsize_width_blockSize3 ;
           	 backgroundsize_height = backgroundsize_height_blockSize3;
           	 tile_indice =12 ;
         	 break;
        case 3:
          	 backgroundsize_width = backgroundsize_width_blockSize4 ;
           	 backgroundsize_height = backgroundsize_height_blockSize4;
           	 tile_indice = 2 ;
        	 break;
		}
		
		while (col < background[0].length && row<background.length ) {
			g2.drawImage(tile[tile_indice].image,x,y,backgroundsize_height,backgroundsize_width, null);//2,8,10,12
			col++;
			x += backgroundsize_height;
			if (col >= background[0].length) {
				col = 0;
				x=0;
				row++;
				y+= backgroundsize_width;
			}
		}
	}
	
	// création + dessin du labyrinthe : 
	

	// création + dessin du cadre du jeu : 
	public void createMazeFrameMatrix() {
		for (int i = 0; i< MazeFrameMatrix.length;i++) {
			for (int j = 0; j < MazeFrameMatrix[0].length;j++) {
				if (i ==0 || i == MazeFrameMatrix.length -1 || j == 0 || j == MazeFrameMatrix[0].length - 1 ) 
					MazeFrameMatrix[i][j] = 1;
				else
					MazeFrameMatrix[i][j] = 0;
			}
		}	
	}
	
	public void drawFrame(Graphics2D g2) {

		for (int i=0; i<MazeFrameMatrix.length;i++) {
			for (int j=0; j<MazeFrameMatrix[0].length;j++) {
				if (MazeFrameMatrix[i][j] == 1)
					g2.drawImage(tile[3].image,i * blockSize, j * blockSize ,blockSize , blockSize, null);	
			}	
		}
	}
	public Tile[] getTile() {
		return tile;
	}
	
	public void resetTileManager() {
		background = loadBackground();
	}
		
}
	