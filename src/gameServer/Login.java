package gameServer;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jetty.util.log.Log;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class Login {
	private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
			.getLogger(Login.class);
	
	private String username;
	private int userId;
	private String password;
	private UserInfo userInfo;
	
	public Login(UserInfo userInfo)
	{
		this.username= userInfo.getUsername();
		this.password=userInfo.getPassword();
		this.userInfo=userInfo;
	}

	public UserInfo validateLogin(UserInfo userInfo) {
		Session session = DatabaseSession.getInstance().getNewSession();
		String SQL_STRING = "from UserInfo u where u.username= :username"+" and u.password= :password";
		Query query = session.createQuery(SQL_STRING);
		query.setParameter("username", this.username);
		query.setParameter("password", this.password);
		List list = query.list();
		if (list.size() > 0)
		{
			userInfo.setUserid(((UserInfo)list.get(0)).getUserid());
			userInfo.setValid(true);
		}
		else
		{
			userInfo.setValid(false);
		}
		session.close();
		return userInfo;
	}
	
	public UserInfo getIDFromName(UserInfo userInfo)
	{
		Session session = DatabaseSession.getInstance().getNewSession();
		String SQL_STRING = "from UserInfo u where u.username=:username";
		Query query = session.createQuery(SQL_STRING);
		query.setParameter("username", this.username);
		List list = query.list();
		if (list.size() > 0)
		{
			userInfo.setUserid(((UserInfo)list.get(0)).getUserid());
			userInfo.setValid(true);
		}
		else
		{
			userInfo.setValid(false);
		}
		session.close();
		return userInfo;
	}

}
