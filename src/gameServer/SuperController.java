package gameServer;

import java.util.ArrayList;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.ResponseStatus;

import testClient.GameClient;
import utility.HighScoreDisplay;
import utility.enums.QueryType;



@Controller
public class SuperController {
	private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
			.getLogger(SuperController.class);
	private String sep = "%^";
	private String lineSeparator = "#";
	
	public SuperController(){
	}
	
	@RequestMapping(value="/customgames/{gameName}", method = RequestMethod.GET)
	public ResponseEntity<String> getCustomGames(@PathVariable String gameName,HttpEntity<byte[]> requestEntity){
		//UserInfo user = getUser(requestEntity);
		
	
		LOG.info("GET /games userId:"+gameName);
		//String gamename= reqHeader.get;
		CustomGames customGames= new CustomGames();
		customGames.setGamename(gameName);
		
		@SuppressWarnings("unchecked")
		CustomGames customGamesWithXML = (CustomGames) performQuery(QueryType.LOADMAKER,   customGames); 	
		
		String responseBodyString = customGamesWithXML.getSavedgame();
//		for(String game : gamesList){
//			responseBodyString += game+sep;
//		}
//		responseBodyString += "</Games>";
		LOG.info("Game list response: "+responseBodyString);
		return new ResponseEntity<String>(responseBodyString, HttpStatus.OK);
	}
	
	@RequestMapping(value="/games", method = RequestMethod.GET)
	public ResponseEntity<String> getCustomGameNameList(HttpEntity<byte[]> requestEntity){
		//UserInfo user = getUser(requestEntity);
		
		HttpHeaders reqHeader=requestEntity.getHeaders();
		
		
		
		CustomGames customGames= new CustomGames();
		//customGames.setGamename(gameName);
		
		@SuppressWarnings("unchecked")
		ArrayList<String> gamesList = (ArrayList<String>) performQuery(QueryType.GETMAKERGAMES,   customGames); 	
		
		String responseBodyString = "<Games>"+sep;
		for(String game : gamesList){
			responseBodyString += game+sep;
		}
		responseBodyString += "</Games>";
		LOG.info("Game list response: "+responseBodyString);
		return new ResponseEntity<String>(responseBodyString, HttpStatus.OK);
	}
	
