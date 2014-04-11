package gameServer;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Transient;

@Entity
public class UserInfo {
	

	  @Id
	  @GeneratedValue
	private int userid;
	private String username;
	private String password;
	
	private static UserInfo userInstance=null;
	
	@Transient
	private boolean isValid;
	
	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	public UserInfo()
	{
		this.username=null;
		this.password=null;
		this.userid=0;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	

}
