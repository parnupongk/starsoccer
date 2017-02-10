package com.isport_starsoccer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.squareup.picasso.Picasso;

public class StarSoccer_RelativeHeader extends RelativeLayout implements OnClickListener
{
	private Context context = null;
	private ImageView home = null;
	private ImageView logoStarSoccer = null;
	private ImageView logo3gx = null;
	private ImageView info = null;
	private ImageView clip = null;
	
	
	private Activity activity = null;
	
	private String urlIcon = "";
	
	public StarSoccer_RelativeHeader(Context context, Activity activity,String urlIcon ) {
		super(context);
		this.activity = activity;
		this.context = context;
		this.urlIcon = urlIcon;
		init();
	}
	
	private void init()
	{
		
		LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.relative_header, this);
        
		home = (ImageView) findViewById(R.id.header_button_home);
		logoStarSoccer = (ImageView) findViewById(R.id.header_button_logo_starsoccer);
		logo3gx = (ImageView) findViewById(R.id.header_button_logo_3gx);
		info = (ImageView) findViewById(R.id.header_button_info);
		clip = (ImageView) findViewById(R.id.header_button_clip);
		home.setImageResource(R.drawable.btn_home);
		logoStarSoccer.setImageResource(R.drawable.header_logo_starsoccer);

		Picasso.with(context).load(urlIcon).into(logo3gx);
		
		home.setOnClickListener(this);
		logoStarSoccer.setOnClickListener(this);
		logo3gx.setOnClickListener(this);
		info.setOnClickListener(this);
		clip.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		if(v == home)
		{
			Intent intent = new Intent(context, StarSoccer_Main.class);

			//intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

			activity.startActivity(intent);
			//activity.overridePendingTransition(R.anim.activity_close_translate, R.anim.activity_open_translate);
			activity.finish();

		}
		
	}

}
