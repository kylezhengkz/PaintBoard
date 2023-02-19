/**
 * Text.java
 * @version 1.0
 * @author Edward, Christopher, Kyle
 * June 2022
 * A class that represents a Text on the PaintBoard screen.
 */

import java.awt.Font;
import java.io.Serializable;
import java.awt.Color;

public class Text implements Serializable {
    private int x;
    private int y;
    private String textString;
    private Font font;
    private Color color;

    /**
     * Constructs a Text with a specified x and y position, content, font, and color
     * @param x The x-position of this Text
     * @param y The y-position of this text
     * @param textString The content of this Text
     * @param font The font of this Text
     * @param color The color of this Text
     */
    Text(int x, int y, String textString, Font font, Color color) {
        this.x = x;
        this.y = y;
        this.textString = textString;
        this.font = font;
        this.color = color;
    }

    /**
     * Gets the x-position of this Text
     * @return An integer of the x-position of this Text
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y-position of this Text
     * @return An integer of the y-position of this Text
     */
    public int getY() {
        return y;
    }

    /**
     * Gets the content of this Text
     * @return A String representing the content of this Text
     */
    public String getTextString() {
        return textString;
    }

    /**
     * Gets the font of this Text
     * @return A font of this Text
     */
    public Font getFont() {
        return font;
    }
    
    /**
     * Gets the color of this Text
     * @return A color of this Text
     */
    public Color getColor() {
        return color;
    }
}
