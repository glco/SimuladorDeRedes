package datagrama;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class DatagramaIpv4  implements Datagrama{
	/* O byte do java usa 1 byte de espaço, mas como está em complemento a 2 1111 1111 representa -128 
	 * e isso estava gerando inconsistenias por isso usei int, no caso dos arrays cada indice vai representar um byte. */
	private int typeOfService = 0;
	private int flagsAndFragmentOffset = 0;
	private int ttl = 7;
	private int checksum = 0;
	private int version =4;
	private int headerLength = 5;
	private int upperLayerProtocol = 0;
	private int identification = 0;//new byte[2];
	private int totalLenght;
	private int [] sourceAddr = new int[4];
	private int [] destAddr = new int[4];
	private int [] data = null;

	
	public DatagramaIpv4(String data,String dest, String source) throws UnsupportedEncodingException{
		this.sourceAddr = this.ipStringToInt(source);
		this.destAddr = this.ipStringToInt(dest);
		this.data = stringtoIntArray(data);		
		//this.totalLenght = stringtoIntArray(Integer.toBinaryString(headerLength + data.getBytes().length)); 
		this.totalLenght = headerLength + data.getBytes().length;
	}
	public DatagramaIpv4(String pkg) throws UnsupportedEncodingException{
		this.version = Integer.parseInt(pkg.substring(0,4),2);
		this.headerLength = Integer.parseInt(pkg.substring(4,8),2);
		this.typeOfService = Integer.parseInt(pkg.substring(8,16),2);
		this.totalLenght = Integer.parseInt(pkg.substring(16,32),2);
		this.identification = Integer.parseInt(pkg.substring(32,48),2);
		this.flagsAndFragmentOffset = Integer.parseInt(pkg.substring(48, 64),2);
		this.ttl = Integer.parseInt(pkg.substring(64,72),2);
		this.upperLayerProtocol = Integer.parseInt(pkg.substring(72,80),2);
		this.checksum  = Integer.parseInt(pkg.substring(80, 96),2);
		this.sourceAddr = binaryIpStringToInt(pkg.substring(96,128));
		this.destAddr = binaryIpStringToInt(pkg.substring(128, 160));
		this.data = binaryStringToIntArray(pkg.substring(160, pkg.length()));
	}
	
	private int[] binaryStringToIntArray(String s) throws UnsupportedEncodingException{
		int [] ans = new int[s.length()/8];
		for(int i = 0; i <= s.length()-8; i += 8) //String binaria, percorre os bytes
		{
		     ans[i/8] = Integer.parseInt(s.substring(i, i+8), 2);
		}
		return ans;
	}
	
	private int [] stringtoIntArray(String s) throws UnsupportedEncodingException{
		byte [] byteArray = s.getBytes("UTF-8");
		int [] arr = new int[byteArray.length];
		for(int i =0; i< arr.length; i++){
			arr[i] = (int) byteArray[i];
		}
		return arr;
	}
	
	private int[] binaryIpStringToInt(String binaryIp){
		int [] ans = new int[4];
		int b = 0;
		for(int i=0; i<ans.length ; i++){
			ans[i] = Integer.parseInt(binaryIp.substring(b, b+8),2);
			b+=8;
		}
		return ans;
	}
	
	private int[] ipStringToInt(String ip){
		String [] s = ip.split("\\.");
		int [] resp = new int[s.length];
		for(int i=0;i<s.length;i++){
			resp[i] = Integer.parseInt(s[i]);
		}
		return resp;
	}
	private String intArrayToIpString(int [] array){
		String s = "";
		for(int i = 0; i<  array.length ;i++){

			s += array[i];
			if(i<array.length-1)
				s+=".";
		}
		return s;
	}
	
	
	private String intToPaddedBinaryString(int i,int nBits){
		return 	String.format("%"+nBits+"s", Integer.toBinaryString(i)).replace(' ', '0');
	}
	
	private String intToPaddedBinaryString(int[]i){
		String s = "";
		for(int j : i){
			s += String.format("%8s", Integer.toBinaryString(j)).replace(' ', '0');
		}
		return s;
	}
	
	@Override
	public String toBinaryString(){
		String s = intToPaddedBinaryString(version,4)+
				   intToPaddedBinaryString(headerLength,4)+
				   intToPaddedBinaryString(typeOfService,8)+
				   intToPaddedBinaryString(totalLenght,16)+
				   intToPaddedBinaryString(identification,16)+
				   intToPaddedBinaryString(flagsAndFragmentOffset,16)+
				   intToPaddedBinaryString(ttl,8)+
				   intToPaddedBinaryString(upperLayerProtocol,8)+
				   intToPaddedBinaryString(checksum,16)+				   
				   intToPaddedBinaryString(sourceAddr)+
				   intToPaddedBinaryString(destAddr)+
				   intToPaddedBinaryString(data);
		return s;
	}
	
	private String intArraytoString(int [] array,boolean convertTochar){
		String s = "";
		for(int i : array){
			if(convertTochar == true)
				s +=(char) i;
			else
				s += i;
		}
		return s;
	}
	
	@Override
	public String toString(){
		String s = "Versão: "					+this.version 										+"\n"+
				   "Tamanho do header: "		+this.headerLength 									+"\n"+
				   "Type of Service: "			+this.typeOfService									+"\n"+
				  // "Tamanho do datagrama:"		+this.intArraytoString(this.totalLenght)			+"\n"+
				   "Tamanho do datagrama:"		+this.totalLenght									+"\n"+
				   "Identificação: "			+this.identification			                    +"\n"+
				   "flags e fragment offset: "	+this.flagsAndFragmentOffset	                    +"\n"+
				   "TTL: "						+this.ttl											+"\n"+
				   "Protocolo: "				+this.upperLayerProtocol							+"\n"+
				   "Checksum: "					+this.checksum										+"\n"+
				   "Ip origem: "				+this.intArrayToIpString(this.sourceAddr)			+"\n"+
				   "Ip de destino: "			+this.intArrayToIpString(this.destAddr)				+"\n"+
				   "Data:  "					+this.intArraytoString(this.data,true)                       ;	
		return s;
	}
	
	@Override
	public boolean decrementaTTL() {
		// TODO Auto-generated method stub
		return false;
	}

}
