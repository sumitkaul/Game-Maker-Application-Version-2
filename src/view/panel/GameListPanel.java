package view.panel;


import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import testClient.GameClient;

public class GameListPanel implements ActionListener {
	
	private static final org.apache.log4j.Logger LOG = 
			org.apache.log4j.Logger.getLogger(GameListPanel.class);
	
	private JFrame listFrame;
	private JComboBox gameListCombo;
	private JButton okButton;
	private JButton cancelButton;
	private ArrayList<String> setGameList;

	private JPanel listPanel;
	
	public GameListPanel()
	{
		listFrame = new JFrame();
		listPanel = new JPanel();
		listPanel.setLayout(new GridLayout(2,2,20,10));
		gameListCombo = new JComboBox();
		okButton = new JButton("OK");
		okButton.setSize(new Dimension(100,50));
		okButton.addActionListener(this);
		setGameList= new ArrayList<String>();
		cancelButton = new JButton("Cancel");
		cancelButton.setSize(new Dimension(100,50));
		cancelButton.addActionListener(this);
		
		listFrame.setPreferredSize(new Dimension(250,100));
		listFrame.setVisible(false);
		listFrame.setLocationRelativeTo(null);
		listFrame.setTitle("Load game");
		
	}
	
	public void setGameList(ArrayList<String> gameList)
	{
		for (String game: gameList)
		{
			LOG.info("The game is :" + game);
					setGameList.add(game);
					gameListCombo.addItem(game);
		}
		
		
		JLabel gameLabel = new JLabel("Games:");
		listPanel.add(gameLabel);
		listPanel.add(gameListCombo);
		listPanel.add(okButton);
		listPanel.add(cancelButton);
		listFrame.getContentPane().add(listPanel);
		listFrame.setVisible(true);
		listFrame.pack();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource().equals(okButton))
		{
			String gameName=gameListCombo.getSelectedItem().toString();
			LOG.info("The game selected is " + gameName);
			if(!gameName.equals(""))
				GameClient.getInstance().setGameName(gameName);
			//String presavedCustomGameXML=GameClient.getInstance().getCustomGameXML(GameClient.getInstance().getGameName());
			LOG.info(gameName);
			String savedXML=GameClient.getInstance().getCustomGameXML(GameClient.getInstance().getGameName());
			LOG.info("The saved XML is"+savedXML);
			listFrame.dispose();
		}
		else if (arg0.getSource().equals(cancelButton))
		{
			listFrame.dispose();
		}
	}
}
