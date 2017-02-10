package com.isport_starsoccer.connnection;

import com.isport_starsoccer.data.DataElementGroupProgram;
import com.isport_starsoccer.data.DataElementLeague;
import com.isport_starsoccer.data.DataElementProgram;

import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLParserMatchProgram extends DefaultHandler
{
	private String sTmName = "";
	private String sContestURLImages = "";
	private String sContestGroupId = "";
	private String sContestGroupName = "";
	private String sTmSystem = "";
	
	private String sIsDetail = "";
	private String sMatchTime = "";
	private String sMatchDate = "";
	private String sLiveChannel = "";
	private String sName1 = "";
	private String sName2 = "";
	private String sTeamCode1 = "";
	private String sTeamCode2 = "";
	private String sMschId = "";
	private String sMatchId = "";
	private String analyse = "";
	private String trend="";
	private boolean bSport = false;
	private boolean bMatch = false;
	private boolean bMessage = false;
	private boolean bStatus = false;
	
	public String status = "";
	public String message = "";
	public String textHeader = "";
	public String textDate = "";
	
	public ArrayList<DataElementGroupProgram> groupProgram = null;
	public DataElementGroupProgram program = null;
	
	private int column = 0;

	@Override
	public void startDocument() throws SAXException
	{}
	
	@Override
	public void endDocument() throws SAXException
	{}
	
	@Override
	public void startElement(String namespaceURI, String localName,String qName, Attributes atts) throws SAXException 
	{
		if (localName.equals("SportApp"))
		{
			bSport = true;

			groupProgram = new ArrayList<DataElementGroupProgram>();
			
			textHeader = atts.getValue("header");
			textDate = atts.getValue("date");
		}
		else if(localName.equals("League"))
		{
			sTmName = atts.getValue("tmName");
			sContestURLImages = atts.getValue("contestURLImages");
			sContestGroupId = atts.getValue("contestGroupId");
			sContestGroupName = atts.getValue("contestGroupName");
			sTmSystem = atts.getValue("tmSystem");
			
			addLeague();
			
		}
		else if(localName.equals("Match"))
		{
			bMatch = true;
			sMschId = atts.getValue("mschId");
			sMatchId = atts.getValue("matchId");
			sTeamCode1 = atts.getValue("teamCode1");
			sTeamCode2 = atts.getValue("teamCode2");
			sName1 = atts.getValue("teamName1");
			sName2 = atts.getValue("teamName2");
			sLiveChannel = atts.getValue("liveChannel");
			sMatchDate = atts.getValue("matchDate");
			sMatchTime = atts.getValue("matchTime");
			sIsDetail = atts.getValue("isDetail");
			this.analyse = atts.getValue("analyse");
			this.trend = atts.getValue("trend");
		}
		else if(localName.equals("status"))
		{
			bStatus = true;
		}
		else if(localName.equals("message"))
		{
			bMessage = true;
		}
		
	}
	
	@Override
	public void endElement(String namespaceURI, String localName, String qName)
	{
		if (localName.equals("SportApp")) {
			bSport = false;
		} else if (localName.equals("Match")) {
			bMatch = false;
			
			addList();
		} else if (localName.equals("League")) {
			sTmName = "";
			sContestURLImages = "";
			sContestGroupId = "";
			sContestGroupName = "";
			sTmSystem = "";
		} else if(localName.equals("status")) {
			bStatus = false;
		} else if(localName.equals("message")) {
			bMessage = false;
		}
	}
	
	@Override
	public void characters(char ch[], int start, int length)
	{
		if (bStatus) {
			status += new String(ch, start, length);
		} else if (bMessage) {
			message += new String(ch, start, length);
		}
	}
	
	private void addLeague()
	{
		
		
		
		program = new DataElementGroupProgram(new DataElementLeague(sTmName, sContestURLImages, sContestGroupId, sContestGroupName, sTmSystem));
		
		//hData.put(sContestGroupId, new DataElementLeague(sTmName, sContestURLImages, sContestGroupId, sContestGroupName, sTmSystem));
		//vData.add(new DataElementMatchProgram(sContestGroupId, "", "", "", "", "", "", "", "", "", "", -1));
		if( program != null ) groupProgram.add(program);
		column = 0 ;
	}
	
	private void addList()
	{
		column++;
		program.GroupItemCollection.add(new DataElementProgram(sContestGroupId, sMschId, sMatchId, sTeamCode1, sTeamCode2
				, sName1, sName2, sLiveChannel, sMatchDate, sMatchTime, sIsDetail, column%2,this.analyse,trend));
		
		//vData.add(new DataElementMatchProgram(sContestGroupId, sMschId, sMatchId, sTeamCode1, sTeamCode2, sName1, sName2, sLiveChannel, sMatchDate, sMatchTime, sIsDetail, column%2));

		sMschId = "";
		sMatchId = "";
		sTeamCode1 = "";
		sTeamCode2 = "";
		sName1= "";
		sName2 = "";
		sLiveChannel = "";
		sMatchDate = "";
		sMatchTime = "";
		sIsDetail = "";
		this.analyse = "";
		this.trend="";
	}
}
