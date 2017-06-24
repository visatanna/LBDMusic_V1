package com.rogacheski.lbd.lbdmusic.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.rogacheski.lbd.lbdmusic.R;
import com.rogacheski.lbd.lbdmusic.entity.BandEntity;
import com.squareup.picasso.Picasso;

import java.util.List;


public class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchRecyclerAdapter.SearchHolder> {

    private List<BandEntity> searchResult;

    public SearchRecyclerAdapter(List<BandEntity> searchResult) {
        this.searchResult = searchResult;
    }

    @Override
    public SearchHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_row, parent, false);
        return new SearchHolder(inflatedView);

    }

    @Override
    public void onBindViewHolder(SearchHolder holder, int position) {
        BandEntity itemEvaluate = searchResult.get(position);
        holder.bindSearch(itemEvaluate);
    }

    @Override
    public int getItemCount() {
        return searchResult.size();
    }

    public static class SearchHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mItemImage;
        private TextView mItemName;
        private RatingBar mItemGrade;
        private int id;

        public SearchHolder(View v) {
            super(v);
            mItemImage = (ImageView) v.findViewById(R.id.backgroundSearch);
            mItemName = (TextView) v.findViewById(R.id.nameEvaluateSearch);
            mItemGrade = (RatingBar) v.findViewById(R.id.ratingStarsEvaluateSearch);
            v.setOnClickListener(this);
        }

        public void bindSearch(BandEntity itemSearch) {
            id = itemSearch.getIdUsuario();
            //mItemImage.setBackgroundColor(R.color.black);
            Picasso.with(mItemImage.getContext()).load(itemSearch.getdImagemBanda()).into(mItemImage);
            mItemImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
            mItemImage.setImageAlpha(100);

            mItemName.setText(itemSearch.getsNomeBanda());
            mItemGrade.setRating((float)itemSearch.getAverageRating());
        }

        //5
        @Override
        public void onClick(View v) {

        }

    }

    public List<BandEntity> getSearchResult() {
        return searchResult;
    }

    public void setSearchResult(List<BandEntity> searchResult) {
        this.searchResult = searchResult;
    }
}

