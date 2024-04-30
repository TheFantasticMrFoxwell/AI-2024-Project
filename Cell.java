package multi.robot.exploration;

import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridLayout;

public class Cell 
{
    boolean obstacle = false;
    boolean frontier = false;
    boolean explored = false;
    boolean occupied = false;
    boolean base = false;
    Polygon square = new Polygon();
    int x_coord;
    int y_coord;
    int[] squareXValues;
    int[] squareYValues;
    
    public Cell(int startx, int starty, int x, int y)
    {
        this.x_coord = x;
        this.y_coord = y;
        square.addPoint(startx, starty);
        square.addPoint(startx, starty+5);
        square.addPoint(startx+5, starty+5);
        square.addPoint(startx+5, starty);
    }
}
