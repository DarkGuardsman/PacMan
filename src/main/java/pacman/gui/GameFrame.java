package pacman.gui;

import pacman.Game;
import pacman.board.GameBoard;
import pacman.entities.Entity;

import javax.swing.*;
import java.awt.*;
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

    Canvas view;

    public GameFrame(final Game game)
    {
        this.game = game;

        view = new Canvas();
        view.setSize(getSize().width, getSize().height);
        view.setPreferredSize(getSize());
        add(view);
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
    }

    public void launch()
    {
        setVisible(true);
        view.createBufferStrategy(2);
    }

    public void tick()
    {
        try
        {
            Graphics g = view.getBufferStrategy().getDrawGraphics();
            GameBoard board = game.getBoard();
            if (board != null)
            {
                //Scale box sizes by window
                int size = getSize().width > getSize().height ? (int) (getSize().height * 0.85) : (int) (getSize().width * 0.85);
                size = size / (board.sizeX > board.sizeY ? board.sizeX : board.sizeY);

                //Make size even so rendering is easier
                if (size % 2 != 0)
                {
                    size -= 1;
                }

                //Render background
                g.setColor(Color.black);
                g.fillRect(1, 1, getSize().width - 1, getSize().height - 1);

                //Render tiles
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

                //Render entities
                for (Entity entity : game.entities)
                {
                    entity.draw(g, startCornerX + size * entity.x(), startCornerY + size * entity.y(), size / 20);
                }
            }
            view.getBufferStrategy().show();
        }
        catch(Exception e)
        {
            e.printStackTrace();
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
