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
    private Set<GraphNode> ready;
    private Set<GraphNode> active;
    private List<GraphNode[]> finalSchedule;
    private boolean f0Available;
    private boolean f1Available;
    private boolean didOutput;
    private boolean debug = false;

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
        this.ready = new HashSet<>();
        this.active = new HashSet<>();
        this.finalSchedule = new ArrayList<>();
    }

    public void computePriorities() {
//        for (GraphNode[] e : edges) {
//            destNodes.add(e[0]);
//        }
//        GraphNode rootNode = null;
        // find node that does not rely on any dependency as root node
        List<GraphNode> rootList = new ArrayList<>();
//        List<GraphNode> nonRootList = new ArrayList<>();
        for (GraphNode node : edgeMap.keySet()) {
            if (node.getNumParents() == 0) {
                rootList.add(node);
            }
//            } else {
//                nonRootList.add(node);
//            }
        }
        if (!rootList.isEmpty()) {
            for (GraphNode rootNode : rootList) {
                computeMaxPriority(rootNode);
            }
        } else {
            System.out.println("Invalid dependency graph: no roots");
        }
    }

    private void computeMaxPriority(GraphNode rootNode) {
        Queue<GraphNode> queue = new LinkedList<>();
        queue.add(rootNode);
        rootNode.setMaxLatencyPathValue(rootNode.getLatency());
        // TODO: changing the priority to be more than just path latency
        rootNode.setPriority(rootNode.getMaxLatencyPathValue());
        while (!queue.isEmpty()) {
            GraphNode currNode = queue.poll();
            // TODO: changing the priority to be more than just path latency
            int currentValue = currNode.getMaxLatencyPathValue();
            currNode.setPriority(currNode.getMaxLatencyPathValue());
            for (GraphEdge neighbor : edgeMap.get(currNode)) {
                GraphNode neighborNode = neighbor.getDestinationNode();
                int potentialLatency = currentValue + neighborNode.getLatency();
                neighborNode.setMaxLatencyPathValue(potentialLatency);
                queue.add(neighborNode);
            }
        }
//        if (targetNode.getMaxLatencyPathValue() == null) {
//            System.out.println();
//        }
//        targetNode.setPriority(targetNode.getMaxLatencyPathValue());
    }

    private boolean isReady(GraphNode node, Set<GraphNode> active) {
        // Add all edges originating from node
        for (GraphEdge x : edgeMap.get(node)) {
            GraphNode nodeX = x.getDestinationNode();
            // first part handles serial edges. offActive handles conflict and data edges
//            if (!nodeX.isOffActive()) {
//                return false;
//            }

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
            GraphNode[] currOps = new GraphNode[2];
            GraphNode nop = new GraphNode(finalSchedule.size(), "nop");
            nop.setOp("nop");
            if (currentCycle == 15) {
                System.out.println();
            }
            if (ready.isEmpty()) {
                currOps[0] = nop;
                currOps[1] = nop;
            } else {
                // node with highest priority is selected
                this.f0Available = true;
                this.f1Available = true;
                this.didOutput = false;
                GraphNode f0 = null;
                GraphNode f1 = null;
                Set<GraphNode> skippedNodes = new HashSet<>();
                while (!ready.isEmpty() && (this.f0Available || this.f1Available)) {
                    int highestPriority = -1;
                    GraphNode selectedNode = null;
                    for (GraphNode n : ready) {
                        if (n.getPriority() > highestPriority) {
                            highestPriority = n.getPriority();
                            selectedNode = n;
                        }
                    }
                    assert selectedNode != null;
                    Object[] canExecuteResult = this.canExecute(selectedNode);
                    if ((boolean) canExecuteResult[0]) {
                        if ((int) canExecuteResult[1] == 0) {
                            f0 = selectedNode;
                            ready.remove(f0);
                            active.add(f0);
                            f0.setIssueCycle(currentCycle);
                            currOps[0] = f0;
                        } else {
                            f1 = selectedNode;
                            ready.remove(f1);
                            active.add(f1);
                            f1.setIssueCycle(currentCycle);
                            currOps[1] = f1;
                        }
                    } else {
                        // remove the selected node that cannot be activated to a list to add back after current ops are
                        // scheduled
                        skippedNodes.add(selectedNode);
                        ready.remove(selectedNode);
                    }
                }
                // add the skipped nodes back to the ready list
                ready.addAll(skippedNodes);
                // if we did an output then need to reset f1 boolean to add a nop
                if (this.didOutput) {
                    this.f1Available = true;
                }
                if (f0Available) {
                    currOps[0] = nop;
                }
                if (f1Available) {
                    currOps[1] = nop;
                }
            }
            finalSchedule.add(currOps);
            currentCycle++;

            // Process active nodes
            Set<GraphNode> activeCopy = new HashSet<>(active);
            for (GraphNode o : activeCopy) {
                if (currentCycle == o.getIssueCycle() + o.getLatency()) {
                    active.remove(o);
                    o.setOffActive(true);
                    // check for nodes that are dependent on the completed node. If so, add to ready list
                    for (GraphNode d : o.getParents()) {
                        if (!d.isOffActive() && isReady(d, active)) {
                            ready.add(d);
                        }
                    }
                }
            }
        }

        // Print the final scheduler
        int cycle = 0;
        for (GraphNode[] i : finalSchedule) {
            StringBuilder resBuilder = new StringBuilder();
            if (this.debug) {
                resBuilder.append(cycle);
            }
            resBuilder.append("[ ");
            resBuilder.append(i[0].getOp());
            resBuilder.append(" ; ");
            resBuilder.append(i[1].getOp());
            resBuilder.append(" ]");
            String res = resBuilder.toString();
            System.out.println(res);
            cycle += 1;
        }
    }

    private Object[] canExecute(GraphNode node) {
        // check if node can execute
        // return boolean to signify if it can AND which unit it occupies
        if (node == null) {
            System.out.println();
        }
        switch (node.getOpCode()) {
            case "load":
            case "store":
                if (this.f0Available) {
                    this.f0Available = false;
                    return new Object[]{true, 0};
                } else {
                    return new Object[]{false, 0};
                }
            case "mult":
                if (this.f1Available) {
                    this.f1Available = false;
                    return new Object[]{true, 1};
                } else {
                    return new Object[]{false, 1};
                }
            case "output":
                if (this.f1Available && this.f0Available) {
                    this.f1Available = false;
                    this.f0Available = false;
                    this.didOutput = true;
                    // always put output in unit 0
                    return new Object[]{true, 0};
                } else {
                    return new Object[]{false, 0};
                }
            default:
                // check for f1 or f2 and return
                if (this.f0Available) {
                    this.f0Available = false;
                    return new Object[]{true, 0};
                } else if (this.f1Available) {
                    this.f1Available = false;
                    return new Object[]{true, 1};
                } else {
                    return new Object[]{false, 1};
                }
        }
    }
}
