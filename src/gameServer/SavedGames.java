package gameServer;
import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class SavedGames implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private int userid;
	@Id
	private int gameid;
	@Id
	private String savedgamename;
	private String savedgameXML;
	private int score;
	@Transient
	private ArrayList<String> gameList;

	public SavedGames()
	{
		this.userid=0;
		this.gameid=0;
		this.savedgamename=null;
		this.savedgameXML=null;
		this.score=0;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public int getGameid() {
		return gameid;
	}
	public void setGameid(int gameid) {
		this.gameid = gameid;
	}
	public String getSavegamename() {
		return savedgamename;
	}
	public void setSavegamename(String savegamename) {
		this.savedgamename = savegamename;
	}
	public String getSavedgameXML() {
		return savedgameXML;
	}
	public void setSavedgameXML(String savedgameXML) {
		this.savedgameXML = savedgameXML;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public ArrayList<String> getGameList() {
		return gameList;
	}
	public void setGameList(ArrayList<String> gameList) {
		this.gameList = gameList;
	}
}
