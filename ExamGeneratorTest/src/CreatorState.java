import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
	int numQuestions = 0;
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
	CreatorStateManager manager = new CreatorStateManager(settingsPanelManager, testPanelManager, questionPanelManager);
	
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
				mainPanel.validate();
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
