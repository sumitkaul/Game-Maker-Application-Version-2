package utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.swing.JOptionPane;

import view.Design;

import model.SpriteModel;
import eventlistener.CollisionEventListener;
import eventlistener.EventListener;
import eventlistener.KeyPressedEventListener;
import eventlistener.NewFrameEventListener;
import eventlistener.OutOfBoundaryEventListener;
import action.ActionBackToLastPosition;
import action.ActionCreateSpriteModel;
import action.ActionBounce;
import action.ActionChangeSpeed;
import action.ActionChangeVisibility;
import action.ActionChangeGameStatus;
import action.ActionMove;
import action.ActionPlaySound;
import action.ActionRandomChangeDirection;
import action.ActionRotate;
import action.ActionStartOver;
import action.ActionUpdateScore;
import action.GameAction;
import action.RemoveAction;

public class Helper {
	public static Helper sharedHelper;
	private int currentKeyCode; 
	
	public static Helper getsharedHelper(){
		if(sharedHelper == null)
			sharedHelper = new Helper();
		return sharedHelper;
	}
	public List<String> getListFromJar(String jarName){
		List<String> imageNames = new ArrayList<String>();

		if( true ) {
			URL urlString = this.getClass().getClassLoader().getResource(jarName);
			ZipInputStream zip;
			try {
				//				LOG.debug(urlString);
				zip = new ZipInputStream(urlString.openStream());
				ZipEntry ze = null;

				while((ze = zip.getNextEntry()) != null) {
					String entryName = ze.getName();
					//					LOG.debug(entryName);
					if(entryName.endsWith(".png") ||
							entryName.endsWith(".jpg")) {
						imageNames.add(entryName);
					}
				}
			} catch (IOException e1) {
				//LOG.error(e1);
			}
		}
		return imageNames;
	}
	public GameAction getActionForString(String actionString, SpriteModel model){
		GameAction gameAction = null;
		if(actionString.equalsIgnoreCase("move")){
			gameAction = new ActionMove();
		}
		else if(actionString.equalsIgnoreCase("create bomb")){
			gameAction = new ActionCreateSpriteModel();
		}
		else if(actionString.equalsIgnoreCase("change visibility")){
			gameAction = new ActionChangeVisibility(!model.isVisible());
		}
		else if(actionString.equalsIgnoreCase("play sound")){
			gameAction = new ActionPlaySound("bounce.au");
		}
		else if(actionString.equalsIgnoreCase("Random Movement")){
			gameAction = new ActionRandomChangeDirection(model);
		}
		else if(actionString.equalsIgnoreCase("Change Speed")){
			gameAction = new ActionChangeSpeed((int)model.getSpeedX(),(int)model.getSpeedY());
		}
		else if(actionString.equalsIgnoreCase("Bounce")){
			gameAction = new ActionBounce(false);
		}
		else if(actionString.equalsIgnoreCase("Remove")){
			gameAction = new RemoveAction();
		}
		else if(actionString.equalsIgnoreCase("Rotate Clockwise")){
			gameAction = new ActionRotate("Clockwise");
		}
		else if(actionString.equalsIgnoreCase("Rotate Anticlockwise")){
			gameAction = new ActionRotate("Anticlockwise");
		}
		else if(actionString.equalsIgnoreCase("Reposition")){
			gameAction = new ActionBackToLastPosition();
		}
		else if(actionString.equalsIgnoreCase("Start over")){
			gameAction = new ActionStartOver();
		}
		else if(actionString.equalsIgnoreCase("Game Win")){
			gameAction = new ActionChangeGameStatus(true);
		}
		else if(actionString.equalsIgnoreCase("Game Lose")){
			gameAction = new ActionChangeGameStatus(false);
		}
		else if(actionString.equalsIgnoreCase("Update Score")){
			//int scoreAdd = JOptionPane.showinpu
			gameAction = new ActionUpdateScore(10);
		}
		
		return gameAction;
	}
	


