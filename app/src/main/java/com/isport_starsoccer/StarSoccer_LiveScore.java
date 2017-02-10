package com.isport_starsoccer;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.isport_starsoccer.data.DataSetting;
import com.isport_starsoccer.exception.PrintLog;
import com.isport_starsoccer.getdata.GetDataListScore;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class StarSoccer_LiveScore extends StarSoccer_BaseClass implements Runnable,OnClickListener {
	
	private RelativeLayout layout = null;
	private ExpandableListView page_ls_livescore = null;
	private ImageView page_ls_image_livescore = null;
	private ImageView page_ls_btn_radio = null;
	private ImageView page_ls_btn_result = null;
	private TextView page_ls_txt_date = null;
	private ProgressDialog progress = null;
	private boolean resumeHasRun = false;
	public String urlRadio = "";
	private Handler thread = null;
	private TextView txtErr = null;
	
	private Tracker tracker=null;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starsoccer_main);
     
        
        layout = new RelativeLayout(this);
		layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.page_livescore, layout);
        
        
        layoutCenter = (RelativeLayout)findViewById(R.id.main_layout_center);
        page_ls_livescore =(ExpandableListView)layout.findViewById(R.id.page_ls_list_livescore);
        page_ls_image_livescore = (ImageView)layout.findViewById(R.id.page_ls_image_livescore);
        page_ls_txt_date = (TextView)layout.findViewById(R.id.page_ls_txt_date);
        //page_ls_btn_radio = (ImageView)layout.findViewById(R.id.page_ls_btn_radio);
        page_ls_btn_result = (ImageView)layout.findViewById(R.id.page_ls_btn_result);
        txtErr = (TextView)layout.findViewById(R.id.page_ls_message);
        page_ls_image_livescore.setOnClickListener(this);
        //page_ls_btn_radio.setOnClickListener(this);
        page_ls_btn_result.setOnClickListener(this);
        page_ls_livescore.setDivider(null);
        page_ls_livescore.setDividerHeight(0);
  
        thread = new Handler();
        
        layoutCenter.addView(layout);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       // getMenuInflater().inflate(R.menu.activity_starsoccer_main, menu);
        return true;
    }
    
    @Override
    protected void onPause() 
    {
    	super.onPause();

    };
    @Override
    protected void onStop() {
    	super.onStop();
    	resumeHasRun = false;
    }

    @Override
    protected void onResume() {
    	try {
				super.onResume();
				
				//StarSoccer_Application.tracker().send(new HitBuilders.ScreenViewBuilder().build());
				
				
				if (!resumeHasRun ) {
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
					else if(SERVERVERSION == null || SERVERVERSION.equals(""))
					{
						Intent intent = new Intent(this, StarSoccer_Logo.class);
		    			startActivity(intent);
		    			finish();
					}
					else if( !SERVERVERSION.equals(DataSetting.VERSION) )
					{
						AlertDialog.Builder builder = new AlertDialog.Builder(this);
			        	builder.setMessage("Please Update Version")
			        	       .setCancelable(false)
			        	       .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			        	           public void onClick(DialogInterface dialog, int id) {
			        	                finish();
			        	           }
			        	       });
			        	AlertDialog alert = builder.create();
			        	alert.show();
					}
					else 
					{
				        resumeHasRun = true;
				        progress = ProgressDialog.show(this, null, "Loading...", true, true);
						GetDataListScore ls = new GetDataListScore(this,page_ls_livescore,"",progress,page_ls_txt_date,urlRadio,txtErr);
						ls.DataBind();
						
						
						thread.postDelayed(this, 80000L);
						
				        return;
					}
			    }
				
				GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
				//Tracker tracker = analytics.newTracker(getResources().getString(R.string.GoogleAnalyticsID)); // Send hits to tracker id UA-XXXX-Y
				tracker = analytics.newTracker(getResources().getString(R.string.GoogleAnalyticsID));
				// All subsequent hits will be send with screen name = "main screen"
				tracker.setScreenName("livescore screen");
				tracker.send(new HitBuilders.ScreenViewBuilder().build());
			
    	} catch (Exception e) {
			PrintLog.printException(this, "", e);
		}
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if( v ==  page_ls_image_livescore )
		{
			page_ls_image_livescore.setImageResource(R.drawable.btn_menu_down);
			Intent intent = new Intent(this, StarSoccer_Menu.class);
			startActivity(intent);
	    	finish();
		}
		/*else if( v == page_ls_btn_radio )
		{
			Intent intent = new Intent(this, StarSoccer_Radio.class);
			intent.putExtra("urlradio", urlRadio);
			intent.putExtra("header", page_ls_txt_date.getText());
			startActivity(intent);
		}*/
		else if(v == page_ls_btn_result)
		{
			Intent intent = new Intent(this, StarSoccer_ScoreResult.class);
			startActivity(intent);
	    	finish();
		}
		
		
	}

	@Override
	public void run() {
		
		try {
			progress = ProgressDialog.show(this, null, "Loading...", true, true);
			GetDataListScore ls = new GetDataListScore(this,page_ls_livescore,"",progress,page_ls_txt_date,urlRadio,txtErr);
			ls.DataBind();
			
			thread.postDelayed(this, 80000L);
			
		} catch (Exception e) {
			PrintLog.printException(this, "", e);
		}
	}

	
}


