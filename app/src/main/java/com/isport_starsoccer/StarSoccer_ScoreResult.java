package com.isport_starsoccer;
import java.io.InputStream;
import java.sql.Date;
import java.util.ArrayList;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.isport_starsoccer.connnection.XMLParserScoreResult;
import com.isport_starsoccer.data.DataElementGroupScoreResult;
import com.isport_starsoccer.data.DataSetting;
import com.isport_starsoccer.data.DataURL;
import com.isport_starsoccer.exception.PrintLog;
import com.isport_starsoccer.list.ListAdapterResult;
import com.isport_starsoccer.service.AsycTaskLoadData;
import com.isport_starsoccer.service.ReceiveDataListener;

import android.app.DatePickerDialog.OnDateSetListener;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.xml.sax.SAXException;


public class StarSoccer_ScoreResult extends StarSoccer_BaseClass implements ReceiveDataListener,OnClickListener,OnChildClickListener,OnDateSetListener {
	
	private Handler handler = null;
	private RelativeLayout layout = null;
	private ExpandableListView result_list_data = null;
	private TextView result_header_text = null;
	private TextView result_message = null;
	private ImageView result_header_btn_program = null;
	private ImageView result_img_menu = null;
	private ImageView result_btn_date = null;
	private ProgressDialog progress = null;
	private boolean resumeHasRun = false;
	public String urlRadio = "";
	private ArrayList<DataElementGroupScoreResult> mGroupData = null;
	
	private Date date = null;
	private String day = "1";
	private String month = "1";
	private String year = "1";
	private XMLParserScoreResult xmlResult=null;
	private Tracker tracker=null;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starsoccer_main);
       
        layout = new RelativeLayout(this);
		layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.page_score, layout);
        
        
        layoutCenter = (RelativeLayout)findViewById(R.id.main_layout_center);
        result_list_data =(ExpandableListView)layout.findViewById(R.id.result_list_data);
        result_header_text = (TextView)layout.findViewById(R.id.result_header_text);
        result_header_btn_program = (ImageView)layout.findViewById(R.id.result_header_btn_program);
        result_img_menu = (ImageView)layout.findViewById(R.id.result_img_menu);
        result_btn_date = (ImageView)layout.findViewById(R.id.result_btn_date);
        result_message = (TextView)layout.findViewById(R.id.result_message);
        
        result_btn_date.setOnClickListener(this);
        result_img_menu.setOnClickListener(this);
        result_header_btn_program.setOnClickListener(this);
        result_list_data.setOnChildClickListener(this);
        result_list_data.setDivider(null);
        result_list_data.setDividerHeight(0);
        result_list_data.setChildDivider(null);
        
        
        date = new Date(System.currentTimeMillis());
        getDate();
        
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
				        return;
					}
			    }
				
				GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
				//Tracker tracker = analytics.newTracker(getResources().getString(R.string.GoogleAnalyticsID)); // Send hits to tracker id UA-XXXX-Y
				tracker = analytics.newTracker(getResources().getString(R.string.GoogleAnalyticsID));
				// All subsequent hits will be send with screen name = "main screen"
				tracker.setScreenName("result screen");
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
    		
    		vResultData = null;
			String url = DataURL.ScoreResult;
			url += "&date="+ year + month + day;
			url += "&sportType=00001";
			url += "&contentgroupid=";
			url += "&lang="+DataSetting.Languge;
			url += "&imei="+DataSetting.IMEI;
			url += "&model="+DataSetting.MODEL;
			url += "&imsi="+DataSetting.IMSI;
			url += "&type="+DataSetting.TYPE;
			AsycTaskLoadData load = new AsycTaskLoadData(this, this,null,"Result");
	        load.execute(url);
	        
    	}
    	catch(Exception ex)
    	{
    		throw new Exception(ex.getMessage());
    	}
    }

	@Override
	public void onClick(View v) {
		if( v == result_header_btn_program)
		{
			Intent intent = new Intent(this, StarSoccer_Program.class);
			this.startActivity(intent);
			this.finish();
		}
		else if(v == result_img_menu)
		{
			result_img_menu.setImageResource(R.drawable.btn_menu_down);
			Intent intent = new Intent(this, StarSoccer_Menu.class);
			startActivity(intent);
			this.finish();
		}
		else if(v == result_btn_date)
		{
			DatePickerDialog dp = new DatePickerDialog(this, this, Integer.parseInt(year), Integer.parseInt(month)-1, Integer.parseInt(day));
			dp.setTitle("เลือกวันที่ต้องการดูสรุปผลบอล");
			
			dp.show();
		}
	}


	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {

		if( mGroupData.get(groupPosition).GroupItemCollection.get(childPosition).isDetail )
		{
			Intent intent = new Intent(this, StarSoccer_ScoreDetail.class);
			intent.putExtra("data",  mGroupData.get(groupPosition).GroupItemCollection.get(childPosition));
			intent.putExtra("header", mGroupData.get(groupPosition).leagueData);
			this.startActivity(intent);
		}
		return false;
	}
	private void getDate()
	{
		String [] d = date.toString().split("-");
		year = d[0];
		if(d[1].length() == 1)
		{
			month = "0"+d[1];
		}
		else
		{
			month = d[1];
		}
		
		if(d[2].length() == 1)
		{
			day = "0"+d[2];
		}
		else
		{
			day = d[2];
		}
	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		try
		{
			date = new Date(year-1900, monthOfYear, dayOfMonth);
	
			getDate();
			
			progress = ProgressDialog.show(this, null, "Loading...", true, true);
			DataBinding();
		}
		catch(Exception ex)
		{
			if( progress != null )progress.dismiss();
			PrintLog.printException(this, "Note", ex);
		}
	}

	@Override
	public void onReceiveDataStream(String loadName, String url, String strOutput) {
		try
		{
			if(loadName == "Result")
			{
				mGroupData = xmlResult.mGroupData;

				final String message = xmlResult.message;
				result_header_text.setText(xmlResult.textDate);
				if(message.trim().equals(""))
				{
					final ListAdapterResult adaterResult = new ListAdapterResult(this,result_list_data,mGroupData,imgUtil,"");

					handler = new Handler();
					handler.post(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							result_message.setVisibility(View.INVISIBLE);
							result_list_data.setVisibility(View.VISIBLE);
							result_list_data.setAdapter(adaterResult);
							if( progress != null )progress.dismiss();


						}
					});
				}
				else
				{
					result_message.setVisibility(View.VISIBLE);
					result_list_data.setVisibility(View.INVISIBLE);
					result_message.setText(message);
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
		xmlResult = new XMLParserScoreResult();
		Xml.parse(strOutput, xmlResult);
	}
}


