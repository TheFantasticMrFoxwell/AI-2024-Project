package multi.robot.exploration;

public class Station 
{
    String name = "base";
    Cell position = Environment.grid[Environment.grid.length/2][Environment.grid.length/2];
    int radius;
    
    public Station(int range)
    {
        this.radius = range;
    }
}