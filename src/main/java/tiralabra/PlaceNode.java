package tiralabra;

/**
 * Paikkaa kuvaava olio. Sisältää kenttinä paikan nimen, leveys- ja pituusastekoordinaatit,
 * listauksen paikan naapuripaikoista, paikan etäisyyden polun lähtö- ja maalipaikoista,
 * tieto paikasta, joka edeltää tätä paikkaa lyhimmällä polulla lähtöpaikasta,
 * sekä tiedon, missä kohtaa kaikki verkon paikat sisältävässä minimikeossa tämä paikka on. 
 */
public class PlaceNode {
    
    private String name;
    private Double latitude;
    private Double longitude;    
    private NeighbourNode[] neighbours;
    private double startDistance;
    private double endDistance;
    private PlaceNode pathPredecessor;
    private int heapindex;
    
    /**
     * Konstruktori asettaa paikan nimen sekä koordinaattitiedot.
     * @param name      Paikan nimi.
     * @param latitude  Paikan leveysastekoordinaatti desimaalimuodossa.
     * @param longitude Paikan pituusastekoordinaatti desimaalimuodossa.
     */
    public PlaceNode(String name, Double latitude, Double longitude) {
        setName(name);
        setLatitude(latitude);
        setLongitude(longitude);
    }

    /**
     * Palauttaa paikan nimen.
     * @return Paikan nimi
     */
    public String getName() {
        return name;
    }

    /**
     * Asettaa paikan nimen.
     * @param name Paikan nimi,
     * @throws IllegalArgumentException Jos paikan nimi on whitespacea tai null.
     */
    public void setName(String name) {
        if(name == null || name.trim().length() == 0) {
            throw new IllegalArgumentException("Paikan nimi on tyhjä");
        }
        this.name = name;
    }

    /**
     * Palauttaa paikan leveysasteen.
     * @return Paikan leveysastekoordinaatti desimaalimuodossa.
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * Asettaa paikan leveysastekoordinaatin.
     * @param latitude Paikan leveysastekoordinaatti desimaalimuodossa.
     * @throws IllegalArgumentException Jos leveysasteen itseisarvo > 90.
     */
    public void setLatitude(Double latitude) {
        if(latitude == null) {
            throw new IllegalArgumentException("Leveysaste on tyhjä");
        }
        if(Math.abs(latitude) > 90) {
            throw new IllegalArgumentException(String.format("Leveysaste (%f) on suurempi kuin 90", latitude));
        }
        this.latitude = latitude;
    }

    /**
     * Palauttaa paikan pituusasteen.
     * @return Paikan pituusaste desimaalimuodossa,
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * Asettaa paikan pituusasteen.
     * @param longitude Paikan pituusaste desimaalimuodossa.
     * @throws IllegalArgumentException Jos pituusasteen itseisarvo > 180.
     */
    public void setLongitude(Double longitude) {
        if(longitude == null) {
            throw new IllegalArgumentException("Pituusaste on tyhjä");
        }
        if(Math.abs(longitude) > 180) {
            throw new IllegalArgumentException(String.format("Pituusaste (%f) on suurempi kuin 180", longitude));
        }
        this.longitude = longitude;
    }

    /**
     * Palauttaa paikan naapuripaikat.
     * @return Paikan naapuripaikat NeighbourNode-olioita sisältävänä taulukkona;
     * NeighbourNodessa on naapuripaikkaa vastaava PlaceNode-olio sekä tieto etäisyydestä
     * naapuripaikkaan desimaalilukuna.
     */
    public NeighbourNode[] getNeighbours() {
        return neighbours;
    }

    /**
     * Asettaa paikan naapuripaikkataulukon.
     * @param neighbours NeighbourNode-olioita sisältävä taulukko.
     */
    public void setNeighbours(NeighbourNode[] neighbours) {
        this.neighbours = neighbours;
    }

    /**
     * Palauttaa paikan etäisyyden polun lähtöpaikkaan.
     * @return Etäisyys polun lähtöpaikkaan desimaalilukuna.
     */
    public double getStartDistance() {
        return startDistance;
    }

    /**
     * Asettaa paikan etäisyyden polun lähtöpaikkaan.
     * @param startDistance Etäisyys polun lähtöpaikkaan.
     * @throws IllegalArgumentException Jos etäisyys on negatiivinen.
     */
    public void setStartDistance(double startDistance) {
        if(startDistance < 0) {
            throw new IllegalArgumentException(String.format("Etäisyys (%f) on negatiivinen", startDistance));
        }
        this.startDistance = startDistance;
    }

    /**
     * Palauttaa paikan etäisyyden polun maalipaikkaan.
     * @return Etäisyys polun maalipaikkaan desimaalilukuna.
     */
    public double getEndDistance() {
        return endDistance;
    }

    /**
     * Asettaa paikan etäisyyden polun maalipaikkaan.
     * @param endDistance Etäisyys polun maalipaikkaan.
     * @throws IllegalArgumentException Jos etäisyys on negatiivinen.
     */
    public void setEndDistance(double endDistance) {
        if(endDistance < 0) {
            throw new IllegalArgumentException(String.format("Etäisyys (%f) on negatiivinen", endDistance));
        }
        this.endDistance = endDistance;
    }

    /**
     * Palauttaa PlaceNode-olion, joka vastaa sitä paikkaa, joka on tämän paikan
     * edellä lyhimmällä polulla lähtöpaikasta tähän paikkaan.
     * @return Lyhimmällä polulla oleva edeltävä paikka.
     */
    public PlaceNode getPathPredecessor() {
        return pathPredecessor;
    }

    /**
     * Asettaa PlaceNode-olion, joka vastaa sitä paikkaa, joka on tämän paikan
     * edellä lyhimmällä polulla lähtöpaikasta tähän paikkaan.
     * @param pathPredecessor Lyhimmällä polulla oleva edeltävä paikka.
     */
    public void setPathPredecessor(PlaceNode pathPredecessor) {
        this.pathPredecessor = pathPredecessor;
    }  

    /**
     * Palauttaa tämän paikan indeksin minimikeossa, joka sisältää kaikki 
     * verkon paikat järjestettynä paikan ja lähtöpaikan välisen etäisyyden mukaan.
     * @return Paikan indeksi minimikeossa.
     */
    public int getHeapindex() {
        return heapindex;
    }

    /**
     * Asettaa tämän paikan indeksin minimikeossa, joka sisältää kaikki 
     * verkon paikat järjestettynä paikan ja lähtöpaikan välisen etäisyyden mukaan.
     * @param heapindex Paikan indeksi minimikeossa.
     */
    public void setHeapindex(int heapindex) {
        this.heapindex = heapindex;
    }   
    
    /**
     * Palauttaa tämän paikan etäisyyden tiettyyn naapuripaikkaan.
     * @param neighbourName Sen naapuripaikan nimi, jonka etäisyys halutaan saada.
     * @return Tämän paikan etäisyys annettuun naapuripaikkaan.
     */
    public double getDistanceToNeighbour(String neighbourName) {
        for (int i = 0; i < this.neighbours.length; i++) {
            if(this.neighbours[i].getNeighbour().getName().equals(neighbourName)){
                return this.neighbours[i].getDistance();
            }
        }
        return Integer.MAX_VALUE;
    }
}
