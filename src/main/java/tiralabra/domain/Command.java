package tiralabra.domain;

/**
 * Polunetsintäkomentoa kuvaava olio. Kenttinä polunetsinnässä käytettävän
 * verkon tiedot sisältävän tiedoston polku, polum lähtöpaikan nimi sekä 
 * polun maalipaikan nimi. 
 */
public class Command implements INamedObject{
    
    private final String filePath;
    private final String startPlaceName;
    private final String endPlaceName;
    
    /**
     * Konstruktori asettaa olion kentät annettujen parametrien mukaisiksi.
     * @param filePath          Polunetsinnässä käytettävän verkon tiedot sisältävän tiedoston polku.
     * @param startPlaceName    Polun lähtöpaikan nimi.
     * @param endPlaceName      Polun maalipaikan nimi.
     */
    public Command(String filePath, String startPlaceName, String endPlaceName) {
        this.filePath = filePath;
        this.startPlaceName = startPlaceName;
        this.endPlaceName = endPlaceName;
    }

    public String getFilePath() {
        return filePath;
    }
    
    public String getStartPlaceName() {
        return startPlaceName;
    }

    public String getEndPlaceName() {
        return endPlaceName;
    }
    
    @Override
    public String getName() {
        return String.format("%s %s %s", filePath, startPlaceName, endPlaceName);
    }    
}
