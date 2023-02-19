/**
 * TextDialog.java
 * @version 1.0
 * @author Edward, Christopher, Kyle, Andrew
 * June 2022
 * A dialog box that prompts the user to select properties for text creation
 */

import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.border.TitledBorder;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TextDialog extends JDialog implements ActionListener {
    private int result;
    private String inputtedText;
    private Font inputtedFont;

    private JTextField text;
    private JComboBox sizes;
    private JComboBox fonts;

    private JButton ok;
    private JButton cancel;

    /**
     * Constructs a TextDialog for a specified BoardFrame
     * @param frame The BoardFrame this TextDialog is to be constructed for
     */
    TextDialog(BoardFrame frame) {
        super(frame, "Create Text", true); //True makes the JDialog modal

        //Create text input fields
        this.text = new JTextField("Example");
        text.setColumns(27);
        text.setFont(new Font("Arial", Font.PLAIN, 15));

        String[]fontsList = {"Arial", "TimesRoman", "Calibri", "Roboto"};
        this.fonts = new JComboBox(fontsList);
        String[]sizeLists = {"8", "9", "10", "11", "12", "14", "18", "24", "30", "36", "48"};
        this.sizes = new JComboBox(sizeLists);

        this.ok = new JButton("Ok");
        ok.addActionListener(this);
        this.cancel = new JButton("Cancel");
        cancel.addActionListener(this);

        //Create inputted text section
        JPanel p = new JPanel();
        p.add(text);
        p.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "Text"));
        //Create font family section
        JPanel p1 = new JPanel();
        p1.add(fonts);
        p1.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "Font Family"));
        //Create font size section
        JPanel p2 = new JPanel();
        p2.add(sizes);
        p2.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "Font Size"));
        //Create ok and cancel button section
        JPanel p3 = new JPanel();
        p3.add(ok);
        p3.add(cancel);

        //Combine all panels
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(p);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(p1);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(p2);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(p3);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        this.setContentPane(panel);
        this.pack();
        this.setResizable(false);
        this.setSize(new Dimension(500, 400));
        this.setVisible(false);
    }

    /**
     * Makes this TextDialog visible and returns the result of the user's response (Success or failure)
     * @return An integer, Const.SUCCESS for a success response or Const.FAILURE for a failed response
     */
    public int showTextDialog() {
        this.setVisible(true);
        return result; //Return result of this response, either Const.SUCCESS or Const.FAILURE
    }

    /**
     * Gets the text that was inputted by the user
     * @return A string of the text that was inputted by the user
     */
    public String getInputtedText() {
        return inputtedText;
    }

    /**
     * Gets the font that was inputted by the user
     * @return The inputted font by the user
     */
    public Font getInputtedFont() {
        return inputtedFont;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(ok)) { //Ok button
            result = Const.SUCCESS;
            inputtedText = text.getText();
            //Convert number from string to int
            int inputtedSize = Integer.parseInt((String)sizes.getSelectedItem());
            //Create new font
            inputtedFont = new Font((String)fonts.getSelectedItem(), Font.PLAIN, inputtedSize);
            this.setVisible(false); //Once submitted, make text dialog invisible
        } else if(e.getSource().equals(cancel)) { //Cancel button
            result = Const.FAILURE;
            this.setVisible(false); //Once submitted, make text dialog invisible
        }
    }
}
