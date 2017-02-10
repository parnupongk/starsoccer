package com.isport_starsoccer;
import java.io.InputStream;
import java.util.ArrayList;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.isport_starsoccer.connnection.XMLParserMatchAnalyse;
import com.isport_starsoccer.data.DataElementGroupMatchAnalyse;
import com.isport_starsoccer.data.DataSetting;
import com.isport_starsoccer.data.DataURL;
import com.isport_starsoccer.exception.PrintLog;
import com.isport_starsoccer.list.ListAdapterMatchAnalyse;
import com.isport_starsoccer.service.AsycTaskLoadData;
import com.isport_starsoccer.service.ReceiveDataListener;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.xml.sax.SAXException;


public class StarSoccer_MatchAnalyse extends StarSoccer_BaseClass implements ReceiveDataListener,OnClickListener,OnChildClickListener {
	
	private Handler handler = null;
	private RelativeLayout layout = null;
	private XMLParserMatchAnalyse xmlAnalyse=null;
	private ExpandableListView analyse_list_data = null;
	private TextView analyse_header_text = null;
	private TextView analyse_message = null;
	private ImageView analyse_header_btn = null;
	private ImageView analyse_img_menu = null;
	private String matchId = "";
	private ArrayList<DataElementGroupMatchAnalyse> dataAnalyse = null;
	private ProgressDialog progress = null;
	private boolean resumeHasRun = false;
	
	private Tracker tracker=null;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starsoccer_main);
        
        try
    	{
        
	        layout = new RelativeLayout(this);
			layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
			
			LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        inflater.inflate(R.layout.page_analyse, layout);
	        
	        if(getIntent().getExtras() != null)
	        {
	        	Bundle b = getIntent().getExtras();
	        	matchId = (String) b.get("matchId");
	        }
	        
	        layoutCenter = (RelativeLayout)findViewById(R.id.main_layout_center);
	        analyse_list_data =(ExpandableListView)layout.findViewById(R.id.analyse_list_data);
	        analyse_header_text = (TextView)layout.findViewById(R.id.analyse_header_text);
	        analyse_header_btn = (ImageView)layout.findViewById(R.id.analyse_header_btn);
	        analyse_img_menu = (ImageView)layout.findViewById(R.id.analyse_image_menu);
	        analyse_message = (TextView)layout.findViewById(R.id.analyse_message);
	        
	        analyse_list_data.setDivider(null);
	        analyse_list_data.setDividerHeight(0);
	        analyse_list_data.setChildDivider(null);
	        analyse_list_data.setOnChildClickListener(this);
	        analyse_img_menu.setOnClickListener(this);
	        analyse_header_btn.setOnClickListener(this);
	        
	        layoutCenter.addView(layout);
    	}
        catch (Exception e) {
			// TODO Auto-generated catch block
			PrintLog.printException(this, "StarSoccer", e);
        }
        
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
				        DataBinding();
					}
	
			    }
				
				GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
				//Tracker tracker = analytics.newTracker(getResources().getString(R.string.GoogleAnalyticsID)); // Send hits to tracker id UA-XXXX-Y
				tracker = analytics.newTracker(getResources().getString(R.string.GoogleAnalyticsID));
				// All subsequent hits will be send with screen name = "main screen"
				tracker.setScreenName("analyse screen");
				tracker.send(new HitBuilders.ScreenViewBuilder().build());
			
    	} catch (Exception e) {
			// TODO Auto-generated catch block
			PrintLog.printException(this, "", e);
		}
    }

    private void DataBinding() throws Exception
    {
    	try
    	{
    		
			String url = DataURL.matchAnalyse;
			url += "&contentgroupid=";
			url += "&sportType=00001";
			url += "&lang="+DataSetting.Languge;
			url += "&imei="+DataSetting.IMEI;
			url += "&model="+DataSetting.MODEL;
			url += "&imsi="+DataSetting.IMSI;
			url += "&type="+DataSetting.TYPE;
			AsycTaskLoadData load = new AsycTaskLoadData(this, this,null,"Analyse");
	        load.execute(url);
	        
    	}
    	catch(Exception ex)
    	{
    		throw new Exception(ex.getMessage());
    	}
    }

	
	@Override
	public void onClick(View v) {
		if( v == analyse_header_btn)
		{
			Intent intent = new Intent(this, StarSoccer_Tded.class);
			//intent.putExtra("data",  mGroupData.get(groupPosition).GroupItemCollection.get(childPosition));
			//intent.putExtra("header", mGroupData.get(groupPosition).leagueData);
			this.startActivity(intent);
	    	finish();
		}
		else if(v == analyse_img_menu)
		{
			analyse_img_menu.setImageResource(R.drawable.btn_menu_down);
			Intent intent = new Intent(this, StarSoccer_Menu.class);
			startActivity(intent);
	    	finish();
		}
	}


	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {

		Intent intent = new Intent(this, StarSoccer_MatchAnalyseDetail.class);
		intent.putExtra("data",   dataAnalyse.get(groupPosition));
		intent.putExtra("index", childPosition);
		this.startActivity(intent);
		return false;
	}


	@Override
	public void onReceiveDataStream(String loadName, String url, String strOutput) {
		try
		{
			if(loadName == "Analyse")
			{

				dataAnalyse = xmlAnalyse.mGroupData;

				final String message = xmlAnalyse.message;
				analyse_header_text.setText(xmlAnalyse.textDate);
				if(message.trim().equals(""))
				{
					final ListAdapterMatchAnalyse adaterResult = new ListAdapterMatchAnalyse(this,analyse_list_data,dataAnalyse,imgUtil,"",matchId);

					handler = new Handler();
					handler.post(new Runnable() {
						@Override
						public void run() {
							analyse_message.setVisibility(View.INVISIBLE);
							analyse_list_data.setVisibility(View.VISIBLE);
							analyse_list_data.setAdapter(adaterResult);

							if( progress != null )progress.dismiss();

						}
					});
				}
				else
				{
					analyse_message.setVisibility(View.VISIBLE);
					analyse_list_data.setVisibility(View.INVISIBLE);
					analyse_message.setText(message);
					if( progress != null )progress.dismiss();
				}
			}

		}
		catch(Exception ex)
		{
			if( progress != null )progress.dismiss();
			PrintLog.printException(this, "Note", ex);
		}
	}

	@Override
	public void onReceiveGetDataXML(String loadName, String url, String strOutput) throws InterruptedException, SAXException {
		xmlAnalyse = new XMLParserMatchAnalyse();
		Xml.parse(strOutput, xmlAnalyse);
	}
}


