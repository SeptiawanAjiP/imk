package com.walker.cloud.sipadu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.walker.cloud.sipadu.asset.SessionManager;
import com.walker.cloud.sipadu.userinterface.LoginActivity;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private SessionManager session;

    private TextView Nama;
    private TextView Kelas;
    private TextView Agama;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session = new SessionManager(this.getApplicationContext());
        if(!session.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(this, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            finish();
            startActivity(i);
        }
        Nama = (TextView)findViewById(R.id.nama);
        Kelas=(TextView) findViewById(R.id.kelas);
        Agama =(TextView)findViewById(R.id.agama);
        HashMap<String,String> userIdentas = new HashMap<>();
        SessionManager sessionManager = new SessionManager(this);
        userIdentas=sessionManager.getUserIdentitas();
        try{
            Nama.setText(userIdentas.get(SessionManager.KEY_NAMA));
            Kelas.setText(userIdentas.get(SessionManager.KEY_KELAS));
            Agama.setText(userIdentas.get(SessionManager.KEY_AGAMA));
        }catch (Exception ex){

        }

    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.refresh, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        switch (id){
//            case R.id.refresh:
//                refresh();
//        }
//        return super.onOptionsItemSelected(item);
//    }
//    public void refresh(){
//        Request request = new Request(MainActivity.this);
//        request.accessIndex(4,1,"","","");
//        request.accessIndex(5,1,"","","");
//    }

}
