package bck.kdan.quiz1.calculator;

public interface CalculatorListener
{
	public void onOperatorClick(char ch);

	public void onNumberClick(int num);

	public void onEqualSignClick();

	public void onCleanClick();
}
