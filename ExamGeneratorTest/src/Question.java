import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

public class Question {
	String questionText;
	int questionNum;
	String questionType;
	ImageIcon format = null;
	boolean isImage = true;
	
	String imageFilename = null;
	byte[] photo = null;
	
	//To store drag and drop locations and values
	List<Integer> xCoords = new ArrayList();; 
	List<Integer> yCoords = new ArrayList();; 
	List<String> ddAnswers = new ArrayList();;
	
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

