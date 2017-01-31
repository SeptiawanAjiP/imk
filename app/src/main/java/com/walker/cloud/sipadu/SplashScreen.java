package com.walker.cloud.sipadu;

/**
 * Created by Kaddafi on 10/28/2016.
 */

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import com.walker.cloud.sipadu.asset.SessionManager;
import com.walker.cloud.sipadu.userinterface.LoginActivity;
import com.walker.cloud.sipadu.userinterface.WelcomeActivity;


public class SplashScreen extends AppCompatActivity {

    TextView noKoneksi;
    SessionManager sm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        sm = new SessionManager(getApplicationContext());
//        noKoneksi = (TextView)findViewById(R.id.no_koneksi);
        Thread splash = new Thread(){
            public void run(){
                try{
                    sleep(1500);

//                    if(adaKoneksi()){
                        if(sm.isInstalled()){
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
                            startActivity(intent);
                            finish();
                        }

//                    }else{
//
//                    }

                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
        splash.start();
    }



    public boolean adaKoneksi() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

}
