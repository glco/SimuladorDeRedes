package console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import servidor.Servidor;

public class Console implements Runnable{
	private Servidor servidor;
	private BufferedReader stdIn;
	
	public Console(Servidor s){
		this.servidor = s;
		this.init();
	}

	private void init(){
		stdIn = new BufferedReader(new InputStreamReader(System.in));
		new Thread(this).start();
	}
	
	private void printMenu(){
		String modoDeUso = "Modo de uso: <comando> <argumentos>\n";
		String comandos = "Comandos: \n"+
						  "send <ip do host> <mensagem>  ** Envia <mensagem> para o host especificado. **\n"+
						  "open <ip> <porta> ** Tenta abrir uma conexao com o host especificado na porta especificada. ** \n"+
						  "help  *Imprime este menu novamente.*\n"+
						  "quit ou exit para terminar a execução do programa.";
		System.out.println(modoDeUso + comandos);
	}
	@Override
	public void run() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		printMenu();
		String s = null;
		while(true){
			try {
				s = stdIn.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(s.compareTo("help")==0)
				printMenu();
			else if(s.compareTo("exit") == 0  || s.compareTo("quit")==0){
				try {
					servidor.shutdown();
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.exit(0);
				return;
			}else{
				String [] args = s.split(" ");
				if(args[0].compareTo("send")!=0 && args[0].compareTo("open")!=0 ){
					System.err.println("Uso incorreto! digite help para obter ajuda.");
				} else
					try {
						servidor.executaAcao(s);
					} catch (NumberFormatException | IOException e) {
						e.printStackTrace();
					}
			}
		}
	}
	
	
}
