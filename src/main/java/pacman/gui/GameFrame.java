package pacman.gui;

import pacman.Game;
import pacman.board.GameBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Game board
 *
 * @author Robert Seifert
 * @version 12-11.2015
 */
public class GameFrame extends JFrame
{
    protected final Game game;

    int startCornerY = 50;
    int startCornerX = 50;

    public GameFrame(final Game game)
    {
        this.game = game;
        addWindowListener(new WindowAdapter()
                          {
                              public void windowClosing(WindowEvent e)
                              {
                                  //TODO open main menu instead
                                  dispose();
                                  game.exitToMenu();
                              }
                          }
        );
    }

    @Override
    public void paint(Graphics g)
    {
        GameBoard board = game.getBoard();
        int size = 20;
        if (board != null)
        {
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
