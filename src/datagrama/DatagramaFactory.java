package datagrama;

import java.io.UnsupportedEncodingException;

public interface DatagramaFactory {
	
	public Datagrama criaDatagrama(String pkg) throws UnsupportedEncodingException;
	public Datagrama criaDatagrama(String data, String destIp, String sourceIp) throws UnsupportedEncodingException;
	
}
