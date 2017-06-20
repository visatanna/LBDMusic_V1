package com.rogacheski.lbd.lbdmusic.controllers;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.rogacheski.lbd.lbdmusic.ProfileMusicianActivity;
import com.rogacheski.lbd.lbdmusic.entity.BandEntity;
import com.rogacheski.lbd.lbdmusic.entity.ConcertDayEntity;
import com.rogacheski.lbd.lbdmusic.entity.ReviewsEntity;
import com.rogacheski.lbd.lbdmusic.entity.TagEntity;


import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static java.lang.Thread.sleep;

/**
 * Created by vis_a on 18-Jun-17.
 */

public class ControllerBanda {

    ProfileMusicianActivity profileMusicianActivity;

    public ControllerBanda(ProfileMusicianActivity profileMusicianActivity) {
        this.profileMusicianActivity = profileMusicianActivity;
    }

    public  void carregaBanda(int id ) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://www.lbd.bravioseguros.com.br/musicianrest/musicianmain/"+Integer.toString(id),new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // called when response HTTP status is "200 OK"
                try {
                    BandEntity bandaR = new BandEntity();
                    JSONObject dataJson= (JSONObject) response.get("data");
                    String id = dataJson.get("idUsuario").toString();
                    JSONObject genreJson= (JSONObject) response.get("genres");
                    JSONObject evaluatesJson= (JSONObject) response.get("evaluates");
                    JSONObject concertDaysJson = (JSONObject) response.get("concertdays");
                    if(!id.equals("false")) {
                        bandaR.setIdUsuario(Integer.parseInt(id));
                        addInformacoesBasicasBanda(bandaR , dataJson);
                        addTagsBanda(bandaR , genreJson);
                        addReviews(bandaR ,evaluatesJson);
                        addConcertDays(bandaR ,concertDaysJson );
                    }
                    profileMusicianActivity.jbInit(bandaR);

                }catch(JSONException e) {
                    Log.e("ConexaoErro", "Erro de JSON no método :onSuccess ", e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("ConexaoErro", "Metodo caiu onFailure , status code: " +statusCode , throwable);
            }

            @Override
            public void onFinish() {

            }

        });
    }

    private static void addConcertDays(BandEntity banda, JSONObject concertDaysJson) {
        try{
            SimpleDateFormat stf = new SimpleDateFormat("yyyy-MM-dd");
            Iterator<String>  iterConcertDays = concertDaysJson.keys();
            ArrayList<ConcertDayEntity> listaConcertDays = new ArrayList<ConcertDayEntity>();
            while(iterConcertDays.hasNext()){
                String key = iterConcertDays.next();

                JSONObject showObjectJson  = (JSONObject)concertDaysJson.get(key);
                ConcertDayEntity concertDay = new ConcertDayEntity();
                concertDay.setBusyDay(stf.parse(showObjectJson.get("busyDay").toString()));
                concertDay.setId_Calendar(Integer.parseInt(showObjectJson.get("idConcertDays").toString()) );
                concertDay.setId_User(Integer.parseInt(showObjectJson.get("idUsuario").toString()));
                listaConcertDays.add(concertDay);
            }
            banda.setListaConcertDays(listaConcertDays);
        }catch (JSONException e){
            Log.e("ConexaoErro", "Erro de JSON no método :addConcertDays ", e);
        } catch (java.text.ParseException e1) {
            Log.e("ConexaoErro", "Erro de JSON no método :addConcertDays ", e1);
        }
    }

    private static void addReviews(BandEntity banda, JSONObject evaluatesJson) {
        try{
            SimpleDateFormat stf = new SimpleDateFormat("yyyy-MM-dd");
            Iterator<String> iterEvaluate = evaluatesJson.keys();
            ArrayList<ReviewsEntity> listaReviews = new ArrayList<ReviewsEntity>();
            float soma = 0;
            int nroReviews = 0;
            while(iterEvaluate.hasNext()) {
                String key = iterEvaluate.next();

                JSONObject evaluateJson = (JSONObject) evaluatesJson.get(key);
                ReviewsEntity review = new ReviewsEntity();
                review.setsEvaluators_Name(evaluateJson.get("fantasyname").toString());
                review.setsEvaluatorsImage(evaluateJson.get("userpicture").toString());
                review.setDescription(evaluateJson.get("description").toString());
                review.setiGrade(Float.parseFloat(evaluateJson.get("grade").toString()));
                soma += review.getiGrade();

                review.setdData_Review(stf.parse(evaluateJson.get("datecreated").toString()));
                listaReviews.add(review);
                nroReviews++;
            }
            banda.setListaReviews(listaReviews);
            banda.setAverageRating(soma/nroReviews);
        }catch (JSONException e){
            Log.e("ConexaoErro", "Erro de JSON no método :addReviews ", e);
        } catch (java.text.ParseException e1) {
            Log.e("ConexaoErro", "Erro de parse no método :addReviews ", e1);
        }
    }

    private static void addTagsBanda(BandEntity banda, JSONObject genreJson) {
        try{Iterator<String> iterGenero = genreJson.keys();
            ArrayList<TagEntity> listaTags = new ArrayList<TagEntity>();
            while(iterGenero.hasNext()) {
                String key = iterGenero.next();

                    TagEntity tag = new TagEntity(genreJson.get(key).toString());
                    listaTags.add(tag);
            }
            banda.setTags(listaTags);
        } catch (JSONException e){
            Log.e("ConexaoErro", "Erro no método addTagsBanda: ", e);
        }
    }

    private static void addInformacoesBasicasBanda(BandEntity banda, JSONObject dataJson) {
        try{
            banda.setsNomeBanda(dataJson.get("fantasyName").toString());
            banda.setdImagemBanda(dataJson.get("profileImage").toString());
            banda.setdImagemDescBanda(dataJson.get("backpicture").toString());
            banda.setsDescricaoBanda(dataJson.get("description").toString());
            banda.setnMembers(Integer.parseInt(dataJson.get("nMembers").toString()));
        }catch(Exception e){
            Log.e("ConexaoErro", "Erro no método informacoesBasicasBanda: ", e);
        }

    }

    public static List<ConcertDayEntity> getConcertDaysById(int id){
        final ArrayList<ConcertDayEntity> listaConcertDays = new ArrayList<ConcertDayEntity>();
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://www.lbd.bravioseguros.com.br/concertdaysrest/searchusuario/"+Integer.toString(id),new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // called when response HTTP status is "200 OK"
                try {
                    JSONObject concertDaysJson= (JSONObject) response.get("data");
                    //TODO
                }catch(JSONException e){
                    //...
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

            }

            @Override
            public void onFinish() {

            }

        });
        return listaConcertDays;
    }
}
