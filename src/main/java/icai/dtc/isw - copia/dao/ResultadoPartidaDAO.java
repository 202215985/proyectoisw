package icai.dtc.isw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import icai.dtc.isw.domain.ResultadoPartida;

public class ResultadoPartidaDAO {
    public void setResultado(ResultadoPartida resultado) {
		Connection con=ConnectionDAO.getInstance().getConnection();
		String insertar = "INSERT INTO resultados_partidas (id_partida, id_hoyo, num_golpes) VALUES (?, ?, ?)";

		try (PreparedStatement pst = con.prepareStatement(insertar)) {
			// Asignar el valor del parámetro
			pst.setLong(1, resultado.getIdPartida());  // El primer parámetro "?" se reemplaza por el valor de 'id'
			pst.setLong(2, resultado.getIdHoyo());
			pst.setInt(3, resultado.getNumGolpes());

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
		
    }
}