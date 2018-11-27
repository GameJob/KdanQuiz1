package bck.kdan.quiz1.canvas;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Path;

public class CanvasModelImpl implements CanvasModel
{
	private List<Path> paths = null;
	private Path nowPath = null;

	public CanvasModelImpl()
	{
		paths = new ArrayList<Path>();
	}

	@Override
	public List<Path> getPaths()
	{
		return paths;
	}

	@Override
	public void addPath()
	{
		nowPath = new Path();
		paths.add(nowPath);
	}

	@Override
	public void startAt(float x, float y)
	{
		nowPath.moveTo(x, y);
	}

	@Override
	public void drawTo(float x, float y)
	{
		nowPath.lineTo(x, y);
	}
}
