package com.isport_starsoccer.list;

import java.util.HashMap;
import java.util.Vector;

import com.isport_starsoccer.R;
import com.isport_starsoccer.data.DataElementSportNews;
import com.isport_starsoccer.service.ImageUtil;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ListAdapterNews extends BaseAdapter
{
	private Context context = null;
	private Vector<DataElementSportNews> vData = null;
	private ImageUtil imgUtil = null;

	
	public ListAdapterNews(Context context, Vector<DataElementSportNews> vData, ImageUtil imgUtil)
	{
		this.context = context;
		this.vData = vData;
		this.imgUtil = imgUtil;
	}

	@Override
	public int getCount() {
		return vData.size();
	}

	@Override
	public Object getItem(int position) {
		return vData.elementAt(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		if(convertView == null)
		{
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.sub_news , null);
		}

		LinearLayout list_main_news_layout = (LinearLayout)convertView.findViewById(R.id.list_main_news_layout);
		TextView title = (TextView) convertView.findViewById(R.id.list_main_news_text_title);
		TextView detail = (TextView) convertView.findViewById(R.id.list_main_news_text_detail);
		ImageView image = (ImageView) convertView.findViewById(R.id.list_main_news_image);
		final ProgressBar progress = (ProgressBar) convertView.findViewById(R.id.list_main_progress);
		ImageView hilight = (ImageView) convertView.findViewById(R.id.list_main_news_hilight);
		
		//list_main_news_layout.setPadding((int)(10*imgUtil.scaleSize()), 0, (int)(10*imgUtil.scaleSize()), 0);
		//convertView.setPadding(0, (int)(10*imgUtil.scaleSize()), 0, (int)(10*imgUtil.scaleSize()));		
		//progress.setLayoutParams(new FrameLayout.LayoutParams((int)(30*imgUtil.scaleSize()), (int)(30*imgUtil.scaleSize()), Gravity.CENTER));
		
		convertView.setBackgroundColor(Color.rgb(207, 207, 207));
		
		int x=10,y=5;
		if(imgUtil.scaleSize() > 1.2)
		{
			progress.setPadding((int)(x*imgUtil.scaleSize()), (int)(x*imgUtil.scaleSize()), 0, 10);
			image.setPadding((int)(x*imgUtil.scaleSize()), (int)(x*imgUtil.scaleSize()), 0, 10);
			list_main_news_layout.setPadding((int)(x*imgUtil.scaleSize()), (int)(x*imgUtil.scaleSize()), 0, 10);
			image.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.TOP));
		}
		else 
		{
			progress.setPadding((int)(y*imgUtil.scaleSize()), 0, 0, 0);
			image.setPadding((int)(y*imgUtil.scaleSize()), 0, 0, 0);
			list_main_news_layout.setPadding((int)(y*imgUtil.scaleSize()), 0, 0, 0);
			//image.setLayoutParams(new FrameLayout.LayoutParams((int)imgUtil.screen_width()/3, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
			image.setLayoutParams(new FrameLayout.LayoutParams((int)imgUtil.screen_width()/3, (int)imgUtil.screen_height()/7, Gravity.TOP));
		}
		
		
		if(vData != null && vData.elementAt(position) != null)
		{
			DataElementSportNews data = vData.elementAt(position);
			
			SpannableString content = new SpannableString(data.header);
		    content.setSpan(new UnderlineSpan(), 0, content.length(), 0);

			title.setText(content);
			detail.setText(data.title);
			title.setTextColor(Color.rgb(45, 45, 45));
			detail.setTextColor(Color.rgb(45, 45, 45));
			title.setMaxLines(2);
			detail.setMaxLines(2);

			progress.setVisibility(View.VISIBLE);
			Picasso.with(context).load(data.img350)
					.into(image, new com.squareup.picasso.Callback() {
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
			/*if(hImage!= null && hImage.get(data.img350) == null)
			{
				if(!data.checkLoad && data.img350.length() > 0)
				{
					
					data.checkLoad = true;
					AsycTaskLoadImage loadImage = new AsycTaskLoadImage(null, null, hImage, null, this);
					loadImage.execute(data.img350);
				}
			}
			
			if(hImage!= null && hImage.get(data.img350)!= null)
			{
				if(progress.getVisibility() == View.VISIBLE)
					progress.setVisibility(View.INVISIBLE);
				
				image.setImageBitmap(hImage.get(data.img350));
			}
			else if(!(data.img350.length() > 0))
			{
				//image.setImageResource(R.drawable.img_small);
			}
			else
			{
//				image.setImageResource(R.drawable.img_small);
//				progress.setVisibility(View.INVISIBLE);
				
				image.setImageBitmap(null);
				progress.setVisibility(View.VISIBLE);
			}*/
		}
		return convertView;
	}
}
