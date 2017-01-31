package com.walker.cloud.sipadu.asset;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.walker.cloud.sipadu.userinterface.LoginActivity;

import java.security.MessageDigest;
import java.util.HashMap;

/**
 * Created by ASUS on 19/01/2017.
 */

public class SessionManager {
    SharedPreferences pref;


    SharedPreferences.Editor editor;

    Context _context;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "AndroidHivePref";
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_NIM = "nim";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_CACHE_SESSION_BAAK = "session_baak";
    public static final String KEY_CACHE_STIS_MAHASISWA = "stis_mahasiswa";
    public static final String IS_VERYFY = "chaceVeryfy";


    public static final String KEY_CACHE_CMSSESSID="cmssessid";
    public static final String KEY_CACHE_SESSION_BAAK_P1="session_baaksatu1";
    public static final String KEY_CACHE_SESSION_BAAK_P2="session_baakdua2";

    //pertama install
    public static final String STATUS_INSTALL = "status_install";
    public static final String FIRST_INSTALL = "first";

    //identitas
    public static final String KEY_NAMA = "nama";
    public static final String KEY_KELAS = "kelas";
    public static final String KEY_TTL = "ttl";
    public static final String KEY_JK = "kelamin";
    public static final String KEY_AGAMA = "agama";


    public static final String d = "SESMANAGER";

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void firstInstall(){
        editor.putBoolean(STATUS_INSTALL,true);
        editor.commit();
    }

    public Boolean isInstalled() {
        return pref.getBoolean(STATUS_INSTALL,false);
    }

    public void createLoginSession(String nim, String password){
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_NIM, nim);
        editor.putString(KEY_PASSWORD, password);
        editor.commit();
    }



    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }

    private static char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
    private static String genHash(MessageDigest md, String fullSourceString, int len )
    {
        byte[] asBytes;
        try {
            asBytes = fullSourceString.getBytes("UTF-8");
        } catch (Exception e) {
            return "";
            //throw new NoSuchAlgorithmException("Cannot get UTF-8 encoding " + e.getMessage());
        }
        md.update(asBytes);

        byte[] messageDigest = md.digest();
        StringBuilder b = new StringBuilder();
        int i = len;
        while ( (2*messageDigest.length) < i ) {
            b.append("0");
            --i;
        }

        for ( int j = 0 ; j < messageDigest.length ; ++j ) {
            byte v = messageDigest[j];
            int hi = (v & 0xF0) >> 4;
            int lo = (v & 0x0F);
            b.append(hexDigits[hi]);
            b.append(hexDigits[lo]);
        }
        return b.toString();
    }



    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user NIM
        user.put(KEY_NIM, pref.getString(KEY_NIM, null));

        // user password
        user.put(KEY_PASSWORD, pref.getString(KEY_PASSWORD, null));

        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
    public boolean isVeryfy() {return pref.getBoolean(IS_VERYFY,false);}

    public void createOrChangeChace(){
        editor.remove(KEY_CACHE_SESSION_BAAK);
        editor.remove(KEY_CACHE_STIS_MAHASISWA);
        editor.remove(IS_VERYFY);
        editor.commit();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            String stress = pref.getString(KEY_NIM, null)+" "+Math.random()*10000+"|"+Math.random()*10000+"||"+pref.getString(KEY_PASSWORD, null);
            String digestAuthHash = genHash(md, stress, 13);

            String stress1 = pref.getString(KEY_PASSWORD, null)+" "+Math.random()*10000+"|"+Math.random()*10000+"||"+pref.getString(KEY_NIM, null);
            String digestAuthHash1 = genHash(md, stress1, 13);

            editor.putString(KEY_CACHE_SESSION_BAAK,System.currentTimeMillis() + digestAuthHash.substring(0,14));
            editor.putString(KEY_CACHE_STIS_MAHASISWA,System.currentTimeMillis() + digestAuthHash1.substring(0,14));
            editor.putBoolean(IS_VERYFY,false);
            editor.commit();
        }catch (Exception ex){
            Log.d(d,"err : "+ex);
        }
    }

    public void veryfyChace(){
        editor.remove(IS_VERYFY);
        editor.commit();
        editor.putBoolean(IS_VERYFY,true);
    }

    public HashMap<String, String> getChace(){
        HashMap<String, String> chace = new HashMap<String, String>();
        // user NIM
        chace.put(KEY_CACHE_SESSION_BAAK, pref.getString(KEY_CACHE_SESSION_BAAK, null));

        // user password
        chace.put(KEY_CACHE_STIS_MAHASISWA, pref.getString(KEY_CACHE_STIS_MAHASISWA, null));

        // return user
        return chace;
    }

    public void createIdentitasUser(String nama, String kelas, String ttl, String jk,String agama){
        editor.remove(KEY_NAMA);
        editor.remove(KEY_KELAS);
        editor.remove(KEY_TTL);
        editor.remove(KEY_JK);
        editor.remove(KEY_AGAMA);
        editor.commit();
        try {
            editor.putString(KEY_NAMA,nama);
            editor.putString(KEY_KELAS,kelas);
            editor.putString(KEY_TTL,ttl);
            editor.putString(KEY_JK,jk);
            editor.putString(KEY_AGAMA,agama);
            editor.commit();
        }catch (Exception ex){
            Log.d(d,"err : "+ex);
        }
    }
    public HashMap<String,String> getUserIdentitas(){
        HashMap<String, String> userIdentitas = new HashMap<String, String>();
        userIdentitas.put(KEY_NAMA,pref.getString(KEY_NAMA,null));
        userIdentitas.put(KEY_KELAS,pref.getString(KEY_KELAS,null));
        userIdentitas.put(KEY_TTL,pref.getString(KEY_TTL,null));
        userIdentitas.put(KEY_JK,pref.getString(KEY_JK,null));
        userIdentitas.put(KEY_AGAMA,pref.getString(KEY_AGAMA,null));
        return  userIdentitas;
    }
}
