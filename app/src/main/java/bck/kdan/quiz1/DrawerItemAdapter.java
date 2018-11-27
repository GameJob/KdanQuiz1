package bck.kdan.quiz1;

import java.util.List;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DrawerItemAdapter extends BaseAdapter
{
	private LayoutInflater inflater;
	private List<Pair<String, OnClickListener>> items;

	public DrawerItemAdapter(Context context, List<Pair<String, OnClickListener>> items)
	{
		inflater = LayoutInflater.from(context);
		this.items = items;
	}

	@Override
	public int getCount()
	{
		return items.size();
	}

	@Override
	public Object getItem(int position)
	{
		return items.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return items.indexOf(getItem(position));
	}

	@Override
	public View getView(int position, View view, ViewGroup parent)
	{
		DrawerItemViewHolder holder = null;
		if (view == null)
		{
			view = inflater.inflate(R.layout.drawer_item, null);
			holder = new DrawerItemViewHolder((TextView)view.findViewById(R.id.tvText));
			view.setTag(holder);
		}
		else
		{
			holder = (DrawerItemViewHolder)view.getTag();
		}

		Pair<String, OnClickListener> pair = items.get(position);
		holder.tvText.setText(pair.first);
		view.setOnClickListener(pair.second);

		return view;
	}

	class DrawerItemViewHolder
	{
		private TextView tvText;

		public DrawerItemViewHolder(TextView tvText)
		{
			this.tvText = tvText;
		}
	}
}
