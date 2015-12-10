package pacman.entities;

import pacman.Game;
import pacman.util.Direction;

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
            if (!moveTo(x + facing.x, y + facing.y))
            {
                //TODO do wall hit animation
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
    }
}
