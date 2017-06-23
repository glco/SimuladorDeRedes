package roteador;

import java.util.LinkedList;

/**
 * Created by desenvolvedor on 22/06/17.
 */
public class RoutingTableEntry {
    private int SubnetAddress = 0;
    private int SubnetMask = 0;
    private int interfaceIndex = 0;

    public RoutingTableEntry(String[] row){
        if(row.length < 3) return;
        this.SubnetAddress = RoutingTableEntry.toBin(row[0]);
        this.SubnetMask = RoutingTableEntry.toBin(row[1]);
        this.interfaceIndex = Integer.parseInt(row[2]);
    }

    public int getSubnetMask(){
        return this.SubnetMask;
    }

    public int getInterfaceIndex(){
        return this.interfaceIndex;
    }

    public RoutingTableEntry(String ip, String mask, int interfaceIndex){
        this.SubnetAddress = RoutingTableEntry.toBin(ip);
        this.SubnetMask = RoutingTableEntry.toBin(mask);
        this.interfaceIndex = interfaceIndex;
    }

    public boolean isGW(int ip){
        return (this.SubnetMask & this.SubnetAddress) == (this.SubnetMask & ip);
    }

    public static int toBin(String addr){
        String[] splittedAddr = addr.split("\\.");
        if (splittedAddr.length == 4) {
            int a = Integer.parseInt(splittedAddr[0]);
            int b = Integer.parseInt(splittedAddr[1]);
            int c = Integer.parseInt(splittedAddr[2]);
            int d = Integer.parseInt(splittedAddr[3]);
            return ((a << 24) + (b << 16) + (c << 8) + d);
        } else {
            //mascara em formato decimal
            int mLen = Integer.parseInt(addr);
            int mask = 0;
            for (int i = 0; i < mLen ; i++) {
                mask += (1 << (mLen - i));
            }
            return mask;
        }
    }
}
