package gameServer;

import static org.junit.Assert.*;
import org.junit.Test;
import org.mockito.Mockito;

public class UserInfoTest {

	UserInfo userInfo = Mockito.mock(UserInfo.class);
	
	@Test
	public void testUserId() throws Exception{
		Mockito.when(userInfo.getUserid()).thenReturn(10);
		assertEquals(10, userInfo.getUserid());
	}

	@Test
	public void testGameId() throws Exception{
		Mockito.when(userInfo.getUsername()).thenReturn("username");
		assertEquals("username", userInfo.getUsername());
	}

	@Test
	public void testPassword() throws Exception{
		Mockito.when(userInfo.getPassword()).thenReturn("password");
		assertEquals("password", userInfo.getPassword());
	}
		
}
