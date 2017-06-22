package roteador;

import java.util.LinkedList;

/**
 * Created by desenvolvedor on 22/06/17.
 */
public class RoutingTableEntry {
    private String SubnetAddress = null;
    private String SubnetMask = null;
    private int interfaceIndex = 0;

    public RoutingTableEntry(String[] row){
        if(row.length < 3) return;
        this.SubnetAddress = row[0];
        this.SubnetMask = row[1];
        this.interfaceIndex = Integer.parseInt(row[2]);
    }
}
