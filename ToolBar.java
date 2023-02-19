/**
 * ToolBar.java
 * @version 1.0
 * @author Edward, Christopher, Kyle, Andrew
 * June 2022
 * ToolBar that holds all the tools avaliable for a user to change the board.
 */
 
import java.awt.Image;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import javax.swing.JPanel;
import javax.swing.JColorChooser;
import javax.swing.ImageIcon;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ToolBar extends JToolBar {
    private BoardPanel boardPanel;
    //Brush button
    private JButton brush;
    private ImageIcon brushIcon = new ImageIcon(getClass().getClassLoader().getResource("icons/brushicon.png"));
    //Eraser button
    private JButton eraser;
    private ImageIcon eraserIcon = new ImageIcon(getClass().getClassLoader().getResource("icons/erasericon.png"));
    //Color button
    private JButton color;
    private ImageIcon colorIcon;
    //Color Picker button
    private JButton colorPicker;
    private ImageIcon colorPickerIcon = new ImageIcon(getClass().getClassLoader().getResource("icons/colorpickericon.png"));
    //Text button
    private JButton text;
    private ImageIcon textIcon = new ImageIcon(getClass().getClassLoader().getResource("icons/texticon.png"));
    //Thickness Slider
    private JSlider thickness;
    private JPanel thicknessPanel;
    private JLabel thicknessLabel;
    //Clear button
    private JButton clear;
    private ImageIcon clearIcon = new ImageIcon(getClass().getClassLoader().getResource("icons/clearicon.jpg"));
    //Undo button
    private JButton undo;
    private ImageIcon undoIcon = new ImageIcon(getClass().getClassLoader().getResource("icons/undoicon.png"));
    //Redo button
    private JButton redo;
    private ImageIcon redoIcon = new ImageIcon(getClass().getClassLoader().getResource("icons/redoicon.png"));
    //Fill button
    private JButton fill;
    private ImageIcon fillIcon = new ImageIcon(getClass().getClassLoader().getResource("icons/fillicon.jpg"));
    //Chat button
    private JButton chat;
    private ImageIcon chatIcon = new ImageIcon(getClass().getClassLoader().getResource("icons/chaticon.png"));
    
    /**
     * Constructs a ToolBar for a specific BoardPanel
     * @param boardPanel The BoardPanel this ToolBar is to be constructed for
     * @param online A boolean that determiens whether the boardPanel is online or not
     */
    ToolBar (BoardPanel boardPanel, boolean online) {
        this.boardPanel = boardPanel;
        //Set up tool menu variables
        if (online) {
            this.setLayout(new GridLayout(11, 0)); //Make each button in tool bar smaller
        } else {
            this.setLayout(new GridLayout(10, 0)); //Make each button in tool bar smaller
        }
        this.setFloatable(false);

        ButtonController buttonController = new ButtonController();

        //Add buttons to tool bar
        //Brush button
        brush = new JButton("Paint");
        brush.setIcon(new ImageIcon(brushIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        brush.addActionListener(buttonController);
        this.add(brush);
        
        //Eraser button
        eraser = new JButton("Eraser");
        eraser.setIcon(new ImageIcon(eraserIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        eraser.addActionListener(buttonController);
        this.add(eraser);

        //Color button
        color = new JButton("Color");
        BufferedImage img = new BufferedImage(50, 50, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setPaint(new Color(0, 0, 0)); //Default color is black
        g2d.fillRect(0, 0, img.getWidth(), img.getHeight());
        colorIcon = new ImageIcon(img);
        color.setIcon(colorIcon);
        color.addActionListener(buttonController);
        this.add(color);
        
        //Color picker button
        colorPicker = new JButton("Color Picker");
        colorPicker.setIcon(new ImageIcon(colorPickerIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        colorPicker.addActionListener(buttonController);
        this.add(colorPicker);
        
        //Text button
        text = new JButton("Text");
        text.setIcon(new ImageIcon(textIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        text.addActionListener(buttonController);
        this.add(text);

        //Thickness slider
        thickness = new JSlider(1, 100, 4);
        thickness.addChangeListener(buttonController);
        thicknessPanel = new JPanel();
        thicknessLabel = new JLabel("Brush Thickness: 4");
        thicknessLabel.setHorizontalAlignment(JLabel.CENTER);
        thicknessPanel.setLayout(new GridLayout(2,0));
        thicknessPanel.add(thicknessLabel);
        thicknessPanel.add(thickness);
        this.add(thicknessPanel);

        //Chat button, only include if it is online
        if (online) {
            chat = new JButton("Chat");
            chat.setIcon(new ImageIcon(chatIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
            chat.addActionListener(buttonController);
            this.add(chat);
        }
        //Clear button
        clear = new JButton("Clear");
        clear.setIcon(new ImageIcon(clearIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        clear.addActionListener(buttonController);
        this.add(clear);
        
        //Fill button
        fill = new JButton("Fill");
        fill.setIcon(new ImageIcon(fillIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        fill.addActionListener(buttonController);
        this.add(fill);
        
        //Undo button
        undo = new JButton("Undo");
        undo.setIcon(new ImageIcon(undoIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        undo.addActionListener(buttonController);
        this.add(undo);

        //Redo button
        redo = new JButton("Redo");
        redo.setIcon(new ImageIcon(redoIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        redo.addActionListener(buttonController);
        this.add(redo);
    }
    
    /**
     * Changes the color icon of this Toolbar to a new color
     * @param newColor The new color that is to be changed to
     */
    public void updateColorIcon(Color newColor) {
        //Create 50 by 50 image
        BufferedImage img = new BufferedImage(50, 50, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = img.createGraphics();
        //Paint image solid with the new color
        g2d.setPaint(newColor);
        g2d.fillRect(0, 0, img.getWidth(), img.getHeight());
        colorIcon = new ImageIcon(img);
        //Set image as the color button's icon
        color.setIcon(colorIcon);
    }
    
    private class ButtonController implements ActionListener, ChangeListener{
        /*----- Overriden methods from ActionListener -----*/
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(brush)){
                boardPanel.switchTool(Const.BRUSH);
            } else if(e.getSource().equals(eraser)){ 
                boardPanel.switchTool(Const.ERASER);
            } else if(e.getSource().equals(color)){ //Color button
                JColorChooser colorChooser = new JColorChooser();
                Color newColor = colorChooser.showDialog(null, "Select a color", Color.BLACK);
                //Check if newColor is null (meaning color dialog box was canceled)
                if (newColor!=null) {
                    boardPanel.setColor(newColor);
                    updateColorIcon(newColor);
                }
            } else if (e.getSource().equals(colorPicker)) {
                boardPanel.switchTool(Const.COLOR_PICKER);
            } else if(e.getSource().equals(text)){
                boardPanel.switchTool(Const.TEXT);
            } else if(e.getSource().equals(chat)) {
                boardPanel.openChat();
            } else if(e.getSource().equals(undo)){
                boardPanel.undo();
            } else if(e.getSource().equals(redo)){
                boardPanel.redo();
            } else if(e.getSource().equals(clear)){
                boardPanel.clear();
                boardPanel.clearServer();
            } else if(e.getSource().equals(fill)){
                boardPanel.switchTool(Const.FILL);
            }
        }

        /*----- Overriden methods from ChangeListener -----*/
        @Override
        public void stateChanged(ChangeEvent e) {
            if (e.getSource().equals(thickness)){
                int newThickness = thickness.getValue();
                boardPanel.setThickness(newThickness);
            }
        }
    }
}
