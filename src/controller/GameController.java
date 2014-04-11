package controller;

import eventlistener.EventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import model.SpriteModel;
import utility.ClockDisplay;
import utility.SpriteList;
import view.panel.GamePanel;

public class GameController implements ActionListener {

	private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
			.getLogger(GameController.class);

	private GamePanel gamePanel;
	private ArrayList<EventListener> events;

	public GameController() {
		this.events = new ArrayList<EventListener>();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		

		if (events != null) {
			for (EventListener event : events) {
				if (event != null)
					event.checkEvent(null);
			}
		}

		for (SpriteModel model : SpriteList.getInstance()
				.getToBeRemovedSpriteModels()) {
			SpriteList.getInstance().removeSprite(model);
		}
		SpriteList.getInstance().getToBeRemovedSpriteModels().clear();
		gamePanel.repaint();
		gamePanel.requestFocusInWindow();
		ClockDisplay.getInstance().updateClock();
	}

	public GamePanel getGamePanel() {
		return gamePanel;
	}

	public void setGamePanel(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}

	public void registerListener(EventListener listener) {
		if(events != null)
			this.events.add(listener);
	}

	public void unregisterListener(EventListener listener) {
		this.events.remove(listener);

	}

	public ArrayList<EventListener> getEvents() {
		return events;
	}

	public void setEvents(List<EventListener> events) {
		this.events = (ArrayList<EventListener>) events;
	}
}
