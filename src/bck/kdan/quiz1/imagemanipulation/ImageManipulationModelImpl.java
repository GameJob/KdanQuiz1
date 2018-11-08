package bck.kdan.quiz1.imagemanipulation;

import java.util.List;
import java.util.Vector;

import android.graphics.Bitmap;

public class ImageManipulationModelImpl implements ImageManipulationModel
{
	private List<BitmapDisplayData> dataList = null;
	private BitmapDisplayData selected = null;

	public ImageManipulationModelImpl()
	{
		dataList = new Vector<BitmapDisplayData>();
	}

	@Override

	public List<BitmapDisplayData> getBitmapConfigs()
	{
		return dataList;
	}

	@Override
	public void addBitmap(Bitmap bitmap)
	{
		dataList.add(new BitmapDisplayData(bitmap));
	}

	@Override
	public void clear()
	{
		deselect();
		for (BitmapDisplayData data: dataList)
		{
			data.recycle();
		}
		dataList.clear();
	}

	@Override
	public void select(float x, float y)
	{
		for (BitmapDisplayData data: dataList)
		{
			if (data.containPoint(x, y))
			{
				selected = data;
				break;
			}
		}
	}

	@Override
	public void deselect()
	{
		selected = null;
	}

	@Override
	public void move(float x, float y)
	{
		if (selected != null)
		{
			selected.moveBy(x, y);
		}
	}

	@Override
	public void scale(float scale)
	{
		for (BitmapDisplayData data: dataList)
		{
			data.scaleBy(scale);
		}
	}

	@Override
	public void rotate(float rotation)
	{
		for (BitmapDisplayData data: dataList)
		{
			data.rotateBy(rotation);
		}
	}
}
