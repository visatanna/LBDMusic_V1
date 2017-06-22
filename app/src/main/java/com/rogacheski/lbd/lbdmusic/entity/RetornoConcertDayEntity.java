package com.rogacheski.lbd.lbdmusic.entity;

/**
 * Created by vis_a on 21-Jun-17.
 */

public class RetornoConcertDayEntity {
    public final static int OPERACAO_INSERT = 0;
    public final static int OPERACAO_DELETE = 1;
    private ConcertDayEntity concertDay;
    private int operacao;
    private boolean isOk;

    public RetornoConcertDayEntity(ConcertDayEntity concertDay, boolean isOk, int operacao) {
        this.concertDay = concertDay;
        this.isOk = isOk;
        this.operacao = operacao;
    }

    public ConcertDayEntity getConcertDay() {
        return concertDay;
    }

    public void setConcertDay(ConcertDayEntity concertDay) {
        this.concertDay = concertDay;
    }

    public int getOperacao() {
        return operacao;
    }

    public void setOperacao(int operacao) {
        this.operacao = operacao;
    }

    public boolean isOk() {
        return isOk;
    }

    public void setOk(boolean ok) {
        isOk = ok;
    }
}
