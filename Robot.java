package multi.robot.exploration;

import java.util.Random;
import java.util.ArrayList;

public class Robot 
{
    public enum Direction {N, NE, E, SE, S, SW, W, NW, R};
    
    String name;
    int communication_distance;
    int direction;
    Cell position;
    int potential_move;
    Cell potential_next;
    ArrayList<Cell> neighbors = new ArrayList();
    
    public Robot(Cell start, String name, int range)
    {
        this.position = start;
        this.potential_next = position;
        this.name = name;
        this.communication_distance = range;
    }
    
    public void setPotentialNext(int direction)
    {
        switch(direction)
        {
            case 0:
                if (position.y_coord > 0)
                    potential_next = Environment.grid[position.y_coord-1][position.x_coord];
                break;
            case 1:
                if (position.y_coord > 0 && position.x_coord < Environment.grid[position.y_coord].length-1)
                    potential_next = Environment.grid[position.y_coord-1][position.x_coord+1];
                break;
            case 2:
                if (position.x_coord < Environment.grid[position.y_coord].length-1)
                    potential_next = Environment.grid[position.y_coord][position.x_coord+1];
                break;
            case 3:
                if (position.y_coord < Environment.grid.length-1 && position.x_coord < Environment.grid[position.y_coord].length-1)
                    potential_next = Environment.grid[position.y_coord+1][position.x_coord+1];
                break;
            case 4:
                if (position.y_coord < Environment.grid.length-1)
                    potential_next = Environment.grid[position.y_coord+1][position.x_coord];
                break;
            case 5:
                if (position.y_coord < Environment.grid.length-1 && position.x_coord > 0)
                    potential_next = Environment.grid[position.y_coord+1][position.x_coord-1];
                break;
            case 6:
                if (position.x_coord > 0)
                    potential_next = Environment.grid[position.y_coord][position.x_coord-1];
                break;
            case 7:
                if (position.y_coord > 0 && position.x_coord > 0)
                    potential_next = Environment.grid[position.y_coord-1][position.x_coord-1];
                break;
            case 8:
                potential_next = position;
                break;
        }
    } // end of setPotentialNext
    
    public void moveRobot(int direction, Environment environment)
    {
        switch(direction)
        {
            case 0:
                if (position.y_coord > 0 && Environment.grid[position.y_coord-1][position.x_coord].occupied == false &&
                        Environment.grid[position.y_coord-1][position.x_coord].obstacle == false && Environment.grid[position.y_coord-1][position.x_coord].base == false)
                {
                    position.explored = true;
                    position.frontier = false;
                    position.occupied = false;
                    position = Environment.grid[position.y_coord-1][position.x_coord];
                    position.occupied = true;
                    position.frontier = false;
                }
                break;
            case 1:
                if (position.y_coord > 0 && position.x_coord < Environment.grid[position.y_coord].length-1 && Environment.grid[position.y_coord-1][position.x_coord+1].obstacle == false
                        && Environment.grid[position.y_coord-1][position.x_coord+1].occupied == false && Environment.grid[position.y_coord-1][position.x_coord+1].base == false)
                {
                    position.explored = true;
                    position.frontier = false;
                    position.occupied = false;
                    position = Environment.grid[position.y_coord-1][position.x_coord+1];
                    position.occupied = true;
                    position.frontier = false;
                }
                break;
            case 2:
                if (position.x_coord < Environment.grid[position.y_coord].length-1 && Environment.grid[position.y_coord][position.x_coord+1].obstacle == false
                        && Environment.grid[position.y_coord][position.x_coord+1].occupied == false && Environment.grid[position.y_coord][position.x_coord+1].base == false)
                {
                    position.explored = true;
                    position.frontier = false;
                    position.occupied = false;
                    position = Environment.grid[position.y_coord][position.x_coord+1];
                    position.occupied = true;
                    position.frontier = false;
                }
                break;
            case 3:
                if (position.y_coord < Environment.grid.length-1 && position.x_coord < Environment.grid[position.y_coord].length-1
                        && Environment.grid[position.y_coord+1][position.x_coord+1].obstacle == false && Environment.grid[position.y_coord+1][position.x_coord+1].occupied == false
                        && Environment.grid[position.y_coord+1][position.x_coord+1].base == false)
                {
                    position.explored = true;
                    position.frontier = false;
                    position.occupied = false;
                    position = Environment.grid[position.y_coord+1][position.x_coord+1];
                    position.occupied = true;
                    position.frontier = false;
                }
                break;
            case 4:
                if (position.y_coord < Environment.grid.length-1 && Environment.grid[position.y_coord+1][position.x_coord].obstacle == false
                        && Environment.grid[position.y_coord+1][position.x_coord].occupied == false && Environment.grid[position.y_coord+1][position.x_coord].base == false)
                {
                    position.explored = true;
                    position.frontier = false;
                    position.occupied = false;
                    position = Environment.grid[position.y_coord+1][position.x_coord];
                    position.occupied = true;
                    position.frontier = false;
                }
                break;
            case 5:
                if (position.y_coord < Environment.grid.length-1 && position.x_coord > 0 && Environment.grid[position.y_coord+1][position.x_coord-1].obstacle == false
                        && Environment.grid[position.y_coord+1][position.x_coord-1].occupied == false && Environment.grid[position.y_coord+1][position.x_coord-1].base == false)
                {
                    position.explored = true;
                    position.frontier = false;
                    position.occupied = false;
                    position = Environment.grid[position.y_coord+1][position.x_coord-1];
                    position.occupied = true;
                    position.frontier = false;
                }
                break;
            case 6:
                if (position.x_coord > 0 && Environment.grid[position.y_coord][position.x_coord-1].obstacle == false && Environment.grid[position.y_coord][position.x_coord-1].occupied == false
                        && Environment.grid[position.y_coord][position.x_coord-1].base == false)
                {
                    position.explored = true;
                    position.frontier = false;
                    position.occupied = false;
                    position = Environment.grid[position.y_coord][position.x_coord-1];
                    position.occupied = true;
                    position.frontier = false;
                }
                break;
            case 7:
                if (position.y_coord > 0 && position.x_coord > 0 && Environment.grid[position.y_coord-1][position.x_coord-1].obstacle == false && 
                        Environment.grid[position.y_coord-1][position.x_coord-1].occupied == false && Environment.grid[position.y_coord-1][position.x_coord-1].base == false)
                {
                    position.explored = true;
                    position.frontier = false;
                    position.occupied = false;
                    position = Environment.grid[position.y_coord-1][position.x_coord-1];
                    position.occupied = true;
                    position.frontier = false;
                }
                break;
            case 8:
                position.explored = true;
                position.frontier = false;
                break;
        }
        environment.revalidate();
        environment.repaint();
    } // end of moveRobot

