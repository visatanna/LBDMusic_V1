package com.rogacheski.lbd.lbdmusic.controllers;

import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.rogacheski.lbd.lbdmusic.R;

import java.util.Date;

/**
 * Created by vis_a on 18-Jun-17.
 */

public class DiaOcupadoDecorator implements DayViewDecorator {

    private Date diaDecorado;
    private Resources resources;

    public DiaOcupadoDecorator(Date day , Resources resources){
        diaDecorado = day;
        this.resources = resources;
    }
    @Override
    public boolean shouldDecorate(CalendarDay day) {
        if(day.getDate().equals(diaDecorado)){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setBackgroundDrawable(new ColorDrawable(resources.getColor(R.color.DarkBlue)));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DiaOcupadoDecorator) {
            DiaOcupadoDecorator day = (DiaOcupadoDecorator) obj;
            if (this.getDiaDecorado().equals(day.getDiaDecorado())) {
                return true;
            } else {
                return false;
            }
        }else{
            return false;
        }
    }
    public Date getDiaDecorado() {
        return diaDecorado;
    }

    public void setDiaDecorado(Date diaDecorado) {
        this.diaDecorado = diaDecorado;
    }

    public Resources getResources() {
        return resources;
    }

    public void setResources(Resources resources) {
        this.resources = resources;
    }
}
