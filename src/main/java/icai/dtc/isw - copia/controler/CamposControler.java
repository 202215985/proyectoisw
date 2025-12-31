package icai.dtc.isw.controler;

import java.util.ArrayList;

import icai.dtc.isw.dao.CamposGolfDAO;
import icai.dtc.isw.domain.CampoGolf;

public class CamposControler {
	
	CamposGolfDAO camposDAO=new CamposGolfDAO();

	public void getCamposGolf(ArrayList<CampoGolf> lista) {
		camposDAO.getCampos(lista);
	}
	public CampoGolf getCampoGolf(int id) {
		return(camposDAO.getCampo(id));
	}
}
