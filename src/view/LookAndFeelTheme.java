package view;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

import net.infonode.gui.laf.InfoNodeLookAndFeel;


import utility.enums.LookAndFeelType;

public final class LookAndFeelTheme {

	private static LookAndFeelTheme instance=new LookAndFeelTheme();

	public static LookAndFeelTheme getInstanceOf()
	{
		return instance;
	}
	private LookAndFeelTheme()
	{

	}
	public void setLookAndFeel(LookAndFeelType type)
	{
		String lnf = null;


		if (type.equals(LookAndFeelType.TATTOO)) {
			lnf = "com.jtattoo.plaf.smart.SmartLookAndFeel";

		}

		else if (type.equals(LookAndFeelType.SYSTEM)) {
			lnf = UIManager.getSystemLookAndFeelClassName();
		} 
		else if (type.equals(LookAndFeelType.METAL)) { 
			lnf = "javax.swing.plaf.metal.MetalLookAndFeel";
		}
		else if (type.equals(LookAndFeelType.MOTIF)) { 
			lnf = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
		}

		else if (type.equals(LookAndFeelType.GTK)) { 
			lnf = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
		} 


		else if (type.equals(LookAndFeelType.NIMRODLF)) { 
			lnf = "com.nilo.plaf.nimrod.NimRODLookAndFeel";
		} 

		else if (type.equals(LookAndFeelType.TINY)) { 
			lnf = "de.muntjak.tinylookandfeel.TinyLookAndFeel";
		} 

		else if (type.equals(LookAndFeelType.SMOOTHMETAL)) { 
			lnf = "smooth.windows.SmoothLookAndFeel";
		} 

		try {

			UIManager.setLookAndFeel(lnf);
			SwingUtilities.updateComponentTreeUI(Design.getInstance().getBaseFrame());

			Design.getInstance().getBaseFrame().pack();

			if (type.equals(LookAndFeelType.INFO)) {
				UIManager.setLookAndFeel(new InfoNodeLookAndFeel()); 
			}	
		} 

		catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Couldn't find class for specified look and feel:"
					+ type);
		} 
		catch (Exception e) {
			lnf = UIManager.getCrossPlatformLookAndFeelClassName();

		}
	}
}


