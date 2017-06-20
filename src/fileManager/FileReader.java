package fileManager;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.io.IOException;

public class fileReader {

    public LinkedList<String[]> readNetworkFile(String fileName) {
        LinkedList<String[]> array = new LinkedList<>();
        BufferedReader buffer = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
        String s = null;
        String [] aux = null;
        while((s = buffer.readLine()) != null){
            aux = s.split(" ");
            array.push(aux);
        }
        buffer.close();

        return array;
    }
}
