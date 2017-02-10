package com.isport_starsoccer.list;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;
import com.isport_starsoccer.R;
import com.isport_starsoccer.data.DataElementGroupListMenu;
import com.isport_starsoccer.data.DataElementListMenu;
import com.isport_starsoccer.service.ImageUtil;
import com.squareup.picasso.Picasso;

import android.R.string;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

public class ListAdapterMenuLeague extends BaseExpandableListAdapter
{
	private ArrayList<DataElementGroupListMenu> mGroupData=null;
	private Context context = null;
	private ImageUtil imgUtil = null;
	private String message = "";
	private ExpandableListView listView = null;
	private int[] groupStatus;
	
	public ListAdapterMenuLeague(Context context ,ExpandableListView listView
			, ArrayList<DataElementGroupListMenu> mGroupData
			, ImageUtil imgUtil, String message)
	{
		this.mGroupData = mGroupData;
		this.listView = listView;
		this.context = context; 
		this.imgUtil = imgUtil;
		this.message = message;
		groupStatus = new int[mGroupData.size()];
		setListEvent();
	}
	
	private void setListEvent() {
		

		listView.setOnGroupExpandListener(new OnGroupExpandListener() {

					@Override
					public void onGroupExpand(int arg0) {
						groupStatus[arg0] = 1;
					}
				});

		listView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

					@Override
					public void onGroupCollapse(int arg0) {
						groupStatus[arg0] = 0;
					}
				});
	}

	@Override
	public Object getChild(int arg0, int arg1) {
		return mGroupData.get(arg0).GroupLeagueCollection.get(arg1);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return 0;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		if(convertView == null)
		{
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.sub_menuleague_group_league,null);
		}
		
		
		TextView sub_menuleague_league = (TextView)convertView.findViewById(R.id.sub_menuleague_league);
		
		
		sub_menuleague_league.setText(mGroupData.get(groupPosition).GroupLeagueCollection.get(childPosition).name);
		convertView.setBackgroundColor(Color.rgb(232, 232, 232));
		SetAnimationView(convertView);
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return mGroupData.get(groupPosition).GroupLeagueCollection.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return mGroupData.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return mGroupData.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		if(convertView == null)
		{
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.sub_menuleague_group_country,null);
		}
			
			ImageView imgCountry = (ImageView)convertView.findViewById(R.id.sub_menuleague_img_country);
			TextView sub_menuleague_country = (TextView)convertView.findViewById(R.id.sub_menuleague_txt_name);
			ImageView imgGroup = (ImageView)convertView.findViewById(R.id.sub_menuleague_img_group);
			DataElementListMenu data = mGroupData.get(groupPosition).countryData;
			sub_menuleague_country.setText(data.name);
			//imgCountry.setPadding((int)5, 5, (int)(5*imgUtil.scaleSize()), 0);
		final ProgressBar progress = new ProgressBar(context);
		progress.setVisibility(View.VISIBLE);
		Picasso.with(context).load(data.icon16x11)
				.into(imgCountry, new com.squareup.picasso.Callback() {
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
			if(!data.checkLoad && data.icon16x11.length() > 0 )
			{
				if(hImage!= null && hImage.get(data.icon16x11) == null)
				{
					data.checkLoad = true;
					AsycTaskLoadImage loadImage = new AsycTaskLoadImage(null, null, hImage, null, this);
					loadImage.execute(data.icon16x11);
				}
			}

			if(hImage!= null && hImage.get(data.icon16x11) != null)
			{
				imgCountry.setImageBitmap(hImage.get(data.icon16x11));
			}
			else
			{
				imgCountry.setImageBitmap(null);
			}*/
			
			if (groupStatus[groupPosition] == 0) {
				imgGroup.setImageResource(R.drawable.group_down);
			} else {
				imgGroup.setImageResource(R.drawable.group_up);
			}
			
			
			//convertView.setBackgroundColor(Color.rgb(180, 180, 180));
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
	private void SetAnimationView(View convertView)
	{
		
		Animation animation = null;
		DisplayMetrics metrics = new DisplayMetrics();
		//animation = new TranslateAnimation(metrics.widthPixels/2, 0, 0, 0);
		animation = new ScaleAnimation((float)1.0, (float)1.0 ,(float)0, (float)1.0);
		   /*switch(mode){
		    case 1:
		     animation = new TranslateAnimation(metrics_.widthPixels/2, 0, 0, 0);
		    break;
		    
		    case 2:
		     animation = new TranslateAnimation(0, 0, metrics_.heightPixels, 0);
		    break;
		    
		    case 3:
		     animation = new ScaleAnimation((float)1.0, (float)1.0 ,(float)0, (float)1.0);
		    break;
		    
		    case 4:
		     //non animation
		     animation = new Animation() {};
		    break;
		   }*/
		   
		   animation.setDuration(750);
		   convertView.startAnimation(animation);
		   animation = null;
	}

	public static void main(String[] args) {
		
	}
	
}
