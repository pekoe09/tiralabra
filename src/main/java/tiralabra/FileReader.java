package tiralabra;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileReader {

    static void readGraphFile(String filePath, IDataMapper mapper) {
        
        Scanner in;
        try {
        in = new Scanner(new File(filePath)); 
        } catch (FileNotFoundException exc) {
            throw new IllegalArgumentException("Tiedostoa ei voi avata!");
        }
        
        String dataRow;        
        Integer rowCounter = 0;

        while(in.hasNextLine()) {
            dataRow = in.nextLine();
            rowCounter++;
            mapper.mapData(dataRow, rowCounter);
        }
        
        in.close();
    }    
}
