package com.isport_starsoccer;
import java.io.InputStream;
import java.util.Vector;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.isport_starsoccer.connnection.XMLParserSportClip;
import com.isport_starsoccer.data.DataElementScore;
import com.isport_starsoccer.data.DataElementSportClip;
import com.isport_starsoccer.data.DataSetting;
import com.isport_starsoccer.data.DataURL;
import com.isport_starsoccer.exception.PrintLog;
import com.isport_starsoccer.list.ListAdapterClip;
import com.isport_starsoccer.service.AsycTaskLoadData;
import com.isport_starsoccer.service.ReceiveDataListener;

import org.xml.sax.SAXException;


public class StarSoccer_Clip extends StarSoccer_BaseClass implements ReceiveDataListener,OnClickListener,OnItemClickListener {
	
	private Handler handler = null;
	private RelativeLayout layout = null;
	
	private ImageView page_clip_img_hot = null;
	private ImageView page_clip_img_menu = null;
	private ImageView page_clip_header_image = null;
	private TextView page_clip_header_text = null;
	private ListView page_clip_listview = null;
	private RelativeLayout page_clip_layout_header = null;
	
	private Vector<DataElementSportClip> vSportClip = null;
	private ProgressDialog progress = null;
	private boolean resumeHasRun = false;
	private XMLParserSportClip xmlClip=null;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starsoccer_main);
      
        
        layout = new RelativeLayout(this);
		layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.page_clip, layout);
        
        
        layoutCenter = (RelativeLayout)findViewById(R.id.main_layout_center);
        page_clip_img_hot = (ImageView)layout.findViewById(R.id.page_clip_img_hot) ;
        page_clip_img_menu = (ImageView)layout.findViewById(R.id.page_clip_image_menu);
        page_clip_header_image = (ImageView)layout.findViewById(R.id.page_clip_header_image);
        page_clip_header_text = (TextView)layout.findViewById(R.id.page_clip_header_text);
        page_clip_listview = (ListView)layout.findViewById(R.id.page_clip_listview);
        page_clip_layout_header = (RelativeLayout)layout.findViewById(R.id.page_clip_layout_header);
        
        page_clip_listview.setOnItemClickListener(this);
        page_clip_img_menu.setOnClickListener(this);
        page_clip_img_hot.setOnClickListener(this);
        
        
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
    	finish();
    }
    @Override
    protected void onResume() {
    	try {
				super.onResume();
				if (!resumeHasRun ) {
			        resumeHasRun = true;
			        progress = ProgressDialog.show(this, null, "Loading...", true, true);
			        DataBinding();
			        return;
			    }
			
    	} catch (Exception e) {
			PrintLog.printException(this, "", e);
		}
    }

    private void DataBinding() throws Exception
    {
    	try
    	{
    		
			String url = DataURL.clip;
			url += "&contentgroupid=";
			url += "&sportType=00001";
			url += "&lang="+ DataSetting.Languge;
			url += "&imei="+DataSetting.IMEI;
			url += "&model="+DataSetting.MODEL;
			url += "&imsi="+DataSetting.IMSI;
			url += "&type="+DataSetting.TYPE;
			AsycTaskLoadData load = new AsycTaskLoadData(this, this,null,"Clip");
	        load.execute(url);
	        
    	}
    	catch(Exception ex)
    	{
    		throw new Exception(ex.getMessage());
    	}
    }

	
	@Override
	public void onClick(View v) {

		if(v == page_clip_img_menu)
		{
			page_clip_img_menu.setImageResource(R.drawable.btn_menu_down);
			Intent intent = new Intent(this, StarSoccer_Menu.class);
			startActivity(intent);
			finish();
		}
		else if(v == page_clip_img_hot)
		{
			Intent intent = new Intent(this, StarSoccer_Hot.class);
			startActivity(intent);
			finish();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		
		if(vSportClip != null)
		{
			Intent intent = new Intent(this, StarSoccer_ClipDetail.class);
			intent.putExtra("data", vSportClip.elementAt(position));
			intent.putExtra("header", page_clip_header_text.getText());
			startActivity(intent);
		}
	}


	@Override
	public void onReceiveDataStream(String loadName, String url, String strOutput) {
		try
		{
			if(loadName == "Clip")
			{

				vResultData  = new Vector<DataElementScore>();


				vSportClip = xmlClip.vData;
				final String message = xmlClip.message;
				final ListAdapterClip adaterClip = new ListAdapterClip(this,vSportClip,imgUtil);

				handler = new Handler();
				handler.post(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub

						page_clip_listview.setAdapter(adaterClip);
						page_clip_header_text.setText(xmlClip.textDate);
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
		xmlClip = new XMLParserSportClip();
		Xml.parse(strOutput, xmlClip);
	}
}


