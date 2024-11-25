package main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction;

import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction.Constants.HeroC;
import main.java.fr.ul.acl.Mazing.TheMazingGame.start.Game;

public class Attack {
	
	private MonsterManager monsterManager;
	private Hero hero;
	private Game game;
	private ArrayList<Monster> monsters;
	
	public Attack(Game game) {
		this.game = game;
		hero = game.getHero();
		monsterManager = game.getMonsterManager();
		monsters = monsterManager.getMonsters();
	}
	
	public void checkMonstersHit(Rectangle2D.Float heroAttackBox)   {
		//System.out.println("Hit");
		for (Monster monster : monsters) {

		checkMonsterHit(heroAttackBox, monster);
		}
	}
	public void checkMonsterHit(Rectangle2D.Float heroAttackBox, Monster monster)  {
		if (monster.isActive()) {
			if (heroAttackBox.intersects(monster.getHitbox())) {
				//System.out.println("BOOM");
				//System.out.println(currentHp);
				if (hero.getHeroAction() == HeroC.ATTACK) 
					monster.hurt(hero.getHeroDmg());
				else if (hero.getHeroAction() == HeroC.DOUBLE_ATTACK)
					monster.hurt(hero.getHeroDmg() * (150/100)); //150% de dégats
				else if (hero.getFireball().isActive()) {
					monster.hurt(hero.getDmgBeforeFireball() * (200/100));//200% de dégats
					
					hero.getFireball().setActive(false);
					//hero.setAttack(false);
				}
			}

		}
	}
	protected void checkHeroHit(Hero hero, Monster monster) {
		if(monster.getAttackBox().intersects(hero.hitbox))
			hero.changeHP(- (monster.getMonsterDmg()));
		monster.setAttackChecked(true);
		
		
	}
	
	

}
