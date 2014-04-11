package view.properties;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import utility.SpriteList;
import view.Design;
import view.panel.FieldPanel;

public class HeightPropertyDocumentListener implements DocumentListener {

	public void changedUpdate(DocumentEvent e) {
		update();
	}

	public void removeUpdate(DocumentEvent e) {
		update();
	}

	public void insertUpdate(DocumentEvent e) {
		update();
	}

	public void update() {
		//LOG.debug(velocityXTextField.getText());
		Design design=Design.getInstance();
		FieldPanel fieldPanel=FieldPanel.getInstance();
		String heightTextString = fieldPanel.getHeightTextField().getText();
		try {
			if(heightTextString != null && !heightTextString.equalsIgnoreCase("")){
				SpriteList.getInstance().getSelectedSpriteModel().setHeight(Double.valueOf(heightTextString));
				design.getGamePanel().repaint();
			}
		} catch (Exception e) {
			//LOG.debug("handling exception due to entering not numbers.");
		}
	}

}
