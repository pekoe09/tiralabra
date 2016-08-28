package tiralabra.domain;

/**
 * Polunetsintäpyynnön tuottamien, eri algoritmeilla saatujen tulosten joukkoa
 * kuvaava olio. 
 */
public class PathSearchResultSet implements INamedObject {
    
    private final PathSearchResult[] results;
    
    /**
     * Konstruktori asettaa tulostaulukoksi parametrina saadun taulukon.
     * @param results   Eri algoritmien samasta polunetsintäpyynnöstä saamien tulosten 
     *                  muodostama taulukko
     */
    public PathSearchResultSet(PathSearchResult[] results) {
        this.results = results;
    }

    /**
     * Palauttaa tulostaulukon annetussa indeksissä sijaitsevan tuloksen.
     * @param index     Tulostaulukon indeksi, jossa sijaitseva tulos halutaan.
     * @return          Polunetsintäpyynnön tulos, joka sijaitsee pyydetyssä indeksissä.
     * @throws          IllegalArgumentException Jos tulostaulukko on tyhjä.
     * @throws          IndexOutOfBoundsException Jos pyydetty indeksi on negatiivinen tai
     *                  suurempi kuin tallennettujen tulosten määrä.
     */
    public PathSearchResult get(int index) {
        if(results == null) {
            throw new IllegalArgumentException("Tuloksia ei vielä ole.");
        }
        if(index > results.length - 1) {
            throw new IndexOutOfBoundsException(
                    String.format("Tulossetin suurin indeksi on %d mutta pyydetään indeksiä %d",
                            results.length - 1, index));
        }
        if(index < 0) {
            throw new IndexOutOfBoundsException("Indeksi ei voi olla negatiivinen luku.");
        }
        return results[index];
    }
    
    @Override
    public String getName() {
        if(results == null || results[0] == null)  {
            return null;
        }
        return String.format("%s - paikasta %s paikkaan %s", 
                results[0].getFilePath(), 
                results[0].getStartPlace().getName(), 
                results[0].getEndPlace().getName());
    }
    
    /**
     * Palauttaa tallennettujen tulosten lukumäärän.
     * @return  Taulukkoon tallennettujen tulosten lukumäärä.
     */
    public int size() {
        if (results == null) {
            return 0;
        }
        return results.length;
    }
    
    /**
     * Palauttaa tulosten tallentamiseen käytetyn taulukon.
     * @return  Tuloksia sisältävä taulukko.
     */
    public PathSearchResult[] getResultArray() {
        return results;
    }
}
