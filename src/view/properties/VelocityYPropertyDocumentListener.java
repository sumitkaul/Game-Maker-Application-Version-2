package view.properties;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import utility.SpriteList;
import view.Design;
import view.panel.FieldPanel;

public class VelocityYPropertyDocumentListener implements DocumentListener {
	
	private static final org.apache.log4j.Logger LOG = 
			org.apache.log4j.Logger.getLogger(VelocityYPropertyDocumentListener.class);

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
				
		Design design=Design.getInstance();
		FieldPanel fieldPanel=FieldPanel.getInstance();
		LOG.debug(fieldPanel.getVelocityYTextField().getText());
		String velocityTextString = fieldPanel.getVelocityYTextField().getText();

		try {
			if(velocityTextString != null && !velocityTextString.equalsIgnoreCase(""))
				SpriteList.getInstance().getSelectedSpriteModel().setSpeedY(Double.parseDouble(velocityTextString));	

		} catch (Exception e) {
			LOG.debug("handling exception due to entering not numbers.");
		}
	}
}
