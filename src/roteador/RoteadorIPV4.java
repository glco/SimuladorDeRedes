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
import interfaceSimulada.InterfaceDeEntrada;
import interfaceSimulada.InterfaceDeSaida;
import interfaceSimulada.InterfaceSimulada;
import parser.StringParser;

public class RoteadorIPV4 extends Roteador {

	public RoteadorIPV4(int porta, String hostFile, String virtualIpFile,Controlador c) throws IOException {
		super(porta,c);
		this.setInterfaces(new HashMap<String,InterfaceSimulada>());
		inicializaInterfaces(hostFile, virtualIpFile);
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
			String ar[] = StringParser.splitByNumber(msg, 65535);
			for(int i = 0; i < ar.length; i++){
				Datagrama  d = this.getDatagramaFactory().criaDatagrama(ar[i],"0000.0000.0000.0000", ip);
				this.getInterfaces().get(ip).enviaMensagem(d.toBinaryString());
				//System.out.println(d.toBinaryString());
				System.out.println(d.toString());
			}
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
	public void inicializaInterfaces(String host, String virtualIp) throws IOException {
		BufferedReader hostsFile = new BufferedReader(new InputStreamReader(new FileInputStream(host)));
		String s = null;
		LinkedList<String[]> interfacesEndTemp = new LinkedList<>();
		LinkedList<String[]> interfacesVirtualEndTemp = new LinkedList<>();;
		String [] aux = null;
		
		while((s = hostsFile.readLine()) != null){
			aux = s.split(" ");
			interfacesEndTemp.push(aux);
		}
		hostsFile.close();
		BufferedReader virtualIpFile = new BufferedReader(new InputStreamReader(new FileInputStream(virtualIp)));
		s = null;
		aux = null;
		
		while((s = virtualIpFile.readLine()) != null){
			aux = s.split(" ");
			interfacesVirtualEndTemp.push(aux);
		}
		virtualIpFile.close();
		
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
