package icai.dtc.isw.domain;

import java.io.Serializable;

public class CampoGolf implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id_campo;
    private String nombre;
    private String ubicacion;
    private String num_hoyos;
    private String fecha_inauguracion;
    private String par;

    // Constructor vacío
    public CampoGolf() {
        this.id_campo = "";
        this.nombre = "";
        this.ubicacion = "";
        this.num_hoyos = "";
        this.fecha_inauguracion = "";
        this.par = "";
    }

    // Constructor con todos los parámetros
    public CampoGolf(String id_campo, String nombre, String ubicacion, String num_hoyos, String fecha_inauguracion, String par) {
        this.id_campo = id_campo;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.num_hoyos = num_hoyos;
        this.fecha_inauguracion = fecha_inauguracion;
        this.par = par;
    }

    // Getters y Setters
    public String getId_campo() {
        return id_campo;
    }

    public void setId_campo(String id_campo) {
        this.id_campo = id_campo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getNum_hoyos() {
        return num_hoyos;
    }

    public void setNum_hoyos(String num_hoyos) {
        this.num_hoyos = num_hoyos;
    }

    public String getFecha_inauguracion() {
        return fecha_inauguracion;
    }

    public void setFecha_inauguracion(String fecha_inauguracion) {
        this.fecha_inauguracion = fecha_inauguracion;
    }

    public String getPar() {
        return par;
    }

    public void setPar(String par) {
        this.par = par;
    }
}
