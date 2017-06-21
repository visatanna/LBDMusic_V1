package com.rogacheski.lbd.lbdmusic.fragments;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.rogacheski.lbd.lbdmusic.R;
import com.rogacheski.lbd.lbdmusic.adapter.RecyclerAdapter;
import com.rogacheski.lbd.lbdmusic.entity.BandEntity;
import com.rogacheski.lbd.lbdmusic.entity.ContatoEntity;
import com.rogacheski.lbd.lbdmusic.singleton.PicassoSingleton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Vinicius on 28-Apr-17.
 */

public class BandReviewsPage extends BaseFragment implements View.OnClickListener, PicassoSingleton.PicassoCallbacksInterface{
    private List<Integer> listaTiposQueContemIcones = Arrays.asList(5,8,10);
    private ArrayList<ContatoEntity> listaIcones = new ArrayList<ContatoEntity>();
    private View view;
    private int numeroDeIcones = 0;

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerAdapter mAdapter;


    public BandReviewsPage(){
        //TODO Se considerarmos que podem ter mais de 3 tipos de contatos com icones
        //TODO talvez seja melhor fazer disso um objeto do tipo tablayout

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_review,container , false );

        BandEntity banda = (BandEntity)getArgument();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mAdapter = new RecyclerAdapter(banda.getListaReviews());
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onPicassoSuccessCallback() {

    }

    @Override
    public void onPicassoErrorCallback() {

    }
}
