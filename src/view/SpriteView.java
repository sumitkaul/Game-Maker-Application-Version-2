package view;

import interfaces.Drawable;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import utility.ResizeHelper;

import model.SpriteModel;

public class SpriteView implements Drawable {

	private BufferedImage image;
	private SpriteModel model;

	public SpriteView(SpriteModel model) {
	        try {

	            //image = ImageIO.read(new File(model.getImageUrlString()));
	            this.image = ImageIO.read(getClass().getResourceAsStream(model.getImageUrlString()));
	            this.model = model;
	        } catch (IOException e) {
	            e.printStackTrace();
	            //log exception
	        }
	    }
    
	 @Override
	    public void draw(Graphics g) {
		 
		 Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			
			int x = (int) (model.getPosX() * ResizeHelper.getInstance().getxFactor());
			int y = (int) (model.getPosY() * ResizeHelper.getInstance().getyFactor());
			int width = (int) (model.getWidth() * ResizeHelper.getInstance().getxFactor());
			int height = (int) (model.getHeight() * ResizeHelper.getInstance().getyFactor());
			
			g2.rotate(Math.toRadians(model.getHeading()), x+width/ 2, y+height/ 2);
	        g2.drawImage(image,x, y,width,height,null);
	        g2.rotate(Math.toRadians(-model.getHeading()), x+width/ 2, y+height/ 2);
	    }

    @Override
    public boolean isVisible() {
        return model.isVisible();
    }
    
    public SpriteModel getModel() {
		return model;
	}

	public void setModel(SpriteModel model) {
		this.model = model;
	}

	@Override
	public String getLayer() {
		// TODO Auto-generated method stub
		return this.model.getLayer();
	}
	

}
