package com.isport_starsoccer;

import java.io.InputStream;
import java.util.Vector;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.isport_starsoccer.connnection.XMLParserHot;
import com.isport_starsoccer.data.DataElementHot;
import com.isport_starsoccer.data.DataSetting;
import com.isport_starsoccer.data.DataURL;
import com.isport_starsoccer.exception.PrintLog;
import com.isport_starsoccer.list.ListAdapterHot;
import com.isport_starsoccer.service.AsycTaskLoadData;
import com.isport_starsoccer.service.ReceiveDataListener;
import com.isport_starsoccer.service.Share;

import org.xml.sax.SAXException;


public class StarSoccer_Hot extends StarSoccer_BaseClass implements ReceiveDataListener, OnClickListener, OnItemClickListener {

	private Handler handler = null;
	private RelativeLayout layout = null;

	private ImageView hot_image_menu = null;
	private ImageView hot_header_img = null;
	private TextView hot_header_text = null;
	private Gallery hot_gallery = null;
	private TextView hot_text_index = null;
	private ImageView hot_share_fb = null;
	private ImageView hot_share_twit = null;

	private ImageView hot_header_livescore = null;
	private String txtheader = null;
	private Vector<DataElementHot> vdataHot = null;
	private ProgressDialog progress = null;
	private boolean resumeHasRun = false;
	private XMLParserHot xmlHot = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_starsoccer_main);

		try {

			layout = new RelativeLayout(this);
			layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));

			LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			inflater.inflate(R.layout.page_hot, layout);


			layoutCenter = (RelativeLayout) findViewById(R.id.main_layout_center);
			hot_image_menu = (ImageView) layout.findViewById(R.id.hot_image_menu);
			hot_header_img = (ImageView) layout.findViewById(R.id.hot_header_img);
			hot_header_text = (TextView) layout.findViewById(R.id.hot_header_text);
			hot_gallery = (Gallery) layout.findViewById(R.id.hot_gallery);
			hot_text_index = (TextView) layout.findViewById(R.id.hot_text_index);
			hot_share_fb = (ImageView) layout.findViewById(R.id.hot_share_fb);
			hot_share_twit = (ImageView) layout.findViewById(R.id.hot_share_twit);

			hot_header_livescore = (ImageView) layout.findViewById(R.id.hot_header_livescore);

			hot_share_fb.setBackgroundResource(R.drawable.btn_face);
			hot_share_twit.setBackgroundResource(R.drawable.btn_twit);

			hot_share_fb.setOnClickListener(this);
			hot_share_twit.setOnClickListener(this);

			hot_header_livescore.setOnClickListener(this);
			hot_image_menu.setOnClickListener(this);
			hot_gallery.setOnItemClickListener(this);
			hot_gallery.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> adap, View view,
										   int position, long arg3) {
					hot_text_index.setText("Page : " + (position + 1) + " of " + hot_gallery.getCount());
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {

				}
			});
			layoutCenter.addView(layout);
		} catch (Exception e) {
			PrintLog.printException(this, "StarSoccer", e);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// getMenuInflater().inflate(R.menu.activity_starsoccer_main, menu);
		return true;
	}

	@Override
	protected void onPause() {
		super.onPause();

	}

	;

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
				resumeHasRun = true;
				progress = ProgressDialog.show(this, null, "Loading...", true, true);
				DataBinding();
				return;
			}

		} catch (Exception e) {
			PrintLog.printException(this, "", e);
		}
	}

	private void DataBinding() throws Exception {
		try {

			String url = DataURL.hot;
			url += "&sportType=00001";
			url += "&lang=" + DataSetting.Languge;
			url += "&imei=" + DataSetting.IMEI;
			url += "&model=" + DataSetting.MODEL;
			url += "&imsi=" + DataSetting.IMSI;
			url += "&type=" + DataSetting.TYPE;
			AsycTaskLoadData load = new AsycTaskLoadData(this, this, null, "Hot");
			load.execute(url);

		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}


	@Override
	public void onClick(View v) {
		if (v == hot_header_livescore) {
			Intent intent = new Intent(this, StarSoccer_LiveScore.class);
			this.startActivity(intent);
			finish();
		} else if (v == hot_image_menu) {
			hot_image_menu.setImageResource(R.drawable.btn_menu_down);
			Intent intent = new Intent(this, StarSoccer_Menu.class);
			startActivity(intent);
			finish();
		} else if (vdataHot != null && vdataHot.size() > 0) {
			DataElementHot data = vdataHot.elementAt(hot_gallery.getSelectedItemPosition());
			if (v == hot_share_fb) {
				Share.shareFB(this, DataSetting.URLPLAYSOTRE, txtheader, "", data.img2);
			} else if (v == hot_share_twit) {
				hot_share_twit.setImageResource(R.drawable.btn_twit_act);
				//Share.sendEmail(this, new String[]{}, txtheader, data.description);
				Intent intent = new Intent(this, StarSoccer_Twitter.class);
				intent.putExtra("message", txtheader);
				this.startActivity(intent);
			}

		}
	}


	@Override
	public void onItemClick(AdapterView<?> adap, View arg1, int position, long arg3) {
		if (hot_gallery == adap) {
			final DataElementHot data = vdataHot.elementAt(position);

			final CharSequence[] items = {"Call " + data.phone, "Go To " + data.url};

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Privilege Menu");
			builder.setItems(items, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int item) {
					if (item == 0) {
						Intent intent = new Intent(Intent.ACTION_CALL);
						intent.setData(Uri.parse("tel:" + data.phone));
						if (ActivityCompat.checkSelfPermission(StarSoccer_Hot.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
							// TODO: Consider calling
							//    ActivityCompat#requestPermissions
							// here to request the missing permissions, and then overriding
							//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
							//                                          int[] grantResults)
							// to handle the case where the user grants the permission. See the documentation
							// for ActivityCompat#requestPermissions for more details.
							return;
						}
						startActivity(intent);
					}
			        else if(item == 1)
			        {
			        	Intent i = new Intent(Intent.ACTION_VIEW);  
			        	i.setData(Uri.parse(data.url));  
			        	startActivity(i); 
			        }
			    }
			});
			AlertDialog alert = builder.create();
			alert.show();
		}
		
	}


	@Override
	public void onReceiveDataStream(String loadName, String url, String strOutput) {
		try
		{
			if(loadName == "Hot")
			{



				vdataHot = xmlHot.vData;
				txtheader = xmlHot.header;
				final String message = xmlHot.message;
				final ListAdapterHot adaterHot = new ListAdapterHot(this,vdataHot);

				handler = new Handler();
				handler.post(new Runnable() {
					@Override
					public void run() {
						hot_text_index.setText("Page : "+(hot_gallery.getSelectedItemPosition()+1)+" of "+hot_gallery.getCount());
						hot_gallery.setAdapter(adaterHot);
						hot_header_text.setText(xmlHot.date);
						hot_header_img.setBackgroundResource(R.drawable.hd_sub_ball);

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
		xmlHot = new XMLParserHot();
		Xml.parse(strOutput, xmlHot);
	}
}


