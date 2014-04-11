package view.panel;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import model.SpriteModel;
import utility.Layers;
import utility.SpriteList;
import view.Design;
import view.properties.GroupNameDocumentListener;
import view.properties.HeightPropertyDocumentListener;
import view.properties.SpritePropertiesDocumentListener;
import view.properties.VelocityXPropertyDocumentListener;
import view.properties.VelocityYPropertyDocumentListener;
import view.properties.WidthPropertyDocumentListener;

public class FieldPanel extends JPanel {

	private JTextField velocityXTextField;
	public JTextField getVelocityXTextField() {
		return velocityXTextField;
	}
	private JTextField velocityYTextField;
	public JTextField getVelocityYTextField() {
		return velocityYTextField;
	}
	private JTextField widthTextField;
	public JTextField getWidthTextField() {
		return widthTextField;
	}
	private JTextField heightTextField;
	public JTextField getHeightTextField() {
		return heightTextField;
	}

	private JTextField spriteNameTextField;
	public JTextField getSpriteNameTextField() {
		return spriteNameTextField;
	}
	private JTextField groupNameTextField;	
	public JTextField getGroupNameTextField() {
		return groupNameTextField;
	}
	private SpritePropertiesDocumentListener documentListener;
	private JButton addLayerBtn;
	private JComboBox layerBox;
	public JComboBox getLayerBox() {
		return layerBox;
	}
	public void setLayerBox(JComboBox layerBox) {
		this.layerBox = layerBox;
	}
	private String layer;
	private static FieldPanel sharedFieldPanel = null;
	public static FieldPanel getInstance(){
		if(sharedFieldPanel == null)
			sharedFieldPanel = new FieldPanel();
		return sharedFieldPanel;
	}
	public FieldPanel() {
		documentListener=new SpritePropertiesDocumentListener();
		setBackground(new Color(140,160,170));
		setLayout(new GridBagLayout());
		createFieldPanel();

	}
	public void createFieldPanel() {

		JLabel layerLabel= new JLabel("Select the Layer");
		addLayerBtn= new JButton("Add New Layer");
		addLayerBtn.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Layers.getInstance().addNewLayer();
			}
		});

		layerBox= new JComboBox( Layers.getInstance().getLayers().toArray());
		layerBox.setSelectedIndex(1);
		layerBox.addItemListener( new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				layer = ((String) ((JComboBox) e.getSource()).getSelectedItem());
				Design.getInstance().getGamePanel().setCurrentLayer(layer);
				Design.getInstance().getGamePanel().repaint();
			}
		});

		JLabel spriteNameLabel = new JLabel("Sprite Name");

		spriteNameTextField = new JTextField(10);
		spriteNameTextField.getDocument().addDocumentListener(documentListener);

		JLabel spriteGroupLabel = new JLabel("Group Name");

		groupNameTextField = new JTextField(10);
		groupNameTextField.getDocument().addDocumentListener(new GroupNameDocumentListener());

		JLabel velocityXLabel = new JLabel("Velocity X");

		velocityXTextField = new JTextField(10);
		velocityXTextField.getDocument().addDocumentListener(new VelocityXPropertyDocumentListener());

		JLabel velocityYLabel = new JLabel("Velocity Y");

		velocityYTextField = new JTextField(10);
		velocityYTextField.getDocument().addDocumentListener(new VelocityYPropertyDocumentListener());

		JLabel heightLabel = new JLabel("Height");

		heightTextField = new JTextField(10);
		heightTextField.getDocument().addDocumentListener(new HeightPropertyDocumentListener());

		JLabel widthLabel = new JLabel("Width");
		widthTextField = new JTextField(10);
		widthTextField.getDocument().addDocumentListener(new WidthPropertyDocumentListener());



		GridBagConstraints c = new GridBagConstraints();

		c.fill  = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(11, 5, 0, 0);
		c.weightx = 0.25;

		//Row 0
		c.gridy = 0;
		c.gridx = 0;
		add(layerLabel);
		c.gridx=1;
		add(layerBox);
		c.gridx=2;
		add(addLayerBtn);
		//Row 1
		c.gridy = 1;

		c.gridx = 0;
		add(spriteNameLabel,c);
		c.gridx = 1;
		add(spriteNameTextField,c);

		//Row 2
		c.gridy = 2;
		c.gridx = 0;
		add(spriteGroupLabel,c);

		c.gridx = 1;
		add(groupNameTextField,c);

		//Row 3
		c.gridy = 3;
		c.gridx = 0;
		add(widthLabel,c);

		c.gridx = 1;
		add(widthTextField,c);

		//Row 4
		c.gridy = 4;
		c.gridx = 0;
		add(heightLabel,c);

		c.gridx = 1;
		add(heightTextField,c);

		//Row 5
		c.gridy = 5;
		c.gridx = 0;
		add(velocityXLabel,c);

		c.gridx = 1;
		add(velocityXTextField,c);

		//Row 6
		c.gridy = 6;
		c.gridx = 0;
		add(velocityYLabel,c);

		c.gridx = 1;
		add(velocityYTextField,c);

		//Row 7
		c.gridy = 7;
		c.gridx = 0;

		c.gridx = 1;


	}
	public void updateProperties(){

		spriteNameTextField.getDocument().removeDocumentListener(documentListener);
		SpriteModel selectedSpriteModel = SpriteList.getInstance().getSelectedSpriteModel();
		if(selectedSpriteModel == null)
			return;
		spriteNameTextField.setText(selectedSpriteModel.getId());
		groupNameTextField.setText(selectedSpriteModel.getGroupId());
		widthTextField.setText(Double.toString(selectedSpriteModel.getWidth()));
		heightTextField.setText(Double.toString(selectedSpriteModel.getHeight()));
		velocityXTextField.setText(Double.toString(selectedSpriteModel.getSpeedX()));
		velocityYTextField.setText(Double.toString(selectedSpriteModel.getSpeedY()));

		int selectedItem = 0;
		DefaultListModel listModel = Design.getInstance().getSpriteListIndividualModel();
		for(int i=0; i<listModel.size(); i++){
			String element = (String) listModel.get(i);
			if(element.equalsIgnoreCase(selectedSpriteModel.getId())){
				selectedItem = i;
			}
		}
		ActionEventPanel.getInstance().getSpriteJList().setSelectedIndex(selectedItem);
		spriteNameTextField.getDocument().addDocumentListener(documentListener);


	}

	public void clearAll(){
		spriteNameTextField.setText("");
		groupNameTextField.setText("");
		widthTextField.setText("");
		heightTextField.setText("");
		velocityXTextField.setText("");
		velocityYTextField.setText("");
		
	}
	

}
