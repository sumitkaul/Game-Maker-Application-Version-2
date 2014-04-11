package utility;

import interfaces.Drawable;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JLabel;

public class HighScoreDisplay  implements Drawable{

	private   static HighScoreDisplay sharedScore;
	private boolean isEnabled;
	private int score;
	private int scoreDiff;
	
	private JLabel scoreLabel;
	private static final org.apache.log4j.Logger LOG =  
			org.apache.log4j.Logger.getLogger(HighScoreDisplay.class);
	private BufferedImage image;
	public static HighScoreDisplay getInstance()
	{
		if(sharedScore==null)
		{
			sharedScore= new HighScoreDisplay();			
			//String scoreString = "00";
			sharedScore.scoreLabel = new JLabel(""+getInstance().getScore());
			sharedScore.isEnabled=true;
			sharedScore.scoreLabel.setForeground(Color.blue);
			sharedScore.scoreLabel.setVisible(true);

		}
		return sharedScore;
	}

	public void updateScore(int scoreDiff)
	{
		score += scoreDiff;
		scoreLabel.setText("" + score);
	}

public void draw(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;


		g2.setColor(Color.RED);
		int x = (int) (20 * ResizeHelper.getInstance().getxFactor());
		int y = (int) (20 * ResizeHelper.getInstance().getyFactor());
		int width = (int) (50 * ResizeHelper.getInstance().getxFactor());
		int height = (int) (75 * ResizeHelper.getInstance().getyFactor());
		image=null;
		image= new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB );
		image.getGraphics();
		g2.drawString(sharedScore.scoreLabel.getText(), x, y);
	}


	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
		sharedScore.scoreLabel.setVisible(isEnabled());
	}

	private boolean isEnabled() {

		return isEnabled;
	}
	
	@Override
	public boolean isVisible() {
		return true;
	}
	@Override
	public String getLayer() {
		return Constants.ALL_LAYERS;
	}
	public void reset () {
		score=0;		
		scoreLabel.setText("" + score);
	}
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

}
