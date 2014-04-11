package action;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

import model.SpriteModel;

public class ActionPlaySound implements GameAction{

    private String soundFile;
    @XStreamOmitField
    private AudioClip sound;

    public ActionPlaySound(String soundFile) {
        super();
        this.soundFile = soundFile;
        URL url = ActionPlaySound.class.getResource("/sounds/" + soundFile);
        this.sound = Applet.newAudioClip(url);
        
    }

    @Override
    public void doAction(SpriteModel model) {
    	if(sound==null){
    		URL url = ActionPlaySound.class.getResource("/sounds/" + soundFile);
    		this.sound = Applet.newAudioClip(url);
    	}
        this.sound.play();
    }
}
