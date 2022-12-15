import org.apache.commons.math3.analysis.polynomials.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

import org.apache.commons.io.FileUtils;
import org.apache.commons.math3.analysis.*;

public class HTMLParser {
	
	//Takes a question list, puts them all through their parses, and adds the results to the output file
	public void convert(List<Question> questionsList)
	{
		String parsedInput = "";
		for (int i = 0; i < questionsList.size(); i++)
		{
			//Question curQ;
			if (questionsList.get(i).questionType.equals("Matrix"))
			{
				MatrixQuestion curQ = (MatrixQuestion) questionsList.get(i);
				parsedInput += parseMatrix(i + 1, curQ.questionText, 10, curQ.imageFilename, curQ.xCoords, curQ.yCoords, curQ.ddAnswers, curQ.matrix1.matrixData, curQ.matrix2.matrixData, curQ.answerMatrix.matrixData);
			}
			else if(questionsList.get(i).questionType.equals("Polynomial"))
			{
				PolyQuestion curQ = (PolyQuestion) questionsList.get(i);
				parsedInput += parsePolyQuestion(i + 1, curQ.questionText, 10, curQ.imageFilename, curQ.xCoords, curQ.yCoords, curQ.ddAnswers, curQ.polynomial_1.coeffArray,  curQ.polynomial_2.coeffArray, curQ.polynomial_ans.coeffArray, '+');
				
			}
			else if(questionsList.get(i).questionType.equals("ShortAnswer"))
			{
				ShortAnswerQuestion curQ = (ShortAnswerQuestion) questionsList.get(i);
				parsedInput += parseFreeResponse(i + 1, curQ.questionText, 10, curQ.imageFilename, curQ.xCoords, curQ.yCoords, curQ.ddAnswers, "Answer this question");
			}
			
		}
		
		System.out.println(parsedInput);
		
		JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

	    // Set the file chooser to only show files (not directories)
	    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	    
	    int returnValue = fileChooser.showSaveDialog(null);
	    File newFolder = null;

	    // If the user chose a location, save the file
	    if (returnValue == JFileChooser.APPROVE_OPTION)
	    {
	      newFolder = fileChooser.getSelectedFile();
	      newFolder.mkdir();
		
		//Write to File
	      try {
	    	  File newFile = new File(newFolder.getPath() + "\\output.txt");
		      FileWriter myWriter = new FileWriter(newFile);
		      myWriter.write(parsedInput);
		      myWriter.close();
		      System.out.println("Successfully wrote to the file.");
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
	    }
	    
	    for (int i = 0; i < questionsList.size(); i++)
		{
	    	if (questionsList.get(i).imageFilename != null)
	    	{
	    		File imageFile = new File(questionsList.get(i).imageFilename);
	    	    BufferedImage sourceImage = null;
				try {
					System.out.println("read image");
					sourceImage = ImageIO.read(imageFile);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("read image failed");
					e.printStackTrace();
				}
				Path destinationFile = Paths.get(newFolder.getPath(), imageFile.getName());
				try {
					Files.copy(imageFile.toPath(), destinationFile, StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	    	    // Write the image to the destination file
	    	    try {
	    	    	System.out.println("Wrote image");
					ImageIO.write(sourceImage, "image.jpg", newFolder);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("Write image failed");
					e.printStackTrace();
				}
	    	}
		}
		
		
	}
	
	// creates the header for each question (they each have the same format)
		public String parseHeader(int id, String title, int points) {
			String parsedInput = "===Question===\n"; //question
			parsedInput += id + "\n"; //identification of unique question
			parsedInput += "++++\n";
			parsedInput += title + "\n"; //title shown
			parsedInput += "\n"; //student id (given to all if blank)
			parsedInput += "1\n"; //attempts (1 for now)
			parsedInput += points + "\n"; //points for the question
			parsedInput += "--POINTS--\n\n";
			parsedInput += "--BODY--\n";
			return parsedInput;
		}
		
		// code to add image, text
		public List<String> parseDragDropImg(String filepath, List<Integer> xCoords, List<Integer> yCoords, List<String> ddAnswers) {
			String htmlImgStyle = ""; //the styling for text placement
			String htmlImg = ""; //the html body for the image and text placed on the image
			String htmlImgLabels = ""; //the html for text placed on img
			String htmlImgBodyAnswer = ""; // the html for input entries
			String htmlImgAnswer = ""; // checks variables with contents
			
			List<String> htmlImgList=new ArrayList<String>();
			byte[] fileContent;
			//if file does not exist then returns empty string
			try {
				fileContent = FileUtils.readFileToByteArray(new File(filepath));
			// if file is not found send empty strings back
			} catch (IOException e) {
				htmlImgList.add(htmlImgStyle);
				htmlImgList.add(htmlImg);
				htmlImgList.add(htmlImgBodyAnswer);
				htmlImgList.add(htmlImgAnswer);
				return htmlImgList;
			}
			//create container surrounding image
			htmlImgStyle += ".container {\r\n"
					+ "  position: relative;\r\n"
					+ "  text-align: left;\r\n"
					+ "  color: white;\r\n"
					+ "}";
			//get base 64 of image to store as text
			String encodedString = Base64.getEncoder().encodeToString(fileContent);
			
			//iterates over drag drop items XYpos and creates the answer, text labels on the image, html styling, and text box entry
			if (xCoords.size() > 0) {
				for (int i = 0; i < xCoords.size(); i++) {
					//add style for image, and each text label
					htmlImgStyle += ".dragdropitem" + i +" {\r\n"
							+ "	position: absolute;\r\n"
							+ "	top:" + yCoords.get(i) + "px;\r\n"
							+ "	left:" + xCoords.get(i) + "px;\r\n"
							+ "        background-color: black;\r\n"
							+ "}";
					//add image label html
					htmlImgLabels += "<div class=\"dragdropitem" + i + "\">" + (char) (i+65) + "</div>\n";
					
					//adds the body html for answers, and what each answer should contain
					htmlImgBodyAnswer += (char) (i+65) + "= [dragdropans"+ i +"|100|100]<br>\n";
					htmlImgAnswer += "dragdropans" + i + " " + ddAnswers.get(i)+ "\n";
				}
			}
			
			// image and text placed on image
			htmlImg += "<div class=\"container\">\n";
			htmlImg += "<img alt=\"\" src=\"data:image/png;base64," + encodedString + "\"/><br>\n";
			htmlImg += htmlImgLabels;
			htmlImg += "</div>\n";
			
			//adds the html items in this order
			htmlImgList.add(htmlImgStyle);
			htmlImgList.add(htmlImg);
			htmlImgList.add(htmlImgBodyAnswer);
			htmlImgList.add(htmlImgAnswer);
			return htmlImgList;
		}
		
		// generates the HTML for the matrix problem
		public String parseMatrix(int id, String title, int points, String imgfilepath, List<Integer> xCoords, List<Integer> yCoords, List<String> ddAnswers, double[][] matrice1, double[][] matrice2, double[][] result) {
			String parsedInput = parseHeader(id, title, points);
			boolean hasImage = false;
			List<String> ImgHTMLList = null;
			if (imgfilepath!=null)
				hasImage = true;
			
			if (hasImage)
				ImgHTMLList = parseDragDropImg(imgfilepath, xCoords, yCoords, ddAnswers);
			// matrix table style (source at top)
			parsedInput += "<style>\r\n";
			parsedInput += "    .matrix {\r\n"
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
					+ "    }\r\n";
			// image style
			if (hasImage) 
				parsedInput += ImgHTMLList.get(0);
			parsedInput += "</style>\n";
				
			if (hasImage) 
			{
				//adds image with text labels and then text inputs
				parsedInput += ImgHTMLList.get(1);
				parsedInput += "<br>";
				parsedInput += ImgHTMLList.get(2);
				parsedInput += "<br>";
			}
			
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
			if (hasImage)
				parsedInput += ImgHTMLList.get(3);
			for (int row = 0; row < result.length; row++) {
				for (int col = 0; col < result[row].length; col++) {
					parsedInput += "var" + col + "_" + row + " " + (int)result[row][col] + "\n";
		        }
			}
			return parsedInput;
		}
		
		// generates the HTML for the matrix problem
		public String parseFreeResponse(int id, String title, int points, String imgfilepath, List<Integer> xCoords, List<Integer> yCoords, List<String> ddAnswers, String prompt) {
			String parsedInput = parseHeader(id, title, points);
			boolean hasImage = false;
			List<String> ImgHTMLList = null;
			if (imgfilepath!=null)
				hasImage = true;
			if (hasImage)
				ImgHTMLList = parseDragDropImg(imgfilepath, xCoords, yCoords, ddAnswers);
			
			//html styling for elements
			if (hasImage)
			{
				parsedInput += "<style>\n";
				parsedInput += ImgHTMLList.get(0);
				parsedInput += "</style>\n";
			
				//adds image with labels and text inputs
				parsedInput += ImgHTMLList.get(1);
				parsedInput += "<br>";
				parsedInput += ImgHTMLList.get(2);
				parsedInput += "<br>";
			}
			
			//adds prompt and input box
			parsedInput += prompt.replace("\n", "<br>\n");
			parsedInput += "<br>\n<br>\n";
			int dim = 5200;
			parsedInput += "[variable|0|" + dim + "]<br>\n";
			parsedInput += "--ANSWER--\n";
			//adds text contents
			if (hasImage)
				parsedInput += ImgHTMLList.get(3);
			return parsedInput;
		}
		
		//takes input of question ID, Question title, points for the question, the two input polynomials (coefs), the computed answer (coefs), and function applied (character)
		public String parsePolyQuestion(int id, String title, int points, String imgfilepath, List<Integer> xCoords, List<Integer> yCoords, List<String> ddAnswers, double[] poly1,  double[] poly2, double[] polyAns, char functType) {
			String parsedInput = parseHeader(id, title, points);
			boolean hasImage = false;
			List<String> ImgHTMLList = null;
			if (imgfilepath!=null)
				hasImage = true;
			if (hasImage)
				ImgHTMLList = parseDragDropImg(imgfilepath, xCoords, yCoords, ddAnswers);
			//adds styling for image
			if (hasImage)
			{
				parsedInput += "<style>\n";
				parsedInput += ImgHTMLList.get(0);
				parsedInput += "</style>\n";
			
				//adds image with labels, and text inputs
				parsedInput += ImgHTMLList.get(1);
				parsedInput += "<br>";
				parsedInput += ImgHTMLList.get(2);
				parsedInput += "<br>";
			}
			
			//polynomial prompt, format, and 
			parsedInput += "Perform the following Polynomial Computation:<br>\n";
			parsedInput += "(" + polyArrToString(poly1, false) + ") " + functType + " (" + polyArrToString(poly2, false) + ")<br>\n";
			parsedInput += "<br>\n<br>\n";
			parsedInput += "[variable|100|100]<br>\n";
			parsedInput += "--ANSWER--\n";
			
			//answer checks for image and matrice
			if (hasImage)
				parsedInput += ImgHTMLList.get(3);
			parsedInput += "variable " + polyArrToString(polyAns, true).trim();
			return parsedInput;
		}
		
		//used in parsePolyQuestion
		public static String polyArrToString(double[] list, Boolean isAns) {
		    String polyStr = "";
			for(int i = 0; i < list.length; i++) {
				double coef = list[i];
		    	//code for adding coefficient/variable x
		    	if (coef == 1) {
		    		if (i >=1) {
		    			polyStr += "x";
		    		} else if (i == 0) {
		    			polyStr += coef;
		    		}
		    	} else if (coef != 0) {
		    		polyStr += coef;
		    		if (i >=1) {
		    			polyStr += "x";
		    		}
		    	}
		    	
		    	//coded for superscript (isAns is for user input otherwise HTML format)
		    	if (i > 1 && coef != 0.0) {
		    		if (isAns) {
		    			polyStr += "^" + i;
		    		} else {
		    			polyStr += "<sup>" + i + "</sup>";
		    		}
		    		
		    	}
		    	//if the next coefficient is zero, or it is the last item then don't add +
		    	if (i < list.length - 1) {
		    		if (list[i+1] != 0.0) {
		    			polyStr += " +";
		    		}
		    	}
		    }
	    //formatting and spacing to look more readable
	    polyStr = polyStr.replace(" +-", " -");
	    polyStr = polyStr.replace(" -+", " -");
	    polyStr = polyStr.replace("+ +", "+");
	    polyStr = polyStr.replace("- +", "+");
	    polyStr = polyStr.replace("+ -", "-");
	    polyStr = polyStr.replace("- -", "-");
	    polyStr = polyStr.replace(" +", " + ");
	    polyStr = polyStr.replace(" -", " - ");
	    polyStr.trim();
//	    System.out.println(polyStr);
	    return polyStr;
	    }

}
