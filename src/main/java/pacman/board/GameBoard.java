package pacman.board;

import pacman.Game;
import pacman.entities.Dot;
import pacman.entities.Monster;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Game board
 *
 * @author Robert Seifert
 * @version 12-10.2015
 */
public class GameBoard
{
    public final int sizeX;
    public final int sizeY;

    protected final byte[][] grid; //Note for very large maps a different storage system may be needed

    /**
     * Creates a new GameBoard
     *
     * @param x - size x (must be > 0)
     * @param y - size y (must be > 0)
     * @throws IllegalArgumentException if map size is too small
     */
    public GameBoard(int x, int y)
    {
        if (x <= 0 || y <= 0)
        {
            throw new IllegalArgumentException("Invalid map size");
        }
        this.sizeX = x;
        this.sizeY = y;
        grid = new byte[x][y];
    }

    /**
     * Adds a wall to the board
     *
     * @param x - location x
     * @param y - location y
     */
    public void addWall(int x, int y)
    {
        this.grid[x][y] = 1;
    }

    /**
     * Removes a wall from the board
     *
     * @param x - location x
     * @param y - location y
     */
    public void removeWall(int x, int y)
    {
        this.grid[x][y] = 0;
    }

    /**
     * Fills in the edges with walls.
     */
    public void generateEdges()
    {
        for (int x = 0; x < sizeX; x++)
        {
            addWall(x, 0);
            addWall(x, sizeY - 1);
        }
        for (int y = 1; y < sizeY - 1; y++)
        {
            addWall(0, y);
            addWall(sizeX - 1, y);
        }
    }


    /**
     * Checks if the location is a wall
     *
     * @param x - location x
     * @param y - location y
     * @return true if location is a wall
     */
    public boolean containsWall(int x, int y)
    {
        return this.grid[x][y] == 1;
    }

    /**
     * Loads up a map
     *
     * @param game
     * @param map
     */
    public static GameBoard load(Game game, String map)
    {
        GameBoard board = null;
        try
        {
            //Open file
            InputStream in = GameBoard.class.getResourceAsStream("/assets/maps/" + map + ".txt");
            if (in != null)
            {
                BufferedReader input = new BufferedReader(new InputStreamReader(in));

                //Read lines

                //First line contains map size for quick setup
                String line = input.readLine();
                if (line != null)
                {
                    String[] split = line.split(" ");
                    int sizeX = Integer.parseInt(split[0]);
                    int sizeY = Integer.parseInt(split[1]);
                    board = new GameBoard(sizeX, sizeY);


                    int y = 0;
                    int x = 0;
                    while (line != null)
                    {
                        //Parse line
                        for (char c : line.toCharArray())
                        {
                            if (c == '0')
                            {
                                board.grid[x][y] = 0;
                            }
                            else if (c == '1')
                            {
                                board.grid[x][y] = 1;
                            }
                            else if (c == '2')
                            {
                                board.grid[x][y] = 0;
                                Dot dot = new Dot(game);
                                dot.setPos(x, y);
                                game.addEntity(dot);
                            }
                            else if (c == '3')
                            {
                                board.grid[x][y] = 0;
                                Monster mob = new Monster(game);
                                mob.setPos(x, y);
                                game.addEntity(mob);
                            }
                            else if (c == '4')
                            {
                                board.grid[x][y] = 0;
                                game.setSpawn(x, y);
                            }
                            x++;
                        }
                        y++;

                        //Next line
                        line = input.readLine();
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(game.frame, "Failed to find map " + map + "!\n  Invalid map format.");
                }
            }
            else
            {
                JOptionPane.showMessageDialog(game.frame, "Failed to find map " + map);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(game.frame, "Failed to load map " + map);
            game.exitToMenu();
        }
        return board;
    }
}
