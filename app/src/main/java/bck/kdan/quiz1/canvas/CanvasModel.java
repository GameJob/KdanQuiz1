package bck.kdan.quiz1.canvas;

import java.util.List;

import android.graphics.Path;

public interface CanvasModel
{
	public List<Path> getPaths();
	
	public void addPath();
	
	public void startAt(float x, float y);
	
	public void drawTo(float x, float y);
}
