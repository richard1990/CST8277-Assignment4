// import statements
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import business.Sprite;
import business.SpriteSessionRemote;
/**
 * This class handles what occurs when a mouse click occurs in the Swing client.
 * If clicked on an empty space, a Sprite is created. If the user clicks on a Sprite,
 * the Sprite object can be modified, where its speed, color, etc. can be changed and
 * the change will be sent back to the server.
 * @author tgk, teammate
 */
public class SpritePanel extends JPanel implements Runnable {
	/** Eclipse-generated serialVersionUID. */
	private static final long serialVersionUID = -1022169018682302297L;
	/** List containing Sprite objects. */
	private List<Sprite> sprites;
	/** SpriteSessionRemote object. */
	private SpriteSessionRemote session;
	/** Static integer to handle clicking on a Sprite. */
	private static final int CLICK_RADIUS = 20;
	/** Sprite object. */
	public Sprite sprite;
	/** JTextField to edit the dx attribute of a Sprite. */
	private JTextField dxTextField;
	/** JTextField to edit the dy attribute of a Sprite. */
	private JTextField dyTextField;
	/** JTextField to edit the x-axis attribute of a Sprite. */
	private JTextField xTextField;
	/** JTextField to edit the y-axis attribute of a Sprite. */
	private JTextField yTextField;
	/** JTextField to edit the red attribute of a Sprite's colour (RGB). */
	private JTextField rTextField;
	/** JTextField to edit the green attribute of a Sprite's colour (RGB). */
	private JTextField gTextField;
	/** JTextField to edit the blue attribute of a Sprite's colour (RGB). */
	private JTextField bTextField;
	/** Integer to hold the Sprite's previous dx attribute. */
	private int oldDx = 0;
	/** Integer to hold the Sprite's previous dy attribute. */
	private int oldDy = 0;

