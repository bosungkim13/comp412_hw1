package common.GraphUtils;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GraphNode {
    private int opNumber;
    private String opCode;
    private String op;
    private int latency;
    private Integer maxLatencyPathValue; // Can be null
    private int priority;
    private int issueCycle;
    private boolean offActive;
    private boolean isReady;
    private Integer delay; // Can be null
    private Set<GraphNode> parents;

    public GraphNode(Integer opNumber, String opCode) {
        this.opNumber = opNumber;
        this.opCode = opCode;
        this.op = "";
        this.latency = 0;
        this.maxLatencyPathValue = null;
        this.priority = 0;
        this.issueCycle = 0;
        this.offActive = false;
        this.isReady = false;
        this.delay = null;
        this.parents = new HashSet<>();
    }

    // Getters
    public Set<GraphNode> getParents() {
        return this.parents;
    }
    public int getNumParents() {
        return this.parents.size();
    }
    public int getOpNumber() {
        return opNumber;
    }

    public String getOpCode() {
        return opCode;
    }

    public String getOp() {
        return op;
    }

    public int getLatency() {
        return latency;
    }

    public Integer getMaxLatencyPathValue() {
        return maxLatencyPathValue;
    }

    public int getPriority() {
        return priority;
    }

    public int getIssueCycle() {
        return issueCycle;
    }

    public boolean isOffActive() {
        return offActive;
    }

    public boolean isReady() {
        return isReady;
    }

    public Integer getDelay() {
        return delay;
    }

    // Setters
    public void setOpNumber(int opNumber) {
        this.opNumber = opNumber;
    }

    public void setOpCode(String opCode) {
        this.opCode = opCode;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public void setLatency(int latency) {
        this.latency = latency;
    }

    public void setMaxLatencyPathValue(Integer potentialLatency) {
        if (this.maxLatencyPathValue == null || potentialLatency > this.maxLatencyPathValue) {
            this.maxLatencyPathValue = potentialLatency;
        }
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setIssueCycle(int issueCycle) {
        this.issueCycle = issueCycle;
    }

    public void setOffActive(boolean offActive) {
        this.offActive = offActive;
    }

    public void setReady(boolean isReady) {
        this.isReady = isReady;
    }

    public void setDelay(Integer delay) {
        this.delay = delay;
    }

    public void addParent(GraphNode parent) {
        this.parents.add(parent);
    }
}

