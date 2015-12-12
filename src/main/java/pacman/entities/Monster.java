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
    public void draw(Graphics g, int startCornerX, int startCornerY, float scale)
    {
        g.setColor(Color.blue);
        g.fillRect(startCornerX + x + (int) (15 * scale), startCornerY + y + (int) (15 * scale), (int) (15 * scale), (int) (15 * scale));
    }
}
