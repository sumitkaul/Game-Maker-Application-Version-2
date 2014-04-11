package testClient;


import gameServer.Scores;
import gameServer.URLImages;


import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.net.UnknownHostException;

import java.util.ArrayList;


import org.eclipse.jetty.client.ContentExchange;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.HttpExchange;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.io.ByteArrayBuffer;

import utility.HighScoreDisplay;


public class GameClient {
	private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
			.getLogger(GameClient.class);

	private String gameServerAddress = "tintin.cs.indiana.edu";
	private int gameServerPort = 8098;
	private String gamesPath = "/games";
	private String playerGamesPath = "/savedgames";
	private String MakerGamesPath = "/customgames";
	private String scoresPath = "/scores";
	private String loginPath = "/login";
	private String highScoresPath="/highscores";
	private String webimagesPath="/webimagesPath";
	private String getWebimagesPath="/getwebimagesPath";
	private static GameClient gameClient;
	private HttpClient client;
	private String username;
	private String password;
	private int userId;
	private String gameName;
	private int gameId;
	
	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public static GameClient getInstance() {

		if (gameClient == null) {
			gameClient = new GameClient();
		}
		return gameClient;

	}
	
	public void setAuthenticationHeaders(HttpExchange exchange) {
		String credentials = username + ":" + password;
		//String encodedCredentials = Base64.encodeBase64String(credentials.getBytes());
		exchange.addRequestHeader("Authorization", credentials);
	}
	public void setSendScoresHeaders(HttpExchange exchange) {
		String credentials = GameClient.getInstance().getUserId() + ":" +"10"+ ":"+ HighScoreDisplay.getInstance().getScore();
		//String encodedCredentials = Base64.encodeBase64String(credentials.getBytes());
		//String credentials = "11" + ":" + "20" + ":"+ HighScoreDisplay.getInstance().getScore();
		exchange.addRequestHeader("SendScores", credentials);
	}	
	public void setWebimageUrl(HttpExchange exchange,URLImages urlImages) {
		String credentials =urlImages.getUserid() + ":" + urlImages.getUrl();
		//String encodedCredentials = Base64.encodeBase64String(credentials.getBytes());
		//String credentials = "11" + ":" + "20" + ":"+ HighScoreDisplay.getInstance().getScore();
		exchange.addRequestHeader("SendWebimages", credentials);
	}
	public void setGameLoadHeader(HttpExchange exchange) {
		//String credentials = GameClient.getInstance().getUserId() + ":" + GameClient.getInstance().getGameId() + ":"+ HighScoreDisplay.getInstance().getScore();
		//String encodedCredentials = Base64.encodeBase64String(credentials.getBytes());
		//String credentials = "11" + ":" + "20" + ":"+ HighScoreDisplay.getInstance().getScore();
		exchange.addRequestHeader("LoadMaker", gameName);
	}
	public void getWebimageUrl(HttpExchange exchange,int id) {
		String credentials =Integer.toString(id);
		//String encodedCredentials = Base64.encodeBase64String(credentials.getBytes());
		//String credentials = "11" + ":" + "20" + ":"+ HighScoreDisplay.getInstance().getScore();
		exchange.addRequestHeader("GetWebimages", credentials);
	}
	
	
	public boolean checkLogin() {
		ContentExchange exchange = new ContentExchange();

		exchange.setMethod("GET");
		String postURL = "http://" + gameServerAddress + ":" + gameServerPort + loginPath;
		exchange.setURL(postURL);
		setAuthenticationHeaders(exchange);
		LOG.debug("Request Fields checkLogin:" + exchange.getRequestFields());

		try {
			client.send(exchange);
		} catch (IOException e) {
			LOG.error(e);
		}
		try {
			exchange.waitForDone();
		}catch (InterruptedException e) {
			LOG.error(e);
		}
		
		int status=-1;
		status = exchange.getResponseStatus();
LOG.debug("checkLogin, status:" + status );
		
		if(status==HttpStatus.OK_200){
			try {
				GameClient.getInstance().setUserId(Integer.parseInt(exchange.getResponseContent()));
			} catch (UnsupportedEncodingException e) {
				LOG.error(e);
			}
			return true;
		}
		else if(status==HttpStatus.UNAUTHORIZED_401){
			return false;
		}
		else {
			LOG.error("checkLogin, status:" + status );
			return false;
		}
	}
	
