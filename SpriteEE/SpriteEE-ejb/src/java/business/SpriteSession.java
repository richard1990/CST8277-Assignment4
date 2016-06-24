/**
 * business is the package for class placement.
 */
package business;
// import statements
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Random;
import javax.ejb.EJB;
import javax.ejb.Stateful;
/**
 *
 * @author tgk, teammate
 */
@Stateful
public class SpriteSession implements SpriteSessionRemote {
    /** Random object. */
    public static final Random random = new Random();
    /** Color object that will randomly create a color. */
    Color color = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
    /** SpriteGame object identified as Enterprise JavaBean. */
    @EJB
    private SpriteGame spriteGame;

    /**
     * Get method that returns a List of Sprites.
     * @return List containing Sprite objects.
     */
    @Override
    public List getSpriteList() { return spriteGame.getSpriteList(); }

    /**
     * Void method that creats a new Sprite at mouse click location.
     * @param   event   MouseEvent object.
     */
    @Override
    public void newSprite(MouseEvent event) { spriteGame.newSprite(event, color); }
    
    /**
     * Void method that updates a Sprite.
     * @param   sprite  Sprite object.
     */
    @Override
    public void updateSprite(Sprite sprite) { spriteGame.updateSprite(sprite); }
    
    /**
     * Get method that retrieves height of the window.
     * @return the height of the window as an integer.
     */
    @Override
    public int getHeight() { return spriteGame.HEIGHT; }
    /**
     * Get method that retrieves the width of the window.
     * @return the width of the window as an integer.
     */
    @Override
    public int getWidth() { return spriteGame.WIDTH; }
} // end class SpriteSession