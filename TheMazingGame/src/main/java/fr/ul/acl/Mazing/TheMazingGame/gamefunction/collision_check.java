package main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction;

import static main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction.Constants.GamePannelC.*;

import java.awt.geom.Rectangle2D;

import main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction.Constants.TileC;

public class collision_check {
	public static boolean CanMoveHere(float x,float y , float width ,float height,int[][] maze) {
		if (!IsSolid(x, y, maze))
			if (!IsSolid(x + width, y + height, maze))
				if (!IsSolid(x + width, y, maze))
					if (!IsSolid(x, y + height, maze))
						return true;
		return false;
}
	public static boolean IsSolid (float x ,float y ,int[][] maze) {

		if ( x < 0|| x >= WINDOW_HEIGHT ) 
			return true;

		if (y <  0|| y >= WINDOW_WIDTH)
			return true ;
		
		float xIndex = x / TileC.mazeBlockSize ; 
		float yIndex = y / TileC.mazeBlockSize ;
	
		int value = maze[(int)yIndex][(int)xIndex];
		if ( value == 1 )
			return true;
		return false;
	}

	public static boolean IsTileSolid(int xTile, int yTile, int[][] lvlData) {
		if ((-1 < xTile )&& (xTile<lvlData[0].length ) && (-1 < yTile ) && (yTile< lvlData.length )){
			int value = lvlData[yTile][xTile];


			if ( value == 1)
				return true;
			}
		return false;

	}
	public static boolean IsFloor(Rectangle2D.Float hitbox, float xSpeed, int[][] lvlData) {
		return IsSolid(hitbox.x + xSpeed, hitbox.y + hitbox.height + 1, lvlData);
	}
	public static boolean IsAllTilesWalkable(int xStart, int xEnd, int y, int[][] lvlData) {
		for (int i = 0; i < xEnd - xStart; i++) {

			if (IsTileSolid(xStart + i, y, lvlData) )
				return true;
			if (!IsTileSolid(xStart + i, y + 1, lvlData))
				return true;
		}

		return false;
	}

	
}
	
