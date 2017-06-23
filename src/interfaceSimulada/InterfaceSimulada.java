package interfaceSimulada;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import roteador.Roteador;

import datagrama.Datagrama;

public abstract class InterfaceSimulada  {
	private String ipAddress = null;
	private int port = 0;
	private Socket host = null;
	private Roteador roteador = null;
	public InterfaceSimulada(String ip,int port,Roteador r){
		this.ipAddress = ip;
		this.port = port;
		this.setRoteador(r);
	}

	protected Socket getHost() {
		return host;
	}
	protected void setHost(Socket host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}

	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public void openConnection() throws UnknownHostException, IOException{
		host = new Socket(ipAddress,port);
	}
	public void closeConnection() throws IOException{
		if(host.isClosed() == false)
		host.close();
	}

	public abstract void enviaMensagem(String msg, String dst) throws IOException;
	public abstract void enviaPacote(Datagrama datagrama) throws IOException;

	public Roteador getRoteador() {
		return roteador;
	}

	public void setRoteador(Roteador roteador) {
		this.roteador = roteador;
	}
}
