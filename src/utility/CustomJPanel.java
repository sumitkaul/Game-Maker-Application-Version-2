package utility;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class CustomJPanel extends JPanel {
	
	public CustomJPanel() {
	
	}

	public CustomJPanel(FlowLayout flowLayout) {
		super(flowLayout);
	}
	public String getJLabelText() {
		String labelString = "";
		labelString=((JLabel) this.getComponent(0)).getText();
		return labelString;
	}
}
