package pathfinder.Model;

/**
 * @author Davi Rocha
 */
public class Cell implements Comparable
{
    private int X;
    private int Y;
    private int Gcost;
    private int Hcost;
    private int Fcost;
    private int Value;
    private Cell Father;
    private boolean Way;

    public Cell()
    {
        this.X = -1;
        this.Y = -1;
    }
    
    public Cell(Cell Father, int Gcost, int Fcost, int Hcost, boolean IsAWay, int Value, int X, int Y)
    {
        this.Gcost = Gcost;
        this.Hcost = Hcost;
        this.Fcost = Fcost;
        this.Value = Value;
        this.Father = Father;
        this.Way = IsAWay;
        this.X = X;
        this.Y = Y;
    }

    public int getY()
    {
        return Y;
    }

    public void setY(int Y)
    {
        this.Y = Y;
    }

    public int getGcost()
    {
        return Gcost;
    }

    public void setGcost(int Gcost)
    {
        this.Gcost = Gcost;
    }

    public int getHcost()
    {
        return Hcost;
    }

    public void setHcost(int Hcost)
    {
        this.Hcost = Hcost;
    }

    public int getFcost()
    {
        return Fcost;
    }

    public void setFcost(int Fcost)
    {
        this.Fcost = Fcost;
    }

    public int getValue()
    {
        return Value;
    }

    public void setValue(int Value)
    {
        this.Value = Value;
    }

    public Cell getFather()
    {
        return this.Father;
    }

    public void setFather(Cell Father)
    {
        this.Father = Father;
    }

    public boolean isWay()
    {
        return Way;
    }

    public void setWay(boolean Way)
    {
        this.Way = Way;
    }

    public int getX()
    {
        return X;
    }

    public void setX(int X)
    {
        this.X = X;
    }
    
    @Override
    public int compareTo(Object o)
    {
        int FCostToCompare =((Cell)o).getFcost();
        return this.Fcost - FCostToCompare;
    }
    
    public boolean Equals(Cell o)
    {
        return (this.X == o.getX()) && (this.Y == o.getY());
    }
    
    public boolean IsEmpty()
    {
        return ((this.X == -1) && (this.Y == -1));
    }
}