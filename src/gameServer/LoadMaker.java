package gameServer;

import java.util.ArrayList;
import org.hibernate.Query;
import org.hibernate.Session;

public class LoadMaker {

	private ArrayList<String> gameList;
	private CustomGames customGames;
	
	public LoadMaker(CustomGames customGames) {
		this.customGames=customGames;
	}

	public ArrayList<String> getGameList() {
		Session session = DatabaseSession.getInstance().getNewSession();
		Query query = session.createSQLQuery("select gamename FROM CustomGames");
		gameList = (ArrayList<String>) query.list();
		customGames.setGameList(gameList);
		session.close();
		return gameList;
	}

	public CustomGames getSavedGame(CustomGames customGames) {
		Session session = DatabaseSession.getInstance().getNewSession();
		Query query = session.createQuery("FROM CustomGames c where c.gamename=:gamename");
		query.setParameter("gamename", customGames.getGamename());
		customGames = (CustomGames)query.list().get(0);
		session.close();
		return customGames;
	}
	
}
