package gameServer;

import static org.junit.Assert.*;
import org.junit.Test;
import org.mockito.Mockito;

public class SavedGamesTest {

	SavedGames savedGames = Mockito.mock(SavedGames.class);

	@Test
	public void testUserId() throws Exception{
		Mockito.when(savedGames.getUserid()).thenReturn(10);
		assertEquals(10, savedGames.getUserid());
	}

	@Test
	public void testGameId() throws Exception{
		Mockito.when(savedGames.getGameid()).thenReturn(50);
		assertEquals(50, savedGames.getGameid());
	}

	@Test
	public void testGameName() throws Exception{
		Mockito.when(savedGames.getSavegamename()).thenReturn("gamename");
		assertEquals("gamename", savedGames.getSavegamename());
	}
	
	@Test
	public void testSavedGame() throws Exception{
		Mockito.when(savedGames.getSavedgameXML()).thenReturn("savedgame");
		assertEquals("savedgame", savedGames.getSavedgameXML());
	}

	@Test
	public void testScore() throws Exception{
		Mockito.when(savedGames.getScore()).thenReturn(100);
		assertEquals(100, savedGames.getScore());
	}

}
