package com.isport_starsoccer.connnection;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLParserLiveNotify extends DefaultHandler
{
	public String notification = "";
	public String status = "";
	public String message = "";

	private boolean bNotification = false;
	private boolean bStatus = false;
	private boolean bMessage = false;
	
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
			
		} else if(localName.equals("notification")) {
			bNotification = true;
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
			
		} else if (localName.equals("notification")) {
			bNotification = false;
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
		} else if (bNotification) {
			notification += new String(ch, start, length);
		}
	}
}
