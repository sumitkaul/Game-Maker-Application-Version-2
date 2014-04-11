package gameServer;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class SavePlayer {
	private SavedGames savedGame;
	
	public SavePlayer(SavedGames savedGame)
	{
		this.savedGame=savedGame;
		//savePlayerGame();
	}
	public int savePlayerGame(SavedGames savedGame) {
		int status=DatabaseSession.getInstance().saveObject(savedGame);
		return status;
		
	}
}
