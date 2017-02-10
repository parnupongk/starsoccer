package com.isport_starsoccer.getdata;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Random;
import java.util.Vector;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Xml;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.isport_starsoccer.StarSoccer_BaseClass;
import com.isport_starsoccer.StarSoccer_NewsDetail;
import com.isport_starsoccer.connnection.XMLParserSportNews;
import com.isport_starsoccer.data.DataElementSportNews;
import com.isport_starsoccer.data.DataSetting;
import com.isport_starsoccer.data.DataURL;
import com.isport_starsoccer.exception.PrintLog;
import com.isport_starsoccer.list.ListAdapterNewsMain;
import com.isport_starsoccer.service.AsycTaskLoadData;
import com.isport_starsoccer.service.ImageUtil;
import com.isport_starsoccer.service.ReceiveDataListener;
import com.squareup.picasso.Picasso;

import org.xml.sax.SAXException;

public class GetDataListNews implements  Runnable,ReceiveDataListener,OnItemClickListener{

	private Handler handler = null;
	private Vector<DataElementSportNews> vDataNews = null;
	private Context context = null;
	private Gallery listGallery = null;
	private ImageUtil imgUtil = null;
	private String contentGroupId = "";
	private String headerText = "";
	private ListAdapterNewsMain adapterNews = null;
	private ImageView imgView=null;
	private ProgressDialog pd = null; 
	private int currentRowIndex = 0;
	XMLParserSportNews xmlPar =null;
	private Handler thread = null;
	
	public GetDataListNews(Context context, Vector<DataElementSportNews> vDataNews,ImageUtil imgUtil
			,Gallery listGallery,String contentGroupId,ProgressDialog progressD)
	{
		this.context = context ;
		this.vDataNews = vDataNews;
		this.imgUtil = imgUtil;
		this.listGallery = listGallery;
		this.contentGroupId = contentGroupId;
		this.pd = progressD;
		handler = new Handler();
	}
	
	public void DataBindSportNews(String sportType) throws Exception
	{
		try
		{
			listGallery.setOnItemClickListener(this);
			
			thread = new Handler();
			String url = DataURL.sportNews;
			
			url += "&date=";
			url += "&sportType=" + sportType;
			url += "&rowcount=";
			url += "&lang="+ DataSetting.Languge;
			url += "&imei="+DataSetting.IMEI;
			url += "&model="+DataSetting.MODEL;
			url += "&imsi="+DataSetting.IMSI;
			url += "&type="+DataSetting.TYPE;
			
			if(vDataNews == null)
			{
				AsycTaskLoadData load = new AsycTaskLoadData(context , this,null,"othernews");
				load.execute(url);
			}
			else
			{
				SetAdapterNews(0);
			}
		}
		catch(Exception ex)
		{
			throw new Exception(ex.getMessage());
		}
	}
	

	private void SetAdapterNews(int rowIndex) throws Exception
	{
		try
		{
			currentRowIndex = rowIndex;
			adapterNews = new ListAdapterNewsMain(context, vDataNews.elementAt(rowIndex), imgUtil,1);
			handler.post(new Runnable() {
				@Override
				public void run() {
						listGallery.setAdapter(adapterNews);
						pd.dismiss();
				}
			});
			
			thread.postDelayed(this, 9000L);
		}
		catch(Exception ex)
		{
			throw new Exception(ex.getMessage());
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> view, View arg1, int position, long arg3) {
		try
		{
			
			if(view == listGallery)
				{

					Intent intent = new Intent(context, StarSoccer_NewsDetail.class);
					intent.putExtra("data", vDataNews.elementAt(currentRowIndex));
					intent.putExtra("header", headerText);
					context.startActivity(intent);
					
				}
		}
		catch(Exception ex)
		{
			PrintLog.printException(context, "", ex);
		}
	}

	@Override
	public void run() {
		try 
		{
			int rowIndex = new Random().nextInt(vDataNews.size());
			SetAdapterNews(rowIndex);
			
		} catch (Exception e) {
			PrintLog.printException(context, "", e);
		}
	}

	@Override
	public void onReceiveDataStream(String loadName, String url, String strOutput) {
		try
		{
			if( strOutput != null )
			{

				if(xmlPar.status.equals("success") && xmlPar.message.equals(""))
				{
					vDataNews = xmlPar.vData;
					StarSoccer_BaseClass.vDataNews = vDataNews;
					headerText = xmlPar.textHeader+" "+xmlPar.textDate;

/*
					if(imgView != null)
					{
						AsycTaskLoadImage loadImage = new AsycTaskLoadImage(null, null, null,this );
						loadImage.execute(vDataNews.get(0).img600);
					}*/

					SetAdapterNews(0);
				}
				else
				{
					//Toast.makeText(context, xmlPar.message, Toast.LENGTH_LONG).show();
					pd.dismiss();
				}
			}

		}
		catch(Exception ex)
		{
			PrintLog.printException(context, "Note", ex);
		}
	}

	@Override
	public void onReceiveGetDataXML(String loadName, String url, String strOutput) throws InterruptedException, SAXException {
		xmlPar = new XMLParserSportNews();
		Xml.parse(strOutput, xmlPar);
	}
}
