package test.pacman.entities;

import junit.framework.TestCase;
import org.junit.Test;
import pacman.Game;
import pacman.entities.Dot;
import pacman.entities.Entity;

/**
 * Test {@link pacman.entities.Entity}
 *
 * @author Robert Seifert
 * @version 12-10.2015
 */
public class TestEntity extends TestCase
{
    @Test
    public void testPos()
    {
        Game game = new Game("testMap1");
        Entity entity = new Dot(game);
        entity.setPos(3, 1);
        assertEquals(entity.x(), 3);
        assertEquals(entity.y(), 1);
    }

    @Test
    public void testCanMove()
    {
        Game game = new Game("testMap1");
        game.load();

        //Test true
        game.player.setPos(3, 1);
        assertTrue(game.player.canMoveTo(4, 1));

        //Tests false
        game.player.setPos(1, 1);
        assertFalse(game.player.canMoveTo(0, 1));
    }
}
