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
    protected Direction facing = Direction.RIGHT;
    /** Is the entity moving */
    protected boolean isMoving = false;

    public Pacman(Game game)
    {
        super(game);
    }

    @Override
    public void tick()
    {
        super.tick();
        if(isMoving)
        {

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