	public boolean registerLogin() {
		ContentExchange exchange = new ContentExchange();

		exchange.setMethod("POST");
		String postURL = "http://" + gameServerAddress + ":" + gameServerPort + loginPath;
		exchange.setURL(postURL);
		setAuthenticationHeaders(exchange);
		LOG.debug("Request Fields registerLogin:" + exchange.getRequestFields());

		try {
			client.send(exchange);
		} catch (IOException e) {
			LOG.error(e);
		}
		try {
			exchange.waitForDone();
		} catch (InterruptedException e) {
			LOG.error(e);
		}
		int status = exchange.getResponseStatus();
		LOG.debug("registerLogin, status:" + status );
		if(status==HttpStatus.OK_200){
			try {
				GameClient.getInstance().setUserId(Integer.parseInt(exchange.getResponseContent()));
			} catch (UnsupportedEncodingException e) {
				LOG.error(e);
			}
			return true;
		}
		else if(status==HttpStatus.CONFLICT_409){
			return false;
		}
		else {
			LOG.error("registerLogin, status:" + status );
			return false;
		}
	}

	public int sendScores() {
		ContentExchange exchange = new ContentExchange();

		
		exchange.setMethod("POST");
		String postURL = "http://" + gameServerAddress + ":" + gameServerPort + scoresPath;
		LOG.info("URL for sending scores" + postURL);
		exchange.setURL(postURL);
		setSendScoresHeaders(exchange);
		LOG.debug("Sending Scores:" + exchange.getRequestFields());

		try {
			client.send(exchange);
		} catch (IOException e) {
			LOG.error(e);
		}
		try {
			exchange.waitForDone();
		} catch (InterruptedException e) {
			LOG.error(e);
		}
		int status = exchange.getResponseStatus();
		LOG.debug("SendScores, status:" + status );
			LOG.error("SendScores, status:" + status );
			return status;
		
	}
	
