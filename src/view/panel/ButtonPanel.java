package view.panel;


import facade.Facade;
import gameServer.CustomGames;

import gameServer.DatabaseSession;

import gameServer.Scores;
import gameServer.SendScore;
import gameServer.SuperController;
import gameServer.URLImages;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import loader.GameDataPackageIO;
import loader.GamePackage;

import org.eclipse.jetty.util.log.Log;


import testClient.GameClient;
import utility.Constants;
import utility.HighScoreDisplay;
import utility.Helper;
import utility.SpriteList;
import view.Design;

public class ButtonPanel extends JPanel implements ActionListener, DocumentListener
{

	private JButton startButton;
	private JButton LoadButton;
	private JButton newGameButton;
	private JButton saveButton;
	private JButton pauseButton;
	private JButton sendScoreButton;
	private JButton highScoreButton;
	private JButton addImagesButton;
	private String imageURL;
	private Dimension butttonDiamention = new Dimension(100,30);
	private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
			.getLogger(ButtonPanel.class);
	public ButtonPanel(int width, int height) 
	{

		startButton = new JButton("Start");
		LoadButton = new JButton("Load");
		newGameButton = new JButton("New Game");
		saveButton = new JButton("Save");
		pauseButton = new JButton("Pause");
		sendScoreButton = new JButton("Send Score");
		highScoreButton = new JButton("High Score");

		startButton.setPreferredSize(butttonDiamention);
		LoadButton.setPreferredSize(butttonDiamention);
		newGameButton.setPreferredSize(butttonDiamention);
		saveButton.setPreferredSize(butttonDiamention);
		pauseButton.setPreferredSize(butttonDiamention);
		sendScoreButton.setPreferredSize(butttonDiamention);
		highScoreButton.setPreferredSize(butttonDiamention);

		startButton.addActionListener(this);
		LoadButton.addActionListener(this);
		newGameButton.addActionListener(this);
		saveButton.addActionListener(this);
		pauseButton.addActionListener(this);
		sendScoreButton.addActionListener(this);
		highScoreButton.addActionListener(this);

		
		this.setLayout(new GridLayout(2,1,15,10));
		this.add(startButton);
		this.add(LoadButton);
		this.add(newGameButton);
		this.add(saveButton);
		this.add(pauseButton);
		this.add(sendScoreButton);
		this.add(highScoreButton);

		if(Constants.isGameMaker)
		{	
			addImagesButton = new JButton("Add Images");
			addImagesButton.addActionListener(this);
			this.add(addImagesButton);

		}

		this.setSize(width,height);


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

		//ByteBuffer byteBuffer = ByteBuffer.wrap(fileBytes);




		LOG.debug("Printing filebytes\n"+new String(fileBytes));

		String str = new String(fileBytes);
		String sub = str.substring(str.indexOf('>') + 1);
		return sub.getBytes();
	}


	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		if(actionEvent.getSource().equals(newGameButton))
		{
			Design.getInstance().reset();
		}
		if(actionEvent.getSource().equals(startButton))
		{
			Design.getInstance().getFacade().startGame();
			Design.getInstance().getGamePanel().requestFocusInWindow();

		}
		if(actionEvent.getSource().equals(sendScoreButton))
		{
			
			
			GameClient.getInstance().sendScores();
		}
		if(actionEvent.getSource().equals(startButton))
		{
			Design.getInstance().getFacade().startGame();
			Design.getInstance().getGamePanel().requestFocusInWindow();

		}
		if(actionEvent.getSource().equals(pauseButton))
		{
			Design.getInstance().getFacade().stopGame();
		}
		if(actionEvent.getSource().equals(saveButton))
		{
			//TODO
			String gameName = JOptionPane.showInputDialog(null, "Enter the GameName", "Save The Game", JOptionPane.OK_CANCEL_OPTION);
			CustomGames customGame= new CustomGames();
			customGame.setGamename(gameName);
			customGame.setUserid(GameClient.getInstance().getUserId());
			GameClient.getInstance().setGameName(gameName);
			
			
			URL url = Design.getInstance().getClass().getResource("../");

			File file = new File("save.xml");
			try {
				if (!file.exists())
					file.createNewFile();
			} catch (IOException e1) {
				LOG.error(e1);
			}
			Facade facade=Design.getInstance().getFacade();	

			LOG.debug("controller events size: " + facade.getGameController().getEvents().size());

			GamePackage game = new GamePackage(SpriteList.getInstance().getSpriteList(), facade.getGameController()
					.getEvents(), facade.getKeyListenerController().getKeyEvents());
			game.setGameName(GameClient.getInstance().getGameName());
			game.setUserName(GameClient.getInstance().getUsername());
			
			try {
				GameDataPackageIO.saveGamePackageToFile(file,game);
			} catch (IOException ex) {
				LOG.error(ex);
			}
			String fileByteString = new String(getByteArrayFromFile(file));
/*			String strBegin = "<?xml version=\"1.0\" ?>" + "<game userName="+ "\"" + GameClient.getInstance().getUsername() + "\"" +
						" gameName=" + "\"" + GameClient.getInstance().getGameName() + "\"" + ">";*/
				
			/*fileByteString = strBegin + fileByteString + "</game>";*/

			LOG.debug("Printing filebyteString\n"+ fileByteString);
				// BufferUtil.toBuffer(file);
			GameClient.getInstance().postGames(GameClient.getInstance().getUserId(), GameClient.getInstance().getGameName(),
						fileByteString.getBytes());

		}		
		if(actionEvent.getSource().equals(LoadButton))
		{
			//TODO
			LOG.info("Start loading game name list ");
			String gameList =GameClient.getInstance().getCustomGameNames();
			LOG.info(gameList);
			StringTokenizer strToken = new StringTokenizer(gameList, "[%^]");
			
			String[] splitStr = new String[strToken.countTokens()];
			int index = 0;
		      while(strToken.hasMoreElements()) 
		      {
		
		    	  splitStr[index++] = strToken.nextToken();
		      }
		      for(index=1; index < splitStr.length-1; index++) 
		      {
		         System.out.println("Tokenizer : " + splitStr[index]);
		      }
			ArrayList<String> gameNames= new ArrayList<String>();
			for(int i=1;i< splitStr.length-1;i++)
			{
				LOG.info(splitStr[i]);
				gameNames.add(splitStr[i]);
			}
			GameListPanel gameNamesList= new GameListPanel();
			gameNamesList.setGameList(gameNames);
			
			
		}
		
