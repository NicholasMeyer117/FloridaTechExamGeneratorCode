import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import org.apache.commons.math3.linear.RealMatrix;

public class TestPanelManager {
	
	int relUnitX;
	int relUnitY;
	public TestPanelManager(int relUnitX, int relUnitY)
	{
		relUnitX = this.relUnitX;
		relUnitY = this.relUnitY;
	}
	
	public JPanel updateTestPanel(List<Question> questions, String updateCode, int numQuestions)
	{
		JPanel basicPanel = new JPanel(new GridBagLayout());
		GridBagConstraints cons = new GridBagConstraints();
		
		Border matteBorderBlack = BorderFactory.createMatteBorder(5, 3, 5, 3, Color.black);
		Border matteBorderRed = BorderFactory.createMatteBorder(2, 2, 2, 2, Color.red);
		
		cons.gridx = 0;
		cons.gridy = 0;
		cons.fill = cons.VERTICAL;
		JLabel imageIcon = new JLabel();
		basicPanel.add(imageIcon, cons);
		
		cons.gridx = 0;
		cons.gridy = 1;
		JLabel name = new JLabel("Question " + numQuestions + ": " + questions.get(questions.size() - 1).questionText);
		//n.setAlignmentY(Component.TOP_ALIGNMENT);
		//n.setAlignmentX(Component.LEFT_ALIGNMENT);
		basicPanel.add(name, cons);

		if (updateCode.equals("Matrix"))
		{
			cons.gridx = 0;
			cons.gridy = 2;
			cons.fill = cons.VERTICAL;
			JPanel newPanel = new JPanel();
			newPanel.setLayout(new BoxLayout(newPanel, BoxLayout.Y_AXIS));
			
			JPanel newQuestionPanel = new JPanel(new GridLayout());
			newQuestionPanel.setBorder(new EmptyBorder(50, 20, 50, 20));
			MatrixQuestion question = (MatrixQuestion) questions.get(questions.size() - 1);
			Matrix matrix1 = question.getMatrix(1);
			Matrix matrix2 = question.getMatrix(2);
			Matrix ansMatrix = question.getMatrix(3);
			JPanel matrixPanel1 = new JPanel(new GridLayout(matrix1.sizeX + 1, matrix1.sizeY));
			JPanel matrixPanel2 = new JPanel(new GridLayout(matrix2.sizeX + 1, matrix2.sizeY));
			JPanel matrixPanel3 = new JPanel(new GridLayout(matrix2.sizeX + 1, matrix2.sizeY));
			for (int i = 0; i <= matrix1.sizeX; i++)
			{
				for (int j = 0; j <= matrix1.sizeY; j++)
				{
					JTextField matrixNum = new JTextField(5);
					//matrixNum.setText(String.valueOf((matrix1.matrixNumbers.get(i).get(j))));
					matrixNum.setText(String.valueOf((matrix1.matrixData[i][j])));
					matrixPanel1.add(matrixNum);
				}
			}
			for (int i = 0; i <= matrix2.sizeX; i++)
			{
				for (int j = 0; j <= matrix2.sizeY; j++)
				{
					JTextField matrixNum = new JTextField(5);
					matrixNum.setText(String.valueOf((matrix2.matrixData[i][j])));
					matrixPanel2.add(matrixNum);
				
				}
			}
			
			for (int i = 0; i < ansMatrix.sizeX; i++)
			{
				for (int j = 0; j < ansMatrix.sizeY; j++)
				{
					JTextField matrixNum = new JTextField(5);
					matrixNum.setText(String.valueOf((ansMatrix.matrixData[i][j])));
					matrixPanel3.add(matrixNum);
				
				}
			}
			
			String[] optionsToChoose = {"X", "/", "+", "-"};
			JComboBox<String> jComboBox = new JComboBox<>(optionsToChoose);
			jComboBox.setBorder(new EmptyBorder(20,25,20,25));
			
			JLabel equals = new JLabel("=", SwingConstants.CENTER);

			matrixPanel1.setBorder(matteBorderBlack);
			matrixPanel2.setBorder(matteBorderBlack);
			newQuestionPanel.add(Box.createRigidArea(new Dimension(1, 1)));
			newQuestionPanel.add(matrixPanel1);
			newQuestionPanel.add(jComboBox);
			newQuestionPanel.add(matrixPanel2);
			newQuestionPanel.add(equals);
			newQuestionPanel.add(matrixPanel3);
			newQuestionPanel.add(Box.createRigidArea(new Dimension(1,1)));
			newPanel.add(newQuestionPanel);
			
			basicPanel.add(newPanel, cons);			
			
			
		}
		else if (updateCode.equals("Polynomial"))
		{
			cons.gridx = 0;
			cons.gridy = 2;
			cons.fill = GridBagConstraints.BOTH;
			cons.weightx = 1;
			cons.weighty = 1;
			cons.anchor = GridBagConstraints.NORTHWEST;
			JPanel newPanel = new JPanel();
			newPanel.setLayout(new BoxLayout(newPanel, BoxLayout.Y_AXIS));
			newPanel.setBorder(new EmptyBorder(50, 20, 50, 20));
			PolyQuestion question = (PolyQuestion) questions.get(questions.size() - 1);
			
			List<Poly> polyList = question.polyList;
			JPanel polyPanel = new JPanel(new FlowLayout());
			polyPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
			
			for (int i = 1; i < polyList.size(); i++)
			{
				JTextField polyStr = new JTextField();
				//matrixNum.setText(String.valueOf((matrix1.matrixNumbers.get(i).get(j))));
				polyStr.setText((polyList.get(i).polynomial).toString());
				polyPanel.add(polyStr);
				
				String[] optionsToChoose = {"X", "/", "+", "-"};
				JComboBox<String> jComboBox = new JComboBox<>(optionsToChoose);
				//jComboBox.setBorder(new EmptyBorder(20,25,20,25));
				
				if (i != polyList.size() - 1)
					polyPanel.add(jComboBox);
					
			}
			
			JLabel equals = new JLabel("=", SwingConstants.CENTER);
			JTextField polyStr = new JTextField();
			polyStr.setText((polyList.get(0).polynomial).toString());
			polyPanel.add(equals);
			polyPanel.add(polyStr);
			
			
			newPanel.add(polyPanel);
			//newPanel.setBorder(matteBorderRed);
			basicPanel.add(newPanel, cons);
			
			
			
		}
		else if (updateCode.equals("ShortAnswer")) 
		{
			cons.gridx = 0;
			cons.gridy = 2;
			cons.weighty = 0.5;
			cons.weightx = 0.5;
			cons.fill = cons.BOTH;
			JPanel newPanel = new JPanel();
			newPanel.setLayout(new BoxLayout(newPanel, BoxLayout.Y_AXIS));
			
			newPanel.setBorder(new EmptyBorder(10, 20, 20, 20));
			
			JTextArea answerBox = new JTextArea(50, 5);
			newPanel.add(answerBox);
			
			basicPanel.add(newPanel, cons);		
			
		}
		
		basicPanel.setBorder(matteBorderRed);
		return basicPanel;
	}
	
	

}