	@RequestMapping(value="/games/{gameName}", method = RequestMethod.POST)
	public ResponseEntity<String> saveGames(@PathVariable String gameName, HttpEntity<byte[]> requestEntity){
		HttpHeaders headers=requestEntity.getHeaders();
		String id=headers.get("USERID").get(0);
		String gameXml = new String(requestEntity.getBody());
		CustomGames customGame = new CustomGames();
		customGame.setGamename(gameName);
		customGame.setUserid(Integer.parseInt(id));
		customGame.setSavedgame(gameXml);
		performQuery(QueryType.SAVEMAKER, customGame);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	

	@RequestMapping(value="/games/{gameName}", method = RequestMethod.GET)
	public ResponseEntity<String> getCustomGame(@PathVariable String gameName, HttpEntity<byte[]> requestEntity)
	{
		int userId=getUserId(requestEntity);
		LOG.info("userId:"+userId);
		CustomGames customGame = new CustomGames();
		customGame.setGamename(gameName);
		
		CustomGames getgame=(CustomGames)performQuery(QueryType.LOADMAKER,customGame);
		String customGameXMl=getgame.toString();
		 return new ResponseEntity<String>(customGameXMl,HttpStatus.OK);
		 }

	@RequestMapping(value="/scores", method = RequestMethod.POST)
	public ResponseEntity<String> sendScores(HttpEntity<byte[]> requestEntity){
		HttpHeaders reqHeader=requestEntity.getHeaders();
		String userValues=reqHeader.get("SendScores").get(0);
		String[] valueArray=userValues.split(":");
		//String gameXml = new String(requestEntity.getBody());
		Scores scores= new Scores();
		scores.setScore(Integer.parseInt(valueArray[2]));
		scores.setUserid(Integer.parseInt(valueArray[0]));
		//scores.setGameid(10);
		scores.setGameid(Integer.parseInt(valueArray[1]));
		performQuery(QueryType.SENDSCORE, scores);
		return new ResponseEntity<String>(HttpStatus.OK);

	}
	
	@RequestMapping(value="/webimagesPath", method = RequestMethod.POST)
	public ResponseEntity<String> sendWebImages(HttpEntity<byte[]> requestEntity){
		HttpHeaders reqHeader=requestEntity.getHeaders();
		String userValues=reqHeader.get("SendWebimages").get(0);
		String[] valueArray=userValues.split(":");
		URLImages urlimages= new URLImages();
		urlimages.setUrl((valueArray[1]));
		urlimages.setUserid(Integer.parseInt(valueArray[0]));
		performQuery(QueryType.SETWEBIMAGES, urlimages);
		return new ResponseEntity<String>(HttpStatus.OK);

	}
	
	@RequestMapping(value="/getwebimagesPath", method = RequestMethod.GET)
	public ResponseEntity<String> viewWebImages(HttpEntity<byte[]> requestEntity){
		HttpHeaders reqHeader=requestEntity.getHeaders();
		String userValues=reqHeader.get("GetWebimages").get(0);
		URLImages urlimages= new URLImages();
		urlimages.setUserid(Integer.parseInt(userValues));
		@SuppressWarnings("unchecked")
		ArrayList<URLImages> webimages= (ArrayList<URLImages>)performQuery(QueryType.GETWEBIMAGES, urlimages);

		String responseBodyString = "<Webimages>" + sep;
		for(URLImages webimageObject : webimages){
			responseBodyString+=responseBodyString+webimageObject.getUrl()+",";
		}
		responseBodyString += "</Webimages>";
		LOG.info("Web image response: "+responseBodyString);
		return new ResponseEntity<String>(responseBodyString,HttpStatus.OK);

	}
	
	@RequestMapping(value="/highscores", method = RequestMethod.GET)
	public ResponseEntity<String> viewHighScores(HttpEntity<byte[]> requestEntity){
		HttpHeaders reqHeader=requestEntity.getHeaders();
		String userValues=reqHeader.get("SendScores").get(0);
		String[] valueArray=userValues.split(":");
		//String gameXml = new String(requestEntity.getBody());
		Scores scores= new Scores();
		scores.setScore(Integer.parseInt(valueArray[2]));
		scores.setUserid(Integer.parseInt(valueArray[0]));
		//scores.setGameid(10);
		scores.setGameid(Integer.parseInt(valueArray[1]));
		ArrayList<Scores> scoreList= (ArrayList<Scores>)performQuery(QueryType.HIGHSCORE, scores);

		String responseBodyString = "<Scores>" + sep;
		for(Scores scoresObject : scoreList){
			responseBodyString+=responseBodyString+scoresObject.getUserid()+":"+scoresObject.getScore()+",";
		}
		responseBodyString += "</Scores>";
		LOG.info("Game list response: "+responseBodyString);
		return new ResponseEntity<String>(responseBodyString,HttpStatus.OK);

	}
	
	@RequestMapping(value="/login", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> login(HttpEntity<byte[]> requestEntity){
		LOG.info("GET /login");
		UserInfo user = getUser(requestEntity);
		return checkLogin(user);
	}
	
	@RequestMapping(value="/login", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> signUp(HttpEntity<byte[]> requestEntity){
		LOG.info("POST /login");
		UserInfo user = getUser(requestEntity);
		if(user.isValid()){
			return new ResponseEntity<String>(HttpStatus.CONFLICT);
		}
		else {
			int userid = (Integer)performQuery(QueryType.REGISTER, user);
			return new ResponseEntity<String>("" + userid, HttpStatus.OK);
		}
	}
	
	@RequestMapping(value="/savedgames", method = RequestMethod.GET)
	public ResponseEntity<String> getSavedGames(HttpEntity<byte[]> requestEntity){
		int userId=getUserId(requestEntity);
		LOG.info("GET /savedgames userId:"+userId);
		SavedGames savedGames = new SavedGames();
		savedGames.setUserid(userId);
		
		@SuppressWarnings("unchecked")
		ArrayList<String> gamesList = (ArrayList<String>) performQuery(QueryType.GETPLAYERGAMES, savedGames); 	
		
		String responseBodyString = "<savedgames>" + sep;
		for(String game : gamesList){
			responseBodyString += game + sep;
		}
		responseBodyString += "</savedgames>";
		LOG.info("Game list response: "+responseBodyString);
		return new ResponseEntity<String>(responseBodyString, HttpStatus.OK);
	}
	
	@RequestMapping(value="/savedgames/{gameName}", method = RequestMethod.POST)
	public ResponseEntity<String> postSavedGame(@PathVariable String gameName, HttpEntity<byte[]> requestEntity){
		HttpHeaders headers=requestEntity.getHeaders();
		String id=headers.get("USERID").get(0);
		String gameXml = new String(requestEntity.getBody());
		SavedGames savedGames = new SavedGames();
		savedGames.setSavegamename(gameName);
		savedGames.setUserid(Integer.parseInt(id));
		savedGames.setSavedgameXML(gameXml);
		performQuery(QueryType.SAVEPLAYER, savedGames);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	/***************** Helper Methods  ******************/
	private ResponseEntity<String> checkLogin(UserInfo user){
		if(user.isValid()){
			LOG.debug("checkLogin returns OK, user:" + user.getUsername() + " password:" + user.getPassword());
			String userInfo = ""+user.getUserid();
			return new ResponseEntity<String>(userInfo,HttpStatus.OK);
		}
		else {
			LOG.debug("checkLogin returns UNAUTHORIZED, user:" + user.getUsername() + " password:" + user.getPassword());
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		}
	}
	
	private int getUserId(HttpEntity<byte[]> requestEntity)
	{
		HttpHeaders requestHeader=requestEntity.getHeaders();
		String userInfo=requestHeader.get("USERID").get(0);
		int userId= Integer.parseInt(userInfo);
		return userId;
	}
	private UserInfo getUser(HttpEntity<byte[]> requestEntity){
		String username, password;
		try {
		HttpHeaders requestHeaders = requestEntity.getHeaders();
		LOG.debug("request Headers:" + requestHeaders);
		String credentials = requestHeaders.get("Authorization").get(0);
		String[] credentialsArray = credentials.split(":");
		username = credentialsArray[0];
		password = credentialsArray[1];
		}
		catch(NullPointerException e){
			UserInfo user = new UserInfo();
			user.setValid(false);
			return user;
		}
		UserInfo userInfo = new UserInfo();
		userInfo.setPassword(password);
		userInfo.setUsername(username);
		LOG.debug("user:" + username + " password:" + password);
		performQuery(QueryType.LOGIN, userInfo);
		return userInfo;
	}
	
	
    private Object performQuery(QueryType type, Object dataModel)
    {
    	Object returnObject= null;
    	if (type == QueryType.LOGIN)
    	{
    		Login login =new Login((UserInfo) dataModel);
    		 returnObject = (Object)login.validateLogin((UserInfo) dataModel);
    	}
    	if (type == QueryType.GETUSERNAME)
    	{
    		Login login =new Login((UserInfo) dataModel);
    		 returnObject = (Object)login.getIDFromName((UserInfo) dataModel);
    	}
    	else if(type == QueryType.REGISTER)
    	{
    		Register register = new Register((UserInfo) dataModel);
    		returnObject = (Integer)register.registerUser((UserInfo) dataModel);
    	}
    	else if (type == QueryType.LOADMAKER)
    	{
    		LoadMaker loadMaker = new LoadMaker((CustomGames) dataModel);
    		returnObject = (Object) loadMaker.getSavedGame((CustomGames) dataModel);
    	}
    	else if (type == QueryType.LOADPLAYER)
    	{
    		LoadPlayer loadPlayer = new LoadPlayer((SavedGames) dataModel);
    		returnObject = (Object) loadPlayer.getSavedGame((SavedGames) dataModel);
    	}
    	else if (type == QueryType.SAVEMAKER)
    	{
    		SaveMaker saveMaker = new SaveMaker((CustomGames) dataModel);
    		 returnObject = (Object)saveMaker.saveMakerGame((CustomGames) dataModel);
    		
    	}
    	else if (type == QueryType.SAVEPLAYER)
    	{
    		SavePlayer savePlayer = new SavePlayer((SavedGames) dataModel);
   		 returnObject = (Object)savePlayer.savePlayerGame((SavedGames) dataModel);
    		
    	}
    	else if (type == QueryType.SENDSCORE)
    	{
    		SendScore sendScore = new SendScore((Scores) dataModel);
      		 returnObject = (Object)sendScore.publishScore((Scores) dataModel);
    	}
    	else if (type == QueryType.HIGHSCORE)
    	{
    		HighScore sendScore = new HighScore((Scores) dataModel);
     		 returnObject = (Object)sendScore.getHighScores((Scores) dataModel);
    	}
    	else if (type == QueryType.GETMAKERGAMES)
    	{
    		LoadMaker loadMaker = new LoadMaker((CustomGames) dataModel);
    		returnObject = (Object) loadMaker.getGameList();
    	}
    	else if (type == QueryType.GETPLAYERGAMES)
    	{
    		LoadPlayer loadPlayer = new LoadPlayer((SavedGames) dataModel);
    		returnObject = (Object) loadPlayer.getGameList();
    	}
    	else if (type == QueryType.SETWEBIMAGES)
    	{
    		URLImageSave urlImageSave = new URLImageSave((URLImages) dataModel);
    		returnObject = (Object) urlImageSave.saveWebImage((URLImages) dataModel);
    	}
    	else if (type == QueryType.GETWEBIMAGES)
    	{
    		URLImagesGetter urlImageGetter = new URLImagesGetter();
    		returnObject = (Object) urlImageGetter.GetImages((URLImages) dataModel);
    	}
    	return returnObject;
    }
	
	
}
