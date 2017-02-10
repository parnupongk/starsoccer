package com.isport_starsoccer.data;

import java.io.Serializable;

public class DataElementProgram implements Serializable
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
	public String analyse = "";
	public String groupId = "";
	public String trend = "";
	public int column = 0;
	
	public DataElementProgram(String sContestGroupId, String sMschId, String sMatchId, 
			String sTeamCode1, String sTeamCode2, String sName1, String sName2,
			String sLiveChannel, String sMatchDate, String sMatchTime, String sIsDetail
			, int column,String analyse,String trend)
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
		this.trend = trend;
		
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
