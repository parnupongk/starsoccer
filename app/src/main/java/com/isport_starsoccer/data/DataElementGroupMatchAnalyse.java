package com.isport_starsoccer.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import android.graphics.Bitmap;

public class DataElementGroupMatchAnalyse implements Serializable
{
	public DataElementLeague leagueData = null;
	
	public List<DataElementMatchAnalyse> GroupItemCollection ;
	
	public DataElementGroupMatchAnalyse(DataElementLeague leagueData)
	{
		this.leagueData  = leagueData;
		GroupItemCollection = new ArrayList<DataElementMatchAnalyse>();
	}
}
