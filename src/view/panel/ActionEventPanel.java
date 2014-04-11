package view.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.SpriteModel;
import utility.CustomJPanel;
import utility.Helper;
import utility.ImageListCellRenderer;
import utility.SpriteList;
import view.Design;
import action.ActionStartOver;
import action.ActionUpdateScore;
import action.GameAction;
import controller.GameController;
import eventlistener.CollisionEventListener;
import eventlistener.EventListener;
import eventlistener.KeyPressedEventListener;
import eventlistener.OutOfBoundaryEventListener;
import facade.Facade;

public class ActionEventPanel extends JPanel {

	
	private String[] sample={"Sprite List"};
	private int scoreDiff;
	private String[] actionTypes={"Change Visibility","Move","Create Bomb",
			"Reposition","Remove","Play Sound","Bounce","Change Speed","Random Movement",
			"Rotate Clockwise","Rotate Anticlockwise","Change Image","Start Over",
			"Game Win","Game Lose","Update Score"};
	private String[] eventTypes={"Collision","Input","New Frame","Time Change","Out of Boundary"};
	private JPanel inputPanel;
	private JList eventActionList;
	private JList spriteJList;
	private double startX, startY;
	public JList getSpriteJList() {
		return spriteJList;
	}
	private JComboBox eventBox;
	private JComboBox collisionSpriteBox;
	private DefaultComboBoxModel collisionSpriteModel;
	private JButton addEventActionButton;
	private JButton removeEventActionButton;
	
