package gameServer;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

public class URLImagesGetter {

	public URLImagesGetter() {
		// TODO Auto-generated constructor stub
	}
	public ArrayList<String> GetImages(URLImages urlimages) {
		
		ArrayList<String> URLs=new ArrayList<String>();
		int id= urlimages.getUserid();
		Session session = DatabaseSession.getInstance().getNewSession();
		String SQL_STRING = "from URLImages u where u.userid= :id";
		Query query = session.createQuery(SQL_STRING);
		query.setParameter("id",id);
		List list = query.list();
			for(Object urlImages:list) {
				URLs.add(((URLImages)urlImages).getUrl());
			}
		session.close();
		return URLs;
	}
	
}
