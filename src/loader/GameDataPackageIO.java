package loader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import model.SpriteModel;

import org.apache.log4j.Logger;

import action.GameAction;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import controller.GameController;
import controller.KeyListenerController;
import eventlistener.EventListener;

public class GameDataPackageIO {

    private static final Logger log = Logger.getLogger(GameDataPackageIO.class.getName());

    public static void saveGamePackageToFile(File file,GamePackage game) throws IOException {
      //  log.debug("saving game to: " + file.getName());

        XStream xstream = new XStream(new StaxDriver());
        xstream.alias("gameObject", SpriteModel.class);
        xstream.alias("gameAction", GameAction.class);
        xstream.alias("gameEventListener", EventListener.class);
        xstream.alias("game", GamePackage.class);
        xstream.alias("gameController", GameController.class);
        xstream.alias("keyController", KeyListenerController.class);
		xstream.useAttributeFor(GamePackage.class, "userName");
		xstream.useAttributeFor(GamePackage.class, "gameName");

        FileWriter writer = new FileWriter(file);

        xstream.toXML(game, writer);
        log.debug("saving done");
        
        
    }

    public static GamePackage loadGamePackage(Object object) {

        XStream xstream = new XStream(new StaxDriver());
        xstream.alias("gameObject", SpriteModel.class);
        xstream.alias("gameAction", GameAction.class);
        xstream.alias("gameEventListener", EventListener.class);
        xstream.alias("game", GamePackage.class);
        xstream.alias("gameController", GameController.class);
        xstream.alias("keyController", KeyListenerController.class);
        
        GamePackage gameCopy = null;
    	if(object instanceof File)
    		gameCopy = (GamePackage) xstream.fromXML((File)object);
    	else
    		gameCopy = (GamePackage) xstream.fromXML((String)object);
        log.debug("loading done");
        return gameCopy;
    }

    private GameDataPackageIO() {
    }
}
