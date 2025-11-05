package icai.dtc.isw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import icai.dtc.isw.domain.Usuario;

public class CustomerDAO {

	public void getClientes(ArrayList<Usuario> lista) {
		Connection con=ConnectionDAO.getInstance().getConnection();
		try (PreparedStatement pst = con.prepareStatement("SELECT * FROM usuarios");
                ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
            	lista.add(new Usuario(rs.getString(1), rs.getString(2), rs.getString(3),rs.getString(4)));
            }

        } catch (SQLException ex) {

            System.out.println(ex.getMessage());
        }

	}
	public Usuario getCliente(int id) {
		Connection con=ConnectionDAO.getInstance().getConnection();
		Usuario cu=null;
		String consulta = "SELECT * FROM usuarios WHERE dni = ?";

		try (PreparedStatement pst = con.prepareStatement(consulta)) {
			// Asignar el valor del parámetro
			pst.setInt(1, id);  // El primer parámetro "?" se reemplaza por el valor de 'id'

			try (ResultSet rs = pst.executeQuery()) {
				// Procesar el resultado
				if (rs.next()) {
					cu = new Usuario(rs.getString(1), rs.getString(2), rs.getString(3),rs.getString(4));  // Obtener los datos de la fila resultante
				}
			}

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return cu;
		//return new Customer("1","Atilano");
	}

	public void setCliente(Usuario usu) {
		Connection con=ConnectionDAO.getInstance().getConnection();
		String insertar = "INSERT INTO usuarios (dni, nombre, apellidos, correo) VALUES (?, ?, ?, ?)";

		try (PreparedStatement pst = con.prepareStatement(insertar)) {
			// Asignar el valor del parámetro
			pst.setString(1, usu.getDni());  // El primer parámetro "?" se reemplaza por el valor de 'id'
			pst.setString(2, usu.getNombre());
			pst.setString(3, usu.getApellidos());
			pst.setString(4, usu.getCorreo());

			int rs = pst.executeUpdate();

			if (rs>0){
				System.out.println("Añadido correctamente");
			}

			else{
				System.out.println("No se ha añadido correctamente");
			}
			

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		//return new Customer("1","Atilano");
	}
	
	public static void main(String[] args) {
		
		CustomerDAO customerDAO=new CustomerDAO();
		ArrayList<Usuario> lista= new ArrayList<>();
		customerDAO.getClientes(lista);
		
		
		 for (Usuario customer : lista) {			
			System.out.println("He leído el id: "+customer.getDni()+" con nombre: "+customer.getNombre());
		}
		
	
	}

}
