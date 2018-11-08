package bck.kdan.quiz1.calculator;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.Space;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import bck.kdan.quiz1.R;

public class CalculatorFragment extends Fragment implements CalculatorView, OnClickListener
{
	private Context mContext;
	private TextView tvDisplay;
	
	private List<CalculatorListener> listeners = new ArrayList<CalculatorListener>();

	@Override
	public void onAttach(Context context)
	{
		super.onAttach(context);
		mContext = context;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View root = inflater.inflate(R.layout.fragment_calculator, container, false);
		LinearLayout layoutCalculator = root.findViewById(R.id.layoutCalculator);
		tvDisplay = root.findViewById(R.id.tvDisplay);
		tvDisplay.setText("0");
		createCalculatorKeyboard(layoutCalculator);
		return root;
	}
	
	private void createCalculatorKeyboard(LinearLayout layoutCalculator)
	{
		String [] [] commands = { { "7", "8", "9", "/" }, { "4", "5", "6", "*" }, { "1", "2", "3", "-" }, { "0", "", "", "+" }, { "", "", "C", "=" } };
		float [] weights = { 4, 4, 4, 3 };
		int padding = 5;
		float textSize = 14f;

		for (int row = 0; row < commands.length; row++)
		{
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			params.gravity = Gravity.CENTER;
			
			LinearLayout layout = new LinearLayout(mContext);
			layout.setOrientation(LinearLayout.HORIZONTAL);
			layout.setLayoutParams(params);
			layout.setPadding(padding, padding, padding, padding);
			layoutCalculator.addView(layout);
			
			for (int col = 0; col < commands[row].length; col++)
			{
				params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
				params.gravity = Gravity.CENTER;
				params.weight = weights[col];
				
				View button;
				if (!commands[row][col].isEmpty())
				{
					button = new Button(mContext);
					((Button)button).setText(commands[row][col]);
					((Button)button).setTextSize(textSize);
					
					button.setOnClickListener(this);
				}
				else
				{
					button = new Space(mContext);
				}
				button.setPadding(padding, padding, padding, padding);
				button.setLayoutParams(params);
				layout.addView(button);
			}
		}
	}

	@Override
	public void display(String text)
	{
		tvDisplay.setText(text);
	}

	@Override
	public boolean addCalculatorListener(CalculatorListener listener)
	{
		return listeners.add(listener);
	}

	@Override
	public boolean removeCalculatorListener(CalculatorListener listener)
	{
		return listeners.remove(listener);
	}

	@Override
	public void onClick(View view)
	{
		Button button = (Button)view;
		char command = button.getText().toString().charAt(0);
		
		if (command >= '0' && command <= '9')
		{
			int number = command - '0';
			for (CalculatorListener listener : listeners)
			{
				listener.onNumberClick(number);
			}
		}
		else if (command == '=')
		{
			for (CalculatorListener listener : listeners)
			{
				listener.onEqualSignClick();
			}
		}
		else if (command == 'C')
		{
			for (CalculatorListener listener : listeners)
			{
				listener.onCleanClick();
			}
		}
		else
		{
			for (CalculatorListener listener : listeners)
			{
				listener.onOperatorClick(command);
			}
		}
	}
}
