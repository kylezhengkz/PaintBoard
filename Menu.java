/** 
 * Menu.Java
 * @version 1.0
 * @author Kyle, Andrew
 * June 2022
 * Frame that displays the menu screen
 */

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;


public class Menu extends JFrame implements ActionListener {
    
    //Button status (if pressed or not)
    private static boolean buttonPressed = false;
    private static boolean single = false;
    private static boolean serverCreate = false;
    private static boolean serverJoin = false;
    private static boolean exit = false;
    
    private final int WIDTH = 1500;
    private final int HEIGHT = 850;

    private JLabel title;
    private JButton offline, createServer, joinServer, quit;

    static JFrame frame = new JFrame();
    
    /**
     * Creates and initializes a Menu
     */
    public Menu() {
        //Set up Menu Frame properties
        Container mainP = frame.getContentPane();
        mainP.setLayout(null);
        frame.setSize(WIDTH, HEIGHT);
        frame.setVisible(true);
        frame.setResizable(false);

        title = new JLabel("PaintBoard");

        //Set up Button options
        offline = new JButton("Offline");
        createServer = new JButton("Create Server");
        joinServer = new JButton("Join Server");
        quit = new JButton("Quit");
        
        //Display buttons
        
        mainP.add(title);
        title.setFont(new Font("Arial", Font.BOLD, 50));
        title.setBounds(WIDTH/2 - 111, HEIGHT/2 - 375, 500, 50);

        mainP.add(offline);
        offline.setFont(new Font("Arial", Font.BOLD, 25));
        offline.setBounds(WIDTH/2 - 390, HEIGHT/2 - 285, 800, 150);
        
        mainP.add(createServer);
        createServer.setFont(new Font("Arial", Font.BOLD, 25));
        createServer.setBounds(WIDTH/2 - 390, HEIGHT/2 - 135, 800, 150);

        mainP.add(joinServer);
        joinServer.setFont(new Font("Arial", Font.BOLD, 25));
        joinServer.setBounds(WIDTH/2 - 390, HEIGHT/2 + 15, 800, 150);

        mainP.add(quit);
        quit.setFont(new Font("Arial", Font.BOLD, 25));
        quit.setBounds(WIDTH/2 - 390, HEIGHT/2 + 165, 800, 150);

        offline.addActionListener(this);
        createServer.addActionListener(this);
        joinServer.addActionListener(this);
        quit.addActionListener(this);
    }
    
    /**
     * Getter method for button status
     * @return Status of the whether the button has been pressed or not
     */
    public boolean buttonPressed() {
        return buttonPressed;
    }
    
    /**
     * Getter method for offline button status
     * @return Status of the user clicking offline
     */
    public boolean offline() {
        return single;
    }
    
    /**
     * Getter method for create server button status
     * @return Status of user clicking create server
     */
    public boolean createServer() {
        return serverCreate;
    }
    
    /**
     * Getter method for join server button status
     * @return Status of user clicking join server
     */
    public boolean joinServer() {
        return serverJoin;
    }
    
    /**
     * Getter method for the exit status of menu
     * @return Status of the menu being exited
     */
    public boolean exit() {
        return exit;
    }

    /*----- Overriden methods from ActionListener -----*/
    @Override
    public void actionPerformed(ActionEvent e) {
        String key = e.getActionCommand();
        if (key.equals("Offline")) { 
            buttonPressed = true;
            single = true;
            frame.dispose();
        }
        else if (key.equals("Create Server")) {
            buttonPressed = true;
            serverCreate = true;
            frame.dispose();
        } else if (key.equals("Join Server")) {
            buttonPressed = true;
            serverJoin = true;
            frame.dispose();
        } else if (key.equals("Quit")) {
            buttonPressed = true;
            exit = true;
            frame.dispose();
            System.exit(0);
        }
    }

}
