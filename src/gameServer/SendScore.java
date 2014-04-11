
package gameServer;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class SendScore {
	private Scores score;
	
	public SendScore(Scores score)
	{
		this.score=score;
		//publishScore();
	}

	public int publishScore(Scores scores) {
		int status=DatabaseSession.getInstance().saveOrUpdateObject(scores);
		return status;
		
	}
}
