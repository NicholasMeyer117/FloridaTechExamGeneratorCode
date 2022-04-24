import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import org.apache.commons.math3.linear.RealMatrix;

public class CreatorState {
	
	int exitCode;
	JButton btnMatrix=new JButton("Add Matrix Question");
	JButton btnExport=new JButton("Finish and Export Test");
	JButton btnCommit=new JButton("Commit Changes");
	int numQuestions = 0;
	int curQuestionNum = 0;
	JPanel mainPanel;
	JPanel settingsPanel;
	JPanel testPanel;
	JPanel questionPanel;
	JScrollPane scrollableTestPane;
	JPanel view;
	
	int relUnitX;
	int relUnitY;
	
	SettingsPanelManager settingsPanelManager = new SettingsPanelManager(relUnitX, relUnitY);
	TestPanelManager testPanelManager = new TestPanelManager(relUnitX, relUnitY);
	QuestionPanelManager questionPanelManager = new QuestionPanelManager(relUnitX, relUnitY);
	//CreatorStateManager manager = new CreatorStateManager(settingsPanelManager, testPanelManager, questionPanelManager);
	
	public int run(Frame frame)
	{ 
		
		exitCode = 0;
		List<Question> questionsList = new ArrayList<Question>();
		createPanel(frame);
		frame.add(mainPanel);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);  
		frame.setVisible(true);  
		mainPanel.setVisible(true);
		
