import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
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
	JButton btnShortAnswer = new JButton("Add Short Answer Question");
	JButton btnMultipleChoice = new JButton("Add Multiple Choice Question");
	
	JButton btnGen = new JButton ("General");
	JButton btnMath = new JButton ("Math");
	JButton btnPhysics = new JButton ("Physics");
	JButton btnChem = new JButton ("Chemistry");
	
	JButton btnExport=new JButton("Finish and Export Test");
	JButton btnCommit=new JButton("Commit Changes");
	JButton btnImage = new JButton ("Add Image");
	JButton btnAddTextBox = new JButton ("Add Textbox");
	List<JButton> dragableButtonsList = new ArrayList<JButton>();
	int btnInList = 0;
	
	static boolean click = false;
	static Toolkit tk = Toolkit.getDefaultToolkit();
	static long eventMask =AWTEvent.MOUSE_MOTION_EVENT_MASK + AWTEvent.MOUSE_EVENT_MASK;
	
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
	QuestionPanelManager questionPanelManager;// = new QuestionPanelManager(relUnitX, relUnitY);
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
		
		//Global Mouse Listener
	    tk.addAWTEventListener(new AWTEventListener() 
	    {
	        @Override
	        public void eventDispatched(AWTEvent e) {
	            if ( e.getID () == MouseEvent.MOUSE_MOVED )
	            {
	                if (click)
	                {
		             int pointX = MouseInfo.getPointerInfo().getLocation().x - testPanel.getLocationOnScreen().x;
		             int pointY = MouseInfo.getPointerInfo().getLocation().y - testPanel.getLocationOnScreen().y;
	               	 dragableButtonsList.get(btnInList).setLocation(pointX - 50, pointY - 30);
	               	 view.revalidate();
				     view.repaint();
	                }
	            }
	            else if ( e.getID () == MouseEvent.MOUSE_PRESSED)
	            {
	            	if (click)
	            		click = !click;
	            }
	            	
	        }
	    }, eventMask);
		
		//Adds a dragable textbox to the image panel
		btnAddTextBox.addActionListener(new ActionListener()
		{  
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				JButton btnNewBtn = new JButton ("Button");
				JTextField textfield = new JTextField();
				btnNewBtn.add(textfield);
				btnNewBtn.setSize(100, 60);
				int btnTag = dragableButtonsList.size();
				dragableButtonsList.add(btnNewBtn);
				
				btnNewBtn.addActionListener(new ActionListener()
				{  
					@Override
					public void actionPerformed(ActionEvent e) 
					{
			            click = !click;
			            btnInList = btnTag; 
			            	
					}
			    });
				
				JPanel curPanel = (JPanel) view.getComponent(curQuestionNum * 2);
				JLabel curLabel = (JLabel) curPanel.getComponent(0);
				curLabel.setLayout(null);
				curLabel.add(dragableButtonsList.get(dragableButtonsList.size() - 1));
				view.revalidate();
			    view.repaint();

			}
	    });
		
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
		
		btnShortAnswer.addActionListener(new ActionListener()
		{  
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				Question question = new ShortAnswerQuestion("Fill in the short answer", numQuestions, "ShortAnswer");
				addQuestion(question);

			}
	    });
		
		btnMultipleChoice.addActionListener(new ActionListener()
		{  
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				Question question = new MatrixQuestion("Choose a multiple choice option", numQuestions, "Matrix");
				addQuestion(question);
			}
	    });
		
		
		btnGen.addActionListener(new ActionListener()
		{  
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				questionPanelManager.type = 0;
				refreshQuestionPanel();
				//questionPanel.add(Box.createRigidArea(new Dimension(1, dimY)));
			}
	    }); 
		
		btnMath.addActionListener(new ActionListener()
		{  
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				questionPanelManager.type = 1;
				refreshQuestionPanel();
				//questionPanel.add(Box.createRigidArea(new Dimension(1, dimY)));
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
                	//BufferedImage newBuf = new BufferedImage(img.getWidth(null), img.getHeight(null),BufferedImage.TYPE_INT_ARGB);
                	
                	/*Font font = new Font("Arial", Font.BOLD, 18);

                	Graphics g = newBuf.getGraphics();
                	g.setFont(font);
                	g.setColor(Color.GREEN);
                	g.drawString("Hello", 0, 20);
                	question.format = new ImageIcon(newBuf);*/
                	

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
				CommitHandler commitHandler = new CommitHandler();
				//if the current question being worked on is a Matrix question, run this code
				if (questionsList.get(curQuestionNum).questionType.equals("Matrix"))
				{
					MatrixSettings curSettings = (MatrixSettings) settingsPanelManager.settingsList.get(curQuestionNum);
					JPanel curPanel = (JPanel) view.getComponent(curQuestionNum * 2);
					curPanel = (JPanel) curPanel.getComponent(1);
					MatrixQuestion question = (MatrixQuestion) questionsList.get(curQuestionNum);
					
					question = commitHandler.commitMatrixChanges(view, mainPanel, curSettings, curPanel, question);
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
	
	public void refreshQuestionPanel()
	{
		questionPanel.remove(questionPanel.getComponent(2));
		questionPanel.revalidate();
		questionPanel.repaint();
		questionPanel.remove(questionPanel.getComponent(1));
		questionPanel.add(questionPanelManager.addQuestions());
		questionPanel.add(Box.createRigidArea(new Dimension(1, relUnitY * 100)));
		view.revalidate();
	    view.repaint();
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
		settingsPanel.add(btnAddTextBox);
		settingsPanel.add(Box.createRigidArea(new Dimension(relUnitX, 15 * relUnitY)));
		mainPanel.validate();
	}
	
	//Changes the current settings panel to a new question's settings
	public void changeSettings(int questionNum)
	{
		settingsPanel.removeAll();
		curQuestionNum = questionNum;
		Question question = questionsList.get(questionNum);
		settingsPanel.add(settingsPanelManager.addSettings(question.questionType, question, true));
		settingsPanel.add(btnImage);
		settingsPanel.add(btnCommit);
		settingsPanel.add(btnAddTextBox);
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
	    
	    questionPanelManager = new QuestionPanelManager(relUnitX, relUnitY, btnShortAnswer, btnMultipleChoice, btnMatrix);
	    
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
		questionPanel.add(questionPanelManager.addQuestionTypes(btnGen, btnMath, btnPhysics, btnChem));
		questionPanel.add(questionPanelManager.addQuestions());
		questionPanel.add(Box.createRigidArea(new Dimension(1, dimY)));
		mainPanel.add(questionPanel, cons);
		
		ComponentCreator creator = new ComponentCreator();
		
		
		return mainPanel;
	}

}
