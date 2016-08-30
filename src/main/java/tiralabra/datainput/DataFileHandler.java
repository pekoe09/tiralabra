package tiralabra.datainput;

import tiralabra.enums.ReadTarget;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import tiralabra.ui.Messenger;

/**
 * DataFileHandler-luokan tehtävänä on käsitellä verkon tiedot sisältävä tiedosto
 * tai skriptin sisältävä tiedosto ja lukea sen sisältö.
 */
public class DataFileHandler {
    
    private final IDataMapper mapper;
    private final Messenger messenger;
    
    /**
     * Konstruktori asettaa injektoidut riippuvuudet.
     * @param mapper    Luetun tiedoston tulkitsemiseen kykenevä IDataMapper-rajapinnan toteuttava luokka.
     * @param messenger Käyttäjälle näytettävän informaation esittämisestä vastaava luokka.
     */
    public DataFileHandler(IDataMapper mapper, Messenger messenger) {
        this.mapper = mapper;
        this.messenger = messenger;
    }
        
    /**
     * Lukee paikkadataa sisältävän tiedoston  sisään ja muodostaa siitä verkon.
     * @param filePath          Datatiedoston tiedostopolku.
     * @return                  IDataMapper-olio, joka sisältää paikkojen ja niiden välisten yhteyksien
     *                          muodostaman verkon.
     */
    public IDataMapper readDataFile(String filePath) {
        mapper.resetMapper();
        try {
            processFile(filePath, mapper);
        } catch (Exception exc) {
            messenger.printFileError(exc.getMessage(), filePath);
            return null;
        }    
        return mapper;
    }

    /**
     * Metodi avaa luettavan tiedoston ja kutsuu readLines-metodia, joka suorittaa
     * sisäänluvun.
     * @param filePath  Luettavan tiedoston sijaintipolku.
     * @param mapper    IDataMapper-rajapinnan toteuttava luokka, joka tulkitsee sisäänluetut tiedot.
     * @throws          IllegalArgumentException Jos tiedoston avaaminen/sulkeminen ei onnistu.
     */
    public void processFile(String filePath, IDataMapper mapper) {        
        try {
            FileReader reader = new FileReader(filePath);
            if(mapper instanceof IGraphMapper) {                
                readLines(new BufferedReader(reader), mapper, ReadTarget.NODE_BASIC_DATA);
                reader.close();
                reader = new FileReader(filePath);
                readLines(new BufferedReader(reader), mapper, ReadTarget.NODE_NEIGHBOUR_DATA);                
            } else {
                readLines(new BufferedReader(reader), mapper, ReadTarget.SCRIPT);
            }
            reader.close();
        } catch (Exception exc) {
            throw new IllegalArgumentException(String.format("Tiedostoa %s ei voi lukea!", filePath));
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
    public void readLines(BufferedReader reader, IDataMapper mapper, ReadTarget target) {                
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
