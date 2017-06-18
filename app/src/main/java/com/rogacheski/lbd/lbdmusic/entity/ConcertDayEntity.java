package com.rogacheski.lbd.lbdmusic.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * Created by vis_a on 15-Jun-17.
 */

public class ConcertDayEntity {
    int id_User;
    int id_Calendar;
    Date busyDay ;

    public ConcertDayEntity() {

    }

    public ConcertDayEntity(Date busyDay) {
        this.busyDay = busyDay;
    }

    public ConcertDayEntity(int id_User, Date busyDay) {
        this.id_User = id_User;
        this.busyDay = busyDay;
    }

    public int getId_Calendar() {
        return id_Calendar;
    }

    public void setId_Calendar(int id_Calendar) {
        this.id_Calendar = id_Calendar;
    }


    public Date getBusyDay() {
        return busyDay;
    }

    public void setBusyDay(Date busyDay) {
        this.busyDay = busyDay;
    }


    public int getId_User() {
        return id_User;
    }

    public void setId_User(int id_User) {
        this.id_User = id_User;
    }

    @Override
    public boolean equals (Object o){
        if (o instanceof ConcertDayEntity) {
            ConcertDayEntity day = (ConcertDayEntity) o;
            if (this.getBusyDay().equals(day.getBusyDay())) {
                return true;
            } else {
                return false;
            }
        }else{
            return false;
        }
    }

    public static class BandEntity implements Serializable {

        /** idUsuario - ID da banda*/
        private int idUsuario;
        public int getIdUsuario() {
            return idUsuario;
        }
        public void setIdUsuario(int idUsuario) {
            this.idUsuario = idUsuario;
        }

        /** sNomeBanda - Nome da banda*/
        private String sNomeBanda;
        public String getsNomeBanda() {
            return sNomeBanda;
        }
        public void setsNomeBanda(String sNomeBanda) {
            this.sNomeBanda = sNomeBanda;
        }

        /** idAddress - Endereço da banda*/
        private int idAddress;
        public int getIdAddress() {
            return idAddress;
        }
        public void setIdAddress(int idAddress) {
            this.idAddress = idAddress;
        }

        /** email - Email da banda*/
        private String email;
        public String getEmail() {
            return email;
        }
        public void setEmail(String email) {
            this.email = email;
        }

        /** fname - Primeiro nome do responsável da banda*/
        private String fname;
        public String getFname() {
            return fname;
        }
        public void setFname(String fname) {
            this.fname = fname;
        }

        /** lname - Último nome do responsável da banda*/
        private String lname;
        public String getLname() {
            return lname;
        }
        public void setLname(String lname) {
            this.lname = lname;
        }

        /** dImagemBanda - Imagem de capa da banda*/
        private String  dImagemBanda;
        public String getdImagemBanda() {
            return dImagemBanda;
        }
        public void setdImagemBanda(String dImagemBanda) {
            this.dImagemBanda = dImagemBanda;
        }

        /** dImagemDescBanda - Imagem da descrição da banda*/
        private String  dImagemDescBanda;
        public String getdImagemDescBanda() {
            return dImagemDescBanda;
        }
        public void setdImagemDescBanda(String dImagemDescBanda) {
            this.dImagemDescBanda = dImagemDescBanda;
        }

        /** tags - Tags de gênero da banda*/
        private List<TagEntity> tags;
        public List<TagEntity> getTags() {
            return tags;
        }
        public void setTags(List<TagEntity> tags) {
            this.tags = tags;
        }

        /** listaContatos - Lista de contatos com a banda (Facebook, Youtube, etc.)*/
        private List<ContatoEntity> listaContatos;
        public List<ContatoEntity> getListaContatos() {
            return listaContatos;
        }
        public void setListaContatos(List<ContatoEntity> listaContatos) {
            this.listaContatos = listaContatos;
        }

        /** sDescricaoBanda - Texto de descrição da banda*/
        private String sDescricaoBanda;
        public String getsDescricaoBanda() {
            return sDescricaoBanda;
        }
        public void setsDescricaoBanda(String sDescricaoBanda) {
            this.sDescricaoBanda = sDescricaoBanda;
        }

        /** listaReviews - Lista de reviews da banda*/
        private List<ReviewsEntity> listaReviews;
        public List<ReviewsEntity> getListaReviews() {
            return listaReviews;
        }
        public void setListaReviews(List<ReviewsEntity> listaReviews) {
            this.listaReviews = listaReviews;
        }

        /** averageRating - Média de avaliações da banda*/
        private double averageRating;
        public double getAverageRating() {
            return averageRating;
        }
        public void setAverageRating(double averageRating) {
            this.averageRating = averageRating;
        }
    }
}
