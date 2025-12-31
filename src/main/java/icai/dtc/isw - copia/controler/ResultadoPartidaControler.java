package icai.dtc.isw.controler;

import icai.dtc.isw.dao.ResultadoPartidaDAO;
import icai.dtc.isw.domain.ResultadoPartida;

public class ResultadoPartidaControler {
	ResultadoPartidaDAO resultadoDAO=new ResultadoPartidaDAO();

	public void setResultado(ResultadoPartida resultado) {
        resultadoDAO.setResultado(resultado);
    }
}