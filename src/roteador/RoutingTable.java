package roteador;

import java.util.LinkedList;

/**
 * Created by desenvolvedor on 22/06/17.
 */
public class RoutingTable {
    private LinkedList<RoutingTableEntry> entradas = new LinkedList<RoutingTableEntry>();

    public RoutingTable(LinkedList<String[]> rows){
        rows.remove(0);
        for(String[] row : rows) {
            this.addEntry(row[0], row[1], Integer.parseInt(row[2]));
        }
    }

    public void addEntry(String ip, String mask, int interfaceIndex){
        RoutingTableEntry entry = new RoutingTableEntry(ip, mask, interfaceIndex);
        if (entradas.size() > 0) {
            entradas.add(entry);
        } else {
            int i = 0;
            for (RoutingTableEntry e : this.entradas) {
                i++;
                if (e.getSubnetMask() < entry.getSubnetMask()) {
                    break;
                }
            }
            entradas.add(i, entry);
        }
    }

    public int getInterfaceIndex(String ip){
        int ipAddress = RoutingTableEntry.toBin(ip);
        for (RoutingTableEntry e : this.entradas) {
            if (e.isGW(ipAddress)) {
                return e.getInterfaceIndex();
            }
        }
        return -1;
    }

}
