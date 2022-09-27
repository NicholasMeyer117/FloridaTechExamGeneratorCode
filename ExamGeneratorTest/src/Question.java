import javax.swing.ImageIcon;

public class Question {
	String questionText;
	int questionNum;
	String questionType;
	ImageIcon format = null;
	boolean isImage = true;
	
	String imageFilename = null;
	byte[] photo = null;
	
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

