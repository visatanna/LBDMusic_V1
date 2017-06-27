package com.rogacheski.lbd.lbdmusic.controllers;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.rogacheski.lbd.lbdmusic.R;
import com.rogacheski.lbd.lbdmusic.adapter.HintAdapter;
import com.rogacheski.lbd.lbdmusic.entity.SearchResultEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

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
                    carregaPaises();
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
             listaDeLocais.add(0, context.getString(R.string.vazioSpinner));
             switch (tipoDica) {
                 case 0:
                     listaDeLocais.add(context.getString(R.string.Pais));
                     break;
                 case 1:
                     listaDeLocais.add(context.getString(R.string.Estado));
                     break;

                 case 2:
                     listaDeLocais.add(context.getString(R.string.Cidade));
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

    public void carregaEstados(final String sPais) {
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
                carregaEstados(sPais);
            }

            @Override
            public void onFinish() {

            }
        });
    }
    public void carregaCidades(final String sEstado) {
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
                carregaCidades(sEstado);
            }

            @Override
            public void onFinish() {

            }
        });
    }




    public void buscarUsuarios(String pais, String estado , String cidade) {
        if(estado.equals(context.getString(R.string.Estado)) || estado.equals(context.getString(R.string.vazioSpinner)) ){
            buscarUsuariosPorPais(pais);
        }
        else if(cidade.equals(context.getString(R.string.Cidade)) || cidade.equals(context.getString(R.string.vazioSpinner))){
            buscarUsuariosPorEstado(pais,estado);
        }
        else{
            buscarUsuarioPorCidade(pais , estado , cidade);
        }

    }

    private void buscarUsuariosPorPais(final String pais) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://www.lbd.bravioseguros.com.br/musicianrest/state/" + pais.trim()  , new JsonHttpResponseHandler() {
            @Override
            public void onStart() {

            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                 ArrayList<SearchResultEntity> listaCards = carregaCards(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                buscarUsuariosPorPais(pais);
            }

            @Override
            public void onFinish() {

            }
        });
    }

    private ArrayList<SearchResultEntity> carregaCards(JSONObject response) {
        ArrayList<SearchResultEntity> resultSet = new ArrayList<SearchResultEntity>();
        try {
            JSONObject dataJson = (JSONObject) response.get("data");
            String id = dataJson.get("idUsuario").toString();

            if(!id.equals("false")) {
                Iterator<String> iterResult = dataJson.keys();

                while(iterResult.hasNext()) {
                    String key = iterResult.next();
                    JSONObject card = (JSONObject) dataJson.get(key);
                    int idUser       =  Integer.parseInt(card.get("idUsuario").toString());
                    String fantasyName  =  card.get("fantasyName").toString();
                    String profileImage =  card.get("profileImage").toString();
                    //TODO average
                    SearchResultEntity cardEntity = new SearchResultEntity(idUser , fantasyName , profileImage , 0.0f);
                    resultSet.add(cardEntity);
                }
            }
        }catch(JSONException e ){
            e.printStackTrace();
        }
        return resultSet;
    }

    private void buscarUsuariosPorEstado(final String pais,final String estado) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://www.lbd.bravioseguros.com.br/musicianrest/state/" + pais.trim() + "/" + estado.trim()  , new JsonHttpResponseHandler() {
            @Override
            public void onStart() {

            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                ArrayList<SearchResultEntity> listaCards = carregaCards(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                buscarUsuariosPorEstado(pais , estado);
            }

            @Override
            public void onFinish() {

            }
        });
    }



    private void buscarUsuarioPorCidade(final String pais ,final String estado ,final String cidade) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://www.lbd.bravioseguros.com.br/musicianrest/state/" + pais.trim() + "/" + estado.trim() + "/" + cidade.trim() , new JsonHttpResponseHandler() {
            @Override
            public void onStart() {

            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                ArrayList<SearchResultEntity> listaCards = carregaCards(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                buscarUsuarioPorCidade(pais , estado , cidade);
            }

            @Override
            public void onFinish() {

            }
        });
    }
}
