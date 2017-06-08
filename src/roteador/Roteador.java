package roteador;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Map;

import controlador.Controlador;
import datagrama.Datagrama;
import datagrama.DatagramaFactory;
import interfaceSimulada.InterfaceSimulada;
import servidor.Servidor;

public abstract class Roteador {
	private Map<String,InterfaceSimulada> interfaces = null;
	private Servidor servidor = null;
	private Controlador controlador = null;
	private DatagramaFactory datagramaFactory = null;
	
	public Roteador(int porta,Controlador c) throws IOException{
		this.servidor = new Servidor(porta,this);
		this.controlador = c;
	}
	
	public abstract void inicializaInterfaces(String host,String virtualIp) throws FileNotFoundException, IOException;
		
		
	
	public Map<String, InterfaceSimulada> getInterfaces() {
		return interfaces;
	}

	public void setInterfaces(Map<String, InterfaceSimulada> interfaces) {
		this.interfaces = interfaces;
	}

	public Servidor getServidor() {
		return servidor;
	}

	public void setServidor(Servidor servidor) {
		this.servidor = servidor;
	}

	public Controlador getControlador() {
		return controlador;
	}

	public void setControlador(Controlador controlador) {
		this.controlador = controlador;
	}

	public void shutdown() {
		
	}
	public void imprimeInterfaces(){
		if(interfaces == null){
			System.out.println("Nenhum host disponivel no momento");
			return;
		}
		System.out.println("Lista de hosts disponiveis:");
		for(Map.Entry<String, InterfaceSimulada> i : interfaces.entrySet()){
			System.out.println("-  <"+i.getKey()+">");
		}
	}
	
	public abstract void enviaMensagem(String msg,String ip) throws IOException;
	public abstract void receberConexao(Socket client);
	public abstract void recebePacote(String data) throws UnsupportedEncodingException;
	public abstract void abreConexoes() throws UnknownHostException, IOException;

	public DatagramaFactory getDatagramaFactory() {
		return datagramaFactory;
	}

	public void setDatagramaFactory(DatagramaFactory datagrama) {
		this.datagramaFactory = datagrama;
	}
	
}
