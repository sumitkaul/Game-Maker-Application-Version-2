package view;

import facade.Facade;

import interfaces.Resizable;

import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import loader.GameDataPackageIO;
import loader.GamePackage;
import model.SpriteModel;
import utility.ClockDisplay;
import utility.Constants;
import utility.Helper;
import utility.Layers;
import utility.ResizeHelper;
import utility.ScoreDisplay;
import utility.SpriteList;
import utility.enums.LookAndFeelType;
import view.panel.ActionEventPanel;
import view.panel.ButtonPanel;
import view.panel.FieldPanel;
import view.panel.GamePanel;
import view.panel.ImagesScrollPanel;
import javax.swing.UIManager.*;

public class Design implements Resizable, ActionListener {

	private static final org.apache.log4j.Logger LOG = 
			org.apache.log4j.Logger.getLogger(Design.class);

	private static Design sharedDesign = null;

	private Map<String,String> gameXMLMap=new HashMap<String,String>();
	public static AudioClip bounce;
	public static AudioClip brickHit;
	public static AudioClip applause;
	JList sampleXmlList;

	private GamePanel gamePanel; 	// Right view in game maker
	public JPanel controlPanel;		// Left view in game maker
	public JPanel buttonPanel;		// Top part of the left view
	private final JFrame baseFrame;
	private Facade facade;
	private JComboBox themeCombo;
	private JLabel themeLabel;

	private DefaultListModel spriteListIndividualModel;

	private ImagesScrollPanel imagesScrollPanel;
	public DefaultListModel getSpriteListIndividualModel() {
		return spriteListIndividualModel;
	}
	private DefaultListModel spriteListGroupModel;	
	public DefaultListModel getSpriteListGroupModel() {
		return spriteListGroupModel;
	}

	private JTextField userName;
	private JTextField gameName;

	private String layer;
	public String getLayer() {
		return layer;
	}

	public void setLayer(String layer) {
		this.layer = layer;
	}
	private ArrayList scoreList = new ArrayList();
	private ArrayList userList = new ArrayList();
	private ArrayList gameList = new ArrayList();

	private JPanel themePanel;

	public GamePanel getGamePanel() {
		return gamePanel;
	}

	public JFrame getBaseFrame() {
		return baseFrame;
	}

	public void setGamePanel(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}