	public String viewHighScores() {
		ContentExchange exchange = new ContentExchange();

		
		exchange.setMethod("GET");
		String postURL = "http://" + gameServerAddress + ":" + gameServerPort + highScoresPath;
		LOG.info("URL for viewing scores" + postURL);
		exchange.setURL(postURL);
		setSendScoresHeaders(exchange);
		LOG.debug("Viewing High Scores:" + exchange.getRequestFields());

		try {
			client.send(exchange);
		} catch (IOException e) {
			LOG.error(e);
		}
		try {
			exchange.waitForDone();
		} catch (InterruptedException e) {
			LOG.error(e);
		}
		int status = exchange.getResponseStatus();
		
			String scoreslist=null;
			try {
				scoreslist = exchange.getResponseContent();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		LOG.debug("HIghScores, status:" + status );
			LOG.error("HighScores, status:" + status );
			return scoreslist;
		
	}
	
	public String sendwebImages(URLImages urlImages) {
		ContentExchange exchange = new ContentExchange();

		
		exchange.setMethod("POST");
		String postURL = "http://" + gameServerAddress + ":" + gameServerPort + webimagesPath;
		LOG.info("URL for viewing web images" + postURL);
		exchange.setURL(postURL);
		setWebimageUrl(exchange,urlImages);
		LOG.debug("Viewing web images:" + exchange.getRequestFields());

		try {
			client.send(exchange);
		} catch (IOException e) {
			LOG.error(e);
		}
		try {
			exchange.waitForDone();
		} catch (InterruptedException e) {
			LOG.error(e);
		}
		int status = exchange.getResponseStatus();
		
			String webImages=null;
			try {
				webImages = exchange.getResponseContent();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		LOG.debug("webImages, status:" + status );
			LOG.error("webImages, status:" + status );
			return webImages;
		
	}
	
	
	public String viewWebImages(int id) {
		ContentExchange exchange = new ContentExchange();

		
		exchange.setMethod("GET");
		String postURL = "http://" + gameServerAddress + ":" + gameServerPort + getWebimagesPath;
		LOG.info("URL for viewing webimages" + postURL);
		exchange.setURL(postURL);
		getWebimageUrl(exchange,id);
		LOG.debug("Viewing webimages:" + exchange.getRequestFields());

		try {
			client.send(exchange);
		} catch (IOException e) {
			LOG.error(e);
		}
		try {
			exchange.waitForDone();
		} catch (InterruptedException e) {
			LOG.error(e);
		}
		int status = exchange.getResponseStatus();
		
		String webimages=null;
			try {
				webimages = exchange.getResponseContent();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		LOG.debug("webimages, status:" + status );
			LOG.error("webimages, status:" + status );
			return webimages;
		
	}
	
	public GameClient() {
		client = new HttpClient();
		client.setConnectorType(HttpClient.CONNECTOR_SELECT_CHANNEL);
		try {
			client.start();
		} catch (Exception e) {
			LOG.error(e);
		}
	}

	public String getCustomGameNames() {
		

		String games = new String();
		LOG.debug("start getGames");

		ContentExchange exchange = new ContentExchange(true) {
			protected void onResponseComplete() throws IOException {
				int status = getResponseStatus();
				if (status == 200)
					return;
				else {
					LOG.error("error response from server, status " + status
							+ "\n" + this.getResponseContent() + "\n"
							+ "fields " + this.getResponseFields());
				}
			}
		};
		exchange.setMethod("GET");
		String getUrl="http://" + gameServerAddress + ":" + gameServerPort
				+ gamesPath;
		exchange.setURL(getUrl);
		LOG.info(getUrl);
		//exchange.addRequestHeader("USERID", ""+GameClient.getInstance().getUserId());
		try {
			client.send(exchange);
		} catch (IOException e) {
			LOG.error(e);
		}

		int exchangeState = -1;
		try {
			exchangeState = exchange.waitForDone();
		} catch (InterruptedException e) {
			LOG.error(e);
		}
		if (exchangeState == HttpExchange.STATUS_COMPLETED) {
			try {
				games = exchange.getResponseContent();
			} catch (UnsupportedEncodingException e) {
				LOG.error(e);
			}
		}
		LOG.debug("games retrieved successfully");
		return games;
	}
	
	public String getCustomGameXML(String gameName) {
		ContentExchange exchange = new ContentExchange();

		
		exchange.setMethod("GET");
		String postURL = "http://" + gameServerAddress + ":" + gameServerPort+"/customgames"+"/"+gameName;
		//String postURL = "http://" + gameServerAddress + ":" + gameServerPort + MakerGamesPath+"/"+gameName;
		//GameClient.getInstance().setGameName(gameName);
		LOG.info("Loads custom Games"+postURL);
		exchange.setURL(postURL);
		setGameLoadHeader(exchange);
		LOG.debug("Loading custom games:" + exchange.getRequestFields());

		try {
			client.send(exchange);
		} catch (IOException e) {
			LOG.error(e);
		}
		try {
			exchange.waitForDone();
		} catch (InterruptedException e) {
			LOG.error(e);
		}
		
		String gameXML = null;
		try {

			gameXML = exchange.getResponseContent();

			LOG.debug("games retrieved successfully");

		} catch (UnsupportedEncodingException e) {

			LOG.debug("Game retrieval failed");

		}

		return gameXML;
			
	}
		
//		String games = new String();
//		LOG.debug("start getCustomGameXML");
//
//		ContentExchange exchange = new ContentExchange(true) {
//			protected void onResponseComplete() throws IOException {
//				int status = getResponseStatus();
//				if (status == 200)
//					return;
//				else {
//					LOG.error("error response from server, status " + status
//							+ "\n" + this.getResponseContent() + "\n"
//							+ "fields " + this.getResponseFields());
//				}
//			}
//		};
//		exchange.setMethod("GET");
//		exchange.setURL("http://" + gameServerAddress + ":" + gameServerPort
//				+ gamesPath+"/"+gameName);
//		
//		//exchange.addRequestHeader("USERID", ""+GameClient.getInstance().getUserId());
//		try {
//			client.send(exchange);
//		} catch (IOException e) {
//			LOG.error(e);
//		}
//
//		int exchangeState = -1;
//		try {
//			exchangeState = exchange.waitForDone();
//		} catch (InterruptedException e) {
//			LOG.error(e);
//		}
//		if (exchangeState == HttpExchange.STATUS_COMPLETED) {
//			try {
//				games = exchange.getResponseContent();
//			} catch (UnsupportedEncodingException e) {
//				LOG.error(e);
//			}
//		}
//		LOG.debug("games retrieved successfully");
//		return games;


	
	
	public void postGames(int userId, String gameName, byte[] gameXMLBytes) {
		LOG.info("postGames");
		ContentExchange exchange = new ContentExchange(true) {
			protected void onResponseComplete() throws IOException {
				int status = getResponseStatus();
				if (status == 200)
					return;
				else {
					LOG.error("error response from server, status " + status
							+ "\n" + this.getResponseContent() + "\n"
							+ "fields " + this.getResponseFields());
				}
			}
		};
		exchange.setRequestHeader("USERID", ""+userId);
		
		exchange.setRequestContentType("text/xml");
		
				exchange.setMethod("POST");
		String postURL = "http://" + gameServerAddress + ":" + gameServerPort
				+ gamesPath+"/"+gameName;
		exchange.setURL(postURL);
		LOG.debug("post URL = " + postURL);

		exchange.setRequestContent(new ByteArrayBuffer(gameXMLBytes));
		LOG.debug("postGames request Content:\n" + exchange.getRequestContent());

		try {
			client.send(exchange);
		} catch (IOException e) {
			LOG.error(e);
		}
		int  exchangeState=-1;
		try {
			exchangeState=exchange.waitForDone();
		} catch (InterruptedException e) {
			LOG.error(e);
		}
		int status= 0;
		if (exchangeState == HttpExchange.STATUS_COMPLETED) {
			status=exchange.getResponseStatus();
			 LOG.debug("games saved successfully:"+ status);
		}else{
			LOG.debug("games couldn't saved");
		}
		
	}
	
	public String getPlayerGames(){
		String playerGames = new String();
		LOG.debug("start getPlayerGames");

		ContentExchange exchange = new ContentExchange(true) {
			protected void onResponseComplete() throws IOException {
				int status = getResponseStatus();
				if (status == 200)
					return;
				else {
					LOG.error("error response from server, status " + status
							+ "\n" + this.getResponseContent() + "\n"
							+ "fields " + this.getResponseFields());
				}
			}
		};
		exchange.setMethod("GET");
		exchange.setURL("http://" + gameServerAddress + ":" + gameServerPort
				+ playerGamesPath);
		exchange.addRequestHeader("USERID", ""+this.getUserId());
		try {
			client.send(exchange);
		} catch (IOException e) {
			LOG.error(e);
		}

		int exchangeState = -1;
		try {
			exchangeState = exchange.waitForDone();
		} catch (InterruptedException e) {
			LOG.error(e);
		}
		if (exchangeState == HttpExchange.STATUS_COMPLETED) {
			try {
				playerGames = exchange.getResponseContent();
			} catch (UnsupportedEncodingException e) {
				LOG.error(e);
			}
		}
		LOG.debug("games retrieved successfully");
		
		return playerGames;
	}

	public void postScores(String userName, String gameName, int score) {

		LOG.info("postScores");

		HttpExchange exchange = new HttpExchange();

		exchange.setMethod("POST");
		String postURL = "http://" + gameServerAddress + ":" + gameServerPort + scoresPath;
		exchange.setURL(postURL);

		LOG.debug("post URL = " + postURL);
		LOG.debug(exchange.getRequestContent());

		String scoreStringXML = "<score gameName=" + "\"" + gameName + "\"" + " userName=" + "\"" + userName + "\""
				+ " score=" + "\"" + score + "\"" + "/>";
		
		byte[] scoreByteArray=scoreStringXML.getBytes();
		exchange.setRequestContent(new ByteArrayBuffer(scoreByteArray));
		LOG.debug(exchange.getRequestContent());
		
		try {
			client.send(exchange);
		} catch (IOException e) {
			LOG.error(e);
		}
		try {
			exchange.waitForDone();
		} catch (InterruptedException e) {
			LOG.error(e);
		}
	}

	public String getScore() {
		String scores = new String();

		ContentExchange exchange = new ContentExchange(true) {
			protected void onResponseComplete() throws IOException {
				int status = getResponseStatus();
				if (status == 200)
					return;
				else {
					LOG.error("error response from server, status " + status
							+ "\n" + this.getResponseContent() + "\n"
							+ "fields " + this.getResponseFields());
				}
			}
		};
		exchange.setMethod("GET");
		exchange.setURL("http://" + gameServerAddress + ":" + gameServerPort
				+ scoresPath);

		try {
			client.send(exchange);
		} catch (IOException e) {
			LOG.error(e);
		}

		int exchangeState = -1;
		try {
			exchangeState = exchange.waitForDone();
		} catch (InterruptedException e) {
			LOG.error(e);
		}
		if (exchangeState == HttpExchange.STATUS_COMPLETED) {
			try {
				scores = exchange.getResponseContent();
			} catch (UnsupportedEncodingException e) {
				LOG.error(e);
			}
		}
		return scores;
	}
	
	public void savePlayedGame(String gameName, byte[] gameXMLBytes){
		LOG.info("postSavedGames");
		ContentExchange exchange = new ContentExchange(true) {
			protected void onResponseComplete() throws IOException {
				int status = getResponseStatus();
				if (status == 200)
					return;
				else {
					LOG.error("error response from server, status " + status
							+ "\n" + this.getResponseContent() + "\n"
							+ "fields " + this.getResponseFields());
				}
			}
		};
		exchange.setRequestHeader("USERID", ""+userId);
		
		exchange.setRequestContentType("text/xml");
		
				exchange.setMethod("POST");
		String postURL = "http://" + gameServerAddress + ":" + gameServerPort
				+ playerGamesPath+"/"+gameName;
		exchange.setURL(postURL);
		LOG.debug("post URL = " + postURL);

		exchange.setRequestContent(new ByteArrayBuffer(gameXMLBytes));
		LOG.debug("postGames request Content:\n" + exchange.getRequestContent());

		try {
			client.send(exchange);
		} catch (IOException e) {
			LOG.error(e);
		}
		int  exchangeState=-1;
		try {
			exchangeState=exchange.waitForDone();
		} catch (InterruptedException e) {
			LOG.error(e);
		}
		int status= 0;
		if (exchangeState == HttpExchange.STATUS_COMPLETED) {
			status=exchange.getResponseStatus();
			 LOG.debug("savegames saved successfully:"+ status);
		}else{
			LOG.debug("savegames couldn't be saved");
		}
		
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}
	
/* This main is for testing purposes only */
	public static void main(String[] args){
		testGetPlayerGames();
		testGetCustomGames();
		testSavePlayedGame();
	}
	
	private static void testGetPlayerGames(){
		GameClient gameClient = new GameClient();
		gameClient.setUsername("blah");
		gameClient.setPassword("blah");
		gameClient.setUserId(10);
		LOG.debug("checking get saved games");
		String ret = gameClient.getPlayerGames();
		LOG.debug("getPlayerGames returned " + ret);
	}
	
	private static void testGetCustomGames(){
		GameClient gameClient = new GameClient();
		gameClient.setUsername("blah");
		gameClient.setPassword("blah");
		gameClient.setUserId(10);
		LOG.debug("checking get custom games " + gameClient.getUsername() + " " + gameClient.getUserId());
		String ret = gameClient.getCustomGameNames();
		LOG.debug("getGames returned " + ret);
	}
	
	private static void testSavePlayedGame(){
		GameClient gameClient = new GameClient();
		gameClient.setUsername("blah");
		gameClient.setPassword("blah");
		gameClient.setUserId(10);
		String gameXML = "<testPlayedGameXML>";
		String gameName = "testPlayedGame";
		LOG.debug("checking save played game " + gameClient.getUsername() + " " + gameClient.getUserId() + " " + gameName + " " + gameXML);
		gameClient.savePlayedGame(gameName, gameXML.getBytes());
	}
}
