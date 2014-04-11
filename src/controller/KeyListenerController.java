package controller;

import eventlistener.EventListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class KeyListenerController implements KeyListener {

    private List<EventListener> keyEvents;
    private static int keyPressCount = 0;

    public KeyListenerController() {
    	this.keyEvents = new ArrayList<EventListener>();
    }
    
    
    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        int pressed = ke.getKeyCode();
        if(keyEvents!=null){
        for (EventListener event : keyEvents) {
        	HashMap<String,Object> map = new HashMap<String,Object>();
        	if (keyPressCount-map.size() <5)
        	{
        	map.put("keypressed", new Integer(pressed));
        	keyPressCount+=1;
        	if(event!=null)
        	event.checkEvent(map);
        	}
        }
       }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
    }
    
    public void registerListener(EventListener listener){
    	this.keyEvents.add(listener);
    }
    
    public void unregisterListener(EventListener listener){
    	this.keyEvents.remove(listener);
    }
	public List<EventListener> getKeyEvents() {
        return keyEvents;
    }
	
    public void setKeyEvents(List<EventListener> keyEvents) {
        this.keyEvents = keyEvents;
    }
}
