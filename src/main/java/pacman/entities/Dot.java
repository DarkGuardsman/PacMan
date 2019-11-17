package pacman.entities;

import pacman.Game;

import java.awt.Color;
import java.awt.Graphics;

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

    /**
     * Draws a 20 pixel white dot
     */
    @Override
    public void draw(Graphics g, int x, int y, float scale)
    {
        g.setColor(Color.white);
        int size = (int) (5 * scale);
        int boxScale = (int) (scale * 20) / 2;
        g.fillRect(x + boxScale - (size / 2), y + boxScale - (size / 2), size, size);
    }

    @Override
    public boolean needsTick()
    {
        return false;
    }
}
