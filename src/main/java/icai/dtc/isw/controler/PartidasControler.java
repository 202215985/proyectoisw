package icai.dtc.isw.controler;

import java.util.ArrayList;

import icai.dtc.isw.dao.PartidasDAO;
import icai.dtc.isw.domain.Partida;

public class PartidasControler {
	PartidasDAO partidaDAO=new PartidasDAO();

	public void setPartida(Partida partida) {
        partidaDAO.setPartida(partida);
    }

    public ArrayList<Partida> getPartidas(String dni_usu){
        return partidaDAO.getPartidas(dni_usu);
    }
    
    public void setResultados(Partida p){
        partidaDAO.setResultados(p);
    }
}