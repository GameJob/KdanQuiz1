package bck.kdan.quiz1.imagemanipulation;

import android.graphics.Bitmap;
import android.graphics.PointF;

public class BitmapDisplayData
{
	private Bitmap bitmap;
	private PointF position = new PointF();
	private float scale = 1;
	private float rotation = 0;

	public BitmapDisplayData(Bitmap bitmap)
	{
		this.bitmap = bitmap;
		position.x = bitmap.getWidth() * 0.5f;
		position.y = bitmap.getHeight() * 0.5f;
	}

	public Bitmap getBitmap()
	{
		return bitmap;
	}

	public float getBitmapWidth()
	{
		return bitmap.getWidth();
	}

	public float getBitmapHeight()
	{
		return bitmap.getHeight();
	}

	public float getX()
	{
		return position.x;
	}

	public float getY()
	{
		return position.y;
	}

	public float getScale()
	{
		return scale;
	}

	public float getRotation()
	{
		return rotation;
	}

	public void moveBy(float x, float y)
	{
		position.x += x;
		position.y += y;
	}

	public void rotateBy(float rotation)
	{
		this.rotation += rotation;
	}

	public void scaleBy(float scale)
	{
		this.scale *= scale;
	}

	public void recycle()
	{
		bitmap.recycle();
	}

	public boolean containPoint(float x, float y)
	{
		// get translated position
		double nx = x - position.x;
		double ny = y - position.y;
		// get rotated position
		double degreeToRadians = Math.PI / 180.0;
		double sin = Math.sin(rotation * degreeToRadians);
		double cos = Math.cos(rotation * degreeToRadians);
		double tx = nx * cos + ny * sin;
		double ty = -nx * sin + ny * cos;
		// check inside
		double boundWidth = getBitmapWidth() * 0.5f * scale;
		double boundHeight = getBitmapHeight() * 0.5f * scale;
		return tx >= -boundWidth && tx <= boundWidth && ty >= -boundHeight && ty <= boundHeight;
	}
}
