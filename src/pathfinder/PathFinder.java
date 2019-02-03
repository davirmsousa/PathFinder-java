package pathfinder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import pathfinder.Enum.CellValueEnum;
import pathfinder.Model.Cell;
import pathfinder.Util.Tuple;

/**
 * @author Davi Rocha
 */
public class PathFinder
{
    public Cell[][] matrix;
    public final int LineLength = 14;
    public final int ColumnLength = 15;
    private final ArrayList<Tuple<Integer, Integer>> destinations;
    private final ArrayList<String> errors;

    /**
     * Constructor
     * @param destinations An ArrayList filled with all destinations
    */
    public PathFinder(ArrayList<Tuple<Integer, Integer>> destinations)
    {
        this.errors = new ArrayList<>();
        this.destinations = destinations;
        matrix = InitMatrix(LineLength, ColumnLength);
    }
    
    /**
     * Method to init the matrix map
     * @param lines Lines of the matrix
     * @param columns Columns of the matrix
     * @return The matrix initialized
     */
    private Cell[][] InitMatrix(int lines, int columns)
    {
        Cell[][] _matrix = new Cell[lines][columns];
        for (int i = 0; i < lines; i++)
        {
            for (int j = 0; j < columns; j++)
            {
                _matrix[i][j] = new Cell( new ArrayList<>(), 0, 0, 0, false, CellValueEnum.WALKABLE.Value, i, j );
            }
        }
        return _matrix;
    }
    
    /**
     * Method to print the matrix on console
     */
    public void ShowMatrix()
    {
        System.out.print("      ");
        for (int j = 0; j < ColumnLength; j++)
        {
            System.out.print(String.format("|%2d |", j));
        }
        System.out.println();
        for (int i = 0; i < LineLength; i++)
        {
            System.out.print(String.format("| %2d  ", i));
            for (int j = 0; j < ColumnLength; j++)
            {
                if (matrix[i][j].getValue() == 1)
                {
                    System.out.print("|||||");
                }
                else
                {
                    if(matrix[i][j].isWay())
                    {
                        System.out.print(". . .");
                    }
                    else
                    {
                        System.out.print("     ");
                    }
                }
            }
            System.out.println();
        }
    }
    
    /**
     * Method to get a cell by a Tuple object
     * @param position A tuple object with the position of the cell that you want to get
     * @return <code>null</code> if the cell was not found; <code>Cell</code> if the cell was found
     */
    private Cell GetCellByTuple(Tuple<Integer, Integer> position)
    {
        if (((position.key >=0) && (position.key < LineLength)) && ((position.value >= 0) && (position.value < ColumnLength)))
        {
            return matrix[position.key][position.value];
        }
        return null;
    }
    
    /**
     * Method to get an ArrayList of Cell with destinations
     * @return ArrayList with Cell destinations
     */
    public ArrayList<Cell> GetDestinations()
    {
        ArrayList<Cell> _destinations = new ArrayList<>();
        destinations.forEach((destin) ->
        {
            Cell cellDestin = GetCellByTuple(destin);
            if(cellDestin != null)
            {
                cellDestin.setValue(CellValueEnum.ENDPOINT.Value);
                _destinations.add(cellDestin);
            }
        });
        return _destinations;
    }
    
    /**
     * Method to get a Cell object that be the start point of the path
     * @param position A tuple object with the position of the cell on metrix
     * @return <code>null</code> if the cell was not found; <code>Cell</code> if the cell was found
     */
    public Cell GetStatPoint(Tuple<Integer, Integer> position)
    {
        return GetCellByTuple(position);
    }
    
    /**
     * Method to clear the matrix
     * @param _matrix The matrix to clear
     * @return The matrix cleaned
     */
    private Cell[][] CleanMatrix(Cell[][] _matrix)
    {
        for (int i = 0; i < LineLength; i++)
        {
            for (int j = 0; j < ColumnLength; j++)
            {
                if (_matrix[i][j].getValue() == 0)
                {
                    //_matrix[i][j].setFather(null);
                    _matrix[i][j].setFcost(0);
                    _matrix[i][j].setGcost(0);
                    _matrix[i][j].setHcost(0);
                    //_matrix[i][j].setWay(false);
                }
            }
        }
        return _matrix;
    }
    
