package com.walker.cloud.sipadu.asset;

/**
 * Created by ASUS on 22/01/2017.
 */

public class ObjectJadwal {
    private int Id;
    private String Tanggal;
    private String MataKuliah;
    private String Dosen;
    private int Sesi;
    private String Hari;
    private String Ruang;

    public ObjectJadwal(){

    }

    public ObjectJadwal(int Id, String Tanggal, String MataKuliah, String Dosen, int Sesi, String Hari, String Ruang){
        this.Id=Id;
        this.Tanggal=Tanggal;
        this.MataKuliah=MataKuliah;
        this.Dosen=Dosen;
        this.Sesi=Sesi;
        this.Hari=Hari;
        this.Ruang=Ruang;
    }

    public String getTanggal() {
        return Tanggal;
    }

    public void setTanggal(String tanggal) {
        Tanggal = tanggal;
    }

    public String getMataKuliah() {
        return MataKuliah;
    }

    public void setMataKuliah(String mataKuliah) {
        MataKuliah = mataKuliah;
    }

    public String getDosen() {
        return Dosen;
    }

    public void setDosen(String dosen) {
        Dosen = dosen;
    }

    public int getSesi() {
        return Sesi;
    }

    public void setSesi(int sesi) {
        Sesi = sesi;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getHari() {
        return Hari;
    }

    public void setHari(String hari) {
        Hari = hari;
    }

    public String getRuang() {
        return Ruang;
    }

    public void setRuang(String ruang) {
        Ruang = ruang;
    }
}
