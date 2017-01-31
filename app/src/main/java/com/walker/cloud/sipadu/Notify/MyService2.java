//package com.walker.cloud.sipadu.Notify;
//
//import android.app.IntentService;
//import android.app.Notification;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.content.Context;
//import android.content.Intent;
//
//import com.walker.cloud.sipadu.MainActivity;
//import com.walker.cloud.sipadu.R;
//import com.walker.cloud.sipadu.asset.Database;
//import com.walker.cloud.sipadu.asset.ObjectJadwal;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//
//
///**
// * Created by ASUS on 30/01/2017.
// */
//
//public class MyService2  extends IntentService {
//    public MyService2() {
//        super("MyService2");
//        // TODO Auto-generated constructor stub
//
//    }
//    private static final int NOTIFICATION = 123;
//
//    private NotificationManager mNM;
//
//
//    @Override
//    protected void onHandleIntent(Intent intent) {
//        Calendar calendar = Calendar.getInstance();
//        int day = calendar.get(Calendar.DAY_OF_WEEK);
//        if(day>1 && day<7) {
//            int sesi = intent.getExtras().getInt("sesi");
//            SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
//            Date date = new Date();
//            String dateString = format1.format(date);
//            ArrayList<ObjectJadwal> objectJadwal= Database.getInstance(getApplicationContext()).getAllJadwalHariIniDanSesi(dateString,sesi);
//            if(objectJadwal.size()==1) {
//                showNotification(objectJadwal.get(0));
//            }
//        }
//    }
//
//
//
//    private void showNotification(ObjectJadwal objectJadwal) {
//
//
//            // This is the 'title' of the notification
//            CharSequence title = "SESI "+objectJadwal.getMataKuliah();
//            // This is the icon to use on the notification
//            int icon = R.drawable.notificationcircleblue;
//            // This is the scrolling text of the notification
//            //CharSequence text = "Your notification time is upon us.";
//            // What time to show on the notification
//            long time = System.currentTimeMillis();
//
//            String messa ="Sesi "+objectJadwal.getSesi()+" | Ruang "+objectJadwal.getRuang();
//            Intent intent = new Intent(this,MainActivity.class);
//            PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//            Notification noti = new Notification.Builder(this.getBaseContext()).setTicker("SIPADU NOTIFIKASI")
//                .setContentTitle(title).setSmallIcon(icon).setContentText(messa).build();
//            noti.defaults |= Notification.DEFAULT_VIBRATE;
//
//            noti.flags = Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Notification.FLAG_AUTO_CANCEL;
//            NotificationManager notificationManager = (NotificationManager) this
//                .getSystemService(Context.NOTIFICATION_SERVICE);
//            notificationManager.notify(0, noti);
//
//    }
//}
