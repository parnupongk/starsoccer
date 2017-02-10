package com.isport_starsoccer;

import java.io.InputStream;

import java.sql.Date;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.isport_starsoccer.connnection.XMLParserSportNews;
import com.isport_starsoccer.data.DataSetting;
import com.isport_starsoccer.data.DataURL;
import com.isport_starsoccer.exception.PrintLog;
import com.isport_starsoccer.list.ListAdapterNews;
import com.isport_starsoccer.service.AsycTaskLoadData;
import com.isport_starsoccer.service.ReceiveDataListener;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import org.xml.sax.SAXException;


public class StarSoccer_News extends StarSoccer_BaseClass implements ReceiveDataListener, OnItemClickListener, OnClickListener,OnDateSetListener //,OnScrollListener
{
	private RelativeLayout layout = null;
	private ImageView btnGetDate = null;
	private ListView list = null;
	private ListAdapterNews adapter = null;
	private TextView headerText = null;
	//private RelativeLayout headerLayout = null;
	//private ImageView headerImage =null;
	private ImageView news_img_menu  = null;
	private ImageView news_header_btn =null;
	private ProgressDialog progress = null;
	private String url = "";	
	private Handler handler = null;
	private Date date = null;
	private String day = "1";
	private String month = "1";
	private String year = "1";
	private Tracker tracker=null;
	private XMLParserSportNews xmlPar=null;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_starsoccer_main);
		
		handler = new Handler();
		
		layout = new RelativeLayout(this);
		layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.page_news, layout);
        
        layoutCenter = (RelativeLayout)findViewById(R.id.main_layout_center);
        list = (ListView) layout.findViewById(R.id.page_news_listview);
        //headerImage = (ImageView) layout.findViewById(R.id.page_news_header_image);
        headerText = (TextView) layout.findViewById(R.id.page_news_header_text);
        //headerLayout = (RelativeLayout) layout.findViewById(R.id.page_news_layout_header);
        btnGetDate = (ImageView) layout.findViewById(R.id.page_news_header_button);
        news_img_menu = (ImageView)layout.findViewById(R.id.news_img_menu);
        news_header_btn = (ImageView)layout.findViewById(R.id.news_header_btn);
        
        news_header_btn.setOnClickListener(this);
        news_img_menu.setOnClickListener(this);
        btnGetDate.setOnClickListener( this);
        
        headerText.setTextColor(Color.rgb(20, 20, 20));
        headerText.setSingleLine();
        //headerImage.setImageResource(R.drawable.hd_sub_ball);
     

        //headerText.setPadding((int)(5*imgUtil.scaleSize()), 0, 0, 0);
        list.setDivider(null);
        
    
        list.setOnItemClickListener(this);
		list.setSmoothScrollbarEnabled(true);
        
        date = new Date(System.currentTimeMillis());
        getDate();
        
        layoutCenter.addView(layout);
	}
	
	private void setURL()
	{
		//contestGroupId=&sportType=1&rowCount=&countryId=&lang=th&code=210002a&type=bb
		url = DataURL.sportNews;

		url += "&date=" + year + month + day;
		url += "&sportType=00001";
		url += "&rowcount=";
		url += "&lang="+ DataSetting.Languge;
		url += "&imei="+DataSetting.IMEI;
		url += "&model="+DataSetting.MODEL;
		url += "&imsi="+DataSetting.IMSI;
		url += "&type="+DataSetting.TYPE;
		
		
		AsycTaskLoadData load = new AsycTaskLoadData(this, this,null,"news");
        load.execute(url);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		
		progress = ProgressDialog.show(this, null, "Loading...", true, true);
		
		setURL();
		
		GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
		//Tracker tracker = analytics.newTracker(getResources().getString(R.string.GoogleAnalyticsID)); // Send hits to tracker id UA-XXXX-Y
		tracker = analytics.newTracker(getResources().getString(R.string.GoogleAnalyticsID));
		// All subsequent hits will be send with screen name = "main screen"
		tracker.setScreenName("news screen");
		tracker.send(new HitBuilders.ScreenViewBuilder().build());
	}


	@Override
	public void onItemClick(AdapterView<?> adap, View view, int position, long arg3) {
		if(adap == list)
		{
			if(vDataNews != null)
			{
				Intent intent = new Intent(this, StarSoccer_NewsDetail.class);
				intent.putExtra("data", vDataNews.elementAt(position));
				intent.putExtra("header", headerText.getText());
				startActivity(intent);
			}
		}
	}

	@Override
	public void onClick(View v) {
		if(v == btnGetDate)
		{
			DatePickerDialog dp = new DatePickerDialog(this, this, Integer.parseInt(year), Integer.parseInt(month)-1, Integer.parseInt(day));
			dp.setTitle("เลือกวันที่ท่านต้องการดูสรุปผลบอล");
			
			dp.show();
		}
		else if(v == news_img_menu)
		{
			news_img_menu.setImageResource(R.drawable.btn_menu_down);
			Intent intent = new Intent(this, StarSoccer_Menu.class);
			startActivity(intent);
	    	finish();
		}else if(v == news_header_btn)
		{
			Intent intent = new Intent(this, StarSoccer_LiveScore.class);
			startActivity(intent);
	    	finish();
		}
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

		date = new Date(year-1900, monthOfYear, dayOfMonth);

		getDate();
		
		progress = ProgressDialog.show(this, null, "Loading...", true, true);
		setURL();
	}


	@Override
	public void onReceiveDataStream(String loadName, String url, String strOutput) {
		try {

			if(xmlPar.status.equals("success") && xmlPar.message.trim().equals(""))
			{

				vDataNews = xmlPar.vData;
				adapter = new ListAdapterNews(this, vDataNews, imgUtil);

				final String text = xmlPar.textDate;
				handler.post(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						list.setAdapter(adapter);
						headerText.setText(text);
					}
				});

			}
			else
			{
				Toast.makeText(this, xmlPar.message, Toast.LENGTH_LONG).show();
			}

			progress.dismiss();
		} catch (Exception e) {
			progress.dismiss();
			PrintLog.printException(this, "Main method onReceiveDataStream", e) ;
		}
	}

	@Override
	public void onReceiveGetDataXML(String loadName, String url, String strOutput) throws InterruptedException, SAXException {
		xmlPar = new XMLParserSportNews();
		Xml.parse(strOutput, xmlPar);
	}
}
