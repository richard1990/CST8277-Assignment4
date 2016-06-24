/**
 * business is the package for class placement.
 */
package business;
// import statements
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
/**
 *
 * @author tgk, teammate
 */
@Singleton
public class SpriteGame {
    /** Static integer to contain panel height (500). */
    public static final int HEIGHT = 500;
    /** Static integer to contain panel height (500). */
    public static final int WIDTH = 500;
    /** List containing Sprite objects. */
    List<Sprite> sprites;
    /** SpriteFacade object. */
    @EJB
    private SpriteFacade spriteFacade;

    /**
     * Get method that returns the list of Sprites.
     * @return list of Sprites.
     */
    public List getSpriteList() { return sprites; }

    /**
     * Void method that creates a new Sprite.
     * @param   event   MouseEvent object.
     * @param   color   Color object.
     */
    public void newSprite(MouseEvent event, Color color) {
        Sprite newSprite = new Sprite(HEIGHT, WIDTH, color);
        spriteFacade.create(newSprite);
        System.out.println("New sprite created");
    }
    
    /**
     * Void method that updates a Sprite. Method added by teammate.
     * @param   sprite  Sprite object.
     */
    public void updateSprite(Sprite sprite) {
        spriteFacade.edit(sprite);
        System.out.println(sprite.getId() + " is updated");
    }
    /**
     * Void method that allows Sprites to move.
     */
    @PostConstruct
    public void go() {
        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    // move all the sprites and update them in the database
                    sprites = spriteFacade.findAll();
                    for (Sprite sprite : sprites) {
                        sprite.move();
                        spriteFacade.edit(sprite);
                    }
                    // sleep while waiting to display the next frame of the animation
                    try {
                        Thread.sleep(100);  // wake up roughly 10 frames per second
                    } catch (InterruptedException exception) {
                        exception.printStackTrace();
                    }
                }
            }
        }).start();
    }
} // end class SpriteGame