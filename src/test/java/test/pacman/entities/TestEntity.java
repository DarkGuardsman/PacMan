package test.pacman.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pacman.Game;
import pacman.board.GameBoard;
import pacman.entities.Entity;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test {@link pacman.entities.Entity}
 *
 * @author Robert Seifert
 * @version 12-10.2015
 */
public class TestEntity
{
    public static String MAP_NAME = "testMap1";

    @Test
    public void testSetGetPos()
    {
        //Test data
        final int x = 3;
        final int y = 1;

        //Create entity and set pos
        final Entity entity = new Entity(null).setPos(x, y);

        //Expect x & y to match
        Assertions.assertEquals(entity.x(), x);
        Assertions.assertEquals(entity.y(), y);
    }

    @Test
    public void testCanMoveTo_false()
    {
        //Mock game
        final Game game = mock(Game.class, RETURNS_DEEP_STUBS);
        when(game.getBoard().containsWall(0, 1)).thenReturn(true);

        //Create entity and set pos
        final Entity entity = new Entity(game).setPos(0, 0);

        //Tests false
        final boolean result = entity.canMoveTo(0, 1);
        Assertions.assertFalse(result);
    }

    @Test
    public void testCanMoveTo_true()
    {
        //Mock game
        final Game game = mock(Game.class, RETURNS_DEEP_STUBS);
        when(game.getBoard().containsWall(0, 1)).thenReturn(false);

        //Create entity and set pos
        final Entity entity = new Entity(game).setPos(0, 0);

        //Tests false
        final boolean result = entity.canMoveTo(0, 1);
        Assertions.assertTrue(result);
    }

    @Test
    public void testMoveTo_false()
    {
        //Mock game
        final Game game = mock(Game.class, RETURNS_DEEP_STUBS);
        when(game.getBoard().containsWall(0, 1)).thenReturn(true);

        //Create entity and set pos
        final Entity entity = new Entity(game).setPos(0, 0);

        //Tests false
        final boolean result = entity.moveTo(0, 1);
        Assertions.assertFalse(result);
    }

    @Test
    public void testMoveTo_true()
    {
        //Mock game
        final Game game = mock(Game.class, RETURNS_DEEP_STUBS);
        when(game.getBoard().containsWall(0, 1)).thenReturn(false);

        //Create entity and set pos
        final Entity entity = new Entity(game).setPos(0, 0);

        //Tests false
        final boolean result = entity.moveTo(0, 1);
        Assertions.assertTrue(result);
    }
}
