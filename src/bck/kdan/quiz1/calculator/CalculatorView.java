package bck.kdan.quiz1.calculator;

public interface CalculatorView
{
	public void display(String text);
	
	public boolean addCalculatorListener(CalculatorListener listener);
	
	public boolean removeCalculatorListener(CalculatorListener listener);
}
