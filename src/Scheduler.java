import com.sun.corba.se.impl.orbutil.graph.Graph;
import common.GraphUtils.GraphEdge;
import common.GraphUtils.GraphNode;

import java.util.*;

public class Scheduler {
    private Map<GraphNode, List<GraphEdge>> edgeMap;
    private List<GraphNode[]> edges;
    private Map<String, Integer> typeLatency;
    private int currentCycle;
    private List<GraphNode> destNodes;
    private List<GraphNode> ready;
    private List<GraphNode> active;
    private List<List<GraphNode>> finalSchedule;
    private Map<GraphNode, Integer> priorityMap;

    public Scheduler(Map<GraphNode, List<GraphEdge>> edgeMap) {
        this.edgeMap = edgeMap;
        this.typeLatency = new HashMap<>();
        // Initialize typeLatency map
        this.typeLatency.put("load", 5);
        this.typeLatency.put("store", 5);
        this.typeLatency.put("output", 1);
        this.typeLatency.put("loadI", 1);
        this.typeLatency.put("add", 1);
        this.typeLatency.put("sub", 1);
        this.typeLatency.put("mult", 3);
        this.typeLatency.put("lshift", 1);
        this.typeLatency.put("rshift", 1);
        this.typeLatency.put("nop", 1);

        this.currentCycle = 1;
        this.destNodes = new ArrayList<>();
        this.ready = new ArrayList<>();
        this.active = new ArrayList<>();
        this.finalSchedule = new ArrayList<>();
        this.priorityMap = new HashMap<>();
    }

    public void computePriorities() {
//        for (GraphNode[] e : edges) {
//            destNodes.add(e[0]);
//        }
//        GraphNode rootNode = null;
        // find node that does not rely on any dependency as root node
        List<GraphNode> rootList = new ArrayList<>();
        for (GraphNode node : edgeMap.keySet()) {
            if (node.getNumParents() == 0) {
                rootList.add(node);
            }
        }
        if (!rootList.isEmpty()) {
            for (GraphNode rootNode : rootList) {
                for (GraphNode targetNode : edgeMap.keySet()) {
                    computeMaxPriority(rootNode, targetNode);
                }
            }
        } else {
            System.out.println("Invalid dependency graph: no roots");
        }
    }

    private void computeMaxPriority(GraphNode rootNode, GraphNode targetNode) {
        Queue<GraphNode> queue = new LinkedList<>();
        queue.add(rootNode);
        rootNode.setMaxLatencyPathValue(rootNode.getLatency());
        while (!queue.isEmpty()) {
            GraphNode currNode = queue.poll();
            int currentValue = currNode.getMaxLatencyPathValue();
            for (GraphEdge neighbor : edgeMap.get(currNode)) {
                GraphNode neighborNode = neighbor.getDestinationNode();
                int potentialLatency = currentValue + neighborNode.getLatency();
                neighborNode.setMaxLatencyPathValue(potentialLatency);
                if (!neighborNode.equals(targetNode)) {
                    queue.add(neighborNode);
                }
            }
        }
        priorityMap.put(targetNode, targetNode.getMaxLatencyPathValue());
        targetNode.setPriority(targetNode.getMaxLatencyPathValue());
    }

    private boolean isReady(GraphNode node, List<GraphNode> active) {
        // Add all edges originating from node
        for (GraphEdge x : edgeMap.get(node)) {
            GraphNode nodeX = x.getDestinationNode();
            // first part handles serial edges. offActive handles conflict and data edges
            if (!(x.getEdgeType().equals("serial") && active.contains(nodeX)) && !nodeX.isOffActive()) {
                return false;
            }
        }
        return true;
    }

    public void createSchedule() {
        // Add all nodes with no dependencies to ready list
        for (Map.Entry<GraphNode, List<GraphEdge>> entry : this.edgeMap.entrySet()) {
            if (entry.getValue().isEmpty()) {
                ready.add(entry.getKey());
            }
        }
        // loop while there are nodes that are ready and nodes are being processed/are active
        while (!active.isEmpty() || !ready.isEmpty()) {
            // insert a nop if there are no nodes ready to be scheduled
            List<GraphNode> currOps = new ArrayList<>();
            if (ready.isEmpty()) {
                GraphNode nop = new GraphNode(finalSchedule.size(), "nop");
                nop.setOp("nop");
                currOps.add(nop);
                currOps.add(nop);
            } else {
                // node with highest priority is selected
                int counter = 0;
                int highestPriority = 0;
                GraphNode selectedNode = null;
                for (GraphNode n : ready) {
                    if (n.getPriority() > highestPriority) {
                        highestPriority = n.getPriority();
                        selectedNode = n;
                    }
                }
                ready.remove(selectedNode);
                active.add(selectedNode);
                assert selectedNode != null;
                selectedNode.setIssueCycle(currentCycle);
                finalSchedule.add(selectedNode);
            }
            finalSchedule.add(currOps);
            currentCycle++;

            // Process active nodes
            List<GraphNode> activeCopy = new ArrayList<>(active);
            for (GraphNode o : activeCopy) {
                if (currentCycle == o.getIssueCycle() + o.getLatency()) {
                    active.remove(o);
                    o.setOffActive(true);
                    // check for nodes that are dependent on the completed node. If so, add to ready list
                    for (GraphNode d : o.getParents()) {
                        if (isReady(d, active)) {
                            ready.add(d);
                        }
                    }
                }
            }
        }

        // Print the final schedule
        for (GraphNode i : finalSchedule) {
            System.out.println(i.getOp());
        }
    }

}
