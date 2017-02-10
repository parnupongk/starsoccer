package com.isport_starsoccer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.json.JSONObject;
import org.xml.sax.SAXException;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.isport_starsoccer.data.DataSetting;
import com.isport_starsoccer.data.DataURL;
import com.isport_starsoccer.exception.PrintLog;
import com.isport_starsoccer.service.AsycTaskLoadData;
import com.isport_starsoccer.service.ReceiveDataListener;


public class StarSoccer_Active extends StarSoccer_BaseClass implements ReceiveDataListener,OnClickListener {
	
	private TextView txtHeader = null;
	private TextView txtDetail = null;
	private TextView txtFooter = null;
	private TextView txtFooter1 = null;
	private EditText txtNumber = null;
	private Button btnSubmit = null;
	private RelativeLayout layout = null;
	private ProgressDialog progress = null;
	private boolean isGenOTP = true;
	private boolean isSubmitOTP = false;
	private String msisdn = "";
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
    	
        super.onCreate(savedInstanceState);        
        setContentView(R.layout.activity_starsoccer_main);
        
        
        layoutCenter = (RelativeLayout)findViewById(R.id.main_layout_center);
        
        layout = new RelativeLayout(this);
		layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.page_active, layout);

        txtHeader = (TextView)layout.findViewById(R.id.active_txt_header);
        txtDetail = (TextView)layout.findViewById(R.id.active_txt_detail);
        txtFooter = (TextView)layout.findViewById(R.id.active_txt_footer);
        txtFooter1 = (TextView)layout.findViewById(R.id.active_txt_footer1);
        btnSubmit = (Button)layout.findViewById(R.id.active_submit);
        txtNumber = (EditText)layout.findViewById(R.id.active_txt_mobile);
        
        
        btnSubmit.setOnClickListener(this);
        
        txtHeader.setText(ACTIVE_HEADER);
		txtDetail.setText(ACTIVE_DETAIL);
		txtFooter.setText(ACTIVE_FOOTER);
		txtFooter1.setText(ACTIVE_FOOTER1);
		
		if( ACTIVE_OTPWAIT.equals("new") )
		{
			isGenOTP = true;
			isSubmitOTP = false;
		}
		else
		{
			isGenOTP = false;
			isSubmitOTP = true;
		}
		
		layoutCenter.addView(layout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
    
    private void GetOTP() throws Exception
    {
    	try
    	{
    		
    		progress = ProgressDialog.show(this, null, "Loading...", true, true);
			String url = DataURL.GetOTP;
			url += "&ano="+ txtNumber.getText();
			url += "&lang="+DataSetting.Languge;
			url += "&imei="+DataSetting.IMEI;
			url += "&model="+DataSetting.MODEL;
			url += "&imsi="+DataSetting.IMSI;
			url += "&type="+DataSetting.TYPE;
			AsycTaskLoadData load = new AsycTaskLoadData(this, this,null,"genOTP");
	        load.execute(url);
	        
    	}
    	catch(Exception ex)
    	{
    		throw new Exception(ex.getMessage());
    	}
    }
    private void SubmitOTP() throws Exception
    {
    	try
    	{
    		
    		progress = ProgressDialog.show(this, null, "Loading...", true, true);
			String url = DataURL.SubmitOTP;
			url += "&otp="+ txtNumber.getText();
			url += "&ano="+ msisdn;
			url += "&lang="+ DataSetting.Languge;
			url += "&imei="+DataSetting.IMEI;
			url += "&model="+DataSetting.MODEL;
			url += "&imsi="+DataSetting.IMSI;
			url += "&type="+DataSetting.TYPE;
			AsycTaskLoadData load = new AsycTaskLoadData(this, this,null,"submitOTP");
	        load.execute(url);
	        
    	}
    	catch(Exception ex)
    	{
    		throw new Exception(ex.getMessage());
    	}
    }
    
    @Override
    protected void onResume() {
    	try {
    		
    		super.onResume();
    		
    		if( ACTIVE_HEADER.equals("") )
    		{
    			Intent intent = new Intent(this, StarSoccer_Logo.class);
    			startActivity(intent);
    			finish();
    		}

    	} catch (Exception e) {
			e.printStackTrace();
		}
    }

	private String readStream(InputStream iStream) throws IOException {
        //build a Stream Reader, it can read char by char
        InputStreamReader iStreamReader = new InputStreamReader(iStream);
        //build a buffered Reader, so that i can read whole line at once
        BufferedReader bReader = new BufferedReader(iStreamReader);
        String line = null;
        StringBuilder builder = new StringBuilder();
        while((line = bReader.readLine()) != null) {  //Read till end
            builder.append(line);
        }
        bReader.close();         //close all opened stuff
        iStreamReader.close();
        iStream.close();
        return builder.toString();
    }


	@Override
	public void onClick(View v) {
		try
		{

			
			  if( v == btnSubmit && isGenOTP)
			{
				btnSubmit.setBackgroundResource(R.drawable.btn_regis_down);
				msisdn = txtNumber.getText().toString();
				GetOTP();
			}
			else if(v == btnSubmit && isSubmitOTP)
			{
				btnSubmit.setBackgroundResource(R.drawable.btn_regis_down);
				SubmitOTP();
			}
		}
		catch(Exception ex)
		{
			PrintLog.printException(this, "Note", ex);
		}
		
	}


	@Override
	public void onReceiveDataStream(String loadName, String url, String strOutput) {
		try
		{
			if( !strOutput.equals("") )
			{
				JSONObject cc = new JSONObject(strOutput) ;
				JSONObject bb =cc.getJSONObject("SportApp");
				JSONObject c = bb.getJSONObject("Response");
				if(loadName == "genOTP")
				{
					txtHeader.setText(c.getString("header"));
					txtDetail.setText(c.getString("detail"));
					txtFooter.setText(c.getString("footer"));

					txtNumber.setText(c.getString("auto"));
					isGenOTP = false;
					isSubmitOTP = true;
					progress.dismiss();
				}
				else if(loadName == "submitOTP")
				{
					if( c.getString("isactive").equals("N") )
					{
						txtDetail.setText(c.getString("detail"));
						txtNumber.setText("");
						progress.dismiss();
					}
					else if(c.getString("isactive").equals("Y"))
					{
						ISACTIVE = c.getString("isactive");
						Toast.makeText(this, c.getString("detail"), Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(this, StarSoccer_Main.class);
						startActivity(intent);
						this.finish();
					}

				}
				//btnSubmit.setBackgroundResource(R.drawable.btn_regis);
			}
		}
		catch(Exception ex)
		{
			progress.dismiss();
			PrintLog.printException(this, "Note", ex);
		}
	}

	@Override
	public void onReceiveGetDataXML(String loadName, String url, String strOutput) throws InterruptedException, SAXException {

	}
}
