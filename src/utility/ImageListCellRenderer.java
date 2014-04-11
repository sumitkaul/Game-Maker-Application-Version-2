package utility;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;


public class ImageListCellRenderer implements ListCellRenderer
{
  
@Override
  public Component getListCellRendererComponent(JList jlist, 
                                                Object value, 
                                                int cellIndex, 
                                                boolean isSelected, 
                                                boolean cellHasFocus)
  {
    if (value instanceof JPanel)
    {
      Component component = (Component) value;
      component.setForeground (Color.white);
      component.setBackground (isSelected ? Color.gray : Color.white);
      return component;
    }
    else
    {
      return new JLabel("Sprite List");
    }
  }


}