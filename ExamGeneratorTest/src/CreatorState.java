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
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.JFileChooser;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

public class CreatorState {
	
	//global variables
	int exitCode; //run method returns exit code when returning to application loop to determine what state to enter next
	JButton btnMatrix=new JButton("Add Matrix Question");
	JButton btnExport=new JButton("Finish and Export Test");
	JButton btnCommit=new JButton("Commit Changes");
	JButton btnImage = new JButton ("Add Image");
	
	int numQuestions = 0; //total number of questions in test
	int curQuestionNum = 0; //number of question in questionList being edited at the moment
	
	//main panel is separated into 3 panels. From left to right they are:
	//settingsPanel allows user to edit current question
	//testPanel displays what the test looks like
	//questionPanel allows user to add questions to the test
	JPanel mainPanel;
	JPanel settingsPanel;
	JPanel testPanel;
	JPanel questionPanel;
	JScrollPane scrollableTestPane;
	JPanel view;
	
	//relative units based on screen specs to preserve scaling
	int relUnitX;
	int relUnitY;
	
	//managers for the respective panels
	SettingsPanelManager settingsPanelManager = new SettingsPanelManager(relUnitX, relUnitY);
	TestPanelManager testPanelManager = new TestPanelManager(relUnitX, relUnitY);
	QuestionPanelManager questionPanelManager = new QuestionPanelManager(relUnitX, relUnitY);
	List<Question> questionsList = new ArrayList<Question>();
	
	//main code block for the creator state
	public int run(Frame frame)
	{ 
		
		exitCode = 0;
		createPanel(frame);
		frame.add(mainPanel);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);  
		frame.setVisible(true);  
		mainPanel.setVisible(true);
		
		HTMLParser parser = new HTMLParser();
		
		//Btn that creates new Matrix Question and adds it to the test
		btnMatrix.addActionListener(new ActionListener()
		{  
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				Question question = new MatrixQuestion("Solve for the unknown matrix", numQuestions, "Matrix");
				addQuestion(question);

			}
	    }); 
		
		btnImage.addActionListener(new ActionListener()
		{  
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				System.out.println("image uploaded opened");
                JFileChooser fileChooser = new JFileChooser ();
                fileChooser.showOpenDialog(null);
                File f = fileChooser.getSelectedFile();
                
                Question question = questionsList.get(curQuestionNum);
                question.imageFilename = f.getAbsolutePath();
                
                try {
                	File imageFile = new File(question.imageFilename);
                	BufferedImage imgBuf = ImageIO.read(imageFile);
                	int imgHeight = imgBuf.getHeight();
                	double dScaleWidth = getScaleFactor(imgBuf.getWidth(), relUnitX * 9);
                	int scaleWidth = (int) Math.round(imgBuf.getWidth() * dScaleWidth);
                	Image img = imgBuf.getScaledInstance(scaleWidth, imgHeight, imgBuf.SCALE_SMOOTH);
                	question.format = new ImageIcon(img);
                	

					JPanel curPanel = (JPanel) view.getComponent(curQuestionNum * 2);
					JLabel curLabel = (JLabel) curPanel.getComponent(0);
					curLabel.setIcon(question.format);
					curPanel.setPreferredSize(new Dimension(relUnitX * 9, imgHeight + (relUnitY * 5) ));
					curPanel.setMaximumSize(new Dimension(relUnitX * 9, imgHeight + (relUnitY * 5)));
					view.revalidate();
				    view.repaint();
					System.out.println("Made it here");
					
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, e1);
				}
			}
			
			public double getScaleFactor(int iMasterSize, int iTargetSize) {

			    double dScale = 1;
			    if (iMasterSize > iTargetSize) {

			        dScale = (double) iTargetSize / (double) iMasterSize;

			    } else {

			        dScale = (double) iTargetSize / (double) iMasterSize;

			    }

			    return dScale;

			}
	    }); 
		
		
		//Button that applies any changes made to the setting panel
		btnCommit.addActionListener(new ActionListener()
		{  
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				//if the current question being worked on is a Matrix question, run this code
				if (questionsList.get(curQuestionNum).questionType.equals("Matrix"))
				{
					MatrixSettings curSettings = (MatrixSettings) settingsPanelManager.settingsList.get(curQuestionNum);
					JPanel curPanel = (JPanel) view.getComponent(curQuestionNum * 2);
					curPanel = (JPanel) curPanel.getComponent(1);
					//System.out.println(view.getComponents());
					MatrixQuestion question = (MatrixQuestion) questionsList.get(curQuestionNum);

					//matrix1Panel and matrix2Panel are the two JPanels in the TestPanel that hold the matrices to be multiplied
					//matrix 3 is the answer matrix JPanel
				    JPanel matrix1Panel = (JPanel) ((JPanel) curPanel.getComponent(1)).getComponent(1);
				    JPanel matrix2Panel = (JPanel) ((JPanel) curPanel.getComponent(1)).getComponent(3);
				    JPanel matrix3Panel = (JPanel) ((JPanel) curPanel.getComponent(1)).getComponent(5);
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
				    
				}
				
				
			}
	    }); 
		
		//Takes the current list of questions and passes to the parser to convert to HTML
		btnExport.addActionListener(new ActionListener()
		{  
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				parser.convert(questionsList);
			}
	    }); 
		
		// Main loop that just keeps running while in the creator state
		while (true)
		{
			if (exitCode != 0)
				return exitCode;
		}
		
		
	}
	
	// Adds a new question to the test
	public void addQuestion(Question question)
	{
		numQuestions++;
		curQuestionNum = numQuestions - 1;
		questionsList.add(question);
		JPanel newPanel = new JPanel();
		newPanel.setLayout(new BoxLayout(newPanel, BoxLayout.Y_AXIS));
		newPanel = testPanelManager.updateTestPanel(questionsList, question.questionType, numQuestions);
		newPanel.setPreferredSize(new Dimension(relUnitX * 9, relUnitY * 5));
		newPanel.setMaximumSize(new Dimension(relUnitX * 9, relUnitY * 5));
		newPanel.setMinimumSize(new Dimension(relUnitX * 9, relUnitY * 5));
		newPanel.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                changeSettings(question.questionNum);
            }
        });
		view.add(newPanel);
		view.add(Box.createRigidArea(new Dimension(relUnitX, relUnitY)));
		testPanel.validate();
		scrollableTestPane.validate();
		settingsPanel.removeAll();
		settingsPanel.add(settingsPanelManager.addSettings(question.questionType, question, true));
		settingsPanel.add(btnImage);
		settingsPanel.add(btnCommit);
		settingsPanel.add(Box.createRigidArea(new Dimension(relUnitX, 15 * relUnitY)));
		mainPanel.validate();
	}
	
	//Changes the current settings panel to a new question's settings
	public void changeSettings(int questionNum)
	{
		settingsPanel.removeAll();
		curQuestionNum = questionNum;
		Question question = questionsList.get(questionNum);
		settingsPanel.add(settingsPanelManager.addSettings("Matrix", question, true));
		settingsPanel.add(btnImage);
		settingsPanel.add(btnCommit);
		settingsPanel.add(Box.createRigidArea(new Dimension(relUnitX, 15 * relUnitY)));
		mainPanel.validate();
	}

	//Creates the main panel and its subpanels, returns mainPanel
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
		//scrollableTestPane.setPreferredSize(new Dimension(relUnitX * 10, dimY));
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
