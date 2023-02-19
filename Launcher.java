/** 
 * Launcher.Java
 * @version 1.0
 * @author Edward, Christopher, Andrew
 * June 2022
 * Frame that displays the menu screen
 */

public class Launcher {
    public static void main(String[] args) {
        Menu menu = new Menu();
        
        //Stalls until a button is pressed
        while(menu.buttonPressed()==false) {
            try { 
                Thread.sleep(200);
            } catch (InterruptedException e) {}
        }
        
        if (menu.offline()==true) {
            try{
                new User();
            }catch(Exception e) {
                System.out.println("Failure to launch PaintBoard");
            }
        } else if (menu.createServer()==true) {
            JoinServerPanel joinServerPanel = new JoinServerPanel(true);
            //Stalls until the user inputs nickname
            while (joinServerPanel.buttonPressed() == false) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {}
            }
            try{
                new User(true, joinServerPanel.getName(), true);
            } catch(Exception e) {
                System.out.println("Failure to launch PaintBoard");
            } 
        } else if (menu.joinServer()==true) {
            JoinServerPanel joinServerPanel = new JoinServerPanel(false);
            //Stalls until the user inputs ID Address
            while (joinServerPanel.buttonPressed() == false) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {}
            }
            try{
                new User(joinServerPanel.getServerIPAdress(), joinServerPanel.getName(), true);
            } catch(Exception e) {
                System.out.println("Failure to launch PaintBoard");
            } 
        }
    }
}
