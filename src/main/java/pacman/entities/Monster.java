package pacman.entities;

import pacman.Game;
import pacman.util.Direction;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Robert Seifert
 * @version 12-10.2015
 */
public class Monster extends Entity
{
    int moveDelay = 0;

    public Monster(Game game)
    {
        super(game);
    }

    @Override
    public void onCollide(Entity entity)
    {
        if (entity instanceof Pacman)
        {
            entity.setDead();
        }
    }

    @Override
    public void tick()
    {
        moveDelay++;
        if (moveDelay >= 50)
        {
            System.out.println(this + " moving...");
            moveDelay = 0;
            move();
        }
    }

    /** Called to move to next tile */
    protected void move()
    {
        Direction moveDir = getBestDirection();
        //Moves the entity
        moveTo(x + moveDir.x, y + moveDir.y);
    }

    /** Gets the cost to path to the player from the location */
    protected Direction getBestDirection()
    {
        int best = Integer.MAX_VALUE;
        List<Direction> l = Arrays.asList(Direction.VALID);
        Collections.shuffle(l);
        Direction bestDir = Direction.NONE;
        for (Direction dir : l)
        {
            if (canMoveTo(dir.x + x, dir.y + y))
            {
                System.out.println(this + " getting best path in " + dir + " direction");
                int c = path(1, x + dir.x, y + dir.y, new ArrayList());
                System.out.println(this + " Dir: " + dir + " " + c);
                if (c != -1 && c < best)
                {
                    best = c;
                    bestDir = dir;
                }
            }
        }
        //If no good direction is found move randomly
        if (bestDir == Direction.NONE)
        {
            Collections.shuffle(l);
            for (Direction dir : l)
            {
                if (canMoveTo(dir.x + x, dir.y + y))
                {
                    return dir;
                }
            }
        }
        return bestDir;
    }

    /**
     * Recuvsive pathfinder that tries to find the shortest rout to the player
     *
     * @param count       - count of tiles moved
     * @param x           - next x
     * @param y           - next y
     * @param pathedTiles - list of tiles pathed as string
     * @return count
     */
    private int path(int count, int x, int y, List<String> pathedTiles)
    {
        String indexKey = x + " " + y;
        //Stop if we find the player
        if (x == game.player.x && y == game.player.y)
        {
            return count;
        }
        else if (count < 20 && !pathedTiles.contains(indexKey) && !game.getBoard().containsWall(x, y))
        {
            pathedTiles.add(indexKey);
            System.out.println(this + " C:" + count + " I:" + indexKey);

            //Keep pathing
            int best = Integer.MAX_VALUE;
            for (Direction dir : Direction.VALID)
            {
                //Find next value in path
                int c = path(count + 1, x + dir.x, y + dir.y, pathedTiles);
                if (c != -1 && c < best)
                {
                    best = c;
                }
            }
            return best;
        }
        return -1;
    }

    @Override
    public boolean canMoveTo(int newX, int newY)
    {
        if (super.canMoveTo(newX, newY))
        {
            List<Entity> entities = game.getEntitiesAt(newX, newY);
            for (Entity entity : entities)
            {
                if (entity instanceof Monster)
                {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public void draw(Graphics g, int x, int y, float scale)
    {
        g.setColor(Color.blue);
        int size = (int) (14 * scale);
        int boxScale = (int) (scale * 20) / 2;
        g.fillRect(x + boxScale - (size / 2), y + boxScale - (size / 2), size, size);
    }

    @Override
    public String toString()
    {
        return "Mob@" + hashCode();
    }
}
