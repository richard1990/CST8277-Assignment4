/**
 * business is the package for class placement.
 */
package business;
// import statements
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.Remote;
/**
 * This interface allows assists with creating new Sprites on the network.
 * @author tgk, teammate
 */
@Remote
public interface SpriteSessionRemote {
    List<Sprite> getSpriteList() throws RemoteException;
    void newSprite(MouseEvent e) throws RemoteException;
    void updateSprite(Sprite sprite) throws RemoteException; // added by teammate
    int getHeight() throws RemoteException;
    int getWidth() throws RemoteException;
} // end interface SpriteSessionRemote