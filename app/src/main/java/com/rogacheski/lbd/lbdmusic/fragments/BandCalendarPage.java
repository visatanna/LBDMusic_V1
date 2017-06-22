package com.rogacheski.lbd.lbdmusic.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.rogacheski.lbd.lbdmusic.R;
import com.rogacheski.lbd.lbdmusic.controllers.ControllerBanda;
import com.rogacheski.lbd.lbdmusic.controllers.DiaOcupadoDecorator;
import com.rogacheski.lbd.lbdmusic.entity.BandEntity;
import com.rogacheski.lbd.lbdmusic.entity.ConcertDayEntity;
import com.rogacheski.lbd.lbdmusic.entity.RetornoConcertDayEntity;
import com.rogacheski.lbd.lbdmusic.session.Session;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import cz.msebera.android.httpclient.Header;

/**
 * Created by vis_a on 28-Apr-17.
 */

public class BandCalendarPage extends BaseFragment implements Observer {
    Handler handle = new Handler();
    ArrayList<ConcertDayEntity> listaDias = new ArrayList<ConcertDayEntity>();
    View viewCalendario;
    BandEntity banda;
    boolean isBandaTela;
    MaterialCalendarView calendario;
    ControllerBanda controller;

    public BandCalendarPage(){

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        banda = (BandEntity)getArgument();

        viewCalendario = inflater.inflate( R.layout.fragment_calendar , container ,false);

        controller = new ControllerBanda(this);

        ImageView showtimeImage = (ImageView)viewCalendario.findViewById(R.id.ShowTime);
        showtimeImage.setImageDrawable(getContext().getDrawable(R.drawable.showtime));

        calendario = (MaterialCalendarView)viewCalendario.findViewById(R.id.calendar_view);

        trataDatasDaBanda(banda, calendario);

        calendario.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                if(selected) {
                    alteraStatusDoItem(date, widget);
                }
            }
        });
        return viewCalendario;
    }


    public void trataDatasDaBanda(BandEntity banda , MaterialCalendarView calendario){
        if(banda.getListaConcertDays() != null){
            for(ConcertDayEntity day : banda.getListaConcertDays()) {
                listaDias.add(day);
                calendario.addDecorator(new DiaOcupadoDecorator(day.getBusyDay() , getResources()));
            }
        }

    }

    private void alteraStatusDoItem(CalendarDay objetoCalendario , MaterialCalendarView calendario ){

        Date diaSelecionado = objetoCalendario.getDate();
        ConcertDayEntity objetoConcertDay = new ConcertDayEntity(objetoCalendario.getDate());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        if (listaDias.contains(objetoConcertDay)) {
            int index = listaDias.indexOf(objetoConcertDay);
            DiaOcupadoDecorator decoraDia = new DiaOcupadoDecorator(diaSelecionado , getResources());
            controller.deleteConcertDays(listaDias.get(index));
            calendario.removeDecorator(decoraDia);
            listaDias.remove(objetoConcertDay);
            Toast.makeText(getActivity(),"O dia "+ sdf.format(diaSelecionado) + " foi ativado para novos contratos" ,Toast.LENGTH_LONG).show();
        } else {
            ConcertDayEntity diaDeShowNovo = new ConcertDayEntity(getIdUser() ,diaSelecionado);
            DiaOcupadoDecorator decoraDia = new DiaOcupadoDecorator(diaSelecionado , getResources());
            calendario.addDecorator(decoraDia);
            listaDias.add(diaDeShowNovo);
            Toast.makeText(getActivity() ,"O dia " + sdf.format(diaSelecionado) + " foi bloqueado para novos contratos" ,Toast.LENGTH_LONG ).show();
            controller.insertConcertDays(diaDeShowNovo , getIdUser()+"");
        }

    }

    @Override
    public void update(Observable o, Object arg) {
        RetornoConcertDayEntity retornoDaQuery = (RetornoConcertDayEntity)arg;
        if(!retornoDaQuery.isOk()){
            ConcertDayEntity ConcertDayErro = retornoDaQuery.getConcertDay();
            if(retornoDaQuery.getOperacao() == RetornoConcertDayEntity.OPERACAO_INSERT){
                if (listaDias.contains(ConcertDayErro)) {
                    listaDias.remove(ConcertDayErro);
                    DiaOcupadoDecorator decoraDia = new DiaOcupadoDecorator(ConcertDayErro.getBusyDay(), getResources());
                    calendario.removeDecorator(decoraDia);
                }
            }else{
                DiaOcupadoDecorator decoraDia = new DiaOcupadoDecorator(ConcertDayErro.getBusyDay() , getResources());
                calendario.addDecorator(decoraDia);
                listaDias.add(ConcertDayErro);
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Toast.makeText(getActivity() ,"Ocorreu um erro , não foi possivel alterar o dia " + sdf.format(ConcertDayErro.getBusyDay()) ,Toast.LENGTH_LONG ).show();
        }
    }
}

