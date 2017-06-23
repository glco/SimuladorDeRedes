package datagrama;

import java.io.UnsupportedEncodingException;

public interface DatagramaFactory {

	public Datagrama criaDatagrama(String data, String destIp, String sourceIp) throws UnsupportedEncodingException;

}
