package com.walker.cloud.sipadu.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.walker.cloud.sipadu.R;
import com.walker.cloud.sipadu.asset.ObjectBerita;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Septiawan Aji Pradan on 1/23/2017.
 */

public class BeritaAdapter extends BaseAdapter {
    Activity activity;
    List<ObjectBerita> listItem;

    public BeritaAdapter(Activity activity, List<ObjectBerita> listItem){
        this.activity = activity;
        this.listItem = listItem;
    }

    @Override
    public int getCount() {
        return listItem.size();
    }

    @Override
    public Object getItem(int position) {
        return listItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder = null;

        if(view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.berita_overview_list, null);
            holder.inisial = (TextView)view.findViewById(R.id.inisial_berita);
            holder.berita = (TextView)view.findViewById(R.id.nama_berita);
            holder.tanggal = (TextView)view.findViewById(R.id.tanggal_berita);
            holder.pengirim = (TextView)view.findViewById(R.id.pengirim_berita);
//            if(activity.getClass().getSimpleName().equals("HomeActivity")) {
//                view = inflater.inflate(R.layout.berita_overview_list, null);
//                holder.inisial = (TextView)view.findViewById(R.id.inisial_berita);
//                holder.berita = (TextView)view.findViewById(R.id.nama_berita);
//                holder.tanggal = (TextView)view.findViewById(R.id.tanggal_berita);
//                holder.pengirim = (TextView)view.findViewById(R.id.pengirim_berita);
//            } else if(activity.getClass().getSimpleName().equals("BeritaActivity")) {
//                view = inflater.inflate(R.layout.berita_list, null);
//                holder.inisial = (TextView)view.findViewById(R.id.berita_txt_item_icon);
//                holder.berita = (TextView)view.findViewById(R.id.berita_txt_item_judul);
//                holder.tanggal = (TextView)view.findViewById(R.id.berita_txt_item_tgl);
//                holder.pengirim = (TextView)view.findViewById(R.id.berita_txt_item_penulis);
//            }

//            holder.berita.setSelected(true);
            view.setTag(holder);
        } else {
            holder = (ViewHolder)view.getTag();
        }

        ObjectBerita getset = (ObjectBerita) getItem(position);
        holder.inisial.setText(getset.getInisial());
        holder.berita.setText(getset.getJudul());
        holder.tanggal.setText(getset.getTanggal());
        holder.pengirim.setText(getset.getDari());

        return view;
    }

    static class ViewHolder {
        TextView inisial, berita, tanggal, pengirim;
    }
}
