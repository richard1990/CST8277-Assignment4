// import statements
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.JFrame;
import business.SpriteSessionRemote;
import java.rmi.RemoteException;
/**
 * This class handles the connection to the server. It displays a blue
 * Sprite inside a JPanel which bounces off the edges of the window. Clicking 
 * on a spot creates a new Sprite.
 * @author tgk
 */
public class SpriteClient {
	/**
	 * Entry point "main()" as required by the JVM. Handles connection
	 * to server.
	 * @param	args	standard command line parameters (arguments) as a string array.
	 */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Bouncing Sprites");
		SpriteSessionRemote session = null;
		System.setProperty("org.omg.CORBA.ORBInitialHost", "localhost");
		System.setProperty("org.omg.CORBA.ORBInitialPort", "3700");
		try {
			System.out.println("about to try for a session...");
			session = (SpriteSessionRemote) new InitialContext().lookup("java:global/SpriteEE/SpriteEE-ejb/SpriteSession");
            System.out.println("I got a session");
            System.out.println("This is the height: " + session.getHeight());
		} catch(NamingException | RemoteException ex) {
			ex.printStackTrace();
		}
		if (session != null) {
		   System.out.println("I got game");
		}
		else {
			System.err.println("Could not contact game server");
			System.exit(1);
		}
		try {
			frame.setSize(session.getHeight(), session.getWidth());
		} catch(RemoteException ex) {
			System.err.println("Could not get one or both of HEIGHT, WIDTH for this game");
			ex.printStackTrace();
		}
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
		SpritePanel panel = new SpritePanel(session);
        frame.add(panel);
        frame.validate();
        new Thread(panel).start();
    }
} // end class SpriteClient