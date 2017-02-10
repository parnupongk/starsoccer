package com.isport_starsoccer;

import java.io.InputStream;
import java.util.Vector;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.isport_starsoccer.connnection.XMLParserLeagueTable;
import com.isport_starsoccer.data.DataElementLeagueTable;
import com.isport_starsoccer.data.DataSetting;
import com.isport_starsoccer.data.DataURL;
import com.isport_starsoccer.exception.PrintLog;
import com.isport_starsoccer.service.AsycTaskLoadData;
import com.isport_starsoccer.service.ReceiveDataListener;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils.TruncateAt;
import android.util.Xml;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.xml.sax.SAXException;

public class StarSoccer_LeagueTable extends StarSoccer_BaseClass implements ReceiveDataListener, OnClickListener
{
	private Context context = null;
	private Activity activity = null;
	private XMLParserLeagueTable xmlPar=null;
	private ProgressDialog progress = null;
	private Handler handler = null;
	
	private RelativeLayout layout = null;
	
	private LinearLayout layoutHeader = null;
	private ImageView imgHeader = null;
	private TextView textHeader = null;
	private ImageView buttonHeader = null;
	private ImageView leaguetable_image_menu = null;
	private LinearLayout layoutLeague = null;
	private ImageView imgLeague = null;
	private TextView textLeague = null;
	private ImageView btnLeague = null;
	
	private TableLayout table = null;
	private TextView star = null;
	
	private String URL = "";
	
	private Vector<DataElementLeagueTable> vData = null;
	private Tracker tracker=null;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_starsoccer_main);
		
		handler = new Handler();
		
//		progress = ProgressDialog.show(this, null, "Loading...", true, true);
		layoutCenter = (RelativeLayout)findViewById(R.id.main_layout_center);
		
		layout = new RelativeLayout(this);
		layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		//layout.setPadding((int)(10*imgUtil.scaleSize()), 0, (int)(10*imgUtil.scaleSize()), 0);
		
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.page_leaguetable, layout);
        
        layoutHeader = (LinearLayout) layout.findViewById(R.id.table_layout_header);
//        imgHeader = (ImageView) layout.findViewById(R.id.table_header_img);
        buttonHeader = (ImageView) layout.findViewById(R.id.table_header_button);
        textHeader = (TextView) layout.findViewById(R.id.table_header_text);
        leaguetable_image_menu = (ImageView)layout.findViewById(R.id.leaguetable_image_menu);
        btnLeague = (ImageView)layout.findViewById(R.id.table_header_img_league);
        
        
        
        btnLeague.setOnClickListener(this);
        leaguetable_image_menu.setOnClickListener(this);
        buttonHeader.setOnClickListener(this);
        
       // imgHeader.setPadding((int)(10*imgUtil.scaleSize()), 0, (int)(10*imgUtil.scaleSize()), 0);
        
        //layoutHeader.setBackgroundResource(R.drawable.hd_sub);
