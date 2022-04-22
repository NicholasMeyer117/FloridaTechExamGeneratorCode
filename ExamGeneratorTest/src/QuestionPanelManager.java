import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class QuestionPanelManager {
	
	int relUnitX;
	int relUnitY;
	public QuestionPanelManager(int relUnitX, int relUnitY)
	{
		relUnitX = this.relUnitX;
		relUnitY = this.relUnitY;
	}
	
	public JPanel addQuestionTypes()
	{
		JPanel options = new JPanel(new FlowLayout());
		JButton btnGen = new JButton ("General");
		JButton btnMath = new JButton ("Math");
		JButton btnPhysics = new JButton ("Physics");
		JButton btnChem = new JButton ("Chemistry");
		Border border = BorderFactory.createMatteBorder(1, 1, 5, 1, Color.black);
		//JButton BtnCompSci = new JButton ("Comp Sci");
		
		options.add(btnGen);
		options.add(btnMath);
		options.add(btnPhysics);
		options.add(btnChem);
		options.setBorder(border);
		//options.add(BtnCompSci);
		
		btnGen.addActionListener(new ActionListener()
		{  
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				
			}
	    }); 
		
		
		return options;
	}

}
