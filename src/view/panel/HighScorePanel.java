package view.panel;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

public class HighScorePanel 
{
	private JFrame maineFrame;
	private JPanel highScorePanel;
	private ArrayList<String> scoreList;
	private int[] usersList;
	
	public HighScorePanel(ArrayList<String> scoreList ) {
		this.maineFrame = new JFrame();
		this.highScorePanel = new JPanel();
		this.scoreList = scoreList;
		
		
		
		highScorePanel.setLayout(new GridLayout(this.scoreList.size()+1,3));
		
		highScorePanel.add(new JLabel("User"));
//		highScorePanel.add(new JLabel("Game"));
		highScorePanel.add(new JLabel("Score"));
//		
		for(int i = 0;i< this.scoreList.size(); i++) {
			
			//String tempUser = (String) this.usersList[i];
			String tempScore = (String) this.scoreList.get(i);
			String user = tempScore.split(":")[0];
			highScorePanel.add(new JLabel(user));
			String score = tempScore.split(":")[1];
			//highScorePanel.add(new JLabel(tempGame));
			highScorePanel.add(new JLabel(tempScore));
		}
		
		JScrollPane scrollPane = new JScrollPane(highScorePanel);
		this.maineFrame.getContentPane().add(scrollPane);
		this.maineFrame.setTitle("High Scores ");
		this.maineFrame.setSize(300,250);
		this.maineFrame.setLayout(new GridLayout(1, 2));
		this.maineFrame.setVisible(true);
		this.maineFrame.validate();
		this.maineFrame.setLocationRelativeTo(null);
	}

}
