package com.isport_starsoccer;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.isport_starsoccer.data.DataSetting;
import com.isport_starsoccer.data.DataURL;
import com.isport_starsoccer.exception.PrintLog;
import com.isport_starsoccer.getdata.GetDataListNews;
import com.isport_starsoccer.getdata.GetDataListScore;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.text.method.DateTimeKeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ExpandableListView;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class StarSoccer_Main extends StarSoccer_BaseClass implements View.OnClickListener {
	
	private RelativeLayout layout = null;
	private ExpandableListView page_lsnews_livescore = null;
	private Gallery page_lsnews_list_news = null;
	private ImageView page_lsnews_image_livescore = null;
	private ImageView page_lsnews_img_news = null;
	private TextView page_lsnews_header_text = null;
	private ProgressDialog progress = null;
	private boolean resumeHasRun = false;
	private TextView txtErr = null;
	private Tracker tracker=null;
	private RelativeLayout layoutNews = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starsoccer_main);

        layout = new RelativeLayout(this);
		layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.page_livescore_news, layout);
        
        
        layoutCenter = (RelativeLayout)findViewById(R.id.main_layout_center);
        page_lsnews_livescore =(ExpandableListView)layout.findViewById(R.id.page_lsnews_list_livescore);
        page_lsnews_list_news = (Gallery)layout.findViewById(R.id.page_lsnews_list_news);
        page_lsnews_image_livescore = (ImageView)layout.findViewById(R.id.page_lsnews_image_livescore);
        page_lsnews_img_news = (ImageView)layout.findViewById(R.id.page_lsnews_img_news);
        page_lsnews_header_text = (TextView)layout.findViewById(R.id.page_lsnews_header_text);
        txtErr = (TextView)layout.findViewById(R.id.page_lsnews_message);
		layoutNews = (RelativeLayout)layout.findViewById(R.id.page_news_main_layout);
        page_lsnews_livescore.setDivider(null);
        page_lsnews_livescore.setDividerHeight(0);
        page_lsnews_image_livescore.setOnClickListener(this);
        page_lsnews_img_news.setOnClickListener(this);
        //page_lsnews_header_text.setText(DateFormatUtils.format(new Date(System.currentTimeMillis()), "dd-MM-yyyy"));
        page_lsnews_header_text.setText(new DateFormat().format("dd/MM/yyyy", new Date(System.currentTimeMillis())));
        //page_lsnews_livescore.setLayoutParams(new LinearLayout.LayoutParams((int)imgUtil.screen_width(), (int)imgUtil.screen_height()/2));

		layoutNews.getLayoutParams().height = imgUtil.scaleSize() > 1.2? (int)imgUtil.screen_height()/3:(int)imgUtil.screen_height()/4;

        layoutCenter.addView(layout);
    
        
    }


    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       // getMenuInflater().inflate(R.menu.activity_starsoccer_main, menu);
        return true;
    }
    
    @Override
    protected void onStop() {
    	super.onStop();
    	resumeHasRun=false;
    }
    
    @Override
    protected void onResume() {
    	try {
				super.onResume();
				if (!resumeHasRun) {
					
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
						else 
						{
					        resumeHasRun = true;
							progress = ProgressDialog.show(this, null, "Loading...", true, true);
							
							GetDataListScore ls = new GetDataListScore(this,page_lsnews_livescore,"",null,null,"",txtErr);
							ls.DataBind();
							
							
							GetDataListNews news = new GetDataListNews(this,vDataNews,imgUtil,page_lsnews_list_news,"",progress);
							news.DataBindSportNews("00001");
						}
					}
					
				}
				
				GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
				//Tracker tracker = analytics.newTracker(getResources().getString(R.string.GoogleAnalyticsID)); // Send hits to tracker id UA-XXXX-Y
				tracker = analytics.newTracker(getResources().getString(R.string.GoogleAnalyticsID));
				
				// All subsequent hits will be send with screen name = "main screen"
				tracker.setScreenName("main screen");
				tracker.send(new HitBuilders.ScreenViewBuilder().build());
				
    	} catch (Exception e) {
			PrintLog.printException(this, "Note", e);
		}
    }

	@Override
	public void onClick(View v) {
		if( v ==  page_lsnews_image_livescore )
		{
			page_lsnews_image_livescore.setImageResource(R.drawable.btn_menu_down);
			Intent intent = new Intent(this, StarSoccer_Menu.class);
			startActivity(intent);
	    	finish();

		}
		else if( v== page_lsnews_img_news)
		{
			tracker.send(new HitBuilders.EventBuilder()
		       .setCategory("Main")
		       .setAction("click")
		       .setLabel("Main News")
		       .build());
			
			page_lsnews_img_news.setImageResource(R.drawable.btn_news_down);
			Intent intent = new Intent(this, StarSoccer_News.class);
			startActivity(intent);
	    	finish();
		}
		
	}

    
}
