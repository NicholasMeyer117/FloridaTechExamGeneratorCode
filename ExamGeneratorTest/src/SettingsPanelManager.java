import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
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
	
	public void refreshSettings(String type, int questionNum, JPanel settingsPanel)
	{
		if (type.equals("Polynomial"))
		{
			JLabel numPolyLabel = (JLabel) ((JPanel) ((JPanel) settingsPanel.getComponent(0)).getComponent(1)).getComponent(0);
			//JPanel matrix1Panel = (JPanel) ((JPanel) curPanel.getComponent(0)).getComponent(1);
			PolySettings curSettings = (PolySettings) settingsList.get(questionNum);
			numPolyLabel.setText("Polynomials: " + curSettings.numOfPolynomials);
		}
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
		
		else if (type.equals("ShortAnswer"))
		{
			
		}
		
		else if (type.equals("Polynomial"))
		{
			PolySettings newSettings;
			PolyQuestion curQ =(PolyQuestion) question;
			if (newQuestion == true)
				newSettings = new PolySettings(type, question);
			else 
				newSettings = (PolySettings) settingsList.get(curQ.questionNum);

			JLabel numPolyLabel = new JLabel();
			numPolyLabel.setText("Polynomials: " + newSettings.numOfPolynomials);
			
			JButton btnAddPoly = new JButton ("+");
			JButton btnRemovePoly = new JButton ("-");
			
			btnAddPoly.addActionListener(new ActionListener()
			{  
				@Override
				public void actionPerformed(ActionEvent e) 
				{
                    newSettings.numOfPolynomials++;
                    System.out.println(newSettings.numOfPolynomials);
                    Poly newPoly = new Poly();
                    curQ.polyList.add(newPoly); 
                    
				}
		    });
			
			btnRemovePoly.addActionListener(new ActionListener()
			{  
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					newSettings.numOfPolynomials--;
				}
		    });

			JPanel newPolyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			newPolyPanel.add(numPolyLabel);
			newPolyPanel.add(btnAddPoly);
			newPolyPanel.add(btnRemovePoly);
			newPanel.add(newPolyPanel);
			
			if(newQuestion) {
				newSettings.numOfPolynomials = curQ.polyList.size() - 1;

				settingsList.add(newSettings);
			}
		}
		
		newPanel.setAlignmentY(Component.TOP_ALIGNMENT);
		newPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		newPanel.add(Box.createHorizontalGlue());
		return newPanel;
		
	}
	
	
}
