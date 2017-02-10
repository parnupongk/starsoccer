package com.isport_starsoccer;

import android.*;
import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.isport_starsoccer.data.DataElementEvent;
import com.isport_starsoccer.exception.PrintLog;
import com.isport_starsoccer.list.ListAdapterEvent;
import com.isport_starsoccer.service.Share;


public class StarSoccer_Event extends StarSoccer_BaseClass implements OnClickListener, OnItemClickListener {

	private Handler handler = null;
	private RelativeLayout layout = null;

	private ImageView event_header_img = null;
	private TextView event_header_text = null;
	private Gallery event_gallery = null;
	private TextView event_text_index = null;
	private ImageView event_share_fb = null;
	private ImageView event_share_twit = null;
	private ImageView event_header_close = null;
	private String txtheader = null;
	private ProgressDialog progress = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.page_event);

		try {

			//layout = new RelativeLayout(this);
			//layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));

			//LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			//inflater.inflate(R.layout.page_hot, layout);


			//layoutCenter = (RelativeLayout)findViewById(R.id.main_layout_center);

			event_header_img = (ImageView) findViewById(R.id.event_header_img);
			event_header_text = (TextView) findViewById(R.id.event_header_text);
			event_gallery = (Gallery) findViewById(R.id.event_gallery);
			event_text_index = (TextView) findViewById(R.id.event_text_index);
			event_share_fb = (ImageView) findViewById(R.id.event_share_fb);
			event_share_twit = (ImageView) findViewById(R.id.event_share_twitter);

			event_header_close = (ImageView) findViewById(R.id.event_header_close);

			event_share_fb.setBackgroundResource(R.drawable.btn_face);
			event_share_twit.setBackgroundResource(R.drawable.btn_twit);

			event_share_fb.setOnClickListener(this);
			event_share_twit.setOnClickListener(this);

			event_header_close.setOnClickListener(this);
			event_gallery.setOnItemClickListener(this);
			event_gallery.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> adap, View view,
										   int position, long arg3) {
					// TODO Auto-generated method stub
					event_text_index.setText("Page : " + (position + 1) + " of " + event_gallery.getCount());
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub

				}
			});

			//layoutCenter.addView(layout);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			PrintLog.printException(this, "StarSoccer", e);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// getMenuInflater().inflate(R.menu.activity_starsoccer_main, menu);
		return true;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		try {
			super.onResume();

			DataBinding();


		} catch (Exception e) {
			// TODO Auto-generated catch block
			PrintLog.printException(this, "", e);
		}
	}

	private void DataBinding() throws Exception {
		try {

			ListAdapterEvent adaterEvent = new ListAdapterEvent(this, vdataEvent);
			event_gallery.setAdapter(adaterEvent);
			event_text_index.setText("Page : " + (event_gallery.getSelectedItemPosition() + 1) + " of " + event_gallery.getCount());

		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}


	@Override
	public void onClick(View v) {
		if (v == event_header_close) {
			Intent intent = new Intent(this, StarSoccer_Main.class);
			startActivity(intent);
			this.finish();
		} else if (vdataEvent != null && vdataEvent.size() > 0) {
			DataElementEvent data = vdataEvent.elementAt(event_gallery.getSelectedItemPosition());
			if (v == event_share_fb) {
				Share.shareFB(this, data.img1, txtheader, "", data.img2);
			} else if (v == event_share_twit) {
				event_share_twit.setImageResource(R.drawable.btn_twit_act);
				Intent intent = new Intent(this, StarSoccer_Twitter.class);
				intent.putExtra("message", data.description);
				this.startActivity(intent);
			}

		}
	}

	@Override
	public void onItemClick(AdapterView<?> adap, View arg1, int position, long arg3) {
		if (event_gallery == adap) {
			final DataElementEvent data = vdataEvent.elementAt(position);

			final CharSequence[] items = {"Call " + data.phone, "Go To " + data.url};

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Activity Event Menu");
			final AlertDialog.Builder builder1 = builder.setItems(items, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int item) {
					if (item == 0) {
						Intent intent = new Intent(Intent.ACTION_CALL);
						intent.setData(Uri.parse("tel:" + data.phone));
						if (ActivityCompat.checkSelfPermission(StarSoccer_Event.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
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
					} else if (item == 1) {
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
	
	
	
}


