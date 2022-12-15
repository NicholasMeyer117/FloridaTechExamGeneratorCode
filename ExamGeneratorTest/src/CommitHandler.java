import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.commons.math3.linear.MatrixUtils;

public class CommitHandler {
	
	public MatrixQuestion commitMatrixChanges(JPanel view, JPanel mainPanel, MatrixSettings curSettings, JPanel curPanel, MatrixQuestion question)
	{
		//matrix1Panel and matrix2Panel are the two JPanels in the TestPanel that hold the matrices to be multiplied
		//matrix 3 is the answer matrix JPanel
	    JPanel matrix1Panel = (JPanel) ((JPanel) curPanel.getComponent(0)).getComponent(1);
	    JPanel matrix2Panel = (JPanel) ((JPanel) curPanel.getComponent(0)).getComponent(3);
	    JPanel matrix3Panel = (JPanel) ((JPanel) curPanel.getComponent(0)).getComponent(5);
	    
	    Matrix matrix1 = question.getMatrix(1);
		Matrix matrix2 = question.getMatrix(2);
		Matrix ansMatrix = question.getMatrix(3);

		//System.out.println("Matrix 1");
		for (int i = 0; i <= matrix1.sizeX; i++)
		{
			for (int j = 0; j <= matrix1.sizeY; j++)
			{
				double value = Double.valueOf(((JTextField) matrix1Panel.getComponent(((matrix1.sizeX + 1) * i) + j)).getText());
				//System.out.println(i + "," + j + "Value: " + value);
				matrix1.matrixData[i][j] = value;
			}
		}
		//System.out.println("Matrix 2");
		for (int i = 0; i <= matrix2.sizeX; i++)
		{
			for (int j = 0; j <= matrix2.sizeY; j++)
			{
				double value = Double.valueOf(((JTextField) matrix2Panel.getComponent(((matrix2.sizeX + 1) * i) + j)).getText());
				//System.out.println(i + "," + j + "Value: " + value);
				matrix2.matrixData[i][j] = value;
			}
		}
	    
	    
	    matrix1Panel.removeAll();
	    matrix2Panel.removeAll();
	    matrix3Panel.removeAll();
	    view.revalidate();
	    view.repaint();
	    
	    Component[] curSettingsComponents = ((Container) ((Container) ((Container) mainPanel.getComponent(0)).getComponent(0)).getComponent(1)).getComponents();
		curSettings.m1d1 = Integer.valueOf(((JTextField) curSettingsComponents[1]).getText()) - 1;
		curSettings.m1d2 = Integer.valueOf(((JTextField) curSettingsComponents[3]).getText()) - 1;
		curSettingsComponents = ((Container) ((Container) ((Container) mainPanel.getComponent(0)).getComponent(0)).getComponent(2)).getComponents();
		curSettings.m2d1 = Integer.valueOf(((JTextField) curSettingsComponents[1]).getText()) - 1;
		curSettings.m2d2 = Integer.valueOf(((JTextField) curSettingsComponents[3]).getText()) - 1;
	    matrix1Panel.setLayout(new GridLayout(curSettings.m1d1 + 1, curSettings.m1d2 + 1));
	    matrix2Panel.setLayout(new GridLayout(curSettings.m2d1 + 1, curSettings.m2d2 + 1));
	    
	    double [][] newMatrixData = new double[curSettings.m1d1 + 1][curSettings.m1d2 + 1];			    
	    for (int i = 0; i <= curSettings.m1d1; i++)
	    {
	    	for (int j = 0; j <= curSettings.m1d2; j++)
	    	{
	    		JTextField matrixNum = new JTextField(5);
				if (i <= question.matrix1.sizeX && j <= question.matrix1.sizeY){
					matrixNum.setText(String.valueOf(question.matrix1.matrixData[i][j]));
					newMatrixData[i][j] = question.matrix1.matrixData[i][j]; }
				else{
					matrixNum.setText("0");
					newMatrixData[i][j] = 0; }
				matrix1Panel.add(matrixNum);
			}
	    }
	    
	    System.out.println(question.matrix1.sizeX);
	    question.matrix1.matrixData = newMatrixData;
	    question.matrix1.sizeX = newMatrixData.length - 1;
	    question.matrix1.sizeY = newMatrixData[0].length - 1;
	    question.matrix1.matrix = MatrixUtils.createRealMatrix(newMatrixData);
	    System.out.println(question.matrix1.sizeX);
	    
	    newMatrixData = new double[curSettings.m2d1 + 1][curSettings.m2d2 + 1];
	    for (int i = 0; i <= curSettings.m2d1; i++){
	    	for (int j = 0; j <= curSettings.m2d2; j++){
	    		JTextField matrixNum = new JTextField(5);
				if (i <= question.matrix2.sizeX && j <= question.matrix2.sizeY){
					matrixNum.setText(String.valueOf(question.matrix2.matrixData[i][j]));
					newMatrixData[i][j] = question.matrix2.matrixData[i][j]; }
				else{
					matrixNum.setText("0");
					newMatrixData[i][j] = 0; }
				matrix2Panel.add(matrixNum);}}
	    
	    question.matrix2.matrixData = newMatrixData;
	    question.matrix2.sizeX = newMatrixData.length - 1;
	    question.matrix2.sizeY = newMatrixData[0].length - 1;
	    question.matrix2.matrix = MatrixUtils.createRealMatrix(newMatrixData);
	    
	    //Recalculate answer matrix based on new matrices
	    System.out.println(question.answerMatrix.sizeX);
	    question.answerMatrix = question.compute();
	    System.out.println(question.answerMatrix.sizeX);
	    matrix3Panel.setLayout(new GridLayout(question.answerMatrix.sizeX, question.answerMatrix.sizeY));
	    for (int i = 0; i < question.answerMatrix.sizeX; i++)
		{
			for (int j = 0; j < question.answerMatrix.sizeY; j++)
			{
				JTextField matrixNum = new JTextField(5);
				matrixNum.setText(String.valueOf((question.answerMatrix.matrixData[i][j])));
				matrix3Panel.add(matrixNum);
			
			}
		}
	    
	    view.revalidate();
	    view.repaint();
	    
	    return question;
	}
	
    public PolyQuestion commitPolyChanges(JPanel view, JPanel mainPanel, PolySettings curSettings, JPanel curPanel, PolyQuestion question)
	{
    	Component[] components = view.getComponents();  
		String componentName;
    	System.out.println("=======================");
		components = curPanel.getComponents();  
		for (Component compo : components) {
            componentName = compo.getClass().getName();
            System.out.println(compo.getClass().getName().substring(componentName.indexOf("swing.") + "swing.".length(), componentName.length()));
        }
		
		System.out.println("=======================");
		JPanel polyPanel = (JPanel) curPanel.getComponent(0);
		components = polyPanel.getComponents();  
		for (Component compo : components) {
            componentName = compo.getClass().getName();
            System.out.println(compo.getClass().getName().substring(componentName.indexOf("swing.") + "swing.".length(), componentName.length()));
        }
		
		for (int i = 1; i <  question.polyList.size(); i++)
		{
			Poly poly = question.polyList.get(i);
			//curSettings.m1d1 = Integer.valueOf(((JTextField) curSettingsComponents[1]).getText()) - 1;
			//Poly newPoly = new Poly();
			//question.polyList.set(i, poly)
			String polyStr = ((JTextField) components[0]).getText();
			System.out.println(polyStr);
			double[] coeff = question.stringToPoly(polyStr);
			for (double element: coeff)
			{
				System.out.println(String.valueOf(element));
			}
			
		}
		
		return question;
	}
}
