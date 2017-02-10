package com.isport_starsoccer.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import android.graphics.Bitmap;

public class DataElementGroupProgram implements Serializable
{
	public DataElementLeague leagueData = null;
	
	public List<DataElementProgram> GroupItemCollection ;
	
	public DataElementGroupProgram(DataElementLeague leagueData)
	{
		this.leagueData  = leagueData;
		GroupItemCollection = new ArrayList<DataElementProgram>();
	}
}
