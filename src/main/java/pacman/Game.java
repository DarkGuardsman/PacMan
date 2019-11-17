package pacman;

import pacman.board.GameBoard;
import pacman.entities.Dot;
import pacman.entities.Entity;
import pacman.entities.Monster;
import pacman.entities.Pacman;
import pacman.gui.GameFrame;
import pacman.util.Direction;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

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
    /** List of all monsters */
    public List<Entity> monsters = new ArrayList();
    /** List of all dots */
    public List<Entity> dots = new ArrayList();
    /** GUI of the game */
    public GameFrame frame;
    /** Spawn location */
    public int spawnX = 0;
    /** Spawn location */
    public int spawnY = 0;
    /** Has the player won */
    public boolean win = false;
    public int winTicks = 0;

    /** List update entities */
    protected List<Entity> updateList = new ArrayList();

    /** Toggles if the game is paused */
    public boolean isPaused = false;
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

    public void load()
    {
        //Cleanup if new map is loaded over old
        monsters.clear();
        dots.clear();
        win = false;
        winTicks = 0;
        isPaused = true;

        //Load new map
        board = GameBoard.load(this, mapName);
        player.setPos(spawnX, spawnY);
        addEntity(player);
    }

    /**
     * Starts the game loop
     */
    public void run()
    {
        load();
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
                Iterator<Entity> it = dots.iterator();
                while (it.hasNext())
                {
                    Entity ent = it.next();
                    if (!ent.isAlive())
                    {
                        it.remove();
                    }
                }
                it = monsters.iterator();
                while (it.hasNext())
                {
                    Entity ent = it.next();
                    if (!ent.isAlive())
                    {
                        it.remove();
                        if (updateList.contains(ent))
                        {
                            updateList.remove(ent);
                        }
                    }
                }
                if (!win)
                {
                    if (dots.size() == 0)
                    {
                        win = true;
                        player.setLives(Math.min(3, player.getLives() + 1));
                        for (Entity entity : monsters)
                        {
                            entity.setDead();
                        }
                        if (mapName == "testMap1")
                        {
                            mapName = "testMap2";
                        }
                    }
                }
                else
                {
                    winTicks++;
                    if (winTicks >= 500)
                    {
                        load();
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
        System.exit(0);
    }

    /**
     * Loads up the even handlers for player keyboard controls
     */
    protected void loadControls()
    {
        KeyEventDispatcher dis = new KeyEventDispatcher()
        {
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
     * Passes all entities into the collector function
     *
     * @param x - x location
     * @param y - y location
     */
    public void loopEntitiesAt(int x, int y, Consumer<Entity> collector)
    {
        monsters.stream()
                .filter(m -> m.x() == x && m.y() == y)
                .forEach(collector);

        dots.stream()
                .filter(d -> d.x() == x && d.y() == y)
                .forEach(collector);

        if (player.x() == x && player.y() == y)
        {
           collector.accept(player);
        }
    }

    /**
     * Adds entity to the game
     *
     * @param entity - entity to add
     */
    public void addEntity(Entity entity)
    {
        if (entity instanceof Dot && !dots.contains(entity))
        {
            dots.add(entity);
            if (entity.needsTick())
            {
                updateList.add(entity);
            }
        }
        else if (entity instanceof Monster && !monsters.contains(entity))
        {
            monsters.add(entity);
        }

        //Added entities to update list
        if (entity.needsTick() && !updateList.contains(entity))
        {
            updateList.add(entity);
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
