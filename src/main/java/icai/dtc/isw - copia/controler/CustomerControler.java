package icai.dtc.isw.controler;

import java.util.ArrayList;

import icai.dtc.isw.dao.CustomerDAO;
import icai.dtc.isw.domain.Usuario;

public class CustomerControler {
	CustomerDAO usuDAO=new CustomerDAO();
	
	public void getCustomers(ArrayList<Usuario> lista) {
		usuDAO.getClientes(lista);
	}

	public void setCustomer(Usuario usu) {
        usuDAO.setCliente(usu);
    }

	public Usuario getCustomer(String dni) {
		return(usuDAO.getCliente(dni));
	}

	public Usuario login(String dni, String password){
		return(usuDAO.login(dni, password));
	}

}


