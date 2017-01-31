package com.walker.cloud.sipadu.userinterface;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.walker.cloud.sipadu.AbsensiActivity;
import com.walker.cloud.sipadu.R;
import com.walker.cloud.sipadu.adapter.BeritaAdapter;
import com.walker.cloud.sipadu.adapter.JadwalAdapter;
import com.walker.cloud.sipadu.asset.CustomVolleyRequest;
import com.walker.cloud.sipadu.asset.Database;
import com.walker.cloud.sipadu.asset.MyUrl;
import com.walker.cloud.sipadu.asset.ObjectBerita;
import com.walker.cloud.sipadu.asset.ObjectJadwal;
import com.walker.cloud.sipadu.asset.Request;
import com.walker.cloud.sipadu.asset.SessionManager;
import com.walker.cloud.sipadu.image.ImageLoadStatic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Septiawan Aji Pradan on 1/23/2017.
 */

public class HomeActivity extends AppCompatActivity implements ObservableScrollViewCallbacks {
    private SessionManager sessionManager;
    private TextView nama, kelas,tanggaljadwal;
    private ObservableScrollView mScrollView;
    private ListView jadwal_overview, berita_overview;
    private ArrayList<ObjectJadwal> jadwal_overview_array;
    private List<ObjectBerita> berita_overview_array;
    private Toolbar toolbar;
    private int parallaxHeight;
    private DisplayMetrics displayMetrics;
    private Button absenButton, nilaiButton,logoutButton;
    private Database database;
    private ImageLoadStatic img;
    private CircleImageView circleImageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        nama = (TextView)findViewById(R.id.username_home);
        kelas = (TextView)findViewById(R.id.class_home);
        tanggaljadwal = (TextView)findViewById(R.id.tanggal_jadwal);

        absenButton = (Button)findViewById(R.id.button_absen);
        nilaiButton = (Button)findViewById(R.id.button_nilai);
        logoutButton = (Button)findViewById(R.id.button_logout);

        setButtonBackgroundColor(absenButton);
        setButtonBackgroundColor(nilaiButton);
        setButtonBackgroundColor(logoutButton);


        sessionManager = new SessionManager(getApplicationContext());
        database = new Database(getApplicationContext());
        img = new ImageLoadStatic(getApplicationContext());



//        //toolbar
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        if(toolbar.getOverflowIcon() != null) {
//            toolbar.getOverflowIcon().setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
//        }
//
//        setSupportActionBar(toolbar);
//        ActionBar actionBar = getSupportActionBar();
//        if(actionBar != null) {
//            actionBar.setDisplayShowTitleEnabled(false);
//        }

        //desain
//        toolbar.setBackgroundColor(ScrollUtils.getColorWithAlpha(0, getResources().getColor(R.color.colorPrimary)));
        mScrollView = (ObservableScrollView)findViewById(R.id.scroller);
        mScrollView.setScrollViewCallbacks(this);

        parallaxHeight = getResources().getDimensionPixelSize(R.dimen.parallax_item);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        //set nama dan kelas user
        HashMap<String,String> userIdentas = new HashMap<>();
        userIdentas=sessionManager.getUserIdentitas();
        try{
            nama.setText(userIdentas.get(SessionManager.KEY_NAMA));
            kelas.setText(userIdentas.get(SessionManager.KEY_KELAS));
        }catch (Exception ex){

        }

        //set tanggal hari ini
        Calendar calendar = Calendar.getInstance();
        int today  = calendar.get(Calendar.DAY_OF_WEEK);
        int date  = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH)+1;
        String monthFix="";
        if(month>=1 &&month<10){
            monthFix="0"+month;
        }else{
            monthFix=Integer.toString(month);
        }
        int year = calendar.get(Calendar.YEAR);
        String tanggalSekarang = Integer.toString(date)+"-"+monthFix+"-"+Integer.toString(year);
        Log.d("sekarang",tanggalSekarang);

        tanggaljadwal.setText(getToday("JADWAL_OVERVIEW"));
        jadwal_overview = (ListView)findViewById(R.id.list_jadwal_overview);
        jadwal_overview_array = database.getAllJadwalHariIni(tanggalSekarang);

        JadwalAdapter adapterJadwal = new JadwalAdapter(HomeActivity.this, jadwal_overview_array);
        jadwal_overview.setAdapter(adapterJadwal);

        View emptyView;
        if (today == 1 || today == 7) {
            emptyView = findViewById(R.id.jadwal_empty_weekend);
        } else {
            emptyView = findViewById(R.id.jadwal_empty_weekday);
        }
        emptyView.setVisibility(View.VISIBLE);
        jadwal_overview.setEmptyView(emptyView);
        if(!jadwal_overview_array.isEmpty()) {//set tinggi list
            setListViewHeight(jadwal_overview);
        }

