package gameMaker;

import org.apache.log4j.PropertyConfigurator;

import view.Design;
import view.MainLogin;

public class Main {

	private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
			.getLogger(Main.class);
	
	public Main() {
		PropertyConfigurator.configure(getClass().getClassLoader().getResource("log4j.properties"));
	}

    public static void main(String[] args) {
		LOG.info("Creating GameMaker");
		new MainLogin();
    }
}