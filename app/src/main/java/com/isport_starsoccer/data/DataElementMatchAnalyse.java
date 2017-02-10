package com.isport_starsoccer.data;

import java.io.Serializable;

public class DataElementMatchAnalyse implements Serializable
{
	public String mschId = "";
	public String matchId = "";
	public String teamCode1 = "";
	public String teamCode2 = "";
	public String name1 = "";
	public String name2 = "";
	public String liveChannel = "";
	public String matchDate = "";
	public String matchTime = "";
	public boolean isDetail = false;
	public String analyse = null;
	public String groupId = "";
	public int column = 0;
	public String trend=null;
	
	public DataElementMatchAnalyse(String sContestGroupId, String sMschId, String sMatchId, 
			String sTeamCode1, String sTeamCode2, String sName1, String sName2,
			String sLiveChannel, String sMatchDate, String sMatchTime, String sIsDetail
			, String analyse,int column,String trend)
	{
		groupId = sContestGroupId;
		mschId = sMschId;
		matchId = sMatchId;
		teamCode1 = sTeamCode1;
		teamCode2 = sTeamCode2;
		name1 = sName1;
		name2 = sName2;
		liveChannel = sLiveChannel;
		matchDate = sMatchDate;
		matchTime = sMatchTime;
		this.analyse = analyse;
		this.column = column;
		this.trend  = trend;
		
		if(sIsDetail.equals("false"))
		{
			isDetail = false;
		}
		else
		{
			isDetail = true;
		}
		
	}
}
