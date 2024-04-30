package multi.robot.exploration;

import java.awt.Graphics;
import java.awt.Polygon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.util.Random;

public class Environment extends JPanel
{
    public static Cell[][] grid;
    int width;
    int height;
    gridEnviron room = new gridEnviron();
    JFrame frame = new JFrame("Robots Exploring An Environment");
    
    public Environment(int width, int height, int obstacles)
    {
        this.width = width;
        this.height = height;
        this.grid = new Cell[height/10][width/10];
        int row_walker = 0;
        int col_walker = 0;
        Random randomizer = new Random();
        
        for (int row = 0; row < grid.length; row++)
        {
            col_walker = 0;
            for (int col = 0; col < grid[row].length; col++)
            {
                grid[row][col] = new Cell(col_walker, row_walker, col, row);
                if ((row == grid.length/2 - 1 && col == grid[0].length/2 - 1) || (row == grid.length/2 - 1 && col == grid[0].length/2 + 1) ||
                        (row == grid.length/2 + 1 && col == grid[0].length/2 - 1) || (row == grid.length/2 + 1 && col == grid[0].length/2 + 1))
                {
                    grid[row][col].occupied = true;
                }
                else if (row == grid.length/2 && col == grid[0].length/2)
                {
                    grid[row][col].base = true;
                }
                else
                {
                    // generate obstacles
                    if (obstacles != 0)
                    {
                        int random_number = 0;
                        if (obstacles == 5)
                        {
                            random_number = randomizer.nextInt(20);
                        }
                        else if (obstacles == 20)
                        {
                            random_number = randomizer.nextInt(5);
                        }
                    
                        if (random_number == 1)
                        {
                            grid[row][col].obstacle = true;
                        }
                    }
                }
                col_walker += 5;
            }
            row_walker += 5;
        }
        
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //gridEnviron room = new gridEnviron();
        frame.add(room);
        frame.pack();
        frame.setSize(width/2, height/2+38);
        frame.validate();
        frame.setVisible(true);
    }
    
    
} // end Environment class

class gridEnviron extends JPanel
{
    @Override public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        for (int i = 0; i < Environment.grid.length; i++)
        {
            for (int j = 0; j < Environment.grid[i].length; j++)
            {
                if (Environment.grid[i][j].obstacle)
                {
                    g.setColor(Color.black);
                }
                else if (Environment.grid[i][j].frontier)
                {
                    g.setColor(Color.yellow);
                }
                else if (Environment.grid[i][j].occupied)
                {
                    g.setColor(Color.red);
                }
                else if (Environment.grid[i][j].base)
                {
                    g.setColor(Color.blue);
                }
                else if (Environment.grid[i][j].explored)
                {
                    g.setColor(Color.green);
                }
                else
                {
                    g.setColor(Color.white);
                }
                
                g.fillPolygon(Environment.grid[i][j].square);
            }
        }
    }
}
