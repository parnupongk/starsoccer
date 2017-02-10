package com.isport_starsoccer.exception;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

//import android.util.Log;

public class PrintLog
{
	public static void print(String tag,String msg)
	{
		try {
//			Log.w(tag, msg);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static void printException(Context context,String comment, Exception e)
	{
		try {
//			Log.w("print exception start", "------ Start "+comment+" -----");
//			e.printStackTrace();
//			Log.w("print exception end", "------ End "+comment+" -----");
			
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
        	builder.setMessage(e.getMessage())
        	       .setCancelable(false)
        	       .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
        	           public void onClick(DialogInterface dialog, int id) {
        	        	   dialog.cancel();
        	           }
        	       });
        	AlertDialog alert = builder.create();
        	alert.show();
        	//System.exit(1);
        	
		} catch (Exception e2) {
			// TODO: handle exception
			e2.printStackTrace();
		}
	}
}
