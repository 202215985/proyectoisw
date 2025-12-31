package icai.dtc.isw.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;

import icai.dtc.isw.configuration.PropertiesISW;
import icai.dtc.isw.domain.CampoGolf;
import icai.dtc.isw.domain.Hoyo;
import icai.dtc.isw.domain.Partida;
import icai.dtc.isw.domain.ResultadoPartida;
import icai.dtc.isw.domain.Usuario;
import icai.dtc.isw.message.Message;

public class Client {
	private String host;
	private int port;

	public Client(String host, int port) {
		this.host=host;
		this.port=port;
	}
	public Client() {
		this.host = PropertiesISW.getInstance().getProperty("host");
		this.port = Integer.parseInt(PropertiesISW.getInstance().getProperty("port"));
	}
	public HashMap<String, Object> sentMessage(String Context, HashMap<String, Object> session) {
		//Configure connections
		//String host = PropertiesISW.getInstance().getProperty("host");
		//int port = Integer.parseInt(PropertiesISW.getInstance().getProperty("port"));

		System.out.println("Host: "+host+" port"+port);
		//Create a cliente class
		//Client cliente=new Client(host, port);
		
		//HashMap<String,Object> session=new HashMap<String, Object>();
		//session.put("/getCustomer","");
		
		Message mensajeEnvio=new Message();
		Message mensajeVuelta=new Message();
		mensajeEnvio.setContext(Context);
		mensajeEnvio.setSession(session);
		this.sent(mensajeEnvio,mensajeVuelta);
		
		
		switch (mensajeVuelta.getContext()) {
			case "/getCamposResponse":
				ArrayList<CampoGolf> camposList=(ArrayList<CampoGolf>)(mensajeVuelta.getSession().get("Campos"));
				 for (CampoGolf campo : camposList) {			
						System.out.println("He leído el id: "+campo.getId_campo()+" con nombre: "+campo.getNombre());
					} 
				session.put("Campos",camposList);
				break;
			case "/getCampoResponse":
				session=mensajeVuelta.getSession();
				CampoGolf campo =(CampoGolf) (session.get("Campo"));
				if (campo!=null) {
					System.out.println("He leído el id: " + campo.getId_campo() + " con nombre: " + campo.getNombre());
				}else {
					System.out.println("No se ha recuperado nada de la base de datos");
				}
				break;
			case "/getUsuarioResponse":
				session=mensajeVuelta.getSession();
				Usuario customer =(Usuario) (session.get("Usuario"));
				if (customer!=null) {
					System.out.println("He leído el dni: " + customer.getDni() + " con nombre: " + customer.getNombre());
				}else {
					System.out.println("No se ha recuperado nada de la base de datos");
				}
				break;
			case "/setUsuarioResponse":
				session=mensajeVuelta.getSession();
				Usuario usuNuevo =(Usuario) (session.get("Usuario"));
				if (usuNuevo!=null) {
					System.out.println("He añadido el usuario con el dni: " + usuNuevo.getDni() + " con nombre: " + usuNuevo.getNombre());
				}else {
					System.out.println("error");
				}
				break;

			case "/loginResponse":
				session=mensajeVuelta.getSession();
				Usuario customerlogin =(Usuario) (session.get("Usuario"));
				if (customerlogin!=null) {
					System.out.println("Se ha logeado el usuario con dni: " + customerlogin.getDni() + " con nombre: " + customerlogin.getNombre());
				}else {
					System.out.println("No se ha recuperado nada de la base de datos (login)");
				}
				break;

			case "/getHoyosResponse":
				ArrayList<Hoyo> hoyos=(ArrayList<Hoyo>)(mensajeVuelta.getSession().get("hoyos"));
				for (Hoyo hoyo : hoyos) {			
						System.out.println("He leído el id: "+hoyo.getIdHoyo()+" con nombre: "+hoyo.getNumHoyo());
					}
				session.put("hoyos", hoyos); 
				break;
			
			case "/setPartidaResponse":
				session=mensajeVuelta.getSession();
				Partida partida =(Partida) (session.get("Partida"));
				if (partida!=null) {
					System.out.println("He añadido la partida del usuario con dni: " + partida.getDniUsuario() + " y con resultado: " + partida.getResultado());
				}else {
					System.out.println("error");
				}
				break;

			case "/setResultadoResponse":
				session=mensajeVuelta.getSession();
				ResultadoPartida resul =(ResultadoPartida) (session.get("Resultado"));
				if (resul!=null) {
					System.out.println("He añadido el resultado del hoyo con id: " + resul.getIdHoyo() + " y con golpes: " + resul.getNumGolpes());
				}else {
					System.out.println("error");
				}
				break;

			case "/getPartidasResponse":
				ArrayList<Partida> partidas=(ArrayList<Partida>)(mensajeVuelta.getSession().get("Partidas"));
				for (Partida p : partidas) {			
						System.out.println("He leído la partida con id: "+p.getIdPartida()+" y con resultado: "+p.getResultado());
					}
				session.put("Partidas", partidas); 
				break;

			case "/getResultadosResponse":
				ArrayList<ResultadoPartida> resultados=(ArrayList<ResultadoPartida>)(mensajeVuelta.getSession().get("Resultados"));
				for (ResultadoPartida r : resultados) {			
						System.out.println("He leído el resultado del hoyo con id: "+r.getIdHoyo()+" y con golpes: "+r.getNumGolpes());
					}
				session.put("Resultados", resultados); 
				break;
				
			default:

				System.out.println("\nError a la vuelta");
				break;
		
		}
		//System.out.println("3.- En Main.- El valor devuelto es: "+((String)mensajeVuelta.getSession().get("Nombre")));
		return session;
	}
	


	public void sent(Message messageOut, Message messageIn) {
		try {

			System.out.println("Connecting to host " + host + " on port " + port + ".");

			Socket echoSocket = null;
			OutputStream out = null;
			InputStream in = null;

			try {
				echoSocket = new Socket(host, port);
				in = echoSocket.getInputStream();
				out = echoSocket.getOutputStream();
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
				
				//Create the objetct to send
				objectOutputStream.writeObject(messageOut);
				
				// create a DataInputStream so we can read data from it.
		        ObjectInputStream objectInputStream = new ObjectInputStream(in);
		        Message msg=(Message)objectInputStream.readObject();
		        messageIn.setContext(msg.getContext());
		        messageIn.setSession(msg.getSession());
			} catch (UnknownHostException e) {
				System.err.println("Unknown host: " + host);
				System.exit(1);
			} catch (IOException e) {
				System.err.println("Unable to get streams from server");
				System.exit(1);
			}		

			/** Closing all the resources */
			out.close();
			in.close();			
			echoSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}