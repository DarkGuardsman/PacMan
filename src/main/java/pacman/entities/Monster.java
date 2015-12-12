package pacman.entities;

import pacman.Game;

import java.awt.*;
import java.util.List;

/**
 * @author Robert Seifert
 * @version 12-10.2015
 */
public class Monster extends Entity
{
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
}
