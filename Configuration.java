package multi.robot.exploration;
import java.util.ArrayList;
import org.graphstream.algorithm.ConnectedComponents;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.DefaultGraph;
import java.util.Random;
import java.lang.Math;

public class Configuration 
{
    ArrayList<Integer> random_directions = new ArrayList();
    ArrayList<Integer> robo_utils = new ArrayList<>();
    int utility = 0;
    Graph graph;
    boolean out_of_range = false;
    
    public Configuration(Robot[] robots, Station base)
    {
        Random randomizer = new Random();
        graph = new DefaultGraph("Connectedness");
        graph.addNode(base.name);
        for (int i = 0; i < 4; i++)
        {
            this.random_directions.add(randomizer.nextInt(9));
            graph.addNode(robots[i].name);
        }  
    }
    
    public void findConfigUtility(Robot[] robots, Station base, ArrayList<Cell> frontier)
    {
        // continue flag
        boolean skip_to_next = false;
        String concat_names;
        
        // set potential next position for each robot using values generated at configuration creation
        for (int i = 0; i < random_directions.size(); i++)
        {
            robots[i].potential_move = random_directions.get(i);
            robots[i].setPotentialNext(robots[i].potential_move);
        }
        
        // check to make sure the robots won't move out of range (use GraphStream and calculateDistance; subtract three times number of bots if they do)
        // base station edges
        for (int i = 0; i < robots.length; i++)
        {
            int distance = calculateDistance(base.position, robots[i].potential_next);
            if (distance < base.radius || distance == 0)
            {
                concat_names = base.name + robots[i].name;
                graph.addEdge(concat_names, base.name, robots[i].name);
            }
            
            for (int j = i+1; j < robots.length; j++)
            {
                distance = calculateDistance(robots[i].potential_next, robots[j].potential_next);
                if (distance < robots[i].communication_distance || distance == 0)
                {
                    concat_names = robots[i].name + robots[j].name;
                    graph.addEdge(concat_names, robots[i].name, robots[j].name);
                }
            }
        }
        
        // if there isn't a set of edges such that each robot and the base station are somehow connected
        ConnectedComponents cc = new ConnectedComponents();
        cc.init(graph);
        if (cc.getConnectedComponentsCount() > 1)
        {
            utility -= robots.length * 100;
            this.out_of_range = true;
            return;
        }
        
        // modify the utility for each robot
        for (int i = 0; i < robots.length; i++)
        {
            // check for obstacles (subtract three if it hits an obstacle)
            if (robots[i].potential_next.obstacle == true || robots[i].potential_next.base == true || robots[i].potential_next.occupied == true)
            {
                this.utility -= 3;
                continue;
            }
            
            // check each position to make sure no collisions between robots will occur (subtract three if so)
            for (int j = i+1; j < robots.length; j++)
            {
                if (robots[i].potential_next.equals(robots[j].potential_next))
                {
                    this.utility -= 3;
                    skip_to_next = true;
                    break;
                }
            }
            if (skip_to_next)
                continue;
            
            // subtract distance to closest frontier node from next position
            int shortest_distance = Integer.MAX_VALUE;
            int current_distance = 0;
            int distance_difference = 0;
            for (Cell cell : frontier)
            {
                int distance = calculateDistance(robots[i].potential_next, cell);
                if (distance < shortest_distance)
                {
                    shortest_distance = distance;
                    current_distance = calculateDistance(robots[i].position, cell);
                }
            }
            distance_difference = current_distance - shortest_distance;
            
            this.utility -= shortest_distance;
        }
    } // end of findConfigUtility
    
    public int calculateDistance(Cell node1, Cell node2)
    {
        return (Math.abs(node1.y_coord - node2.y_coord)) + (Math.abs(node1.x_coord - node2.x_coord));
    } // end of calculateDistance
}