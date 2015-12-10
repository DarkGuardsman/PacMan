package pacman.entities;

import pacman.Game;

/**
 * Prefab class for any game board object used
 * by the game.
 *
 * @author Robert Seifert
 * @version 12-10.2015
 */
public abstract class Entity
{
    /** Is the entity alive */
    protected boolean isAlive = true;
    /** X grid coordinate */
    protected int x = 0;
    /** Y grid coordinate */
    protected int y = 0;

    /** Game the entity is part of */
    protected final Game game;

    public Entity(Game game)
    {
        this.game = game;
    }


    /**
     * Called to update the entity each tick.
     */
    public void tick()
    {

    }

    /**
     * Called when one entity enters the grid
     * of another entity.
     *
     * @param entity - entity collided with
     */
    public void onCollide(Entity entity)
    {

    }

    public boolean tryToMoveTo(int newX, int newY)
    {
        return false;
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

    /**
     * Called to see if the entity is alive
     *
     * @return true if alive
     */
    public boolean isAlive()
    {
        return isAlive;
    }

    /**
     * Sets the entity dead
     */
    public void setDead()
    {
        this.isAlive = false;
    }
}
