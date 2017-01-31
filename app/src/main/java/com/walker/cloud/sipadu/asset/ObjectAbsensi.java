package com.walker.cloud.sipadu.asset;

/**
 * Created by ASUS on 22/01/2017.
 */

public class ObjectAbsensi {
    private String Matkul;
    private String Dosen;
    private String Absensi;
    private String hurufDepan;

    public  ObjectAbsensi(){

    }
    public  ObjectAbsensi(String Matkul, String Dosen, String Absensi){
        this.Matkul=Matkul;
        this.Dosen=Dosen;
        this.Absensi=Absensi;
    }
    public String getMatkul() {
        return Matkul;
    }

    public void setMatkul(String matkul) {
        Matkul = matkul;
    }

    public String getDosen() {
        return Dosen;
    }

    public void setDosen(String dosen) {
        Dosen = dosen;
    }

    public String getAbsensi() {
        return Absensi;
    }

    public void setAbsensi(String absensi) {
        Absensi = absensi;
    }

    public String getHurufDepan() {
        return hurufDepan;
    }

    public void setHurufDepan(String hurufDepan) {
        this.hurufDepan = hurufDepan;
    }
}
