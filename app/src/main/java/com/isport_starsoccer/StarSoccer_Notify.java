package com.isport_starsoccer;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.util.Xml;

import com.isport_starsoccer.connnection.XMLParserLiveNotify;
import com.isport_starsoccer.data.DataSetting;
import com.isport_starsoccer.data.DataURL;
import com.isport_starsoccer.service.AsycTaskLoadData;
import com.isport_starsoccer.service.ReceiveDataListener;
import com.isport_starsoccer.service.StartUp;

import org.xml.sax.SAXException;


public class StarSoccer_Notify extends Service implements Runnable, ReceiveDataListener
{
	private String URL = "";
	private Handler thread = null;

	private Notification notification = null;
	private CharSequence contentTitle = "StarSoccer";
    private NotificationManager mNotificationManager = null;
	private XMLParserLiveNotify xmlPar=null;

//	public LiveScoreNotify(Context context, ReceiveDataListener receive, String TYPE)
//	public LiveScoreNotify(Context context)
//	{
//		this.TYPE = TYPE;
//		this.context = context;
//		this.receive = receive;
//		this.context = context;
//		mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//		thread = new Handler();
//	}
	
	@Override
	public IBinder onBind(Intent intent) {
		
		return null;
	}
	
	@Override
	public void onCreate()
	{
		mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
		thread = new Handler();
		thread.post(this);
	}

	
	private void setURLLivescore()
	{
		if( DataSetting.setLeague == null )
			{
				StartUp.getSetting(this);
				DataSetting.IMEI = StartUp.getImei(this);
		        DataSetting.IMSI = StartUp.getImsi(this);
		        StartUp.getModel(this);
			}
		
		URL = DataURL.notification;
		URL += "&contentgroupid=" + DataSetting.setLeague;
        URL += "&min=";
        URL += "&lang="+DataSetting.Languge;
        URL += "&imei="+DataSetting.IMEI;
        URL += "&model="+DataSetting.MODEL;
        URL += "&imsi="+DataSetting.IMSI;
        URL += "&type="+DataSetting.TYPE;
	}

	@Override
	public void run() {
		
		try {
			setURLLivescore();
			
			
			AsycTaskLoadData load = new AsycTaskLoadData(this,this,null,"notification");
	        load.execute(URL);
	        
	        if(DataSetting.checkNotify)
	        	thread.postDelayed(this, 60000L);
	        
		} catch (Exception e) {
			
			//PrintLog.printException("LiveScoreNotify method run", e);
		}
	}
	
	private void notifyScore(String wording)
	{
        notification = new Notification(R.drawable.hd_sub_ball, wording, System.currentTimeMillis()); 

        /*Intent notifyIntent = new Intent(this, StarSoccer_LiveScore.class);//new Intent(android.content.Intent.ACTION_VIEW,Uri.parse("http://www.android.com"));
        PendingIntent intent = PendingIntent.getActivity(this, 0, notifyIntent, Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

        notification.setLatestEventInfo(this, contentTitle, wording, intent);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.defaults |= Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND ;          
        notification.ledARGB = Color.WHITE;
        notification.flags |= Notification.FLAG_SHOW_LIGHTS;
        notification.ledOnMS = 1500;                         
        notification.ledOffMS = 1500;  
        mNotificationManager.notify(SIMPLE_NOTFICATION_ID, notification);*/
        
		//Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
		//v.vibrate(1000);
	}


	@Override
	public void onReceiveDataStream(String loadName, String url, String strOutput) {
		try {


			if(!xmlPar.notification.trim().equals("none") && DataSetting.checkNotify && !xmlPar.notification.trim().equals(""))
			{
				notifyScore(xmlPar.notification);
			}
		} catch (Exception e) {
			Log.w("print exception start", "------ Start "+e.getMessage()+" -----" + url);
		}
	}

	@Override
	public void onReceiveGetDataXML(String loadName, String url, String strOutput) throws InterruptedException, SAXException {
		xmlPar = new XMLParserLiveNotify();
		Xml.parse(strOutput, xmlPar);
	}
}