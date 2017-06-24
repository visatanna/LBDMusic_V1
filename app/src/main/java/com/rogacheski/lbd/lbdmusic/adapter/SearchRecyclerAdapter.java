package com.rogacheski.lbd.lbdmusic.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.rogacheski.lbd.lbdmusic.ProfileMusicianActivity;
import com.rogacheski.lbd.lbdmusic.R;
import com.rogacheski.lbd.lbdmusic.entity.BandEntity;
import com.rogacheski.lbd.lbdmusic.entity.ReviewsEntity;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;


public class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchRecyclerAdapter.evaluateHolder> {

    private List<BandEntity> mEvaluates;

    public SearchRecyclerAdapter(List<BandEntity> evaluates) {
        mEvaluates = evaluates;
    }


    @Override
    public SearchRecyclerAdapter.evaluateHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.evaluate_row_search, parent, false);
        return new evaluateHolder(inflatedView);

    }

    @Override
    public void onBindViewHolder(SearchRecyclerAdapter.evaluateHolder holder, int position) {
        BandEntity itemEvaluate = mEvaluates.get(position);
        holder.bindEvaluate(itemEvaluate);
    }

    @Override
    public int getItemCount() {
        return mEvaluates.size();
    }

    public static class evaluateHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mItemImage;
        private TextView mItemName;
        private RatingBar mItemGrade;
        private int id;

        public evaluateHolder(View v) {
            super(v);
            mItemImage = (ImageView) v.findViewById(R.id.backgroundSearch);
            mItemName = (TextView) v.findViewById(R.id.nameEvaluateSearch);
            mItemGrade = (RatingBar) v.findViewById(R.id.ratingStarsEvaluateSearch);
            v.setOnClickListener(this);
        }

        public void bindEvaluate(BandEntity eval) {
            id = eval.getIdUsuario();
            //mItemImage.setBackgroundColor(R.color.black);
            Picasso.with(mItemImage.getContext()).load(eval.getdImagemBanda()).into(mItemImage);
            mItemImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
            mItemImage.setImageAlpha(100);

            mItemName.setText(eval.getsNomeBanda());
            mItemGrade.setRating((float)eval.getAverageRating());

        }

        //5
        @Override
        public void onClick(View v) {

        }

    }
}

