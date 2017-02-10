package com.isport_starsoccer.connnection;

import com.isport_starsoccer.data.DataElementHot;

import java.util.Vector;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLParserHot extends DefaultHandler
{
	private String img1 = "";
	private String img2 = "";
	private String img3 = "";
	private String description = "";
	private String phone = "";
	private String url = "";
	
	private boolean bMessage = false;
	private boolean bStatus = false;
	
	public String message = "";
	public String status = "";
	public String header = "";
	public String date = "";
	
	public Vector<DataElementHot> vData = null;
	
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
			vData = new Vector<DataElementHot>();
			
			header = atts.getValue("header");
			date = atts.getValue("date");
		} else if(localName.equals("Privilege")) {
			img1 = atts.getValue("image1");
			img2 = atts.getValue("image2");
			img3 = atts.getValue("image3");
			description = atts.getValue("message");
			phone = atts.getValue("phone");
			url = atts.getValue("url");
		}  else if(localName.equals("status")) {
			bStatus = true;
		} else if(localName.equals("message")) {
			bMessage = true;
		}
	}
	
	@Override
	public void endElement(String namespaceURI, String localName, String qName)
	{
		if (localName.equals("SportApp")) {
			
		} else if (localName.equals("Privilege")) {
			addData();
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
	
	private void addData()
	{
		vData.add(new DataElementHot(img1, img2, img3, description, phone, url));
		
		img1 = "";
		img2 = "";
		img3 = "";
		description = "";
		phone = "";
		url = "";
	}
}
