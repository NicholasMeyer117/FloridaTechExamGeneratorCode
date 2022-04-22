import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;


class Matrix
{
	int sizeX = 2;
	int sizeY = 2;
	
	double[][] matrixData = { {1,2,3}, {4,5,6}, {7,8,9}};
	RealMatrix matrix = MatrixUtils.createRealMatrix(matrixData);
}

public class MatrixQuestion extends Question {
	int numberOfMatrices = 2;
	//List<Matrix> listOfMatrices=new ArrayList<Matrix>();  
	Matrix matrix1 = new Matrix();
	Matrix matrix2 = new Matrix();
	Matrix answerMatrix = new Matrix();

	public MatrixQuestion(String text, int num, String type) {
		super(text, num, type);
		compute();
		 
	}
	
	public Matrix compute()
	{
		answerMatrix.matrix = matrix1.matrix.multiply(matrix2.matrix);
		answerMatrix.sizeX = answerMatrix.matrix.getRowDimension();
		answerMatrix.sizeY = answerMatrix.matrix.getColumnDimension();
		answerMatrix.matrixData = answerMatrix.matrix.getData();
		
		return answerMatrix;
	}
	
	public Matrix getMatrix(int num)
	{
		if (num == 1)
			return matrix1;
		else if (num == 2)
			return matrix2;
		else if (num == 3)
			return answerMatrix;
		return null;
	}

}
