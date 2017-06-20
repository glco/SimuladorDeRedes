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
import datagrama.Datagrama;
import datagrama.DatagramaIpv4;
import datagrama.DatagramaIpv4Factory;
import fileManager.FileReader;
import interfaceSimulada.InterfaceDeEntrada;
import interfaceSimulada.InterfaceDeSaida;
import interfaceSimulada.InterfaceSimulada;

public class RoteadorIPV4 extends Roteador {

	public RoteadorIPV4(int porta, String hostFile, String virtualIpFile, String routingTableFile, Controlador c) throws IOException {
		super(porta,c);
		this.setInterfaces(new HashMap<String,InterfaceSimulada>());
		inicializaInterfaces(hostFile, virtualIpFile, routingTableFile);
		this.setDatagramaFactory(new DatagramaIpv4Factory());
	}

	@Override
	public void recebePacote(String data) throws UnsupportedEncodingException {
		Datagrama d = getDatagramaFactory().criaDatagrama(data);
		System.out.println(d.toString());
	}

	@Override
	public void enviaMensagem(String msg, String ip) throws IOException {
		// Envia mensagem msg usando a interface com ip virutal == ip
		if(this.getInterfaces().containsKey(ip)){
			Datagrama  d = this.getDatagramaFactory().criaDatagrama(msg,"0000.0000.0000.0000", ip);
			this.getInterfaces().get(ip).enviaMensagem(d.toBinaryString());
			//System.out.println(d.toBinaryString());
			System.out.println(d.toString());
		}
		
	}
	
	@Override
	public void abreConexoes() throws UnknownHostException, IOException {
		// Abre conexão com todos os sockets, (Cria o socket/interface de saida)
		if(this.getInterfaces() == null)
			return;
		System.out.println("Abrindo conexões");
		for(Map.Entry<String, InterfaceSimulada> i : this.getInterfaces().entrySet()){
			i.getValue().openConnection();
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
	public void inicializaInterfaces(String host, String virtualIp, String routingTableFile) throws IOException {
		// testar se está funcionando
		LinkedList<String[]> interfacesEndTemp = new FileReader().readNetworkFile(host);
		LinkedList<String[]> interfacesVirtualEndTemp = new FileReader().readNetworkFile(host);

		// ainda não implementado para tabela de roteamento!
		// LinkedList<String[]> routingTableTemp = new FileReader().readNetworkFile(routingTableFile);

		if(interfacesEndTemp.size() != interfacesVirtualEndTemp.size()){
			throw new Error("Numero de entradas nos dois arquivos de inicialização são diferentes!");
		}
		
		for (String [] end : interfacesEndTemp) {
			for(String[] virtEnd : interfacesVirtualEndTemp){
				this.getInterfaces().put(virtEnd[0], new InterfaceDeSaida(end[0],Integer.parseInt(end[1]), virtEnd[0],virtEnd[1], this));
			}
		}
		
	}

}
