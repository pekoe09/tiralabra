package tiralabra.datastructures;

import java.text.Collator;
import java.util.Iterator;
import tiralabra.domain.INamedObject;

/**
 * Dynaamisesti kokoaan kasvattava taulukko, johon varastoidaan INamedObject-rajapinnan toteuttavien luokkien
 * olioita. Siten tämän taulukon sisältämiä alkioita voi hakea olion nimikentän perusteella.
 * Iterator-implementointi: http://stackoverflow.com/questions/5849154/can-we-write-our-own-iterator-in-java
 */
public class NamedArrayList implements Iterable {
    
    private INamedObject[] array;
    private int size;
    
    /**
     * Konstruktori alustaa 100 paikan mittaisen taulukon ja asettaa kokotiedoksi 0.
     */
    public NamedArrayList() {
        array = new INamedObject[100];
        size = 0;
    }
    
    /**
     * Lisää uuden olion taulukkoon.
     * @param newObject Taulukkoon lisättävä olio.
     */
    public void add(INamedObject newObject) {
        if(newObject == null) {
            throw new IllegalArgumentException("ArrayListiin lisättävä olio ei voi olla null!");
        }
        if(size >= array.length) {
            growArray();
        }
        array[size] = newObject;
        size++;        
    }
    
    /**
     * Palauttaa taulukon annetussa indeksissä sijaitsevan olion.
     * @param index Taulukon indeksi, jossa sijaitseva olio halutaan,
     * @return      Olio, joka sijaitsee annetussa indeksissä.
     * @throws      IndexOutOfBoundsException Jos annettu indeksi on negatiivinen tai on vähintään 
     * taulukkoon varastoitujen olioiden lukumäärä.
     */
    public INamedObject get(int index) {
        if(index > size - 1) {
            throw new IndexOutOfBoundsException(
                    String.format("Listan suurin indeksi on %d mutta pyydettiin indeksiä %d",
                            size - 1, index));
        }
        if(index < 0) {
            throw new IndexOutOfBoundsException("Indeksi ei voi olla negatiivinen.");
        }
        return array[index];
    }
    
    /**
     * Palauttaa sellaisen taulukon olion, jonka nimi vastaa annettua nimeä.
     * @param name  Nimi, jolla halutaan etsiä taulukosta oliota.
     * @return      Olio, jonka nimi täsmää annettuun.
     * @throws      IllegalArgumentException Jos annettu nimi on null tai tyhjä.
     */
    public INamedObject findByName(String name) {
        if(name == null || name.trim().length() == 0) {
            throw new IllegalArgumentException("Nimi on virheellinen!");
        }
        for(int i = 0; i < size; i++) {
            if(array[i].getName().toLowerCase().equals(name.toLowerCase())) {
                return array[i];
            }
        }        
        return null;
    }
    
    /**
     * Palauttaa taulukkoon tallennettujen olioiden lukumäärän.
     * @return      Taulukon olioiden lukumäärä.
     */
    public int size() {
        return size;
    }

    /**
     * Kasvattaa taulukon kokoa kaksinkertaiseksi.
     */
    private void growArray() {
        INamedObject[] newArray = new INamedObject[array.length * 2];
        for (int i = 0; i < array.length; i++) {
            newArray[i] = array[i];
        }
        array = newArray;
    }
    
    /**
     * Palauttaa olioiden varastointiin käytetyn taulukon.
     * @return  Olioiden varastointiin käytetty taulukko.
     */
    public INamedObject[] getArray() {
        return array;
    }

    @Override
    public Iterator iterator() {
        Iterator it = new Iterator() {
            
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < size && array[currentIndex] != null;
            }

            @Override
            public Object next() {
                if(currentIndex + 1 > array.length - 1) {
                    throw new IndexOutOfBoundsException();
                }
                return array[currentIndex++];
            }            
        };       
        
        return it;
    }    
}
