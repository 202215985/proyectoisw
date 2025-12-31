package icai.dtc.isw.domain;

import java.io.Serializable;

public class Hoyo implements Serializable{

    private int idHoyo;
    private int idCampo;
    private int numHoyo;

    public Hoyo() {
    }

    public Hoyo(int idHoyo, int idCampo, int numHoyo) {
        this.idCampo = idCampo;
        this.idHoyo = idHoyo;
        this.numHoyo = numHoyo;
    }

    public int getIdHoyo() {
        return idHoyo;
    }

    public void setIdHoyo(int idHoyo) {
        this.idHoyo = idHoyo;
    }

    public int getIdCampo() {
        return idCampo;
    }

    public void setIdCampo(int idCampo) {
        this.idCampo = idCampo;
    }

    public int getNumHoyo() {
        return numHoyo;
    }

    public void setNumHoyo(int numHoyo) {
        this.numHoyo = numHoyo;
    }

     @Override
        public String toString() {
            return "Hoyo " + numHoyo;
        }
}

