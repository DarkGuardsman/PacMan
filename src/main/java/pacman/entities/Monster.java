package pacman.entities;

/**
 * @author Robert Seifert
 * @version 12-10.2015
 */
public class Monster extends Entity
{
    @Override
    public void onCollide(Entity entity)
    {
        if (entity instanceof Pacman)
        {
            entity.setDead();
        }
    }
}
