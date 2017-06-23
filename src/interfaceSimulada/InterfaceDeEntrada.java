package interfaceSimulada;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import controlador.Controlador;
import roteador.Roteador;

import datagrama.Datagrama;
import datagrama.DatagramaIpv4;

public class InterfaceDeEntrada extends InterfaceSimulada implements Runnable {
	private Thread t = null;


	public InterfaceDeEntrada(String ip,int port,Roteador r) {
		super(ip,port,r);
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
			while(true){
                if ((s = in.readLine()) != null) {
                    DatagramaIpv4 d = new DatagramaIpv4(s);
                    if (d.getDestIP() == this.getIpAddress()) {
                        this.getRoteador().recebePacote(d);
                    } else if (d.getTTL() > 0) {
                        d.decrementaTTL();
                        this.getRoteador().enviaPacote(d);
                    } else {
                        System.out.println("Pacote não pode ser entregue.");
                    }
                }
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void enviaMensagem(String msg, String ip) {
		System.out.println("Apenas entrada");
		return;
	}

    @Override
	public void enviaPacote(Datagrama datagrama) throws IOException {
		System.out.println("Apenas entrada");
		return;
	}

}
