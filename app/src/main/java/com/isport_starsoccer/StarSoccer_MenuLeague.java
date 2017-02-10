package com.isport_starsoccer;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.RelativeLayout;

import com.isport_starsoccer.data.DataSetting;
import com.isport_starsoccer.list.ListAdapterMenuLeague;


public class StarSoccer_MenuLeague extends StarSoccer_BaseClass  implements OnChildClickListener{
	
	private RelativeLayout layout = null;
	private RelativeLayout layoutCenter = null;
	private ExpandableListView list_menuleague = null;

	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    	
        super.onCreate(savedInstanceState);        
        setContentView(R.layout.sub_menuleague);
        
        //layoutCenter = (RelativeLayout)findViewById(R.id.main_layout_center);
        
        //layout = new RelativeLayout(this);
		//layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		
		//LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflater.inflate(R.layout.sub_menuleague, layout);

        list_menuleague = (ExpandableListView)findViewById(R.id.sub_menuleague_list_data);

        list_menuleague.setOnChildClickListener(this);
        //layoutCenter.addView(layout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
    
    
    @Override
    protected void onResume() {
    	try {
    		
    		super.onResume();
    		
    		if(vGroupListMenu == null)
    		{
    			Intent intent = new Intent(this, StarSoccer_Logo.class);
    			startActivity(intent);
    			this.finish();
    		}
    		else
    		{
    			ListAdapterMenuLeague adapterMenuLeague = new ListAdapterMenuLeague(this, list_menuleague, vGroupListMenu, imgUtil, "");
    			list_menuleague.setAdapter(adapterMenuLeague);
    		}
    		

    	} catch (Exception e) {
			e.printStackTrace();
		}
    }



	@Override
	public boolean onChildClick(ExpandableListView arg0, View arg1, int groupPosition,
			int childPosition, long arg4) {
		
		DataSetting.contentGroupId = vGroupListMenu.get(groupPosition).GroupLeagueCollection.get(childPosition).contestGroupId;
		this.finish();
		return false;
	}

    
}
