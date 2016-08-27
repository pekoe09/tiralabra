package tiralabra.domain;

public class PathSearchResultSet implements INamedObject {
    
    private PathSearchResult[] results;
    
    public PathSearchResultSet(PathSearchResult[] results) {
        this.results = results;
    }

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
    
    public int size() {
        if (results == null) {
            return 0;
        }
        return results.length;
    }
    
    public PathSearchResult[] getResultArray() {
        return results;
    }
}
