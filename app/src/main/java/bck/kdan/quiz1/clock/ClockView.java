package bck.kdan.quiz1.clock;

public interface ClockView
{
	public void setPinRadians(double radiansHour, double radiansMinute, double radiansSecond);
	
	public boolean addClockChangeListener(ClockChangeListener listener);
	
	public boolean removeClockChangeListener(ClockChangeListener listener);
}
