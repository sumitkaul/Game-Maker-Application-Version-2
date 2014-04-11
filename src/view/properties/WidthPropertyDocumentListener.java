package view.properties;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import utility.SpriteList;
import view.Design;
import view.panel.FieldPanel;

public class WidthPropertyDocumentListener implements DocumentListener {
	
	private static final org.apache.log4j.Logger LOG = 
			org.apache.log4j.Logger.getLogger(WidthPropertyDocumentListener.class);


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
		LOG.debug(fieldPanel.getWidthTextField().getText());
		String widthTextString = fieldPanel.getWidthTextField().getText();
		try {
			if(widthTextString != null && !widthTextString.equalsIgnoreCase("")){
				SpriteList.getInstance().getSelectedSpriteModel().setWidth(Double.valueOf(widthTextString));
				design.getGamePanel().repaint();
			}
		} catch (Exception e) {
			LOG.debug("handling exception due to entering not numbers.");
		}
	}

}
