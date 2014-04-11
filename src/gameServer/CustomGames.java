package gameServer;


import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Transient;


@Entity
public class CustomGames {
	
	@Id
	@GeneratedValue
	private int gameid;
	 
	private int userid;
	private String gamename;

	private String savedgame;
	@Transient
	private ArrayList<String> gameList;

	
	public CustomGames()
	{
		this.gamename=null;
		this.savedgame=null;
		this.userid=0;
		this.gameid=0;
	}
	
	public void save() {
		DatabaseSession.getInstance().saveObject(this);
	}
	
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getGamename() {
		return gamename;
	}
	public void setGamename(String gamename) {
		this.gamename = gamename;
	}
	public int getGameid() {
		return gameid;
	}
	public void setGameid(int gameid) {
		this.gameid = gameid;
	}
	public String getSavedgame() {
		return savedgame;
	}
	public void setSavedgame(String savedgame) {
		this.savedgame = savedgame;
	}
	public ArrayList<String> getGameList() {
		return gameList;
	}
	public void setGameList(ArrayList<String> gameList) {
		this.gameList = gameList;
	}
	
	

}
