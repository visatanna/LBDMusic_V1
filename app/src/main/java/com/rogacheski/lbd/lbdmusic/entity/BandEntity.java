package com.rogacheski.lbd.lbdmusic.entity;
import android.graphics.drawable.Drawable;
import java.util.List;

/**
 * Created by Vinicius on 28-Apr-17.
 */

public class BandEntity {

    private String sNomeBanda;
    private Drawable dImagemBanda;
    private Drawable dImagemDescBanda;
    private List<TagEntity> tags;
    private List<ContatoEntity> listaContatos;
    private String sDescricaoBanda;
    private List<ReviewsEntity> listaReviews;
    private int IdUser;

    public int getIdUser() {
        return IdUser;
    }

    public void setIdUser(int idUser) {
        IdUser = idUser;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    private double averageRating;

    public Drawable getdImagemDescBanda() {
        return dImagemDescBanda;
    }

    public void setdImagemDescBanda(Drawable dImagemDescBanda) {
        this.dImagemDescBanda = dImagemDescBanda;
    }

    public String getsNomeBanda() {
        return sNomeBanda;
    }

    public void setsNomeBanda(String sNomeBanda) {
        this.sNomeBanda = sNomeBanda;
    }

    public Drawable getdImagemBanda() {
        return dImagemBanda;
    }

    public List<ContatoEntity> getListaContatos() {
        return listaContatos;
    }

    public void setTags(List<TagEntity> tags) {
        this.tags = tags;
    }

    public List<ReviewsEntity> getListaReviews() {
        return listaReviews;
    }

    public List<TagEntity> getTags() {
        return tags;
    }

    public String getsDescricaoBanda() {
        return sDescricaoBanda;
    }

    public void setdImagemBanda(Drawable dImagemBanda) {
        this.dImagemBanda = dImagemBanda;
    }

    public void setListaContatos(List<ContatoEntity> listaContatos) {
        this.listaContatos = listaContatos;
    }

    public void setsDescricaoBanda(String sDescricaoBanda) {
        this.sDescricaoBanda = sDescricaoBanda;
    }

    public void setListaReviews(List<ReviewsEntity> listaReviews) {
        this.listaReviews = listaReviews;
    }
}
