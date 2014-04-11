package gameServer;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class SaveMaker {
	private CustomGames customGames;

	
	public SaveMaker(CustomGames customGames)
	{
		this.customGames=customGames;
		//saveMakerGame();
	}
	
	public int saveMakerGame(CustomGames customGames) {
		
			int status=DatabaseSession.getInstance().saveObject(customGames);
			return status;
		
	}

}
