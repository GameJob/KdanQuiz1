package bck.kdan.quiz1.imagemanipulation;

import java.util.List;

import android.graphics.Bitmap;

public interface ImageManipulationModel
{
	public List<BitmapDisplayData> getBitmapConfigs();
	
	public void addBitmap(Bitmap bitmap);
	
	public void clear();

	public void select(float x, float y);
	
	public void deselect();

	public void move(float x, float y);
	
	public void scale(float scale);
	
	public void rotate(float rotation);
}
