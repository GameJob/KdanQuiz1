package bck.kdan.quiz1.clock;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import bck.kdan.quiz1.R;

public class ClockFragment extends Fragment implements ClockView
{
	private ClockDisplayView clockDisplayView;
	private List<ClockChangeListener> listeners = new ArrayList<ClockChangeListener>();

	private Timer timer = null;
	private TimerTask task = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View root = inflater.inflate(R.layout.fragment_clock, container, false);
		clockDisplayView = root.findViewById(R.id.clockDisplayView);
		return root;
	}

	@Override
	public void onPause()
	{
		super.onPause();
		if (task != null)
		{
			task.cancel();
			task = null;
		}
		if (timer != null)
		{
			timer.cancel();
			timer = null;
		}
	}

	@Override
	public void onResume()
	{
		super.onResume();
		if (timer == null)
		{
			timer = new Timer();
		}
		if (task == null)
		{
			task = new TimerTask()
			{
				public void run()
				{
					long time = System.currentTimeMillis();
					for (ClockChangeListener listener: listeners)
					{
						listener.onClockChange(time);
					}
				}
			};
		}
		timer.schedule(task, 100, 1000);
	}

	@Override
	public void onDestroy()
	{
		if (task != null)
		{
			task.cancel();
			task = null;
		}
		if (timer != null)
		{
			timer.cancel();
			timer = null;
		}
		super.onDestroy();
	}

	@Override
	public void setPinRadians(double radiansHour, double radiansMinute, double radiansSecond)
	{
		clockDisplayView.setPinRadians(radiansHour, radiansMinute, radiansSecond);
	}

	@Override
	public boolean addClockChangeListener(ClockChangeListener listener)
	{
		return listeners.add(listener);
	}

	@Override
	public boolean removeClockChangeListener(ClockChangeListener listener)
	{
		return listeners.remove(listener);
	}
}
