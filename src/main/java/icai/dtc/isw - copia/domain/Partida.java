
package icai.dtc.isw.domain;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;


public class Partida implements Serializable{

    private long idPartida;
    private String dniUsuario;
    private int resultado;
    private Date fecha;
    private CampoGolf campo;  
    private ArrayList<ResultadoPartida> resultados;

    public Partida(String dniUsuario, Date fecha, int resultado) {
        this.dniUsuario = dniUsuario;
        this.fecha = fecha;
        this.resultado = resultado;
    }

    public Partida(long idPartida, String dniUsuario, Date fecha, int resultado) {
        this.idPartida = idPartida;
        this.dniUsuario = dniUsuario;
        this.fecha = fecha;
        this.resultado = resultado;
    }

    public long getIdPartida() {
        return idPartida;
    }

    public void setIdPartida(long idPartida) {
        this.idPartida = idPartida;
    }

    public String getDniUsuario() {
        return dniUsuario;
    }

    public void setDniUsuario(String dniUsuario) {
        this.dniUsuario = dniUsuario;
    }

    public int getResultado() {
        return resultado;
    }

    public void setResultado(int resultado) {
        this.resultado = resultado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public CampoGolf getCampo() {
        return campo;
    }

    public void setCampo(CampoGolf campo) {
        this.campo = campo;
    }

    public void setResultados(ArrayList<ResultadoPartida> resultados) {
        this.resultados = resultados;
    }

    public ArrayList<ResultadoPartida> getResultados() {
        return resultados;
    }
}