	/**
	 * Constructor to setup program.
	 * @param	session		SpriteSessionRemote object.
	 */
	public SpritePanel(final SpriteSessionRemote session) {
		this.session = session;
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(final MouseEvent event) {
				sprite = spriteClick(event);
				if (sprite != null) {
					System.out.println("Sprite Clicked");
					try {
						spriteSettings(sprite, session);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				} else {
					System.out.println("No Sprite Clicked");
					try {
						System.out.println("Creating a new sprite");
						session.newSprite(event);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}
			}

			// method added by teammate to handle updating a Sprite
			private Sprite spriteClick(MouseEvent event) {
				for (Sprite s : sprites) {
					if (s.getX() >= event.getX() - CLICK_RADIUS && s.getX() <= event.getX() + CLICK_RADIUS) {
						if (s.getY() >= event.getY() - CLICK_RADIUS && s.getY() <= event.getY() + CLICK_RADIUS) {
							return s;
						}
					}
				}
				return null;
			}
			// method added by teammate to handle updating a Sprite
			public void spriteSettings(final Sprite sprite, final SpriteSessionRemote session) throws RemoteException {
				JPanel frame = new JPanel();
				frame.setSize(260, 260);
				frame.setLayout(new GridLayout(7, 1));
				JLabel dxLabel = new JLabel("Dx");
				JLabel dyLabel = new JLabel("Dy");
				JLabel xLabel = new JLabel("X");
				JLabel yLabel = new JLabel("Y");
				JLabel rLabel = new JLabel("R");
				rLabel.setHorizontalAlignment((int) CENTER_ALIGNMENT);
				JLabel gLabel = new JLabel("G");
				gLabel.setHorizontalAlignment((int) CENTER_ALIGNMENT);
				JLabel bLabel = new JLabel("B");
				bLabel.setHorizontalAlignment((int) CENTER_ALIGNMENT);
				JLabel colorLabel = new JLabel("Color:");
				colorLabel.setHorizontalAlignment((int) CENTER_ALIGNMENT);
				JLabel speedLabel = new JLabel("Speed");
				speedLabel.setHorizontalAlignment((int) CENTER_ALIGNMENT);
				JLabel positionLabel = new JLabel("Position");
				positionLabel.setHorizontalAlignment((int) CENTER_ALIGNMENT);

				dxTextField = new JTextField();
				dyTextField = new JTextField();
				xTextField = new JTextField();
				yTextField = new JTextField();
				rTextField = new JTextField();
				gTextField = new JTextField();
				bTextField = new JTextField();

				JPanel speedPanel = new JPanel(new GridLayout(2, 2));
				JPanel positionPanel = new JPanel(new GridLayout(2, 2));
				JPanel colorPanel = new JPanel(new GridLayout(1, 6));

				speedPanel.setSize(300, 150);

				speedPanel.add(dxLabel);
				speedPanel.add(dxTextField);
				speedPanel.add(dyLabel);
				speedPanel.add(dyTextField);
				positionPanel.add(xLabel);
				positionPanel.add(xTextField);
				positionPanel.add(yLabel);
				positionPanel.add(yTextField);

				colorPanel.add(rLabel);
				colorPanel.add(rTextField);
				colorPanel.add(gLabel);
				colorPanel.add(gTextField);
				colorPanel.add(bLabel);
				colorPanel.add(bTextField);
				
				frame.add(speedLabel);
				frame.add(speedPanel);
				frame.add(positionLabel);
				frame.add(positionPanel);
				frame.add(colorLabel);
				frame.add(colorPanel);
				frame.setVisible(true);
				frame.validate();
				dxTextField.setText(Integer.toString(sprite.getDx()));
				dyTextField.setText(Integer.toString(sprite.getDy()));
				xTextField.setText(Integer.toString(sprite.getX()));
				yTextField.setText(Integer.toString(sprite.getY()));
				rTextField.setText(Integer.toString(sprite.getColor().getRed()));
				gTextField.setText(Integer.toString(sprite.getColor().getGreen()));
				bTextField.setText(Integer.toString(sprite.getColor().getBlue()));

				oldDx = sprite.getDx();
				oldDy = sprite.getDy();
				sprite.setDx(0);
				sprite.setDy(0);

				session.updateSprite(sprite);

				int option = JOptionPane.showConfirmDialog(null, frame, "Sprite Information",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
				if (option == JOptionPane.OK_OPTION) {
					Color color = new Color(Integer.parseInt(rTextField.getText()),
					Integer.parseInt(gTextField.getText()), Integer.parseInt(bTextField.getText()));
					sprite.setColor(color);
					sprite.setDx(Integer.parseInt(dxTextField.getText()));
					sprite.setDy(Integer.parseInt(dyTextField.getText()));
					sprite.setX(Integer.parseInt(xTextField.getText()));
					sprite.setY(Integer.parseInt(yTextField.getText()));

					session.updateSprite(sprite);
					return;
				} else {
					// set sprite with old speed
					sprite.setDx(oldDx);
					sprite.setDy(oldDy);
					session.updateSprite(sprite);

				}
			}
		});
	}

	/**
	 * Void method run used by Runnable interface.
	 */
	@Override
	public void run() {
		System.out.println("now animating...");
		try {
			while (true) {
				try {
					sprites = session.getSpriteList();
				} catch (javax.ejb.NoSuchEJBException e) {
					System.out.println("Lost contact with server, exiting...");
					System.exit(1);
				}
				repaint();
				// sleep while waiting to display the next frame of the
				// animation
				try {
					Thread.sleep(200); // wake up roughly 25 frames per second
				} catch (InterruptedException exception) {
					exception.printStackTrace();
				}
			}
		} catch (RemoteException e) {
			System.out.println("game over? exiting...");
		}
	}

	/**
	 * paintComponent method.
	 * @param	g	Graphics object.
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (sprites != null) {
			for (Sprite s : sprites) {
				s.draw(g);
			}
		}
	}
} // end class SpritePanel