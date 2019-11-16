package test.pacman.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pacman.Game;
import pacman.entities.Dot;
import pacman.entities.Entity;

/**
 * Test {@link pacman.entities.Entity}
 *
 * @author Robert Seifert
 * @version 12-10.2015
 */
public class TestEntity
{
    @Test
    public void testPos()
    {
        Game game = new Game("testMap1");
        Entity entity = new Dot(game);
        entity.setPos(3, 1);
        Assertions.assertEquals(entity.x(), 3);
        Assertions.assertEquals(entity.y(), 1);
    }

    @Test
    public void testCanMove()
    {
        Game game = new Game("testMap1");
        game.load();

        //Test true
        game.player.setPos(3, 1);
        Assertions.assertTrue(game.player.canMoveTo(4, 1));

        //Tests false
        game.player.setPos(1, 1);
        Assertions.assertFalse(game.player.canMoveTo(0, 1));
    }
}
