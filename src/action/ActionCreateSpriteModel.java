package action;

import java.util.List;

import controller.GameController;

import eventlistener.EventListener;
import eventlistener.NewFrameEventListener;
import utility.Helper;
import utility.SpriteList;
import view.Design;
import model.SpriteModel;

public class ActionCreateSpriteModel implements GameAction {

	public void doAction(SpriteModel parentSprite) {

		double heading = parentSprite.getHeading();
		double cosValue = Math.cos(heading * Math.PI / 180) * 10;
		double sinValue = Math.sin(heading * Math.PI / 180) * 10;

		double x = sinValue;
		double y = -cosValue;

		GameController gameController = Design.getInstance().getFacade()
				.getGameController();

		SpriteModel newSpriteModel = new SpriteModel(
				(parentSprite.getPosX() + (parentSprite.getWidth() / 2)),
				(parentSprite.getPosY() + 5), x, y, 7, 12,
				parentSprite.getImageUrlString(), parentSprite.getLayer());

		SpriteList.getInstance().addSprite(newSpriteModel);
		Design.getInstance().getFacade().addSpriteModelToView(newSpriteModel);
		newSpriteModel.setGroupId("Bomb");

		boolean foundBombMoveListener = false;
		List<EventListener> listenerList = Design.getInstance().getFacade()
				.getGameController().getEvents();
		for (EventListener listener : listenerList) {
			if (listener instanceof NewFrameEventListener) {
				NewFrameEventListener newFrameListerner = (NewFrameEventListener) listener;
				String groupId = newFrameListerner.getRegisteredGroupId();
				if (groupId.equalsIgnoreCase("Bomb"))
					foundBombMoveListener = true;
			}

		}

		if (!foundBombMoveListener) {
			EventListener listener = Helper.getsharedHelper()
					.getEventListenerForString("New Frame", "Move",
							newSpriteModel, null);
			gameController.registerListener(listener);
		}

		List<SpriteModel> spriteList = SpriteList.getInstance().getSpriteList();
		for (SpriteModel sprite : spriteList) {
			if ((!sprite.getGroupId().equalsIgnoreCase("Bomb"))
					&& sprite != parentSprite) {
				EventListener listener1 = Helper.getsharedHelper()
						.getEventListenerForString("Collision", "remove",
								newSpriteModel, sprite);
				gameController.registerListener(listener1);

				EventListener listener2 = Helper.getsharedHelper()
						.getEventListenerForString("Collision", "Play Sound",
								newSpriteModel, sprite);
				gameController.registerListener(listener2);

				EventListener listener3 = Helper.getsharedHelper()
						.getEventListenerForString("Collision", "remove",
								sprite, newSpriteModel);
				gameController.registerListener(listener3);

				EventListener listener4 = Helper.getsharedHelper()
						.getEventListenerForString("Collision", "Update Score",
								sprite, newSpriteModel);
				gameController.registerListener(listener4);

			}
		}
	}
}