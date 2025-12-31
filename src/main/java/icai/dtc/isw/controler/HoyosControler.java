package icai.dtc.isw.controler;

import java.util.ArrayList;

import icai.dtc.isw.dao.HoyoDAO;
import icai.dtc.isw.domain.Hoyo;

public class HoyosControler {
    HoyoDAO hoyoDAO = new HoyoDAO();

    public void getHoyos(int id_campo, ArrayList<Hoyo> hoyos) {
		  hoyoDAO.getHoyos(id_campo, hoyos);
	  }
}
