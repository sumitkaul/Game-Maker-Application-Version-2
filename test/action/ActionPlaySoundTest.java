package action;


import static org.junit.Assert.*;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;

import org.junit.Test;

public class ActionPlaySoundTest {

	@Test
	public void test() {
		
		try {
			//Play a sound file from resource using PlaySound action. If no exception occurs, the test succeeded.
			String soundFile = "bounce.au";
			ActionPlaySound playSound = new ActionPlaySound(soundFile);
			playSound.doAction(null);
			
		} catch (Exception e) {
			//If any exception fail the test
			assertTrue(false);
		}
		//If no exception-test succeeded
		assertTrue(true);
		
	}

}
