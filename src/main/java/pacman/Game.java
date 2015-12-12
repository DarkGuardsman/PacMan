package pacman;

import pacman.board.GameBoard;
import pacman.entities.Entity;
import pacman.entities.Pacman;
import pacman.gui.GameFrame;
import pacman.util.Direction;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;

/**
 * Running game instance, only one of these should exist at a time.
 *
 * @author Robert Seifert
 * @version 12-10.2015
 */
public class Game
{
    /** Current player */
    public Pacman player;
    /** List of all game peaces */
    public List<Entity> entities = new ArrayList();
    /** GUI of the game */
    public GameFrame frame;
    /** Spawn location */
    public int spawnX = 0;
    /** Spawn location */
    public int spawnY = 0;

    /** List update entities */
    protected List<Entity> updateList = new ArrayList();

    /** Toggles if the game is paused */
    protected boolean isPaused = false;
    protected boolean exit = false;

    /** Current loaded game board */
    protected GameBoard board;
    /** Name of the map */
    protected String mapName;

    public Game(String map)
    {
        player = new Pacman(this);
        mapName = map;
    }

    /**
     * Starts the game loop
     */
    public void run()
    {
        board = GameBoard.load(this, mapName);
        player.setPos(spawnX, spawnY);
        addEntity(player);
        openGUI();
        //Loop
        while (!shouldExit())
        {
            if (!isPaused)
            {
                //Update entities
                for (Entity entity : updateList)
                {
                    entity.tick();
                }

                //Remove dead objects
                Iterator<Entity> it = entities.iterator();
                while (it.hasNext())
                {
                    Entity ent = it.next();
                    if (!ent.isAlive() && ent != player)
                    {
                        it.remove();
                        if (updateList.contains(ent))
                        {
                            updateList.remove(ent);
                        }
                    }
                }
            }
            frame.tick();
            //Wait 17ms so we have 60 game ticks a second
            try
            {
                Thread.sleep(17);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * Opens the game GUI that the player sees
     */
    public void openGUI()
    {
        if (frame == null)
        {
            frame = new GameFrame(this);
            frame.setTitle("Kata Pacman by Robert Seifert");
            frame.setSize(800, 600);
            frame.launch();
            loadControls();
        }
    }

    public void exitToMenu()
    {
        this.exit = true;
    }

    /**
     * Loads up the even handlers for player keyboard controls
     */
    protected void loadControls()
    {
        KeyEventDispatcher dis = new KeyEventDispatcher()
        {
            @Override
            public boolean dispatchKeyEvent(KeyEvent ke)
            {
                if (ke.getID() == KeyEvent.KEY_PRESSED)
                {
                    if (ke.getKeyCode() == KeyEvent.VK_PAUSE)
                    {
                        isPaused = !isPaused;
                    }
                    else if (!isPaused && player.isAlive())
                    {
                        if (ke.getKeyCode() == KeyEvent.VK_W)
                        {
                            if (player.getFacing() != Direction.UP)
                            {
                                player.setFacing(Direction.UP);
                            }
                            else
                            {
                                player.setIsMoving(true);
                            }
                        }
                        else if (ke.getKeyCode() == KeyEvent.VK_S)
                        {
                            if (player.getFacing() != Direction.DOWN)
                            {
                                player.setFacing(Direction.DOWN);
                            }
                            else
                            {
                                player.setIsMoving(true);
                            }
                        }
                        else if (ke.getKeyCode() == KeyEvent.VK_A)
                        {
                            if (player.getFacing() != Direction.LEFT)
                            {
                                player.setFacing(Direction.LEFT);
                            }
                            else
                            {
                                player.setIsMoving(true);
                            }
                        }
                        else if (ke.getKeyCode() == KeyEvent.VK_D)
                        {
                            if (player.getFacing() != Direction.RIGHT)
                            {
                                player.setFacing(Direction.RIGHT);
                            }
                            else
                            {
                                player.setIsMoving(true);
                            }
                        }
                        else if (ke.getKeyCode() == KeyEvent.VK_ESCAPE)
                        {
                            if (!exit)
                            {
                                exitToMenu();
                            }
                        }
                    }
                }
                return false;
            }
        };
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(dis);
    }

    /**
     * Checks each tick too see if the player wants
     * to exit
     *
     * @return true if should exit
     */

    protected boolean shouldExit()
    {
        return exit;
    }

    /**
     * Gets the game board
     *
     * @return GameBoard
     */
    public GameBoard getBoard()
    {
        return board;
    }

    /**
     * Gets all entities currently at a location
     *
     * @param x - x location
     * @param y - y location
     * @return list, or empty list if nothing is found
     */
    public List<Entity> getEntitiesAt(int x, int y)
    {
        List<Entity> list = new ArrayList();
        for (Entity entity : entities)
        {
            if (entity.x() == x && entity.y() == y)
            {
                list.add(entity);
            }
        }
        return list;
    }

    /**
     * Adds entity to the game
     *
     * @param entity - entity to add
     */
    public void addEntity(Entity entity)
    {
        if (!entities.contains(entity))
        {
            entities.add(entity);
            if (entity.needsTick())
            {
                updateList.add(entity);
            }
        }
    }

    /**
     * Sets the player's spawn point
     *
     * @param x - location x
     * @param y - location y
     */
    public void setSpawn(int x, int y)
    {
        this.spawnX = x;
        this.spawnY = y;
    }
}
