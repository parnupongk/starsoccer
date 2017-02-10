package com.isport_starsoccer;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import org.xml.sax.SAXException;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Xml;
import android.widget.ImageView;

import com.isport_starsoccer.connnection.XMLParserEvent;
import com.isport_starsoccer.connnection.XMLParserListMenu;
import com.isport_starsoccer.data.DataElementGroupListMenu;
import com.isport_starsoccer.data.DataSetting;
import com.isport_starsoccer.data.DataURL;
import com.isport_starsoccer.exception.PrintLog;
import com.isport_starsoccer.service.AsycTaskLoadData;
import com.isport_starsoccer.service.ReceiveDataListener;
import com.isport_starsoccer.service.StartUp;

public class StarSoccer_Logo extends Activity implements Runnable,ReceiveDataListener
{
    /** Called when the activity is first created. */
	private ImageView logo = null;
	private Handler thread = null;
	private boolean isLoaded = false;
	private XMLParserListMenu xmlListMenu=null;
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_logo);
        DataSetting.IMEI = StartUp.getImei(this);
        DataSetting.IMSI = StartUp.getImsi(this);
        StartUp.getModel(this);
        StartUp.getSetting(this);
        
        logo = (ImageView) findViewById(R.id.logo);
    	logo.setBackgroundResource(R.drawable.landingfinal);
        

    	if( !isOnline() )
    	{
    		AlterInternetError();
    	}
    	
    	//thread = new Handler();
    	//thread.postDelayed(this, 5000L);

    }

    private void AlterInternetError()
    {
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setMessage("Please Connect to the internet")
    	       .setCancelable(false)
    	       .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
    	           public void onClick(DialogInterface dialog, int id) {
    	                finish();
    	           }
    	       });
    	AlertDialog alert = builder.create();
    	alert.show();
    }
    
    @Override
    protected void onStop() {
    	// TODO Auto-generated method stub
    	super.onStop();
    	isLoaded = false;
    	finish();
    }
    @Override
    protected void onDestroy() {
    	// TODO Auto-generated method stub
    	super.onDestroy();
    	finish();
    } 
    
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	if( !isLoaded )
    	{
	    	EventDataBinding();
	    	
	    	menuDataBinding();
	    	
	    	if(DataSetting.checkNotify)
	    	{
	    		//this.startService(new Intent(this, StarSoccer_Notify.class));
	    	}
	    	isLoaded = true;
    	}
    }
	private void menuDataBinding()
	{
		try
		{
			String url = "";
			url = DataURL.listMenu;
			url += "&sportType=00001" ;
			url += "&lang="+DataSetting.Languge;
			url += "&imei="+DataSetting.IMEI;
			url += "&model="+DataSetting.MODEL;
			url += "&imsi="+DataSetting.IMSI;
			url += "&type="+DataSetting.TYPE;
			

			AsycTaskLoadData load = new AsycTaskLoadData(this , this,null,"listmenu");
			load.execute(url);
		}
		catch(Exception ex)
		{
			PrintLog.printException(this,"Logo method run", ex);
		}
	}
	private void EventDataBinding() 
    {
    	try
    	{
    		
			String url = DataURL.event;
			url += "&sportType=00001";
			url += "&lang="+DataSetting.Languge;
			url += "&imei="+DataSetting.IMEI;
			url += "&model="+DataSetting.MODEL;
			url += "&imsi="+DataSetting.IMSI;
			url += "&type="+DataSetting.TYPE;
			AsycTaskLoadData load = new AsycTaskLoadData(this, this,null,"event");
	        load.execute(url);
	        
    	}
    	catch(Exception ex)
    	{
    		PrintLog.printException(this,"Logo method run", ex);
    	}
    }
	
	public boolean isOnline() {
	    ConnectivityManager cm =
	        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

	    return cm.getActiveNetworkInfo() != null && 
	       cm.getActiveNetworkInfo().isConnectedOrConnecting();
	}

	@Override
	public void run() {
		if( StarSoccer_BaseClass.vdataEvent !=null && StarSoccer_BaseClass.vdataEvent.size() > 0 )
		{
			Intent intent = new Intent(this, StarSoccer_Event.class);
			startActivity(intent);
			finish();
		}
		else
		{
			Intent intent = new Intent(this, StarSoccer_Main.class);
			startActivity(intent);
			finish();
		}
	}

	@Override
	public void onReceiveDataStream(String loadName, String url, String strOutput) {
		try
		{
			if( !strOutput.equals("") )
			{
				if(loadName == "listmenu")
				{



					StarSoccer_BaseClass.IVRNO = xmlListMenu.ivrNo;
					StarSoccer_BaseClass.OTPCODE=xmlListMenu.otpCode;
					StarSoccer_BaseClass.URLBANNER = xmlListMenu.urlBanner;
					StarSoccer_BaseClass.URLICON = xmlListMenu.urlIcon;
					StarSoccer_BaseClass.ISACTIVE = xmlListMenu.isActive;
					StarSoccer_BaseClass.ISAdView = xmlListMenu.isAdView;
					StarSoccer_BaseClass.ACTIVE_HEADER = xmlListMenu.active_header;
					StarSoccer_BaseClass.ACTIVE_DETAIL = xmlListMenu.active_detail;
					StarSoccer_BaseClass.ACTIVE_FOOTER = xmlListMenu.active_footer;
					StarSoccer_BaseClass.ACTIVE_FOOTER1 = xmlListMenu.active_footer1;
					StarSoccer_BaseClass.ACTIVE_OTPWAIT = xmlListMenu.active_otpwait;
					StarSoccer_BaseClass.SERVERVERSION = xmlListMenu.serverversion;
					StarSoccer_BaseClass.vGroupListMenu = new ArrayList<DataElementGroupListMenu>();
					StarSoccer_BaseClass.vGroupListMenu = xmlListMenu.vGroupListMenu;

				}
				else if(loadName == "event")
				{
					XMLParserEvent xmlList = new XMLParserEvent();
					Xml.parse(strOutput, xmlList);

					StarSoccer_BaseClass.vdataEvent = xmlList.vData;
					//menuDataBinding();

					thread = new Handler();
					thread.postDelayed(this, 3000L);
				}
			}
			else
			{
				AlterInternetError();
			}

		}
		catch(Exception ex)
		{
			PrintLog.printException(this,"Logo method run", ex);
		}
	}

	@Override
	public void onReceiveGetDataXML(String loadName, String url, String strOutput) throws InterruptedException, SAXException {
		xmlListMenu = new XMLParserListMenu();
		Xml.parse(strOutput,  xmlListMenu);
	}
}