	public EventListener getEventListenerForString(String eventName,String actionName, 
			SpriteModel selectedSpriteModel, SpriteModel secondarySpriteModel) {
		
		if(eventName.equalsIgnoreCase("New Frame")){
			NewFrameEventListener newFrameEventListener = new NewFrameEventListener();
			newFrameEventListener.setRegisteredGroupId(selectedSpriteModel.getGroupId());
			newFrameEventListener.setRegisteredObjectId(selectedSpriteModel.getId());
			GameAction action = getActionForString(actionName,selectedSpriteModel);
			newFrameEventListener.setAction(action);
			return newFrameEventListener;
		}
		else if(eventName.equalsIgnoreCase("Collision")){
			CollisionEventListener collisionListener = new CollisionEventListener();
			if(selectedSpriteModel != null && selectedSpriteModel.getGroupId() != null)
				collisionListener.setRegisteredGroupId1(selectedSpriteModel.getGroupId());
			if(secondarySpriteModel!=null && secondarySpriteModel.getId()!=null)
				collisionListener.setRegisteredGroupId2(secondarySpriteModel.getGroupId());
			GameAction action = getActionForString(actionName, selectedSpriteModel);
			
			collisionListener.setAction(action);
	
			return collisionListener;
			
		}
		else if(eventName.equalsIgnoreCase("Input")){
			KeyPressedEventListener keyListener = new KeyPressedEventListener();
			keyListener.setRegisteredGroupId(selectedSpriteModel.getGroupId());
			keyListener.setRegisteredObjectId(selectedSpriteModel.getId()); 
			
			keyListener.setxSpeed(selectedSpriteModel.getSpeedX());
			keyListener.setySpeed(selectedSpriteModel.getSpeedY());
			
			keyListener.setKeyRegistered(this.currentKeyCode);
			
			GameAction action = getActionForString(actionName, selectedSpriteModel);
			keyListener.setAction(action);
			
			return keyListener;
		}
		else if(eventName.equalsIgnoreCase("Out of Boundary")){
			OutOfBoundaryEventListener outOfBoundary = new OutOfBoundaryEventListener();
			outOfBoundary.setRegisteredGroupId(selectedSpriteModel.getGroupId());
			outOfBoundary.setRegisteredObjectId(selectedSpriteModel.getId()); 
			GameAction action = getActionForString(actionName,selectedSpriteModel);
			List<GameAction> list = new ArrayList<GameAction>();
			if(action!=null)
				list.add(action);
			outOfBoundary.setAction(action);
			return outOfBoundary;
		}
		return null;
		
	}
	
	/*
	 * GETTER AND SETTER
	 */
	
	public int getCurrentKeyCode() {
		return currentKeyCode;
	}

	public void setCurrentKeyCode(int currentKeyCode) {
		this.currentKeyCode = currentKeyCode;
	}
	
	public void updateZipFile(File zipFile,
            File[] files) throws IOException {
 File tempFile = File.createTempFile(zipFile.getName(), null);
 tempFile.delete();

 boolean renameOk=zipFile.renameTo(tempFile);
 if (!renameOk)
 {
     throw new RuntimeException("could not rename the file "+zipFile.getAbsolutePath()+" to "+tempFile.getAbsolutePath());
 }
 byte[] buf = new byte[1024];

 ZipInputStream zin = new ZipInputStream(new FileInputStream(tempFile));
 ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFile));

 ZipEntry entry = zin.getNextEntry();
 while (entry != null) {
     String name = entry.getName();
     boolean notInFiles = true;
     for (File f : files) {
         if (f.getName().equals(name)) {
             notInFiles = false;
             break;
         }
     }
     if (notInFiles) {
         // Add ZIP entry to output stream.
         out.putNextEntry(new ZipEntry(name));
         // Transfer bytes from the ZIP file to the output file
         int len;
         while ((len = zin.read(buf)) > 0) {
             out.write(buf, 0, len);
         }
     }
     entry = zin.getNextEntry();
 }
   
 zin.close();
 for (int i = 0; i < files.length; i++) {
     InputStream in = new FileInputStream(files[i]);
     out.putNextEntry(new ZipEntry(files[i].getName()));
     int len;
     while ((len = in.read(buf)) > 0) {
         out.write(buf, 0, len);
     }
     out.closeEntry();
     in.close();
 }
 out.close();
 tempFile.delete();
}
	
	public static void addFilesToExistingZip(File zipFile,
			File[] files) throws IOException {
			// get a temp file
			File tempFile = File.createTempFile(zipFile.getName(), null);
			// delete it, otherwise you cannot rename your existing zip to it.
			tempFile.delete();
			boolean renameOk=zipFile.renameTo(tempFile);
			/*if (!renameOk)
			{
			throw new RuntimeException("could not rename the file "+zipFile.getAbsolutePath()+" to "+tempFile.getAbsolutePath());
			}*/
			byte[] buf = new byte[1024];
			ZipInputStream zin = new ZipInputStream(new FileInputStream(tempFile));
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFile));
			ZipEntry entry = zin.getNextEntry();
			while (entry != null) {
			String name = entry.getName();
			boolean notInFiles = true;
			for (File f : files) {
			if (f.getName().equals(name)) {
			notInFiles = false;
			break;
			}
			}
			if (notInFiles) {
			// Add ZIP entry to output stream.
			out.putNextEntry(new ZipEntry(name));
			// Transfer bytes from the ZIP file to the output file
			int len;
			while ((len = zin.read(buf)) > 0) {
			out.write(buf, 0, len);
			}
			}
			entry = zin.getNextEntry();
			}
			// Close the streams       
			zin.close();
			// Compress the files
			for (int i = 0; i < files.length; i++) {
			InputStream in = new FileInputStream(files[i]);
			// Add ZIP entry to output stream.
			out.putNextEntry(new ZipEntry(files[i].getName()));
			// Transfer bytes from the file to the ZIP file
			int len;
			while ((len = in.read(buf)) > 0) {
			out.write(buf, 0, len);
			}
			// Complete the entry
			out.closeEntry();
			in.close();
			}
			// Complete the ZIP file
			out.close();
			tempFile.delete();
			}
	
}
