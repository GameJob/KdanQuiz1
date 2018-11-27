package bck.kdan.quiz1.clock;

import java.util.Calendar;
import java.util.Date;

public class ClockModelImpl implements ClockModel
{
	private long time = 0;
	private Date date = new Date();
	private Calendar calender = Calendar.getInstance();
	
	private double radiansHour = 0;
	private double radiansMinute = 0;
	private double radiansSecond = 0;
	
	public ClockModelImpl()
	{
		
		date = new Date();
		calender = Calendar.getInstance();
	}

	@Override
	public void setTime(long time)
	{
		date.setTime(time);
		calender.setTime(date);

		long hour = calender.get(Calendar.HOUR);
		long minute = calender.get(Calendar.MINUTE);
		long second = calender.get(Calendar.SECOND);
		
		final double degreeToRadians = Math.PI / 180.0;
		radiansHour = (hour + minute / 60.0 + second / 3600.0) * 30.0 * degreeToRadians;
		radiansMinute = (minute + second / 60.0) * 6.0 * degreeToRadians;
		radiansSecond = second * 6.0 * degreeToRadians;

	}

	@Override
	public long getTime()
	{
		return time;
	}

	@Override
	public double getHourPinRadians()
	{
		return radiansHour;
	}

	@Override
	public double getMinutePinRadians()
	{
		return radiansMinute;
	}

	@Override
	public double getSecondPinRadians()
	{
		return radiansSecond;
	}
}
