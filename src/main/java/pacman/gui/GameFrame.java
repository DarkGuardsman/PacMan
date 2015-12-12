package pacman.gui;

import pacman.Game;
import pacman.board.GameBoard;
import pacman.entities.Entity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * GUI that draws the game board and player actions
 *
 * @author Robert Seifert
 * @version 12-11.2015
 */
public class GameFrame extends JFrame
{
    /** Current game being rendered. */
    protected final Game game;
    /** Starting point for draw code. */
    int startCornerY = 50;
    /** Starting point for draw code. */
    int startCornerX = 50;

    public GameFrame(final Game game)
    {
        this.game = game;
        //Catch to prevent player from exiting the game right away
        addWindowListener(new WindowAdapter()
                          {
                              public void windowClosing(WindowEvent e)
                              {
                                  dispose();
                                  game.exitToMenu();
                              }
                          }
        );
        //Used to check for resize of window
        addComponentListener(new ComponentListener()
        {
            public void componentResized(ComponentEvent e)
            {

            }

            @Override
            public void componentMoved(ComponentEvent e)
            {

            }

            @Override
            public void componentShown(ComponentEvent e)
            {

            }

            @Override
            public void componentHidden(ComponentEvent e)
            {

            }
        });
    }

    @Override
    public void paint(Graphics g)
    {
        GameBoard board = game.getBoard();
        if (board != null)
        {
            int size = getSize().width > getSize().height ? (int) (getSize().height * 0.85) : (int) (getSize().width * 0.85);
            size = size / (board.sizeX > board.sizeY ? board.sizeX : board.sizeY);

            g.setColor(Color.black);
            g.fillRect(1, 1, getSize().width - 1, getSize().height - 1);
            for (int x = 0; x < board.sizeX; x++)
            {
                for (int y = 0; y < board.sizeY; y++)
                {
                    if (board.containsWall(x, y))
                    {
                        drawBoxFilled(g, x, y, size);
                    }
                    drawBox(g, x, y, size);
                }
            }

            for (Entity entity : game.entities)
            {
                entity.draw(g, startCornerX, startCornerY, size / 20);
            }
        }
    }

    protected void drawBox(Graphics g, int x, int y, int size)
    {
        g.setColor(Color.green);
        g.drawRect(startCornerX + size * x, startCornerY + size * y, size, size);
    }

    protected void drawBoxFilled(Graphics g, int x, int y, int size)
    {
        g.setColor(Color.GRAY);
        g.fillRect(startCornerX + size * x, startCornerY + size * y, size, size);
    }
}
