package pacman.entities;

import pacman.Game;

/**
 * @author Robert Seifert
 * @version 12-10.2015
 */
public class Dot extends Entity
{
    public Dot(Game game)
    {
        super(game);
    }

    @Override
    public boolean needsTick()
    {
        return false;
    }
}
