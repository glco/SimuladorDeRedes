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
            entradas.add(new RoutingTableEntry(row));
        }
    }
}
