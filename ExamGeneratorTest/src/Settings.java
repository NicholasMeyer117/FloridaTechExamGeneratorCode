
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
	
	
	
}

class PolySettings extends Settings {
	int poly1Degree;
	int poly2Degree;
	String poly1Coeff;
	String poly2Coeff;
	
	public PolySettings(String type, Question question) {
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
