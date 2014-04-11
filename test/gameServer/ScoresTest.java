package gameServer;

import static org.junit.Assert.*;
import org.junit.Test;
import org.mockito.Mockito;

public class ScoresTest {

	Scores scores = Mockito.mock(Scores.class);
	
	@Test
	public void testUserId() throws Exception{
		Mockito.when(scores.getUserid()).thenReturn(10);
		assertEquals(10, scores.getUserid());
	}

	@Test
	public void testGameId() throws Exception{
		Mockito.when(scores.getGameid()).thenReturn(50);
		assertEquals(50, scores.getGameid());
	}

	@Test
	public void testScore() throws Exception{
		Mockito.when(scores.getScore()).thenReturn(100);
		assertEquals(100, scores.getScore());
	}

}
