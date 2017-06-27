package com.rogacheski.lbd.lbdmusic.entity;

/**
 * Created by vis_a on 27-Jun-17.
 */

public class SearchResultEntity {
    int id;
    String fantasyName;
    String ImagemCard;
    float AverageReview;

    public SearchResultEntity(int id, String fantasyName, String imagemCard, float averageReview) {
        this.id = id;
        this.fantasyName = fantasyName;
        ImagemCard = imagemCard;
        AverageReview = averageReview;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFantasyName() {
        return fantasyName;
    }

    public void setFantasyName(String fantasyName) {
        this.fantasyName = fantasyName;
    }

    public String getImagemCard() {
        return ImagemCard;
    }

    public void setImagemCard(String imagemCard) {
        ImagemCard = imagemCard;
    }

    public float getAverageReview() {
        return AverageReview;
    }

    public void setAverageReview(float averageReview) {
        AverageReview = averageReview;
    }
}
