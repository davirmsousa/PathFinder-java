package pathfinder.Model;

import java.util.ArrayList;

/**
 * @author Davi Rocha
 */
public class Cell implements Comparable
{
    private int Gcost;
    private int Hcost;
    private int Fcost;
    private int Value;
    private ArrayList<Cell> Fathers;
    private boolean Way;
    private int X;
    private int Y;

    public Cell(ArrayList<Cell> Fathers, int Gcost, int Fcost, int Hcost, boolean IsAWay, int Value, int X, int Y)
    {
        this.Gcost = Gcost;
        this.Hcost = Hcost;
        this.Fcost = Fcost;
        this.Value = Value;
        this.Fathers = Fathers;
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

    public ArrayList<Cell> getFathers()
    {
        return Fathers;
    }

    public Cell getFather(int index)
    {
        if(index >= 0 && index < Fathers.size())
        {
            return Fathers.get(index);
        }
        else
        {
            return null;
        }
    }

    public void setFathers(ArrayList<Cell> Fathers)
    {
        this.Fathers = Fathers;
    }

    public void setFather(Cell Father)
    {
        this.Fathers.add(Father);
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
}
