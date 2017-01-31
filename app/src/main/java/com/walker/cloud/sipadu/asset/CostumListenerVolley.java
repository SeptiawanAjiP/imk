package com.walker.cloud.sipadu.asset;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.walker.cloud.sipadu.MainActivity;
import com.walker.cloud.sipadu.SplashScreen;
import com.walker.cloud.sipadu.userinterface.HomeActivity;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ASUS on 21/01/2017.
 */

public class CostumListenerVolley implements Response.Listener<String> {
    private String chaceSatu;
    private String chaceDua; //stis_mahasiswa
    private SessionManager sessionManager;
    private Activity activity;
    private Request request;
    private Map<String,String> params;
    private int type;
    //type 0 untuk verivy 1 untuk yang lain
    CostumListenerVolley (Activity activity, Map<String,String> params, SessionManager sessionManager, Request request, int type){
        HashMap<String,String> user =sessionManager.getChace();
        this.setChaceSatu(user.get(SessionManager.KEY_CACHE_SESSION_BAAK));
        this.setChaceDua(user.get(SessionManager.KEY_CACHE_STIS_MAHASISWA));
        this.sessionManager=sessionManager;
        this.activity=activity;
        this.request=request;
        this.type=type;
        this.params=params;
    }
    @Override
    public void onResponse(String s) {
        Log.d("response",s);
        if(type>1 && type<7) {
            if (s.trim().equals("") || s.trim().length() == 0 || s == null) {
                HashMap<String, String> user = sessionManager.getUserDetails();
                if (getChaceSatu().trim().equals(user.get(SessionManager.KEY_CACHE_SESSION_BAAK).trim()) ||
                        getChaceDua().trim().equals(user.get(SessionManager.KEY_CACHE_STIS_MAHASISWA).trim())) {
                    sessionManager.createOrChangeChace();
                    MySingleton.getmInstance(activity).getRequestQueue().cancelAll(activity);
                    request.verivyNim(user.get(SessionManager.KEY_NIM), user.get(SessionManager.KEY_PASSWORD),1);
                    Log.d("ErrorNilai",""+s);
                } else {

                }

            } else {
                Parsing parsing = new Parsing(activity.getApplicationContext());
                if(type==2){
                    //index page


                }else if(type==3){
                    //profil
                    parsing.Profil(s);
                    sessionManager.createLoginSession(params.get("username"),params.get("password"));
                    //this.request.accessIndex(3,0,"","","");
                    this.request.accessIndex(4,1,"","","");
                    this.request.accessIndex(5,1,"","","");
                    Intent intent = new Intent(activity, SplashScreen.class);
                    activity.startActivity(intent);
                    activity.finish();
                }else if(type==4){
                    //Berita
                    parsing.ParsingBerita(s);
                }else if(type==5){
                    //Jadwal
                    parsing.ParsingJadwal(s);
                }else if(type==6){
                    //searchDosen

                }
                sessionManager.veryfyChace();
            }
        }else if(type==0){
            this.request.accessIndex(3,0,"",params.get("username"),params.get("password"));
            //this.request.accessIndex(3,0,"");
            //this.request.accessIndex(4,0,"");


        }else if(type==1){
            this.request.accessIndex(4,0,"","","");
        }else if(type==7){
            Database.getInstance(activity).dropAll();
            sessionManager.logoutUser();
        }
    }

    public String getChaceSatu() {
        return chaceSatu;
    }

    public void setChaceSatu(String chaceSatu) {
        this.chaceSatu = chaceSatu;
    }

    public String getChaceDua() {
        return chaceDua;
    }

    public void setChaceDua(String chaceDua) {
        this.chaceDua = chaceDua;
    }
}
