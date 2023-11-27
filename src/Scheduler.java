import common.GraphUtils.GraphEdge;
import common.GraphUtils.GraphNode;

import java.util.*;

public class Scheduler {
    private Map<GraphNode, List<GraphEdge>> map;
    private List<GraphNode[]> edges;
    private Map<String, Integer> typeLatency;
    private int currentCycle;
    private List<GraphNode> destNodes;
    private List<GraphNode> ready;
    private List<GraphNode> active;
    private List<GraphNode> finalSchedule;
    private Map<GraphNode, Integer> priorityMap;

    public Scheduler(Map<GraphNode, List<GraphEdge>> map, List<GraphNode[]> edges) {
        this.map = map;
        this.edges = edges;
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
        for (GraphNode[] e : edges) {
            destNodes.add(e[0]);
        }
        GraphNode rootNode = null;
        // find node that does not rely on any dependecy as root node
        // TODO: is it possible to improve the selection of the root node??? check if there are multiple options
        for (GraphNode node : map.keySet()) {
            if (!destNodes.contains(node)) {
                rootNode = node;
                break;
            }
        }
        if (rootNode != null) {
            for (GraphNode targetNode : map.keySet()) {
                computeMaxPriority(rootNode, targetNode);
            }
        }
    }

    private void computeMaxPriority(GraphNode rootNode, GraphNode targetNode) {
        Queue<GraphNode> queue = new LinkedList<>();
        queue.add(rootNode);
        rootNode.setMaxLatencyPathValue(rootNode.getLatency());
        while (!queue.isEmpty()) {
            GraphNode currNode = queue.poll();
            int currentValue = currNode.getMaxLatencyPathValue();
            for (GraphEdge neighbor : map.get(currNode)) {
                GraphNode neighborNode = neighbor.getDestinationNode();
                int potentialLatency = currentValue + neighborNode.getLatency();
                if (neighborNode.getMaxLatencyPathValue() == null ||
                        potentialLatency > neighborNode.getMaxLatencyPathValue()) {
                    neighborNode.setMaxLatencyPathValue(potentialLatency);
                }
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
        for (GraphEdge x : map.get(node)) {
            GraphNode nodeX = x.getDestinationNode();
            if (x.getEdgeType().equals("serial") && active.contains(nodeX)) {
                return true;
            }
            if (nodeX.isOffActive()) {
                return true;
            }
        }
        return false;
    }

    public void createSchedule() {
        // Add all nodes with no dependencies to ready list
        for (Map.Entry<GraphNode, List<GraphEdge>> entry : map.entrySet()) {
            if (entry.getValue().isEmpty()) {
                ready.add(entry.getKey());
            }
        }
        // loop while there are nodes that are ready and nodes are being processed/are active
        while (!active.isEmpty() || !ready.isEmpty()) {
            // insert a nop if there are no nodes ready to be scheduled
            if (ready.isEmpty()) {
                GraphNode nop = new GraphNode(finalSchedule.size(), "nop");
                nop.setOp("nop");
                finalSchedule.add(nop);
            } else {
                // node with highest priority is selected
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
            currentCycle++;

            // Process active nodes
            List<GraphNode> activeCopy = new ArrayList<>(active);
            for (GraphNode o : activeCopy) {
                if (currentCycle == o.getIssueCycle() + o.getLatency()) {
                    active.remove(o);
                    o.setOffActive(true);
                    // check for nodes that are dependent on the completed node. If so, add to ready list
                    for (GraphEdge d : map.get(o)) {
                        if (isReady(d.getDestinationNode(), active)) {
                            ready.add(d.getDestinationNode());
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
