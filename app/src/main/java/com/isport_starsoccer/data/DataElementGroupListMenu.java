package com.isport_starsoccer.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import android.graphics.Bitmap;

public class DataElementGroupListMenu implements Serializable
{
	public DataElementListMenu countryData = null;
	
	public List<DataElementListMenu> GroupLeagueCollection ;
	
	public DataElementGroupListMenu(DataElementListMenu countryData)
	{
		this.countryData  = countryData;
		GroupLeagueCollection = new ArrayList<DataElementListMenu>();
	}
}
