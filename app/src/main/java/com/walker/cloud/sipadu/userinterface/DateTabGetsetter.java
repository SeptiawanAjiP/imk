package com.walker.cloud.sipadu.userinterface;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Rahadi on 05/05/2016.
 */
public class DateTabGetsetter {

    Date date;
    int sesi[];

    public String getDate() {
        String format = "dd-MM-yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setSesi(int[] sesi) {
        this.sesi = sesi;
    }

    public int[] getSesi() {
        return sesi;
    }
}
