package servidor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

import controlador.Controlador;
import interfaceSimulada.*;
import roteador.Roteador;

public class Servidor implements Runnable{
	private int porta = 0;
	private ServerSocket serverSocket = null;
	private Roteador roteador;
	
	public Servidor(int porta, Roteador r) throws IOException{
		this.porta = porta;
		this.roteador = r;
		new Thread(this).start();
	}
	
//	private void iniciaVizinhos(String hostFile,String virtualIpFile) throws IOException{
//		BufferedReader in = new BufferedReader(new FileReader(new File(hostFile)));
//		String s,ip,porta;
//		InterfaceSimulada c = null;
//		while((s = in.readLine()) != null){
//			if(s.compareTo(" ") != 0){
//				String aux[] = s.split(" ");
//				ip= aux[0];
//				porta = aux[1];
//				c = new InterfaceSimulada(ip, Integer.parseInt(porta), null);
//				vizinhos.put(ip+porta,c);
//			}
//		}
//		in.close();
//	}
	
	public void init(){
		new Thread(this).start();
	}
	
	public void shutdown() throws IOException{
		this.serverSocket.close();
	}
	
//	public void executaAcao(String s) throws NumberFormatException, UnknownHostException, IOException{
//		String [] args = s.split(" ");
//		if(args[0].compareTo("open") == 0){
//			if(args.length == 1){
//				System.out.println("IP list:");
//				this.imprimeVizinhos();
//			}else{
//				System.out.println(args[1]+" "+args[2]);
//				abreConexao(args[1], Integer.parseInt(args[2]));
//				
//			}
//		}else if(args[0].compareTo("send") == 0){
//			String msg = "";
//			for(int i=3;i<args.length;i++) msg +=" "+args[i];
//			enviaMensagem(args[1]+args[2], msg);
//		}
//	}
	
//	private void enviaMensagem(String ip,String msg) throws IOException{
//		if(vizinhos.containsKey(ip)== false)
//			throw new IllegalArgumentException("Host não encontrado!");
//		vizinhos.get(ip).enviarMensagem(msg);
//		
//	}
//	
//	private void imprimeVizinhos(){
//		for(Map.Entry<String,InterfaceSimulada> entry : vizinhos.entrySet()){
//			System.out.println(entry.getValue().toString());
//		}
//	}
	
//	private void abreConexao(String ip,int porta) throws UnknownHostException, IOException{
//		InterfaceSimulada c = null;
//		if(vizinhos.containsKey(ip+porta))
//			c = vizinhos.get(ip+porta);
//		else{
//			// Remover
//			c = new InterfaceSimulada(ip,porta,null);
//			vizinhos.put(ip+porta, c);
//		}
//		c.abreConexao();
//	}
	
	@Override
	public void run() {
		try {
			this.serverSocket = new ServerSocket(porta);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Socket clienteSocket = null;
		System.out.println("Aceitando conexões na porta: "+this.serverSocket.getLocalPort());
		while(true){
			try {
				clienteSocket = this.serverSocket.accept();
			} catch (IOException e) {
				e.printStackTrace();
			}
//			System.out.println("Conexao recebida de <"+clienteSocket.getInetAddress().getHostAddress()+":"+clienteSocket.getPort()+">");
			roteador.receberConexao(clienteSocket);
		}
	}
}
