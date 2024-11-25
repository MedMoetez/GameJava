package test.java.fr.ul.acl.Mazing.TheMazingGame;



import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction.Constants;
import main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction.Hero;
import main.java.fr.ul.acl.Mazing.TheMazingGame.gamefunction.Maze;
import main.java.fr.ul.acl.Mazing.TheMazingGame.start.Game;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
    	assertTrue(true);
    }
    public void testGameOver() {
    	Game game = new Game();
    	assertFalse(game.getGameOver());
    }
    public void testInitClasses() {
        // Créez une instance de Game
        Game game = new Game();
        
        // Appelez la fonction initClasses
        game.initClasses();
        
        // Ajoutez des assertions pour vérifier si les objets ont été correctement initialisés
        assertNotNull(game.getTileM());
        assertNotNull(game.getHero());
        assertNotNull(game.getMonsterManager());
        assertNotNull(game.getObjectManager());
        assertNotNull(game.getGameOverOverlay());
        assertNotNull(game.getAttack());
        assertNotNull(game.getMaze());
    }

    public void testSpawn() {
        // Créez une instance de Game
        Game game = new Game();

        // Créez une instance de Hero
        Hero hero = new Hero(game, 0, 0, 20, 32);

        // Testez la fonction spawn avec différentes valeurs de difficulté
        hero.spawn(0);
        assertEquals(Constants.HeroC.heroX0, (int) hero.getHitbox().x);

        hero.spawn(1);
        assertEquals(Constants.HeroC.heroX1, (int) hero.getHitbox().x);

        hero.spawn(2);
        assertEquals(Constants.HeroC.heroX2, (int) hero.getHitbox().x);

        hero.spawn(3);
        assertEquals(Constants.HeroC.heroX3, (int) hero.getHitbox().x);
        
     // Testez la fonction spawn avec une valeur de difficulté inconnue
        hero.spawn(100);
        assertEquals(Constants.HeroC.heroX0, (int) hero.getHitbox().x);

    }
}
