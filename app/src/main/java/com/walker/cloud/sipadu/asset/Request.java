package com.walker.cloud.sipadu.asset;

import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ASUS on 21/01/2017.
 */

public class Request {

    private static final String d="request";
    private Activity activity;
    private SessionManager sessionManager;
    ProgressDialog progres;
    public Request(Activity activity){
        this.activity=activity;
        sessionManager = new SessionManager(this.activity);


    }

    public void verivyNim(String nim, String pass,int type){
        //0 untuk login 1 untuk vervy ulang
        try {

            Map<String, String> params = new HashMap<String, String>();
            params.put("submit", "Login");
            params.put("username", nim);
            params.put("password",pass);
            Log.d(d,nim+"|"+pass);
            if(type==0){
                sessionManager.createOrChangeChace();
            }
            CostumListenerVolley costumListenerVolley = new CostumListenerVolley(activity,params,sessionManager,this,type);
            MySingleton.getmInstance(activity).addToRequestQueue(new com.walker.cloud.sipadu.asset.StringRequest(com.android.volley.Request.Method.POST, MyUrl.Veryfy,
                    params,costumListenerVolley,
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }));


        }catch (Exception ex){
            Log.d(d+"Error",""+ex);
        }
    }
    public boolean accessIndex(int i,int page,String search,String nim,String pass){
        String url="";
        Map<String, String> params = new HashMap<String, String>();
        if(i==2){
            //index page
            url=MyUrl.Indexx;
            params.put("submit", "Login");
            params.put("username", nim);
            params.put("password",pass);
        }else if(i==3){
            //profil
            url=MyUrl.aboutProfil;
        }else if(i==4){
            //Berita
            url=MyUrl.Berita+page;
            params.put("key","394783274hfwejhrjewh83724");
        }else if(i==5){
            //Jadwal
            url=MyUrl.Jadwal+page;
            params.put("key","394783274hfwejhrjewh83724");
        }else if(i==6){
            //searchDosen
            url=MyUrl.SearchDosen;
            params.put("key","394783274hfwejhrjewh83724");
            params.put("submit","Tampilkan");
            params.put("nama_dosen",search);
        }
        try{
            CostumListenerVolley costumListenerVolley = new CostumListenerVolley(activity,params,sessionManager,this,i);
            MySingleton.getmInstance(activity).addToRequestQueue(new com.walker.cloud.sipadu.asset.StringRequest(com.android.volley.Request.Method.POST, url,
                    params,costumListenerVolley,
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }));


        }catch (Exception ex){
            Log.d(d+"Error",""+ex);
        }
        return true;
    }
    public boolean LogOut(){
        Map<String, String> params = new HashMap<String, String>();
        try{
            CostumListenerVolley costumListenerVolley = new CostumListenerVolley(activity,params,sessionManager,this,2);
            MySingleton.getmInstance(activity).addToRequestQueue(new com.walker.cloud.sipadu.asset.StringRequest(com.android.volley.Request.Method.POST, MyUrl.LogOut,
                    params,costumListenerVolley,
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }));


        }catch (Exception ex){
            Log.d(d+"Error",""+ex);
        }
        return true;
    }

}
