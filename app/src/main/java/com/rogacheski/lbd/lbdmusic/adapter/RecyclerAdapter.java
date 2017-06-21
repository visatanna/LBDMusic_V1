package com.rogacheski.lbd.lbdmusic.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.rogacheski.lbd.lbdmusic.R;
import com.rogacheski.lbd.lbdmusic.entity.ReviewsEntity;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.evaluateHolder> {

    private List<ReviewsEntity> mEvaluates;

    public RecyclerAdapter(List<ReviewsEntity> evaluates) {
        mEvaluates = evaluates;
    }

    @Override
    public RecyclerAdapter.evaluateHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.evaluate_row, parent, false);
        return new evaluateHolder(inflatedView);

    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.evaluateHolder holder, int position) {
        ReviewsEntity itemEvaluate = mEvaluates.get(position);
        holder.bindEvaluate(itemEvaluate);
    }

    @Override
    public int getItemCount() {
        return mEvaluates.size();
    }

    public static class evaluateHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mItemImage;
        private TextView mItemName;
        private TextView mItemDate;
        private TextView mItemDescription;
        private RatingBar mItemGrade;

        public evaluateHolder(View v) {
            super(v);

            mItemImage = (ImageView) v.findViewById(R.id.picture);
            mItemName = (TextView) v.findViewById(R.id.nameEvaluate);
            mItemDate = (TextView) v.findViewById(R.id.dateEvaluate);
            mItemDescription = (TextView) v.findViewById(R.id.decriptionEvaluate);
            mItemGrade = (RatingBar) v.findViewById(R.id.ratingStarsEvaluate);
            v.setOnClickListener(this);
        }

        public void bindEvaluate(ReviewsEntity eval) {
            SimpleDateFormat stf = new SimpleDateFormat("yyyy-MM-dd");
            Picasso.with(mItemImage.getContext()).load(eval.getsEvaluatorsImage()).into(mItemImage);
            mItemName.setText(eval.getsEvaluators_Name());
            mItemDate.setText(stf.format(eval.getdData_Review()));
            mItemDescription.setText(eval.getDescription());
            mItemGrade.setRating(eval.getiGrade());

        }

        //5
        @Override
        public void onClick(View v) {
            Log.d("RecyclerView", "CLICK!");
        }
    }
}