    public void getNeighbors(Environment environment)
    {
    // add top row of neighbors (north of current position)
        if (position.y_coord > 0)
        {
            // middle
            neighbors.add(environment.grid[position.y_coord-1][position.x_coord]);
            // left
            if (position.x_coord > 0)
            {
                neighbors.add(environment.grid[position.y_coord-1][position.x_coord-1]);
            }
            // right
            if (position.x_coord < environment.grid[position.y_coord].length-1)
            {
                neighbors.add(environment.grid[position.y_coord-1][position.x_coord+1]);
            }
        }
    // add neighbors to the left and right of current position
        // left
        if (position.x_coord > 0)
        {
           neighbors.add(environment.grid[position.y_coord][position.x_coord-1]); 
        }
        // right
        if (position.x_coord < environment.grid[position.y_coord].length-1)
        {
            neighbors.add(environment.grid[position.y_coord][position.x_coord+1]);
        }
    // add bottom row of neighbors (south of current position)
        if (position.y_coord < environment.grid.length-1)
        {
            // middle
            neighbors.add(environment.grid[position.y_coord+1][position.x_coord]);
            // left
            if (position.x_coord > 0)
            {
                neighbors.add(environment.grid[position.y_coord+1][position.x_coord-1]);
            }
            // right
            if (position.x_coord < environment.grid[position.y_coord].length-1)
            {
                neighbors.add(environment.grid[position.y_coord+1][position.x_coord+1]);
            }
        }
    } // end of getNeighbors
    
    void moveToBase(Robot[] robots, Station base, Environment environment)
    {
        Configuration escape = new Configuration(robots, base);
        Cell closest_cell = this.position;
        int current_distance = escape.calculateDistance(this.position, base.position);
        int shortest_distance = Integer.MAX_VALUE;
        int direction = 8;
        int x_diff;
        int y_diff;
        
        if (current_distance < 10)
        {
            direction = 3;
            this.moveRobot(direction, environment);
            return;
        }
        
        this.getNeighbors(environment);
        for (Cell neighbor : this.neighbors)
        {
            int temp = escape.calculateDistance(base.position, neighbor);
            
            if (neighbor.obstacle == false && neighbor.occupied == false && neighbor.base == false
                    && temp < shortest_distance)
            {
                closest_cell = neighbor;
                shortest_distance = temp;
            }
        }

        // go north
        if (closest_cell.x_coord == this.position.x_coord && closest_cell.y_coord < this.position.y_coord)
            direction = 0;
        // go northeast
        else if (closest_cell.x_coord > this.position.x_coord && closest_cell.y_coord < this.position.y_coord)
            direction = 1;
        // go east
        else if (closest_cell.x_coord > this.position.x_coord && closest_cell.y_coord == this.position.y_coord)
            direction = 2;
        // go southeast
        else if (closest_cell.x_coord > this.position.x_coord && closest_cell.y_coord > this.position.y_coord)
            direction = 3;
        // go south
        else if (closest_cell.x_coord == this.position.x_coord && closest_cell.y_coord > this.position.y_coord)
            direction = 4;
        // go southwest
        else if (closest_cell.x_coord < this.position.x_coord && closest_cell.y_coord > this.position.y_coord)
            direction = 5;
        // go west
        else if (closest_cell.x_coord < this.position.x_coord && closest_cell.y_coord == this.position.y_coord)
            direction = 6;
        // go northwest
        else if (closest_cell.x_coord < this.position.x_coord && closest_cell.y_coord < this.position.y_coord)
            direction = 7;
        
        this.moveRobot(direction, environment);
    } // end of moveToBase
}