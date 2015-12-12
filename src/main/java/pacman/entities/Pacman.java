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
    private int dotsEaten = 0;
    private Direction facing = Direction.RIGHT;
    private boolean isMoving = false;
    private int lives = 1;

    private boolean changeDirection = false;
    private boolean hitWall = false;
    private int deathTicks = 0;

    public Pacman(Game game)
    {
        super(game);
    }

    @Override
    public void tick()
    {
        super.tick();
        if (isMoving())
        {
            this.changeDirection = false;
            setIsMoving(false);
            if (!moveTo(x + getFacing().x, y + getFacing().y))
            {
                hitWall = true;
            }
        }
    }

    @Override
    public void onCollide(Entity entity)
    {
        if (entity instanceof Dot)
        {
            entity.setDead();
            this.setDotsEaten(this.getDotsEaten() + 1);
        }
        else if (entity instanceof Monster)
        {
            setDead();
        }
    }

    @Override
    public void setDead()
    {
        super.setDead();
        if (lives > 0)
        {
            lives -= 1;
        }
    }

    @Override
    public void draw(Graphics g, int x, int y, float scale)
    {
        g.setColor(Color.yellow);
        int size = (int) (16 * scale);
        int boxScale = (int) (scale * 20) / 2;
        int cx = x + boxScale - (size / 2);
        int cy = y + boxScale - (size / 2);


        g.setColor(Color.yellow);
        int angle = 0;
        int arc = !changeDirection ? 300 : hitWall ? 190 : 220;
        switch (getFacing())
        {
            case UP:
                angle = 130;
                break;
            case DOWN:
                angle = 315;
                break;
            case LEFT:
                angle = 210;
                break;
            case RIGHT:
                angle = 30;
                break;
        }
        if (isAlive)
        {
            g.fillArc(cx, cy, size, size, angle, arc);
        }
        else if (deathTicks < 120)
        {
            g.fillArc(cx, cy, size, size, angle - (int) (arc * ((float) deathTicks / 120f)), arc - (int) (arc * ((float) deathTicks / 120f)));
            deathTicks++;
            if (deathTicks >= 120 && lives > 0)
            {
                this.setPos(game.spawnX, game.spawnY);
                this.isAlive = true;
                deathTicks = 0;
            }
        }
    }

    /** Number of dots eaten */
    public int getDotsEaten()
    {
        return dotsEaten;
    }

    public void setDotsEaten(int dotsEaten)
    {
        this.dotsEaten = dotsEaten;
    }

    /** Direction pacman is facing */
    public Direction getFacing()
    {
        return facing;
    }

    public void setFacing(Direction facing)
    {
        if (facing != this.facing)
        {
            this.facing = facing;
            this.changeDirection = true;
            this.hitWall = false;
        }
    }

    /** Is the entity moving */
    public boolean isMoving()
    {
        return isMoving;
    }

    public void setIsMoving(boolean isMoving)
    {
        this.isMoving = isMoving;
    }

    /** Number of lives */
    public int getLives()
    {
        return lives;
    }

    public void setLives(int lives)
    {
        this.lives = lives;
    }
}
