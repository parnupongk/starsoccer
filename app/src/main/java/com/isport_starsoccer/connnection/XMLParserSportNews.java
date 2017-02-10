package com.isport_starsoccer.connnection;

import com.isport_starsoccer.data.DataElementSportNews;

import java.util.Vector;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLParserSportNews extends DefaultHandler
{

	private String urlFB = "";
	private String imgDescription = "";
	private String img350 = "";
	private String img400 = "";
	private String img600 = "";
	private String img1000 = "";
	private String img190 = "";
	private String detail = "";
	private String title = "";
	private String header = "";
	private String id = "";
	
	
	public Vector<DataElementSportNews> vData = null;
	
	private boolean bMessage = false;
	private boolean bStatus = false;
	private boolean bMessageScore = false;
	private boolean bStatusScore = false;
	
	public String message = "";
	public String status = "";
	public String textHeader = "";
	public String textDate = "";
	
	public String messageScore = "";
	public String statusScore = "";
	
	private boolean bScore = false;
	public String liveScore = "";
	
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
			vData = new Vector<DataElementSportNews>();
			textHeader = atts.getValue("header");
			textDate = atts.getValue("date");
		} else if(localName.equals("News")) {
			urlFB = atts.getValue("news_url_fb");
			imgDescription = atts.getValue("news_images_description");
			img350 = atts.getValue("news_images_350");
			img400 = atts.getValue("news_images_400");
			img600 = atts.getValue("news_images_600");
			img1000 = atts.getValue("news_images_1000");
			img190 = atts.getValue("news_images_190");
			detail = atts.getValue("news_detail");
			title = atts.getValue("news_title");
			header = atts.getValue("news_header");
			id = atts.getValue("news_id");
			
			addList();
		} else if(localName.equals("status")) {
			bStatus = true;
		} else if(localName.equals("message")) {
			bMessage = true;
		} else if(localName.equals("status_score")) {
			bStatusScore = true;
		} else if(localName.equals("message_score")) {
			bMessageScore = true;
		} else if(localName.equals("Score_score")) {
			bScore = true;
		}
	}
	
	@Override
	public void endElement(String namespaceURI, String localName, String qName)
	{
		if (localName.equals("SportApp")) {
			
		} else if (localName.equals("News")) {
			
		} else if (localName.equals("status")) {
			bStatus = false;
		} else if (localName.equals("message")) {
			bMessage = false;
		} else if(localName.equals("status_score")) {
			bStatusScore = false;
		} else if(localName.equals("message_score")) {
			bMessageScore = false;
		} else if(localName.equals("Score_score")) {
			bScore = false;
		}
	}
	
	@Override
	public void characters(char ch[], int start, int length)
	{
		if (bStatus) {
			status += new String(ch, start, length);
		} else if (bMessage) {
			message += new String(ch, start, length);
		} else if (bMessageScore) {
			messageScore += new String(ch, start, length);
		} else if (bStatusScore) {
			statusScore += new String(ch, start, length);
		} else if (bScore) {
			liveScore += new String(ch, start, length);
		}
	}
	
	private void addList()
	{
		vData.add(new DataElementSportNews(urlFB, imgDescription, img350, img400, img600, img1000, img190, detail, title, header, id));
		
		urlFB = "";
		imgDescription = "";
		img350 = "";
		img400 = "";
		img600 = "";
		img1000 = "";
		img190 = "";
		detail = "";
		title = "";
		header = "";
		id = "";
	}
}
