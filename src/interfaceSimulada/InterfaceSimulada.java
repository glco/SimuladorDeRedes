package interfaceSimulada;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import roteador.Roteador;
import java.io.IOException;
import java.io.PrintWriter;

import datagrama.DatagramaIpv4;
import datagrama.Datagrama;

public class InterfaceSimulada {
	private String ipAddress = null;
    private String mask = null;
	private String hostAddress = null;
	private int port = 0;
	private Socket host = null;
	private Roteador roteador = null;

    public InterfaceSimulada(String ip, String mask, String host, int port, Roteador r){
		this.ipAddress = ip;
		this.port = port;
        this.hostAddress = host;
        this.mask = mask;
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
		this.host = new Socket(this.hostAddress, this.port);
	}

	public void closeConnection() throws IOException{
		if(host.isClosed() == false)
		host.close();
	}

	public void enviaMensagem(String msg, String dst) throws IOException {
        DatagramaIpv4 datagrama = new DatagramaIpv4(msg, dst, this.ipAddress);
        this.enviaPacote(datagrama);
    }

	public void enviaPacote(Datagrama datagrama) throws IOException {
        if (this.host == null) {
            System.out.println("Sem conexão com vizinho.");
            return;
        }
        PrintWriter out = new PrintWriter(this.host.getOutputStream(),true);
		out.println(datagrama.toBinaryString());
		out.close();
    }

	public Roteador getRoteador() {
		return roteador;
	}

	public void setRoteador(Roteador roteador) {
		this.roteador = roteador;
	}
}
