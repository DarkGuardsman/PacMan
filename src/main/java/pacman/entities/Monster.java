package pacman.entities;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 12/10/2015.
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
