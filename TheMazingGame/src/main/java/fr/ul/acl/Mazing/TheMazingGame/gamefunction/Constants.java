package main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction;

import static main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction.Constants.GamePannelC.WINDOW_WIDTH;

import main.java.fr.ul.acl.Mazing.TheMazingGame.start.Game;

public class Constants {
	public static class GamePannelC{
		public static final int WINDOW_HEIGHT = 1280;
		public static final int WINDOW_WIDTH = 800 ;
		public static final int ZOOM = 2;
	}
	public static class Directions{
		public static final int LEFT = 0;
		public static final int RIGHT = 2;
		public static final int UP = 1;
		public static final int DOWN = 3;
		public static final int JUMP = 4;
	}
	
	
	
	public static class HeroC{
		public static final int heroX0 = 80;
		public static final int heroY0 = 400;
		public static final int heroX1 = 1000;
		public static final int heroY1 = 600;
		public static final int heroX2 = 150;
		public static final int heroY2 = 600;
		public static final int heroX3 = 750;
		public static final int heroY3 = 300;
		public static final int STANDING = 0;
		public static final int RUNNING = 1;
		public static final int JUMPING_HIGH = 2;
		public static final int SLIDING = 3;
		public static final int ZIPLINE = 4;
		public static final int JUMPING_LOW = 5;
		public static final int ATTACK = 6;
		public static final int DOUBLE_ATTACK = 7;
		public static final int PROJECTILE = 8;
		public static final int DIE = 9;
	}
	public static class MonsterC{
		public static final int monsterX0 = 400;
		public static final int monsterY0 = 600;
		public static final int STANDING = 0;
		public static final int RUNNING = 1;
		public static final int ATTACK = 2;
		public static final int HIT = 3;
		public static final int DIE = 4;
		public static Monster monster;
		
	}
	
	
	public static int getHeroAction(int heroAction) {
			switch(heroAction) {
			case HeroC.STANDING:
				return 5;
			case HeroC.RUNNING:
				return 6;
			case HeroC.DIE:
				return 4;
			case HeroC.JUMPING_HIGH:
			case HeroC.JUMPING_LOW:
			case HeroC.ATTACK:
			case HeroC.DOUBLE_ATTACK:
				return 3;
			case HeroC.SLIDING:
				return 2;
			default:
				return 1;
			}
		}
	
	public static class TileC{
		public static final int blockSize = 20;
		public static final int frame_blockSize = 20;
		public static final int mazeBlockSize = 40;
		
		public static final int background_height_blockSize = 1080;
		public static final int background_width_blockSize = 1920;
		public static final int background_height_blockSize2 = 400;
		public static final int background_width_blockSize2 = 800;
		public static final int background_height_blockSize3 = 512; //lava_background
		public static final int background_width_blockSize3 = 512;
		public static final int background_height_blockSize4 = 400;
		public static final int background_width_blockSize4 = 167;
		
		public static final int backgroundsize_height_blockSize = GamePannelC.WINDOW_HEIGHT -1;
		public static final int backgroundsize_width_blockSize = GamePannelC.WINDOW_WIDTH -1;
		public static final int backgroundsize_height_blockSize2 = GamePannelC.WINDOW_HEIGHT -1;
		public static final int backgroundsize_width_blockSize2 = GamePannelC.WINDOW_WIDTH -1;
		public static final int backgroundsize_height_blockSize3 = 256; //lava_background
		public static final int backgroundsize_width_blockSize3 = 256;
		public static final int backgroundsize_height_blockSize4 = 400;
		public static final int backgroundsize_width_blockSize4 = 167;

		public static final int wall_height_blockSize_violet = 40;
		public static final int wall_width_blockSize_violet = 40;
		public static final int wall_height_blockSize_red= 212;
		public static final int wall_width_blockSize_red = 250;
		public static final int lava_wall_height_blockSize = 256;
		public static final int lava_wall_width_blockSize = 256;
		
		public static final int TILE_XMAX = 30;
		public static final int TILE_YMAX = 20;
		
	}
	public static class Collision {
		public static final int  HERO_HEIGHT = 100;
		public static final int HERO_WIDTH = 74 ;
	}
	public static class ObjectConstants {
		public static final int  RED_POTION = 0;
		public static final int  PINK_POTION = 1;
		public static final int  GREEN_POTION = 2;
		public static final int  PORTAIL = 3;
		public static final int  KEY = 4;
		
		
		public static final int  RED_POTION_VALUE = 10;
		public static final int  GREEN_POTION_VALUE = 20;
		public static final int  PINK_POTION_VALUE = 3;
		
		public static final int  POTION_WIDTH_DEFAULT = 20;
		public static final int  POTION_HEIGHT_DEFAULT = 40;
		public static final int  POTION_WIDTH = (int) (Game.SCALE * POTION_WIDTH_DEFAULT);
		public static final int  POTION_HEIGHT = (int) (Game.SCALE * POTION_HEIGHT_DEFAULT);
		
		
	}
}
