package tiralabra;

/**
 * Minimikeko, joka tallentaa PlaceNode-olioina kuvattuja paikkoja niihin
 * liitetyn etäisyystiedon mukaisessa prioriteettijärjestyksessä; PlaceNode-etäisyys-pari
 * kuvataan NeighbourNode-oliona.
 */
public class MinHeap {
    
    NeighbourNode[] heap;
    int heapsize;
    
    /**
     * Minimikeko-olion konstruktori.
     * @param size Minimikekoon tallennettavaksi aiottujen PlaceNode-olioiden lukumäärä.
     * @throws IllegalArgumentException Jos keon kooksi annetaan negatiivinen luku.
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
     * @param heapindex Tutkittavan alkion 0-pohjainen indeksi keon taulukossa.
     * @return Tutkittavan alkion vasemman lapsen 0-pohjainen indeksi keon taulukossa.
     * @throws IllegalArgumentException Jos annettu tutkittava indeksi on negatiivinen.
     */
    public int left(int heapindex) {
        if(heapindex < 0) {
            throw new IllegalArgumentException("indeksi ei voi olla negatiivinen");
        }
        return 2 * (heapindex + 1) - 1;
    }
    
    /**
     * Palauttaa annetun indeksin mukaisen keon alkion oikean lapsen indeksin keon taulukossa.
     * @param heapindex Tutkittavan alkion 0-pohjainen indeksi keon taulukossa.
     * @return Tutkittavan alkion oikean lapsen 0-pohjainen indeksi keon taulukossa.
     * @throws IllegalArgumentException Jos annettu tutkittava indeksi on negatiivinen.
     */
    public int right(int heapindex) {
        if(heapindex < 0) {
            throw new IllegalArgumentException("indeksi ei voi olla negatiivinen");
        }
        return 2 * (heapindex + 1);
    }
    
    /**
     * Palauttaa annetun indeksin mukaisen keon alkion vanhemman indeksin keon taulukossa.
     * @param heapindex Tutkittavan alkion 0-pohjainen indeksi keon taulukossa.
     * @return Tutkittavan alkion vanhemman 0-pohjainen indeksi keon taulukossa.
     * @throws IllegalArgumentException Jos annettu tutkittava indeksi on negatiivinen.
     */
    public int parent(int heapindex) {
        if(heapindex < 0) {
            throw new IllegalArgumentException("indeksi ei voi olla negatiivinen");
        }
        return (heapindex + 1)/ 2 - 1;
    }

    /**
     * Lisää annetun PlaceNode-olion kekoon ja asettaa sen avaintiedoksi annetun etäisyystiedon.
     * @param node  PlaceNode-olio, joka halutaan lisätä kekoon.
     * @param distance PlaceNode-olioon liittyvä etäisyystieto, jota käytetään sen avaimena keossa.
     * HUOM! Etäisyystiedon on oltava positiivinen.
     * @throws IllegalArgumentException Jos etäisyystieto on negatiivinen.
     */
    public void insert(PlaceNode node, double distance) {
        if(distance < 0) {
            throw new IllegalArgumentException("Etäisyystieto ei voi olla negatiivinen");
        }
        NeighbourNode newnode = new NeighbourNode(node, distance);
        heapsize++;
        int heapindex = heapsize - 1;
        while(heapindex > 0 && heap[parent(heapindex)].getDistance() > distance) {
            heap[heapindex] = heap[parent(heapindex)];
            heap[heapindex].getNeighbour().setHeapindex(heapindex);
            heapindex = parent(heapindex);
        }
        node.setHeapindex(heapindex);
        node.setIsInHeap(true);
        heap[heapindex] = newnode;
    }
    
    /**
     * Metodi, joka korjaa kekoehdon jos se on rikki annetun indeksin kohdalla.
     * @param heapindex Keon taulukon 0-pohjainen indeksi, jonka kohdalta kekoehtoa korjataan.
     * @throws IllegalArgumentException Jos annettu taulukon indeksi on negatiivinen.
     */
    public void heapify(int heapindex) {
        if(heapindex < 0) {
            throw new IllegalArgumentException("Indeksi ei voi olla negatiivinen");
        }
        int left = left(heapindex);
        int right = right(heapindex);
        if(right <= this.heapsize - 1) {
            int smallest = (heap[left].getDistance() < heap[right].getDistance()) ? left : right;
            if(heap[heapindex].getDistance() > heap[smallest].getDistance()) {
                exchange(heapindex, smallest);
                heapify(smallest);
            }
        } else if(left == heapsize - 1 && heap[heapindex].getDistance() > heap[left].getDistance()) {
            exchange(heapindex, left);
        }
    }
    
    /**
     * Vaihtaa kahden keon alkion paikkaa päittäin ja päivittää niihin liitetyn kekoindeksitiedon.
     * @param index1    Ensimmäisen päittäin vaihdettavan alkion 0-pohjainen indeksi keon taulukossa.
     * @param index2    Toisen päittäin vaihdettavan alkion 0-pohjainen indeksi keon taulukossa.
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
     * @throws IndexOutOfBoundsException Jos keossa ei ole alkioita jäljellä.
     */
    public PlaceNode del_min() {
        if(heapsize == 0) {
            throw new IndexOutOfBoundsException("Keko on tyhjä");
        }
        PlaceNode closest = heap[0].getNeighbour();
        closest.setIsInHeap(false);
        heap[0] = heap[heapsize - 1];
        heap[0].getNeighbour().setHeapindex(0);
        heapsize--;
        heapify(0);
        return closest;
    }    

    /**
     * Pienentää annettuun PlaceNode-alkioon liitettyä etäisyystietoa ja korjaa tämän
     * jälkeen kekoa niin että kekoehto säilyy.
     * @param neighbour PlaceNode-olio, johon liitettyä etäisyystietoa on tarkoitus muuttaa.
     * @param distance Uusi etäisyystieto; ei voi olla negatiivinen luku.
     * @throws IllegalArgumentException Jos annettu alkio on null tai annettu etäisyys on negatiivinen
     */
    public void decrease_key(PlaceNode neighbour, double distance) {
        if(neighbour == null) {
            throw new IllegalArgumentException("Muutettava alkio ei voi olla null");
        }
        if(distance < 0) {
            throw new IllegalArgumentException("Etäisyys ei voi olla negatiivinen");
        }
        if(neighbour.getIsInHeap() && distance < heap[neighbour.getHeapindex()].getDistance()) {
            int i = neighbour.getHeapindex();
            System.out.println("Asetetaan " + neighbour.getName() + " etäisyys: " + distance);
            heap[i].setDistance(distance);
            while(i > 0 && heap[parent(i)].getDistance() > heap[i].getDistance()) {
                exchange(i, parent(i));
                i = parent(i);
            }
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
