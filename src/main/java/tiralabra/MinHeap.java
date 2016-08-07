package tiralabra;

public class MinHeap {
    
    NeighbourNode[] heap;
    int heapsize;
    
    public MinHeap(int size) {
        this.heap = new NeighbourNode[size];
        this.heapsize = size;
    }
    
    public int left(int heapindex) {
        return 2 * heapindex;
    }
    
    public int right(int heapindex) {
        return 2 * heapindex + 1;
    }
    
    public int parent(int heapindex) {
        return heapindex / 2;
    }

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
    
    public void heapify(int heapindex) {
        int left = left(heapindex);
        int right = right(heapindex);
        if(right <= this.heapsize) {
            int largest = (heap[left].getDistance() > heap[right].getDistance()) ? left : right;
            if(heap[heapindex].getDistance() < heap[largest].getDistance()) {
                exchange(heapindex, largest);
                heapify(largest);
            }
        } else if(left == heapsize && heap[heapindex].getDistance() < heap[left].getDistance()) {
            exchange(heapindex, left);
        }
    }
    
    public void exchange(int index1, int index2) {
        NeighbourNode node1 = heap[index1];
        heap[index1] = heap[index2];
        heap[index2] = node1;
        heap[index2].getNeighbour().setHeapindex(index2);
        heap[index1].getNeighbour().setHeapindex(index1);
    }

    public boolean isEmpty() {
        return this.heapsize == 0;
    }   

    public PlaceNode del_min() {
        PlaceNode closest = heap[0].getNeighbour();
        heap[0] = heap[heapsize - 1];
        heap[0].getNeighbour().setHeapindex(0);
        heapsize--;
        heapify(0);
        return closest;
    }    

    public void decrease_key(PlaceNode neighbour, double distance) {
        if(distance < heap[neighbour.getHeapindex()].getDistance()) {
            heap[neighbour.getHeapindex()].setDistance(distance);
            heapify(neighbour.getHeapindex());
        }
    }
    
}
