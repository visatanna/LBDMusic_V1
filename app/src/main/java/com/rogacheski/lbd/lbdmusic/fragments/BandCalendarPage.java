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
import com.rogacheski.lbd.lbdmusic.controllers.DiaOcupadoDecorator;
import com.rogacheski.lbd.lbdmusic.entity.ConcertDayEntity;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

/**
 * Created by vis_a on 28-Apr-17.
 */

public class BandCalendarPage extends BaseFragment {
    Handler handle = new Handler();
    ArrayList<ConcertDayEntity> listaDias = new ArrayList<ConcertDayEntity>();
    ArrayList<DiaOcupadoDecorator> listaDecorator = new ArrayList<DiaOcupadoDecorator>();
    View viewCalendario;
    BandEntity banda;

    public BandCalendarPage(){

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        banda = (BandEntity)getArgument();

        viewCalendario = inflater.inflate( R.layout.fragment_calendar , container ,false);

        ImageView showtimeImage = (ImageView)viewCalendario.findViewById(R.id.ShowTime);
        showtimeImage.setImageDrawable(getContext().getDrawable(R.drawable.showtime));

        //listaDias.addAll(getDatasDaBanda(banda.getIdUser()));

        MaterialCalendarView calendario = (MaterialCalendarView)viewCalendario.findViewById(R.id.calendar_view);


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




    public int getLastCalendarId(){
        //SELECT TOP(1) FROM CONCERTDAYS
        int maiorId = 18;
        return maiorId;
    }
    private BandCalendarPage returnThis(){
        return this;
    }

    public ArrayList<ConcertDayEntity> getDatasDaBanda(int idUser){
        //SELECT idConcertDays , busyDay , idUsuario , DESCRIPTION , ATIVO
        //FROM CONCERTDAYS
        //WHERE idUsuario = idUsuario
        ArrayList<ConcertDayEntity> ListaDias = new ArrayList<ConcertDayEntity>();

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("idUsuario",idUser);

        final RequestHandle requestHandle = client.get("http://www.lbd.bravioseguros.com.br/??????", params, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // called when response HTTP status is "200 OK"
                try {
                    int a = 0;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return ListaDias;
    }

    private void alteraStatusDoItem(CalendarDay objetoCalendario , MaterialCalendarView calendario ){

        Date diaSelecionado = objetoCalendario.getDate();
        ConcertDayEntity objetoConcertDay = new ConcertDayEntity(objetoCalendario.getDate());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        if (listaDias.contains(objetoConcertDay)) {
            listaDias.remove(objetoConcertDay);
            DiaOcupadoDecorator decoraDia = new DiaOcupadoDecorator(diaSelecionado , getResources());
            calendario.removeDecorator(decoraDia);
            Toast.makeText(getActivity(),"O dia "+ sdf.format(diaSelecionado) + " foi ativado para novos contratos" ,Toast.LENGTH_LONG).show();
        } else {
            ConcertDayEntity diaDeShowNovo = new ConcertDayEntity(banda.getIdUser(),diaSelecionado);
            DiaOcupadoDecorator decoraDia = new DiaOcupadoDecorator(diaSelecionado , getResources());
            calendario.addDecorator(decoraDia);
            listaDias.add(diaDeShowNovo);
            Toast.makeText(getActivity() ,"O dia " + sdf.format(diaSelecionado) + " foi bloqueado para novos contratos" ,Toast.LENGTH_LONG ).show();
        }

    }


}

