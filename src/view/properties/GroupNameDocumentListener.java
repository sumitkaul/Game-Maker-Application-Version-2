package view.properties;

import javax.swing.DefaultListModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import model.SpriteModel;
import utility.SpriteList;
import view.Design;
import view.panel.FieldPanel;

public class GroupNameDocumentListener implements DocumentListener {			
	public void changedUpdate(DocumentEvent e) {

	}

	public void removeUpdate(DocumentEvent e) {
		update();
	}

	public void insertUpdate(DocumentEvent e) {
		update();
	}

	public void update() {

		Design design=Design.getInstance();
		FieldPanel fieldPanel=FieldPanel.getInstance();
		DefaultListModel spriteListGroupModel=design.getSpriteListGroupModel();
		String groupNameTextFieldString = fieldPanel.getGroupNameTextField().getText();
		String previousGroupId = SpriteList.getInstance().getSelectedSpriteModel().getGroupId();
		if(groupNameTextFieldString != null && !groupNameTextFieldString.equalsIgnoreCase("")){
			SpriteModel spriteModel = SpriteList.getInstance().getSelectedSpriteModel();
			spriteModel.setGroupId(groupNameTextFieldString);

			for(int i=0; i<spriteListGroupModel.size(); i++){
				String textString = (String) spriteListGroupModel.get(i);
				if(textString.equalsIgnoreCase(previousGroupId)){
					spriteListGroupModel.set(i, groupNameTextFieldString);		
				}

			}

			for(int i=0; i<spriteListGroupModel.size(); i++){
				String textString = (String) spriteListGroupModel.get(i);
				if(textString.equalsIgnoreCase(groupNameTextFieldString))
					spriteListGroupModel.remove(i);

			}

			if(!spriteListGroupModel.contains(spriteModel.getGroupId()))
				spriteListGroupModel.addElement(spriteModel.getGroupId());
		}

		if(groupNameTextFieldString != null && !groupNameTextFieldString.equalsIgnoreCase(""))
			SpriteList.getInstance().getSelectedSpriteModel().setGroupId(groupNameTextFieldString);
	}

}
