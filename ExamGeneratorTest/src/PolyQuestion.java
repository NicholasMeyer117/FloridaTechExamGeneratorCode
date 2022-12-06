import org.apache.commons.math3.analysis.polynomials.*;
import org.apache.commons.math3.analysis.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.*;


class Poly {

	double[] coeffArray = {1, 2, 3};
	PolynomialFunction polynomial = new PolynomialFunction(coeffArray);
	int polySize = polynomial.toString().split(" ").length - 1;
	
	public String getStringByElement(int element)
	{
		String polyStr = polynomial.toString();
		System.out.println(polyStr);
		String[] polyStrElements = polyStr.split(" ");
		return polyStrElements[element];
	}

}

public class PolyQuestion extends Question {
	List<Poly> polyList = new ArrayList<>();
	Poly polynomial_1 = new Poly();
	Poly polynomial_2 = new Poly();
	Poly polynomial_ans = new Poly();
	
	public PolyQuestion(String text, int num, String type) {
		super(text, num, type);
		polyList.add(polynomial_ans);
		polyList.add(polynomial_1);
		polyList.add(polynomial_2);
		compute(1);
	}


	public Poly compute (int check) {

		//If check is 1 add the two polynomials
		if(check == 1) {
			polynomial_ans.polynomial = polynomial_1.polynomial.add(polynomial_2.polynomial); 
		}
		else if(check == 2) {
			polynomial_ans.polynomial = polynomial_1.polynomial.subtract(polynomial_2.polynomial);
		}
		else if(check == 3) {
			polynomial_ans.polynomial = polynomial_1.polynomial.multiply(polynomial_2.polynomial);
		}


		return polynomial_ans;
	}

	public Poly updatePoly (int degree, double[] coeff, int check) {
		PolynomialFunction newPoly;


		if (check == 1) {
			polynomial_1.coeffArray = Arrays.copyOf(polynomial_1.coeffArray, degree);
			for(int i = 0; i < degree; i++) {
				polynomial_1.coeffArray[i] = coeff[i];
			}
			newPoly = new PolynomialFunction(polynomial_1.coeffArray);
			polynomial_1.polynomial = newPoly;
			return polynomial_1;

		}
		else if (check == 2) {
			polynomial_2.coeffArray = Arrays.copyOf(polynomial_2.coeffArray, degree);
			for(int i = 0; i < degree; i++) {
				polynomial_2.coeffArray[i] = coeff[i];
			}
			newPoly = new PolynomialFunction(polynomial_2.coeffArray);
			polynomial_2.polynomial = newPoly;
			return polynomial_2;
		}
		else {
			polynomial_ans.coeffArray = Arrays.copyOf(polynomial_ans.coeffArray, degree);
			for(int i = 0; i < degree; i++) {
				polynomial_ans.coeffArray[i] = coeff[i];
			}
			newPoly = new PolynomialFunction(polynomial_ans.coeffArray);
			polynomial_ans.polynomial = newPoly;
			return polynomial_ans;
		}

	}

	public double[] getPoly_fromStr(int degree, String polyStr) {
		double[] coefficents = new double[degree];

		String[] coeffNums = polyStr.split(" ");

		for(int i = 0; i < degree; i++) {
			coefficents[i] = Double.parseDouble(coeffNums[i]);
		}


		return coefficents;
	}
	
	public double[] stringToPoly(String polyStr)
	{
		int lengthArr = 0;
		
		char[] polyArr = polyStr.toCharArray();
		
		//Need to remove any exponents
		//I do this in this instance by changing the value there into a non int
		
		for(int i = 0; i < polyStr.length(); i++)
		{
			if (polyArr[i] == '^')
			{
				polyArr[i+1] = 'a';
			}
			else if (Character.isDigit(polyStr.charAt(i)))
			{
				lengthArr++;
			}
		}
		
		double[] coeffArr =  new double[lengthArr];
		String numStr = String.valueOf(polyArr);
		
		//Use Guava CharMather in actually program this is just a test to see if it works
		String numsOfPoly = numStr.replaceAll("[^0-9]", "");
		System.out.println(numsOfPoly);
		
		for(int i = 0; i < numsOfPoly.length(); i++) {
			coeffArr[i] = (double) (numsOfPoly.charAt(i) - '0');
		}
		
		return coeffArr;
	}
	
	public Poly getPoly(int num)
	{
		return polyList.get(num);
	}

}