package fileManager;

import java.io.*;
import java.util.LinkedList;

public class FileReader {

    public LinkedList<String[]> readNetworkFile(String fileName) throws IOException {
        String s = null;
        String [] aux = null;
        LinkedList<String[]> array = new LinkedList<>();
        BufferedReader buffer = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
        while((s = buffer.readLine()) != null){
            aux = s.split(" ");
            array.push(aux);
        }
        buffer.close();
        return array;
    }
}
