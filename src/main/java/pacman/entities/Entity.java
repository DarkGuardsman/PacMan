package pacman.entities;

import pacman.Game;

import java.awt.*;
import java.util.List;

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

    /**
     * Called to move the entity to the next
     * location.
     *
     * @param newX - next x location
     * @param newY - next y location
     * @return true if the entity was moved.
     */
    public boolean moveTo(int newX, int newY)
    {
        if (canMoveTo(newX, newY))
        {
            List<Entity> entities = game.getEntitiesAt(newX, newY);
            for (Entity entity : entities)
            {
                onCollide(entity);
            }
            this.x = newX;
            this.y = newY;
            return true;
        }
        return false;
    }

    /**
     * Tests if the entity can move to the next location
     *
     * @param newX - next x location
     * @param newY - next y location
     * @return true if the entity can move
     */
    public boolean canMoveTo(int newX, int newY)
    {
        return !game.getBoard().containsWall(newX, newY);
    }

    /**
     * Draws the entity
     *
     * @param g - graphics instance
     * @param startCornerX
     * @param startCornerY
     */
    public abstract void draw(Graphics g, int startCornerX, int startCornerY, float scale);

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

    /**
     * X location
     *
     * @return x
     */
    public int x()
    {
        return x;
    }

    /**
     * Y location
     *
     * @return y
     */
    public int y()
    {
        return y;
    }

    public void setPos(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
}
