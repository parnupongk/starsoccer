package com.isport_starsoccer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.isport_starsoccer.data.DataElementSportClip;
import com.isport_starsoccer.service.ReceiveDataListener;

import org.xml.sax.SAXException;

public class StarSoccer_ClipDetail extends Activity implements ReceiveDataListener, OnClickListener
{
	private DataElementSportClip data = null;
	private RelativeLayout layout = null;
	private TextView textHeader = null;
	private TextView textTitle = null;
	private ImageView clip_detail_image_menu = null;
	private String text = "";
	private VideoView videoView = null;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_starsoccer_main);
		setContentView(R.layout.page_clip_detail);
		if(getIntent().getExtras() != null)
		{
			data = (DataElementSportClip) getIntent().getExtras().get("data");
			text = getIntent().getExtras().getString("header");
		}

/*
		layout = new RelativeLayout(this);
		layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		layout.setGravity(Gravity.CENTER_HORIZONTAL);
		
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.page_clip_detail, layout);*/
        
        //layoutCenter = (RelativeLayout)findViewById(R.id.main_layout_center);
        textHeader = (TextView) findViewById(R.id.clip_detail_header_text);
        textTitle = (TextView) findViewById(R.id.clip_detail_data_topic);
        videoView = (VideoView)findViewById(R.id.clip_detail_videoview);
        clip_detail_image_menu = (ImageView)findViewById(R.id.clip_detail_image_menu);
        
        clip_detail_image_menu.setOnClickListener(this);
       
        
        if(data != null)
        {
        	
//        	textHeader.setText(data.header);
        	textHeader.setText(text);
        	textTitle.setText("     "+data.clipTopic);
        	
        	//videoView.setVideoPath(data.clipURL);
        	
        	videoView.requestFocus();
        	
        	//Uri video = Uri.parse("rtsp://203.149.30.25:1935/vod/mp4:504d79d1427c4_small.mp4");
        	Uri video = Uri.parse(data.clipURL);
            videoView.setVideoURI(video);
            videoView.setMediaController(new MediaController(this));
            videoView.setKeepScreenOn(true);
            videoView.start();    	
        }
        
        //layoutCenter.addView(layout);
	}

	@Override
	public void onClick(View v) {
		if(v == clip_detail_image_menu)
		{
			Intent intent = new Intent(this, StarSoccer_Menu.class);
			startActivity(intent);
			finish();
		}
	}


	@Override
	public void onReceiveDataStream(String loadName, String url, String strOutput) {

	}

	@Override
	public void onReceiveGetDataXML(String loadName, String url, String strOutput) throws InterruptedException, SAXException {

	}
}
