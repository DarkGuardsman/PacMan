package pacman.entities;

import pacman.Game;
import pacman.util.Direction;

import java.awt.*;

/**
 * @author Robert Seifert
 * @version 12-10.2015
 */
public class Pacman extends Entity
{
    /** Number of dots eaten */
    protected int dotsEaten = 0;
    /** Direction pacman is facing */
    public Direction facing = Direction.RIGHT;
    /** Is the entity moving */
    public boolean isMoving = false;
    /** Number of lives */
    public int lives = 1;

    public Pacman(Game game)
    {
        super(game);
    }

    @Override
    public void tick()
    {
        super.tick();
        if (isMoving)
        {
            isMoving = false;
            System.out.println("moving");
            if (!moveTo(x + facing.x, y + facing.y))
            {
                System.out.println("wall");
            }
        }
    }

    @Override
    public void onCollide(Entity entity)
    {
        if (entity instanceof Dot)
        {
            entity.setDead();
            this.dotsEaten++;
        }
    }

    @Override
    public void setDead()
    {
        super.setDead();
        //TODO do death animation
        this.setPos(game.spawnX, game.spawnY);
        if(lives-- <= 0)
        {
            //TODO Reset map
        }
    }

    @Override
    public void draw(Graphics g, int x, int y, float scale)
    {
        g.setColor(Color.yellow);
        int size = (int) (16 * scale);
        int boxScale = (int) (scale * 20) / 2;
        g.fillRect(x + boxScale - (size / 2), y + boxScale - (size / 2), size, size);
    }
}
