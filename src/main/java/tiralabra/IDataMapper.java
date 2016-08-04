package tiralabra;

import java.util.List;

public interface IDataMapper {
    
    public void mapData(String data, Integer counter, ReadTarget target);
    public List<PlaceNode> getData();
    
}
