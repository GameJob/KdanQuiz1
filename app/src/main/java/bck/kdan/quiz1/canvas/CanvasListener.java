package bck.kdan.quiz1.canvas;

public interface CanvasListener
{
	public void onTouchBegan(int pointerId, float x, float y);

	public void onTouchMoved(int pointerId, float x, float y);

	public void onTouchEnded(int pointerId, float x, float y);
}
