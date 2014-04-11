/**
 * 
 */
package utility;

import java.util.ArrayList;
import java.util.List;

import view.Design;
import view.panel.FieldPanel;



public class Layers {

	private static Layers layer;
	public static Layers getInstance() {
		if(layer==null)
			layer = new Layers();
		return layer;
	}
	List<String> layers;
	
	public List<String> getLayers() {
		return layers;
	}

	public void setLayers(List<String> layers) {
		this.layers = layers;
	}
	
	@SuppressWarnings("unchecked")
	public String addNewLayer() {
		String layer = "Layer "+layers.size();
		this.layers.add(layer);
		
		FieldPanel.getInstance().getLayerBox().addItem(layer);
		
		return layer;
	}

	public Layers() {
		layers = new ArrayList<String>();
		layers.add(Constants.ALL_LAYERS);
		layers.add("Layer 1");
	}
	
	public void addLayer(String layerName){
		this.layers.add(layerName);
		FieldPanel.getInstance().getLayerBox().addItem(layerName);
	}
	
	public void clearAllLayers(){
		List<String> tobeRemovedLayers = new ArrayList<String>();
		for(String layer:layers){
			if(!layer.equalsIgnoreCase(Constants.ALL_LAYERS) && !layer.equalsIgnoreCase("Layer 1")){
				FieldPanel.getInstance().getLayerBox().removeItem(layer);
				tobeRemovedLayers.add(layer);
			}
		}
		
		for(int i=0; i<tobeRemovedLayers.size(); i++){
			layers.remove(i);
		}
	}
	
	
}
