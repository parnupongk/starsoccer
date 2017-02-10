package com.isport_starsoccer.connnection;

import java.util.Vector;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import android.graphics.Bitmap;

import com.isport_starsoccer.data.DataElementSMSService;

public class XMLParserSMSService extends DefaultHandler
{
	public String pssv_id = "";
	public String pssv_name = "";
	public String pssv_shortcode = "";
	public String pssv_price = "";
	public String pssv_promotion = "";
	public String pssv_desc = "";
	public String pssv_image = "";
	public String pssv_cancelcode="";
	public String pssv_remark1 = "";
	public String pssv_remark2 = "";
	public Bitmap image = null;
	
	public Vector<DataElementSMSService> vData = null;
	
	private boolean bMessage = false;
	private boolean bStatus = false;
	
	public String message = "";
	public String status = "";
	public String textHeader = "";
	public String textDate = "";
	public String wording1 = "";
	public String wording2 = "";
	
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
			vData = new Vector<DataElementSMSService>();
			textHeader = atts.getValue("header");
			textDate = atts.getValue("date");
			wording1 = atts.getValue("wording1");
			wording2 = atts.getValue("wording2");
		} else if(localName.equals("SMSService")) {
			
			pssv_id = atts.getValue("pssv_id");
			pssv_name = atts.getValue("pssv_name");
			pssv_shortcode = atts.getValue("pssv_shortcode");
			pssv_price = atts.getValue("pssv_price");
			pssv_promotion = atts.getValue("pssv_promotion");
			pssv_desc = atts.getValue("pssv_desc");
			pssv_image = atts.getValue("pssv_image");
			pssv_cancelcode = atts.getValue("pssv_cancel");
			pssv_remark1 = atts.getValue("pssv_remark1");
			pssv_remark2 = atts.getValue("pssv_remark2");
			
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
			
		} else if (localName.equals("SMSService")) {
			
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
		vData.add(new DataElementSMSService(pssv_id, pssv_name, pssv_shortcode, pssv_price, pssv_promotion,pssv_desc,pssv_image,pssv_cancelcode,pssv_remark1,pssv_remark2));
		pssv_id = "";
		pssv_name = "";
		pssv_shortcode = "";
		pssv_price = "";
		pssv_promotion = "";
		pssv_desc = "";
		pssv_image = "";
		pssv_cancelcode = "";
		pssv_remark1 = "";
		pssv_remark2 = "";
	}
}
