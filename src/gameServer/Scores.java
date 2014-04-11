package gameServer;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import java.io.Serializable;
import java.util.ArrayList;

@Entity
public class Scores implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private int userid;
	@Id
	private int gameid;
	private int score;
	
	 @Transient
	private ArrayList<String> list;
	
	//private static Scores instance =null;
	
//	public static Scores getInstanceOf()
//	{
//		if (instance ==null)
//		{
//			instance = new Scores();	
//		}
//		return instance;
//	}
//	
	public Scores()
	{
		this.userid=0;
		this.gameid=0;
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
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}

	public void setList(ArrayList<String> list) {
		
		this.list = list;
	}
	public ArrayList<String> getList() {
		
		return this.list;
	}
	

}
