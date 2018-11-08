package bck.kdan.quiz1.imagemanipulation;

import java.util.ArrayList;
import java.util.List;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import bck.kdan.quiz1.FileUtils;
import bck.kdan.quiz1.MainActivity;
import bck.kdan.quiz1.R;

public class ImageManipulationFragment extends Fragment implements ImageManipulationView, OnClickListener, OnTouchListener, OnScaleGestureListener
{
	private static final int FILE_PICKER_EXTERNAL_READ_PERMISSION_GRANT = 112;
	private static final int GALLERY_REQUEST_CODE = 113;

	private Context mContext;
	private List<ImageManipulationListener> listeners = new ArrayList<ImageManipulationListener>();
	private ImageManipulationDisplayView imageManipulationDisplayView;

	private ScaleGestureDetector detector;

	private float oldTouchX;
	private float oldTouchY;
	private float oldRotation;
	private float oldRotateX;
	private float oldRotateY;
	private boolean startRotating = false;

	@Override
	public void onAttach(Context context)
	{
		super.onAttach(context);
		this.mContext = context;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View root = inflater.inflate(R.layout.fragment_imagemanipulation, container, false);
		Button btnLoad = root.findViewById(R.id.btnLoad);
		Button btnClear = root.findViewById(R.id.btnClear);
		btnLoad.setOnClickListener(this);
		btnClear.setOnClickListener(this);
		detector = new ScaleGestureDetector(mContext, this);
		imageManipulationDisplayView = root.findViewById(R.id.imageManipulationDisplayView);
		imageManipulationDisplayView.setOnTouchListener(this);
		return root;
	}

	@Override
	public void setBitmapDisplayDataList(List<BitmapDisplayData> dataList)
	{
		imageManipulationDisplayView.setBitmapDisplayData(dataList);
	}

	@Override
	public void updateDisplayView()
	{
		imageManipulationDisplayView.invalidate();
	}

	@Override
	public boolean addImageManipulationListener(ImageManipulationListener listener)
	{
		return listeners.add(listener);
	}

	@Override
	public boolean removeImageManipulationListener(ImageManipulationListener listener)
	{
		return listeners.remove(listener);
	}

	@Override
	public void onClick(View view)
	{
		if (view.getId() == R.id.btnLoad)
		{
			if (Build.VERSION.SDK_INT >= 23)
			{
				if (!MainActivity.hasPermissions(mContext, Manifest.permission.READ_EXTERNAL_STORAGE))
				{
					requestPermissions(new String [] { Manifest.permission.READ_EXTERNAL_STORAGE }, FILE_PICKER_EXTERNAL_READ_PERMISSION_GRANT);
				}
				else
				{
					getPictureFromGallery();
				}
			}
			else
			{
				getPictureFromGallery();
			}
		}
		else if (view.getId() == R.id.btnClear)
		{
			for (ImageManipulationListener listener: listeners)
			{
				listener.onClear();
			}
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int [] grantResults)
	{
		if (requestCode == FILE_PICKER_EXTERNAL_READ_PERMISSION_GRANT)
		{
			if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
			{
				getPictureFromGallery();
			}
			else
			{
				// Permission has not been granted. Notify the user.
				Toast.makeText(mContext, "Permission is Required", Toast.LENGTH_SHORT).show();
			}
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == GALLERY_REQUEST_CODE && data != null)
		{
			Bitmap bitmap = FileUtils.getInstance().getBitmapFromIntent(mContext, data, 768);
			for (ImageManipulationListener listener: listeners)
			{
				listener.onAddBitmap(bitmap);
			}
		}
	}

	private void getPictureFromGallery()
	{
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType("image/*");
		startActivityForResult(Intent.createChooser(intent, "Select"), GALLERY_REQUEST_CODE);
	}

	@Override
	public boolean onTouch(View view, MotionEvent event)
	{
		int actionIndex = event.getActionIndex();
		int pointerId = event.getPointerId(actionIndex);
		int pointerCount = event.getPointerCount();
		if (event.getAction() == MotionEvent.ACTION_DOWN)
		{
			if (pointerId == 0 && pointerCount == 1)
			{
				handleSelectEvent(event);
				return true;
			}
		}
		else if (event.getAction() == MotionEvent.ACTION_UP)
		{
			if (pointerCount == 1)
			{
				handleDeselectEvent(event);
			}
		}
		else if (event.getAction() == MotionEvent.ACTION_POINTER_DOWN)
		{
			handleRotateBeganEvent(event);
			return true;
		}
		else if (event.getAction() == MotionEvent.ACTION_MOVE)
		{
			if (pointerCount == 1)
			{
				handleMoveEvent(event);
			}
			else
			{
				handleRotateEvent(event);
			}
		}

		return detector.onTouchEvent(event);
	}

	private void handleSelectEvent(MotionEvent event)
	{
		int actionIndex = event.getActionIndex();
		float x = event.getX(actionIndex);
		float y = event.getY(actionIndex);
		for (ImageManipulationListener listener: listeners)
		{
			listener.onSelect(x, y);
		}
		oldTouchX = x;
		oldTouchY = y;
	}

	private void handleDeselectEvent(MotionEvent event)
	{
		for (ImageManipulationListener listener: listeners)
		{
			listener.onDeselect();
		}
		startRotating = false;
	}

	private void handleMoveEvent(MotionEvent event)
	{
		int actionIndex = event.getActionIndex();
		float x = event.getX(actionIndex);
		float y = event.getY(actionIndex);
		float diffX = x - oldTouchX;
		float diffY = y - oldTouchY;
		for (ImageManipulationListener listener: listeners)
		{
			listener.onMove(diffX, diffY);
		}
		oldTouchX = x;
		oldTouchY = y;
	}

	private void handleRotateBeganEvent(MotionEvent event)
	{
		int actionIndex = event.getActionIndex();
		int pointerId = event.getPointerId(actionIndex);
		float x = event.getX(actionIndex);
		float y = event.getY(actionIndex);
		if (pointerId == 0)
		{
			oldTouchX = x;
			oldTouchY = y;
		}
		else
		{
			oldRotateX = x;
			oldRotateY = y;
		}
	}

	private void handleRotateEvent(MotionEvent event)
	{
		for (int i = 0; i < 2; i++)
		{
			int actionIndex = i;
			int pointerId = event.getPointerId(actionIndex);
			float x = event.getX(actionIndex);
			float y = event.getY(actionIndex);
			if (pointerId == 0)
			{
				oldTouchX = x;
				oldTouchY = y;
			}
			else
			{
				oldRotateX = x;
				oldRotateY = y;
			}
		}

		float diffX = oldTouchX - oldRotateX;
		float diffY = oldTouchY - oldRotateY;
		float rotation = diffX == 0? 0: (float)Math.atan2(diffY, diffX);
		rotation *= 180 / Math.PI;
		if (!startRotating)
		{
			startRotating = true;
		}
		else
		{
			for (ImageManipulationListener listener: listeners)
			{
				listener.onRotate(rotation - oldRotation);
			}
		}
		oldRotation = rotation;
	}

	@Override
	public boolean onScale(ScaleGestureDetector detector)
	{
		float scale = detector.getScaleFactor();
		for (ImageManipulationListener listener: listeners)
		{
			listener.onScale(scale);
		}
		return true;
	}

	@Override
	public boolean onScaleBegin(ScaleGestureDetector detector)
	{
		return true;
	}

	@Override
	public void onScaleEnd(ScaleGestureDetector detector)
	{
	}
}