	public void setSpriteJList(JList spriteJList) {
		this.spriteJList = spriteJList;
	}
	private JScrollPane spriteListScrollPane;
	private JComboBox actionBox;
	private DefaultListModel eventActionListModel;
	private InputKeyPanel inputKeyPanel;
	private static ActionEventPanel sharedActionEventPanel = null;
	public static ActionEventPanel getInstance(){
		if(sharedActionEventPanel == null)
			sharedActionEventPanel = new ActionEventPanel();
		return sharedActionEventPanel;
	}
	public ActionEventPanel() {
		createActionEventPanel();
	}
	public void createActionEventPanel(){

		collisionSpriteModel = new DefaultComboBoxModel();
		collisionSpriteModel.addElement("SpriteList");
		eventActionListModel=new DefaultListModel();
		setLayout(new GridBagLayout());
		setBackground(new Color(140,160,170));
		inputPanel=new JPanel();
		inputKeyPanel=new InputKeyPanel(inputPanel);
		
		inputPanel.setBackground(Color.WHITE);
		inputPanel.setVisible(false);
		inputPanel.addMouseListener(inputKeyPanel);
		inputPanel.addKeyListener(inputKeyPanel);
	
		spriteJList=new JList();		
		spriteJList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		spriteJList.setListData(sample);
		spriteListScrollPane = new JScrollPane(spriteJList);
		spriteListScrollPane.setSize(100, 100);
		spriteListScrollPane.setMinimumSize(new Dimension(100,100));
		spriteJList.setCellRenderer(new ImageListCellRenderer());
		
		actionBox=new JComboBox(actionTypes);
		actionBox.addActionListener(new ActionListener() {


			@Override
			public void actionPerformed(ActionEvent arg0) {
				String actionName = (String) actionBox.getSelectedItem();
				if(actionName.equalsIgnoreCase("Start Over")){
					String[] combo = { "Start of frame",
							"End of frame", "Present value" };
					JComboBox startXCombo = new JComboBox(combo);
					startXCombo.setEditable(true);
					JComboBox startYCombo = new JComboBox(combo);
					startYCombo.setEditable(true);
					final JComponent[] inputs = new JComponent[] {
							new JLabel("Start X position"),
							startXCombo,
							new JLabel("Start Y position"),
							startYCombo,
					};
					JOptionPane.showMessageDialog(null, inputs, "Start positions", JOptionPane.PLAIN_MESSAGE);
					String xval = (String) startXCombo.getSelectedItem();
					if(xval.equalsIgnoreCase("Start of frame")){
						startX = 0;
					}
					else if(xval.equalsIgnoreCase("End of frame")){
						startX = Design.getInstance().getGamePanel().getWidth();
					}
					else if(xval.equalsIgnoreCase("Present value")){
						SpriteModel selectedSpriteModel = SpriteList.getInstance().getSelectedSpriteModel();
						startX = selectedSpriteModel.getPosX();
					}
					else{
						startX = Integer.parseInt(xval);
					}
					String yval = (String) startYCombo.getSelectedItem();
					if(yval.equalsIgnoreCase("Start of frame")){
						startY = 0;
					}
					else if(yval.equalsIgnoreCase("End of frame")){
						startY = Design.getInstance().getGamePanel().getHeight();
					}
					else if(yval.equalsIgnoreCase("Present value")){
						SpriteModel selectedSpriteModel = SpriteList.getInstance().getSelectedSpriteModel();
						startY = selectedSpriteModel.getPosY();
					}
					else{
						startY = Integer.parseInt(yval);
					}
				}
				if(actionName.equalsIgnoreCase("Update Score")){
					String str = JOptionPane.showInputDialog( "Update score by : " , "10");
					scoreDiff = Integer.parseInt(str);
				}
			}
		});

		eventBox=new JComboBox(eventTypes);
		eventActionList=new JList();
		if(eventActionListModel!=null)
			eventActionList.setModel(eventActionListModel);
		JScrollPane scrollPane=new JScrollPane(eventActionList);
		scrollPane.setMinimumSize(new Dimension(100,100));
		scrollPane.setEnabled(false);

		collisionSpriteBox=new JComboBox();
		if(collisionSpriteModel!=null)
		collisionSpriteBox.setModel(collisionSpriteModel);
		collisionSpriteBox.setVisible(false);
		addEventActionButton=new JButton("Add");
		addEventActionButton.addActionListener(new ActionListener(){


			public void actionPerformed(ActionEvent e){
				Facade facade=Design.getInstance().getFacade();
				eventActionList.setEnabled(true);
				String eventName = (String)eventBox.getSelectedItem();
				String actionName = (String)actionBox.getSelectedItem();
				SpriteModel selectedSpriteModel = SpriteList.getInstance().getSelectedSpriteModel();

				String eventActionString = eventName+"+"+actionName;
				eventActionListModel.addElement(eventActionString);

				SpriteModel secondaryModel = null;
				String itemName =  (String) collisionSpriteBox.getSelectedItem();
				List<SpriteModel> spriteList =  SpriteList.getInstance().getSpriteList();
				for(SpriteModel model : spriteList){
					if(model.getId().equalsIgnoreCase(itemName)){
						secondaryModel = model;
					}
				}

				EventListener listener = Helper.getsharedHelper().getEventListenerForString(eventName, actionName, selectedSpriteModel,secondaryModel);
				if(listener instanceof KeyPressedEventListener){
					facade.getKeyListenerController().registerListener(listener);
				}
				else{
					facade.getGameController().registerListener(listener);
				}
				if(listener instanceof OutOfBoundaryEventListener){
					OutOfBoundaryEventListener outOfBoundary = (OutOfBoundaryEventListener)listener;
					GameAction action = outOfBoundary.getAction();
					if(action instanceof ActionStartOver){
						ActionStartOver startOver = (ActionStartOver)action;
						startOver.setStartX(startX);
						startOver.setStartY(startY);
					}
				}
				if(listener instanceof CollisionEventListener){
					CollisionEventListener collisionEventListener = (CollisionEventListener)listener;
					GameAction action = collisionEventListener.getAction();
					if(action instanceof ActionUpdateScore){
						ActionUpdateScore updateScore = (ActionUpdateScore)action;
						updateScore.setScoreDiff(scoreDiff);
					}
				}
			
				selectedSpriteModel.getStringToEventMap().put(eventActionString, listener.getEventId());
				selectedSpriteModel.getEventListenerList().add(listener);

			}
		});
		removeEventActionButton=new JButton("Remove");
		removeEventActionButton.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e){
				Facade facade=Design.getInstance().getFacade();
				String eventActionPair = (String) eventActionList.getSelectedValue();
				if(eventActionPair == null || eventActionPair.equalsIgnoreCase(""))
					return;
				eventActionListModel.removeElement(eventActionPair);

				SpriteModel spriteModel = SpriteList.getInstance().getSelectedSpriteModel();
				int hashId = spriteModel.getStringToEventMap().get(eventActionPair);

				ArrayList<EventListener> toRemoveListeners = new ArrayList<EventListener>();
				for(EventListener listener :spriteModel.getEventListenerList()){
					if(listener.getEventId() == hashId) 
						toRemoveListeners.add(listener);
				}

				GameController gameController =  facade.getGameController();

				for(EventListener listener :toRemoveListeners){

					ArrayList<EventListener> eventListeners=spriteModel.getEventListenerList();
					eventListeners.remove(listener);
					SpriteModel selectedSpriteModel = SpriteList.getInstance().getSelectedSpriteModel();
					selectedSpriteModel.getStringToEventMap().remove(eventActionPair);

					spriteModel.setEventListenerList(eventListeners);
					gameController.unregisterListener(listener);
				}
			}
		});


		spriteJList.addListSelectionListener(new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent arg0) {

				eventActionListModel.clear();
				collisionSpriteModel.removeAllElements();

				for(int i=0;i<SpriteList.getInstance().getSpriteList().size();i++)
				{
					if(spriteJList.getSelectedIndex()>=0)
					{
						String stringID = "";
						Object  object= spriteJList.getSelectedValue();
						if(object instanceof String)
							stringID= (String) spriteJList.getSelectedValue();
						else if(object instanceof CustomJPanel)
							stringID = ((CustomJPanel)object).getJLabelText();
						if(SpriteList.getInstance().getSpriteList().get(i).getId().equals(stringID))
						{

							SpriteList.getInstance().setSelectedSpriteModel(SpriteList.getInstance().getSpriteList().get(i));
							FieldPanel.getInstance().updateProperties();
							eventActionListModel.clear();
							Set<String> s=SpriteList.getInstance().getSelectedSpriteModel().getStringToEventMap().keySet();
							java.util.Iterator<String> it=s.iterator();
							while(it.hasNext())
								eventActionListModel.addElement(it.next());
						}
					}
				}

			}

		});
	
		GridBagConstraints gridBagConstraints = new GridBagConstraints();

		gridBagConstraints.fill  = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.insets = new Insets(10, 5, 0, 0);
		gridBagConstraints.weightx = 0.25;

		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;

		add(spriteListScrollPane,gridBagConstraints);

		gridBagConstraints.gridx = 1;
		add(scrollPane,gridBagConstraints);


		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy=1;
		add(eventBox,gridBagConstraints);

		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy=2;
		add(actionBox,gridBagConstraints);

		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy=1;
		add(addEventActionButton,gridBagConstraints);

		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy=2;
		add(removeEventActionButton,gridBagConstraints);

		gridBagConstraints.gridx=0;
		gridBagConstraints.gridy=3;
		add(collisionSpriteBox,gridBagConstraints);
		add(inputPanel,gridBagConstraints);

		eventBox.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				String text=(String)eventBox.getSelectedItem();

				if(text.equalsIgnoreCase("Collision")){
					collisionSpriteBox.setVisible(true);

					for(int i=0;i<SpriteList.getInstance().getSpriteList().size();i++){
						collisionSpriteModel.removeAllElements();
					}

					for(int i=0;i<SpriteList.getInstance().getSpriteList().size();i++){
						if(SpriteList.getInstance().getSpriteList().get(i).getId()==spriteJList.getSelectedValue()){}
						else	
							collisionSpriteModel.addElement(SpriteList.getInstance().getSpriteList().get(i).getId());
					}
				}
				if(text.equalsIgnoreCase("Input")){
					collisionSpriteBox.setVisible(false);
					inputPanel.setVisible(true);
					inputPanel.requestFocusInWindow();
				}	
				if(text.equalsIgnoreCase("New Frame")){
					collisionSpriteBox.setVisible(false);
					inputPanel.setVisible(false);
				}
				if(text.equalsIgnoreCase("Time Change")){
					collisionSpriteBox.setVisible(false);
					inputPanel.setVisible(false);
				}
			}	
		});
		
	}
	private static ImageIcon scale(Image src, int w, int h) {
		int type = BufferedImage.TYPE_INT_RGB;
		BufferedImage dst = new BufferedImage(w, h, type);
		Graphics2D g2 = dst.createGraphics();
		g2.drawImage(src, 0, 0, w, h, null);
		g2.dispose();
		return new ImageIcon(dst);
	}
	public void updateSpriteJList() {
		List<SpriteModel> Sprites=SpriteList.getInstance().getSpriteList();
		CustomJPanel[] panels=new CustomJPanel[Sprites.size()];
		for(int j=0;j<Design.getInstance().getSpriteListIndividualModel().size();j++)
		{
			panels[j]=new CustomJPanel(new FlowLayout(FlowLayout.LEFT));
		}

		int k=0;
		BufferedImage image = null;
		URL url = null;
		for(SpriteModel Sprite:Sprites) {

			try {
				
				if(Sprite.getImageUrlString().substring(0, 1).equals("u"))
				{
					try {
						url = new URL(Sprite.getImageUrlString().substring(1));
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
					try {
						image = ImageIO.read(url);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				else
				image = ImageIO.read(getClass().getResourceAsStream(Sprite.getImageUrlString()));
				
			} catch (IOException e) {

				e.printStackTrace();
			};

			Icon scaled = scale(image, 20, 20);
			JLabel newLabel = new JLabel(Sprite.getId(), scaled, JLabel.LEFT);
			panels[k].add(newLabel);
			k++;
		}
		spriteJList.setListData(panels);
	}
}
