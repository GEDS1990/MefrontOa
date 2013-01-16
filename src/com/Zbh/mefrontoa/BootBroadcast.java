package com.Zbh.mefrontoa;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

public class BootBroadcast extends BroadcastReceiver {
	  
    @Override  
    public void onReceive(Context context, Intent mintent) {  
  
        if (Intent.ACTION_BOOT_COMPLETED.equals(mintent.getAction())) {  
            // �������  
            Intent intent = new Intent(context, Alarmreceiver.class);  
            intent.setAction("mefrontoa.alarm.action");  
            PendingIntent sender = PendingIntent.getBroadcast(context, 0,  
                    intent, 0);  
            long firstime = SystemClock.elapsedRealtime();  
            AlarmManager am = (AlarmManager) context  
                    .getSystemService(Context.ALARM_SERVICE);  
  
            // 30��һ�����ڣ���ͣ�ķ��͹㲥  
            am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, firstime,  
                    30 * 1000, sender);
        }  
  
    }  

}