        //set berita
        for (int i=0;i<database.getAllBerita().size();i++){
            Log.d("berita_cuy",database.getAllBerita().get(i).getJudul().toString()+" | "+database.getAllBerita().get(i).getInisial().toString());
        }
        berita_overview = (ListView)findViewById(R.id.list_berita_overview);
        berita_overview_array = database.getAllBerita();
        BeritaAdapter adapterBerita = new BeritaAdapter(HomeActivity.this, berita_overview_array);
        berita_overview.setAdapter(adapterBerita);
        if(!berita_overview_array.isEmpty()) {
            setListViewHeight(berita_overview);
        }
//
//        CircleImageView circleImageView = (CircleImageView)findViewById(R.id.detail_userpic);
//        circleImageView.setImageResource(R.drawable.ic_user);

//        CardView beritaOverview = (CardView)findViewById(R.id.card_view_berita_overview);
//        beritaOverview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(HomeActivity.this, BeritaActivity.class);
//                startActivity(i);
//            }
//        });
//
//        berita_overview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                BeritaGetsetter mbl = berita_overview_array.get(position);
//
//                Intent intent = new Intent(HomeActivity.this, BeritaDetailActivity.class);
//                intent.putExtra(BeritaDetailActivity.KEY_ITEM, mbl);
//                startActivityForResult(intent, 0);
//            }
//        });

        jadwal_overview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent i = new Intent(HomeActivity.this, JadwalActivity.class);
//                startActivity(i);
                Toast.makeText(HomeActivity.this, "Hai", Toast.LENGTH_SHORT).show();
            }
        });

        CardView jadwalOverview = (CardView)findViewById(R.id.card_view_jadwal_overview);
        jadwalOverview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(HomeActivity.this, JadwalActivity.class);
//                startActivity(i);
                Toast.makeText(HomeActivity.this, "Jadwal", Toast.LENGTH_SHORT).show();
            }
        });

        absenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d("kabeh",database.getAllJadwal().get(0).getTanggal().toString());

                Intent i = new Intent(HomeActivity.this, AbsensiActivity.class);
                startActivity(i);
                Toast.makeText(HomeActivity.this, "Absensu", Toast.LENGTH_SHORT).show();
            }
        });

        nilaiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(HomeActivity.this, NilaiActivity.class);
