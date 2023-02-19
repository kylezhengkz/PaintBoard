/** 
 * BoardFrame.java
 * @version 1.0
 * @author Edward, Christopher, Kyle, Andrew
 * June 2022
 * Frame that contains the BoardPanel, menu bar, and tool bar
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class BoardFrame extends JFrame implements ActionListener {
    private BoardPanel boardPanel;
    private String serverIP;
    private ToolBar toolBar;
    private JMenuBar menuBar;
    private JButton serverIPButton;
    private JMenu fileMenu;
    private JMenuItem save;
    private JMenuItem open;
    private JMenuItem exit;
    private final int MAX_X = 1500;
    private final int MAX_Y = 850;
    
    /**
     * Constructs and initializes the BoardFrame
     * @param user The person associated with the BoardPanel
     * @param serverIP The IP address that the user joins and if it's empty that means it's offline
     * @param online Determines if the user wants to be online or offline
     */
    BoardFrame(User user, String serverIP, boolean online) throws Exception {
        this.serverIP = serverIP;
    	this.boardPanel = new BoardPanel(user, serverIP, this, online);

        //Set up Tool Bar
        toolBar = new ToolBar(boardPanel, online);
        boardPanel.addToolBarReference(toolBar);

        //Set up Menu Bar
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        if (online) {
            serverIPButton = new JButton("Server IP");
            serverIPButton.addActionListener(this);
            menuBar.add(Box.createHorizontalGlue());
            menuBar.add(serverIPButton);
        }
        //Set up Menu Bar items
        save = new JMenuItem("Save");
        save.addActionListener(this);
        exit = new JMenuItem("Exit");
        exit.addActionListener(this);
        open = new JMenuItem("Open");
        open.addActionListener(this);
        fileMenu.add(save);
        fileMenu.add(open);
        fileMenu.add(exit);
        this.setJMenuBar(menuBar);
        
        //Board Panel set up
        boardPanel.setBackground(Color.WHITE);
        this.getContentPane().add(BorderLayout.CENTER, boardPanel);
        this.add(BorderLayout.WEST, toolBar);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(MAX_X, MAX_Y);
        this.setTitle(user.getName());
        this.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
    	if (e.getSource().equals(exit)) {
    		try {
				boardPanel.quit();
                System.exit(0);
			} catch (Exception e1) {}
    	} else if (e.getSource().equals(save)) {
    		try {
				boardPanel.saveBoard();
			} catch (Exception e1) {}
    	} else if (e.getSource().equals(open)) {
    		try {
    			boardPanel.openBoard();
    		} catch (Exception e1) {}
    	} else if (e.getSource().equals(serverIPButton)){
            try {
                JOptionPane.showMessageDialog(this, serverIP);
            } catch (Exception e1) {}
        }
    }
    
    /**
     * Destroys the BoardFrame
     */
    public void quit() {
        this.removeAll();
        this.dispose();
    }
    
}
