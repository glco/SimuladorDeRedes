package interfaceSimulada;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import controlador.Controlador;
import roteador.Roteador;

public class InterfaceDeEntrada extends InterfaceSimulada implements Runnable {
	private Thread t = null;
	
	
	public InterfaceDeEntrada(String ip,int port,Roteador r) {
		super(ip,port,r);
		// TODO Auto-generated constructor stub
	}
	public InterfaceDeEntrada(Socket s,Roteador r){
		super(s.getInetAddress().getHostAddress(),s.getPort(),r);
		this.setHost(s);
	}
	
	
	@Override
	public void run() {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(this.getHost().getInputStream()));
			String s = null;
			while((s= in.readLine()) != null){
				this.getRoteador().recebePacote(s);
				}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void enviaMensagem(String data) {
		System.out.println("Apenas entrada");
		return;
	}
	
}
