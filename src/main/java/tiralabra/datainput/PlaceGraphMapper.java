package tiralabra.datainput;

import tiralabra.domain.NeighbourNode;
import tiralabra.domain.PlaceNode;
import tiralabra.enums.ReadTarget;
import tiralabra.datastructures.NamedArrayList;

/**
 * IDataMapper-rajapinnan toteuttava luokka, jonka tehtävänä on tulkita datatiedostosta
 * sisäänluettuja paikkatietueita.
 */
public class PlaceGraphMapper implements IGraphMapper {
    
    private NamedArrayList graph;
    private final int BASIC_FIELDS = 3;
    private long numberOfEdgeEndPoints = 0;
    
    /**
     * Konstruktori asettaa paikkojen muodostamaa verkkoa kuvaavan NamedArrayList-olion tyhjäksi listaksi.
     */
    public PlaceGraphMapper() {
        this.graph = new NamedArrayList();
    }

    /**
     * IDataMapper-rajapinnan metodi, joka tulkitsee yhden sisäänluetun tietueen tiedot.
     * @param data          Sisäänluettu paikkatieto merkkijonomuotoisena.
     * @param rowCounter    Laskuri sisäänluettujen rivien määrälle.
     * @param target        ReadTarget-enumin arvo: NODE_BASIC_DATA kun luetaan paikkojen omat tiedot sisään
     *                      (ensimmäinen kierros) ja NODE_NEIGHBOUR_DATA kun luetaan paikkojen naapuruustiedot (toinen kierros).
     */
    @Override
    public void mapData(String data, Integer rowCounter, ReadTarget target) {   
        if(data == null) {
            throw new IllegalArgumentException("Dataa ei ole!");
        }
        String[] placeData = data.split(";");
        if(placeData.length < BASIC_FIELDS) {
            throw new IllegalArgumentException(
                    String.format("Virhe tiedostossa: rivillä %d on liian vähän tietoa:\n %s", rowCounter, data));
        }        
        try {
            if(target == ReadTarget.NODE_BASIC_DATA) {
                addPlace(placeData); 
            } else {
                addNeighbours(placeData);
            }
        } catch (IllegalArgumentException exc) {
            throw new IllegalArgumentException(
                    String.format("Virhe tiedostossa: rivillä %d epäkuranttia dataa:\n%s\n%s", rowCounter, exc.getMessage(), data));
        }
    }

    /**
     * Lisää verkkoa kuvaavaan listaan uuden paikkatiedon.
     * @param placeData Paikkatiedon tietokentät merkkijonoja sisältävänä taulukkona.
     * Taulukon pituuden on oltava vähintään kolme; ensimmäinen merkkijono on paikan nimi,
     * toinen merkkijono paikan leveysastekoordinaatti desimaalimuotoisena, kolmas 
     * merkkijono paikan pituusastekoordinaatti desimaalimuotoisena. Tämän jälkeen tulevat
     * merkkijonot ovat ao. paikan naapuripaikkojen nimiä ja etäisyyksiä, muodossa 
     * "[nimi]/[etäisyys]", missä etäisyys on ilmaistu desimaalilukua vastaavana
     * merkkijonona ja sen on oltava vähintään 0.
     * @throws IllegalArgumentException Jos annetusta merkkijonotaulukosta ei voi muodostaa PlaceNode-oliota.
     */
    public void addPlace(String[] placeData) {
        PlaceNode newPlace = null;
        String placeName = placeData[0];
        try {
            Double latitude = Double.parseDouble(placeData[1]);
            Double longitude = Double.parseDouble(placeData[2]);
            newPlace = new PlaceNode(placeName, latitude, longitude);
        } catch(Exception exc) {
            throw new IllegalArgumentException("Paikkadatassa virhe");
        }
        graph.add(newPlace);       
    }
        
    /**
     * IDataMapper-rajapinnan metodi, joka palauttaa luetut tiedot verkkona.
     * @return Verkko PlaceNode-olioita sisältävän List-olion muodossa.
     */
    @Override
    public NamedArrayList getData() {
        return this.graph;
    }    

    /**
     * Lisää paikan naapuruustiedot (naapuripaikan nimi + etäisyys sinne) verkkoon
     * tallennettuun paikkaan.
     * @param placeData Paikkatiedon tietokentät merkkijonoja sisältävänä taulukkona.
     * Taulukon pituuden on oltava vähintään kolme; ensimmäinen merkkijono on paikan nimi,
     * toinen merkkijono paikan leveysastekoordinaatti desimaalimuotoisena, kolmas 
     * merkkijono paikan pituusastekoordinaatti desimaalimuotoisena. Tämän jälkeen tulevat
     * merkkijonot ovat ao. paikan naapuripaikkojen nimiä ja etäisyyksiä, muodossa 
     * "[nimi]/[etäisyys]", missä etäisyys on ilmaistu desimaalilukua vastaavana
     * merkkijonona ja sen on oltava vähintään 0.
     * @throws IllegalArgumentException Jos naapurin etäisyystietoa ei ole, se ei ole
     * parsittavissa liukuluvuksi tai se on negatiivinen.
     */
    public void addNeighbours(String[] placeData) {
        PlaceNode place = (PlaceNode)graph.findByName((String)placeData[0]);       
        NeighbourNode[] neighbours = new NeighbourNode[placeData.length - BASIC_FIELDS];
        for(int i = BASIC_FIELDS; i < placeData.length; i++) {
            String[] neighbourData = placeData[i].split("/");
            PlaceNode neighbour = (PlaceNode)graph.findByName(neighbourData[0]);
            try {
                double distance = Double.parseDouble(neighbourData[1]);
                neighbours[i - BASIC_FIELDS] = new NeighbourNode(neighbour, distance);   
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException exc) {
                throw new IllegalArgumentException(String.format("Naapurin %s etäisyys on virheellinen", neighbourData[0]));
            }                     
        }
        place.setNeighbours(neighbours);
        numberOfEdgeEndPoints += neighbours.length;
    }

    /**
     * IGraphMapper-rajapinnan metodi, joka kertoo verkon kaarien lukumäärän.
     * @return Verkon kaarien lukumäärä.
     */
    @Override
    public long getNumberOfEdges() {
        return numberOfEdgeEndPoints / 2;
    }
    
    @Override
    public String toString() {
        return String.format("verkossa on %d solmua ja %d kaarta", graph.size(), getNumberOfEdges());
    }
    
    /**
     * IDataMapper-rajapinnan metodi, joka tyhjentää luetut tiedot.
     */
    @Override
    public void resetMapper() {
        graph = new NamedArrayList();
        numberOfEdgeEndPoints = 0;
    }
}
