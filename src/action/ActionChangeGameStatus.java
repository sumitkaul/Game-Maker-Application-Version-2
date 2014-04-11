package action;

import gameMaker.Main;

import java.awt.Rectangle;
import java.util.List;

import javax.swing.JOptionPane;

import utility.SpriteList;
import view.Design;

import model.SpriteModel;

public class ActionChangeGameStatus implements GameAction{
	
	private static final org.apache.log4j.Logger LOG = 
			org.apache.log4j.Logger.getLogger(Main.class);

	String message= "";
	
	public ActionChangeGameStatus(boolean isWin) {
		if(isWin)
			message ="You Win";
		else
			message = "You Lose";
	}
	
	
	@Override
	public void doAction(SpriteModel model) {
		double xSpeed = model.getSpeedX();
		double ySpeed = model.getSpeedY();

		Rectangle xReversed = (Rectangle) model.getBoundingBox();
		Rectangle yReversed = (Rectangle) model.getBoundingBox();		
		xReversed.x -= xSpeed;
		yReversed.y -= ySpeed;
		List<SpriteModel> spriteModels = SpriteList.getInstance().getSpriteList();
		for(SpriteModel obj : spriteModels){
			if(obj.equals(model)) continue;
			
			if(model.intersects(obj.getBoundingBox())){
				if(!xReversed.intersects(obj.getBoundingBox())){
					LOG.debug("XReversed");
					JOptionPane.showMessageDialog(null, message);
					Design.getInstance().getFacade().stopGame();
					break;
				}
				else if(!yReversed.intersects(obj.getBoundingBox())){
					LOG.debug("YReversed");
					JOptionPane.showMessageDialog(null, message);
					Design.getInstance().getFacade().stopGame();
					break;
				}
				else if(yReversed.intersects(obj.getBoundingBox())&&xReversed.intersects(obj.getBoundingBox())){
					LOG.debug("XYReversed");
					JOptionPane.showMessageDialog(null, message);
					Design.getInstance().getFacade().stopGame();
					break;
				}
				
			}
		}
	}

}