//        layoutHeader.setBackgroundColor(Color.WHITE);
//        imgHeader.setImageResource(R.drawable.hd_sub_ball);
        
        layoutLeague = (LinearLayout) layout.findViewById(R.id.table_layout_league);
        imgLeague = (ImageView) layout.findViewById(R.id.table_league_img);
        textLeague = (TextView) layout.findViewById(R.id.table_league_text);
        
        imgLeague.setPadding((int)(10*imgUtil.scaleSize()), 0, (int)(5*imgUtil.scaleSize()), 0);
        
        table = (TableLayout) layout.findViewById(R.id.table_layout_table);
        star = (TextView) layout.findViewById(R.id.table_star_text);
        
        star.setTextColor(Color.WHITE);
        
        layoutCenter.addView(layout);
	}
	
	private void setURL()
	{
		//contestGroupId=&lang=&code=210002a&type=bb
		URL = DataURL.table;
		if(DataSetting.contentGroupId != null)
		{
        	URL += "&contentgroupid="+DataSetting.contentGroupId;
		}
		else
		{
			URL += "&contentgroupid=";
		}
			URL += "&sportType=00001";
			URL += "&lang="+DataSetting.Languge;
			URL += "&imei="+DataSetting.IMEI;
			URL += "&model="+DataSetting.MODEL;
			URL += "&imsi="+DataSetting.IMSI;
			URL += "&type="+DataSetting.TYPE;
        
        AsycTaskLoadData load = new AsycTaskLoadData(this, this,null,"leaguetable");
        load.execute(URL);
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
		tracker.setScreenName("leaguetable screen");
		tracker.send(new HitBuilders.ScreenViewBuilder().build());
	}
	
	private void AddDataLeagueTable(DataElementLeagueTable data, TableRow row, TextView lp, TextView name, TextView p
			, TextView w, TextView d, TextView l, TextView f, TextView a, TextView pds, TextView gd)
	{
		name.setSingleLine();
		name.setEllipsize(TruncateAt.END);
		
		lp.setText(data.place);
		name.setText(data.name);
		p.setText(data.tPlay);
		w.setText(data.tWon);
		d.setText(data.tDraws);
		l.setText(data.tLost);
		f.setText(data.tScore);
		a.setText(data.tConcede);
		pds.setText(data.tPoint);
		gd.setText(data.tDiff);
		
		lp.setTextColor(Color.WHITE);
		name.setTextColor(Color.WHITE);
		p.setTextColor(Color.WHITE);
		w.setTextColor(Color.WHITE);
		d.setTextColor(Color.WHITE);
		l.setTextColor(Color.WHITE);
		f.setTextColor(Color.WHITE);
		a.setTextColor(Color.WHITE);
		pds.setTextColor(Color.WHITE);
		gd.setTextColor(Color.WHITE);
		
		AddRowToLeagueTable(row,lp,name,p,w,d,l,f,a,pds,gd);
	}
	private void AddHeaderLeagueTable(TableRow row,TextView lp,TextView name,TextView p
			,TextView w,TextView d,TextView l,TextView f,TextView a,TextView pds,TextView gd)
	{
		lp.setText("LP");
		name.setText("TEAM");
		p.setText("P");
		w.setText("W");
		d.setText("D");
		l.setText("L");
		f.setText("F");
		a.setText("A");
		pds.setText("PDs");
		gd.setText("GD");
		
		lp.setTextColor(Color.YELLOW);
		name.setTextColor(Color.YELLOW);
		p.setTextColor(Color.YELLOW);
		w.setTextColor(Color.YELLOW);
		d.setTextColor(Color.YELLOW);
		l.setTextColor(Color.YELLOW);
		f.setTextColor(Color.YELLOW);
		a.setTextColor(Color.YELLOW);
		pds.setTextColor(Color.YELLOW);
		gd.setTextColor(Color.YELLOW);
		
		row.setBackgroundColor(Color.rgb(44, 44, 44));
		AddRowToLeagueTable(row,lp,name,p,w,d,l,f,a,pds,gd);
	}
	private void AddRowToLeagueTable(TableRow row,TextView lp,TextView name,TextView p
			,TextView w,TextView d,TextView l,TextView f,TextView a,TextView pds,TextView gd)
	{
		
		row.addView(lp);
		row.addView(name);
		row.addView(p);
		row.addView(w);
		row.addView(d);
		row.addView(l);
		row.addView(f);
		row.addView(a);
		row.addView(pds);
		row.addView(gd);
		
		table.addView(row);
	}

	
	@Override
	public void onClick(View v) 	{
		if(v == buttonHeader)
		{	
			Intent intent = new Intent(this, StarSoccer_Hot.class);
			startActivity(intent);
	    	finish();
		}
		else if(v == leaguetable_image_menu)
		{
			leaguetable_image_menu.setImageResource(R.drawable.btn_menu_down);
			Intent intent = new Intent(this, StarSoccer_Menu.class);
			startActivity(intent);
	    	finish();
		}
		else if( v == btnLeague)
		{
			Intent intent = new Intent(this, StarSoccer_MenuLeague.class);
			startActivity(intent);
		}
		
	}

	@Override
	public void onReceiveDataStream(String loadName, String url, String strOutput) {
		try {


			if(xmlPar.status.equals("success") && xmlPar.message.trim().equals("") )
			{
				vData = xmlPar.vData;

				final String text = xmlPar.date;
				final String leagueName = xmlPar.tmName;
				final String remark = xmlPar.remark;

				textHeader.setText(text);
				textLeague.setText(leagueName);
				star.setText(remark);

				TableRow row = null;
				TextView lp = null;
				TextView name = null;
				TextView p = null;
				TextView w = null;
				TextView d = null;
				TextView l = null;
				TextView f = null;
				TextView a = null;
				TextView pds = null;
				TextView gd = null;
				int place = 0;
				int placeTemp = 0;
				table.removeAllViews();

				for(int i = 0; i < vData.size(); i++)
				{
					row = new TableRow(this);
					if((i%2) == 0)
					{
						row.setBackgroundColor(Color.rgb(142, 142, 142));
					}
					else
					{
						row.setBackgroundColor(Color.rgb(60, 60, 60));
					}

					lp = new TextView(this);
					name = new TextView(this);
					p = new TextView(this);
					w = new TextView(this);
					d = new TextView(this);
					l = new TextView(this);
					f = new TextView(this);
					a = new TextView(this);
					pds = new TextView(this);
					gd = new TextView(this);

					lp.setWidth((int)(38*imgUtil.scaleSize()));
					name.setWidth((int)(118*imgUtil.scaleSize()));
					p.setWidth((int)(38*imgUtil.scaleSize()));
					w.setWidth((int)(38*imgUtil.scaleSize()));
					d.setWidth((int)(38*imgUtil.scaleSize()));
					l.setWidth((int)(38*imgUtil.scaleSize()));
					f.setWidth((int)(38*imgUtil.scaleSize()));
					a.setWidth((int)(38*imgUtil.scaleSize()));
					pds.setWidth((int)(38*imgUtil.scaleSize()));
					gd.setWidth((int)(38*imgUtil.scaleSize()));

					lp.setGravity(Gravity.CENTER);
					p.setGravity(Gravity.CENTER);
					w.setGravity(Gravity.CENTER);
					d.setGravity(Gravity.CENTER);
					l.setGravity(Gravity.CENTER);
					f.setGravity(Gravity.CENTER);
					a.setGravity(Gravity.CENTER);
					pds.setGravity(Gravity.CENTER);
					gd.setGravity(Gravity.CENTER);

					DataElementLeagueTable data = vData.elementAt(i);
					placeTemp=Integer.parseInt( data.place) ;
					if(place > placeTemp || i==0)
					{
						AddHeaderLeagueTable(row,lp,name,p,w,d,l,f,a,pds,gd);
						row = new TableRow(this);
						if((i%2) == 0)
						{
							row.setBackgroundColor(Color.rgb(142, 142, 142));
						}
						else
						{
							row.setBackgroundColor(Color.rgb(60, 60, 60));
						}

						lp = new TextView(this);
						name = new TextView(this);
						p = new TextView(this);
						w = new TextView(this);
						d = new TextView(this);
						l = new TextView(this);
						f = new TextView(this);
						a = new TextView(this);
						pds = new TextView(this);
						gd = new TextView(this);

						lp.setWidth((int)(38*imgUtil.scaleSize()));
						name.setWidth((int)(118*imgUtil.scaleSize()));
						p.setWidth((int)(38*imgUtil.scaleSize()));
						w.setWidth((int)(38*imgUtil.scaleSize()));
						d.setWidth((int)(38*imgUtil.scaleSize()));
						l.setWidth((int)(38*imgUtil.scaleSize()));
						f.setWidth((int)(38*imgUtil.scaleSize()));
						a.setWidth((int)(38*imgUtil.scaleSize()));
						pds.setWidth((int)(38*imgUtil.scaleSize()));
						gd.setWidth((int)(38*imgUtil.scaleSize()));

						lp.setGravity(Gravity.CENTER);
						p.setGravity(Gravity.CENTER);
						w.setGravity(Gravity.CENTER);
						d.setGravity(Gravity.CENTER);
						l.setGravity(Gravity.CENTER);
						f.setGravity(Gravity.CENTER);
						a.setGravity(Gravity.CENTER);
						pds.setGravity(Gravity.CENTER);
						gd.setGravity(Gravity.CENTER);

						AddDataLeagueTable(data,row,lp,name,p,w,d,l,f,a,pds,gd);
					}
					else
					{
						AddDataLeagueTable(data,row,lp,name,p,w,d,l,f,a,pds,gd);

					}

					place = Integer.parseInt( data.place) ;


				}


				Picasso.with(this).load(xmlPar.contestURLImages).into(imgLeague);

			}
			else
			{
				textLeague.setText(xmlPar.message);
				star.setText("");
				table.removeAllViews();
			}
			progress.dismiss();
		} catch (Exception e) {
			PrintLog.printException(this,"Table method onReceiveDataStream", e);
			progress.dismiss();
		}
	}

	@Override
	public void onReceiveGetDataXML(String loadName, String url, String strOutput) throws InterruptedException, SAXException {
		xmlPar = new XMLParserLeagueTable();
		Xml.parse(strOutput, xmlPar);
	}
}
