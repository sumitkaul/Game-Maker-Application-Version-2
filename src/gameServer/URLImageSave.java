package gameServer;

public class URLImageSave {

	private URLImages urlImages;
	
	public URLImageSave(URLImages urlImages)
	{
		this.urlImages=urlImages;
	}

	public int saveWebImage(URLImages urlImages) {
		int status=DatabaseSession.getInstance().saveObject(urlImages);
		return status;
	}
}
