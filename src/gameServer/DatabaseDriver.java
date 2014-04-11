
package gameServer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseDriver {
	private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
			.getLogger(DatabaseDriver.class);

	private String databaseHost = "jdbc:mysql://localhost:8099/a8team2db?";
	private String databaseName = "a8team2db";
	private String dbUser = "team2";
	private String dbPassword = "password01";	
	
	private String savedGamesTable = "savedGames";
	private String scoresTable = "scores";
	private String gameXMLColumnLabel = "game";
	private String userColumnLabel = "user";
	private String scoreColumnLabel = "score";
	private String gamesNameColumnLabel = "name";
	private String scoresGameNameColumnLabel = "gamename";


	private Connection connect = null;
	private Statement statement = null;
	private ResultSet resultSet = null;

	public DatabaseDriver() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			LOG.error(e);
		}

		try {
			connect = DriverManager.getConnection(databaseHost + "user="
					+ dbUser + "&password=" + dbPassword);

		} catch (SQLException e) {
			LOG.error(e);
		}
	}

	public String getGames() {
		String resultXML = new String();
		try {
			statement = connect.createStatement();
			resultSet = statement.executeQuery("select * from " + databaseName
					+ "." + savedGamesTable);

			String agame;
			String gameTag;
			resultXML = new String("<Games>");
			// append each game in the database to resultXML
			while (resultSet.next()) {
				String gameName = (String) resultSet
						.getObject(gamesNameColumnLabel);
				String userName = (String) resultSet.getObject(userColumnLabel);
				gameTag = "<game gameName='" + gameName + "' userName='"
						+ userName + "'>";
				agame = new String(
						(byte[]) resultSet.getObject(gameXMLColumnLabel));
				resultXML += gameTag + agame + "</game>";
			}
			resultXML += new String("</Games>");

		} catch (SQLException e) {
			LOG.error(e);
		}

		return resultXML;
	}

	public void insertGame(String user, String gameName, String savedGame) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			LOG.error(e);
		}
		LOG.debug("attempting to get connection to mysql server");
		try {
			connect = DriverManager.getConnection(databaseHost + "user="
					+ dbUser + "&password=" + dbPassword);
			statement = connect.createStatement();
			statement.executeUpdate("INSERT INTO " + savedGamesTable
					+ " VALUES(default,'" + user + "','" + savedGame + "','"
					+ gameName + "')");
		} catch (SQLException e) {
			LOG.error(e);
		}
	}

	public String getScores() {
		String resultXML = new String();
		try {
			statement = connect.createStatement();
			resultSet = statement.executeQuery("select * from " + databaseName
					+ "." + scoresTable + " order by score desc");

			resultXML = new String("<Scores>");
			// append each game in the database to resultXML
			while (resultSet.next()) {
				String user = (String) resultSet.getObject(userColumnLabel);
				String gamename = (String) resultSet
						.getObject(scoresGameNameColumnLabel);
				int score = resultSet.getInt(scoreColumnLabel);

				resultXML += "<score gamename=" + "\"" + gamename + "\""
						+ " user=" + "\"" + user + "\"" + " score=" + "\""
						+ score + "\"" + "/>";
			}
			resultXML += new String("</Scores>");

		} catch (SQLException e) {
			LOG.error(e);
		}

		return resultXML;
	}

	public void insertScore(String user, int score, String gameName) {
		LOG.info("insert score, use=" + user + " score=" + score + " gameName="
				+ gameName);
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			LOG.error(e);
		}
		LOG.debug("attempting to get connection to mysql server");
		try {
			statement = connect.createStatement();
			statement.executeUpdate("INSERT INTO " + scoresTable
					+ " VALUES(default,'" + user + "','" + score + "','"
					+ gameName + "')");
		} catch (SQLException e) {
			LOG.error(e);
		}
	}

}
