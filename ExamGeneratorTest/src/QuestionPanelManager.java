import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class QuestionPanelManager {
	
	int relUnitX;
	int relUnitY;
	int type = 0; //General = 0, Math = 1, Physics = 2, Chemistry = 3
	JButton btnShortAnswer;
	JButton btnMultipleChoice;
	
	JButton btnMatrix;
	
	public QuestionPanelManager(int relX, int relY, JButton bShortAnswer, JButton bMultipleChoice, JButton bMatrix)
	{
		relUnitX = relX;
		relUnitY = relY;
		btnMultipleChoice = bMultipleChoice;
		btnShortAnswer = bShortAnswer;
		btnMatrix = bMatrix;
	}
	
	public JPanel addQuestionTypes(JButton bGen, JButton bMath, JButton bPhysics, JButton bChem)
	{
		JPanel options = new JPanel(new FlowLayout());
		JButton btnGen = bGen;
		JButton btnMath = bMath;
		JButton btnPhysics = bPhysics;
		JButton btnChem = bChem;
		Border border = BorderFactory.createMatteBorder(1, 1, 5, 1, Color.black);
		//JButton BtnCompSci = new JButton ("Comp Sci");
		
		options.add(btnGen);
		options.add(btnMath);
		options.add(btnPhysics);
		options.add(btnChem);
		options.setBorder(border);
		//options.add(BtnCompSci);
		
		
		return options;
	}
	
	public JPanel addQuestions()
	{
		
		JPanel questionsPanel = new JPanel();
		questionsPanel.setLayout(new BoxLayout(questionsPanel, BoxLayout.Y_AXIS));
		Font font1 = new Font("Arial", Font.BOLD, 15);
		
		//questionsPanel.setBorder(matteBorderBlack);
		
		if (type == 0)
		{
			questionsPanel.add(Box.createVerticalStrut(relUnitY));
			
			btnShortAnswer.setFont(font1);
			btnShortAnswer.setAlignmentX(Component.CENTER_ALIGNMENT);
			btnShortAnswer.setVisible(true);
			questionsPanel.add(btnShortAnswer); 

			questionsPanel.add(Box.createVerticalStrut(relUnitY));
			
			btnMultipleChoice.setFont(font1);
			btnMultipleChoice.setAlignmentX(Component.CENTER_ALIGNMENT);
			btnMultipleChoice.setVisible(true);
			questionsPanel.add(btnMultipleChoice);
		}
		else if (type == 1)
		{
            questionsPanel.add(Box.createVerticalStrut(relUnitY));
			
			btnMatrix.setFont(font1);
			btnMatrix.setAlignmentX(Component.CENTER_ALIGNMENT);
			btnMatrix.setVisible(true);
			questionsPanel.add(btnMatrix); 	
		}
		
		
		return questionsPanel;
		
	}

}
