package bck.kdan.quiz1.clock;

public interface ClockModel
{
	public void setTime(long time);
	
	public long getTime();
	
	public double getHourPinRadians();
	
	public double getMinutePinRadians();
	
	public double getSecondPinRadians();
}
