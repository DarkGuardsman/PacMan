package pacman.util;

/**
 * Simple enum for holding directions
 *
 * @author Robert Seifert
 * @version 12-10.2015
 */
public enum Direction
{
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0),
    NONE(0, 0);

    public final int x;
    public final int y;

    Direction(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
}
