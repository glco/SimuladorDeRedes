package datagrama;

import java.io.UnsupportedEncodingException;

public class DatagramaIpv4Factory implements DatagramaFactory {

	public DatagramaIpv4Factory() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Datagrama criaDatagrama(String data,String destIp,String sourceIp) throws UnsupportedEncodingException {
		return new DatagramaIpv4(data,destIp,sourceIp);
	}

	@Override
	public Datagrama criaDatagrama(String pkg) throws UnsupportedEncodingException {
		return new DatagramaIpv4(pkg);
	}
}
