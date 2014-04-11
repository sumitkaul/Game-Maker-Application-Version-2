package gameServer;

import java.util.ArrayList;

import org.hibernate.Query;
import org.hibernate.Session;

public class HighScore {

	private int gameId;
	private Scores score;

	public HighScore(Scores score)
	{
		this.score=score;
	}
	public ArrayList<Scores> getHighScores(Scores score) {
		Session session = DatabaseSession.getInstance().getNewSession();
		Query query = session.createQuery("FROM Scores where :gameid");
		query.setParameter("gameid", score.getGameid());
		ArrayList<Scores> scores = (ArrayList<Scores>) query.list();
		session.close();
		return scores;
	}
}
