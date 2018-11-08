package bck.kdan.quiz1.canvas;

import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class CanvasDisplayView extends View
{
	private Paint paint = null;
	private List<Path> paths = null;

	public CanvasDisplayView(Context context)
	{
		super(context);
		initialize();
	}

	public CanvasDisplayView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		initialize();
	}

	public CanvasDisplayView(Context context, AttributeSet attrs, int defStyle)
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
		paint.setColor(0xff00ff00);
		paint.setStrokeWidth(1f);
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);

		if (paths != null)
		{
			for (Path path: paths)
			{
				canvas.drawPath(path, paint);
			}
		}
	}

	public void setDisplayPaths(List<Path> paths)
	{
		this.paths = paths;
	}
}
