import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SettingsPanelManager {
	
	int relUnitX;
	int relUnitY;
	public SettingsPanelManager(int relUnitX, int relUnitY)
	{
		relUnitX = this.relUnitX;
		relUnitY = this.relUnitY;
	}
	
	public JPanel addSettings(String type, Question question)
	{
		JPanel newPanel = new JPanel();
		newPanel.setLayout(new BoxLayout(newPanel, BoxLayout.Y_AXIS));
		newPanel.setBorder(BorderFactory.createLineBorder(Color.green));
		if (type.equals("Matrix"))
		{
			JPanel matrix1Panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			matrix1Panel.add(new JLabel("Matrix 1: "));
			JTextField dim1 = new JTextField(5);
			matrix1Panel.add(dim1);
			matrix1Panel.add(new JLabel("X"));
			JTextField dim2 = new JTextField(5);
			matrix1Panel.add(dim2);
			matrix1Panel.setAlignmentY(Component.TOP_ALIGNMENT);
			
			JPanel matrix2Panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			matrix2Panel.add(new JLabel("Matrix 2: "));
			JTextField dim3 = new JTextField(5);
			matrix2Panel.add(dim3);
			matrix2Panel.add(new JLabel("X"));
			JTextField dim4 = new JTextField(5);
			matrix2Panel.add(dim4);
			matrix2Panel.setAlignmentY(Component.TOP_ALIGNMENT);
			
			newPanel.add(matrix1Panel);
			newPanel.add(matrix2Panel);
			
		}
		
		newPanel.setAlignmentY(Component.TOP_ALIGNMENT);
		return newPanel;
		
	}
}
