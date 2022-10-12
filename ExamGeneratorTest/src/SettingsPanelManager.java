import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SettingsPanelManager {
	
	int relUnitX;
	int relUnitY;
	//JButton btnCommit;// = new JButton ("Commit Changes");
	List<Settings> settingsList = new ArrayList<Settings>();
	
	public SettingsPanelManager(int relUnitX, int relUnitY)
	{
		relUnitX = this.relUnitX;
		relUnitY = this.relUnitY;
	}
	
	public JPanel addSettings(String type, Question question, boolean newQuestion)
	{
		JPanel newPanel = new JPanel();
		newPanel.setLayout(new BoxLayout(newPanel, BoxLayout.Y_AXIS));
		newPanel.setBorder(BorderFactory.createLineBorder(Color.green));
		newPanel.add(new JLabel("Question: " + Integer.toString(question.questionNum + 1)));
		if (type.equals("Matrix"))
		{
			MatrixSettings newSettings = new MatrixSettings(type, question);
			MatrixQuestion curQ = (MatrixQuestion) question;
			JPanel matrix1Panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			matrix1Panel.add(new JLabel("Matrix 1: "));
			JTextField dim1 = new JTextField(5);
			dim1.setText(String.valueOf(curQ.matrix1.sizeX + 1));
			matrix1Panel.add(dim1);
			matrix1Panel.add(new JLabel("X"));
			JTextField dim2 = new JTextField(5);
			dim2.setText(String.valueOf(curQ.matrix1.sizeY + 1));
			matrix1Panel.add(dim2);
			matrix1Panel.setAlignmentY(Component.TOP_ALIGNMENT);
			
			JPanel matrix2Panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			matrix2Panel.add(new JLabel("Matrix 2: "));
			JTextField dim3 = new JTextField(5);
			dim3.setText(String.valueOf(curQ.matrix2.sizeX + 1));
			matrix2Panel.add(dim3);
			matrix2Panel.add(new JLabel("X"));
			JTextField dim4 = new JTextField(5);
			dim4.setText(String.valueOf(curQ.matrix2.sizeY + 1));
			matrix2Panel.setAlignmentY(Component.TOP_ALIGNMENT);
			matrix2Panel.add(dim4);
			
			newPanel.add(matrix1Panel);
			newPanel.add(matrix2Panel);
			
			if (newQuestion)
			{
				newSettings.m1d1 = Integer.valueOf(dim1.getText());
				newSettings.m1d2 = Integer.valueOf(dim2.getText());
				newSettings.m2d1 = Integer.valueOf(dim3.getText());
				newSettings.m2d2 = Integer.valueOf(dim4.getText());
			
				settingsList.add(newSettings);
			}
			
			
		}
		else if(type.equals("Polynomial")) {
			PolySettings newSettings = new PolySettings(type, question);
			PolyQuestion curQ =(PolyQuestion) question;
			JPanel poly1Panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			poly1Panel.add(new JLabel("Polynomial 1: "));
			
			JTextField poly1 = new JTextField(5);
			poly1.setText(String.valueOf(curQ.polynomial_1.polynomial.toString()));
			poly1Panel.add(poly1);
			poly1Panel.add(new JLabel("+"));
			
			JTextField poly2 = new JTextField(5);
			poly2.setText(String.valueOf(curQ.polynomial_2.polynomial.toString()));
			poly1Panel.add(poly2);
			
			newPanel.add(poly1Panel);
			
			if(newQuestion) {
				newSettings.poly1Coeff = String.valueOf(poly1.getText());
				newSettings.poly2Coeff = String.valueOf(poly2.getText());
				
				settingsList.add(newSettings);
			}
		}
		
		
		newPanel.setAlignmentY(Component.TOP_ALIGNMENT);
		return newPanel;
		
	}
}
