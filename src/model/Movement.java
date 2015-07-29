package model;

import utils.Timestamp_util;

/**
 * Created by blackleones on 28/07/15.
 */
public class Movement {
    private int qta;
    private String reason;
    private String date;

    public Movement(int qta, String reason){
        this.qta = qta;
        this.reason = reason;
        date = Timestamp_util.getFormattedCurrentTime();
    }

    Movement(int qta, String reason, String date){
        this.qta = qta;
        this.reason = reason;
        this.date = date;
    }

    public int getQta() {
        return qta;
    }

    public String getReason() {
        return reason;
    }

    public String getDate() {
        return date;
    }
}
