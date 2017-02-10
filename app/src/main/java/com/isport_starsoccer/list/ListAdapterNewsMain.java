package com.isport_starsoccer.list;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;
import com.isport_starsoccer.*;
import com.isport_starsoccer.data.DataElementSportNews;
import com.isport_starsoccer.service.ImageUtil;
import com.squareup.picasso.Picasso;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Handler;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class ListAdapterNewsMain extends BaseAdapter 
{
	private Context context = null;
	private DataElementSportNews vData = null;
	private ImageUtil imgUtil = null;
	private int rowCount=0;

	
	public ListAdapterNewsMain(Context context, DataElementSportNews vData, ImageUtil imgUtil,int rowCount)
	{
		this.context = context;
		this.vData = vData;
		this.imgUtil = imgUtil;
		this.rowCount = rowCount;
	}
	

	@Override
	public int getCount() {
		return rowCount;
	}

	@Override
	public Object getItem(int position) {
		return vData;
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
			convertView = inflater.inflate(R.layout.sub_news_main, null);
			
		}
		TextView title = (TextView) convertView.findViewById(R.id.list_main_news_text_title);
		ImageView image = (ImageView) convertView.findViewById(R.id.list_main_news_image);

		final ProgressBar progress = (ProgressBar) convertView.findViewById(R.id.list_main_progress);
		
		if(vData != null )
		{
			
			SpannableString content = new SpannableString(vData.header);
		    content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
		    
		    String urlImage = "";

		    //title.setTextColor(Color.WHITE);
		    title.setMaxLines(1);
		    title.setText(content);
			urlImage = vData.img600;

			/*if(imgUtil.scaleSize() > 1.2)
			{
				//layout.getLayoutParams().height = (int)imgUtil.screen_height()/7;
				//layout.setLayoutParams(new LayoutParams((int)imgUtil.screen_width(), );
				image.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.TOP));
			}
			else
			{
				//layout.getLayoutParams().height = (int)imgUtil.screen_height()/7;
				//layout.setLayoutParams(new LayoutParams((int)imgUtil.screen_width(), (int)imgUtil.screen_height()/7));
				image.setLayoutParams(new FrameLayout.LayoutParams((int)imgUtil.screen_width(), (int)imgUtil.screen_height()/7, Gravity.TOP));
			}*/

			progress.setVisibility(View.VISIBLE);
			Picasso.with(context).load(urlImage)
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
		}
		SetAnimationView(convertView);
		return convertView;
	}
	
	private void SetAnimationView(View convertView)
	{

		Animation animation = null;
		DisplayMetrics metrics = new DisplayMetrics();
		animation = new TranslateAnimation(metrics.widthPixels/2, 0, 0, 0);
		   animation.setDuration(750);
		   convertView.startAnimation(animation);
		   animation = null;
	}
}
