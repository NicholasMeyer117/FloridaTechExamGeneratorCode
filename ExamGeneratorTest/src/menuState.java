import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class menuState {
	
	int exitCode;
	public int run(Frame frame)
	{ 
		exitCode = 0;
		JPanel introPanel = new JPanel();
		introPanel.setLayout(new BoxLayout(introPanel, BoxLayout.Y_AXIS));
		ComponentCreator creator = new ComponentCreator();
		
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int dimX = (int) (dimension.getWidth());
	    int dimY = (int) (dimension.getHeight());
	    
	    //Using these relative Units means that components will resize on different screen sizes
	    int relUnitX = dimX/20; //relative X unit
	    int relUnitY = dimY/20; //Relative Y unit
	    System.out.println(dimX + " " + dimY);
		          
	    
		JButton btnCreateTest=new JButton("Create New Test");//creating instance of JButton
		Font font1 = new Font("Arial", Font.BOLD, 15);
		btnCreateTest.setFont(font1);
		btnCreateTest.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnCreateTest.addActionListener(new ActionListener()
		{  
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				introPanel.setVisible(false);
				exitCode = 1;
			}
	    }); 
		
		JButton btnOpenTest=new JButton("Open Test");//creating instance of JButton
		btnOpenTest.setFont(font1);
		btnOpenTest.setAlignmentX(Component.CENTER_ALIGNMENT);

		
		Font font2 = new Font("Serif", Font.BOLD, 45);
		JTextPane introTextPane = creator.createTextPane("Florida Institute of Technology\nExam Generator", font2, true);

		Border matteBorderRed = BorderFactory.createMatteBorder(0, 80, 0, 80, Color.red);
		Color gold = new Color(255,215,0);
		Border matteBorderGold = BorderFactory.createMatteBorder(30, 0, 30, 0, gold);
		Border compoundMatteBorder = BorderFactory.createCompoundBorder(matteBorderRed, matteBorderGold);
		Border emptyBorder = new EmptyBorder(new Insets(relUnitY, relUnitX, relUnitY * 8, relUnitX));
		Border compoundBorder = BorderFactory.createCompoundBorder(compoundMatteBorder, emptyBorder);
		introPanel.add(introTextPane);
		introPanel.add(btnCreateTest);//adding button in JFrame  
		introPanel.add(Box.createRigidArea(new Dimension(0, relUnitY)));
		introPanel.add(btnOpenTest);
		introPanel.setBackground(Color.white);
		introPanel.setBorder(compoundBorder);

	
		frame.add(introPanel);
		
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		//frame.setUndecorated(true);
		          
		//frame.setSize(400,500);//400 width and 500 height  
		//frame.setLayout(null);//using no layout managers  
		frame.setVisible(true);  
		
		while (true)
		{
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (exitCode != 0)
				return exitCode;
		}
	}
	

}
