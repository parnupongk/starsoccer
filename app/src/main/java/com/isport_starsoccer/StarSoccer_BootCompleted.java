package com.isport_starsoccer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.isport_starsoccer.data.DataSetting;
import com.isport_starsoccer.service.StartUp;

public class StarSoccer_BootCompleted extends BroadcastReceiver
{
	@Override
	public void onReceive(Context context, Intent intent)
	{
		if(intent.getAction().equals("android.intent.action.BOOT_COMPLETED"))
		{			
			DataSetting.IMSI = StartUp.getImsi(context);
			DataSetting.IMEI = StartUp.getImei(context);
			StartUp.getModel(context);
			StartUp.getSetting(context);
			
			if(DataSetting.IMSI != null && DataSetting.IMSI.length() > 1)
			{
					if(DataSetting.checkNotify)
					{
						context.startService(new Intent(context, StarSoccer_Notify.class));
					}
			}
		}
	}
}
