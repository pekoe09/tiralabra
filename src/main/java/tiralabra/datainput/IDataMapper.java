package tiralabra.datainput;

import tiralabra.enums.ReadTarget;
import tiralabra.datastructures.NamedArrayList;

/**
 * Rajapinta luokille, joiden tehtävä on tulkita datatiedostoista sisäänluettuja 
 * paikkatietoja.
 */
public interface IDataMapper {
    
    /**
     * Tulkitsee yhden sisäänluetun tietueen tiedot.
     * @param data      Sisäänluettu tietue merkkijonomuotoisena.
     * @param counter   Laskuri sisäänluettujen rivien määrälle.
     * @param target    ReadTarget-enumin arvo: NODE_BASIC_DATA kun luetaan paikkojen omat tiedot sisään
     * (ensimmäinen kierros) ja NODE_NEIGHBOUR_DATA kun luetaan paikkojen naapuruustiedot (toinen kierros).
     */
    public void mapData(String data, Integer counter, ReadTarget target);
    /**
     * Palauttaa luetut tiedot verkkona, joka on PlaceNode-olioita sisältävän List-olion muodossa.
     * @return Verkko PlaceNode-olioita sisältävän NamedArrayList-olion muodossa.
     */
    public NamedArrayList getData();

    
    public void resetMapper();
    
}
