package servidor;

import java.io.IOException;
import java.net.*;
import java.util.HashMap;
import cliente.*;

public class Servidor implements Runnable{
	private int porta;
	private ServerSocket serverSocket;
	private HashMap<String,Cliente> vizinhos = null;
	public Servidor(int porta){
		this.porta = porta;
		this.vizinhos = new HashMap<>();
	}
	
	public void init(){
		new Thread(this).start();
	}
	
	public void shutdown() throws IOException{
		this.serverSocket.close();
	}
	
	public void executaAcao(String s) throws NumberFormatException, UnknownHostException, IOException{
		String [] args = s.split(" ");
		if(args[0].compareTo("open") == 0){
			abreConexao(args[1], Integer.parseInt(args[2]));
		}else if(args[0].compareTo("send") == 0){
			String msg = "";
			for(int i=2;i<args.length;i++) msg +=" "+args[i];
			enviaMensagem(args[1], msg);
		}
	}
	
	private void enviaMensagem(String ip,String msg) throws IOException{
		if(vizinhos.containsKey(ip)== false)
			throw new IllegalArgumentException("Host não encontrado!");
		vizinhos.get(ip).enviarMensagem(msg);
		
	}
	
	private void abreConexao(String ip,int porta) throws UnknownHostException, IOException{
		Cliente c = null;
		if(vizinhos.containsKey(ip))
			c = vizinhos.get(ip);
		else{
			c = new Cliente(ip,porta,null);
			vizinhos.put(ip, c);
		}
		c.abreConexao();
	}
	
	@Override
	public void run() {
		try {
			this.serverSocket = new ServerSocket(porta);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Socket clienteSocket = null;
		Cliente c = null; 
		System.out.println("Aceitando conexões na porta: "+this.serverSocket.getLocalPort());
		while(true){
			try {
				clienteSocket = this.serverSocket.accept();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("Conexao recebida de <"+clienteSocket.getInetAddress().getHostAddress()+":"+clienteSocket.getPort()+">");
			c = new Cliente(clienteSocket.getInetAddress().getHostAddress(),clienteSocket.getPort(),clienteSocket);
			new Thread(c).start();
		}
	}
}
