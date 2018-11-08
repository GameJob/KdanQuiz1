package bck.kdan.quiz1.clock;

import bck.kdan.quiz1.Presenter;

public class ClockPresenter implements Presenter, ClockChangeListener
{
	private ClockView view;
	private ClockModel model;
	
	public ClockPresenter(ClockView view)
	{
		this.view = view;
		this.model = new ClockModelImpl();
		this.view.addClockChangeListener(this);
	}

	@Override
	public void onClockChange(long time)
	{
		model.setTime(time);
		double radiansHour = model.getHourPinRadians();
		double radiansMinute = model.getMinutePinRadians();
		double radiansSecond = model.getSecondPinRadians();
		view.setPinRadians(radiansHour, radiansMinute, radiansSecond);
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
