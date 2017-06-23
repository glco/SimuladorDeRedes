package roteador;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import controlador.Controlador;
import fileManager.FileReader;
import interfaceSimulada.InterfaceDeEntrada;
import interfaceSimulada.InterfaceDeSaida;
import interfaceSimulada.InterfaceSimulada;
import datagrama.Datagrama;
import datagrama.DatagramaIpv4;

public class RoteadorIPV4 extends Roteador {

	public RoteadorIPV4(int porta, String hostFile, String virtualIpFile, String routingTableFile, Controlador c) throws IOException {
		super(porta, routingTableFile, c);
		this.setInterfaces(new LinkedList<InterfaceSimulada>());
		inicializaInterfaces(hostFile, virtualIpFile);

	}

	@Override
	public void recebePacote(Datagrama data) throws UnsupportedEncodingException {
		System.out.println(data.toString());
	}

	@Override
	public void enviaMensagem(String msg, String ip) throws IOException {
		// Envia mensagem msg usando a interface com ip virutal == ip
        this.getInterfaceFor(ip).enviaMensagem(msg, ip);
		//System.out.println(d.toString());
	}

    @Override
    public void enviaPacote(Datagrama data) throws UnsupportedEncodingException {
        DatagramaIpv4 datagrama = (DatagramaIpv4) data;
        this.getInterfaceFor(datagrama.getDestIP()).enviaPacote(data);
    }

	@Override
	public void abreConexoes() throws UnknownHostException, IOException {
		// Abre conexão com todos os sockets, (Cria o socket/interface de saida)
		if(this.getInterfaces() == null)
			return;
		System.out.println("Abrindo conexões");
		for(InterfaceSimulada i : this.getInterfaces()){
			i.openConnection();
		}
		System.out.println("Conexões abertas com sucesso");
	}


	@Override
	public void receberConexao(Socket client) {
		//TODO controle sobre threads
		System.out.println("Conexao recebida de <"+client.getInetAddress().getHostAddress()+":"+client.getPort()+">");
		new Thread(new InterfaceDeEntrada(client,this)).start();
	}

	@Override
	public void inicializaInterfaces(String host, String virtualIp) throws IOException {
		// testar se está funcionando
		LinkedList<String[]> interfacesEndTemp = new FileReader().readNetworkFile(host);
		LinkedList<String[]> interfacesVirtualEndTemp = new FileReader().readNetworkFile(host);

		if(interfacesEndTemp.size() != interfacesVirtualEndTemp.size()){
			throw new Error("Numero de entradas nos dois arquivos de inicialização são diferentes!");
		}

		for (String [] end : interfacesEndTemp) {
			for(String[] virtEnd : interfacesVirtualEndTemp){
				this.getInterfaces().add(new InterfaceDeSaida(end[0],Integer.parseInt(end[1]), virtEnd[0],virtEnd[1], this));
			}
		}

	}

}
