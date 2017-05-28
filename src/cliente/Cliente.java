package cliente;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Cliente implements Runnable{
	private int porta;
	private String ipAddr;
	private Socket inSocket;
	private Socket outSocket;
	private PrintWriter out;
	
	
	public Cliente(String ip,int p,Socket s){
		this.porta = p;
		this.ipAddr = ip;
		this.inSocket = s;
	}
	
	public void enviarMensagem(String s) throws IOException{
		out.println(s);
		System.out.println("Mensagem enviada para <"+outSocket.getInetAddress().getHostAddress()+">");
	}
	
	public void abreConexao() throws UnknownHostException, IOException{
		outSocket = new Socket(ipAddr,porta);
		out = new PrintWriter(outSocket.getOutputStream(),true);
		System.out.println("Conexão aberta com <"+outSocket.getInetAddress().getHostAddress()+":"+outSocket.getPort()+">");
	}
	
	public int getPorta() {
		return porta;
	}
	
	public String getEnderecoIp(){
		return this.ipAddr;
	}
	
	public Socket getSocket(){
		return this.inSocket;
	}
	
	public void setSocket(Socket s){
		this.inSocket = s;
	}
	
	//Enquanto o cliente envia mensagem, mantém a conexão aberta.
	@Override
	public void run() {
		if(inSocket == null)
			return;
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(inSocket.getInputStream()));
			String s;
			while((s = in.readLine()) != null){
				System.out.println("<"+this.ipAddr+">:" + s);
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
}
