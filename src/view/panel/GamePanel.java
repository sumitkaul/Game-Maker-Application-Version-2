package view.panel;
import interfaces.Drawable;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import model.SpriteModel;
import utility.ClockDisplay;
import utility.Constants;
import utility.ScoreDisplay;
import utility.SpriteList;
import view.Design;
import view.SpriteView;


public class GamePanel extends JPanel implements KeyListener{

	private static final org.apache.log4j.Logger LOG = 
			org.apache.log4j.Logger.getLogger(SpriteList.class);
    private static final long serialVersionUID = 1L;
    private Image image;
    private Graphics graphic;
    private List<Drawable> drawables;
    private String currentLayer;
   
    BindMouseMove movingAdapt = new BindMouseMove();
	ResizeHandler gameobjectResize = new ResizeHandler();

    public GamePanel(double width, double height) {
        this.setSize((int)(width), (int)(height));
        this.drawables = new ArrayList<Drawable>(10);
        this.currentLayer=Constants.ALL_LAYERS;
        addMouseMotionListener(movingAdapt);
        addMouseListener(movingAdapt);
        addMouseWheelListener(gameobjectResize);
        addKeyListener(this);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.getSize().width, this.getSize().height);
        
        
        
        if(currentLayer.equalsIgnoreCase(Constants.ALL_LAYERS))
        {
        	for (Drawable d : drawables) {
                if (d.isVisible() ) {
                    d.draw(g);
                    ClockDisplay.getInstance().draw(g);
                    ScoreDisplay.getInstance().draw(g);
                }
            }
        }
        else{
	        for (Drawable d : drawables) {
	            if (d.isVisible() && d.getLayer().equalsIgnoreCase(getCurrentLayer())) {
	                d.draw(g);
	                ClockDisplay.getInstance().draw(g);
	                ScoreDisplay.getInstance().draw(g);
	            }
	        }
        }
    }

    @Override
    public void update(Graphics g) {
        if (image == null) {
            image = createImage(this.getSize().width, this.getSize().height);
            graphic = image.getGraphics();
        }

        graphic.setColor(this.getBackground());
        graphic.fillRect(0, 0, this.getSize().width, this.getSize().height);
        graphic.setColor(this.getForeground());

        paint(graphic);
        g.drawImage(image, 0, 0, this);
    }
    
    public void unregisterModel(SpriteModel model) {
    	ArrayList<Drawable> toBeRemoved = new ArrayList<Drawable>();
    	for(Drawable drawable : drawables){
    		if(drawable instanceof SpriteView){
    			SpriteView view = (SpriteView) drawable;
        		if(view.getModel().equals(model))
        			toBeRemoved.add(drawable);
    		}
    	}
    		
    	for(Drawable drawable : toBeRemoved)
    		unregisterDrawable(drawable);
    }

    public void registerDrawable(Drawable d) {
        this.drawables.add(d);
    }

    public void unregisterDrawable(Drawable d) {
        this.drawables.remove(d);
    }

    public void removeAllDrawables() {
        this.drawables.clear();
    }
    

    class BindMouseMove extends MouseAdapter {
	    private double x;
	    private double y;
	    @Override
	    public void mousePressed(MouseEvent event) {

	    	
	    	if(Design.getInstance().getFacade().getTimer().isRunning())
	    		return;
	    	
	    	x = event.getX();
	    	y = event.getY();

	    	boolean foundObject = false;
	    	List<SpriteModel> spriteModels = SpriteList.getInstance().getSpriteList();
	    	SpriteModel selectedSpriteModel = null;
	    	for(SpriteModel model:spriteModels){
	    		if (model.getBoundingBox().contains(x, y)){
	    			selectedSpriteModel = model;
	    			FieldPanel.getInstance().updateProperties();
	    			foundObject = true;
	    		}
	    		else{
	    			if(!foundObject)
	    				FieldPanel.getInstance().clearAll();
	    		}
	    	}


	    	SpriteList.getInstance().setSelectedSpriteModel(selectedSpriteModel);

	    	double clickCount = event.getClickCount();
	    	if(clickCount == 2){
	    		if(foundObject)
	    			Design.getInstance().createDuplicateSpriteModel(selectedSpriteModel);
	    	}

	    	requestFocus();
	    }
	    
	    
	    	@Override
	    	public void mouseDragged(MouseEvent event) {
	    		double dx = event.getX() - x;
	    		double dy = event.getY() - y;
	    		
	    		
	    		SpriteModel selectedGameObject = SpriteList.getInstance().getSelectedSpriteModel();
	    		if(selectedGameObject == null)
	    			return;

	    		double tempx = selectedGameObject.getPosX();
    			double tempy = selectedGameObject.getPosY();
    			
	    		double gameBoardWidth = Design.getInstance().getGamePanel().getWidth();
	    		double gameBoardHeight = Design.getInstance().getGamePanel().getHeight();
	    		
	    		double centerX = (double) (tempx+selectedGameObject.getWidth()/2);
    			double centerY = (double) (tempy+selectedGameObject.getHeight()/2);
	    		
	    		if(selectedGameObject.getWidth() < gameBoardWidth &&
	    				selectedGameObject.getHeight() < gameBoardHeight){

		    		if(centerX >= gameBoardWidth || centerX <= 0)
		    			tempx = selectedGameObject.getPreviousX();
		    		else
		    			tempx += dx;
		    		
		    		if(centerY >= gameBoardHeight || centerY <= 0)
		    			tempy = selectedGameObject.getPreviousY();
		    		else
		    			tempy += dy;
		    			
					
	    		}
	    		else{
	    			tempx = selectedGameObject.getPosX();
					tempx += dx;
					
					tempy = selectedGameObject.getPosY();
					tempy += dy;
	    		}
	    		
	    		selectedGameObject.setPosX(tempx);
	    		selectedGameObject.setPosY(tempy);
				
				LOG.info("In Mouse Dragged if condition");
				repaint();
				
				//if(x+selectedGameObject.getWidth()/2<=Design.getInstance().getGamePanel().getWidth())
				x += dx;
				//if(y+selectedGameObject.getHeight()/2<=Design.getInstance().getGamePanel().getHeight())
	    		y += dy;

	    	}
	}
	
	class ResizeHandler implements MouseWheelListener {
		@Override
	    public void mouseWheelMoved(MouseWheelEvent e) 
		{
	        double x = e.getX();
	        double y = e.getY();
	        double tempheight;
	        double tempwidth;
	        if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) 
	        {
	        	List<SpriteModel> gameObjects = SpriteList.getInstance().getSpriteList();
	    		for(SpriteModel gameObject:gameObjects)
	    		{		
	 
	    			if (gameObject.getBoundingBox().contains(x, y)) 
	    			{
	    				float amount = e.getWheelRotation() * 5f;
	    				tempwidth = gameObject.getWidth();
	    				tempwidth += amount;
	    				gameObject.setWidth(tempwidth);
	    				
	    				tempheight = gameObject.getHeight();
	    				tempheight += amount;
	    				gameObject.setHeight(tempheight);
	    				
	    				repaint();
	    				
	    				FieldPanel.getInstance().updateProperties();
	    			}
	    		}
	        }
	    }
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
	
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void keyTyped(KeyEvent arg0) {
		if(arg0.getKeyChar() == 'r'){
			Design.getInstance().removeSpriteModelFromList(SpriteList.getInstance().getSelectedSpriteModel());
			SpriteList.getInstance().removeSprite(SpriteList.getInstance().getSelectedSpriteModel());
			Design.getInstance().getGamePanel().repaint();
		}
		
	}

	public String getCurrentLayer() {
		return this.currentLayer;
	}

	public void setCurrentLayer(String currentLayer) {
		this.currentLayer = currentLayer;
	}
}
