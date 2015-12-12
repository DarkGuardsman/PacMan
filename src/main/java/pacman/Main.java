package pacman;

/**
 * @author Robert Seifert
 * @version 12-10.2015
 */
public class Main
{
    /**
     * Main method
     * @param args - not used
     */
    public static void main(String... args)
    {
        //Create game
        //TODO later open Menu
        Game game = new Game();
        game.isPaused = true;
        game.openGUI();
        game.run();
    }
}
