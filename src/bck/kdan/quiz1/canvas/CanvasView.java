package bck.kdan.quiz1.canvas;

import java.util.List;

import android.graphics.Path;

public interface CanvasView
{
	public void setDisplayPaths(List<Path> paths);
	
	public void updateDisplayView();
	
	public boolean addCanvasListener(CanvasListener listener);
	
	public boolean removeCanvasListener(CanvasListener listener);
}
