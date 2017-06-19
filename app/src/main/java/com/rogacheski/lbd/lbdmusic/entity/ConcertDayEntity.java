package com.rogacheski.lbd.lbdmusic.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * Created by vis_a on 15-Jun-17.
 */

public class ConcertDayEntity {
    int id_User;
    int id_Calendar;
    Date busyDay ;

    public ConcertDayEntity() {

    }

    public ConcertDayEntity(Date busyDay) {
        this.busyDay = busyDay;
    }

    public ConcertDayEntity(int id_User, Date busyDay) {
        this.id_User = id_User;
        this.busyDay = busyDay;
    }

    public int getId_Calendar() {
        return id_Calendar;
    }

    public void setId_Calendar(int id_Calendar) {
        this.id_Calendar = id_Calendar;
    }


    public Date getBusyDay() {
        return busyDay;
    }

    public void setBusyDay(Date busyDay) {
        this.busyDay = busyDay;
    }


    public int getId_User() {
        return id_User;
    }

    public void setId_User(int id_User) {
        this.id_User = id_User;
    }

    @Override
    public boolean equals (Object o){
        if (o instanceof ConcertDayEntity) {
            ConcertDayEntity day = (ConcertDayEntity) o;
            if (this.getBusyDay().equals(day.getBusyDay())) {
                return true;
            } else {
                return false;
            }
        }else{
            return false;
        }
    }

}
