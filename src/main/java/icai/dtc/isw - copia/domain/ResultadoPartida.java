package icai.dtc.isw.domain;

import java.io.Serializable;

public class ResultadoPartida implements Serializable{

    private long idResultado;
    private long idPartida;
    private long idHoyo;
    private int numGolpes;

    public ResultadoPartida() {
    }

    public ResultadoPartida(long idHoyo, long idPartida, int numGolpes) {
        this.idHoyo = idHoyo;
        this.idPartida = idPartida;
        this.numGolpes = numGolpes;
    }

    public long getIdResultado() {
        return idResultado;
    }

    public void setIdResultado(long idResultado) {
        this.idResultado = idResultado;
    }

    public long getIdPartida() {
        return idPartida;
    }

    public void setIdPartida(long idPartida) {
        this.idPartida = idPartida;
    }

    public long getIdHoyo() {
        return idHoyo;
    }

    public void setIdHoyo(long idHoyo) {
        this.idHoyo = idHoyo;
    }

    public int getNumGolpes() {
        return numGolpes;
    }

    public void setNumGolpes(int numGolpes) {
        this.numGolpes = numGolpes;
    }
}

