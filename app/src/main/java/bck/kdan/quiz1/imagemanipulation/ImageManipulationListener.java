package bck.kdan.quiz1.imagemanipulation;

import android.graphics.Bitmap;

public interface ImageManipulationListener
{
	public void onAddBitmap(Bitmap bitmap);

	public void onClear();

	public void onSelect(float x, float y);
	
	public void onDeselect();

	public void onMove(float x, float y);
	
	public void onScale(float scale);
	
	public void onRotate(float rotation);
}
