package tiralabra.datastructures;

import java.util.Iterator;
import tiralabra.domain.INamedObject;

/**
 * 
 * Iterator-implementointi: http://stackoverflow.com/questions/5849154/can-we-write-our-own-iterator-in-java
 */
public class NamedArrayList implements Iterable {
    
    private INamedObject[] array;
    private int size;
    
    public NamedArrayList() {
        array = new INamedObject[100];
        size = 0;
    }
    
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
    
    public INamedObject findByName(String name) {
        if(name == null || name.trim().length() == 0) {
            throw new IllegalArgumentException("Nimi ei voi olla tyhjä!");
        }
        for(int i = 0; i < size; i++) {
            if(array[i].getName().equalsIgnoreCase(name)) {
                return array[i];
            }
        }        
        return null;
    }
    
    public int size() {
        return size;
    }

    private void growArray() {
        INamedObject[] newArray = new INamedObject[array.length * 2];
        for (int i = 0; i < array.length; i++) {
            newArray[i] = array[i];
        }
        array = newArray;
    }
    
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
