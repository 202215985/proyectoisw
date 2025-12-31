package icai.dtc.isw.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import icai.dtc.isw.domain.CampoGolf;
import icai.dtc.isw.domain.Partida;
import icai.dtc.isw.domain.ResultadoPartida;

public class PartidasDAO {
    public void setPartida(Partida partida) {
        Connection con=ConnectionDAO.getInstance().getConnection();
        String insertar = "INSERT INTO partidas (dni_usuario, resultado, fecha) VALUES (?, ?, ?)";

        try (PreparedStatement pst = con.prepareStatement(insertar, Statement.RETURN_GENERATED_KEYS)) {
            // Asignar el valor del parámetro
            pst.setString(1, partida.getDniUsuario());
            pst.setInt(2, partida.getResultado());
            pst.setDate(3, partida.getFecha());

            int rs = pst.executeUpdate();

            if (rs > 0) {
                // Obtener la clave generada
                try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int idGenerado = generatedKeys.getInt(1);
                        partida.setIdPartida(idGenerado); // Establecer el ID generado en el objeto Partida
                        System.out.println("Añadido correctamente con ID: " + idGenerado);
                    }
                }
            } else {
                System.out.println("No se ha añadido correctamente");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }        
    }

	public ArrayList<Partida> getPartidas(String dni_usu){
		Connection con=ConnectionDAO.getInstance().getConnection();
        String consulta = """
            SELECT DISTINCT
                p.id_partida, p.resultado, p.fecha,
                cg.id_campo, cg.nombre AS nombre_campo, cg.ubicacion, cg.par AS par_campo
            FROM partidas p
            INNER JOIN resultados_partidas rp ON p.id_partida = rp.id_partida
            INNER JOIN hoyos h ON rp.id_hoyo = h.id_hoyo
            INNER JOIN campos_golf cg ON h.id_campo = cg.id_campo
            WHERE p.dni_usuario = ?
            ORDER BY p.fecha DESC, p.id_partida DESC
        """;
		ArrayList<Partida> partidas = new ArrayList<>();

		try (PreparedStatement pst = con.prepareStatement(consulta)) {
			// Asignar el valor del parámetro
			pst.setString(1, dni_usu);  // El primer parámetro "?" se reemplaza por el valor de 'id'

			try (ResultSet rs = pst.executeQuery()) {
			
                while (rs.next()) {
                    Partida p_actual=new Partida(rs.getLong(1),dni_usu, rs.getDate(3), rs.getInt(2));   
                    CampoGolf c_actual=new CampoGolf(rs.getInt(4),rs.getString(5),rs.getString(6),0,"",rs.getInt(7));
            	    p_actual.setCampo(c_actual);
                    partidas.add(p_actual);
            }

            } catch (SQLException ex) {

                System.out.println(ex.getMessage());
            }
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}

		System.out.println("Número de partidas: " + partidas.size());

		return partidas;
	}

    public void setResultados(Partida p){
        Connection con=ConnectionDAO.getInstance().getConnection();
        String consulta = "SELECT id_hoyo, num_golpes FROM resultados_partidas WHERE id_partida = ?";

        ArrayList<ResultadoPartida> resultados = new ArrayList<>();

        try (PreparedStatement pst = con.prepareStatement(consulta)) {
            pst.setLong(1, p.getIdPartida());

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    ResultadoPartida resultado = new ResultadoPartida();
                    resultado.setIdPartida(p.getIdPartida());
                    resultado.setIdHoyo(rs.getLong("id_hoyo"));
                    resultado.setNumGolpes(rs.getInt("num_golpes"));
                    resultados.add(resultado);
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        p.setResultados(resultados);
    }
}

