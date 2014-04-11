package gameServer;

import static org.junit.Assert.*;
import org.junit.Test;
import org.mockito.Mockito;

public class CustomGamesTest {

	CustomGames customGames = Mockito.mock(CustomGames.class);
	
	@Test
	public void testGameId() throws Exception{
		Mockito.when(customGames.getGameid()).thenReturn(50);
		assertEquals(50, customGames.getGameid());
	}

	@Test
	public void testUserId() throws Exception{
		Mockito.when(customGames.getUserid()).thenReturn(10);
		assertEquals(10, customGames.getUserid());
	}

	@Test
	public void testGameName() throws Exception{
		Mockito.when(customGames.getGamename()).thenReturn("gamename");
		assertEquals("gamename", customGames.getGamename());
	}
	
	@Test
	public void testSavedGame() throws Exception{
		Mockito.when(customGames.getSavedgame()).thenReturn("savedgame");
		assertEquals("savedgame", customGames.getSavedgame());
	}
	
}
