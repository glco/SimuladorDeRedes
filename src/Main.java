import java.io.IOException;

import console.Console;
import controlador.Controlador;
import controlador.ControladorIPV4;
import roteador.RoteadorIPV4;
import servidor.Servidor;

public class Main {

	public static void main(String [] args){
		//TODO tratar porta, nome de arquivo
		if(args.length < 2){
			System.err.println("Wrong number of parameters");
			System.exit(-1);
		}
		Controlador c = null;
		try {
			//s = new Servidor(Integer.parseInt(args[0]), args[1] , args[2]);
			c = new ControladorIPV4(new RoteadorIPV4(Integer.parseInt(args[0]), args[1] , args[2], c));
		} catch (NumberFormatException  | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Console console = new Console(c);
		//new Thread(s).start();
	}
	
}
