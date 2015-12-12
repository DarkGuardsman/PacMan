package pacman.entities;

import pacman.Game;

import java.awt.*;

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
    public void draw(Graphics g, int startCornerX, int startCornerY, float scale)
    {
        g.setColor(Color.white);
        g.fillRect(startCornerX + x + (int) (10 * scale), startCornerY + y + (int) (10 * scale), (int) (10 * scale), (int) (10 * scale));
    }

    @Override
    public boolean needsTick()
    {
        return false;
    }
}
