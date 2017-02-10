package com.isport_starsoccer.list;

import java.util.HashMap;
import java.util.Vector;
import com.isport_starsoccer.R;
import com.isport_starsoccer.data.DataElementEvent;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ListAdapterEvent extends BaseAdapter
{
	private Context context = null;
	private Vector<DataElementEvent> vData = null;
	
	public ListAdapterEvent(Context context, Vector<DataElementEvent> vData)
	{
		this.context = context;
		this.vData = vData;
	}

	@Override
	public int getCount() {
		return vData.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null)
		{
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.sub_hot, null);
		}
		
		ImageView img = (ImageView) convertView.findViewById(R.id.sub_hot_img);
		final ProgressBar progress = (ProgressBar) convertView.findViewById(R.id.sub_hot_progress);

		if(vData.size() > 0)
		{
			DataElementEvent data = vData.elementAt(position);
			progress.setVisibility(View.VISIBLE);
			Picasso.with(context).load(data.img2)
					.into(img, new com.squareup.picasso.Callback() {
						@Override
						public void onSuccess() {
							if (progress != null) {
								progress.setVisibility(View.GONE);
							}
						}

						@Override
						public void onError() {

						}
					});
			/*
			if(!data.checkLoad)
			{
				data.checkLoad = true;
				AsycTaskLoadImage load = new AsycTaskLoadImage(null,null,hImg,null,this);
				load.execute(data.img2);
			}
			
			if(hImg != null)
			{
				if(hImg.get(data.img2) != null)
				{
					//img.setImageBitmap(hImg.get(data.img2));
					progress.setVisibility(View.INVISIBLE);
					img.setBackgroundDrawable(new BitmapDrawable(hImg.get(data.img2)));
//					img.setImageBitmap(hImg.get(data.img1));
					
				}
			}
			else
			{
				img.setBackgroundDrawable(null);
//				img.setImageBitmap(null);
				progress.setVisibility(View.VISIBLE);
			}*/
		}
		
		return convertView;
	}
}