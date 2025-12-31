package icai.dtc.isw.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import icai.dtc.isw.configuration.PropertiesISW;
import icai.dtc.isw.controler.CamposControler;
import icai.dtc.isw.controler.CustomerControler;
import icai.dtc.isw.controler.HoyosControler;
import icai.dtc.isw.controler.PartidasControler;
import icai.dtc.isw.controler.ResultadoPartidaControler;
import icai.dtc.isw.domain.CampoGolf;
import icai.dtc.isw.domain.Hoyo;
import icai.dtc.isw.domain.Partida;
import icai.dtc.isw.domain.ResultadoPartida;
import icai.dtc.isw.domain.Usuario;
import icai.dtc.isw.message.Message;

public class SocketServer extends Thread {
	public static int port = Integer.parseInt(PropertiesISW.getInstance().getProperty("port"));

	protected Socket socket;

	private SocketServer(Socket socket) {
		this.socket = socket;
		//Configure connections
		System.out.println("Nuevo cliente conectado desde " + socket.getInetAddress().getHostAddress());
		start();
	}

	public void run() {
		InputStream in = null;
		OutputStream out = null;
		try {
			in = socket.getInputStream();
			out = socket.getOutputStream();
			
			//first read the object that has been sent
			ObjectInputStream objectInputStream = new ObjectInputStream(in);
		    Message mensajeIn= (Message)objectInputStream.readObject();
		    //Object to return informations 
		    ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
		    Message mensajeOut=new Message();
			HashMap<String,Object> session=mensajeIn.getSession();
			CamposControler camposControler;
			CustomerControler usuControler;
			HoyosControler hoyosControler;
			PartidasControler partidaControler;
			ResultadoPartidaControler resulControler;

		    switch (mensajeIn.getContext()) {
		    	case "/getCampos":
		    		camposControler=new CamposControler();
		    		ArrayList<CampoGolf> lista=new ArrayList<CampoGolf>();
		    		camposControler.getCamposGolf(lista);
		    		mensajeOut.setContext("/getCamposResponse");
		    		//HashMap<String,Object> session=new HashMap<String, Object>();
		    		session.put("Campos",lista);
		    		mensajeOut.setSession(session);
		    		objectOutputStream.writeObject(mensajeOut);		    		
		    		break;
				case "/getCampo":
					int id= (int) session.get("id");
					camposControler=new CamposControler();
					CampoGolf cu=camposControler.getCampoGolf(id);
					if (cu!=null){
						System.out.println("id:"+cu.getId_campo());
					}else {
						System.out.println("No encontrado en la base de datos");
					}

					mensajeOut.setContext("/getCampoResponse");
					session.put("Campo",cu);
					mensajeOut.setSession(session);
					objectOutputStream.writeObject(mensajeOut);
					break;
				
				case "/getUsuario":
					String dni= (String) session.get("dni");
					usuControler=new CustomerControler();
					Usuario usu =usuControler.getCustomer(dni);
					if (usu!=null){
						System.out.println("dni:"+usu.getDni());
					}else {
						System.out.println("No encontrado en la base de datos");
					}

					mensajeOut.setContext("/getUsuarioResponse");
					session.put("Usuario",usu);
					mensajeOut.setSession(session);
					objectOutputStream.writeObject(mensajeOut);
					break;
				
				case "/setUsuario":
					Usuario nuevo_usuario= (Usuario) session.get("Usuario");
					usuControler=new CustomerControler();
					usuControler.setCustomer(nuevo_usuario);
					if (nuevo_usuario!=null){
						System.out.println("dni:"+nuevo_usuario.getDni());
					}else {
						System.out.println("No encontrado en la base de datos");
					}
					mensajeOut.setContext("/setUsuarioResponse");
					session.put("Usuario",nuevo_usuario);
					mensajeOut.setSession(session);
					objectOutputStream.writeObject(mensajeOut);
					break;

				case "/login":
					String dni_login= (String) session.get("dni");
					String password= (String) session.get("password");
					usuControler=new CustomerControler();
					Usuario usu_login =usuControler.login(dni_login,password);
					if (usu_login!=null){
						System.out.println("dni:"+usu_login.getDni());
					}else {
						System.out.println("No encontrado en la base de datos");
					}

					mensajeOut.setContext("/loginResponse");
					session.put("Usuario",usu_login);
					mensajeOut.setSession(session);
					objectOutputStream.writeObject(mensajeOut);
					break;
				
				case "/getHoyos":
					System.out.println("Entrando en gethoyos server");
					hoyosControler=new HoyosControler();
					ArrayList<Hoyo> hoyos=new ArrayList<>();
					int id_campo= (int) session.get("id_campo");
		    		hoyosControler.getHoyos(id_campo,hoyos);
		    		mensajeOut.setContext("/getHoyosResponse");
		    		session.put("hoyos",hoyos);
					session.put("id_campo",id_campo);
		    		mensajeOut.setSession(session);
		    		objectOutputStream.writeObject(mensajeOut);		    		
		    		break;

				case "/getPartidas":
					partidaControler=new PartidasControler();
					ArrayList<Partida> partidas;
					String dni_usu= (String) session.get("dni_usu");
		    		partidas = partidaControler.getPartidas(dni_usu);
		    		mensajeOut.setContext("/getPartidasResponse");
					session.put("Partidas",partidas);
		    		mensajeOut.setSession(session);
		    		objectOutputStream.writeObject(mensajeOut);		    		
		    		break;

				case "/setPartida":
					Partida partida= (Partida) session.get("Partida");
					partidaControler=new PartidasControler();
					partidaControler.setPartida(partida);
					if (partida!=null){
						System.out.println("partida con id:"+partida.getIdPartida());
					}else {
						System.out.println("No encontrado en la base de datos");
					}
					mensajeOut.setContext("/setPartidaResponse");
					session.put("Partida",partida);
					mensajeOut.setSession(session);
					objectOutputStream.writeObject(mensajeOut);
					break;
				
				case "/setResultado":
					ResultadoPartida resultado= (ResultadoPartida) session.get("Resultado");
					resulControler=new ResultadoPartidaControler();
					resulControler.setResultado(resultado);
					if (resultado!=null){
						System.out.println("hoyo con id: "+resultado.getIdHoyo()+" con "+resultado.getNumGolpes()+" golpes");
					}else {
						System.out.println("No encontrado en la base de datos");
					}
					mensajeOut.setContext("/setResultadoResponse");
					session.put("Resultado",resultado);
					mensajeOut.setSession(session);
					objectOutputStream.writeObject(mensajeOut);
					break;

				case "/getResultados":
					Partida partida_resul= (Partida) session.get("Partida");
					partidaControler=new PartidasControler();
		    		partidaControler.setResultados(partida_resul);		
					mensajeOut.setContext("/getResultadosResponse");
					session.put("Partida",partida_resul);
					session.put("Resultados",partida_resul.getResultados());
					mensajeOut.setSession(session);
		    		objectOutputStream.writeObject(mensajeOut);		    		
		    		break;	

		    	default:
		    		System.out.println("\nParámetro no encontrado");
		    		break;
		    }
		} catch (IOException ex) {
			System.out.println("No se pudo obtener stream del cliente");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				in.close();
				out.close();
				socket.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		System.out.println("Server en puerto: "+port);
		ServerSocket server = null;
		try {
			server = new ServerSocket(port);
			while (true) {
				/**
				 * create a new {@link SocketServer} object for each connection
				 * this will allow multiple client connections
				 */
				new SocketServer(server.accept());
			}
		} catch (IOException ex) {
			System.out.println("No se pudo iniciar Server.");
		} finally {
			try {
				if (server != null)
					server.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}