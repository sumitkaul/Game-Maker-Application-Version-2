package view.panel;

import gameServer.URLImagesGetter;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.vladium.emma.report.SrcFileItem;

import model.SpriteModel;

import testClient.GameClient;
import utility.Constants;
import utility.Helper;
import view.Design;

public class ImagesScrollPanel extends JPanel implements ActionListener,MouseListener{
	List<String> imageNames ;
	//ImageActionListener listener ;
	private static final org.apache.log4j.Logger LOG = 
			org.apache.log4j.Logger.getLogger(Design.class);


	public ImagesScrollPanel() {

	}
	public JScrollPane getImagePanel() {
		JPanel imagePanel = new JPanel();

		List<String> imageNames = Helper.getsharedHelper().getListFromJar("images.jar");

		//ImageActionListener listener = this.new ImageActionListener();
		for(String entryName:imageNames){

			//			LOG.info(entryName);
			Image image = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource(entryName));
			ImageIcon icon = new ImageIcon(image);

			icon = new ImageIcon(image.getScaledInstance(Constants.DEFAULT_BUTTONWIDTH, Constants.DEFAULT_BUTTONHEIGHT, 1));

			JButton button = new JButton(icon);
			button.addMouseListener(this);
			button.setName("/"+entryName);
			button.addActionListener(this);
			imagePanel.add(button);
		}
		ArrayList<String> URLs= new ArrayList<String>();
				
	//	String[] webImages =	GameClient.getInstance().viewWebImages(GameClient.getInstance().getUserId()).split(",");
		
		/*for (int i =0; i < webImages.length; i++)
		{
			URLs.add(webImages[i]);
		}
*/
	//	ArrayList<String> URLs=(new URLImagesGetter()).GetImages(GameClient.getInstance().getUserId());
		for(String urlname:URLs) {
	
		URL url=null;
		try {
			url = new URL(urlname);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Image image = null;
		try {
			image = ImageIO.read(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ImageIcon icon = new ImageIcon(image);

		icon = new ImageIcon(image.getScaledInstance(Constants.DEFAULT_BUTTONWIDTH, Constants.DEFAULT_BUTTONHEIGHT, 1));

		JButton button = new JButton(icon);
		button.addMouseListener(this);
		button.setName("u"+urlname);
		button.addActionListener(this);
		imagePanel.add(button);
		}


		JScrollPane scroller = new JScrollPane(imagePanel,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		//scroller.setMaximumSize(scroller.getSize());
		scroller.setPreferredSize(new Dimension(Constants.MAGNIFIED_BUTTONWIDTH + Constants.MARGINE , Constants.MAGNIFIED_BUTTONHEIGHT+ Constants.MARGINE));
		return scroller;
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {


		JButton btn = (JButton) arg0.getSource();

		double sizeX;
		double sizeY;
		double speedX;
		double speedY;
		String layer = null;
		if(FieldPanel.getInstance().getLayerBox().getSelectedItem().equals(Constants.ALL_LAYERS))
		{
			JOptionPane.showMessageDialog(Design.getInstance().getBaseFrame(), Constants.SELECT_LAYER);
		}else{
			try {
				sizeX = Double.valueOf(FieldPanel.getInstance().getWidthTextField().getText());
				sizeY = Double.valueOf(FieldPanel.getInstance().getHeightTextField().getText());
				LOG.debug("got size values for customObject x:" + sizeX + " y:"
						+ sizeY);


			} catch (Exception exception) {
				LOG.error("did not read in size values ... using defaults");
				sizeX = 30;
				sizeY = 30;
			}
			try {
				speedX = Double.valueOf(FieldPanel.getInstance().getVelocityXTextField().getText());
				speedY = Double.valueOf(FieldPanel.getInstance().getVelocityYTextField().getText());

				LOG.debug("got speed values for customObject x:" + speedX
						+ " y:" + speedY);
			} catch (Exception exception) {
				LOG.error("did not read in speed values ... using defaults");
				speedX = 1;
				speedY = 1;
			}
			try
			{
				layer=FieldPanel.getInstance().getLayerBox().getSelectedItem().toString();
			}
			catch(Exception exception)
			{
				LOG.error("layer value not set", exception);
			}
			if (btn != null) {
				// TO-DO : To get two images while importing objects. So
				// that the second object can be used as a secondary image
				// based on requirements.
				SpriteModel spriteModel = new SpriteModel(100, 100, speedX,
						speedY, sizeX, sizeY, btn.getName(),layer);
				Design.getInstance().updateSpriteList(spriteModel);
				FieldPanel.getInstance().updateProperties();
				Design.getInstance().getFacade().addSpriteModelToView(spriteModel);
				Design.getInstance().getGamePanel().repaint();
			}
		}


	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseEntered(MouseEvent e) {
		JButton button =	(JButton) e.getSource();
		changeButtonSize(button, Constants.MAGNIFIED_BUTTONWIDTH, Constants.MAGNIFIED_BUTTONHEIGHT);
	}
	@Override
	public void mouseExited(MouseEvent e) {
		JButton button =	(JButton) e.getSource();
		changeButtonSize(button, Constants.DEFAULT_BUTTONWIDTH, Constants.DEFAULT_BUTTONHEIGHT);
	}
	@Override
	public void mousePressed(MouseEvent e) {

	}
	@Override
	public void mouseReleased(MouseEvent e) {

	}

	public void changeButtonSize(JButton button , int width  , int height)
	{
		String strImagePath = null ;
		Image image = null;
		URL url = null;
		if(button.getName().substring(0, 1).equals("u"))
		{
			try {
				url = new URL(button.getName().substring(1));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			try {
				image = ImageIO.read(url);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			strImagePath = button.getName().substring(1);
			image = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource(strImagePath));
		}
		
		ImageIcon icon = new ImageIcon(image.getScaledInstance(width, height, 1));
		button.setIcon(icon);
		button.setSize(width, height);
	}

}
