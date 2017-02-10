package com.isport_starsoccer.getdata;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Xml;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;

import com.isport_starsoccer.R;
import com.isport_starsoccer.StarSoccer_ScoreDetail;
import com.isport_starsoccer.connnection.XMLParserScoreResult;
import com.isport_starsoccer.data.DataElementGroupScoreResult;
import com.isport_starsoccer.data.DataSetting;
import com.isport_starsoccer.data.DataURL;
import com.isport_starsoccer.exception.PrintLog;
import com.isport_starsoccer.list.ListAdapterLiveScore;
import com.isport_starsoccer.service.AsycTaskLoadData;
import com.isport_starsoccer.service.ReceiveDataListener;

import org.xml.sax.SAXException;


public class GetDataListScore implements ReceiveDataListener,OnItemClickListener,OnChildClickListener{

	private Handler handler = null;
	private ArrayList<DataElementGroupScoreResult> mGroupData = null;
	private Context context = null;
	private ExpandableListView listView = null;
	private String contentGroupId = "";
	private String txtDate = null;
	private ProgressDialog progressD = null;
	private TextView txtHeader=null;
	private String urlRadio = null;
	private TextView txtErr = null;
	XMLParserScoreResult xmlResult=null;
	public GetDataListScore(Context context,ExpandableListView listView,String contentGroupId
			,ProgressDialog progressD,TextView txtHeader
			,String urlRadio
			,TextView txtErr)
	{
		this.context = context ;
		this.listView = listView;
		this.contentGroupId = contentGroupId;
		this.progressD = progressD;
		this.txtHeader = txtHeader;
		this.urlRadio = urlRadio;
		this.txtErr = txtErr;
		handler = new Handler();
		
	}
	
	
	public void DataBind() throws Exception
	{
		
		try
		{
			listView.setOnChildClickListener(this);
			
			mGroupData = null;
			String url = DataURL.liveScore;
			url += "&contentgroupid="+contentGroupId;
			url += "&sportType=00001";
			url += "&lang="+ DataSetting.Languge;
			url += "&imei="+DataSetting.IMEI;
			url += "&model="+DataSetting.MODEL;
			url += "&imsi="+DataSetting.IMSI;
			url += "&type="+DataSetting.TYPE;
			AsycTaskLoadData load = new AsycTaskLoadData(context, this,null,"LiveScore");
	        load.execute(url);
	        

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

			
		}
		catch(Exception ex)
		{
			//SportArena_ErrorManagement.ErrorException(context, ex.getMessage());
			//PrintLog.printException(context, "Note", ex);
		}
	}


	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		
		if( mGroupData.get(groupPosition).GroupItemCollection.size() > 0 && mGroupData.get(groupPosition).GroupItemCollection.get(childPosition).isDetail)
		{
			Intent intent = new Intent(context, StarSoccer_ScoreDetail.class);
			intent.putExtra("data", mGroupData.get(groupPosition).GroupItemCollection.get(childPosition));
			intent.putExtra("header", mGroupData.get(groupPosition).leagueData);
			context.startActivity(intent);
		}
		return false;
	}

	@Override
	public void onReceiveDataStream(String loadName, String url, String strOutput) {
		try
		{
			if(strOutput != null )
			{
				if(context != null && loadName == "LiveScore")
				{

					mGroupData = xmlResult.mGroupData;

					txtDate = xmlResult.textDate;
					if(txtHeader != null)txtHeader.setText(xmlResult.textDate);
					urlRadio = xmlResult.radioURL;
					final String message = xmlResult.message;

					if(message.trim().equals(""))
					{

						final ListAdapterLiveScore adaterResult = new ListAdapterLiveScore(context,listView,mGroupData,"");

						handler.post(new Runnable() {
							@Override
							public void run() {
								txtErr.setVisibility(View.INVISIBLE);
								listView.setVisibility(View.VISIBLE);
								listView.setAdapter(adaterResult);
								listView.expandGroup(0);


								if( progressD != null )progressD.dismiss();
							}
						});

					}else
					{
						txtErr.setVisibility(View.VISIBLE);
						listView.setVisibility(View.INVISIBLE);
						txtErr.setText(message);
						if( progressD != null )progressD.dismiss();
					}

					//StarSoccer_BaseClass.vResultData = vResultData;
					//StarSoccer_BaseClass.hLeagueResult = hLeagueResult;
				}
			}

		}
		catch(Exception ex)
		{
			if( progressD != null )progressD.dismiss();
			PrintLog.printException(context, "Note", ex);
		}
	}

	@Override
	public void onReceiveGetDataXML(String loadName, String url, String strOutput) throws InterruptedException, SAXException {
		xmlResult = new XMLParserScoreResult();
		Xml.parse(strOutput, xmlResult);
	}
}
