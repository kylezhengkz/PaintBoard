/** 
 * Client.java
 * @version 1.0
 * @author Edward, Christopher, Kyle, Andrew
 * June 2022
 * Client that handles sending and receiving information from the Server
 */

import java.util.LinkedHashSet;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    private BoardPanel boardPanel;
    private ServerChat serverChat;
    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private ConnectionHandler connectionHandler;
    private boolean closed;
    private final int PORT = 5000;
    
    /**
     * Constructs and initializes the client
     * @param serverIP The IP of the server
     * @param boardPanel The BoardPanel that the client is connected to
     */
    Client(String serverIP, BoardPanel boardPanel) throws Exception {
        this.boardPanel = boardPanel;
        this.socket = new Socket(serverIP, PORT);
    }
    
    /**
     * Starts the client
     */
    public void start() throws Exception {
        //Set up input and output streams
        this.input = new ObjectInputStream(socket.getInputStream());
        this.output = new ObjectOutputStream(socket.getOutputStream());

        //Create and start connection handler thread
        this.connectionHandler = new ConnectionHandler();
        connectionHandler.start();
        this.closed = false;

        System.out.println("Socket started");
    }
    
    /**
     * Check if client is closed
     * @return the status of client being closed or not
     */
    public boolean getClosed() {
        return closed;
    }
    
    /**
     * Adds a reference to the server chat
     * @param serverChat The server chat connected to the BoardPanel
     */
    public void addChatReference(ServerChat serverChat) {
        this.serverChat = serverChat;
    }
    
    /**
     * Stops the client
     */
    public void quit() throws Exception {
        connectionHandler.quit();
        connectionHandler.interrupt();

        input.close();
        output.close();
        
        socket.close();
        System.out.println("Closed client");
        closed = true;
    }
    
    /**
     * Adds element to Server
     * @param element The object to be sent
     */
    public void addElement(Object element) {
        try {
            output.writeInt(Const.ADD_ELEMENT);
            output.writeObject(element);
            output.flush();
        } catch (Exception e) {}
    }
    
    /**
     * Removes element from Server
     * @param element The object to be removed
     */
    public void removeElement(Object element) {
        try{
            output.writeInt(Const.REMOVE_ELEMENT);
            output.writeObject(element);
            output.flush();
        } catch (Exception e) {}
    }
    
    /**
     * Tells the server to clear the boards of all clients
     */
    public void clear() {
        try{
            output.writeInt(Const.CLEAR);
            output.flush();
        } catch (Exception e) {}
    }
    
    /**
     * Requests the elements from the server
     */
    public void requestElements() {
        try{
            output.writeInt(Const.GET_ELEMENTS);
            output.flush();
        } catch (Exception e) {}
    }
    
    /**
     * Sends message to other clients
     * @param message The message to be sent
     */
    public void sendMessage(String message) {
        try{
            output.writeInt(Const.SEND_MESSAGE);
            output.writeObject(message);
            output.flush();
        } catch (Exception e) {}
    }
    
    class ConnectionHandler extends Thread {
        private boolean running = true;
        
        /**
         * Stops the connection handler from running
         */
        public void quit() throws Exception {
            running = false;
            closed = true;
        }
        
        /**
         * Runs the connection handler thread
         */
        public void run() {
            while (running) {
                try {
                    int command = input.readInt();
                    if (command==Const.ADD_ELEMENT) {
                        Object element = input.readObject();
                        boardPanel.addElement(element);
                    } else if(command==Const.REMOVE_ELEMENT) {
                        Object element = input.readObject();
                        boardPanel.removeElement(element);
                    } else if (command==Const.CLEAR) {
                        boardPanel.clear();
                    } else if (command==Const.SEND_ELEMENTS) {
                        LinkedHashSet<Object> elements = (LinkedHashSet<Object>)input.readObject();
                        boardPanel.syncBoard(elements);
                    } else if (command==Const.SEND_MESSAGE) {
                        String message = (String)input.readObject();
                        serverChat.sendMessage(message);
                    }
                } catch(Exception e) {
                    System.out.println("Server Disconnected");
                    running = false;
                    closed = true;
                }
            }
        }
    }
}
