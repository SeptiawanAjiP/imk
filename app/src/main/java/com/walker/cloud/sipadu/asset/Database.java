package com.walker.cloud.sipadu.asset;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by ASUS on 19/01/2017.
 */

public class Database extends SQLiteOpenHelper {
    private Context context;
    private static Database sInstance;

    private String t = "Database";

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "sipaduIMK";

    //tabel jadwal
    public static final String TABLE_JADWAL="jadwal";
    public static final String K_ID="id";
    public static final String K_HARI="hari";
    public static final String K_TANGGAL="tanggal";
    public static final String K_MATKUL="matkul";
    public static final String K_DOSEN="dosen";
    public static final String K_SESI="sesi";
    public static final String K_RUANG="ruang";

    //tabel berita
    public static final String TABLE_BERITA="berita";
    public static final String K_DARI="dari";
    public static final String K_JUDUL="judul";
    public static final String K_URL="url";
    public static final String K_ISI="isiBerita";
    public static final String K_INISIAL="inisial";

    public static final String TABLE_ISI_BERITA="isiBerita";

    public static final String TABLE_ABSENSI="tableAbsensi";
    public static final String K_PERABSENSI="perAbsensi";

    public static final String READ_STATUS = "read";


    public static final String CREATE_TABLE_JADWAL=" CREATE TABLE "+TABLE_JADWAL+" ( "
            +K_ID+" integer PRIMARY KEY AUTOINCREMENT NOT NULL ,  "
            +K_HARI+" text, "
            +K_TANGGAL+" text, "
            +K_MATKUL+" text, "
            +K_DOSEN+" text, "
            +K_SESI+" integer , "
            +K_RUANG+" text )";

    public static final String CREATE_TABLE_BERITA=" CREATE TABLE "+TABLE_BERITA+" ( "
            +K_ID+" integer PRIMARY KEY AUTOINCREMENT NOT NULL ,  "
            +K_HARI+" text, "
            +K_TANGGAL+" text, "
            +K_DARI+" text, "
            +K_JUDUL+" text, "
            +K_INISIAL+" text,"
            +K_URL+" text ) ";

    public static final String CREATE_TABLE_ISI_BERITA=" CREATE TABLE "+TABLE_ISI_BERITA+" ( "
            +K_ID+" integer PRIMARY KEY AUTOINCREMENT NOT NULL ,  "
            +K_HARI+" text, "
            +K_TANGGAL+" text, "
            +K_DARI+" text, "
            +K_JUDUL+" text, "
            +K_ISI+" text ) ";

    public static final String CREATE_TABLE_ABSENSI=" CREATE TABLE "+TABLE_ABSENSI+" ( "
            +K_ID+" integer PRIMARY KEY AUTOINCREMENT NOT NULL ,  "
            +K_MATKUL+" text, "
            +K_DOSEN+" text, "
            +K_PERABSENSI+" text ) ";



    public static synchronized Database getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new Database(context);
        }
        return sInstance;
    }

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_BERITA);
        db.execSQL(CREATE_TABLE_JADWAL);
        db.execSQL(CREATE_TABLE_ISI_BERITA);
        db.execSQL(CREATE_TABLE_ABSENSI);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String drop = "DROP TABLE IF EXISTS ";
        db.execSQL(drop + TABLE_BERITA);
        db.execSQL(drop + TABLE_JADWAL);
        db.execSQL(drop + TABLE_ISI_BERITA);
        db.execSQL(drop + TABLE_ABSENSI);
    }

    public void dropAll() {
        SQLiteDatabase db = getInstance(context).getWritableDatabase();
        String drop = "DROP TABLE IF EXISTS ";
        db.execSQL(drop + TABLE_BERITA);
        db.execSQL(drop + TABLE_JADWAL);
        db.execSQL(drop + TABLE_ISI_BERITA);
        db.execSQL(drop + TABLE_ABSENSI);
    }

    public boolean InsertBerita(ArrayList<ObjectBerita> objectBeritas){
        for (ObjectBerita objectBerita : objectBeritas ){
            try{
                SQLiteDatabase db = getInstance(context).getWritableDatabase();
                ContentValues v = new ContentValues();
                v.put(K_HARI,objectBerita.getHari());
                v.put(K_TANGGAL,objectBerita.getTanggal());
                v.put(K_DARI,objectBerita.getDari());
                v.put(K_JUDUL,objectBerita.getJudul());
                v.put(K_INISIAL,objectBerita.getJudul().substring(0,1).toUpperCase());
                v.put(K_URL, objectBerita.getUrl());
                db.insert(TABLE_BERITA, null, v);
                Log.d(t,"Insert Berita : "+objectBerita.getJudul()+" | "+objectBerita.getUrl());
                db.close();
            }catch (Exception ex){
                Log.d("TambahCatatan",""+ex);
                return  false;
            }
        }
        return true;
    }
