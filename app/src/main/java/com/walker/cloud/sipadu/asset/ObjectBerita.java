package com.walker.cloud.sipadu.asset;

/**
 * Created by ASUS on 22/01/2017.
 */

public class ObjectBerita {
    private int Id;
    private String Hari;
    private String Tanggal;
    private String Dari;
    private String Judul;
    private String Url;
    private String IsiBerita;
    private String inisial;

    public ObjectBerita(){

    }

    public ObjectBerita(int Id, String Hari,String Tanggal, String Dari, String Judul, String Url,String inisial){
        this.Id=Id;
        this.Hari=Hari;
        this.Tanggal=Tanggal;
        this.Dari=Dari;
        this.Judul=Judul;
        this.Url=Url;
        this.inisial=inisial;
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

    public String getTanggal() {
        return Tanggal;
    }

    public void setTanggal(String tanggal) {
        Tanggal = tanggal;
    }

    public String getDari() {
        return Dari;
    }

    public void setDari(String dari) {
        Dari = dari;
    }

    public String getJudul() {
        return Judul;
    }

    public void setJudul(String judul) {
        Judul = judul;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getIsiBerita() {
        return IsiBerita;
    }

    public void setIsiBerita(String isiBerita) {
        IsiBerita = isiBerita;
    }

    public String getInisial() {
        return inisial;
    }

    public void setInisial(String inisial) {
        this.inisial = inisial;
    }
}
