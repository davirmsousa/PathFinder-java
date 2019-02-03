package pathfinder.Enum;

/**
 * @author Davi Rocha
 */
public enum CellValueEnum
{
    WALKABLE(0), OBSTACLE(1), STARTPOINT(2), ENDPOINT(3);
    
    public int Value;
    
    CellValueEnum(int value)
    {
        Value = value;
    }
}