//    public boolean InsertIsiBerita(ArrayList<ObjectBerita> objectBeritas){
//        for (ObjectBerita objectBerita : objectBeritas ){
//            try{
//                SQLiteDatabase db = getInstance(context).getWritableDatabase();
//                ContentValues v = new ContentValues();
//                v.put(K_HARI,objectBerita.getHari());
//                v.put(K_TANGGAL,objectBerita.getTanggal());
//                v.put(K_DARI,objectBerita.getDari());
//                v.put(K_JUDUL,objectBerita.getJudul());
//                v.put(K_ISI, objectBerita.getIsiBerita());
//                db.insert(TABLE_ISI_BERITA, null, v);
//                Log.d(t,"Insert Isi Berita : "+objectBerita.getJudul()+" | "+objectBerita.getUrl());
//                db.close();
//            }catch (Exception ex){
//                Log.d("TambahCatatan",""+ex);
//                return  false;
//            }
//        }
//        return true;
//    }
    public boolean DeleteBerita(){
        try {
            SQLiteDatabase db = getInstance(context).getWritableDatabase();
            db.execSQL("DELETE FROM "+TABLE_BERITA+" WHERE 1 ");
            db.close();
        }catch (Exception ex){

        }
        return true;
    }
    public boolean InsertJadwal(ArrayList<ObjectJadwal> objectJadwals){
        for(ObjectJadwal objectJadwal : objectJadwals){
            SQLiteDatabase db = getInstance(context).getWritableDatabase();
            db.execSQL("DELETE FROM "+TABLE_JADWAL+" WHERE "+K_TANGGAL+" = '"+objectJadwal.getTanggal()+"'");
            db.close();
        }
        for (ObjectJadwal objectJadwal : objectJadwals ){
            if(objectJadwal.getSesi()!=0 && objectJadwal.getDosen()!=null) {
                try {
                    SQLiteDatabase db = getInstance(context).getWritableDatabase();
                    ContentValues v = new ContentValues();
                    v.put(K_HARI, objectJadwal.getHari());
                    v.put(K_TANGGAL, objectJadwal.getTanggal());
                    v.put(K_MATKUL, objectJadwal.getMataKuliah().replace("&amp;","&"));
                    v.put(K_SESI, objectJadwal.getSesi());
                    v.put(K_DOSEN, objectJadwal.getDosen());
                    v.put(K_RUANG, objectJadwal.getRuang());
                    db.insert(TABLE_JADWAL, null, v);
                    Log.d(t, "Insert Jadwal :" + objectJadwal.getTanggal() + " | " + objectJadwal.getMataKuliah()+" | "+objectJadwal.getRuang());
                    db.close();
                } catch (Exception ex) {
                    Log.d("TambahCatatan", "" + ex);
                    return false;
                }
            }
        }
        return true;
    }

    public boolean InsertAbsensi(ArrayList<ObjectAbsensi> objectAbsensis){
        if(objectAbsensis.size()>0){
            for(ObjectAbsensi objectAbsensis1 : objectAbsensis){
                SQLiteDatabase db = getInstance(context).getWritableDatabase();
                db.execSQL("DELETE FROM "+TABLE_ABSENSI+" WHERE "+K_MATKUL+"='"+objectAbsensis1.getMatkul()+"'");
                db.close();
            }
        }
        for (ObjectAbsensi objectAbsensi : objectAbsensis ){
                try {
                    SQLiteDatabase db = getInstance(context).getWritableDatabase();
                    ContentValues v = new ContentValues();
                    v.put(K_MATKUL, objectAbsensi.getMatkul());
                    v.put(K_DOSEN, objectAbsensi.getDosen());
                    v.put(K_PERABSENSI, objectAbsensi.getAbsensi());
                    db.insert(TABLE_JADWAL, null, v);
                    Log.d(t, "InsertAbsensi" + objectAbsensi.getMatkul() + " | " + objectAbsensi.getDosen()+" | "+objectAbsensi.getAbsensi());
                    db.close();
                } catch (Exception ex) {
                    Log.d("TambahCatatan", "" + ex);
                    return false;
                }

        }
        return true;
    }

    public ArrayList<ObjectJadwal> getAllJadwal(){
        ArrayList<ObjectJadwal> objectAbsensis = new ArrayList<>();

        String whereString="";
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
//cal.setTime(new Date());//Set specific Date if you want to

        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        whereString = K_TANGGAL+" >= '"+format1.format(cal.getTime())+"'";

        String quesry = " SELECT "+K_ID+","+K_TANGGAL+","+K_MATKUL+","+K_DOSEN+","+K_SESI+","+K_HARI+","+K_RUANG+" FROM "+TABLE_JADWAL+" WHERE "+whereString+" ORDER BY "+K_TANGGAL;

        try {
            SQLiteDatabase database = getInstance(context).getReadableDatabase();
            Cursor cursor = database.rawQuery(quesry, null);

            if(cursor.moveToFirst()){
                Log.d("DEBUG","move to first");
                do {
                    objectAbsensis.add(new ObjectJadwal(cursor.getInt(cursor.getColumnIndex(K_ID)),cursor.getString(cursor.getColumnIndex(K_TANGGAL)),cursor.getString(cursor.getColumnIndex(K_MATKUL)),
                            cursor.getString(cursor.getColumnIndex(K_DOSEN)),cursor.getInt(cursor.getColumnIndex(K_SESI)),cursor.getString(cursor.getColumnIndex(K_HARI))
                            ,cursor.getString(cursor.getColumnIndex(K_RUANG))));
                }while (cursor.moveToNext());
            }else {
                Log.d("DEBUG","move not to first");
                return objectAbsensis;
            }
            return objectAbsensis;
        }catch (Exception e){
            Log.d("DEBUG",e.toString());
            return objectAbsensis;
        }
    }
    public ArrayList<ObjectJadwal> getAllJadwalHariIni(String tanggal){
        ArrayList<ObjectJadwal> objectJadwals = new ArrayList<>();


//        String quesry = " SELECT "+TABLE_JADWAL+" WHERE "+K_TANGGAL+" = '"+tanggal+"' ORDER BY "+K_SESI;

        String quesry = " SELECT "+K_ID+","+K_TANGGAL+","+K_MATKUL+","+K_DOSEN+","+K_SESI+","+K_HARI+","+K_RUANG+" FROM "+TABLE_JADWAL+" WHERE "+K_TANGGAL+" = '"+tanggal+"' ORDER BY "+K_SESI;

        try {
            SQLiteDatabase database = getInstance(context).getReadableDatabase();
            Cursor cursor = database.rawQuery(quesry, null);

            if(cursor.moveToFirst()){
                Log.d("DEBUG","move to first");
                do {
                    objectJadwals.add(new ObjectJadwal(cursor.getInt(cursor.getColumnIndex(K_ID)),cursor.getString(cursor.getColumnIndex(K_TANGGAL)),cursor.getString(cursor.getColumnIndex(K_MATKUL)),
                            cursor.getString(cursor.getColumnIndex(K_DOSEN)),cursor.getInt(cursor.getColumnIndex(K_SESI)),cursor.getString(cursor.getColumnIndex(K_HARI))
                            ,cursor.getString(cursor.getColumnIndex(K_RUANG))));
                }while (cursor.moveToNext());
            }else {
                Log.d("DEBUG","move not to first");
                return objectJadwals;
            }
            return objectJadwals;
        }catch (Exception e){
            Log.d("DEBUG",e.toString());
            return objectJadwals;
        }
    }
    public ArrayList<ObjectBerita> getAllBerita(){
        ArrayList<ObjectBerita> objectBeritas = new ArrayList<>();


        String quesry = " SELECT "+K_ID+","+K_HARI+","+K_TANGGAL+","+K_DARI+","+K_JUDUL+","+K_URL+","+K_INISIAL+" FROM "+TABLE_BERITA+" WHERE  1 ";

        try {
            SQLiteDatabase database = getInstance(context).getReadableDatabase();
            Cursor cursor = database.rawQuery(quesry, null);

            if(cursor.moveToFirst()){
                Log.d("DEBUG","move to first");
                do {
                    objectBeritas.add(new ObjectBerita(cursor.getInt(cursor.getColumnIndex(K_ID)),cursor.getString(cursor.getColumnIndex(K_HARI)),cursor.getString(cursor.getColumnIndex(K_TANGGAL)),
                            cursor.getString(cursor.getColumnIndex(K_DARI)),cursor.getString(cursor.getColumnIndex(K_JUDUL)),cursor.getString(cursor.getColumnIndex(K_URL))
                            ,cursor.getString(cursor.getColumnIndex(K_INISIAL))));
                }while (cursor.moveToNext());
            }else {
                Log.d("DEBUG","move not to first");
                return objectBeritas;
            }
            return objectBeritas;
        }catch (Exception e){
            Log.d("DEBUG",e.toString());
            return objectBeritas;
        }
    }
    public ArrayList<ObjectAbsensi> getAllAbsensi(){
        ArrayList<ObjectAbsensi> objectAbsensis = new ArrayList<>();


        String quesry = " SELECT "+K_MATKUL+","+K_DOSEN+","+K_PERABSENSI+" FROM "+TABLE_ABSENSI;

        try {
            SQLiteDatabase database = getInstance(context).getReadableDatabase();
            Cursor cursor = database.rawQuery(quesry, null);

            if(cursor.moveToFirst()){
                Log.d("absen1","move to first");
                do {
                    objectAbsensis.add(new ObjectAbsensi(cursor.getString(cursor.getColumnIndex(K_MATKUL)),cursor.getString(cursor.getColumnIndex(K_DOSEN)),
                            cursor.getString(cursor.getColumnIndex(K_PERABSENSI))));
                }while (cursor.moveToNext());
            }else {
                Log.d("absen2","move not to first");
                return objectAbsensis;
            }
            Log.d("absen3",objectAbsensis.toString());
            return objectAbsensis;
        }catch (Exception e){
            Log.d("DEBUG",e.toString());
            return objectAbsensis;
        }
    }

}