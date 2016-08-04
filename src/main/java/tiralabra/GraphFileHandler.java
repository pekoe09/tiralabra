package tiralabra;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class GraphFileHandler {

    public static void readGraphFile(String filePath, IDataMapper mapper) {        
        try {
            FileReader reader = new FileReader(filePath);
            readLines(new BufferedReader(reader), mapper, ReadTarget.NODE_BASIC_DATA);
            reader.close();
            reader = new FileReader(filePath);
            readLines(new BufferedReader(reader), mapper, ReadTarget.NODE_NEIGHBOUR_DATA);
            reader.close();
        } catch (FileNotFoundException exc) {
            throw new IllegalArgumentException(String.format("Tiedostoa %s ei voi avata!", filePath));
        } catch (IOException exc) {
            throw new IllegalArgumentException(String.format("Tiedostoa %s ei voi sulkea!", filePath));
        }          
    }    
    
    public static void readLines(BufferedReader reader, IDataMapper mapper, ReadTarget target) {                
        Integer rowCounter = 0;

        try {
            String dataRow = reader.readLine();
            while(dataRow != null) {
                rowCounter++;
                mapper.mapData(dataRow, rowCounter, target);
                dataRow = reader.readLine();
            }
        } catch (IOException exc) {
            throw new IllegalArgumentException("Tiedoston luvussa tapahtui virhe!");
        }
    }

}
