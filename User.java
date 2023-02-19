/** 
 * User.java
 * @version 1.0
 * @author Edward, Christopher, Kyle
 * June 2022
 * A class that represents a PaintBoard user.
 */

public class User {
    private boolean hasServer;
    private boolean online;
    private Server server;
    private BoardFrame boardFrame;
    private String name;
    
    /**
     * Constructs a user that has the capability to host a server.
     * @param hasServer Determines if the user wants to host a server or not
     * @param name The name of this user
     * @param online Determines if the user wants to be online or not
     */
    User(boolean hasServer, String name, boolean online) { //host user
        this.hasServer = hasServer;
        this.online = online;
        this.name = name;
        try {
            if (hasServer) {
                this.server = new Server();
            }
            this.boardFrame = new BoardFrame(this, server.getServerIP(), online);
        } catch(Exception e) {
            System.out.println("Failed to create a user");
        }
    }
    
    /**
     * Constructs a user for client usage.
     * @param serverIP The IP address that the user joins
     * @param name The name of this user
     * @param online Determines if the user wants to be online or not
     */
    User(String serverIP, String name, boolean online) { //client user
        this.name = name;
        try {
            this.boardFrame = new BoardFrame(this, serverIP, online);
        } catch(Exception e) {
            System.out.println("Failed to create a user");
        }
    }
    
    /**
     * Constructs a user for offline suage.
     */
    User() { //offline user
        try {
            this.boardFrame = new BoardFrame(this, "", online);
        } catch(Exception e) {
            System.out.println("Failed to create a user");
        }
    }
    
    /**
     * Exits this user and closes any servers associated with this user
     */
    public void quit() throws Exception{
        if (hasServer) {
            server.quit();
        }
    }
    
    /**
     * Returns a boolean of whether this user is hosting a server or not
     * @return if this user is hosting a server
     */
    public boolean isHost() {
        return hasServer;
    }
    
    /**
     * Returns a string of this user's name
     * @return the name of this user
     */
    public String getName() {
        return name;
    }
}
