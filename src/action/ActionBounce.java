package action;

import java.util.ArrayList;
import utility.SpriteList;

import model.SpriteModel;

public class ActionBounce implements GameAction {

	private boolean randomBounce;
	
	public ActionBounce(boolean randomBounce) {
		this.setRandomBounce(randomBounce);
	}
	
    @Override
    public void doAction(SpriteModel spriteModel) {
    	
		double xSpeed = spriteModel.getSpeedX();
		double ySpeed = spriteModel.getSpeedY();

		ArrayList<SpriteModel> spriteModels = (ArrayList<SpriteModel>) SpriteList.getInstance().getSpriteList();

		for(SpriteModel model : spriteModels){
			if(model.equals(spriteModel)) continue;
			if(spriteModel.intersects(model.getBoundingBox())){

				if((model.getPosY()<spriteModel.getPosY()) &&
						(model.getPosY()+model.getHeight()>spriteModel.getPosY()+spriteModel.getHeight())){
					spriteModel.setSpeedX(xSpeed*-1);
				}
				else if((model.getPosX()<spriteModel.getPosX()) &&
						(model.getPosX()+model.getWidth()>spriteModel.getPosX()+spriteModel.getWidth())){
					spriteModel.setSpeedY(ySpeed*-1);
				}
				
			}
		}
	
    }
    
	public boolean isRandomBounce() {
		return randomBounce;
	}

	public void setRandomBounce(boolean randomBounce) {
		this.randomBounce = randomBounce;
	}
}
