import java.io.IOException;

import console.Console;
import controlador.Controlador;
import controlador.ControladorIPV4;
import roteador.Roteador;
import servidor.Servidor;

public class Main {

	/**
	 * nextNodesAddressesFile contains the address of the nodes
	 * connected to the source.
	 *
	 * interfacesForNextNodesFile contains the interface to access
	 * neighbor routers from source.
	 *
	 * @param args
	 */
	public static void main(String [] args){
		//TODO tratar porta, nome de arquivo
		if(args.length < 2){
			System.err.println("Wrong number of parameters");
			System.exit(-1);
		}
		Controlador c = null;
		try {
			//s = new Servidor(Integer.parseInt(args[0]), args[1] , args[2]);
			int port = Integer.parseInt(args[0]);
			String nextNodesAddressesFile = args[1];
			String interfacesForNextNodesFile = args[2];
			String routingTableFile = args[3];
			c = new ControladorIPV4(
					new Roteador(port, nextNodesAddressesFile, interfacesForNextNodesFile, routingTableFile, c));
		} catch (NumberFormatException  | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Console console = new Console(c);
		//new Thread(s).start();
	}

}