		if(actionEvent.getSource().equals(highScoreButton))
		{
			String ScoreList=GameClient.getInstance().viewHighScores();
			String[] scores = ScoreList.split(",");
			ArrayList<String> scoresList = new ArrayList<String>();
			for (int i =0; i < scores.length; i++)
			{
				scoresList.add(scores[i]);
			}
			HighScorePanel highScorePanel = new HighScorePanel(scoresList);
		}
		
		
		if(actionEvent.getSource().equals(addImagesButton))
		{
			imageURL = JOptionPane.showInputDialog(null, "Enter the URL", "Add images from web", JOptionPane.OK_CANCEL_OPTION);
			URLImages urlImages=new URLImages();
			urlImages.setUrl(imageURL);
			urlImages.setUserid(GameClient.getInstance().getUserId());
			String webImages= GameClient.getInstance().sendwebImages(urlImages);
			//DatabaseSession.getInstance().saveObject(urlImages);
			//addImagesFromWeb(imageURL);
//			URL url=null;
//			if(!imageURL.equalsIgnoreCase("")){
//				try {
//					url = new URL(imageURL);
//				} catch (MalformedURLException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//				LOG.info("url: "+url);
//			    File file = new File(url.getFile());
//			    
//			    File[] files={file};
//			    
//			    URL urlString = this.getClass().getClassLoader().getResource("images.jar");
//			    File mainjar;
//				try {
//					mainjar = new File(urlString.toURI());
//					//files[0]=mainjar;
//				} catch (URISyntaxException e2) {
//					// TODO Auto-generated catch block
//					mainjar = new File(urlString.getPath());
//				}
//				
//			    try {
//					Helper.getsharedHelper();
//					LOG.info("files: "+files.toString());
//					//Helper.getsharedHelper().updateZipFile(urlString.toURI().toString().replaceFirst("/",""), file);
//					Helper.addFilesToExistingZip(mainjar, files);
//				} catch (IOException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//			
//			}

		}
	}
		

	

	@Override
	public void changedUpdate(DocumentEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void insertUpdate(DocumentEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeUpdate(DocumentEvent arg0) {
		// TODO Auto-generated method stub

	}



}
