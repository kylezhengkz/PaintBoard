/**
 * Stroke.java
 * @version 1.0
 * @author Edward, Christopher, Kyle
 * June 2022
 * A class that represents one single pressed stroke on the PaintBoard
 */

import java.util.ArrayList;
import java.io.Serializable;
import java.awt.Color;

public class Stroke implements Serializable {
    private ArrayList<int[]>lines;
    private int thickness;
    private Color color;
    
    /**
     * Constructs a Stroke with a specified thickness
     * @param thickness An integer of the thickness of this Stroke
     */
    Stroke(int thickness) {
        this.lines = new ArrayList<int[]>();
        this.thickness = thickness;
    }

    /**
     * Adds one line segment to this stroke
     * @param startX The x-position of the starting point of this new line segment
     * @param startY The y-position of the starting point of this new line segment
     * @param endX The x-position of the ending point of this new line segment
     * @param endY The y-position of the ending point of this new line segment
     */
    public void addLine(int startX, int startY, int endX, int endY) {
        int[]x = {startX, startY, endX, endY};
        lines.add(x);
    }

    /**
     * Returns an ArrayList of all the line segments of this Stroke
     * @return An ArrayList contianing all of the line segments of this Stroke
     */
    public ArrayList<int[]> getLines() {
        return lines;
    }

    /**
     * Sets the color of this Stroke to some new specified color
     * @param newColor The new color that is to be set to
     */
    public void setColor(Color newColor) {
        color = newColor;
    }

    /**
     * Gets the current color of this Stroke
     * @return The current color of this Stroke
     */
    public Color getColor() {
        return color;
    }

    /**
     * Gets the thickness of this Stroke
     * @return An integer representing the thickness of this Stroke
     */
    public int getThickness() {
        return thickness;
    }
}
