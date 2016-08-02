package tiralabra;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileReader {

    static List<PlaceNode> readGraphFile(String filePath) {
        
        Scanner in;
        try {
        in = new Scanner(new File(filePath)); 
        } catch (FileNotFoundException exc) {
            throw new IllegalArgumentException("Tiedostoa ei voi avata!");
        }
        
        String placeRow;
        PlaceNode newPlace;
        List<PlaceNode> graphData = new ArrayList<>();
        Integer rowCounter = 0;
        while(in.hasNextLine()) {
            placeRow = in.nextLine();
            rowCounter++;
            String[] placeData = placeRow.split(";");
            if(placeData.length < 3) {
                throw new IllegalArgumentException(
                        String.format("Virhe tiedostossa: rivillä %d on liian vähän tietoa:\n %s", rowCounter, placeRow));
            }
            String placeName = placeData[0];
            try {
                Double latitude = Double.parseDouble(placeData[1]);
                Double longitude = Double.parseDouble(placeData[2]);
                newPlace = new PlaceNode(placeName, latitude, longitude);
            } catch(Exception exc) {
                newPlace = null;
                throw new IllegalArgumentException(
                        String.format("Virhe tiedostossa: rivillä %d epäkuranttia dataa:\n %s", rowCounter, placeRow));
            }
            graphData.add(newPlace);
        }

        return graphData;
    }    
}
