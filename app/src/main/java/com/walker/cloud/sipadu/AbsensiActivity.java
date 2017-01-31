package com.walker.cloud.sipadu;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


import com.walker.cloud.sipadu.adapter.AbsensiAdapter;
import com.walker.cloud.sipadu.asset.Database;
import com.walker.cloud.sipadu.asset.ObjectAbsensi;

import java.util.ArrayList;

public class AbsensiActivity extends AppCompatActivity {
    private Database db;
    private ListView lvAbsensi;
    private ArrayList<ObjectAbsensi> listAbsensi;
    private int currentPercentNumber = 0;

    public void animateTextView(int initialValue, int finalValue, final TextView textview, int duration) {

        ValueAnimator valueAnimator = ValueAnimator.ofInt((int)initialValue, (int)finalValue);
        valueAnimator.setDuration(duration);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                textview.setText(valueAnimator.getAnimatedValue().toString());
            }
        });
        valueAnimator.start();

    }

//    public void setListAbsensi(String[][] dataAbsensi, ArrayList listAbsensi, String nim, String tahunAjaran, String semester){
//        ObjectAbsensi absensi;
//        for(int i = 0; i < dataAbsensi.length; i++){
//            if(dataAbsensi[i][0] == nim && dataAbsensi[i][1] == tahunAjaran && dataAbsensi[i][2] == semester ){
//                absensi = new AbsensiGetsetter();
//                absensi.setMataKuliah(dataAbsensi[i][3]);
//                absensi.setDosen(dataAbsensi[i][4]);
//                absensi.setPersentase(dataAbsensi[i][5]);
//                absensi.setHurufDepan();
//
//                listAbsensi.add(absensi);
//            }
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absensi);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new Database(getApplicationContext());

        lvAbsensi = (ListView)findViewById(R.id.lvAbsensi);
        listAbsensi = db.getAllAbsensi();
        Log.d("cingire",Integer.toString(listAbsensi.size()));

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Absensi");
        }

//        setListAbsensi(ArrayContainer.konten_absensi, listAbsensi, "14.8189", "2015", "4");
//
//        /*AbsensiAdapter absensi;
//
//        for (int i = 0; i < dataAbsensi.length; i++){
//            absensi = new AbsensiAdapter();
//            absensi.setMataKuliah(dataAbsensi[i][0]);
//            absensi.setDosen(dataAbsensi[i][1]);
//            absensi.setPersentase(dataAbsensi[i][2]);
//            absensi.setHurufDepan();
//
//            listAbsensi.add(absensi);
//        }*/

        AbsensiAdapter adapter = new AbsensiAdapter(AbsensiActivity.this, listAbsensi);
        lvAbsensi.setAdapter(adapter);

        lvAbsensi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ObjectAbsensi absensi = listAbsensi.get(position);

                TextView textPercentageBig = (TextView) findViewById(R.id.textPersentaseBig);
                TextView textMaatKuiahlBig = (TextView) findViewById(R.id.textMataKuliahBig);
                TextView textDosenBig = (TextView) findViewById(R.id.textDosenBig);

                currentPercentNumber = Integer.parseInt(textPercentageBig.getText().toString());

                //textPercentageBig.setText(absensi.getPersenase());
                textMaatKuiahlBig.setText(absensi.getMatkul());
                textDosenBig.setText(absensi.getDosen());

                animateTextView(currentPercentNumber, Integer.parseInt(absensi.getAbsensi()), (TextView) findViewById(R.id.textPersentaseBig), 1500);

            }
        });

    }

    @Override
    public void onAttachedToWindow() {
        animateTextView(100, 0, (TextView) findViewById(R.id.textPersentaseBig), 3000);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}