/** 
 * ServerChat.Java
 * @version 1.0
 * @author Kyle, Christopher, Edward, Andrew
 * June 2022
 * Frame that contains the server chat
 */

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;

public class ServerChat extends JFrame implements ActionListener {
    private Client client;
    private User user;
    private JTextArea txaDisplay;
    private JScrollPane scrdisplay;
    private JTextField txtInput;
    private JButton button;
    private String message;
    
    /**
     * Creates and initializes a ServerChat
     * @param client The client associated with this ServerChat JFrame
     * @param user The user associated with this Serverchat JFrame
     */
    ServerChat(Client client, User user) {    
        this.client = client;
        this.user = user;

        //Set up server chat frame properties
        this.setTitle("Chat");
        this.setLayout(new FlowLayout());
        setSize(500, 320);
        setVisible(false);
        setResizable(false);
        setLocationRelativeTo(null);
        
        //Create message display area
        txaDisplay = new JTextArea();
        DefaultCaret caret = (DefaultCaret)txaDisplay.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);//Forces chat to scroll down
        txaDisplay.setEditable(false);
        scrdisplay = new JScrollPane(txaDisplay);
        scrdisplay.setPreferredSize(new Dimension(450, 200));
        getContentPane().add(scrdisplay);
        
        //Create message input
        txtInput = new JTextField();
        txtInput.setPreferredSize(new Dimension(450, 30));
        getContentPane().add(txtInput);
        
        //Create send message
        button = new JButton("Send message");
        button.addActionListener(this);
        this.add(button);
    }

    /**
     * Getter method for text message
     * @return The message
     */
    public String getMessage() {
        return message;
    }
    
    /**
     * Displays message onto textfield, and displays it below previous messages
     * @param message The message that the user entered
     */
    public void sendMessage(String message) {
        txaDisplay.append(message+"\n");
    }
    
    /*----- Overriden methods from ActionListener -----*/
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(button)) {
            message = txtInput.getText();
            txaDisplay.append(user.getName()+": "+message+"\n");
            try {
                client.sendMessage(user.getName()+": "+message);
            } catch (Exception ex) {}
        } 
    }
    
}
