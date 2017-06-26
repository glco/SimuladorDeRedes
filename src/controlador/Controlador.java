package controlador;

import java.io.IOException;
import java.net.UnknownHostException;

import protocolo.Protocolo;
import roteador.Roteador;


public class Controlador {
	private Protocolo protocolo;
	private Roteador roteador;

	protected Controlador(Protocolo p,Roteador r){
		this.protocolo =p;
		this.roteador = r;
	}

	public Protocolo getProtocolo() {
		return protocolo;
	}

	public void setProtocolo(Protocolo protocolo) {
		this.protocolo = protocolo;
	}

	public Roteador getRoteador() {
		return roteador;
	}

	public void setRoteador(Roteador roteador) {
		this.roteador = roteador;
	}

	public void enviaMensagem(String msg, String ip) throws IOException{
		this.roteador.enviaMensagem(msg,ip);
	}
	public void desencapsulaDatagrama(String msg){
		this.protocolo.desencapsular(msg);
	}
	public void shutdown(){
		this.roteador.shutdown();
	}
	public void abreConexoes() throws UnknownHostException, IOException {
		this.roteador.abreConexoes();
	}

}
