package bck.kdan.quiz1.calculator;

import java.text.DecimalFormat;

public class CalculatorModelImpl implements CalculatorModel
{
	private String display = "0";
	private double result = 0;
	private double operand = 0;
	private char operator = '\0';
	private boolean newRound = true;
	private DecimalFormat format = null;

	public CalculatorModelImpl()
	{
		format = new DecimalFormat("#.####");
	}
	
	@Override
	public void setDecimalFormat(DecimalFormat format)
	{
		this.format = format;
	}

	@Override
	public void setOperator(char ch)
	{
		if (newRound || operator == '\0')
		{
			result = operand;
		}
		calcualte();

		operand = 0;
		operator = ch;

		newRound = false;
		display = "" + ch;
	}

	@Override
	public void putNumber(int num)
	{
		if (newRound)
		{
			reset();
		}
		operand *= 10;
		operand += num;

		newRound = false;
		display = format.format(operand);
	}

	@Override
	public void calcualte()
	{
		switch (operator)
		{
		case '+':
			result += operand;
			break;
		case '-':
			result -= operand;
			break;
		case '*':
			result *= operand;
			break;
		case '/':
			result /= operand;
			break;
		default:
			result = operand;
			break;
		}

		newRound = true;
		display = format.format(result);
	}

	@Override
	public void reset()
	{
		result = 0;
		operand = 0;
		operator = '\0';

		newRound = true;
		display = format.format(result);
	}

	@Override
	public double getResult()
	{
		return result;
	}

	@Override
	public String getDisplay()
	{
		return display;
	}
}
