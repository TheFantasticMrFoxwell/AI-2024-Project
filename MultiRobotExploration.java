package multi.robot.exploration;

import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.Random;
import java.util.ArrayList;
import javax.swing.SwingUtilities;

public class MultiRobotExploration 
{
    public static void main(String[] args) 
    {
        int range = 0;
        int obstacles = 0;
        int time_limit = 10000;
for (int demo = 0; demo < 5; demo++)
    {
        switch(demo)
        {
            case 0:
                range = 5000;
                obstacles = 0;
                break;
            case 1:
                range = 5000;
                obstacles = 20;
                break;
            case 2:
                range = 25;
                obstacles = 5;
                break;
            case 3:
                range = 25;
                obstacles = 20;
                break;
            case 4:
                range = 25;
                obstacles = 20;
                time_limit = 100000;
                break;
        }
    // deadlock detection variables
        Integer deadlock_detector = 0;
        Integer stuck_detector = 0;
    // default configuration
        ArrayList<Integer> do_nothing = new ArrayList();
    // initialize an environment
        Environment environment = new Environment(1280, 720, obstacles);
    // setup time limit
        //int time_limit = 10000;
        int time_step = 0;
    // create random number generator
        Random randomizer = new Random();
    // generate list of robots and base station
        Station base = new Station(range);
        Robot[] robots = new Robot[4];
        robots[0] = new Robot(environment.grid[environment.grid.length/2-1][environment.grid[0].length/2-1], "1", range);
        robots[1] = new Robot(environment.grid[environment.grid.length/2-1][environment.grid[0].length/2+1], "2", range);
        robots[2] = new Robot(environment.grid[environment.grid.length/2+1][environment.grid[0].length/2-1], "3", range);
        robots[3] = new Robot(environment.grid[environment.grid.length/2+1][environment.grid[0].length/2+1], "4", range);
        for (Robot robot : robots)
        {
            do_nothing.add(8);
        }
        Configuration nothing = new Configuration(robots, base);
    // initialize configuration array -- each index in a config corresponds to a robot
        Configuration[] potential_configs = new Configuration[50];
    // initialize frontier list
        ArrayList<Cell> frontier = new ArrayList<>();
        updateFrontier(frontier, robots, environment);
        
        try{
                Thread.sleep(1000);
            }
            catch(InterruptedException e){
                ;
            }
    // run until time is up
        while ((time_step < time_limit) && !frontier.isEmpty())
        {
            int max_fitness = Integer.MIN_VALUE;
            Configuration best_configuration = nothing;
            best_configuration.random_directions = do_nothing;
            best_configuration.utility = -399;
        // generate a population; the constructor assigns random directions
            for (int i = 0; i < potential_configs.length; i++)
            {
                potential_configs[i] = new Configuration(robots, base);
            // compute fitness; find maximum
                potential_configs[i].findConfigUtility(robots, base, frontier);
                if (potential_configs[i].utility > max_fitness)
                    max_fitness = potential_configs[i].utility;
            }
            
        // select best config
            for (Configuration config : potential_configs)
            {
                if (config.utility == max_fitness && config.out_of_range == false)
                {
                    best_configuration = config;
                }
            }
            
            for (int i = 0; i < robots.length; i++)
            {
                for (int j = i+1; j < robots.length; j++)
                {
                    int distance = best_configuration.calculateDistance(robots[i].position, robots[j].position);              
                }
            }
            
        // move the robots to the positions with the maximum fitness; update frontier
            for (int i = 0; i < robots.length; i++)
            {
                robots[i].moveRobot(best_configuration.random_directions.get(i), environment);
            }
            
            if (!best_configuration.equals(nothing))
            {
                stuck_detector = 0;
            }
            
            if (deadlock_detector > 100 || stuck_detector > 5)
            {
                for (Robot robot : robots)
                {
                    robot.moveToBase(robots, base, environment);
                }
                deadlock_detector = 0;
                stuck_detector = 0;
            }
            
            updateFrontier(frontier, robots, environment);
        // animation code, bookkeeping
            SwingUtilities.updateComponentTreeUI(environment);
            environment.room.revalidate();
            environment.room.setVisible(true);
            environment.room.repaint();
            stuck_detector++;
            deadlock_detector++;
            time_step++;
        } // end of main while loop
    
        float explored_number = 0;
        float total_number = environment.grid.length * environment.grid[0].length;
        for (int i = 0; i < environment.grid.length; i++)
        {
            for (int j = 0; j < environment.grid[0].length; j++)
            {
                if (environment.grid[i][j].explored == true)
                    explored_number++;
                if (environment.grid[i][j].obstacle == true)
                    total_number--;
            }
        }
        float percentage = explored_number / total_number * 100;
        if (frontier.isEmpty())
        {
            System.out.println("Exploration complete.");
        }
        else
        {
            System.out.println("Time up.");
        }
        System.out.printf("Explored cells: %.0f; %.2f percent\n", explored_number, percentage);
        // allow time to see results
        try{
                Thread.sleep(6000);
            }
            catch(InterruptedException e){
                ;
            }
        // remove window
        environment.frame.dispose();
        // allow time between windows
         try{
                Thread.sleep(1000);
            }
            catch(InterruptedException e){
                ;
            }
    }
    } // end of main method
    
    public static void updateFrontier(ArrayList<Cell> frontier, Robot[] robots, Environment environment)
    {
        // remove explored cells
        for (int i = frontier.size()-1; i >= 0; i--)
        {
            if (frontier.get(i).frontier == false)
            {
                frontier.remove(i);
            }
        }
        
        // add neighbor cells
        for (Robot robot : robots)
        {
            robot.getNeighbors(environment);
            for (Cell neighbor : robot.neighbors)
            {
                if (neighbor.occupied == false && neighbor.explored == false &&
                        neighbor.obstacle == false && neighbor.base == false && 
                        neighbor.frontier == false)
                {
                    neighbor.frontier = true;
                    frontier.add(neighbor);
                }
            }
            robot.neighbors.clear();
        }
        environment.revalidate();
        environment.repaint();
    }
} // end of class MultiRobotExploration