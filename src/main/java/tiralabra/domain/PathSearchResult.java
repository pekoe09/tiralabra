package tiralabra.domain;

import tiralabra.datastructures.NamedArrayList;
import tiralabra.datastructures.PathStack;
import tiralabra.enums.AlgorithmAlternative;

public class PathSearchResult implements INamedObject {
    
    private String filePath;
    private NamedArrayList graph;
    private int nodeCount;
    private long edgeCount;
    private PlaceNode startPlace;
    private PlaceNode endPlace;
    private PathStack shortestPath;
    private AlgorithmAlternative algorithm;
    private long runTimeNanoSecs;
    
    public PathSearchResult() {
        graph = new NamedArrayList();
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    
    public NamedArrayList getGraph() {
        return graph;
    }

    public void setGraph(NamedArrayList graph) {
        this.graph = graph;
    }

    public int getNodeCount() {
        return nodeCount;
    }

    public void setNodeCount(int nodeCount) {
        this.nodeCount = nodeCount;
    }

    public long getEdgeCount() {
        return edgeCount;
    }

    public void setEdgeCount(long edgeCount) {
        this.edgeCount = edgeCount;
    }

    public PlaceNode getStartPlace() {
        return startPlace;
    }

    public void setStartPlace(PlaceNode startPlace) {
        this.startPlace = startPlace;
    }

    public PlaceNode getEndPlace() {
        return endPlace;
    }

    public void setEndPlace(PlaceNode endPlace) {
        this.endPlace = endPlace;
    }

    public PathStack getShortestPath() {
        return shortestPath;
    }

    public void setShortestPath(PathStack shortestPath) {
        this.shortestPath = shortestPath;
    }

    public AlgorithmAlternative getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(AlgorithmAlternative algorithm) {
        this.algorithm = algorithm;
    }

    public long getRunTimeNanoSecs() {
        return runTimeNanoSecs;
    }

    public void setRunTimeNanoSecs(long runTimeNanoSecs) {
        this.runTimeNanoSecs = runTimeNanoSecs;
    }

    @Override
    public String getName() {
        return String.format("%s, paikasta %s paikkaan %s", filePath, startPlace.getName(), endPlace.getName());
    }
    
}