		HTMLParser parser = new HTMLParser();
		
		
		btnMatrix.addActionListener(new ActionListener()
		{  
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				numQuestions++;
				curQuestionNum = numQuestions - 1;
				Question question = new MatrixQuestion("Solve for the unknown matrix", numQuestions, "Matrix");
				questionsList.add(question);
				JPanel newPanel = new JPanel();
				newPanel.setLayout(new BoxLayout(newPanel, BoxLayout.Y_AXIS));
				newPanel = testPanelManager.updateTestPanel(questionsList, "newMatrix", numQuestions);
				newPanel.setPreferredSize(new Dimension(relUnitX * 9, relUnitY * 5));
				newPanel.setMaximumSize(new Dimension(relUnitX * 9, relUnitY * 5));
				newPanel.setMinimumSize(new Dimension(relUnitX * 9, relUnitY * 5));
				view.add(newPanel);
				view.add(Box.createRigidArea(new Dimension(relUnitX, relUnitY)));
				testPanel.validate();
				scrollableTestPane.validate();
				settingsPanel.add(settingsPanelManager.addSettings("Matrix", question));
				settingsPanel.add(btnCommit);
				mainPanel.validate();
			}
	    }); 
		
		btnCommit.addActionListener(new ActionListener()
		{  
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				 
				if (questionsList.get(curQuestionNum).questionType.equals("Matrix"))
				{
					MatrixSettings curSettings = (MatrixSettings) settingsPanelManager.settingsList.get(curQuestionNum);
					JPanel curPanel = (JPanel) view.getComponent(curQuestionNum * 2);
					MatrixQuestion question = (MatrixQuestion) questionsList.get(curQuestionNum);
					//Component[] curSettingsComponents = ((Container) ((Container) ((Container) mainPanel.getComponent(0)).getComponent(0)).getComponent(0)).getComponents();
					/*((JLabel) curPanel.getComponent(0)).setText("Changed Settings");
					Component[] components;
				    String componentName;
				    components =  mainPanel.getComponents();            
				    for (Component compo : components) {
				        componentName = compo.getClass().getName();
				        System.out.println(compo.getClass().getName().substring(componentName.indexOf("swing.") + "swing.".length(), componentName.length()));
				    }
				    System.out.println("=====================");
				    components =  ((Container) ((Container) ((Container) mainPanel.getComponent(0)).getComponent(0)).getComponent(0)).getComponents();            
				    for (Component compo : components) {
				        componentName = compo.getClass().getName();
				        System.out.println(compo.getClass().getName().substring(componentName.indexOf("swing.") + "swing.".length(), componentName.length()));
				    }
				    System.out.println("=====================");*/
				    JPanel matrix1Panel = (JPanel) ((JPanel) curPanel.getComponent(1)).getComponent(1);
				    JPanel matrix2Panel = (JPanel) ((JPanel) curPanel.getComponent(1)).getComponent(3);
				    matrix1Panel.removeAll();
				    matrix2Panel.removeAll();
				    view.revalidate();
				    view.repaint();
				    Component[] curSettingsComponents = ((Container) ((Container) ((Container) mainPanel.getComponent(0)).getComponent(0)).getComponent(0)).getComponents();
					curSettings.m1d1 = Integer.valueOf(((JTextField) curSettingsComponents[1]).getText()) - 1;
					curSettings.m1d2 = Integer.valueOf(((JTextField) curSettingsComponents[3]).getText()) - 1;
					curSettingsComponents = ((Container) ((Container) ((Container) mainPanel.getComponent(0)).getComponent(0)).getComponent(1)).getComponents();
					curSettings.m2d1 = Integer.valueOf(((JTextField) curSettingsComponents[1]).getText()) - 1;
					curSettings.m2d2 = Integer.valueOf(((JTextField) curSettingsComponents[3]).getText()) - 1;
				    matrix1Panel.setLayout(new GridLayout(curSettings.m1d1 + 1, curSettings.m1d2 + 1));
				    matrix2Panel.setLayout(new GridLayout(curSettings.m2d1 + 1, curSettings.m2d2 + 1));
				    double [][] newMatrixData = new double[curSettings.m1d1 + 1][curSettings.m1d2 + 1];
				    System.out.println("Settings Dim: \n" + curSettings.m1d1 + " x " + curSettings.m1d2);
				    System.out.println("GridLayout Dim: \n" + (curSettings.m1d1 + 1) + " x " + (curSettings.m1d2 + 1));
				    System.out.println("matrix SizeX and SizeY: \n" + question.matrix1.sizeX + " x " +question.matrix1.sizeY);
				    System.out.println("matrix Data Dim: \n" + question.matrix1.matrixData.length + " x " +question.matrix1.matrixData[0].length);
				    System.out.println(" new matrix Data Dim: \n" + newMatrixData.length + " x " +newMatrixData[0].length);
				    System.out.println("\n=============================\n");
				    
				    for (int i = 0; i <= curSettings.m1d1; i++){
				    	for (int j = 0; j <= curSettings.m1d2; j++){
				    		JTextField matrixNum = new JTextField(5);
							if (i <= question.matrix1.sizeX && j <= question.matrix1.sizeY){
								matrixNum.setText(String.valueOf(question.matrix1.matrixData[i][j]));
								newMatrixData[i][j] = question.matrix1.matrixData[i][j]; }
							else{
								matrixNum.setText("0");
								newMatrixData[i][j] = 0; }
							matrix1Panel.add(matrixNum);}}
				    question.matrix1.matrixData = newMatrixData;
				    question.matrix1.sizeX = newMatrixData.length - 1;
				    question.matrix1.sizeY = newMatrixData[0].length - 1;
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
				    
				    view.revalidate();
				    view.repaint();
				    
				}
				
				
			}
	    }); 
		
		btnExport.addActionListener(new ActionListener()
		{  
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				parser.convert(questionsList);
			}
	    }); 
		
		while (true)
		{
			if (exitCode != 0)
				return exitCode;
		}
		
		
	}
	


	public JPanel createPanel(Frame frame)
	{
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int dimX = (int) (dimension.getWidth());
	    int dimY = (int) (dimension.getHeight() - 100);
	    
	    //Using these relative Units means that components will resize on different screen sizes
	    relUnitX = dimX/20; //relative X unit
	    relUnitY = dimY/20; //Relative Y unit
	    
		mainPanel = new JPanel(new GridBagLayout());
		mainPanel.setBackground(new Color(255,215,0));
		GridBagConstraints cons = new GridBagConstraints();
		Border matteBorderBlack = BorderFactory.createMatteBorder(5, 2, 5, 2, Color.black);
		mainPanel.setBorder(matteBorderBlack);
		
		settingsPanel = new JPanel();
		settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));
		settingsPanel.setBorder(matteBorderBlack);
		
		cons.gridx = 0;
		cons.gridy = 0;
		cons.fill = cons.BOTH;
		//settingsPanel.add(Box.createRigidArea(new Dimension(1, dimY)));
		settingsPanel.setPreferredSize(new Dimension(relUnitX * 5, dimY));
		settingsPanel.setMinimumSize(new Dimension(relUnitX * 5, dimY));
		mainPanel.add(settingsPanel, cons);
		
		testPanel = new JPanel();
		testPanel.setLayout(new BoxLayout(testPanel, BoxLayout.Y_AXIS));
		testPanel.setBorder(matteBorderBlack);
		
		cons.gridx = 1;
		cons.gridy = 0;
		cons.fill = cons.BOTH;
		view = new JPanel();
		view.setLayout(new BoxLayout(view, 1));
		view.setBackground(Color.white);
		scrollableTestPane = new JScrollPane(view);
		scrollableTestPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
		//testPanel.add(Box.createRigidArea(new Dimension(relUnitX * 10, 1)));
		testPanel.setPreferredSize(new Dimension(relUnitX * 10, dimY));
		testPanel.setMinimumSize(new Dimension(relUnitX * 10, dimY));
		testPanel.add(scrollableTestPane);
		Font font1 = new Font("Arial", Font.BOLD, 15);
		btnExport.setFont(font1);
		btnExport.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnExport.setVisible(true);
		testPanel.add(btnExport, Component.BOTTOM_ALIGNMENT);
		//testPanel.add(Box.createGlue());
		mainPanel.add(testPanel, cons);
		
		questionPanel = new JPanel();
		questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));
		questionPanel.setBorder(matteBorderBlack);
		
		cons.gridx = 2;
		cons.gridy = 0;
		//cons.fill = cons.BOTH;
		questionPanel.setPreferredSize(new Dimension(relUnitX * 5, dimY));
		questionPanel.setMinimumSize(new Dimension(relUnitX * 5, dimY));
		btnMatrix.setFont(font1);
		btnMatrix.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnMatrix.setVisible(true);
		questionPanel.add(questionPanelManager.addQuestionTypes());
		questionPanel.add(Box.createRigidArea(new Dimension(1, relUnitY)));
		questionPanel.add(Box.createVerticalStrut(relUnitY));
		questionPanel.add(btnMatrix);
		questionPanel.add(Box.createRigidArea(new Dimension(1, dimY)));
		mainPanel.add(questionPanel, cons);
		
		ComponentCreator creator = new ComponentCreator();
		
		
		return mainPanel;
	}

}
