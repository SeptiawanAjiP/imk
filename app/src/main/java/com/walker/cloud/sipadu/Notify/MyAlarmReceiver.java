//package com.walker.cloud.sipadu.Notify;
//
//import android.app.AlarmManager;
//import android.app.PendingIntent;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//
//import java.util.Calendar;
//
//public class MyAlarmReceiver extends BroadcastReceiver {
//
//    @Override
//    public void onReceive(Context context, Intent intent) {
//        // TODO Auto-generated method stub
//
//
//    }
//    public static void scheduleCheck(Context context){
//        scheduleCheckJadwalSesiNol(context);
//        scheduleCheckSesiSatu(context);
//        scheduleCheckSesiDua(context);
//        scheduleCheckSesiTiga(context);
//        scheduleCheckSesiEmpat(context);
//        scheduleCheckJadwalSesiLima(context);
//    }
//
//    public static void scheduleCheckJadwalSesiNol(Context context) {
//
//        Intent i = new Intent(context, MyService.class);
//        i.putExtra("sesi",0);
//        PendingIntent pi = PendingIntent.getService(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//        int interval = 1000 * 60 * 20;
//
//        /* Set the alarm to start at 10:30 AM */
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        calendar.set(Calendar.HOUR_OF_DAY, 2);
//        calendar.set(Calendar.MINUTE, 0);
//
//        /* Repeating on every 20 minutes interval */
//        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
//                1000 * 60 * 1440, pi);
//    }
//    public static void scheduleCheckSesiSatu(Context context) {
//        Intent i = new Intent(context, MyService2.class);
//        i.putExtra("sesi",1);
//        PendingIntent pi = PendingIntent.getService(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//
//        /* Set the alarm to start at 10:30 AM */
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        calendar.set(Calendar.HOUR_OF_DAY, 7);
//        calendar.set(Calendar.MINUTE, 15);
//
//        /* Repeating on every 20 minutes interval */
//        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
//                1000 * 60 * 1440, pi);
//    }
//    public static void scheduleCheckSesiDua(Context context) {
//        Intent i = new Intent(context, MyService2.class);
//        i.putExtra("sesi",2);
//        PendingIntent pi = PendingIntent.getService(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//
//        /* Set the alarm to start at 10:30 AM */
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        calendar.set(Calendar.HOUR_OF_DAY, 10);
//        calendar.set(Calendar.MINUTE, 0);
//
//        /* Repeating on every 20 minutes interval */
//        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
//                1000 * 60 * 1440, pi);
//    }
//    public static void scheduleCheckSesiTiga(Context context) {
//        Intent i = new Intent(context, MyService2.class);
//        i.putExtra("sesi",3);
//        PendingIntent pi = PendingIntent.getService(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//
//
//        /* Set the alarm to start at 10:30 AM */
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        calendar.set(Calendar.HOUR_OF_DAY, 13);
//        calendar.set(Calendar.MINUTE, 15);
//
//        /* Repeating on every 20 minutes interval */
//        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
//                1000 * 60 * 1440, pi);
//    }
//    public static void scheduleCheckSesiEmpat(Context context) {
//        Intent i = new Intent(context, MyService2.class);
//        i.putExtra("sesi",4);
//        PendingIntent pi = PendingIntent.getService(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//
//        /* Set the alarm to start at 10:30 AM */
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        calendar.set(Calendar.HOUR_OF_DAY, 16);
//        calendar.set(Calendar.MINUTE, 0);
//
//        /* Repeating on every 20 minutes interval */
//        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
//                1000 * 60 * 1440, pi);
//    }
//    public static void scheduleCheckJadwalSesiLima(Context context) {
//        Intent i = new Intent(context, MyService.class);
//        i.putExtra("sesi",5);
//        PendingIntent pi = PendingIntent.getService(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//
//        /* Set the alarm to start at 10:30 AM */
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        calendar.set(Calendar.HOUR_OF_DAY, 18);
//        calendar.set(Calendar.MINUTE, 15);
//
//        /* Repeating on every 20 minutes interval */
//        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
//                1000 * 60 * 1440, pi);
//    }
//
//
//    public static void stopService(Context context){
//        AlarmManager mgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//
//        Intent i = new Intent(context, MyService.class);
//        PendingIntent pi = PendingIntent.getService(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        mgr.cancel(pi);
//
//        Intent i2 = new Intent(context, MyService2.class);
//        PendingIntent pi2 = PendingIntent.getService(context, 0, i2, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        mgr.cancel(pi2);
//    }
//    public static void restartService(Context context){
//
//    }
//
//}
//
