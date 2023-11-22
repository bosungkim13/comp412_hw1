package common.GraphUtils;

public class GraphEdge {
    private GraphNode destinationNode;
    private String edgeType; // "data" or "conflict"
    private int sourceLatency;

    public GraphEdge(GraphNode destinationNode, String edgeType, int sourceLatency) {
        this.destinationNode = destinationNode;
        this.edgeType = edgeType;
        this.sourceLatency = sourceLatency;
    }

    // Getters
    public GraphNode getDestinationNode() {
        return destinationNode;
    }

    public String getEdgeType() {
        return edgeType;
    }

    public int getSourceLatency() {
        return sourceLatency;
    }

    // Setters
    public void setDestinationNode(GraphNode destinationNode) {
        this.destinationNode = destinationNode;
    }

    public void setEdgeType(String edgeType) {
        this.edgeType = edgeType;
    }

    public void setSourceLatency(int sourceLatency) {
        this.sourceLatency = sourceLatency;
    }
}

