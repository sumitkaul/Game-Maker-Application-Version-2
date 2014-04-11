package facade;

import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;
import controller.KeyListenerController;
import controller.GameController;
import view.panel.ButtonPanel;
import view.panel.GamePanel;
import model.SpriteModel;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * The class <code>FacadeTest</code> contains tests for the class
 * <code>{@link Facade}</code>.
 * 
 * @version $Revision: 1.0 $
 */
public class FacadeTest {
	/**
	 * Run the Facade(GamePanel) constructor test.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testFacade_1() throws Exception {
		GamePanel gamePanel = new GamePanel(1, 1);

		Facade result = new Facade(gamePanel);

		assertNotNull(result);
	}

	/**
	 * Run the void createViewsForModels(List<SpriteModel>) method test.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCreateViewsForModels_1() throws Exception {
		Facade fixture = new Facade(new GamePanel(1, 1));
		fixture.setKeyListenerController(new KeyListenerController());
		fixture.setTimer(new Timer(1, new ButtonPanel(100,100)));
		fixture.setGameController(new GameController());
		List<SpriteModel> spriteModels = new ArrayList<SpriteModel>();

		fixture.createViewsForModels(spriteModels);

	}

	/**
	 * Run the void createViewsForModels(List<SpriteModel>) method test.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCreateViewsForModels_2() throws Exception {
		Facade fixture = new Facade(new GamePanel(1, 1));
		fixture.setKeyListenerController(new KeyListenerController());
		fixture.setTimer(new Timer(1, new ButtonPanel(100,100)));
		fixture.setGameController(new GameController());
		List<SpriteModel> spriteModels = new ArrayList<SpriteModel>();

		fixture.createViewsForModels(spriteModels);

	}

	/**
	 * Run the GameController getGameController() method test.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetGameController_1() throws Exception {
		Facade fixture = new Facade(new GamePanel(1, 1));
		fixture.setKeyListenerController(new KeyListenerController());
		fixture.setTimer(new Timer(1, new ButtonPanel(100,100)));
		fixture.setGameController(new GameController());

		GameController result = fixture.getGameController();

		assertNotNull(result);
		assertEquals(null, result.getGamePanel());
	}

	/**
	 * Run the KeyListenerController getKeyListenerController() method test.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetKeyListenerController_1() throws Exception {
		Facade fixture = new Facade(new GamePanel(1, 1));
		fixture.setKeyListenerController(new KeyListenerController());
		fixture.setTimer(new Timer(1, new ButtonPanel(100,100)));
		fixture.setGameController(new GameController());

		KeyListenerController result = fixture.getKeyListenerController();

		assertNotNull(result);
	}

	/**
	 * Run the Timer getTimer() method test.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetTimer_1() throws Exception {
		Facade fixture = new Facade(new GamePanel(1, 1));
		fixture.setKeyListenerController(new KeyListenerController());
		fixture.setTimer(new Timer(1, new ButtonPanel(100,100)));
		fixture.setGameController(new GameController());

		Timer result = fixture.getTimer();

		assertNotNull(result);
		assertEquals(false, result.isRunning());
		assertEquals(1, result.getDelay());
		assertEquals(null, result.getActionCommand());
		assertEquals(true, result.isCoalesce());
		assertEquals(true, result.isRepeats());
		assertEquals(1, result.getInitialDelay());
	}

	/**
	 * Run the void setGameController(GameController) method test.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSetGameController_1() throws Exception {
		Facade fixture = new Facade(new GamePanel(1, 1));
		fixture.setKeyListenerController(new KeyListenerController());
		fixture.setTimer(new Timer(1, new ButtonPanel(100,100)));
		fixture.setGameController(new GameController());
		GameController gameController = new GameController();

		fixture.setGameController(gameController);

	}

	/**
	 * Run the void setKeyListenerController(KeyListenerController) method test.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSetKeyListenerController_1() throws Exception {
		Facade fixture = new Facade(new GamePanel(1, 1));
		fixture.setKeyListenerController(new KeyListenerController());
		fixture.setTimer(new Timer(1, new ButtonPanel(100,100)));
		fixture.setGameController(new GameController());
		KeyListenerController keyListenerController = new KeyListenerController();

		fixture.setKeyListenerController(keyListenerController);

	}

	/**
	 * Run the void setTimer(Timer) method test.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSetTimer_1() throws Exception {
		Facade fixture = new Facade(new GamePanel(1, 1));
		fixture.setKeyListenerController(new KeyListenerController());
		fixture.setTimer(new Timer(1, new ButtonPanel(100,100)));
		fixture.setGameController(new GameController());
		Timer timer = new Timer(1, new ButtonPanel(100,100));

		fixture.setTimer(timer);

	}

	/**
	 * Run the void startGame() method test.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testStartGame_1() throws Exception {
		Facade fixture = new Facade(new GamePanel(1, 1));
		fixture.setKeyListenerController(new KeyListenerController());
		fixture.setTimer(new Timer(1, new ButtonPanel(100,100)));
		fixture.setGameController(new GameController());

		fixture.startGame();

	}

	/**
	 * Run the void stopGame() method test.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testStopGame_1() throws Exception {
		Facade fixture = new Facade(new GamePanel(1, 1));
		fixture.setKeyListenerController(new KeyListenerController());
		fixture.setTimer(new Timer(1, new ButtonPanel(100,100)));
		fixture.setGameController(new GameController());

		fixture.stopGame();

	}

	/**
	 * Perform pre-test initialization.
	 * 
	 * @throws Exception
	 *             if the initialization fails for some reason
	 */
	@Before
	public void setUp() throws Exception {
		// add additional set up code here
	}

	/**
	 * Perform post-test clean-up.
	 * 
	 * @throws Exception
	 *             if the clean-up fails for some reason
	 */
	@After
	public void tearDown() throws Exception {
		// Add additional tear down code here
	}

	/**
	 * Launch the test.
	 * 
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		new org.junit.runner.JUnitCore().run(FacadeTest.class);
	}
}