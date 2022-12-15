import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;  
public class application {
	public static void main(String[] args) 
	{   
		JFrame frame=new JFrame();//creating instance of JFrame 
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		menuState MenuState = new menuState();
		CreatorState creatorState = new CreatorState();
		int state = 0;
		
		while (true)
		{
			System.out.println(state);
			if (state == 0)
				state = MenuState.run(frame);
			else if (state == 1)
			{
				//Run creator state
				state = creatorState.run(frame);
			}
		}
		
	}  
	
}
