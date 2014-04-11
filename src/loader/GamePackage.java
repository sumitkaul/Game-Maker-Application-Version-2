package loader;

import java.util.List;

import model.SpriteModel;
import eventlistener.EventListener;

public class GamePackage {

    private List<SpriteModel> spriteList;
    private List<EventListener> eventsForGameController;
    private List<EventListener> eventsForKeyController;
	private String userName;
	private String gameName;

    public GamePackage(List<SpriteModel> spriteList, List<EventListener> eventsForGameController, List<EventListener> eventsForKeyController) {
        this.spriteList = spriteList;
        this.eventsForGameController = eventsForGameController;
        this.eventsForKeyController = eventsForKeyController;
    }

    public List<EventListener> getEventsForGameController() {
        return eventsForGameController;
    }

    public void setEventsForGameController(List<EventListener> eventsForGameController) {
        this.eventsForGameController = eventsForGameController;
    }

    public List<EventListener> getEventsForKeyController() {
        return eventsForKeyController;
    }

    public void setEventsForKeyController(List<EventListener> eventsForKeyController) {
        this.eventsForKeyController = eventsForKeyController;
    }

    public List<SpriteModel> getSpriteList() {
        return spriteList;
    }

    public void setSpriteList(List<SpriteModel> spriteList) {
        this.spriteList = spriteList;
    }

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

}