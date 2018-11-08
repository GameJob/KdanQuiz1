package bck.kdan.quiz1.calculator;

import java.text.DecimalFormat;

public interface CalculatorModel
{
	public void setDecimalFormat(DecimalFormat format);
	
	public void setOperator(char ch);

	public void putNumber(int num);

	public void calcualte();

	public void reset();

	public double getResult();
	
	public String getDisplay();
}
