package bck.kdan.quiz1.imagemanipulation;

import android.graphics.Bitmap;
import bck.kdan.quiz1.Presenter;

public class ImageManipulationPresenter implements Presenter, ImageManipulationListener
{
	private ImageManipulationView view;
	private ImageManipulationModel model;

	public ImageManipulationPresenter(ImageManipulationView view)
	{
		this.view = view;
		this.model = new ImageManipulationModelImpl();
		this.view.addImageManipulationListener(this);
	}

	@Override
	public void onAddBitmap(Bitmap bitmap)
	{
		model.addBitmap(bitmap);
		view.setBitmapDisplayDataList(model.getBitmapConfigs());
		view.updateDisplayView();
	}

	@Override
	public void onClear()
	{
		model.clear();
		view.updateDisplayView();
	}

	@Override
	public void onSelect(float x, float y)
	{
		model.select(x, y);
	}

	@Override
	public void onDeselect()
	{
		model.deselect();
	}

	@Override
	public void onMove(float x, float y)
	{
		model.move(x, y);
		view.updateDisplayView();
	}

	@Override
	public void onScale(float scale)
	{
		model.scale(scale);
		view.updateDisplayView();
	}

	@Override
	public void onRotate(float rotation)
	{
		model.rotate(rotation);
		view.updateDisplayView();
	}

	@Override
	public void onCreate()
	{

	}

	@Override
	public void onPause()
	{

	}

	@Override
	public void onResume()
	{

	}

	@Override
	public void onDestroy()
	{
		model.clear();
	}
}
