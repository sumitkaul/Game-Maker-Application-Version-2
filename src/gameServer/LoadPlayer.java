package gameServer;

import java.util.ArrayList;
import org.hibernate.Query;
import org.hibernate.Session;

public class LoadPlayer {

	private ArrayList<String> gameList;
	private SavedGames savedGames;

	public LoadPlayer(SavedGames savedGames) {
		this.savedGames=savedGames;
	}

	public ArrayList<String> getGameList() {
		Session session = DatabaseSession.getInstance().getNewSession();
		Query query = session.createSQLQuery("SELECT savedgamename FROM SavedGames WHERE userid="+this.savedGames.getUserid());
		gameList = (ArrayList<String>) query.list();
		savedGames.setGameList(gameList);
		session.close();
		return gameList;
	}

	public SavedGames getSavedGame(SavedGames savedGames) {
		Session session = DatabaseSession.getInstance().getNewSession();
		Query query = session.createQuery("FROM SavedGames s where s.savedgamename=:savedgamename");
		query.setParameter("savedgamename", savedGames.getSavegamename());
		savedGames = (SavedGames) query.list().get(0);
		session.close();
		return savedGames;
	}
	
}
