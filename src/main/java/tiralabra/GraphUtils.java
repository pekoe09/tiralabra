package tiralabra;

import java.util.List;

/**
 * Apumetodiluokka, joka sisältää verkkoon liittyviä apumetodeja.
 */
public class GraphUtils {
    
    /**
     * FindPlace-metodi etsii annetusta verkosta paikan nimen perusteella paikkaa
     * vastaavan PlaceNode-olion.
     * @param graph Verkko kuvattuna PlaceNode-olioita sisältävänä List-oliona.
     * @param name  Etsittävän paikan nimi.
     * @return      Etsittyä paikkaa vastaava PlaceNode-olio.
     * @throws      IllegalArgumentException Jos etsittävää paikkaa ei löydy verkon solmuista.
     */
    public static PlaceNode findPlace(List<PlaceNode> graph, String name) {
        for(int i = 0; i < graph.size(); i++) {
            if(graph.get(i).getName().equals(name)) {
                return graph.get(i);
            }
        }
        throw new IllegalArgumentException(String.format("Paikkaa %s ei löydy", name));
    }
}
