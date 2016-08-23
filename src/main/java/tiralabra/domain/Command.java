package tiralabra.domain;

public class Command implements INamedObject{
    
    private String filePath;
    private String startPlaceName;
    private String endPlaceName;
    
    public Command(String filePath, String startPlaceName, String endPlaceName) {
        this.filePath = filePath;
        this.startPlaceName = startPlaceName;
        this.endPlaceName = endPlaceName;
    }

    public String getFilePath() {
        return filePath;
    }
    
    public String getStartPlaceName() {
        return startPlaceName;
    }

    public String getEndPlaceName() {
        return endPlaceName;
    }
    
    @Override
    public String getName() {
        return String.format("%s %s %s", filePath, startPlaceName, endPlaceName);
    }    
}
