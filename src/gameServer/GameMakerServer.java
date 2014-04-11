package gameServer;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.xml.XmlConfiguration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import utility.enums.QueryType;

public class GameMakerServer {
	private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
			.getLogger(GameMakerServer.class);
	
    public static void main(String[] args) throws Exception
    {
        Server jetty = new Server();
        String[] configFiles = {"jetty.xml"};
        for(String configFile : configFiles) {
           XmlConfiguration configuration = new XmlConfiguration(GameMakerServer.class.getClassLoader().getResource(configFile)); 
           configuration.configure(jetty);
        }
        
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.register(GameServerConfig.class);
        
        ServletHolder servletHolder = new ServletHolder(new DispatcherServlet(applicationContext));
        ServletContextHandler contextHandler = new ServletContextHandler();
        contextHandler.setContextPath("/");
        contextHandler.addServlet(servletHolder, "/*");
        
        jetty.setHandler(contextHandler);
        
        jetty.start();
        jetty.join();
    }
}
