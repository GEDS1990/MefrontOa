package com.Zbh.mefrontoa;

import android.content.BroadcastReceiver;  
import android.content.Context;  
import android.content.Intent;  
  
public class Alarmreceiver extends BroadcastReceiver {  
    @Override  
    public void onReceive(Context context, Intent intent) {  
  
        if (intent.getAction().equals("mefrontoa.alarm.action")) {  
            Intent i = new Intent();  
            i.setClass(context, MSService.class);  
            // ����service   
            // ��ε���startService�������������service ���ǻ��ε���onStart  
            context.startService(i);  
        }  
    }  
} 