import java.util.List;

public class HTMLParser {
	
	public void convert(List<Question> questionsList)
	{
		String parsedInput = "";
		for (int i = 0; i < questionsList.size(); i++)
		{
			//Question curQ;
			if (questionsList.get(i).questionType.equals("Matrix"));
			{
				MatrixQuestion curQ = (MatrixQuestion) questionsList.get(i);
				parsedInput += parseMatrix(i + 1, curQ.questionText, 10, curQ.matrix1.matrixData, curQ.matrix2.matrixData, curQ.answerMatrix.matrixData);
			}
		}
		
		System.out.println(parsedInput);
		
	}
	
	public String parseMatrix(int id, String title, int points, double[][] matrice1, double[][] matrice2, double[][] result) {
		String parsedInput = "===Question===\n"; //question
		parsedInput += id + "\n"; //identification of unique question
		parsedInput += "++++\n";
		parsedInput += title + "\n"; //title shown
		parsedInput += "\n"; //student id (given to all if blank)
		parsedInput += "1\n"; //attempts (1 for now)
		parsedInput += points + "\n"; //points for the question
		parsedInput += "--POINTS--\n\n";
		parsedInput += "--BODY--\n";
		
		// matrix table style (source at top)
		parsedInput += "<style>\r\n"
				+ "    .matrix {\r\n"
				+ "        position: relative;\r\n"
				+ "    }\r\n"
				+ "    .matrix:before, .matrix:after {\r\n"
				+ "        content: \"\";\r\n"
				+ "        position: absolute;\r\n"
				+ "        top: 0;\r\n"
				+ "        border: 1px solid #000;\r\n"
				+ "        width: 6px;\r\n"
				+ "        height: 100%;\r\n"
				+ "    }\r\n"
				+ "    .matrix:before {\r\n"
				+ "        left: -6px;\r\n"
				+ "        border-right: 0;\r\n"
				+ "    }\r\n"
				+ "    .matrix:after {\r\n"
				+ "        right: -6px;\r\n"
				+ "        border-left: 0;\r\n"
				+ "    }\r\n"
				+ "</style>";
		
		//spacing, and first matrix
		parsedInput += "<br><table><tr><td>  &nbsp &nbsp &nbsp</td><td>\n<table class=\"matrix\">\n<tr>";
		for (int row = 0; row < matrice1.length; row++) {
			for (int col = 0; col < matrice1[row].length; col++) {
                parsedInput += "<td>" + (int)matrice1[row][col] + "&nbsp</td>";
            }
			parsedInput += "</tr>\n";
		}
		parsedInput += "</table>\n";
		
		//spacing and multiplication symbol
		parsedInput += "</td><td> &nbsp &nbsp &nbsp x &nbsp &nbsp &nbsp</td><td>\n";
		
		//matrice2 html
		parsedInput += "<table class=\"matrix\">\n<tr>";
		for (int row = 0; row < matrice2.length; row++) {
			for (int col = 0; col < matrice2[row].length; col++) {
                parsedInput += "<td>" + (int)matrice2[row][col] + "&nbsp</td>";
            }
			parsedInput += "</tr>\n";
		}
		parsedInput += "</table>\n";
		
		//spacing and equals sign
		parsedInput += "</td><td>&nbsp &nbsp &nbsp=</td></tr></table>\n";
		
		//input field
		parsedInput += "<br><p>\n";
		for (int row = 0; row < result.length; row++) {
			for (int col = 0; col < result[row].length; col++) {
                parsedInput += "[var" + col + "_" + row + "|4|4]";
            }
			parsedInput += "<br>\n";
		}
		
		//answers to variables in input field
		parsedInput += "--ANSWER--\n";
		for (int row = 0; row < result.length; row++) {
			for (int col = 0; col < result[row].length; col++) {
                parsedInput += "var" + col + "_" + row + " " + (int)result[row][col] + "\n";
            }
		}
		return parsedInput;
	}

}
