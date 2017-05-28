import console.Console;
import servidor.Servidor;

public class Main {

	public static void main(String [] args){
		//TODO tratar porta, nome de arquivo		
		Servidor s = new Servidor(Integer.parseInt(args[0]));
		s.init();
		Console c = new Console(s);
		//new Thread(s).start();
	}
	
}
