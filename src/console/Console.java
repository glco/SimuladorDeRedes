package console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import controlador.Controlador;

public class Console implements Runnable{
	private Controlador controlador;
	private BufferedReader stdIn;
	
	public Console(Controlador c){
		this.controlador = c;
		this.init();
	}
	
	
	private void init(){
		stdIn = new BufferedReader(new InputStreamReader(System.in));
		new Thread(this).start();
	}
	private void printUsoIncorreto(){
		System.err.println("Uso incorreto! digite help para obter ajuda.");
	}
	private void printMenu(){
		String modoDeUso = "Modo de uso: <comando> <argumentos>                                                                        \n";
		String comandos  = "Comandos: \n"+
						   "send <ip do host> <mensagem>  ** Envia <mensagem> para o host especificado.                             ** \n"+
						   "open <ip> <porta>             ** Tenta abrir uma conexao com o host especificado na porta especificada. ** \n"+
						   "show                          ** Imprime os hosts disponiveis                                           ** \n"+
						   "help                          ** Imprime este menu novamente.                                           ** \n"+
						   "quit ou exit                  ** terminar a execução do programa.                                       **   ";
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
					controlador.shutdown();
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.exit(0);
				return;
			}else{
				String [] args = s.split(" ");
				if(args[0].compareTo("send")!=0 && args[0].compareTo("open")!=0 && args[0].compareTo("show")!=0){
					printUsoIncorreto();
				} else
					try {
						if(args[0].compareTo("send")==0 && args.length ==3)
							controlador.enviaMensagem(args[2], args[1]);
						else if(args[0].compareTo("send")==0 && args.length >=3){
							String msg = "";
							for(int i =2;i<args.length;i++){
								msg+=args[i];
								if(i<args.length-1)
									msg+=" ";
							}
							controlador.enviaMensagem(msg, args[1]);
						}else if(args[0].compareTo("send")==0 && args.length < 3)
							printUsoIncorreto();
						else if(args[0].compareTo("open")==0)
							controlador.abreConexoes();
						else if(args[0].compareTo("show") == 0)
							controlador.imprimeInterfaces();
					} catch (NumberFormatException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
		}
	}
	
	
}