    /**
     * Method to sort a ArrayList with Cell destinations based in absolut distance between the start point
     * @param startPoint A cell object that is the starting point
     * @param _destinations An ArrayList of Cells with all destinations
     * @return A sorted ArrayList of Cells
     */
    private ArrayList<Cell> SortDestinations(Cell startPoint, ArrayList<Cell> _destinations)
    {
        Collections.sort(_destinations, (o1, o2) ->
        {
            return CalcuLateHCost(startPoint, o1) - CalcuLateHCost(startPoint, o2);
        });
        return _destinations;
    }
    
    /**
     * Method to init the search for the short path
     * @param startPoint A Cell object that is the start point
     * @param destinations An ArrayList of Cell that the algoritm need to pass by
     * @return An ArrayList with errors
     */
    public ArrayList<String> FindTotalPath(Cell startPoint, ArrayList<Cell> destinations)
    {
        destinations = SortDestinations(startPoint, destinations);
        if((startPoint != null) && (!(destinations.isEmpty()) && (destinations.size() > 0)))
        {
            for (Cell endPoint : destinations)
            {
                if (FindPath(matrix, startPoint, endPoint))
                {
                    startPoint = endPoint;
                    matrix = CleanMatrix(matrix);
                }
                else
                {
                    String error = String.format("O caminho entre [%d,%d] e [%d,%d] não foi encontrado;", startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY());
                    errors.add(error);
                }
            }
            if(errors.isEmpty())
            {
                matrix = HighlightPath(matrix, destinations.get(destinations.size() - 1));
                return errors;
            }
            else
            {
                return errors;
            }
        }
        errors.add("startPoint ou lista de destinos nula;");
        return errors;
    }
    
    /**
     * Method to find the short path between two points
     * @param matrix A matrix that represents a map
     * @param startPoint A Cell object that is the start point of the path
     * @param endPoint A Cell object that is the end point of the path
     * @return An boolean that represents of the algorithm found the path
     */
    public boolean FindPath(Cell[][] matrix, Cell startPoint, Cell endPoint)
    {
        Cell current;
        boolean found = false;
        ArrayList<Cell> openList = new ArrayList<>();
        ArrayList<Cell> closedList = new ArrayList<>();
        openList.add(startPoint);
        do
        {
            current = openList.get(0);
            if (current.Equals(endPoint)  && current.getValue() == CellValueEnum.ENDPOINT.Value)
            {
                found = true;
                continue;
            }
            if (openList.isEmpty())
            {
                continue;
            }
            openList.remove(0);
            closedList.add(current);
            ArrayList<Cell> neighborsOfCurrentCell = FindNeighbors(current, true);
            for (Cell neighbor : neighborsOfCurrentCell)
            {
                if ((neighbor.getValue() == CellValueEnum.OBSTACLE.Value) || (closedList.stream().anyMatch(c -> c.Equals(neighbor))))
                {
                    continue;
                }
                if (!openList.stream().anyMatch(c -> c.Equals(neighbor)))
                {
                    matrix[neighbor.getX()][neighbor.getY()].setFather(current);
                    matrix[neighbor.getX()][neighbor.getY()].setHcost(CalcuLateHCost(endPoint, neighbor));
                    matrix[neighbor.getX()][neighbor.getY()].setGcost(CalcuLateGCost(current, neighbor));
                    matrix[neighbor.getX()][neighbor.getY()].setFcost(matrix[neighbor.getX()][neighbor.getY()].getHcost() + matrix[neighbor.getX()][neighbor.getY()].getGcost());
                    openList.add(neighbor);
                }
                else
                {
                    int newGCost = CalcuLateGCost(current, neighbor);
                    if (newGCost <= matrix[neighbor.getX()][neighbor.getY()].getGcost())
                    {
                        matrix[neighbor.getX()][neighbor.getY()].setGcost(newGCost);
                        matrix[neighbor.getX()][neighbor.getY()].setFcost(matrix[neighbor.getX()][neighbor.getY()].getHcost() + matrix[neighbor.getX()][neighbor.getY()].getGcost());
                        matrix[neighbor.getX()][neighbor.getY()].setFather(current);
                    }
                }
            }
            Collections.sort(openList);
        } while ((!found) && (!openList.isEmpty()));
        return found;
    }
    
