package com.isport_starsoccer;

import java.io.InputStream;
import java.util.Vector;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.MotionEventCompat;
import android.util.Xml;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

import com.isport_starsoccer.connnection.XMLParserScoreDetail;
import com.isport_starsoccer.data.DataElementLeague;
import com.isport_starsoccer.data.DataElementScore;
import com.isport_starsoccer.data.DataElementScoreDetailGame;
import com.isport_starsoccer.data.DataElementScoreDetailScore;
import com.isport_starsoccer.data.DataSetting;
import com.isport_starsoccer.data.DataURL;
import com.isport_starsoccer.exception.PrintLog;
import com.isport_starsoccer.list.ListAdapterScoreDetailGame;
import com.isport_starsoccer.service.AsycTaskLoadData;
import com.isport_starsoccer.service.ImageUtil;
import com.isport_starsoccer.service.ReceiveDataListener;
import com.isport_starsoccer.service.Share;
import com.squareup.picasso.Picasso;

import org.xml.sax.SAXException;

public class StarSoccer_ScoreDetail extends Activity implements ReceiveDataListener, Runnable, OnClickListener
{
	private RelativeLayout layout = null;
	private Handler handler = null;
	private ProgressDialog progress = null;
	
	private DataElementLeague dataLeague = null;
	private DataElementScore data = null;
	private ImageView imgLeagueName = null;
	private TextView textLeagueName = null;
	
	private TableLayout layoutResult = null;
	private TextView team1 = null;
	private TextView score = null;
	private TextView time = null;
	private TextView team2 = null;
	private ImageView viewClose = null;
	private ImageView shareFB = null;
	private ImageView shareTwit = null;
	
	private String URL = "";
	private XMLParserScoreDetail xmlPar=null;
	private AsycTaskLoadData load = null;
	
	private Vector<DataElementScoreDetailGame> vGame = null;
	private DataElementScoreDetailScore dataScore = null;
	private ListAdapterScoreDetailGame adapterGame = null;
	private ListView listGame = null;
	private ImageUtil imgUtil = null;

	private int index = 0;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sub_score_detail);

		handler = new Handler();
		imgUtil = new ImageUtil(this);
