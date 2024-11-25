package main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction;
import main.java.fr.ul.acl.Mazing.TheMazingGame.graphics.GamePannel;
import main.java.fr.ul.acl.Mazing.TheMazingGame.start.Game;
import main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction.Monster;

import static main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction.Constants.MonsterC.monsterX0;
import static main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction.Constants.MonsterC.monsterY0;

import java.awt.Graphics;
import java.util.ArrayList;

import main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction.Constants;


public class MonsterManager{
	public  ArrayList <Monster> Monsters;

	private int difficulty;
	private int nombreMonster;
	private ArrayList<Integer> monsterX;
	private ArrayList<Integer> monsterY;
	private Game game;

	public  MonsterManager(Game game){
		this.game = game;
		Monsters = new ArrayList <Monster>();
		difficulty = game.getDificulty();
		generateMonsters();

	}
	
	public void generateMonsters() {
		switch(difficulty) {
		  case 0:
			  nombreMonster = 2;
			  monsterX= new ArrayList<Integer>();
			  monsterY= new ArrayList<Integer>();
			  monsterX.add(1040);
			  monsterX.add(250);
			  
			  monsterY.add(300);
			  monsterY.add(150);

			  for(int i=0;i<nombreMonster;i++) {
					Monsters.add(new Monster(game,monsterX.get(i),monsterY.get(i),30,40));
					
			  }
			  break;
			  

		  case 1:
			  nombreMonster = 4;
			  monsterX= new ArrayList<Integer>();
			  monsterY= new ArrayList<Integer>();
			  monsterX.add(900);
			  monsterX.add(520);
			  monsterX.add(270);
			  monsterX.add(300);

			  monsterY.add(415);
			  monsterY.add(100);
			  monsterY.add(150);
			  monsterY.add(395);
			  for(int i=0;i<nombreMonster;i++) {
					Monsters.add(new Monster(game,monsterX.get(i),monsterY.get(i),30,40));
					
			  }
			  break;


			  
			  
		  case 2:
			  nombreMonster = 4;
			  monsterX= new ArrayList<Integer>();
			  monsterY= new ArrayList<Integer>();
			  monsterX.add(100);
			  monsterX.add(160);
			  monsterX.add(300);
			  monsterX.add(800);

			  monsterY.add(150);
			  monsterY.add(470);
			  monsterY.add(310);
			  monsterY.add(630);
			  for(int i=0;i<nombreMonster;i++) {
					Monsters.add(new Monster(game,monsterX.get(i),monsterY.get(i),30,40));
					
			  }
			  break;


			    
			 case 3: //niveau diff
				  nombreMonster = 6;
				  monsterX= new ArrayList<Integer>();
				  monsterY= new ArrayList<Integer>();
				  monsterX.add(600);
				  monsterX.add(600);
				  monsterX.add(550);
				  monsterX.add(800);
				  monsterX.add(600);
				  monsterX.add(600);
				
				  
				  monsterY.add(470);
				  monsterY.add(560);
				  monsterY.add(715);
				  monsterY.add(640);
				  monsterY.add(35);
				  monsterY.add(150);


				  for(int i=0;i<nombreMonster;i++) {
						Monsters.add(new Monster(game,monsterX.get(i),monsterY.get(i),30,40));
						
				  }
				  break;
	}
		
	}
	public void draw(Graphics g) {
		for (Monster monster : Monsters) {
			monster.drawMonster(g);
		}
	}
	
	public void resetMonsters() {
		difficulty = game.getDificulty();
		Monsters.clear();
		generateMonsters();
		
		
	}
	public ArrayList<Monster> getMonsters() {
		return Monsters;
	}
}

	
			
			
		
		
	
			
			
		

	


