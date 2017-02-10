package com.isport_starsoccer;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.isport_starsoccer.data.DataElementSportNews;

import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class StarSoccer_Twitter extends StarSoccer_BaseClass 
{
//	private ProgressDialog progress = null;
	private RelativeLayout layout = null;
	private final String consumerKey = "0NQeB2it7ciewUwiqs1Zaw";
	   private final String consumerSecret = "TK1gF8nzqvydMFdlGs4wmeU2xoaydrza7FCEzkcWZE";
	   private final String CALLBACKURL = "x-oauthflow-twitter://callback";
	   private Twitter twitter;
	   private WebView mWebView;
	   private RequestToken requestToken;
	   private DataElementSportNews data = null;
	   private String message = "";
	   File ss = null;
	   private ProgressDialog progress = null;
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
//		getWindow().requestFeature(Window.FEATURE_PROGRESS);
   //     getWindow().requestFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_starsoccer_main);
		progress = ProgressDialog.show(this, null, "Loading...", true, true);
		
		
		layout = new RelativeLayout(this);
		layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.page_twitter, layout);
        
        layoutCenter = (RelativeLayout)findViewById(R.id.main_layout_center);
        
		if(getIntent().getExtras() != null)
		{
			message = getIntent().getExtras().getString("message");
			//imgUrl = getIntent().getExtras().getString("message");
		}
		
		try
		{
				twitter = new TwitterFactory().getInstance();
			      twitter.setOAuthConsumer(consumerKey, consumerSecret);
			      mWebView = (WebView)layout.findViewById(R.id.twitter_webview);
			      requestToken = twitter.getOAuthRequestToken(CALLBACKURL);
			      mWebView.loadUrl(requestToken.getAuthenticationURL());
			      mWebView.setVisibility(View.VISIBLE);
			      mWebView.requestFocus(View.FOCUS_DOWN);
			      
			      mWebView.setWebViewClient(new WebViewClient(){
			            @Override
			            public boolean shouldOverrideUrlLoading (WebView view, String url) {
			                if (url.startsWith(CALLBACKURL)) {
			                    Uri uri = Uri.parse(url);
			                    completeVerify(uri);
			                    
			                    return true;
			                }
			                
			                return false;
			            }
			        });
			        mWebView.getSettings().setAppCacheEnabled(false);
			        mWebView.getSettings().setJavaScriptEnabled(true);
			        mWebView.clearCache(true);
			        mWebView.clearFormData();
			        getWindow().setFeatureInt(Window.FEATURE_PROGRESS, Window.PROGRESS_VISIBILITY_ON);
			        final Activity activity = this;
			        mWebView.setWebChromeClient(new WebChromeClient() {
			            public void onProgressChanged(WebView w, int p) {
			                activity.setProgress(p * 100);
			            }
			        });
			 
			        // AsyncTask to load mRequestToken.getAuthorizationURL() into mWebView
			        //OAuthTask oauthTask = new OAuthTask();
			        //oauthTask.execute();
			}
			catch(Exception ex)
			{
				String s  = ex.getMessage();
			}
		 layoutCenter.addView(layout);
		 
	    }
		private void completeVerify(Uri uri) {
		    if (uri != null) {
		        String verifier = uri.getQueryParameter("oauth_verifier");
		        try {
		        	AccessToken mAccessToken = twitter.getOAuthAccessToken(requestToken, verifier);
		            twitter.setOAuthAccessToken(mAccessToken);
		            
		            InputStream stream = null;
		            /*String urlString = "http://wap.isport.co.th/isportws/images/ic_launcher.png";
		            URL url = new URL(urlString);
		            URLConnection connection = url.openConnection();
		            HttpURLConnection httpConnection = (HttpURLConnection) connection;
	                httpConnection.setRequestMethod("GET");
	                httpConnection.connect();
	 
	                if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
	                    stream = httpConnection.getInputStream();
	                }*/
		            //Uri tempuri = Uri.parse("");
		            //InputStream is = cR.openInputStream(tempuri);
		            
		            String urlString = "http://wap.isport.co.th/isportws/images/ic_launcher.png";
		            URL url = new URL(urlString);
					Bitmap b = null;
					stream = (InputStream) url.getContent();
		            
	                //stream = getResources().openRawResource(R.drawable.btn_home);
	                message += " " + "http://wap.isport.co.th";
		            StatusUpdate status = new StatusUpdate(message);
		            status.setMedia(message,stream);
		            //twitter.uploadMedia(arg0)
		            
		            if( stream == null )
		            {
		            	twitter.updateStatus(message);
		            }
		            else twitter.updateStatus(status);
		            
		            
		            progress.dismiss();
		            showToast("Status update sucess!");
		            // Add code here to save the OAuth AccessToken and AccessTokenSecret into  SharedPreferences;
		        } catch (Exception e) {
		            Log.d("", "Cannot get AccessToken: " + e.getMessage());
		            //showToast("Error :" + e.getMessage());
		            showToast("Status update fail!");
		        }
		        setResult(Activity.RESULT_OK, getIntent());
		        finish();
		    }
		}

	@Override
	  protected void onResume() {
	        super.onResume();
	  }
	 
	 
	 private void showToast(String s) {
	     Toast.makeText(this, s, Toast.LENGTH_LONG).show();
	 }
	 
	
}
