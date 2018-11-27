package bck.kdan.quiz1.canvas;

import bck.kdan.quiz1.Presenter;

public class CanvasPresenter implements Presenter, CanvasListener
{
	private CanvasView view;
	private CanvasModel model;
	
	public CanvasPresenter(CanvasView view)
	{
		this.view = view;
		this.model = new CanvasModelImpl();
		this.view.addCanvasListener(this);
	}

	@Override
	public void onTouchBegan(int pointerId, float x, float y)
	{
		if (pointerId != 0)
			return;
		model.addPath();
		model.startAt(x, y);
		view.setDisplayPaths(model.getPaths());
		view.updateDisplayView();
	}

	@Override
	public void onTouchMoved(int pointerId, float x, float y)
	{
		if (pointerId != 0)
			return;
		model.drawTo(x, y);
		view.updateDisplayView();
	}

	@Override
	public void onTouchEnded(int pointerId, float x, float y)
	{
		if (pointerId != 0)
			return;
		view.updateDisplayView();
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
