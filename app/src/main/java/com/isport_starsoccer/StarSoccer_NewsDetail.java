package com.isport_starsoccer;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.isport_starsoccer.data.DataElementSportNews;
import com.isport_starsoccer.data.DataSetting;
import com.isport_starsoccer.service.ImageUtil;
import com.isport_starsoccer.service.ReceiveDataListener;
import com.isport_starsoccer.service.Share;
import com.squareup.picasso.Picasso;

import org.xml.sax.SAXException;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.OAuthAuthorization;
import twitter4j.auth.RequestToken;
import twitter4j.conf.*;

public class StarSoccer_NewsDetail extends Activity implements ReceiveDataListener, OnClickListener
{
//	private ProgressDialog progress = null;
	private ConfigurationBuilder builder = null;
	private Twitter mTwitter;
	private DataElementSportNews data = null;
	private RelativeLayout layout = null;
	private RelativeLayout layoutCenter = null;
	private ImageView headerImage = null;
	private TextView textHeader = null;
	//private ImageView imageHilight = null;
	private RelativeLayout layoutData = null;
	//private ScrollView scroll = null;
	private ImageView detailImage = null;
	private TextView textDataHeader = null;
	private TextView textTitle = null;
	private TextView textDetail = null;
	private ImageView btnFB  = null;
	private ImageView btnTwit = null;
	private ProgressBar progress = null;
	private String text = "";
	private ImageUtil imgUtil=null;
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_starsoccer_main);
		setContentView(R.layout.sub_news_detail);

		imgUtil = new ImageUtil(this);

		if(getIntent().getExtras() != null)
		{
			data = (DataElementSportNews) getIntent().getExtras().get("data");
			text = getIntent().getExtras().getString("header");
		}
		
		/*layout = new RelativeLayout(this);
		layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		layout.setGravity(Gravity.CENTER_HORIZONTAL);
		
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.sub_news_detail, layout);*/
        
        //layoutCenter = (RelativeLayout)findViewById(R.id.main_layout_center);
        //layoutHeader = (LinearLayout) layout.findViewById(R.id.detail_news_layout_header);
        headerImage = (ImageView) findViewById(R.id.detail_news_header_image);
        textHeader = (TextView) findViewById(R.id.detail_news_header_text);
        btnFB = (ImageView)findViewById(R.id.news_share_fb);
        btnTwit = (ImageView)findViewById(R.id.news_share_twit);
        layoutData = (RelativeLayout) findViewById(R.id.detail_news_layout_data);
        detailImage = (ImageView) findViewById(R.id.detail_news_data_image);
        progress = (ProgressBar) findViewById(R.id.detail_news_data_progress);
        textDataHeader = (TextView) findViewById(R.id.detail_news_data_header);
        textTitle = (TextView) findViewById(R.id.detail_news_data_title);
        textDetail = (TextView) findViewById(R.id.detail_news_data_detail);
        
        progress.setLayoutParams(new FrameLayout.LayoutParams((int)(100*imgUtil.scaleSize()), (int)(100*imgUtil.scaleSize()), Gravity.CENTER));
        detailImage.setLayoutParams(new FrameLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT,Gravity.CENTER));
        
        btnFB.setImageResource(R.drawable.btn_face);
        btnTwit.setImageResource(R.drawable.btn_twit);
        
        btnFB.setOnClickListener(this);
        btnTwit.setOnClickListener(this);
        layoutData.setPadding((int)(10*imgUtil.scaleSize()), 0, (int)(10*imgUtil.scaleSize()), 0);

                
        if(data != null)
        {
        	SpannableString content = new SpannableString(data.header);
		    content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
		    
        	textHeader.setText(text);
        	textDataHeader.setText(content);
        	textTitle.setText("     "+data.title);
        	textDetail.setText("    "+Html.fromHtml(data.detail));
        	
        	textHeader.setTextSize(19);
        	textDataHeader.setTextSize(19);
        	
        	textTitle.setTextColor(Color.BLACK);
        	textHeader.setTextColor(Color.BLACK);
        	textDataHeader.setTextColor(Color.BLACK);
        	textDetail.setTextColor(Color.BLACK);
        	
        	//textTitle.setTextSize(19);
        	//textDetail.setTextSize(18);
			progress.setVisibility(View.VISIBLE);
			Picasso.with(this).load(data.img600)
					.into(detailImage, new com.squareup.picasso.Callback() {
						@Override
						public void onSuccess() {
							if (progress != null) {
								progress.setVisibility(View.GONE);
							}
						}

						@Override
						public void onError() {

						}
					});

        	
        }
        else
        {
        	//PrintLog.printException(this, "", e)("data == null", "data == null");
        }
        
        //layoutCenter.addView(layout);
	}
	
	@Override
		protected void onPause() {
			super.onPause();
			finish();
		}


	@Override
	public void onClick(View view) {
		if(view == detailImage && data != null)
		{
			/*Intent intent = new Intent(this, DetailNewsImage.class);
			intent.putExtra("data", data);
			intent.putExtra("image", image);
			startActivity(intent);*/
		}
		else if(data != null )
		{

			if(view == btnFB)
			{
				Share.shareFB(this, DataSetting.URLPLAYSOTRE, data.title, "", data.img350);
			}
			else if(view == btnTwit)
			{
				btnTwit.setImageResource(R.drawable.btn_twit_act);
				//Share.sendEmail(this, new String[]{}, data.title, data.detail);
				Intent intent = new Intent(this, StarSoccer_Twitter.class);
				intent.putExtra("message", data.header);
				this.startActivity(intent);
			}
		
		}
	}

	@Override
	public void onReceiveDataStream(String loadName, String url, String strOutput) {

	}

	@Override
	public void onReceiveGetDataXML(String loadName, String url, String strOutput) throws InterruptedException, SAXException {

	}
}
