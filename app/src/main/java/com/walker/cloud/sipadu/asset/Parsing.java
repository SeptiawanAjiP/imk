package com.walker.cloud.sipadu.asset;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Created by ASUS on 22/01/2017.
 */

public class Parsing {

    private Context context;
    private static final String t="Parsing";
    public Parsing(Context context){
        this.context=context;
    }
    public void ParsingJadwal(String isi){
        Log.d(t,"jadwal "+isi);
        ArrayList<ObjectJadwal> jadwals = new ArrayList<>();
        jadwals.add(new ObjectJadwal());
        jadwals.add(new ObjectJadwal());
        jadwals.add(new ObjectJadwal());
        jadwals.add(new ObjectJadwal());
        jadwals.add(new ObjectJadwal());
        jadwals.add(new ObjectJadwal());
        jadwals.add(new ObjectJadwal());
        jadwals.add(new ObjectJadwal());
        jadwals.add(new ObjectJadwal());
        jadwals.add(new ObjectJadwal());
        jadwals.add(new ObjectJadwal());
        jadwals.add(new ObjectJadwal());
        jadwals.add(new ObjectJadwal());
        jadwals.add(new ObjectJadwal());
        jadwals.add(new ObjectJadwal());
        jadwals.add(new ObjectJadwal());
        jadwals.add(new ObjectJadwal());
        jadwals.add(new ObjectJadwal());
        jadwals.add(new ObjectJadwal());
        jadwals.add(new ObjectJadwal());
        jadwals.add(new ObjectJadwal());
        jadwals.add(new ObjectJadwal());
        jadwals.add(new ObjectJadwal());
        jadwals.add(new ObjectJadwal());
        Document doc = Jsoup.parse(isi);
        Element tabElement = doc.select("table").get(0).select("tr").get(1).select("td").get(1).select("table").get(0);
        //System.out.println(""+tabElement.text());
        Elements rows = tabElement.select("tr");

        for (int i=0;i<rows.size();i++){
            Elements colms = rows.get(i).select("td");
            for(int j=1;j<colms.size();j++){
                    if(i==0){
                        int awal = (j-1)*4;
                        jadwals.get(awal).setHari(colms.get(j).html().split("<br>")[0].trim());
                        jadwals.get(awal).setTanggal(colms.get(j).html().split("<br>")[1].trim().substring(0,6)+"20"+colms.get(j).html().split("<br>")[1].trim().substring(6));
                        jadwals.get(awal+1).setHari(colms.get(j).html().split("<br>")[0].trim());
                        jadwals.get(awal+1).setTanggal(colms.get(j).html().split("<br>")[1].trim().substring(0,6)+"20"+colms.get(j).html().split("<br>")[1].trim().substring(6));
                        jadwals.get(awal+2).setHari(colms.get(j).html().split("<br>")[0].trim());
                        jadwals.get(awal+2).setTanggal(colms.get(j).html().split("<br>")[1].trim().substring(0,6)+"20"+colms.get(j).html().split("<br>")[1].trim().substring(6));
                        jadwals.get(awal+3).setHari(colms.get(j).html().split("<br>")[0].trim());
                        jadwals.get(awal+3).setTanggal(colms.get(j).html().split("<br>")[1].trim().substring(0,6)+"20"+colms.get(j).html().split("<br>")[1].trim().substring(6));

                    }else{
                        if(!colms.get(j).html().trim().equals("<br>")){
                            jadwals.get((j-1)*4+i).setSesi(i);
                            Log.d(t,"number : "+(j-1)*4+i);
                            Log.d(t,"tanggal : "+jadwals.get((j-1)*4+i).getTanggal());
                            Log.d(t,"sesi : "+colms.get(j).html().split("<br>")[0].trim().replaceAll("<b>","").replaceAll("</b>","").trim());
                            Log.d(t,"dosen : "+colms.get(j).html().split("<br>")[1].trim().replaceAll("<font color:\"#0094ff\">Ruang:","").replaceAll("</font>","").trim());
                            Log.d(t,"ruang : "+colms.get(j).html().split("<br>")[2].trim());
                            jadwals.get((j-1)*4+i).setMataKuliah(colms.get(j).html().split("<br>")[0].trim().replaceAll("<b>","").replaceAll("</b>","").trim());
                            jadwals.get((j-1)*4+i).setDosen(colms.get(j).html().split("<br>")[1].trim().replaceAll("<font color:\"#0094ff\">Ruang:","").replaceAll("</font>","").trim());
                            jadwals.get((j-1)*4+i).setRuang(colms.get(j).html().split("<br>")[2].trim());
                        }
                    }

            }

        }
        Database.getInstance(context).InsertJadwal(jadwals);
    }
    public void ParsingBerita(String isi){
        Log.d(t,"berita "+isi);
        ArrayList<ObjectBerita> objectBeritas= new ArrayList<>();
        Document doc = Jsoup.parse(isi);
        Elements  beritas = doc.select("span");
        System.out.println(""+beritas.size());
        ObjectBerita objectBerita=null;
        for(Element berita: beritas){

            if(berita.className().trim().equals("judulBeritaAll")){
                Element beritaText = berita.select("a").get(0);
                if(objectBerita!=null){
                    objectBeritas.add(objectBerita);
                    objectBerita=null;
                }
                if(objectBerita==null){
                    objectBerita= new ObjectBerita();
                    objectBerita.setUrl(beritaText.attr("href").trim());
                    objectBerita.setJudul(beritaText.text());
                }

            }else if(berita.className().trim().equals("rowTglBerita")){
                objectBerita.setHari(berita.text().split("\\|")[1].trim().split(",")[0].trim());
                objectBerita.setTanggal(berita.text().split("\\|")[1].trim().split(",")[0].trim());
                objectBerita.setDari(berita.text().split("\\|")[0].trim());
            }
        }
        Database.getInstance(context).InsertBerita(objectBeritas);

    }
    public String ParsingIsiBerita(String url,String isi){
        Log.d(t,"isiberita "+isi);
        isi=isi.replaceFirst("<body>", "");
        int awal=isi.indexOf("<body>");
        int akhir=isi.indexOf("</body>");
        //System.out.println(awal+"|"+akhir);
        isi=isi.substring(awal,akhir);
        return isi;

    }

    public void Profil(String isi){
        Log.d(t,"profil "+isi);
        Document doc = Jsoup.parse(isi);
        Elements  profileTable = doc.select("div#div_dataProfile").get(0).select("table").get(0).select("tr");
        SessionManager sessionManager = new SessionManager(context);
        sessionManager.createIdentitasUser(profileTable.get(0).select("td").get(1).text().trim(),
                doc.select("div#identity").get(0).text().split(",")[2].trim(),
                profileTable.get(3).select("td").get(1).text().trim(),
                profileTable.get(4).select("td").get(1).text().trim(),
                profileTable.get(5).select("td").get(1).text().trim());
    }
    public void Absensi(String isi){
        Log.d(t,"absensi "+isi);
        ArrayList <ObjectAbsensi> objectAbsensis = new ArrayList<>();
        Document doc = Jsoup.parse(isi);
        Elements  absensiTable = doc.select("table#tabel_jadwal").get(0).select("tr");
        for(int i=1; i<absensiTable.size();i++){
            objectAbsensis.add(new ObjectAbsensi(absensiTable.get(i).select("td").get(1).text(),absensiTable.get(i).select("td").get(2).text(),
                    absensiTable.get(i).select("td").get(3).text()));
        }
        Log.d("cingire2",objectAbsensis.toString());
        Database.getInstance(context).InsertAbsensi(objectAbsensis);
    }

}
