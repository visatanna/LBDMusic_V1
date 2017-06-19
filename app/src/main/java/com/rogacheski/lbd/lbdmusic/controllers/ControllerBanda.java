package com.rogacheski.lbd.lbdmusic.controllers;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
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

/**
 * Created by vis_a on 18-Jun-17.
 */

public class ControllerBanda {
    public static BandEntity getBandaWithId(int id  ) {
        final BandEntity banda = new BandEntity();
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("www.lbl.bravioseguros.com.br/musicianrest/musicianmain/"+Integer.toString(id),new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // called when response HTTP status is "200 OK"
                try {
                    JSONObject dataJson= (JSONObject) response.get("data");
                    String id = dataJson.get("idUsuario").toString();
                    JSONObject genreJson= (JSONObject) response.get("genres");
                    JSONObject evaluatesJson= (JSONObject) response.get("evaluates");
                    if(!id.equals("false")) {
                        banda.setsNomeBanda(dataJson.get("fantasyName").toString());
                        banda.setdImagemBanda(dataJson.get("profileImage").toString());
                        banda.setdImagemDescBanda(dataJson.get("backpicture").toString());
                        banda.setsDescricaoBanda(dataJson.get("description").toString());
                        banda.setnMembers(Integer.parseInt(dataJson.get("nMembers").toString()));
                        Iterator<String> iterGenero = genreJson.keys();
                        ArrayList<TagEntity> listaTags = new ArrayList<TagEntity>();
                        while(iterGenero.hasNext()) {
                             String key = iterGenero.next();
                             try{
                                TagEntity tag = new TagEntity(genreJson.get(key).toString());
                                listaTags.add(tag);
                             } catch (JSONException e){
                                // Something went wrong!
                             }
                        }
                        banda.setTags(listaTags);
                        Iterator<String> iterEvaluate = evaluatesJson.keys();
                        ArrayList<ReviewsEntity> listaReviews = new ArrayList<ReviewsEntity>();
                        SimpleDateFormat stf = new SimpleDateFormat("yyyyMMdd");
                        int soma = 0;
                        int nroReviews = 0;
                        while(iterEvaluate.hasNext()){
                            String  key = iterEvaluate.next();
                            try{
                                JSONObject evaluateJson  = (JSONObject)evaluatesJson.get(key);
                                ReviewsEntity review = new ReviewsEntity();
                                review.setsEvaluators_Name(evaluateJson.get("fantasyname").toString());
                                review.setsEvaluatorsImage(evaluateJson.get("userpicture").toString());
                                review.setDescription(evaluateJson.get("description").toString());
                                review.setiGrade(Integer.parseInt(evaluateJson.get("grade").toString()));
                                soma += review.getiGrade();

                                review.setdData_Review(stf.parse(evaluateJson.get("datecreated").toString()));
                                listaReviews.add(review);
                                nroReviews ++;
                            }catch (JSONException e){
                                e.printStackTrace();
                            } catch (java.text.ParseException e1) {
                                e1.printStackTrace();
                            }
                            banda.setListaReviews(listaReviews);
                            banda.setAverageRating(soma/nroReviews);
                        }

                    } else {

                    }

                }catch(JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

            }

            @Override
            public void onFinish() {

            }

        });
        return banda;
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
