package roteador;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;

import controlador.Controlador;
import datagrama.Datagrama;
import datagrama.DatagramaIpv4;
import fileManager.FileReader;
import interfaceSimulada.InterfaceSimulada;
import interfaceSimulada.ConnectionWorker;
import servidor.Servidor;

public class Roteador {
	private LinkedList<InterfaceSimulada> interfaces = null;
	private Servidor servidor = null;
	private Controlador controlador = null;

	protected RoutingTable tabelaRoteamento = null;

	public Roteador(int porta, String hostFile, String virtualIpFile, String routingTableFile, Controlador c) throws IOException{
		this.tabelaRoteamento = new RoutingTable(new FileReader().readNetworkFile(routingTableFile));
		this.servidor = new Servidor(porta,this);
		this.controlador = c;
        this.interfaces = new LinkedList<InterfaceSimulada>();
        this.inicializaInterfaces(hostFile, virtualIpFile);
	}

    public InterfaceSimulada getInterfaceFor(String ip){
        int i = this.tabelaRoteamento.getInterfaceIndex(ip);
        if (i > -1) {
            return this.interfaces.get(i);
        } else {
            return null;
        }
    }

	public Servidor getServidor() {
		return servidor;
	}

	public void setServidor(Servidor servidor) {
		this.servidor = servidor;
	}

	public Controlador getControlador() {
		return controlador;
	}

	public void setControlador(Controlador controlador) {
		this.controlador = controlador;
	}

	public void shutdown() {

	}

	public void enviaMensagem(String msg,String ip) throws IOException {
        InterfaceSimulada i = this.getInterfaceFor(ip);
        if (i != null) {
            i.enviaMensagem(msg, ip);
        } else {
            System.out.println("Rede inalcansável para o IP " + ip + ".");
        }
    }

	public void receberConexao(Socket client){
        System.out.println("Conexao recebida de <"+client.getInetAddress().getHostAddress()+":"+client.getPort()+">");
		new Thread(new ConnectionWorker(client, this)).start();
    }

	public void recebePacote(Datagrama data) throws UnsupportedEncodingException{
        System.out.println("Pacote Recebido:");
		System.out.println(data.toString());
    }

	public void enviaPacote(Datagrama data) throws UnsupportedEncodingException, IOException{
        DatagramaIpv4 datagrama = (DatagramaIpv4) data;
        InterfaceSimulada i = this.getInterfaceFor(datagrama.getDestIP());
        if (i != null) {
            i.enviaPacote(datagrama);
        } else {
            System.out.println("Rede inalcansavel para o IP " + datagrama.getDestIP() + ".");
        }
    }

	public void abreConexoes() throws UnknownHostException, IOException{
        // Abre conexão com todos os sockets, (Cria o socket/interface de saida)
		if(this.interfaces == null)
			return;
		System.out.println("Abrindo conexões");
		for(InterfaceSimulada i : this.interfaces){
			i.openConnection();
		}
		System.out.println("Conexões abertas com sucesso");
    }

    public void inicializaInterfaces(String host, String virtualIp) throws IOException {
		// testar se está funcionando
		LinkedList<String[]> interfacesEndTemp = new FileReader().readNetworkFile(host);
		LinkedList<String[]> interfacesVirtualEndTemp = new FileReader().readNetworkFile(virtualIp);

		if(interfacesEndTemp.size() != interfacesVirtualEndTemp.size()){
			throw new Error("Numero de entradas nos dois arquivos de inicialização são diferentes!");
		}

		for (int i = 0; i < interfacesEndTemp.size(); i++) {
            String [] end = interfacesEndTemp.get(i);
            String [] virtEnd = interfacesVirtualEndTemp.get(i);
            InterfaceSimulada a = new InterfaceSimulada(virtEnd[0], virtEnd[1], end[0], Integer.parseInt(end[1]), this);
            // a.openConnection();
            // new Thread(a).start();
            this.interfaces.add(a);
		}

	}

}
