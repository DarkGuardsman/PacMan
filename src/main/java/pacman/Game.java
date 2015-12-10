package pacman;

import pacman.entities.Entity;
import pacman.entities.Pacman;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Robert Seifert
 * @version 12-10.2015
 */
public class Game
{
    /** Current player */
    protected Pacman player;
    /** List of all game peaces */
    protected List<Entity> entities = new ArrayList();
    /** List update entities */
    protected List<Entity> updateList = new ArrayList();

    /**
     * Starts the game loop
     */
    public void run()
    {
        while(!shouldExit())
        {
            //Update entities
            for(Entity entity: updateList)
            {
                entity.tick();
            }

            //Remove dead objects
            Iterator<Entity> it = entities.iterator();
            while(it.hasNext())
            {
                Entity ent = it.next();
                if(!ent.isAlive())
                {
                    it.remove();
                    if(updateList.contains(ent))
                    {
                        it.remove();
                    }
                }
            }
        }
    }

    /**
     * Checks each tick too see if the player wants
     * to exit
     *
     * @return true if should exit
     */
    protected boolean shouldExit()
    {
        //TODO have exit key
        return false;
    }
}
