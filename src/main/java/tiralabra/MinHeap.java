package tiralabra;

/**
 * Minimikeko, joka tallentaa PlaceNode-olioina kuvattuja paikkoja niihin
 * liitetyn etäisyystiedon mukaisessa prioriteettijärjestyksessä.
 */
public class MinHeap {
    
    NeighbourNode[] heap;
    int heapsize;
    
    /**
     * Minimikeko-olion konstruktori.
     * @param size Minimikekoon tallennettavaksi aiottujen PlaceNode-olioiden lukumäärä.
     */
    public MinHeap(int size) {
        if(size < 0) {
            throw new IllegalArgumentException("Keon koko ei voi olla negatiivinen");
        }
        this.heap = new NeighbourNode[size];
        this.heapsize = 0;
    }
    
    /**
     * Palauttaa annetun indeksin mukaisen keon alkion vasemman lapsen indeksin keon taulukossa.
     * @param heapindex Tutkittavan alkion indeksi keon taulukossa.
     * @return Tutkittavan alkion vasemman lapsen indeksi keon taulukossa.
     * @throws IllegalArgumentException Jos annettu tutkittava indeksi on negatiivinen.
     */
    public int left(int heapindex) {
        if(heapindex < 0) {
            throw new IllegalArgumentException("indeksi ei voi olla negatiivinen");
        }
        return 2 * heapindex;
    }
    
    /**
     * Palauttaa annetun indeksin mukaisen keon alkion oikean lapsen indeksin keon taulukossa.
     * @param heapindex Tutkittavan alkion indeksi keon taulukossa.
     * @return Tutkittavan alkion oikean lapsen indeksi keon taulukossa.
     * @throws IllegalArgumentException Jos annettu tutkittava indeksi on negatiivinen.
     */
    public int right(int heapindex) {
        if(heapindex < 0) {
            throw new IllegalArgumentException("indeksi ei voi olla negatiivinen");
        }
        return 2 * heapindex + 1;
    }
    
    /**
     * Palauttaa annetun indeksin mukaisen keon alkion vanhemman indeksin keon taulukossa.
     * @param heapindex Tutkittavan alkion indeksi keon taulukossa.
     * @return Tutkittavan alkion vanhemman indeksi keon taulukossa.
     * @throws IllegalArgumentException Jos annettu tutkittava indeksi on negatiivinen.
     */
    public int parent(int heapindex) {
        if(heapindex < 0) {
            throw new IllegalArgumentException("indeksi ei voi olla negatiivinen");
        }
        return heapindex / 2;
    }

    /**
     * Lisää annetun PlaceNode-olion kekoon ja asettaa sen avaintiedoksi annetun etäisyystiedon.
     * @param node  PlaceNode-olio, joka halutaan lisätä  kekoon.
     * @param distance PlaceNode-olioon liittyvä etäisyystieto, jota käytetään sen avaimena keossa.
     */
    public void insert(PlaceNode node, double distance) {
        NeighbourNode newnode = new NeighbourNode(node, distance);
        heapsize++;
        int heapindex = heapsize - 1;
        while(heapindex > 0 && heap[parent(heapindex)].getDistance() > distance) {
            heap[heapindex] = heap[parent(heapindex)];
            heapindex = parent(heapindex);
        }
        node.setHeapindex(heapindex);
        heap[heapindex] = newnode;
    }
    
    /**
     * Metodi, joka korjaa kekoehdon jos se on rikki annetun indeksin kohdalla.
     * @param heapindex Keon taulukon indeksi, jonka kohdalta kekoehtoa korjataan.
     */
    public void heapify(int heapindex) {
        int left = left(heapindex);
        int right = right(heapindex);
        if(right <= this.heapsize) {
            int smallest = (heap[left].getDistance() < heap[right].getDistance()) ? left : right;
            if(heap[heapindex].getDistance() > heap[smallest].getDistance()) {
                exchange(heapindex, smallest);
                heapify(smallest);
            }
        } else if(left == heapsize && heap[heapindex].getDistance() > heap[left].getDistance()) {
            exchange(heapindex, left);
        }
    }
    
    /**
     * Vaihtaa kahden keon alkion paikkaa päittäin ja päivittää niihin liitetyn kekoindeksitiedon.
     * @param index1    Ensimmäisen päittäin vaihdettavan alkion indeksi keon taulukossa.
     * @param index2    Toisen päittäin vaihdettavan alkion indeksi keon taulukossa.
     * @throws IllegalArgumentException Jos jompi kumpi annetuista indekseistä on negatiivinen.
     */
    public void exchange(int index1, int index2) {
        if(index1 < 0 || index2 < 0) {
            throw new IllegalArgumentException("Indeksi on negatiivinen");
        }
        NeighbourNode node1 = heap[index1];
        heap[index1] = heap[index2];
        heap[index2] = node1;
        heap[index2].getNeighbour().setHeapindex(index2);
        heap[index1].getNeighbour().setHeapindex(index1);
    }

    /**
     * Kertoo onko keko tyhjä alkioista.
     * @return True jos keossa ei ole alkioita, false jos alkioita on.
     */
    public boolean isEmpty() {
        return this.heapsize == 0;
    }   

    /**
     * Poistaa keosta prioriteettijärjestyksessä ensimmäisen alkion (eli sen, jolla on
     * pienin etäisyystieto kaikista keossa olevista alkioista) ja siirtää jäljellä 
     * olevia alkioita niin että kekoehto säilyy.
     * @return Keon prioriteettijärjestyksessä ensimmäinen alkio.
     */
    public PlaceNode del_min() {
        PlaceNode closest = heap[0].getNeighbour();
        heap[0] = heap[heapsize - 1];
        heap[0].getNeighbour().setHeapindex(0);
        heapsize--;
        heapify(0);
        return closest;
    }    

    /**
     * Pienentää annettuun PlaceNode-alkioon liitettyä etäisyystietoa ja korjaa tämän
     * jälkeen kekoa niin että kekoehto säilyy,
     * @param neighbour PlaceNode-olio, johon liitettyä etäisyystietoa on tarkoitus muuttaa.
     * @param distance Uusi etäisyystieto
     */
    public void decrease_key(PlaceNode neighbour, double distance) {
        if(distance < heap[neighbour.getHeapindex()].getDistance()) {
            heap[neighbour.getHeapindex()].setDistance(distance);
            heapify(neighbour.getHeapindex());
        }
    }

    /**
     * Palauttaa kekoa kuvaavan taulukon.
     * @return Kekoa kuvaava taulukko, joka sisältää NeighbourNode-olioita.
     */
    public NeighbourNode[] getHeap() {
        return heap;
    }

    /**
     * Palauttaa keon alkioiden lukumäärän.
     * @return Keon alkioiden lukumäärä.
     */
    public int getHeapsize() {
        return heapsize;
    }
    
}
