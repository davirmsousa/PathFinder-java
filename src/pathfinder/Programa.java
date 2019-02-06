package pathfinder;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import pathfinder.Util.Tuple;

/**
 *
 * @author Home
 */
public class Programa
{
    static PathFinder pathFinder;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        ArrayList<Tuple<Integer, Integer>> destinations = new ArrayList<>();
            destinations.add(new Tuple<>(2, 2));
            destinations.add(new Tuple<>(8, 1));
            destinations.add(new Tuple<>(1, 9));
        Tuple<Integer, Integer> startPoint = new Tuple<>(13, 3);
        pathFinder = new PathFinder(startPoint, destinations);
        if(pathFinder.errors.size() > 0)
        {
            System.err.println(String.format("Erro ao iniciar navegação:\n\t- %s", pathFinder.errors.get(0)));
        }
        else
        {
            System.out.println("Sucesso ao iniciar navegação.");
        }
    }
    
    private static void ClearScreen()
    {
        try
        {
            Robot robot = new Robot();
            robot.setAutoDelay(10);
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_L);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyRelease(KeyEvent.VK_L);
        }
        catch (AWTException ex)
        {
            System.out.print ("Error: " + ex.getMessage());
        }
    }
}