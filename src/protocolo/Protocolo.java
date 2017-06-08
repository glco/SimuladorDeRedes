package protocolo;

import datagrama.Datagrama;

public interface Protocolo {
	public  String desencapsular(String d);
	public  Datagrama encapsular(String s);
}

