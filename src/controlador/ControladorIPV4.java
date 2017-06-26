package controlador;

import java.io.IOException;

import protocolo.ProtocoloIpv4;
import roteador.Roteador;

public class ControladorIPV4 extends Controlador{

	public ControladorIPV4(Roteador r) throws IOException{
		super(new ProtocoloIpv4(), r);
	}
}
