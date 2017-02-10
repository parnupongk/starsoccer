package com.isport_starsoccer.service;

import java.net.URLEncoder;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;

import com.isport_starsoccer.data.DataSetting;

public class StartUp
{
	public static String getImsi(Context context)
	{
		try {
			TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			return telephonyManager.getSubscriberId();
		} catch (Exception e) {
			// TODO: handle exception
			//PrintLog.printException("StartUp method getImsi", e);
			return "";
		}
			
	}
	
	public static String getImei(Context context)
    {
		try
		{
			TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//	        DataSetting.IMEI = getDeviceID(telephonyManager);
	
	    	String id = telephonyManager.getDeviceId();
	    	return id;
//	    	if (id == null)
//	    	{
//	    		return "";
//	    	}
//	    	
//	    	int phoneType = telephonyManager.getPhoneType();
//	    	switch(phoneType)
//	    	{
//	    		case TelephonyManager.PHONE_TYPE_NONE:
//	    			return id;//"NONE: " + id;
//	 
//	    		case TelephonyManager.PHONE_TYPE_GSM:
//	    			return id;//"GSM: IMEI=" + id;
//
//	    		case TelephonyManager.PHONE_TYPE_CDMA:
//	    			return id;//"CDMA: MEID/ESN=" + id;
//	    		default:
//	    			return id;//"UNKNOWN: ID=" + id;
//	    	}
		}catch (Exception e) {
			// TODO: handle exception]
			//PrintLog.printException("StartUp method getImei", e);
			return "";
		}
    }
	
	public static void getModel(Context context)
	{
		try{
			DataSetting.MODEL = URLEncoder.encode(Build.MODEL);
			//PrintLog.print("StartUp method getModel", DataSetting.MODEL);
		} catch (Exception e) {
			// TODO: handle exception
			//PrintLog.printException("StartUp method getModel", e);
		}
	}
	
	//////////////////////////////////////// Setting /////////////////////////////////////////
	
//	private final static String PREFS_NAME = "BS_SIAM_SPORT"; 
	
	public static void getSetting(Context context)
	{
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
//		SharedPreferences settings = activity.getSharedPreferences(PREFS_NAME, 0);
		DataSetting.Languge = settings.getString("selectLanguage", "th");
		DataSetting.setLeague = settings.getString("selectLeague", "");
		DataSetting.checkNotify = settings.getBoolean("selectNotify", true);
		DataSetting.checkFirst = settings.getBoolean("checkFirst", true);
	}
	
	public static void setSetting(Context context, String language, String setLeague, boolean setNotify, boolean checkFirst)
	{
		// We need an Editor object to make preference changes.
		// All objects are from android.context.Context
//		SharedPreferences settings = activity.getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("selectLanguage", language);
		editor.putString("selectLeague", setLeague);
		editor.putBoolean("selectNotify", setNotify);
		editor.putBoolean("checkFirst", checkFirst);
		
		DataSetting.Languge = language;
		DataSetting.checkNotify = setNotify;
		DataSetting.setLeague = setLeague;
		DataSetting.checkFirst = checkFirst;
		// Commit the edits!
		editor.commit();
	}
}
