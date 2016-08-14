package tiralabra.datainput;

import tiralabra.enums.ReadTarget;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * GraphFileHandler-luokan tehtävänä on käsitellä verkon tiedot sisältävä data-tiedosto
 * ja lukea sen sisältö.
 */
public class GraphFileHandler {

    /**
     * ReadGraphFile-metodi avaa luettavan tiedoston ja kutsuu readLines-metodia, joka suorittaa
     * sisäänluvun.
     * @param filePath  Luettavan tiedoston sijaintipolku.
     * @param mapper    IDataMapper-rajapinnan toteuttava luokka, joka tulkitsee sisäänluetut tiedot.
     * @throws          IllegalArgumentException Jos tiedoston avaaminen/sulkeminen ei onnistu.
     */
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
    
    /**
     * ReadLines-metodi lukee datatiedoston sisällön rivi riviltä sisään ja kutsuu mapper-olion metodia
     * jokaisen rivin kohdalla, jotta mapper tulkitsisi luetut tiedot.
     * @param reader    BufferedReader, joka on linkattu avattuun datatiedostoon.
     * @param mapper    IDataMapper-rajapinnan toteuttava luokka, joka tulkitsee luetut tiedot.
     * @param target    ReadTarget-enumin arvo: NODE_BASIC_DATA kun luetaan paikkojen omat tiedot sisään
     *                  (ensimmäinen kierros) ja NODE_NEIGHBOUR_DATA kun luetaan paikkojen naapuruustiedot 
     *                  (toinen kierros).
     * @throws          IllegalArgumentException Jos rivin sisäänluvussa tapahtui virhe.
     */
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
