package pathfinder;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import pathfinder.Enum.CellValueEnum;
import pathfinder.Model.Cell;
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
        destinations.add(new Tuple<>(12, 7));
        destinations.add(new Tuple<>(6, 7));
        destinations.add(new Tuple<>(1, 9));
        Tuple<Integer, Integer> startPoint = new Tuple<>(13, 3);
        pathFinder = new PathFinder(destinations);
        FillMatrixObstacles();
        ArrayList<String> errors = pathFinder.FindTotalPath( pathFinder.GetStatPoint(startPoint), pathFinder.GetDestinations());
        if (errors.isEmpty())
        {
            pathFinder.ShowMatrix();
        }
        else
        {
            errors.forEach((erro) ->
            {
                System.err.println(String.format("- %s", erro));
            });
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
    
    private static void FillMatrixObstaclesManually() throws InterruptedException
    {
        int stop;
        Scanner s = new Scanner(System.in);
        do
        {
            pathFinder.ShowMatrix();
            System.out.println("Digite as coordenadas para inserir um obstáculo");
            System.out.print("Linha: ");
            int line = s.nextInt();
            System.out.print("Coluna: ");
            int column = s.nextInt();
            if(IsCoordinateValid(line, column))
            {
                System.out.println("Salvando...");
                pathFinder.matrix[line][column].setValue(CellValueEnum.OBSTACLE.Value);
            }
            else
            {
                System.out.println("Digite uma coordenada válida");
            }
            Thread.sleep(500);
            System.out.print("Deseja sair? [1 = S] [2 = N] ");
            stop = s.nextInt();
            ClearScreen();
        } while (stop != 1);
    }
    
    private static void FillMatrixObstacles()
    {
        ArrayList<Tuple<Integer, Integer>> obstacles = new ArrayList<>();
        obstacles.add(new Tuple<>(0, 0));
        obstacles.add(new Tuple<>(1, 0));
        obstacles.add(new Tuple<>(2, 0));
        obstacles.add(new Tuple<>(3, 0));
        obstacles.add(new Tuple<>(4, 0));
        obstacles.add(new Tuple<>(5, 0));
        obstacles.add(new Tuple<>(6, 0));
        obstacles.add(new Tuple<>(7, 0));
        obstacles.add(new Tuple<>(8, 0));
        obstacles.add(new Tuple<>(9, 0));
        obstacles.add(new Tuple<>(12, 0));
        obstacles.add(new Tuple<>(11, 0));
        obstacles.add(new Tuple<>(10, 0));
        obstacles.add(new Tuple<>(0, 11));
        obstacles.add(new Tuple<>(0, 1));
        obstacles.add(new Tuple<>(0, 2));
        obstacles.add(new Tuple<>(0, 3));
        obstacles.add(new Tuple<>(0, 4));
        obstacles.add(new Tuple<>(0, 5));
        obstacles.add(new Tuple<>(0, 6));
        obstacles.add(new Tuple<>(0, 7));
        obstacles.add(new Tuple<>(0, 8));
        obstacles.add(new Tuple<>(0, 9));
        obstacles.add(new Tuple<>(0, 10));
        obstacles.add(new Tuple<>(2, 3));
        obstacles.add(new Tuple<>(6, 3));
        obstacles.add(new Tuple<>(5, 3));
        obstacles.add(new Tuple<>(4, 3));
        obstacles.add(new Tuple<>(3, 3));
        obstacles.add(new Tuple<>(2, 6));
        obstacles.add(new Tuple<>(2, 4));
        obstacles.add(new Tuple<>(2, 5));
        obstacles.add(new Tuple<>(6, 6));
        obstacles.add(new Tuple<>(6, 4));
        obstacles.add(new Tuple<>(6, 5));
        obstacles.add(new Tuple<>(3, 6));
        obstacles.add(new Tuple<>(4, 6));
        obstacles.add(new Tuple<>(5, 6));
        obstacles.add(new Tuple<>(9, 3));
        obstacles.add(new Tuple<>(12, 3));
        obstacles.add(new Tuple<>(12, 6));
        obstacles.add(new Tuple<>(9, 6));
        obstacles.add(new Tuple<>(9, 4));
        obstacles.add(new Tuple<>(9, 5));
        obstacles.add(new Tuple<>(12, 4));
        obstacles.add(new Tuple<>(12, 5));
        obstacles.add(new Tuple<>(3, 8));
        obstacles.add(new Tuple<>(11, 8));
        obstacles.add(new Tuple<>(10, 8));
        obstacles.add(new Tuple<>(9, 8));
        obstacles.add(new Tuple<>(8, 8));
        obstacles.add(new Tuple<>(7, 8));
        obstacles.add(new Tuple<>(6, 8));
        obstacles.add(new Tuple<>(5, 8));
        obstacles.add(new Tuple<>(4, 8));
        obstacles.add(new Tuple<>(2, 12));
        obstacles.add(new Tuple<>(5, 12));
        obstacles.add(new Tuple<>(4, 12));
        obstacles.add(new Tuple<>(3, 12));
        obstacles.add(new Tuple<>(9, 12));
        obstacles.add(new Tuple<>(12, 12));
        obstacles.add(new Tuple<>(11, 12));
        obstacles.add(new Tuple<>(10, 12));
        obstacles.add(new Tuple<>(9, 14));
        obstacles.add(new Tuple<>(10, 14));
        obstacles.add(new Tuple<>(11, 14));
        obstacles.add(new Tuple<>(12, 14));
        obstacles.forEach((obstacle) -> {
            pathFinder.matrix[obstacle.key][obstacle.value].setValue(CellValueEnum.OBSTACLE.Value);
        });
    }
    
    private static boolean IsCoordinateValid(int line, int column)
    {
        return ((line >=0) && (line < pathFinder.LineLength)) && ((column >= 0) && (column < pathFinder.ColumnLength));
    }
}
