package action;

import utility.ScoreDisplay;
import model.SpriteModel;

public class ActionUpdateScore implements GameAction{

	private int scoreDiff;

	public int getScoreDiff() {
		return scoreDiff;
	}
	public void setScoreDiff(int scoreDiff) {
		this.scoreDiff = scoreDiff;
	}
	public ActionUpdateScore(int scoreDiff) 
	{
			this.scoreDiff= scoreDiff;
	}
	@Override
	public void doAction(SpriteModel model) {
		ScoreDisplay.getInstance().updateScore(this.scoreDiff);
	}


}
