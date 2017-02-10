package com.isport_starsoccer.connnection;

import com.isport_starsoccer.data.DataElementSportClip;

import java.util.Vector;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLParserSportClip extends DefaultHandler
{
	public String clipId = "";
	public String clipTopic = "";
	public String clipImage = "";
	public String clipURL = "";
	public String clipDate = "";
	
	public Vector<DataElementSportClip> vData = null;
	
	private boolean bMessage = false;
	private boolean bStatus = false;
	
	public String message = "";
	public String status = "";
	public String textHeader = "";
	public String textDate = "";
	
	@Override
	public void startDocument() throws SAXException
	{}
	
	@Override
	public void endDocument() throws SAXException
	{}
	
	@Override
	public void startElement(String namespaceURI, String localName,String qName, Attributes atts) throws SAXException 
	{
		if (localName.equals("SportApp")) {
			vData = new Vector<DataElementSportClip>();
			textHeader = atts.getValue("header");
			textDate = atts.getValue("date");
		} else if(localName.equals("Clip")) {
			clipId = atts.getValue("clip_id");
			clipTopic = atts.getValue("clip_topic");
			clipImage = atts.getValue("clip_images");
			clipURL = atts.getValue("clip_url");
			clipDate = atts.getValue("clip_date");
			
			addList();
		} else if(localName.equals("status")) {
			bStatus = true;
		} else if(localName.equals("message")) {
			bMessage = true;
		} 
	}
	
	@Override
	public void endElement(String namespaceURI, String localName, String qName)
	{
		if (localName.equals("SportApp")) {
			
		} else if (localName.equals("Clip")) {
			
		} else if (localName.equals("status")) {
			bStatus = false;
		} else if (localName.equals("message")) {
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
	
	private void addList()
	{
		vData.add(new DataElementSportClip(clipId, clipTopic, clipImage, clipURL, clipDate));
		clipId = "";
		clipTopic = "";
		clipImage = "";
		clipURL = "";
		clipDate = "";
	}
}
