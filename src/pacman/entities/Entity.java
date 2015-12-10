package pacman.entities;

/**
 * @author Robert Seifert
 * @version 12-10.2015
 */
public class Entity
{
    protected boolean isAlive = true;
    protected int x = 0;
    protected int y = 0;

    public void tick()
    {

    }

    /**
     * Called when the entity is added to the board.
     * When added if, true, it will be added to the
     * world tick list. If false it will just be
     * added to the entity list.
     *
     * @return true to receive update ticks
     */
    public boolean needsTick()
    {
        return true;
    }

    public boolean isAlive()
    {
        return isAlive;
    }
}
