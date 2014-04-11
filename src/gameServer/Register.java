package gameServer;

public class Register {
	
	private UserInfo userInfo;
	public Register(UserInfo userInfo)
	{
		this.userInfo=userInfo;
		//registerUser(userInfo);
	}

	public int registerUser(UserInfo userInfo) {
		int status=DatabaseSession.getInstance().saveObject(userInfo);
		return status;
	}

}