    /**
     * Method to find the neighbors of a father
     * @param father The cell that you want to get the neighbors
     * @param diagonally <code> true </ code> if the algorithm should consider walk diagonally with obstacles
     * @return An ArrayList with the neighbors of the father
     */
    private ArrayList<Cell> FindNeighbors(Cell father, boolean diagonally)
    {
        ArrayList<Cell> neighbors = new ArrayList<>();
        if (father.getX() + 1 < LineLength)
        {
            neighbors.add(matrix[father.getX() + 1][father.getY()]);
            if (father.getY() + 1 < ColumnLength)
            {
                if ((matrix[father.getX()][father.getY() + 1].getValue() != CellValueEnum.OBSTACLE.Value || diagonally) && (matrix[father.getX() + 1][father.getY()].getValue() != CellValueEnum.OBSTACLE.Value || diagonally))
                {
                    neighbors.add(matrix[father.getX() + 1][father.getY() + 1]);
                }
            }
            if ((father.getY() - 1 < ColumnLength) && (father.getY() - 1 >= 0))
            {
                if ((matrix[father.getX()][father.getY() - 1].getValue() != CellValueEnum.OBSTACLE.Value || diagonally) && (matrix[father.getX() + 1][father.getY()].getValue() != CellValueEnum.OBSTACLE.Value || diagonally))
                {
                    neighbors.add(matrix[father.getX() + 1][father.getY() - 1]);
                }
            }
        }
        if (father.getY() + 1 < ColumnLength)
        {
            neighbors.add(matrix[father.getX()][father.getY() + 1]);
        }
        if ((father.getY() - 1 < ColumnLength) && (father.getY() - 1 >= 0))
        {
            neighbors.add(matrix[father.getX()][father.getY() - 1]);
        }
        if ((father.getX() - 1 < LineLength) && (father.getX() - 1 >= 0))
        {
            neighbors.add(matrix[father.getX() - 1][father.getY()]);
            if (father.getY() + 1 < ColumnLength)
            {
                if ((matrix[father.getX()][father.getY() + 1].getValue() != CellValueEnum.OBSTACLE.Value || diagonally) && (matrix[father.getX() - 1][father.getY()].getValue() != CellValueEnum.OBSTACLE.Value || diagonally))
                {
                    neighbors.add(matrix[father.getX() - 1][father.getY() + 1]);
                }
            }
            if ((father.getY() - 1 < ColumnLength) && (father.getY() - 1 >= 0))
            {
                if ((matrix[father.getX()][father.getY() - 1].getValue() != CellValueEnum.OBSTACLE.Value || diagonally) && (matrix[father.getX() - 1][father.getY()].getValue() != CellValueEnum.OBSTACLE.Value || diagonally))
                {
                    neighbors.add(matrix[father.getX() - 1][father.getY() - 1]);
                }
            }
        }
        return neighbors;
    }
    
    /**
     * Method to calculate the HCost of the cell (distance between the current cell and the end point)
     * @param endPoint The end point of the path
     * @param current The current cell that the algorithm is analyzing
     * @return An Integer that represents the HCost
     */
    private int CalcuLateHCost(Cell endPoint, Cell current)
    {
        int sum = 0;
        sum += Math.abs(endPoint.getX() - current.getX());
        sum += Math.abs(endPoint.getY() - current.getY());
        return sum;
    }
    
    /**
     * Method to celculate the GCost of the cell (distance between the current cell and the start point)
     * @param father The current cell that the algorithm is analyzing
     * @param neighbor neighbor of the father Cell
     * @return An Integer that represents the GCost
     */
    private int CalcuLateGCost(Cell father, Cell neighbor)
    {
        int sum;
        if ((father.getX() != neighbor.getX()) && (father.getY() != neighbor.getY()))
        {
            sum = father.getGcost() + 14;
        }
        else
        {
            sum = father.getGcost() + 10;
        }
        return sum;
    }
    
    /**
     * Method to highlight the path
     * @param _matrix The metrix after the algorithm finds the path
     * @param cell The end point of the path
     * @return The matrix with the path highlighted
     */
    private Cell[][] HighlightPath(Cell[][] _matrix, Cell cell)
    {
        while ((cell != null) && (cell.getValue() != CellValueEnum.STARTPOINT.Value))
        {
            _matrix[cell.getX()][cell.getY()].setWay(true);
            int X = cell.getX();
            int Y = cell.getY();
            cell = _matrix[X][Y].getFather(0);
            if(cell != null)
            {
                _matrix[X][Y].getFathers().remove(0);
            }
        }
        return _matrix;
    }
}