	public static Design getInstance(){
		if(sharedDesign == null)
			sharedDesign = new Design(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
		return sharedDesign;
	}

	protected Design(int frameWidth, int frameHeight) {

		spriteListIndividualModel = new DefaultListModel();
		spriteListGroupModel = new DefaultListModel();

		userName = new JTextField(20);
		gameName = new JTextField(20);

		//Create a baseframe for the game maker
		baseFrame = new JFrame();

		baseFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		baseFrame.setTitle("Game Maker - Team 2");
		baseFrame.setSize(frameWidth, frameHeight);
		baseFrame.setLayout(new GridLayout(1, 2));

		baseFrame.setMinimumSize(new Dimension(Constants.MINIMUM_FRAMEWIDTH,Constants.MINIMUM_FRAMEHEIGHT));
		baseFrame.setDefaultLookAndFeelDecorated(true);
		baseFrame.getRootPane().addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				int originalFrameWidth = Constants.FRAME_WIDTH;
				int originalFrameHeight = Constants.FRAME_HEIGHT;

				Component rootPane = e.getComponent();
				Rectangle r = rootPane.getBounds();

				double xScale = (double)r.width/originalFrameWidth;
				double yScale = (double)r.height/originalFrameHeight;

				ResizeHelper.getInstance().setxFactor(xScale);
				ResizeHelper.getInstance().setyFactor(yScale);

				gamePanel.repaint();

			}
		});

		//This is the panel where the sprite will be inserted. The right side of the game maker
		gamePanel = new GamePanel(Constants.BOARD_WIDTH, Constants.BOARD_LENGTH);
		facade = new Facade(gamePanel);

		//This is the panel where all the controls are placed. The left side of the game maker
		controlPanel = new JPanel();

		//Top part of the control panel, which contains buttons like start/load/save.
		JPanel buttonPanel =  new ButtonPanel(Constants.CONTROL_PANEL_WIDTH,Constants.CONTROL_PANEL_LENGTH);
		controlPanel.add(buttonPanel);

		if(utility.Constants.isGameMaker)
		{

			//Bottom part of the control panel
			JPanel fieldPanel = FieldPanel.getInstance();

			controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
			//JScrollPane imagePanel = getImagePanel();
			imagesScrollPanel = new ImagesScrollPanel();
			controlPanel.add(imagesScrollPanel.getImagePanel());
			controlPanel.add(fieldPanel);
			themeCombo = new JComboBox();
			themeLabel = new JLabel("Themes : ");
			themeCombo.addItem("System");
			themeCombo.addItem("Tattoo");
			themeCombo.addItem("Metal");
			themeCombo.addItem("Motif");
			themeCombo.addItem("Synth");
			themeCombo.addItem("GTK");
			themeCombo.addItem("NIMRODLF");

			
		
			themeCombo.addItemListener(new ItemListener(){

				@Override
				public void itemStateChanged(ItemEvent arg0) {

					if (arg0.getItem().equals("Tattoo"))
					{
						LookAndFeelTheme.getInstanceOf().setLookAndFeel(LookAndFeelType.TATTOO);
					}
					else if (arg0.getItem().equals("System"))
					{
						LookAndFeelTheme.getInstanceOf().setLookAndFeel(LookAndFeelType.SYSTEM);
					}
					else if (arg0.getItem().equals("Metal"))
					{
						LookAndFeelTheme.getInstanceOf().setLookAndFeel(LookAndFeelType.METAL);
					}
					else if (arg0.getItem().equals("Motif"))
					{
						LookAndFeelTheme.getInstanceOf().setLookAndFeel(LookAndFeelType.MOTIF);
					}
					else if (arg0.getItem().equals("GTK"))
					{
						LookAndFeelTheme.getInstanceOf().setLookAndFeel(LookAndFeelType.GTK);
					}
					else if (arg0.getItem().equals("Info"))
					{
						LookAndFeelTheme.getInstanceOf().setLookAndFeel(LookAndFeelType.INFO);
					}
					
					else if (arg0.getItem().equals("NIMRODLF"))
					{
						LookAndFeelTheme.getInstanceOf().setLookAndFeel(LookAndFeelType.NIMRODLF);
					}
					
					else if (arg0.getItem().equals("QUAQUA"))
					{
						LookAndFeelTheme.getInstanceOf().setLookAndFeel(LookAndFeelType.QUAQUA);
					}
					
					else if (arg0.getItem().equals("TINY"))
					{
						LookAndFeelTheme.getInstanceOf().setLookAndFeel(LookAndFeelType.TINY);
					}
					
					
					else if (arg0.getItem().equals("SMOOTHMETAL"))
					{
						LookAndFeelTheme.getInstanceOf().setLookAndFeel(LookAndFeelType.SMOOTHMETAL);
					}
					
				
					
					baseFrame.setPreferredSize(new Dimension(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT));
					baseFrame.pack();
				}

			});
			
			JPanel actionEventPanel=ActionEventPanel.getInstance();
			controlPanel.add(actionEventPanel);
			
			themePanel = new JPanel();
			themePanel.setBackground(new Color(140,160,170));
			
			themePanel.setLayout(new FlowLayout());
			themeLabel.setPreferredSize(new Dimension(80,25));
			themeCombo.setPreferredSize(new Dimension(80,25));
			themePanel.add(themeLabel);
			themePanel.add(themeCombo);
			controlPanel.add(themePanel);

		}

		this.addComponents();


		baseFrame.setVisible(true);
		baseFrame.setResizable(true);

	}


	public byte[] getByteArrayFromFile(File file) {

		InputStream is = null;

		try {
			is = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		long lengthFile = file.length();
		byte[] fileBytes = new byte[(int)lengthFile]; 
		int offset = 0;
		int numRead = 0;

		try {
			while(offset < fileBytes.length && (numRead = is.read(fileBytes, offset, fileBytes.length - offset)) >= 0) {
				offset += numRead;				
			}
			is.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		LOG.debug("Printing filebytes\n"+new String(fileBytes));

		String str = new String(fileBytes);
		String sub = str.substring(str.indexOf('>') + 1);
		return sub.getBytes();
	}

	public void loadSavedGame(Object object){

		GamePackage game = GameDataPackageIO.loadGamePackage(object);

		reset();

		LOG.debug("load done");

		List<SpriteModel> allSpriteModels = game.getSpriteList();

		for(SpriteModel spriteModel:allSpriteModels){
			LOG.debug(spriteModel.getId()+"--------------------");
		}

		if(!allSpriteModels.isEmpty())
			SpriteList.getInstance().setSelectedSpriteModel(allSpriteModels.get(0));

		facade.getGameController().setEvents(game.getEventsForGameController());
		facade.getKeyListenerController().setKeyEvents(game.getEventsForKeyController());
		facade.createViewsForModels(game.getSpriteList());

		for(SpriteModel model: allSpriteModels){
			SpriteList.getInstance().addSprite(model);
			SpriteList.getInstance().setSelectedSpriteModel(model);

			spriteListIndividualModel.addElement(model.getId());
			if(!spriteListGroupModel.contains(model.getGroupId()))
				spriteListGroupModel.addElement(model.getGroupId());
			if(spriteListIndividualModel.size() >0 )
				ActionEventPanel.getInstance().getSpriteJList().setModel(spriteListIndividualModel);
		}

		List<SpriteModel> allSpriteList =  SpriteList.getInstance().getSpriteList();

		List<String> layers = new ArrayList<String>();
		for(SpriteModel model:allSpriteList){
			if(!layers.contains(model.getLayer()))
				layers.add(model.getLayer());
		}
		for(String layerName: layers){
			if(!layerName.equalsIgnoreCase(Constants.ALL_LAYERS) &&
					!layerName.equalsIgnoreCase("Layer 1"))
				Layers.getInstance().addLayer(layerName);
		}

		FieldPanel.getInstance().updateProperties();
		ActionEventPanel.getInstance().updateSpriteJList();

		List<String> infoNames = Helper.getsharedHelper().getListFromJar("info.jar");

		for(String entryName:infoNames){
			int index = entryName.indexOf(".");
			String subEntryName = entryName;
			if(index >0)
			{
				subEntryName = entryName.substring(0, index);
				if(((String) sampleXmlList.getSelectedValue()).contains(subEntryName)){
					Image image = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource(entryName));
					ImageIcon icon = new ImageIcon(image);

					JLabel imageLabel = new JLabel();
					imageLabel.setIcon(icon);
					JFrame infoFrame = new JFrame();

					infoFrame.setTitle("Controls for this game");
					infoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					infoFrame.setLayout(new GridLayout());
					infoFrame.add(imageLabel);
					infoFrame.setSize(icon.getIconWidth(), icon.getIconHeight());
					infoFrame.setLocationRelativeTo(null);
					infoFrame.setVisible(true);
				}
			}
		}
	}

	@Override
	public void Resize(int framewidth, int frameheight) {
		int widthdiff = framewidth - Constants.FRAME_WIDTH;
		int heightdiff = frameheight - Constants.FRAME_HEIGHT;
		controlPanel.setSize(Constants.CONTROL_PANEL_WIDTH + (int) (widthdiff * 0.6), Constants.CONTROL_PANEL_LENGTH + (int) ((heightdiff * 2) / 7));
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

	}

	public void createDuplicateSpriteModel(SpriteModel model){
		SpriteModel spriteModel = new SpriteModel(model.getPosX()+model.getWidth()/2, model.getPosY()+model.getHeight()/2, model.getSpeedX(), model.getSpeedY(), model.getWidth(), model.getHeight(),model.getImageUrlString(),model.getLayer());
		updateSpriteList(spriteModel);
		FieldPanel.getInstance().updateProperties();

		facade.addSpriteModelToView(spriteModel);
		gamePanel.repaint();
	}

	public void updateSpriteList(SpriteModel spriteModel) {
		SpriteList.getInstance().addSprite(spriteModel);
		SpriteList.getInstance().setSelectedSpriteModel(spriteModel);
		spriteListIndividualModel.addElement(spriteModel.getId());
		if(!spriteListGroupModel.contains(spriteModel.getGroupId()))
			spriteListGroupModel.addElement(spriteModel.getGroupId());
		if(spriteListIndividualModel.size() >0 )
		{
			ActionEventPanel.getInstance().updateSpriteJList();
		}
	}


	public Facade getFacade() {
		return facade;
	}

	public void removeSpriteModelFromList(SpriteModel selectedSpriteModel) {
		int selectedItem = 0;
		DefaultListModel listModel = getSpriteListIndividualModel();
		for(int i=0; i<listModel.size(); i++){
			String element = (String) listModel.get(i);
			if(element.equalsIgnoreCase(selectedSpriteModel.getId())){
				selectedItem = i;
			}
		}
		listModel.remove(selectedItem);
		//ActionEventPanel.getInstance().getSpriteJList().setModel(listModel);

	}


	public void reset(){
		List<SpriteModel> allSpriteModels = SpriteList.getInstance().getSpriteList();
		for(SpriteModel model: allSpriteModels){
			Design.getInstance().getGamePanel().unregisterModel(model);
		}

		SpriteList.getInstance().getSpriteList().clear();

		if(facade.getGameController().getEvents()!=null)
			facade.getGameController().getEvents().clear();
		if(facade.getKeyListenerController().getKeyEvents()!=null)
			facade.getKeyListenerController().getKeyEvents().clear();

		spriteListIndividualModel.removeAllElements();
		ActionEventPanel.getInstance().getSpriteJList().setModel(spriteListIndividualModel);
		Layers.getInstance().clearAllLayers(); 
		FieldPanel.getInstance().updateProperties();

		FieldPanel.getInstance().clearAll();
		baseFrame.validate();
		ClockDisplay.getInstance().reset();
		ScoreDisplay.getInstance().reset();
		this.gamePanel.repaint();
		

	}

	public void addComponents()
	{
		if(utility.Constants.isGameMaker){
			baseFrame.setLayout(new GridLayout(1, 2));
			baseFrame.getContentPane().add(controlPanel);
			baseFrame.getContentPane().add(gamePanel);

		}
		else{
			baseFrame.setLayout(new BorderLayout());
			baseFrame.getContentPane().add(controlPanel , BorderLayout.NORTH);
			baseFrame.getContentPane().add(gamePanel,BorderLayout.CENTER);

		}

	}
}
