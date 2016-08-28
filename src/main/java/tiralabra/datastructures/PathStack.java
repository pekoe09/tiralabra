package tiralabra.datastructures;

import tiralabra.domain.PlaceNode;

/**
 * Pinorakenne paikkasolmuista muodostuvan polun tallentamiseen.
 */
public class PathStack {
    
    private PlaceNode[] places;
    private int top;
    
    /**
     * Konstruktori alustaa olion kentät.
     * @param size  Tallennettavaksi aiottavan polun pituus paikkasolmujen lukumääränä.
     *              Oltava suurempi kuin 0.
     * @throws      IllegalArgumentException Jos annettu pituus on pienempi kuin 1.
     */
    public PathStack(int size) {
        if(size < 1) {
            throw new IllegalArgumentException("Polun pituuden on oltava suurempi kuin 0");
        }
        top = -1;
        places = new PlaceNode[size];
    }
    
    /**
     * Lisää polkuun uuden paikan.
     * @param place Polkuun lisättävä paikka.
     * @throws      IllegalArgumentException Jos polkuun yritetään lisätä null.
     */
    public void push(PlaceNode place) {
        if(place == null) {
            throw new IllegalArgumentException("Paikkasolmu ei voi olla null");
        }
        top++;
        places[top] = place;
    }
    
    /**
     * Ottaa polusta pois viimeisimmän lisätyn paikan ja palauttaa sen.
     * @return Viimeksi polkuun lisätty paikka.
     * @throws IllegalArgumentException Jos polulla ei ole paikkoja.
     */
    public PlaceNode pop() {
        if(isEmpty()) {
            throw new IllegalArgumentException("Polku on tyhjä");
        }
        PlaceNode nextPlace = places[top];
        top--;
        return nextPlace;
    }
    
    /**
     * Kertoo onko polulla tallennettuna paikkoja.
     * @return True jos polulla ei ole paikkoja, false jos polulla on paikkoja.
     */
    public boolean isEmpty() {
        return top == -1;
    }

    /**
     * Getter palauttaa taulukon, johon on tallennettu polun paikat.
     * @return Taulukko, johon on tallennettu polun paikat.
     */
    public PlaceNode[] getPlaces() {
        return places;
    }

    /**
     * Palauttaa viimeisimmän polulle lisätyn paikan sijainti-indeksin (nollapohjainen)
     * polun paikat varastoivassa taulukossa.
     * @return Paikkataulukon indeksi, jossa polun viimeisin paikka sijaitsee.
     */
    public int getTop() {
        return top;
    }
}
