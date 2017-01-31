//package com.walker.cloud.sipadu.Notify;
//
//import android.app.IntentService;
//import android.content.Intent;
//
//import com.walker.cloud.sipadu.asset.Request;
//
//import java.util.Calendar;
//
//public class MyService extends IntentService {
//
//
//    public MyService() {
//        super("MyService");
//        // TODO Auto-generated constructor stub
//
//    }
//    private static final int NOTIFICATION = 123;
//
//
//
//    @Override
//    protected void onHandleIntent(Intent intent) {
//        Calendar calendar = Calendar.getInstance();
//        int day = calendar.get(Calendar.DAY_OF_WEEK);
//        if(day>1 && day<7) {
////            Request request = new Request(getApplicationContext());
////            request.accessIndex(5, 1, "", "", "");
//        }
//    }
//
//
//
//
//}