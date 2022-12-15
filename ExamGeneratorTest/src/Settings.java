import javax.swing.JPanel;

class MatrixSettings extends Settings
{
	//m1d1 = matrix 1 dimension 1, etc
	int m1d1;
	int m1d2;
	int m2d1;
	int m2d2;
	public MatrixSettings(String type, Question question) {
		super(type, question);

	}	
	
	public JPanel commitSettings(JPanel curPanel) {
		JPanel matrix1Panel = (JPanel) ((JPanel) curPanel.getComponent(1)).getComponent(1);
	    JPanel matrix2Panel = (JPanel) ((JPanel) curPanel.getComponent(1)).getComponent(3);
	    JPanel matrix3Panel = (JPanel) ((JPanel) curPanel.getComponent(1)).getComponent(5);
	    matrix1Panel.removeAll();
	    matrix2Panel.removeAll();
	    matrix3Panel.removeAll();
		return curPanel;
	}
	
}

class PolySettings extends Settings {
	int numOfPolynomials = 2;

	public PolySettings(String type, Question question) {
		super(type, question);
	}

}

class ShortAnswerSettings extends Settings {

	public ShortAnswerSettings(String type, Question question) {
		super(type, question);
	}
}

public class Settings {

	String type;
	Question question;
	public Settings(String type, Question question)
	{
		type= this.type;
		question = this.question;
	}
	
}
