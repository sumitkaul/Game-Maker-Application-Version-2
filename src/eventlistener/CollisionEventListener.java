package eventlistener;

import action.GameAction;
import java.util.HashMap;
import java.util.List;
import utility.SpriteList;
import model.SpriteModel;

public class CollisionEventListener implements EventListener {
	private static final org.apache.log4j.Logger LOG = 
			org.apache.log4j.Logger.getLogger(CollisionEventListener.class);
    private GameAction action;
    private String registeredObjectId1, registeredObjectId2, registeredGroupId1, registeredGroupId2;
   
    
    @Override
    public void checkEvent(HashMap<String,Object> map) {
  
        List<SpriteModel> allSprites = SpriteList.getInstance().getSpriteList();
        
        for (SpriteModel model : allSprites) {
        	if(!model.getGroupId().equalsIgnoreCase(registeredGroupId1))
        		continue;
        	if(model.intersectsAny(allSprites,registeredGroupId2)){
        		LOG.debug(model.getId() + " collides... action:" + action);
				action.doAction(model);
			}
		}
    }
   
    public String getRegisteredGroupId1() {
        return registeredGroupId1;
    }
    
    public void setRegisteredGroupId1(String registeredGroupId1) {
        this.registeredGroupId1 = registeredGroupId1;
    }
    
    public String getRegisteredGroupId2() {
        return registeredGroupId2;
    }
    
    public void setRegisteredGroupId2(String registeredGroupId2) {
        this.registeredGroupId2 = registeredGroupId2;
    }
    
    public String getRegisteredObjectId1() {
        return registeredObjectId1;
    }
    
    public void setRegisteredObjectId1(String registeredObjectId1) {
        this.registeredObjectId1 = registeredObjectId1;
    }
    
    public String getRegisteredObjectId2() {
        return registeredObjectId2;
    }
    
    public void setRegisteredObjectId2(String registeredObjectId2) {
        this.registeredObjectId2 = registeredObjectId2;
    }
    
    @Override
	public int getEventId() {
		return this.hashCode();
	}

	public GameAction getAction() {
		return action;
	}

	public void setAction(GameAction action) {
		this.action = action;
	}
}
