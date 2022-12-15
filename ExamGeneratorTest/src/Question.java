import java.awt.Button;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Question {
	String questionText;
	int questionNum;
	String questionType;
	ImageIcon format = null;
	boolean isImage = true;
	
	String imageFilename = null;
	byte[] photo = null;
	
	//To store drag and drop locations and values
	List<JButton> dragableButtonsList = new ArrayList<JButton>();
	List<Integer> xCoords = new ArrayList<Integer>();
	List<Integer> yCoords = new ArrayList<Integer>();
	List<String> ddAnswers = new ArrayList<String>();
	
	public Question(String text, int num, String type)
	{
		questionText = text;
		questionNum = num;
		questionType = type;
		
	}
	
	void solve()
	{
		//virtual function to be implemented by child classes
	}

}

