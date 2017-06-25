package roteador;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;

import controlador.Controlador;
import datagrama.Datagrama;
import datagrama.DatagramaFactory;
import fileManager.FileReader;
import interfaceSimulada.InterfaceSimulada;
import servidor.Servidor;

public abstract class Roteador {
	private LinkedList<InterfaceSimulada> interfaces = null;
	private Servidor servidor = null;
	private Controlador controlador = null;

	protected RoutingTable tabelaRoteamento = null;

	public Roteador(int porta, String routingTableFile, Controlador c) throws IOException{
		this.tabelaRoteamento = new RoutingTable(new FileReader().readNetworkFile(routingTableFile));
		this.servidor = new Servidor(porta,this);
		this.controlador = c;
	}

	public abstract void inicializaInterfaces(String host,String virtualIp) throws FileNotFoundException, IOException;

	public LinkedList<InterfaceSimulada> getInterfaces() {
		return interfaces;
	}

    public InterfaceSimulada getInterfaceFor(String ip){
        int i = this.tabelaRoteamento.getInterfaceIndex(ip);
        if (i > -1) {
            return this.getInterfaces().get(i);
        } else {
            return null;
        }
    }

	public void setInterfaces(LinkedList<InterfaceSimulada> interfaces) {
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
		for(InterfaceSimulada i : this.interfaces){
			//System.out.println("-  <"+i.get()+">");
		}
	}

	public abstract void enviaMensagem(String msg,String ip) throws IOException;
	public abstract void receberConexao(Socket client);
	public abstract void recebePacote(Datagrama data) throws UnsupportedEncodingException;
	public abstract void enviaPacote(Datagrama data) throws UnsupportedEncodingException;
	public abstract void abreConexoes() throws UnknownHostException, IOException;

}
