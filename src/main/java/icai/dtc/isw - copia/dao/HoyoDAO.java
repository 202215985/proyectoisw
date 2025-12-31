package icai.dtc.isw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import icai.dtc.isw.domain.Hoyo;

public class HoyoDAO {
    public void getHoyos(int id_campo, ArrayList<Hoyo> hoyos) {
		Connection con=ConnectionDAO.getInstance().getConnection();
        System.out.println("PAsado el connection");
		String consulta = "SELECT * FROM hoyos WHERE id_campo = ?";

		try (PreparedStatement pst = con.prepareStatement(consulta)) {
			// Asignar el valor del parámetro
			pst.setInt(1, id_campo);  // El primer parámetro "?" se reemplaza por el valor de 'id'

			try (ResultSet rs = pst.executeQuery()) {
			
                while (rs.next()) {
            	    hoyos.add(new Hoyo(rs.getInt(1), rs.getInt(2), rs.getInt(3)));
            }

            } catch (SQLException ex) {

                System.out.println(ex.getMessage());
            }
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}
}
