
package gameServer;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import utility.Constants;

public class DatabaseSession {
	
	private static DatabaseSession instance = null;
	private Configuration configuration;
	private ServiceRegistry serviceRegistry;
	private SessionFactory sessionFactory;
	
	public static DatabaseSession getInstance()
	{
		if (instance == null) {
			instance = new DatabaseSession();	
		}
		return instance;
	}
	
	private DatabaseSession(){
		configuration = new Configuration();
	    configuration.configure();
	    serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry(); 
	    sessionFactory = new Configuration().configure().buildSessionFactory(serviceRegistry);
	}
	
	public <Generic> int saveObject(Generic dbObject){
		Session session = sessionFactory.openSession();
		try
		{
			session.beginTransaction();
			session.save(dbObject);
		}
		catch (Exception e) {
			return Constants.FAILED;
		}
		session.getTransaction().commit();
		session.close();
		return Constants.OK;
	}
	public <Generic> int saveOrUpdateObject(Generic dbObject){
		Session session = sessionFactory.openSession();
		try
		{
			session.beginTransaction();
			session.saveOrUpdate(dbObject);
		}
		catch (Exception e) {
			return Constants.FAILED;
		}
		session.getTransaction().commit();
		session.close();
		return Constants.OK;
	}
	
	public Session getNewSession(){
		return this.sessionFactory.openSession();
	}
}

