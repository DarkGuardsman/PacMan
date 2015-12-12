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

    /**
     * Called to open the GUI
     */
    public void launch()
    {
        setVisible(true);
        view.createBufferStrategy(2);
    }

    /**
     * Called each tick to update the screen render
     */
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
                for (Entity entity : game.dots)
                {
                    entity.draw(g, startCornerX + size * entity.x(), startCornerY + size * entity.y(), size / 20);
                }
                for (Entity entity : game.monsters)
                {
                    entity.draw(g, startCornerX + size * entity.x(), startCornerY + size * entity.y(), size / 20);
                }
                //Player needs to draw over everything
                game.player.draw(g, startCornerX + size * game.player.x(), startCornerY + size * game.player.y(), size / 20);

                //Draw in game GUI
                g.setColor(Color.YELLOW);
                g.drawString("Score: " + game.player.getDotsEaten() * 25, 25, 25);
                g.drawString("Lives: " + game.player.getLives(), 25, 35);
                if (game.isPaused)
                {
                    g.drawString("PAUSED", startCornerX + size * (board.sizeX / 2), 20);
                    g.drawString("~press [PAUSE] to continue", startCornerX + size * (board.sizeX / 2), 35);
                    g.drawString("~press W,A,S,D to move", startCornerX, startCornerY + size * board.sizeY);
                }
                if (game.player.getLives() <= 0)
                {
                    g.setColor(Color.RED);
                    g.drawString("GAME OVER", startCornerX + size * (board.sizeX / 2), startCornerY + size * (board.sizeY / 2));
                }
                else if(game.win)
                {
                    g.setColor(Color.GREEN);
                    g.drawString("WINNER", startCornerX + size * (board.sizeX / 2), startCornerY + size * (board.sizeY / 2));
                }
            }
            else
            {
                g.setColor(Color.YELLOW);
                g.drawString("NO MAP", getSize().width / 2 - 3, getSize().height / 2);
            }
            view.getBufferStrategy().show();
        }
        catch (Exception e)
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
