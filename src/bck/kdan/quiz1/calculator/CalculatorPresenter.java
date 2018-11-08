package bck.kdan.quiz1.calculator;

import bck.kdan.quiz1.Presenter;

public class CalculatorPresenter implements Presenter, CalculatorListener
{
	private CalculatorView view;
	private CalculatorModel model;

	public CalculatorPresenter(CalculatorView view)
	{
		this.view = view;
		this.model = new CalculatorModelImpl();
		this.view.addCalculatorListener(this);
	}

	@Override
	public void onOperatorClick(char ch)
	{
		model.setOperator(ch);
		view.display(model.getDisplay());
	}

	@Override
	public void onNumberClick(int num)
	{
		model.putNumber(num);
		view.display(model.getDisplay());
	}

	@Override
	public void onEqualSignClick()
	{
		model.calcualte();
		view.display(model.getDisplay());
	}

	@Override
	public void onCleanClick()
	{
		model.reset();
		view.display(model.getDisplay());
	}

	@Override
	public void onCreate()
	{
	}

	@Override
	public void onPause()
	{
	}

	@Override
	public void onResume()
	{
	}

	@Override
	public void onDestroy()
	{
	}
}
