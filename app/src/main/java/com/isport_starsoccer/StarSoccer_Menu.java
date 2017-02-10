package com.isport_starsoccer;

import java.util.HashMap;
import java.util.Vector;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.isport_starsoccer.data.DataSetting;
import com.isport_starsoccer.data.DataURL;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;


public class StarSoccer_Menu extends StarSoccer_BaseClass  implements OnClickListener{
	
	private RelativeLayout layout = null;
	private RelativeLayout layoutCenter = null;
	
	// Button
	private ImageView btnNews = null;
	private ImageView btnLiveScore = null;
	private ImageView btnScoreResult = null;
	private ImageView btnProgram = null;
	private ImageView btnAnalyse = null;
	private ImageView btnTable = null;
	private ImageView btnClip = null;
	private ImageView btnTded = null;
	private ImageView btnHot = null;
	private ImageView btnSetting = null;
	
	private Tracker tracker=null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE);


        super.onCreate(savedInstanceState);        
        setContentView(R.layout.activity_starsoccer_main);
        
        layoutCenter = (RelativeLayout)findViewById(R.id.main_layout_center);
        
        layout = new RelativeLayout(this);
		layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.sub_menu, layout);
        
        btnNews = (ImageView)layout.findViewById(R.id.btnNews);
        btnNews.setOnClickListener(this);
        
        btnLiveScore = (ImageView) layout.findViewById(R.id.btnLiveSocre);
        btnLiveScore.setOnClickListener(this);
        
        btnScoreResult = (ImageView) layout.findViewById(R.id.btnResult);
        btnScoreResult.setOnClickListener(this);
        
        btnProgram = (ImageView) layout.findViewById(R.id.btnProgram);
        btnProgram.setOnClickListener(this);
        
        btnAnalyse = (ImageView) layout.findViewById(R.id.btnanalyse);
        btnAnalyse.setOnClickListener(this);
        
        btnTable = (ImageView)layout.findViewById(R.id.btnLeagueTable);
        btnTable.setOnClickListener(this);
        
        btnClip = (ImageView)layout.findViewById(R.id.sub_menu_img_clip);
        btnClip.setOnClickListener(this);
        
        btnTded = (ImageView)layout.findViewById(R.id.btnTded);
        btnTded.setOnClickListener(this);
        
        btnHot = (ImageView)layout.findViewById(R.id.btnHot);
        btnHot.setOnClickListener(this);
        
        btnSetting = (ImageView)layout.findViewById(R.id.btnSetting);
        btnSetting.setOnClickListener(this);

        layoutCenter.addView(layout);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
    
    
    @Override
    protected void onResume() {
    	try {
    		
    		super.onResume();

    		if(SERVERVERSION == null || SERVERVERSION.equals(""))
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
	        	        	   Intent i = new Intent(Intent.ACTION_VIEW);  
	   			        		i.setData(Uri.parse(DataURL.playstore));
	   			        		startActivity(i); 
	        	                finish();
	        	           }
	        	       });
	        	AlertDialog alert = builder.create();
	        	alert.show();
			}
    		
			GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
			//Tracker tracker = analytics.newTracker(getResources().getString(R.string.GoogleAnalyticsID)); // Send hits to tracker id UA-XXXX-Y
			tracker = analytics.newTracker(getResources().getString(R.string.GoogleAnalyticsID));
			// All subsequent hits will be send with screen name = "main screen"
			tracker.setScreenName("menu screen");

    	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	@Override
	public void onClick(View v) {
		if(v == btnNews)
		{
			//btnNews.setBackgroundResource(R.drawable.news_down);
			btnNews.setImageResource(R.drawable.news_down);
			Intent intent = new Intent(this, StarSoccer_News.class);
			startActivity(intent);
			this.finish();
		}
		else if(v == btnLiveScore)
		{
			btnLiveScore.setImageResource(R.drawable.livescore_th_down);
			Intent intent = new Intent(this, StarSoccer_LiveScore.class);
			startActivity(intent);
			this.finish();
		}
		else if(v == btnScoreResult)
		{
			btnScoreResult.setImageResource(R.drawable.result_down);
			Intent intent = new Intent(this, StarSoccer_ScoreResult.class);
			startActivity(intent);
			this.finish();
		}
		else if(v == btnProgram)
		{
			btnProgram.setImageResource(R.drawable.program_down);
			Intent intent = new Intent(this, StarSoccer_Program.class);
			startActivity(intent);
			this.finish();
		}
		else if(v == btnAnalyse)
		{
			btnAnalyse.setImageResource(R.drawable.analyse_down);
			Intent intent = new Intent(this, StarSoccer_MatchAnalyse.class);
			startActivity(intent);
			this.finish();
		}
		else if(v == btnTable)
		{
			btnTable.setImageResource(R.drawable.table_down);
			Intent intent = new Intent(this, StarSoccer_LeagueTable.class);
			startActivity(intent);
			this.finish();
		}
		else if(v == btnClip)
		{
			Intent intent = new Intent(this, StarSoccer_Clip.class);
			startActivity(intent);
			this.finish();
		}
		else if(v == btnTded)
		{
			btnTded.setImageResource(R.drawable.tded_down);
			Intent intent = new Intent(this, StarSoccer_Tded.class);
			startActivity(intent);
			this.finish();
		}
		else if(v == btnHot)
		{
			btnHot.setImageResource(R.drawable.hot_down);
			Intent intent = new Intent(this, StarSoccer_Hot.class);
			startActivity(intent);
			this.finish();

		}else if(v == btnSetting)
		{
			btnSetting.setImageResource(R.drawable.setting_down);
			Intent intent = new Intent(this, StarSoccer_Setting.class);
			//Intent intent = new Intent(this, StarSoccer_Active.class);
			startActivity(intent);
			this.finish();
		}
		
		//ImageView img = (ImageView)v;
		String str = v.getResources().getResourceEntryName(v.getId());
		tracker.send(new HitBuilders.EventBuilder()
	       .setCategory("Menu")
	       .setAction("click")
	       .setLabel(str)
	       .build());
		
	}


    
}
