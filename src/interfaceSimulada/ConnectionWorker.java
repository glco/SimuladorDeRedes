package interfaceSimulada;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import datagrama.DatagramaIpv4;
import datagrama.Datagrama;
import roteador.Roteador;

public class ConnectionWorker implements Runnable {
    private Socket client = null;
    private Roteador receiver = null;

    public ConnectionWorker(Socket client, Roteador receiver){
        this.client = client;
        this.receiver = receiver;
    }
    @Override
	public void run() {
        BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
			String s = "", batch = null;
			while((batch = in.readLine()) != null) {
                s += batch;
			}
            DatagramaIpv4 d = new DatagramaIpv4(s);
            InterfaceSimulada Ireceiver = this.receiver.getInterfaceFor(d.getDestIP());
            if (d.getDestIP() == Ireceiver.getIpAddress()) {
                this.receiver.recebePacote(d);
            } else if (d.getTTL() > 0) {
                d.decrementaTTL();
                this.receiver.enviaPacote(d);
            } else {
                System.out.println("Pacote não pode ser entregue.");
            }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
