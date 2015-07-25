package model;

import utils.Timestamp_util;

import java.util.Date;

/**
 * Created by blackleones on 20/07/15.
 */
public class Movement {
    private int qta;
    private String date;

    public Movement(int qta){
        this.qta = qta;
        this.date = Timestamp_util.getFormattedCurrentTime();
    }

    public int getQta() {
        return qta;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "\nMovement{" +
                "qta='" + qta + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
