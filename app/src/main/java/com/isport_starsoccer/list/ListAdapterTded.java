package com.isport_starsoccer.list;


import java.util.HashMap;
import java.util.Vector;

import com.isport_starsoccer.R;
import com.isport_starsoccer.data.DataElementSMSService;
import com.isport_starsoccer.service.ImageUtil;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

public class ListAdapterTded extends BaseExpandableListAdapter implements OnClickListener {
	private Vector<DataElementSMSService> mGroupData = null;
	private Context context = null;
	private ImageUtil imgUtil = null;
	private String message = "";
	private ExpandableListView listView = null;
	private int[] groupStatus;

	public ListAdapterTded(Context context, ExpandableListView listView
			, Vector<DataElementSMSService> mGroupData
			, ImageUtil imgUtil, String message) {
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
		return mGroupData.get(arg0);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return 0;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
							 boolean isLastChild, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.sub_tded_group_detail, null);
		}


		TextView txtDesc = (TextView) convertView.findViewById(R.id.sub_tded_detail_desc);
		TextView txtPromotion = (TextView) convertView.findViewById(R.id.sub_tded_detail_promotion);
		TextView txtPrice = (TextView) convertView.findViewById(R.id.sub_tded_detail_price);
		ImageView imgOk = (ImageView) convertView.findViewById(R.id.sub_tded_detail_ok);
		TextView txtCancel = (TextView) convertView.findViewById(R.id.sub_tded_detail_cancel);


		txtDesc.setText(mGroupData.get(groupPosition).pssv_desc);
		txtPromotion.setText(mGroupData.get(groupPosition).pssv_promotion);
		txtPrice.setText(mGroupData.get(groupPosition).pssv_remark1);
		txtCancel.setText(mGroupData.get(groupPosition).pssv_remark2);

		final int position = groupPosition;
		imgOk.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				Intent intent = new Intent(Intent.ACTION_CALL);
				intent.setData(Uri.parse("tel:" + mGroupData.get(position).pssv_shortcode));
				if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
					// TODO: Consider calling
					//    ActivityCompat#requestPermissions
					// here to request the missing permissions, and then overriding
					//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
					//                                          int[] grantResults)
					// to handle the case where the user grants the permission. See the documentation
					// for ActivityCompat#requestPermissions for more details.
					return;
				}
				context.startActivity(intent);

				Toast.makeText(context, "กรุณารอรับ SMS ตอบกลับค่ะ", Toast.LENGTH_SHORT).show();
	             }
	           });
		
		/*imgCancel.setVisibility(View.INVISIBLE);
		imgCancel.setOnClickListener(new OnClickListener() {
	          public void onClick(View v) {
	        	
	        	  	Intent intent = new Intent(Intent.ACTION_CALL);
		        	intent.setData(Uri.parse("tel:"+mGroupData.get(position).pssv_cancelcode));
		            context.startActivity(intent);
		            
		            Toast.makeText(context, "กรุณารอรับ SMS ตอบกลับค่ะ", Toast.LENGTH_SHORT).show();
	             }
	           });*/

		SetAnimationView(convertView);
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return 1;
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
			convertView = inflater.inflate(R.layout.sub_tded_group_title,null);
		}
			ImageView img = (ImageView)convertView.findViewById(R.id.sub_tded_img_country);
			ImageView imgGroup = (ImageView)convertView.findViewById(R.id.sub_tded_img_group);
			TextView txt = (TextView)convertView.findViewById(R.id.sub_tded_txt_name);
			
			txt.setText(mGroupData.get(groupPosition).pssv_name );
			img.setImageResource(R.drawable.hd_sub_ball);
			
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
		animation = new ScaleAnimation((float)1.0, (float)1.0 ,(float)0, (float)1.0);
	
		   animation.setDuration(750);
		   convertView.startAnimation(animation);
		   animation = null;
	}

	@Override
	public void onClick(View v) {
		
	}
	
}
