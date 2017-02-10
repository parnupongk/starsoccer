package com.isport_starsoccer;


import java.util.Vector;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.isport_starsoccer.data.DataSetting;
import com.isport_starsoccer.list.ListAdapterSetting;
import com.isport_starsoccer.service.StartUp;


public class StarSoccer_Setting extends StarSoccer_BaseClass  implements OnClickListener,OnChildClickListener{
	
	private RelativeLayout layout = null;
	private RelativeLayout layoutCenter = null;
	private ExpandableListView list_menuleague = null;
	private ImageView setting_menu_img = null;
	private TextView textLanguage = null;
	private ImageView btLanguage = null;
	private TextView textNotify = null;
	private ImageView btNotify = null;
	private ImageView btnDone = null;
	private TextView textList = null;
	
	private String setLeague = null;
	private Vector<String> league = null;
	private ListAdapterSetting adapterMenuLeague = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);        
        setContentView(R.layout.activity_starsoccer_main);
        
        StartUp.getSetting(this);
        
        layoutCenter = (RelativeLayout)findViewById(R.id.main_layout_center);
        
        layout = new RelativeLayout(this);
		layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.page_setting, layout);

        league = new Vector<String>();
        AddvLeague();
        list_menuleague = (ExpandableListView)layout.findViewById(R.id.setting_list_data);
        setting_menu_img = (ImageView)layout.findViewById(R.id.setting_img_menu);
        
        textLanguage = (TextView) layout.findViewById(R.id.setting_language_text);
        btLanguage = (ImageView) layout.findViewById(R.id.setting_language_bt);
        textNotify = (TextView) layout.findViewById(R.id.setting_notify_text);
        btNotify = (ImageView) layout.findViewById(R.id.setting_notify_bt);
        btnDone = (ImageView) layout.findViewById(R.id.setting_buttom_done);
        textList = (TextView) layout.findViewById(R.id.setting_text_list);
        
        list_menuleague.setDivider(null);
        list_menuleague.setChildDivider(null);
        
        btLanguage.setImageResource(R.drawable.onoff_th);
        btNotify.setImageResource(R.drawable.onoff_on);
        
        textLanguage.setText("Result Score");
        textNotify.setText("Notification");
        textList.setText("Country");
        
        textLanguage.setPadding((int)(20*imgUtil.scaleSize()), 0, 0, 0);
        textNotify.setPadding((int)(20*imgUtil.scaleSize()), 0, 0, 0);
        textList.setPadding((int)(20*imgUtil.scaleSize()), 0, 0, 0);
        
        btnDone.setOnClickListener(this);
        setting_menu_img.setOnClickListener(this);
        list_menuleague.setOnChildClickListener(this);
        
        if(DataSetting.Languge.equals("th"))
		{
			btLanguage.setImageResource(R.drawable.onoff_th);
		}
		else
		{
			btLanguage.setImageResource(R.drawable.onoff_eng);
		}
        btLanguage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(DataSetting.Languge.equals("th"))
				{
					btLanguage.setImageResource(R.drawable.onoff_eng);
					DataSetting.Languge = "en";
				}
				else
				{
					btLanguage.setImageResource(R.drawable.onoff_th);
					DataSetting.Languge = "th";
				}
			}
		});
        
        if(DataSetting.checkNotify)
		{
			btNotify.setImageResource(R.drawable.onoff_on);
		}
		else
		{
			btNotify.setImageResource(R.drawable.onoff_off);
		}
        btNotify.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(DataSetting.checkNotify)
				{
					DataSetting.checkNotify = false;
					btNotify.setImageResource(R.drawable.onoff_off);
				}
				else
				{
					DataSetting.checkNotify = true;
					btNotify.setImageResource(R.drawable.onoff_on);
				}
			}
		});
        layoutCenter.addView(layout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
    
    @Override
    protected void onStop() {
    	super.onStop();
    }
    @Override
    protected void onResume() {
    	try {
    		
    		super.onResume();
    		if( ISACTIVE == null || ISACTIVE.equals("") )
			{
				Intent intent = new Intent(this, StarSoccer_Logo.class);
    			startActivity(intent);
    			finish();
			}
			else if(ISACTIVE.equals("N"))
			{
				Intent intent = new Intent(this, StarSoccer_Active.class);
    			startActivity(intent);
    			finish();

			}
			else
			{
	    		if(vGroupListMenu == null)
	    		{
	    			Intent intent = new Intent(this, StarSoccer_Logo.class);
	    			startActivity(intent);
	    			this.finish();
	    		}
	    		else
	    		{
	    			adapterMenuLeague = new ListAdapterSetting(this, list_menuleague, vGroupListMenu, imgUtil, "",league);
	    			list_menuleague.setAdapter(adapterMenuLeague);
	    			list_menuleague.expandGroup(0);
	    		}
			}
    		

    	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }



	@Override
	public boolean onChildClick(ExpandableListView arg0, View v, int groupPosition,
			int childPosition, long arg4) {
		
		/*String contentGroupId = vGroupListMenu.get(groupPosition).GroupLeagueCollection.get(childPosition).contestGroupId;
		CheckBox chk = (CheckBox)v.findViewById(R.id.sub_setting_on);
		if(chk.isChecked())
		{
			league.remove(contentGroupId);
			chk.setChecked(false);
		}else
		{
			league.add(contentGroupId);
			chk.setChecked(true);
		}
		*/
		return false;
	}

	private void AddvLeague()
	{
		if(DataSetting.setLeague != null && DataSetting.setLeague.length() > 0)
		{
			String[] selectLeague = DataSetting.setLeague.split(",");
			for(int i = 0; i < selectLeague.length; i++)
			{
				league.add(selectLeague[i]);
			}
		}
	}
	private void setLeague()
	{
		if(league != null)
		{
			for(int i = 0; i < league.size(); i++)
			{
				if(i == 0)
				{
					setLeague = league.elementAt(i);
				}
				else
				{
					setLeague += ","+league.elementAt(i);
				}
			}
		}
	}
	
	@Override
	public void onClick(View v) {
		if(v == setting_menu_img)
		{
			setting_menu_img.setImageResource(R.drawable.btn_menu_down);
			Intent intent = new Intent(this, StarSoccer_Menu.class);
			startActivity(intent);
	    	finish();
		}
		else if(v == btnDone)
		{
			league = adapterMenuLeague.vLeague;
			setLeague();
			StartUp.setSetting(this, DataSetting.Languge, setLeague, DataSetting.checkNotify, false);
			Toast.makeText(this, "setting success.", Toast.LENGTH_SHORT).show();
			if(DataSetting.checkNotify)
			{
				startService(new Intent(this, StarSoccer_Notify.class));
			}
			//finish();
		}
		
	}

    
}
