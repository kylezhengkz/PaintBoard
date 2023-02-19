/** 
 * Server.Java
 * @version 1.0
 * @author Edward, Christopher, Kyle, Andrew
 * June 2022
 * Manages server functions
 */

import java.util.ArrayList;
import java.util.LinkedHashSet;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
 
public class Server {
    private String ip;
    private ArrayList<ConnectionHandler>connections;
    private ServerSocket serverSocket;
    private ServerThread serverThread;
    private boolean running = true;
    private LinkedHashSet<Object> elements = new LinkedHashSet<Object>();
    final int PORT = 5000;
    
    /**
     * Constructs a server for the computer currently running on port 5000 and its current network.
     */
    Server() throws Exception {
        //Get server IP address
        String localHost = InetAddress.getLocalHost().toString();
        ip = localHost.substring(localHost.indexOf('/')+1);
        System.out.println(ip);

        this.serverSocket = new ServerSocket(PORT);
        this.connections = new ArrayList<ConnectionHandler>();

        //Create and start server thread
        this.serverThread = new ServerThread();
        serverThread.start();
    }
    
    /**
     * Gets the server's IP address.
     * @return A String of the server's IP address
     */
    public String getServerIP() {
        return ip;
    }
    
    /**
     * Quits this server and closes all threads, sockets, and input streams associated with this server.
     */
    public void quit() throws Exception {
        System.out.println("Server quit");
        //Stop server thread
        running = false;
        serverThread.interrupt();
        //Stop all connection handler threads
        for (ConnectionHandler connectionHandler: connections) {
            connectionHandler.quit();
            connectionHandler.interrupt();
        }
        serverSocket.close();
    }
    
    class ServerThread extends Thread {
    	/**
    	 * Runs ServerThread
    	 */
        public void run() {
            while (running) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("New Connection");

                    //Create new connection handler thread for new client
                    ConnectionHandler t = new ConnectionHandler(clientSocket);
                    Thread connectionThread = new Thread(t);
                    connections.add(t);
                    connectionThread.start();
                } catch(Exception e) {
                    running = false;
                }
            }
        }
    }

    class ConnectionHandler extends Thread{
        private Socket socket;
        private ObjectInputStream input;
        private ObjectOutputStream output;
        private boolean running = true;

        /**
         * Constructs a ConnectionHandler thread to handle the input and output functions for a specified socket.
         * @param socket The socket that this ConnectionHandler is to be constructed for
         */
        ConnectionHandler(Socket socket) throws Exception {
            this.socket = socket;
            this.output = new ObjectOutputStream(socket.getOutputStream());
            this.input = new ObjectInputStream(socket.getInputStream());
        }
        
        /**
         * Returns whehter this ConnectionHandler thread is currently running or not
         * @return A boolean, whether this thread is running or not
         */
        public boolean getRunning() {
            return running;
        }
        
        /**
         * Quits this ConnectionHandler thread and closes the socket and all input and output streams.
         */
        public void quit() throws Exception {
            System.out.println("intiated server connection quit");
            running = false;
            input.close();
            output.close();
            socket.close();
        }
        
        /**
         * Runs the connectionhandler thread
         */
        public void run() {
            while (running) {
                try {
                    int command = input.readInt();
                    if (command==Const.ADD_ELEMENT) { //Add element command
                        Object element = input.readObject();
                        elements.add(element);
                        //Update in all other clients
                        for (ConnectionHandler i: connections) {
                            if (i!=this && i.getRunning()) {
                                i.addElement(element);
                            }
                        }
                    } else if (command==Const.REMOVE_ELEMENT) { //Remove element command
                        Object element = input.readObject();
                        elements.remove(element);
                        //Update in all other clients
                        for (ConnectionHandler i: connections) {
                            if (i!=this && i.getRunning()) {
                                i.removeElement(element);
                            }
                        }
                    } else if (command==Const.CLEAR) { //Clear command
                        elements.clear();
                        //Update in all other clients
                        for (ConnectionHandler i: connections) {
                            if (i!=this && i.getRunning()) {
                                i.clear();
                            }
                        }
                    } else if(command==Const.GET_ELEMENTS) { //Get elements command
                        //Update in all other clients
                        for(ConnectionHandler i: connections) {
                            if (i==this&&i.getRunning()) {
                                i.sendElements();
                            }
                        }
                    } else if(command==Const.SEND_MESSAGE) { //Send message command
                        String message = (String)input.readObject();
                        //Update in all other clients
                        for (ConnectionHandler i: connections) {
                            if (i!=this && i.getRunning()) {
                                i.sendMessage(message);
                            }
                        }
                    }
                } catch(Exception e) {
                    System.out.println("Error inputing from socket. Socket thread stopped");
                    running = false;
                }
            }
        }
        
        /**
         * Sends all PaintBoard elements(Text, Stroke, Image) stored in this Server to a client
         */
        public void sendElements() {
            try {
                output.writeInt(Const.SEND_ELEMENTS);
                output.writeObject(elements);
                output.flush();
            } catch(Exception e) {}
        }
        
        /**
         * Adds a PaintBoard element to clients connected to this server
         * @param element The new PaintBoard element that is to be added.
         */
        public void addElement(Object element){
            try {
                output.writeInt(Const.ADD_ELEMENT);
                output.writeObject(element);
                output.flush();
            } catch(Exception exp) {}
        }
        
        /**
         * Removes a PaintBoard element to clients connected to this server
         * @param element The PaintBoard element that is to be removed from this Server
         */
        public void removeElement(Object element){
            try {
                output.writeInt(Const.REMOVE_ELEMENT);
                output.writeObject(element);
                output.flush();
            } catch(Exception exp) {}
        }
        
        /**
         * Sends a clear command to all Clients connected to this Server
         */
        public void clear() {
            try {
                output.writeInt(Const.CLEAR);
                output.flush();
            } catch(Exception exp) {}
        }
        
        /**
         * Sends a specified message to all Clients connected to this Server
         * @param message A String containing the message that is to be sent
         */
        public void sendMessage(String message) {
            try {
                output.writeInt(Const.SEND_MESSAGE);
                output.writeObject(message);
                output.flush();
            } catch(Exception exp) {}
        }
    }
    
}
