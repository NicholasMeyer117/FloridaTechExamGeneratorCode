import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class CreatorStateManager{
	
	SettingsPanelManager SManager;
	TestPanelManager TManager;
	QuestionPanelManager QManager;
	JButton btnCommit = new JButton ("Commit Changes");
	
	public CreatorStateManager(SettingsPanelManager SManager, TestPanelManager TManager, QuestionPanelManager QManager)
	{
		/*SManager = this.SManager;
		TManager = this.TManager;
		QManager = this.QManager;*/
		
		//SManager.btnCommit.addActionListener(this);
		
		
		btnCommit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.print("New Changes Committed");
			
			}
		});
		
		
	}
	
	

	
	public void changeSettings()
	{
		
	}



	/*@Override
	public void actionPerformed(ActionEvent e) {
		System.out.print("New Changes Committed");
		
	}*/

}
