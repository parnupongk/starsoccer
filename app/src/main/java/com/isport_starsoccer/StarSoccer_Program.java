package com.isport_starsoccer;
import java.io.InputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Vector;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.isport_starsoccer.connnection.XMLParserMatchProgram;
import com.isport_starsoccer.data.DataElementGroupProgram;
import com.isport_starsoccer.data.DataElementScore;
import com.isport_starsoccer.data.DataSetting;
import com.isport_starsoccer.data.DataURL;
import com.isport_starsoccer.exception.PrintLog;
import com.isport_starsoccer.list.ListAdapterProgram;
import com.isport_starsoccer.service.AsycTaskLoadData;
import com.isport_starsoccer.service.ReceiveDataListener;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
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
import android.widget.DatePicker;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.xml.sax.SAXException;


public class StarSoccer_Program extends StarSoccer_BaseClass implements ReceiveDataListener,OnClickListener,OnChildClickListener,OnDateSetListener {
	
	private Handler handler = null;
	private RelativeLayout layout = null;
	
	private ExpandableListView program_list_data = null;
	private TextView program_header_text = null;
	private ImageView program_header_btn = null;
	private ImageView program_header_btn_date = null;
	private ImageView program_img_menu = null;
	private TextView program_message = null;
	private ProgressDialog progress = null;
	private boolean resumeHasRun = false;
	public String urlRadio = "";
	private ArrayList<DataElementGroupProgram> mGroupData = null;
	private Date date = null;
	private String day = "1";
	private String month = "1";
	private String year = "1";
	private Tracker tracker=null;
	private XMLParserMatchProgram xmlProgram=null;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starsoccer_main);
       
        layout = new RelativeLayout(this);
		layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.page_program, layout);
        
        
        layoutCenter = (RelativeLayout)findViewById(R.id.main_layout_center);
        program_list_data =(ExpandableListView)layout.findViewById(R.id.program_list_data);
        program_header_text = (TextView)layout.findViewById(R.id.program_header_text);
        program_header_btn = (ImageView)layout.findViewById(R.id.program_header_btn);
        program_header_btn_date = (ImageView)layout.findViewById(R.id.program_header_btn_date);
        program_img_menu = (ImageView)layout.findViewById(R.id.program_img_menu);
        program_message = (TextView)layout.findViewById(R.id.program_message);
        
        program_img_menu.setOnClickListener(this);
        program_header_btn_date.setOnClickListener(this);
        program_header_btn.setOnClickListener(this);
        program_list_data.setOnChildClickListener(this);
        
        layoutCenter.addView(layout);
        
        program_list_data.setDivider(null);
        program_list_data.setDividerHeight(0);
        program_list_data.setChildDivider(null);
        
        date = new Date(System.currentTimeMillis());
        getDate();
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
    	// TODO Auto-generated method stub
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
		        	                finish();
		        	           }
		        	       });
		        	AlertDialog alert = builder.create();
		        	alert.show();
				}
				else if (!resumeHasRun) {
			        resumeHasRun = true;
			        
			        if(mGroupData != null)
					{
						ListAdapterProgram adaterResult = new ListAdapterProgram(this,program_list_data,mGroupData,imgUtil,"");
						program_list_data.setAdapter(adaterResult);
					}else
					{
						DataBinding();
						return;
					}
			    }
				
				GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
				//Tracker tracker = analytics.newTracker(getResources().getString(R.string.GoogleAnalyticsID)); // Send hits to tracker id UA-XXXX-Y
				tracker = analytics.newTracker(getResources().getString(R.string.GoogleAnalyticsID));
				// All subsequent hits will be send with screen name = "main screen"
				tracker.setScreenName("program screen");
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
    		
    		progress = ProgressDialog.show(this, null, "Loading...", true, true);
			String url = DataURL.program;
			url += "&date="+ year + month + day;
			url += "&contentgroupid=";
			url += "&sportType=00001";
			url += "&lang="+DataSetting.Languge;
			url += "&imei="+DataSetting.IMEI;
			url += "&model="+DataSetting.MODEL;
			url += "&imsi="+DataSetting.IMSI;
			url += "&type="+DataSetting.TYPE;
			AsycTaskLoadData load = new AsycTaskLoadData(this, this,null,"Program");
	        load.execute(url);
	        
    	}
    	catch(Exception ex)
    	{
    		throw new Exception(ex.getMessage());
    	}
    }
	
	@Override
	public void onClick(View v) {
		if( v == program_header_btn)
		{
			Intent intent = new Intent(this, StarSoccer_MatchAnalyse.class);
			//intent.putExtra("data",  mGroupData.get(groupPosition).GroupItemCollection.get(childPosition));
			//intent.putExtra("header", mGroupData.get(groupPosition).leagueData);
			this.startActivity(intent);
	    	finish();
		}
		else if( v== program_header_btn_date)
		{
			DatePickerDialog dp = new DatePickerDialog(this, this, Integer.parseInt(year), Integer.parseInt(month)-1, Integer.parseInt(day));
			dp.setTitle("เลือกวันที่ต้องการ");
			
			dp.show();
		}
		else if(v == program_img_menu)
		{
			program_img_menu.setImageResource(R.drawable.btn_menu_down);
			Intent intent = new Intent(this, StarSoccer_Menu.class);
			startActivity(intent);
	    	finish();
		}
	}



	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {

		if( mGroupData.get(groupPosition).GroupItemCollection.get(childPosition).isDetail )
		{

			Intent intent = new Intent(this, StarSoccer_ProgramAnalyseDetail.class);
			intent.putExtra("data",  mGroupData.get(groupPosition));
			intent.putExtra("index", childPosition);
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
		try {
			
			date = new Date(year-1900, monthOfYear, dayOfMonth);

			getDate();
		
			DataBinding();
			
		} catch (Exception e) {
			PrintLog.printException(this, "Note", e);
		}
	}

	@Override
	public void onReceiveDataStream(String loadName, String url, String strOutput) {
		try
		{
			if(loadName == "Program")
			{

				vResultData  = new Vector<DataElementScore>();

				mGroupData = xmlProgram.groupProgram;
				//txtDate = xmlResult.textHeader+" "+xmlResult.textDate;
				//if(txtHeader != null)txtHeader.setText(xmlResult.textDate);
				//urlRadio = xmlResult.radioURL;

				final String message = xmlProgram.message;
				program_header_text.setText(xmlProgram.textDate);

				if( message.equals("") )
				{

					final ListAdapterProgram adaterResult = new ListAdapterProgram(this,program_list_data,mGroupData,imgUtil,"");

					handler = new Handler();
					handler.post(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							program_message.setVisibility(View.INVISIBLE);
							program_list_data.setVisibility(View.VISIBLE);
							program_list_data.setAdapter(adaterResult);
							if( progress != null )progress.dismiss();

						}
					});

				}
				else
				{
					program_message.setVisibility(View.VISIBLE);
					program_list_data.setVisibility(View.INVISIBLE);
					program_message.setText(message);
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
		xmlProgram = new XMLParserMatchProgram();
		Xml.parse(strOutput, xmlProgram);
	}
}


