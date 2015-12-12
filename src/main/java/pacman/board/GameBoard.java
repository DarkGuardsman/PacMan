package pacman.board;

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
}
