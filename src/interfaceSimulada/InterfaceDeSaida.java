package interfaceSimulada;

import java.io.IOException;
import java.io.PrintWriter;

import datagrama.Datagrama;
import datagrama.DatagramaIpv4;
import datagrama.DatagramaIpv4Factory;

import roteador.Roteador;

public class InterfaceDeSaida extends InterfaceSimulada {
	private String ipVirtual = null;
	private String subMascara = null;

	public InterfaceDeSaida(String ip, int port,String ipVirtual,String sub,Roteador r) {
		super(ip, port, r);
		this.ipVirtual = ipVirtual;
		this.subMascara = sub;
	}


	public String getIpVirtual() {
		return ipVirtual;
	}
	public void setIpVirtual(String ipVirtual) {
		this.ipVirtual = ipVirtual;
	}
	public String getSubMascara() {
		return subMascara;
	}
	public void setSubMascara(String subMascara) {
		this.subMascara = subMascara;
	}
	@Override
	public void enviaMensagem(String msg, String dst) throws IOException{
        DatagramaIpv4 datagrama = new DatagramaIpv4(msg, dst, this.ipVirtual);
        this.enviaPacote(datagrama);
	}

    public void enviaPacote(Datagrama datagrama) throws IOException {
        PrintWriter out = new PrintWriter(this.getHost().getOutputStream(),true);
		out.println(datagrama.toBinaryString());
		out.close();
    }

}
