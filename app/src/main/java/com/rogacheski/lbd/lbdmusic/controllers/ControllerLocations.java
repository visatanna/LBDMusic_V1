package com.rogacheski.lbd.lbdmusic.controllers;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.rogacheski.lbd.lbdmusic.R;
import com.rogacheski.lbd.lbdmusic.adapter.HintAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


/**
 * Created by vis_a on 25-Jun-17.
 */

public class ControllerLocations {

    private Context context;
    private Spinner spinner; //zuado não poder passar como parâmetro para carrega paises, deve ter um jeito

    public ControllerLocations(Context context, Spinner spinner) {
        this.context = context;
        this.spinner = spinner;
    }

    public void carregaPaises() {
        AsyncHttpClient client = new AsyncHttpClient();
        try {
            client.get("http://www.lbd.bravioseguros.com.br/musicianrest/country", new JsonHttpResponseHandler() {
                @Override
                public void onStart() {

                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    // called when response HTTP status is "200 OK"
                    ArrayList<String> listaDePaises = carregaListaSpinner(response);
                    carregaSpinner(listaDePaises, 0);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.e("ConexaoErro", "Metodo caiu onFailure , status code: " + statusCode, throwable);
                }

                @Override
                public void onFinish() {

                }
            });
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private ArrayList<String> carregaListaSpinner(JSONObject response) {
        ArrayList<String> listaDelugares = new ArrayList<String>();
        try {
            JSONArray paises = (JSONArray) response.get("data");
            for(int i = 0 ; i < paises.length() ; i ++ ){
                listaDelugares.add(paises.getString(i));
            }
        } catch (JSONException e) {
            Log.e("ConexaoErro", "Erro de JSON no método :onSuccess ", e);
        } catch (Exception e){
            e.printStackTrace();
        }
        return listaDelugares;
    }

    private void carregaSpinner(ArrayList<String> listaDeLocais, int tipoDica) {
         try {
             listaDeLocais.add(0, " ");
             switch (tipoDica) {
                 case 0:
                     listaDeLocais.add("País");
                     break;
                 case 1:
                     listaDeLocais.add("Estado");
                     break;

                 case 2:
                     listaDeLocais.add("Cidade");
                     break;
             }
             HintAdapter<String> spinnerAdapter = new HintAdapter<String>(context, R.layout.simple_spinner_layout, listaDeLocais);
             spinner.setAdapter(spinnerAdapter);
             spinner.setTextAlignment(Spinner.TEXT_ALIGNMENT_CENTER);
             spinner.setSelection(spinnerAdapter.getCount());
         }catch(Exception e){
             e.printStackTrace();
         }
    }

    public void carregaEstados(String sPais) {
        if(sPais.equals(" ") || sPais.equals("País")){
            carregaSpinner(new ArrayList<String>(),1);
            return;
        }
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://www.lbd.bravioseguros.com.br/musicianrest/state/" + sPais, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                ArrayList<String> listaDeEstados = carregaListaSpinner(response);
                carregaSpinner(listaDeEstados,1);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("ConexaoErro", "Metodo caiu onFailure , status code: " + statusCode, throwable);
            }

            @Override
            public void onFinish() {

            }
        });
    }
    public void carregaCidades(String sEstado) {
        AsyncHttpClient client = new AsyncHttpClient();
        if(sEstado.equals(" ") || sEstado.equals("Estado")){
            carregaSpinner(new ArrayList<String>() , 2);
            return;
        }
        client.get("http://www.lbd.bravioseguros.com.br/musicianrest/city/" + sEstado, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                ArrayList<String> listaDePaises = carregaListaSpinner(response);
                carregaSpinner(listaDePaises , 2);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("ConexaoErro", "Metodo caiu onFailure , status code: " + statusCode, throwable);
            }

            @Override
            public void onFinish() {

            }
        });
    }
}