//		progress = ProgressDialog.show(this, null, "Loading...", true, true);
		
        
        //imgHeader.setImageResource(R.drawable.hd_sub_ball);
        //layoutHeader.setBackgroundResource(R.drawable.hd_sub);
        
        viewClose = (ImageView) findViewById(R.id.sub_score_detail_img_close);
        imgLeagueName = (ImageView) findViewById(R.id.sub_score_detail_img_leagueName);
        textLeagueName = (TextView) findViewById(R.id.sub_score_detail_text_leagueName);
        listGame = (ListView)findViewById(R.id.sub_score_detail_list_game);
       
        shareFB = (ImageView) findViewById(R.id.livescore_button_fb); 
        shareTwit = (ImageView) findViewById(R.id.livescore_button_twit);
        
        shareFB.setImageResource(R.drawable.btn_face);
        shareTwit.setImageResource(R.drawable.btn_twit);
        
        viewClose.setOnClickListener(this);
        shareFB.setOnClickListener(this);
        shareTwit.setOnClickListener(this);
        
        //layoutLeagueName.setBackgroundResource(R.drawable.tab02);
        imgLeagueName.setPadding((int)(10*imgUtil.scaleSize()), 0, (int)(5*imgUtil.scaleSize()), 0);
        
        layoutResult = (TableLayout) findViewById(R.id.sub_score_detail_layout_result_score);
        team1 = (TextView) findViewById(R.id.sub_score_detail_result_team1);
        team2 = (TextView) findViewById(R.id.sub_score_detail_result_team2);
        score = (TextView) findViewById(R.id.sub_score_detail_result_score);
        time = (TextView) findViewById(R.id.sub_score_detail_result_time);
        
        team1.setWidth((int)(183*imgUtil.scaleSize()));
        team2.setWidth((int)(183*imgUtil.scaleSize()));
        score.setWidth((int)(94*imgUtil.scaleSize()));
        time.setWidth((int)(94*imgUtil.scaleSize()));
        
        team1.setGravity(Gravity.CENTER);
        team2.setGravity(Gravity.CENTER);
        score.setGravity(Gravity.CENTER);
        time.setGravity(Gravity.CENTER);
        
        layoutResult.setBackgroundColor(Color.rgb(69, 69, 69));
        score.setBackgroundColor(Color.BLACK);
        time.setBackgroundColor(Color.BLACK);
        
 
        if(getIntent().getExtras() != null)
        {
        	Bundle b = getIntent().getExtras();
        	index = b.getInt("page");
        	
        	data = (DataElementScore) b.get("data");
        	dataLeague = (DataElementLeague) b.get("header");

			Picasso.with(this).load(dataLeague.contestURLImages).into(imgLeagueName);
            
            textLeagueName.setText(dataLeague.contestGroupName);
            textLeagueName.setTextColor(Color.WHITE);
            
            team1.setText(data.teamName1);
            team2.setText(data.teamName2);
            score.setText(data.scoreHome+"-"+data.scoreAway);
            time.setText(data.minutes);
        
	        team1.setTextColor(Color.YELLOW);
	        team2.setTextColor(Color.YELLOW);
	        score.setTextColor(Color.WHITE);
	        time.setTextColor(Color.WHITE);
        
        }
	}
	
	private void setURL()
	{
		URL = DataURL.liveScoreDetail;
        URL += "&matchId="+data.matchId;
        URL += "&mschId="+data.mschId;
        URL += "&lang="+DataSetting.Languge;
        URL += "&imei="+DataSetting.IMEI;
        URL += "&model="+DataSetting.MODEL;
        URL += "&imsi="+DataSetting.IMSI;
        URL += "&type="+DataSetting.TYPE;
        
        load = new AsycTaskLoadData(this, this,null,"ScoreDetail");
		load.execute(URL);
	}

	@Override
	public void onResume()
	{
		super.onResume();
		
		progress = ProgressDialog.show(this, null, "Loading...", true, true);
		setURL();
		
	}


	@Override
	public synchronized void run() {
		try {
			//load = new AsycTaskLoadData(this, this);
            //load.execute(URL);
            
            //thread.postDelayed(this, 60000L);
		} catch (Exception e) {
			PrintLog.printException(this,"LiveScoreDetail method run", e);
		}
	}

	@Override
	public void onClick(View v) {
		String message = "",score = "";
		if(v == viewClose)
		{
			this.finish();
		}
		else
		{
			if(dataScore.scoreType.toUpperCase().equals("FT") || dataScore.scoreType.toUpperCase().equals("HT"))
			{
				message = dataScore.scoreType;
			}
			else if(dataScore.scoreType.toUpperCase().equals("FINISHED"))
			{
				message = "FT";
			}
			else
			{
				message = "น. "+dataScore.scoreType;
			}
			
			//score = (dataScore.scoreHome.equals("") && dataScore.scoreAway.equals(""))? :;
			message += " "+dataScore.teamName1+" "+data.scoreHome+"-"+data.scoreAway+" "+dataScore.teamName2;
			
			if(v == shareFB)
			{
				//Share.shareFB(this, dataScore.shareURL, message, "", dataScore.shareURL);
				Share.shareFB(this, DataSetting.URLPLAYSOTRE, message, "", "http://wap.isport.co.th/isportws/images/ic_launcher.png");
			}
			else if(v == shareTwit)
			{
				/*String game = "";
				DataElementScoreDetailGame data = null;
	
				for(int i = 0; i < vGame.size(); i++)
				{
					data = vGame.elementAt(i);
					game += data.minute+" "+data.evenType+" "+data.playerName+" "+data.teamName;
				}*/
				
				//Share.sendEmail(this, new String[]{}, message, message+game);
				shareTwit.setImageResource(R.drawable.btn_twit_act);
				Intent intent = new Intent(this, StarSoccer_Twitter.class);
				intent.putExtra("message", message);
				this.startActivity(intent);
			}	
		}
	}

	@Override
	public void onReceiveDataStream(String loadName, String url, String strOutput) {
		final Context context = this;
		try {


			if(xmlPar.status.equals("success"))
			{
				vGame = xmlPar.vGame;
				dataScore = xmlPar.dataLiveScore;
				//listPlayerTeam1 = xmlPar.vNameTeam1;
				//listPlayerTeam2 = xmlPar.vNameTeam2;

				handler.post(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						adapterGame = new ListAdapterScoreDetailGame(context, vGame, dataScore, imgUtil);
						listGame.setAdapter(adapterGame);

//						adapterPlayer = new ListAdapterLiveScoreDetailPlayerName(context, listPlayerTeam1, listPlayerTeam2, imgUtil);
//						listName.setAdapter(adapterPlayer);
					}
				});
			}

			progress.dismiss();
		} catch (Exception e) {
			// TODO: handle exception
			PrintLog.printException(this,"LiveScoreDetail Note", e);
			progress.dismiss();
		}
	}

	@Override
	public void onReceiveGetDataXML(String loadName, String url, String strOutput) throws InterruptedException, SAXException {
		xmlPar = new XMLParserScoreDetail();
		Xml.parse(strOutput, xmlPar);
	}
}
