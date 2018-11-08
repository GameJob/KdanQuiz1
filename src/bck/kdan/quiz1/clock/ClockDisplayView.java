package bck.kdan.quiz1.clock;

import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.util.AttributeSet;
import android.view.View;

public class ClockDisplayView extends View
{
	private Paint paint = null;
	private float ox;
	private float oy;

	private float sinHour = 0;
	private float sinMinute = 0;
	private float sinSecond = 0;

	private float cosHour = 1;
	private float cosMinute = 1;
	private float cosSecond = 1;

	private float radius = 1;
	private float lengthHour = 1;
	private float lengthMinute = 1;
	private float lengthSecond = 1;

	public ClockDisplayView(Context context)
	{
		super(context);
		initialize();
	}

	public ClockDisplayView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		initialize();
	}

	public ClockDisplayView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		initialize();
	}

	private void initialize()
	{		
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeCap(Cap.ROUND);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom)
	{
		super.onLayout(changed, left, top, right, bottom);

		radius = Math.min(getMeasuredWidth(), getMeasuredHeight()) * 0.5f;
		lengthHour = radius * 0.5f;
		lengthMinute = radius * 0.7f;
		lengthSecond = radius * 0.9f;
		
		ox = getMeasuredWidth() * 0.5f;
		oy = getMeasuredHeight() * 0.5f;
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);

		paint.setColor(0xff00ff00);
		paint.setStrokeWidth(10f);
		canvas.drawCircle(ox, oy, radius, paint);

		paint.setColor(0xff0000ff);
		paint.setStrokeWidth(5f);
		canvas.drawLine(ox, oy, ox + lengthHour * sinHour, oy - lengthHour * cosHour, paint);

		paint.setColor(0xffff0000);
		paint.setStrokeWidth(4f);
		canvas.drawLine(ox, oy, ox + lengthMinute * sinMinute, oy - lengthMinute * cosMinute, paint);

		paint.setColor(0xffffffff);
		paint.setStrokeWidth(2f);
		canvas.drawLine(ox, oy, ox + lengthSecond * sinSecond, oy - lengthSecond * cosSecond, paint);

		invalidate();
	}

	public void setPinRadians(double radiansHour, double radiansMinute, double radiansSecond)
	{
		sinHour = (float)Math.sin(radiansHour);
		sinMinute = (float)Math.sin(radiansMinute);
		sinSecond = (float)Math.sin(radiansSecond);

		cosHour = (float)Math.cos(radiansHour);
		cosMinute = (float)Math.cos(radiansMinute);
		cosSecond = (float)Math.cos(radiansSecond);
	}
}
