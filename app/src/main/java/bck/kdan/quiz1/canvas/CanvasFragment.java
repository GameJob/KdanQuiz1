package bck.kdan.quiz1.canvas;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Path;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import bck.kdan.quiz1.R;

public class CanvasFragment extends Fragment implements CanvasView, OnTouchListener
{
	private List<CanvasListener> listeners = new ArrayList<CanvasListener>();
	private CanvasDisplayView canvasDisplayView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View root = inflater.inflate(R.layout.fragment_canvas, container, false);
		canvasDisplayView = root.findViewById(R.id.canvasDisplayView);
		canvasDisplayView.setOnTouchListener(this);
		return root;
	}

	@Override
	public void setDisplayPaths(List<Path> paths)
	{
		canvasDisplayView.setDisplayPaths(paths);
	}

	@Override
	public void updateDisplayView()
	{
		canvasDisplayView.invalidate();
	}

	@Override
	public boolean addCanvasListener(CanvasListener listener)
	{
		return listeners.add(listener);
	}

	@Override
	public boolean removeCanvasListener(CanvasListener listener)
	{
		return listeners.remove(listener);
	}

	@Override
	public boolean onTouch(View view, MotionEvent event)
	{
		int actionIndex = event.getActionIndex();
		int pointerId = event.getPointerId(actionIndex);
		float x = event.getX(actionIndex);
		float y = event.getY(actionIndex);
		if (event.getAction() == MotionEvent.ACTION_DOWN)
		{
			for (CanvasListener listener: listeners)
			{
				listener.onTouchBegan(pointerId, x, y);
			}
			return true;
		}
		else if (event.getAction() == MotionEvent.ACTION_MOVE)
		{
			for (CanvasListener listener: listeners)
			{
				listener.onTouchMoved(pointerId, x, y);
			}
		}
		else if (event.getAction() == MotionEvent.ACTION_UP)
		{
			for (CanvasListener listener: listeners)
			{
				listener.onTouchEnded(pointerId, x, y);
			}
			return true;
		}

		return false;
	}
}
