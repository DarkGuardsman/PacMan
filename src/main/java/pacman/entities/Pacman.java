package pacman.entities;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 12/10/2015.
 */
public class Pacman extends Entity
{
    /** Number of dots eaten */
    protected int dotsEaten = 0;

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