//                startActivity(i);
                Toast.makeText(HomeActivity.this, "Nilai", Toast.LENGTH_SHORT).show();
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_logout) {
//            Toast.makeText(this, "Keluar", Toast.LENGTH_SHORT).show();
//        }
//
//        return super.onOptionsItemSelected(item);
//    }


//    public void logout() {
//        Intent i = new Intent(HomeActivity.this, LoginActivity.class);
//        SharedPreferences preferences = getSharedPreferences("INPUT_NIM", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putBoolean("INPUT_NIM", false);
//        editor.apply();
//        preferences = getSharedPreferences("LOGIN_FLAG", Context.MODE_PRIVATE);
//        editor = preferences.edit();
//        editor.putBoolean("LOGIN_FLAG", false);
//        editor.apply();
//        startActivity(i);
//        finish();
//    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if(resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static void setListViewHeight(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if(listAdapter == null) {
            return;
        }
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for(int i = 0; i<listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if(i==0) view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    public void setButtonBackgroundColor(Button button) {
        button.getBackground().setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary), PorterDuff.Mode.SRC_OVER);
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);

        float alpha = Math.min(1, (float) scrollY / (parallaxHeight - toolbar.getHeight()));

        float alpha2 = ScrollUtils.getFloat(scrollY / (parallaxHeight - toolbar.getHeight()), 0, 1);
        toolbar.setBackgroundColor(ScrollUtils.getColorWithAlpha(alpha2, getResources().getColor(R.color.colorPrimary)));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if(getActionBar() != null) {
                getActionBar().setElevation(alpha2);
            }
        }

        View tinthead = findViewById(R.id.tint_head);
        tinthead.setMinimumHeight(parallaxHeight);
        tinthead.setBackgroundColor(ScrollUtils.getColorWithAlpha(alpha, getResources().getColor(R.color.colorPrimary)));
        tinthead.setTranslationY(0);

        View parallaxContent = findViewById(R.id.konten_parallax);
        parallaxContent.setTranslationY(scrollY / 2);

        circleImageView = (CircleImageView)findViewById(R.id.detail_userpic);
        circleImageView.setAlpha(Math.max(1 - alpha * 3, 0));
        circleImageView.setTranslationY(-scrollY);

        //taruh url foto nya
        img.DisplayImage(MyUrl.FotoMhs+"13.7868.jpg",circleImageView);

        TextView uname = (TextView) findViewById(R.id.username_home);
        TextView kelas = (TextView) findViewById(R.id.class_home);

        View konten = findViewById(R.id.konten_home);

        displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        konten.setMinimumHeight(displayMetrics.heightPixels-toolbar.getHeight());

        int screenWidth = displayMetrics.widthPixels;
        int unameWidth = uname.getWidth();
        int unameSpace = (screenWidth - unameWidth)/2;

        int kelasWidth = kelas.getWidth();
        int kelasSpace = (screenWidth - kelasWidth) / 2;

        float marginDefault = getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin);

        float maxUnameY = (float)(parallaxHeight - toolbar.getHeight() - 0.3*marginDefault);
        int adjustedUnameY = (int)ScrollUtils.getFloat(scrollY, 0, maxUnameY);
        float maxKelasY = (float)(parallaxHeight - toolbar.getHeight());
        int adjustedKelasY = (int)ScrollUtils.getFloat(scrollY, 0, maxKelasY);

        uname.setTranslationY(-adjustedUnameY);
        kelas.setTranslationY(-adjustedKelasY);

        uname.setPivotX(0);
        uname.setPivotY(0);
        kelas.setPivotX(0);
        kelas.setPivotY(0);

        int tempUnameX = (int)ScrollUtils.getFloat(scrollY-(parallaxHeight/2), 0, parallaxHeight);
        int tempKelasX = (int)ScrollUtils.getFloat(scrollY-(parallaxHeight/2), 0, (kelasSpace));
        int adjustedUnameX = (int)ScrollUtils.getFloat(((unameSpace-marginDefault)/uname.getY())*tempUnameX, 0, (unameSpace-marginDefault));
        int adjustedKelasX = (int)ScrollUtils.getFloat(((kelasSpace+toolbar.getHeight()+marginDefault)/kelas.getY())*tempKelasX, 0, (kelasSpace-marginDefault));
        uname.setTranslationX(-adjustedUnameX);
        kelas.setTranslationX(-adjustedKelasX);

        float scaler =((float)parallaxHeight - (adjustedUnameY-(parallaxHeight/2)))/parallaxHeight;
        float scale = ScrollUtils.getFloat(scaler, (float)0.9, 1);
        uname.setScaleX(scale);
        uname.setScaleY(scale);
    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        onScrollChanged(mScrollView.getCurrentScrollY(), false, false);
    }

    public String getToday(String usage) {
        String day, month, tanggal;
        Calendar calendar = Calendar.getInstance();

        tanggal = null;

        if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
            day = "Senin";
        } else if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
            day = "Selasa";
        } else if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
            day = "Rabu";
        } else if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
            day = "Kamis";
        } else if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
            day = "Jumat";
        } else if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            day = "Sabtu";
        } else if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            day = "Minggu";
        } else {
            day = "";
        }

        if(calendar.get(Calendar.MONTH) == Calendar.JANUARY) {
            month = "Januari";
        } else if(calendar.get(Calendar.MONTH) == Calendar.FEBRUARY) {
            month = "Februari";
        } else if(calendar.get(Calendar.MONTH) == Calendar.MARCH) {
            month = "Maret";
        } else if(calendar.get(Calendar.MONTH) == Calendar.APRIL) {
            month = "April";
        } else if(calendar.get(Calendar.MONTH) == Calendar.MAY) {
            month = "Mei";
        } else if(calendar.get(Calendar.MONTH) == Calendar.JUNE) {
            month = "Juni";
        } else if(calendar.get(Calendar.MONTH) == Calendar.JULY) {
            month = "Juli";
        } else if(calendar.get(Calendar.MONTH) == Calendar.AUGUST) {
            month = "Agustus";
        } else if(calendar.get(Calendar.MONTH) == Calendar.SEPTEMBER) {
            month = "September";
        } else if(calendar.get(Calendar.MONTH) == Calendar.OCTOBER) {
            month = "Oktober";
        } else if(calendar.get(Calendar.MONTH) == Calendar.NOVEMBER) {
            month = "November";
        } else if(calendar.get(Calendar.MONTH) == Calendar.DECEMBER) {
            month = "Desember";
        } else {
            month = "";
        }
        if(usage.equals("JADWAL_OVERVIEW")) {
            tanggal = day + ", " + calendar.get(Calendar.DAY_OF_MONTH) + " " + month + " " + calendar.get(Calendar.YEAR);
        }
        return tanggal;
    }
}