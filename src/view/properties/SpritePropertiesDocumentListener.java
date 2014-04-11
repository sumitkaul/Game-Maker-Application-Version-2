package view.properties;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import utility.SpriteList;
import view.Design;
import view.panel.ActionEventPanel;
import view.panel.FieldPanel;

public class SpritePropertiesDocumentListener implements DocumentListener{

	
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
		ActionEventPanel actionEventPanel=ActionEventPanel.getInstance();
		FieldPanel fieldPanel=FieldPanel.getInstance();
		String spriteNameTextFieldString = fieldPanel.getSpriteNameTextField().getText();
		String previousName = SpriteList.getInstance().getSelectedSpriteModel().getId();
		if(spriteNameTextFieldString != null && !spriteNameTextFieldString.equalsIgnoreCase("")){
			SpriteList.getInstance().getSelectedSpriteModel().setId(spriteNameTextFieldString);

			for(int i=0; i<design.getSpriteListIndividualModel().size(); i++){
				String textString = (String) design.getSpriteListIndividualModel().get(i);
				if(textString.equalsIgnoreCase(previousName)){
					design.getSpriteListIndividualModel().set(i, spriteNameTextFieldString);
				}
			}
			
			actionEventPanel.updateSpriteJList();
		}
	}
}