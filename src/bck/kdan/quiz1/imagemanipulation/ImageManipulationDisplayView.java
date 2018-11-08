package bck.kdan.quiz1.imagemanipulation;

import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.util.AttributeSet;
import android.view.View;

public class ImageManipulationDisplayView extends View
{
	private Paint paint = null;
	private List<BitmapDisplayData> dataList = null;

	public ImageManipulationDisplayView(Context context)
	{
		super(context);
		initialize();
	}

	public ImageManipulationDisplayView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		initialize();
	}

	public ImageManipulationDisplayView(Context context, AttributeSet attrs, int defStyle)
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
		paint.setColor(0xff000000);
		paint.setStrokeWidth(2f);
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);

		if (dataList != null)
		{
			for (BitmapDisplayData data: dataList)
			{
				drawBitmap(canvas, data);
			}
		}
	}
	
	private void drawBitmap(Canvas canvas, BitmapDisplayData data)
	{
		canvas.save();
		canvas.translate(data.getX(), data.getY());
		canvas.scale(data.getScale(), data.getScale());
		canvas.rotate(data.getRotation());
		canvas.translate(-data.getBitmapWidth() * 0.5f, -data.getBitmapHeight() * 0.5f);
		canvas.drawBitmap(data.getBitmap(), 0, 0, paint);
		canvas.restore();
	}

	public void setBitmapDisplayData(List<BitmapDisplayData> dataList)
	{
		this.dataList = dataList;
	}
}
