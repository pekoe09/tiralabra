package tiralabra;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileHandler {

    public static void readGraphFile(String filePath, IDataMapper mapper) {        
        try {
            FileReader reader = new FileReader(filePath);
            readBuffer(new BufferedReader(reader), mapper);
        } catch (FileNotFoundException exc) {
            throw new IllegalArgumentException(String.format("Tiedostoa %s ei voi avata!", filePath));
        }      
    }    
    
    public static void readBuffer(BufferedReader reader, IDataMapper mapper) {                
        Integer rowCounter = 0;

        try {
            String dataRow = reader.readLine();
            while(dataRow != null) {
                rowCounter++;
                mapper.mapData(dataRow, rowCounter);
                dataRow = reader.readLine();
            }
            reader.close();
        } catch (IOException exc) {
            throw new IllegalArgumentException("Tiedostoa ei voi avata!");
        }
    }
}
