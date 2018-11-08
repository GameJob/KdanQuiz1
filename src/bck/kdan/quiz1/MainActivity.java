package bck.kdan.quiz1;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import bck.kdan.quiz1.calculator.CalculatorFragment;
import bck.kdan.quiz1.calculator.CalculatorPresenter;
import bck.kdan.quiz1.canvas.CanvasFragment;
import bck.kdan.quiz1.canvas.CanvasPresenter;
import bck.kdan.quiz1.clock.ClockFragment;
import bck.kdan.quiz1.clock.ClockPresenter;
import bck.kdan.quiz1.imagemanipulation.ImageManipulationFragment;
import bck.kdan.quiz1.imagemanipulation.ImageManipulationPresenter;

public class MainActivity extends AppCompatActivity
{	
	private Presenter presenter;

	@Override
	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initMainUI();

		Fragment fragment = new CalculatorFragment();
		presenter = new CalculatorPresenter((CalculatorFragment)fragment);
		navigateTo(fragment);
	}

	private void navigateTo(Fragment fragment)
	{
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.container, fragment);
		transaction.commit();
	}

	@SuppressLint("NewApi")
	public static boolean hasPermissions(Context context, String... permissions)
	{
		if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null)
		{
			for (String permission: permissions)
			{
				if (context.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED)
				{
					return false;
				}
			}
		}
		return true;
	}

	/** Called when leaving the activity */
	@Override
	public void onPause()
	{
		if (presenter != null)
			presenter.onPause();
		super.onPause();
	}

	/** Called when returning to the activity */
	@Override
	public void onResume()
	{
		super.onResume();
		if (presenter != null)
			presenter.onResume();
	}

	/** Called before the activity is destroyed */
	@Override
	public void onDestroy()
	{
		if (presenter != null)
			presenter.onDestroy();
		super.onDestroy();
	}

	@SuppressWarnings("deprecation")
	private void initMainUI()
	{
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayUseLogoEnabled(true);

		DrawerLayout drawerLayout = findViewById(R.id.drawerlayout);
		ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
		actionBarDrawerToggle.syncState();
		drawerLayout.setDrawerListener(actionBarDrawerToggle);

		RelativeLayout layoutBackground = findViewById(R.id.layoutBackground);
		layoutBackground.setOnClickListener(null);

		ListView leftDrawer = findViewById(R.id.leftDrawer);

		List<Pair<String, OnClickListener>> items = new ArrayList<Pair<String, OnClickListener>>();
		items.add(new Pair<String, OnClickListener>("Calculator", showCalculatorFragment(drawerLayout)));
		items.add(new Pair<String, OnClickListener>("Canvas", showCanvasFragment(drawerLayout)));
		items.add(new Pair<String, OnClickListener>("Clock", showClockFragment(drawerLayout)));
		items.add(new Pair<String, OnClickListener>("Image Manipulation", showImageManipulationFragment(drawerLayout)));

		DrawerItemAdapter adapter = new DrawerItemAdapter(this, items);
		leftDrawer.setAdapter(adapter);
	}

	private OnClickListener showCalculatorFragment(final DrawerLayout drawerLayout)
	{
		return new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Fragment fragment = new CalculatorFragment();
				presenter = new CalculatorPresenter((CalculatorFragment)fragment);
				navigateTo(fragment);
				drawerLayout.closeDrawers();
			}
		};
	}

	private OnClickListener showCanvasFragment(final DrawerLayout drawerLayout)
	{
		return new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Fragment fragment = new CanvasFragment();
				presenter = new CanvasPresenter((CanvasFragment)fragment);
				navigateTo(fragment);
				drawerLayout.closeDrawers();
			}
		};
	}

	private OnClickListener showClockFragment(final DrawerLayout drawerLayout)
	{
		return new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Fragment fragment = new ClockFragment();
				presenter = new ClockPresenter((ClockFragment)fragment);
				navigateTo(fragment);
				drawerLayout.closeDrawers();
			}
		};
	}

	private OnClickListener showImageManipulationFragment(final DrawerLayout drawerLayout)
	{
		return new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Fragment fragment = new ImageManipulationFragment();
				presenter = new ImageManipulationPresenter((ImageManipulationFragment)fragment);
				navigateTo(fragment);
				drawerLayout.closeDrawers();
			}
		};
	}
}
