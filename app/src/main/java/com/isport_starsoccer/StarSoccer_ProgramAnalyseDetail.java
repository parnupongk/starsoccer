package com.isport_starsoccer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.isport_starsoccer.data.DataElementGroupProgram;
import com.isport_starsoccer.service.ImageUtil;
import com.squareup.picasso.Picasso;

public class StarSoccer_ProgramAnalyseDetail extends Activity implements  OnClickListener
{
	private RelativeLayout layout = null;
	private ProgressDialog progress = null;

	private ImageView imgLeagueName = null;
	private TextView textLeagueName = null;
	
	private TableLayout layoutResult = null;
	private TextView team1 = null;
	private TextView score = null;
	private TextView time = null;
	private TextView team2 = null;
	private ImageView viewClose = null;
	private TextView t1 = null;
	private TextView t2 = null;
	private TextView trendH = null;
	private TextView trendD = null;
	
	private DataElementGroupProgram data = null;
	private ImageUtil imgUtil = null;
	private int index = 0;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sub_analyse_detail);

		imgUtil = new ImageUtil(this);
//		progress = ProgressDialog.show(this, null, "Loading...", true, true);
		
        
        //imgHeader.setImageResource(R.drawable.hd_sub_ball);
        //layoutHeader.setBackgroundResource(R.drawable.hd_sub);
        t1  = (TextView)findViewById(R.id.sub_analyse_t1);
        t2  = (TextView)findViewById(R.id.sub_analyse_t2);
        viewClose = (ImageView) findViewById(R.id.sub_analyse_detail_img_close);
        imgLeagueName = (ImageView) findViewById(R.id.sub_analyse_detail_img_leagueName);
        textLeagueName = (TextView) findViewById(R.id.sub_analyse_detail_text_leagueName);

        viewClose.setOnClickListener(this);
        //layoutLeagueName.setBackgroundResource(R.drawable.tab02);
        imgLeagueName.setPadding((int)(10*imgUtil.scaleSize()), 0, (int)(5*imgUtil.scaleSize()), 0);
        
        layoutResult = (TableLayout) findViewById(R.id.sub_analyse_detail_layout_result_score);
        team1 = (TextView) findViewById(R.id.sub_analyse_detail_result_team1);
        team2 = (TextView) findViewById(R.id.sub_analyse_detail_result_team2);
        score = (TextView) findViewById(R.id.sub_analyse_detail_result_score);
        time = (TextView) findViewById(R.id.sub_analyse_detail_result_time);
        
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
        
        trendH = (TextView)findViewById(R.id.sub_analyse_trend_h);
        trendD = (TextView)findViewById(R.id.sub_analyse_trend_d);
        
        
 
        if(getIntent().getExtras() != null)
        {
        	Bundle b = getIntent().getExtras();
        	index = b.getInt("index");
        	data = (DataElementGroupProgram) b.get("data");

			Picasso.with(this).load(data.leagueData.contestURLImages).into(imgLeagueName);
            
            textLeagueName.setText(data.leagueData.contestGroupName);
            textLeagueName.setTextColor(Color.WHITE);
            
            team1.setText(data.GroupItemCollection.get(index).name1);
            team2.setText(data.GroupItemCollection.get(index).name2);
            score.setText(" VS ");
            time.setText(data.GroupItemCollection.get(index).matchTime);
        
            t1.setText("ความน่าจะเป็นของเกม");
            t2.setText(data.GroupItemCollection.get(index).analyse);
	        team1.setTextColor(Color.YELLOW);
	        team2.setTextColor(Color.YELLOW);
	        score.setTextColor(Color.WHITE);
	        time.setTextColor(Color.WHITE);
	        
	        trendH.setText("ผลที่คาด");
            trendD.setText(data.GroupItemCollection.get(index).trend);
	        
        }
	}
	

	@Override
	public void onResume()
	{
		super.onResume();
		
		//progress = ProgressDialog.show(this, null, "Loading...", true, true);
		
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String message = "";
		if(v == viewClose)
		{
			this.finish();
		}

	}
}
