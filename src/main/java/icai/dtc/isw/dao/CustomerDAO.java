package icai.dtc.isw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import icai.dtc.isw.domain.Customer;

public class CustomerDAO {

	public void getClientes(ArrayList<Customer> lista) {
		Connection con=ConnectionDAO.getInstance().getConnection();
		try (PreparedStatement pst = con.prepareStatement("SELECT * FROM campos_golf");
                ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
            	lista.add(new Customer(rs.getString(1), rs.getString(2), rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6)));
            }

        } catch (SQLException ex) {

            System.out.println(ex.getMessage());
        }

	}
	public Customer getCliente(int id) {
		Connection con=ConnectionDAO.getInstance().getConnection();
		Customer cu=null;
		String consulta = "SELECT * FROM campos_golf WHERE id_campo = ?";

		try (PreparedStatement pst = con.prepareStatement(consulta)) {
			// Asignar el valor del parámetro
			pst.setInt(1, id);  // El primer parámetro "?" se reemplaza por el valor de 'id'

			try (ResultSet rs = pst.executeQuery()) {
				// Procesar el resultado
				if (rs.next()) {
					cu = new Customer(rs.getString(1), rs.getString(2), rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6));  // Obtener los datos de la fila resultante
				}
			}

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return cu;
		//return new Customer("1","Atilano");
	}
	
	public static void main(String[] args) {
		
		CustomerDAO customerDAO=new CustomerDAO();
		ArrayList<Customer> lista= new ArrayList<>();
		customerDAO.getClientes(lista);
		
		
		 for (Customer customer : lista) {			
			System.out.println("He leído el id: "+customer.getId_campo()+" con nombre: "+customer.getNombre());
		}
		
	
	}

}
