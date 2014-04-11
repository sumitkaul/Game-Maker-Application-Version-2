package model;

import java.awt.Rectangle;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import eventlistener.EventListener;
import utility.SpriteList;
import utility.enums.ImageSource;

public class SpriteModel  {

    private double posX, posY, speedX, speedY, width, height, previousX, previousY;
    private boolean visible;
    private String id;
    private String groupId;
    private String imageUrlString;
    private double heading;
    private ArrayList<EventListener> eventListenerList;
    private HashMap<String,Integer> stringToEventMap;
    private String layer;
  

    public SpriteModel(double posX, double posY, double speedX, double speedY, double width, double height, String primaryImageUrlString,String layer) {
        this.posX = posX;
        this.posY = posY;
        this.speedX = speedX;
        this.speedY = speedY;
        this.width = width;
        this.height = height;
        this.imageUrlString = primaryImageUrlString;
        this.visible = true;
        this.heading = 0;
        this.layer= layer;
        URL url = SpriteModel.class.getResource("/images/closed.png");
       
        int spriteCount = SpriteList.getInstance().getSpriteList().size();
        setId("Untitled "+spriteCount);
        setGroupId("Default Group 0");
        
        this.setEventListenerList(new ArrayList<EventListener>());
        setStringToEventMap(new HashMap<String,Integer>());
    }

    
    public Rectangle getBoundingBox() {
        return new Rectangle((int) (posX),
                (int) (posY ),
                (int) (width ),
                (int) (height));
    }

    public boolean intersects(Rectangle rect) {
		Rectangle gameRect = new Rectangle((int)this.getPosX(), (int)this.getPosY(), (int)this.getWidth(), (int)this.getHeight());
		return gameRect.intersects(rect);
	}
    
    public boolean intersectsAny(List<SpriteModel> spriteModels, String collidingGroupId){
		for(SpriteModel model : spriteModels){
			if(model.equals(this)) continue;
			if(this.intersects(model.getBoundingBox())){
				if((model.getGroupId().equalsIgnoreCase(collidingGroupId)) &&
						(model.getLayer().equalsIgnoreCase(this.getLayer()))){
					return true;
				}
			}
		}
		return false;
	}

    public double getWidth() {
        return this.width;
    }

    
    public double getHeight() {
        return this.height;
    }

    
    public double getPosX() {
        return this.posX;
    }

    
    public void setPosX(double posX) {
        previousX = this.posX;
        this.posX = posX;

    }

    
    public double getPosY() {
        return this.posY;
    }

    
    public void setPosY(double posY) {
        previousY = this.posY;
        this.posY = posY;

    }

    
    public double getSpeedX() {
        return this.speedX;
    }

    
    public void setSpeedX(double speedX) {
    	if(!this.groupId.equalsIgnoreCase("Bomb"))
    		this.speedX = speedX;

    }

    
    public double getSpeedY() {
        return this.speedY;
    }

    
    public void setSpeedY(double speedY) {
    	if(!this.groupId.equalsIgnoreCase("Bomb"))
    		this.speedY = speedY;

    }

    
    public void setWidth(double i) {
        this.width = i;

    }

    
    public void setHeight(double i) {
        this.height = i;

    }

    
    public boolean isVisible() {
        return visible;
    }

    
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    
    public String getGroupId() {
        return groupId;
    }

    
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    
    public String getId() {
        return id;
    }

    
    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrlString() {
        return imageUrlString;
    }

    public void setImageUrlString(String imageUrlString) {
        this.imageUrlString = imageUrlString;
    }

    
    public double getPreviousX() {
        return previousX;
    }

    
    public void setPreviousX(double previousX) {
        this.previousX = previousX;
    }

    
    public double getPreviousY() {
        return previousY;
    }

    
    public void setPreviousY(double previousY) {
        this.previousY = previousY;
    }

	public ArrayList<EventListener> getEventListenerList() {
		return eventListenerList;
	}

	public void setEventListenerList(ArrayList<EventListener> eventListenerList) {
		this.eventListenerList = eventListenerList;
	}

	public HashMap<String,Integer> getStringToEventMap() {
		return stringToEventMap;
	}

	public void setStringToEventMap(HashMap<String,Integer> stringToEventMap) {
		this.stringToEventMap = stringToEventMap;
	}


	public double getHeading() {
		return heading;
	}


	public void setHeading(double heading) {
		this.heading = heading%360;
	}

	public String getLayer() {
		return layer;
	}


	public void setLayer(String layer) {
		this.layer = layer;
	}
}
