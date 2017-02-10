package com.isport_starsoccer;
import java.io.InputStream;
import java.util.Vector;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.isport_starsoccer.connnection.XMLParserSMSService;
import com.isport_starsoccer.data.DataElementSMSService;
import com.isport_starsoccer.data.DataSetting;
import com.isport_starsoccer.data.DataURL;
import com.isport_starsoccer.exception.PrintLog;
import com.isport_starsoccer.list.ListAdapterTded;
import com.isport_starsoccer.service.AsycTaskLoadData;
import com.isport_starsoccer.service.ReceiveDataListener;

import android.app.ProgressDialog;
import android.content.Context;
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


public class StarSoccer_Tded extends StarSoccer_BaseClass implements ReceiveDataListener,OnClickListener,OnChildClickListener {
	
	private Handler handler = null;
	private RelativeLayout layout = null;
	private XMLParserSMSService xmlSMS=null;
	private ExpandableListView tded_list_data = null;
	private TextView tded_header_text = null;
	//private ImageView tded_header_btn = null;
	private ImageView tded_img_menu = null;
	private TextView tded_message = null;
	private Vector<DataElementSMSService> vdataSMS = null;
	private ProgressDialog progress = null;
	private boolean resumeHasRun = false;
	private TextView tded_header_wording1= null;
	private TextView tded_header_wording2= null;
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
	        inflater.inflate(R.layout.page_tded, layout);
	        
	        
	        layoutCenter = (RelativeLayout)findViewById(R.id.main_layout_center);
	        tded_list_data =(ExpandableListView)layout.findViewById(R.id.tded_list_data);
	        tded_header_text = (TextView)layout.findViewById(R.id.tded_header_text);
	        //tded_header_btn = (ImageView)layout.findViewById(R.id.tded_header_btn);
	        tded_img_menu = (ImageView)layout.findViewById(R.id.tded_image_menu);
	        tded_message = (TextView)layout.findViewById(R.id.tded_message);
	        tded_header_wording1 = (TextView)layout.findViewById(R.id.tded_header_wording1);
	        tded_header_wording2 = (TextView)layout.findViewById(R.id.tded_header_wording2);
	        
	        tded_list_data.setDivider(null);
	        tded_list_data.setDividerHeight(0);
	        tded_list_data.setChildDivider(null);
	        tded_img_menu.setOnClickListener(this);
	        //tded_header_btn.setOnClickListener(this);
	        
	        layoutCenter.addView(layout);
    	}
        catch (Exception e) {
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
				if (!resumeHasRun || vdataSMS ==null) {
			        resumeHasRun = true;
			        progress = ProgressDialog.show(this, null, "Loading...", true, true);
			        DataBinding();
			        return;
			    }
				GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
				//Tracker tracker = analytics.newTracker(getResources().getString(R.string.GoogleAnalyticsID)); // Send hits to tracker id UA-XXXX-Y
				tracker = analytics.newTracker(getResources().getString(R.string.GoogleAnalyticsID));
				// All subsequent hits will be send with screen name = "main screen"
				tracker.setScreenName("tded screen");
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
    		
			String url = DataURL.smsservice;
			url += "&sportType=00001";
			url += "&lang="+ DataSetting.Languge;
			url += "&imei="+DataSetting.IMEI;
			url += "&model="+DataSetting.MODEL;
			url += "&imsi="+DataSetting.IMSI;
			url += "&type="+DataSetting.TYPE;
			AsycTaskLoadData load = new AsycTaskLoadData(this, this,null,"SMSService");
	        load.execute(url);
	        
    	}
    	catch(Exception ex)
    	{
    		throw new Exception(ex.getMessage());
    	}
    }
	
	@Override
	public void onClick(View v) {
		/*if( v == tded_header_btn)
		{
			Intent intent = new Intent(this, StarSoccer_LeagueTable.class);
			//intent.putExtra("data",  mGroupData.get(groupPosition).GroupItemCollection.get(childPosition));
			//intent.putExtra("header", mGroupData.get(groupPosition).leagueData);
			this.startActivity(intent);
		}
		else */
		if(v == tded_img_menu)
		{
			tded_img_menu.setImageResource(R.drawable.btn_menu_down);
			Intent intent = new Intent(this, StarSoccer_Menu.class);
			startActivity(intent);
	    	finish();
		}
	}


	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {

		//Intent intent = new Intent(this, StarSoccer_ScoreDetail.class);
		//intent.putExtra("data",  mGroupData.get(groupPosition).GroupItemCollection.get(childPosition));
		//intent.putExtra("header", mGroupData.get(groupPosition).leagueData);
		//this.startActivity(intent);
		return false;
	}


	@Override
	public void onReceiveDataStream(String loadName, String url, String strOutput) {
		try
		{
			if(loadName == "SMSService")
			{
				vdataSMS = xmlSMS.vData;

				final String message = xmlSMS.message;
				final ListAdapterTded adaterTded = new ListAdapterTded(this,tded_list_data,vdataSMS,imgUtil,"");

				handler = new Handler();
				handler.post(new Runnable() {
					@Override
					public void run() {

						tded_list_data.setAdapter(adaterTded);
						tded_header_text.setText(xmlSMS.textDate);
						tded_header_wording1.setText(xmlSMS.wording1);
						tded_header_wording2.setText(xmlSMS.wording2);
						if( progress != null )progress.dismiss();

					}
				});

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
		xmlSMS = new XMLParserSMSService();
		Xml.parse(strOutput, xmlSMS);
	}
}


