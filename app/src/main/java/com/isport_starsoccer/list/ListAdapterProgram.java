package com.isport_starsoccer.list;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;
import com.isport_starsoccer.R;
import com.isport_starsoccer.data.DataElementGroupProgram;
import com.isport_starsoccer.data.DataElementLeague;
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

public class ListAdapterProgram extends BaseExpandableListAdapter
{
	private ArrayList<DataElementGroupProgram> mGroupData=null;
	private Context context = null;
	private ImageUtil imgUtil = null;
	private String message = "";
	private ExpandableListView listView = null;
	private int[] groupStatus;
	
	public ListAdapterProgram(Context context ,ExpandableListView listView
			, ArrayList<DataElementGroupProgram> mGroupData
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
		return mGroupData.get(arg0).GroupItemCollection.get(arg1);
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
			convertView = inflater.inflate(R.layout.sub_program_group_match,null);
		}
		
		RelativeLayout list_layout = (RelativeLayout)convertView.findViewById(R.id.sub_program_layout);
		//list_layout.setPadding((int)(5*imgUtil.scaleSize()), 0, (int)(5*imgUtil.scaleSize()), 0);
		
		
		TextView sub_program_team1 = (TextView)convertView.findViewById(R.id.sub_program_team1);
		TextView sub_program_time = (TextView)convertView.findViewById(R.id.sub_program_time);
		TextView sub_program_team2 = (TextView)convertView.findViewById(R.id.sub_program_team2);
		TextView sub_program_channel = (TextView)convertView.findViewById(R.id.sub_program_channel);
		ImageView sub_program_img_detail = (ImageView)convertView.findViewById(R.id.sub_program_img_detail);
		
		
		sub_program_team1.setText(mGroupData.get(groupPosition).GroupItemCollection.get(childPosition).name1);
		sub_program_team2.setText(mGroupData.get(groupPosition).GroupItemCollection.get(childPosition).name2);
		sub_program_time.setText(mGroupData.get(groupPosition).GroupItemCollection.get(childPosition).matchTime);
		sub_program_channel.setText(mGroupData.get(groupPosition).GroupItemCollection.get(childPosition).liveChannel);

		
		sub_program_team1.setWidth((int)(150*imgUtil.scaleSize()));
		sub_program_team2.setWidth((int)(150*imgUtil.scaleSize()));
		sub_program_time.setWidth((int)(80*imgUtil.scaleSize()));
		sub_program_channel.setWidth((int)(80*imgUtil.scaleSize()));
		
		sub_program_channel.setMaxLines(1);
		
		//sub_program_team1.setGravity(Gravity.CENTER_VERTICAL);
		//sub_program_team1.setGravity(Gravity.);
		//sub_program_team2.setGravity(Gravity.CENTER);
		//sub_program_time.setGravity(Gravity.CENTER);
		//sub_program_channel.setGravity(Gravity.CENTER);
		
		if(mGroupData.get(groupPosition).GroupItemCollection.get(childPosition).isDetail)
		{
			sub_program_img_detail.setImageResource(R.drawable.btnnext);
		}
		else
		{
			sub_program_img_detail.setImageDrawable(null);
		}
		SetAnimationView(convertView);
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return mGroupData.get(groupPosition).GroupItemCollection.size();
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
			convertView = inflater.inflate(R.layout.sub_score_group_league,null);
		}
			
			ImageView imgHeader = (ImageView)convertView.findViewById(R.id.sub_score_group_league_img);
			TextView sub_score_group_league_txt = (TextView)convertView.findViewById(R.id.sub_score_group_league_txt);
			ImageView imgGroup = (ImageView)convertView.findViewById(R.id.sub_score_group_league_imgExpand);
			
			DataElementLeague data = mGroupData.get(groupPosition).leagueData;
			sub_score_group_league_txt.setText(data.contestGroupName);
			//imgHeader.setPadding((int)10, 5, (int)(5*imgUtil.scaleSize()), 0);

		final ProgressBar progress = new ProgressBar(context);
		progress.setVisibility(View.VISIBLE);
		Picasso.with(context).load(data.contestURLImages)
				.into(imgHeader, new com.squareup.picasso.Callback() {
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
			if(!data.checkLoad && data.contestURLImages.length() > 0 )
			{
				if(hImage!= null && hImage.get(data.contestURLImages) == null)
				{
					data.checkLoad = true;
					AsycTaskLoadImage loadImage = new AsycTaskLoadImage(null, null, hImage, null, this);
					loadImage.execute(data.contestURLImages);
				}
			}

			if(hImage!= null && hImage.get(data.contestURLImages) != null)
			{
				imgHeader.setImageBitmap(hImage.get(data.contestURLImages));
			}
			else
			{
				imgHeader.setImageBitmap(null);
			}*/

			if (groupStatus[groupPosition] == 0) {
				imgGroup.setImageResource(R.drawable.group_down);
			} else {
				imgGroup.setImageResource(R.drawable.group_up);
			}
			
			
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
	
}
