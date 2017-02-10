package com.isport_starsoccer.connnection;

import android.util.Log;

import com.isport_starsoccer.data.DataElementGroupListMenu;
import com.isport_starsoccer.data.DataElementListMenu;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


public class XMLParserListMenu extends DefaultHandler
{
    private static final String TAG = "KissModel";
    private String iconURL_48x48 = "";
	private String iconURL_16x11 = "";
	private String sportType = "";
	private String countryName = "";
	private String countryId = "";
	
	private String iconFileName16x11 = "";
	private String iconFileName48x48 = "";
	private String contestGroupName = "";
	private String contestGroupId = "";
	
	private String id = "";
	
	public String isActive = "";
	public String isAdView = "";
	public String active_header = "";
	public String active_detail = "";
	public String active_footer = "";
	public String active_footer1 = "";
	public String active_otpwait = "";
	
	private boolean bMessage = false;
	private boolean bStatus = false;
	
	public String serverversion = "";
	public String message = "";
	public String status = "";
	public String header = "";
	public String date = "";
	public String urlIcon = "";
	public String urlBanner = "";
	public String ivrNo = "";
	public String otpCode="";

	public DataElementGroupListMenu vListMenu=null;
	public ArrayList<DataElementGroupListMenu> vGroupListMenu=null;

	
	@Override
	public void startDocument() throws SAXException
	{}
	
	@Override
	public void endDocument() throws SAXException
	{}

    public void get(InputStream inputStream) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser mSaxParser = factory.newSAXParser();
            XMLReader mXmlReader = mSaxParser.getXMLReader();
            mXmlReader.setContentHandler(this);
            mXmlReader.parse(new InputSource(inputStream));
        } catch(Exception e) {
            // Exceptions can be handled for different types
            // But, this is about XML Parsing not about Exception Handling
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

	@Override
	public void startElement(String namespaceURI, String localName,String qName, Attributes atts) throws SAXException 
	{
        try {


        if (localName.equals("SportApp")) {
			header = atts.getValue("header");
			date = atts.getValue("date");
			serverversion = atts.getValue("version");
			isAdView = atts.getValue("adview");
			urlIcon = atts.getValue("urlicon");
			urlBanner = atts.getValue("urlbanner");
			ivrNo = atts.getValue("IVR");
			otpCode = atts.getValue("OPTCode");
			vGroupListMenu = new ArrayList<DataElementGroupListMenu>();
			//vListMenu = new DataElementGroupListMenu(countryData)

		}
		else if(localName.equals("Active"))
		{
			isActive = atts.getValue("isactive");
			active_header = atts.getValue("header");
			active_detail = atts.getValue("detail");
			active_footer = atts.getValue("footer");
			active_footer1 = atts.getValue("footer1");
			active_otpwait = atts.getValue("otp_status");
		}
		else if(localName.equals("Country"))
		{
			iconURL_48x48 = atts.getValue("iconURL_48x48");
			iconURL_16x11 = atts.getValue("iconURL_16x11");
			sportType = atts.getValue("sportType");
			countryName = atts.getValue("countryName");
			countryId = atts.getValue("countryId");
			id = atts.getValue("id");
			iconFileName16x11 = iconURL_16x11+atts.getValue("iconFileName16x11");
			iconFileName48x48 = iconURL_48x48+atts.getValue("iconFileName48x48");
			
			NewCountry();

		}
		else if(localName.equals("League"))
		{
			if(atts.getValue("iconFileName16x11") != null)
			{
				iconFileName16x11 = iconURL_16x11+atts.getValue("iconFileName16x11");
				iconFileName48x48 = iconURL_48x48+atts.getValue("iconFileName48x48");
			}
			contestGroupName = atts.getValue("contestGroupName");
			contestGroupId = atts.getValue("contestGroupId");
			addLeagueByCountry();
			
			
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
        catch(Exception ex){}
	}
	
	@Override
	public void endElement(String namespaceURI, String localName, String qName)
	{
        try {


        if (localName.equals("SportApp")) {
			
		} else if (localName.equals("Country")) {
			iconURL_48x48 = "";
			iconURL_16x11 = "";
			AddCountry();
		} else if (localName.equals("League")) {
			
		} else if (localName.equals("status")) {
			bStatus = false;
		} else if (localName.equals("message")) {
			bMessage = false;
		}
        }
        catch(Exception ex){}
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
	
	
	private void addLeagueByCountry()
	{
		DataElementListMenu data = new DataElementListMenu(countryId,countryName, contestGroupName, contestGroupId, iconFileName16x11, iconFileName48x48);
		
		vListMenu.GroupLeagueCollection.add(data);
		
		contestGroupName = "";
		contestGroupId = "";
	}
	

	private void AddCountry()
	{
		if( vListMenu != null ) vGroupListMenu.add(vListMenu);
	}
	private void NewCountry()
	{
		DataElementListMenu data = new DataElementListMenu(countryId, countryName, "", iconFileName16x11, iconFileName48x48);
		vListMenu = new DataElementGroupListMenu(data);
	
		sportType = "";
		id = "";
	}
	


}