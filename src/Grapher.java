import com.sun.corba.se.impl.orbutil.graph.Graph;
import common.GraphUtils.GraphEdge;
import common.GraphUtils.GraphNode;
import common.IntermediateRepresentation.IntermediateList;
import common.IntermediateRepresentation.IntermediateNode;
import common.IntermediateRepresentation.IntermediateStoreNode;

import java.util.*;
import java.util.stream.Collectors;

public class Grapher {
    private IntermediateList IR;
    private Map<GraphNode, List<GraphEdge>> nodeEdgeMap;
    private Map<Integer, GraphNode> reg2Node; // Maps register number to node
    private Map<IntermediateNode, GraphNode> IR2graph; // Maps IR node to graph node
    private GraphNode undefNode;
    private int nodeNum;
    private List<GraphNode[]> edges;
    private List<Object[]> prevMemOps;
    private Map<String, Integer> typeLatency;
    private IntermediateNode currNode;

    public Map<GraphNode, List<GraphEdge>> getNodeEdgeMap() {
        return this.nodeEdgeMap;
    }
    public List<GraphNode[]> getEdges() {
        return this.edges;
    }
    public Grapher(IntermediateList IR) {
        this.IR = IR;
        this.nodeEdgeMap = new HashMap<>();
        this.reg2Node = new HashMap<>();
        this.undefNode = new GraphNode(-1, "-1");
        this.nodeNum = 0;
        this.edges = new ArrayList<>();
        this.prevMemOps = new ArrayList<>();
        this.typeLatency = new HashMap<>();
        this.currNode = this.IR.getHead().getNext();
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
    }

    public void buildGraph() {
        IntermediateNode tailNode = IR.getTail();
        while (currNode != tailNode) {
            String currentOpcode = currNode.getLexeme();
            int currRegVal;
            List<Integer> defs = new ArrayList<>();
            List<Integer> uses = new ArrayList<>();
            // 1 = lexeme, 2 = source register 0, 3 = virtual register 0, 7 = virtual register 1, 11 = virtual register 1 or 2,
            int numOps = 3;
            int currOpcode = currNode.getOpCode();

            // handle definitions
            for (int i = 0; i < numOps; i ++) {
                currRegVal = currNode.getSourceRegister(i);
                if (currRegVal < 0) {
                    continue;
                }
                if (currOpcode == Parser.MEMOP && i == 1 && !(currNode instanceof IntermediateStoreNode)) {
                    defs.add(currNode.getVirtualRegister(i));
                } else if (currOpcode == Parser.LOADI && i == 1) {
                    defs.add(currNode.getVirtualRegister(i));
                } else if (currOpcode == Parser.ARITHOP && i == 2) {
                    defs.add(currNode.getVirtualRegister(i));
                }
            }
            // handle uses
            for (int i = 0; i < numOps; i ++) {
                currRegVal = currNode.getSourceRegister(i);
                if (currRegVal < 0) {
                    continue;
                }
                if (currOpcode == Parser.MEMOP) {
                    if (currNode instanceof IntermediateStoreNode) {
                        uses.add(currNode.getVirtualRegister(i));
                    } else if (i == 0) {
                        uses.add(currNode.getVirtualRegister(i));
                    }
                } else if (currOpcode == Parser.ARITHOP && i < 2) {
                    uses.add(currNode.getVirtualRegister(i));
                }

            }

            GraphNode graphNode = new GraphNode(nodeNum, currentOpcode);
            IR2graph.put(this.currNode, graphNode);
            graphNode.setLatency(typeLatency.get(currentOpcode));
            graphNode.setOp(currNode.getPRrep());

            // TODO: creating graph
            // initialize adjacency list for new graph node
            nodeEdgeMap.put(graphNode, new ArrayList<>());

            // map definitions to graph nodes
            // node map allows us to find the node that defines a certain value
            for (int d : defs) {
                reg2Node.put(d, graphNode);
            }

            for (int u : uses) {
                // check if this value is produced by any previous node in the graph
                // if it doesn't then we use a special undefined node to represent an undefined or external value
                if (!reg2Node.containsKey(u)) {
                    reg2Node.put(u, undefNode);
                }
                // create new data edge and add it to the edges for the current node
                GraphEdge graphEdge = new GraphEdge(reg2Node.get(u), "data", typeLatency.get(currentOpcode));
                // add to adjacency list
                nodeEdgeMap.get(graphNode).add(graphEdge);
                // add to a list of all edges
                edges.add(new GraphNode[]{graphNode, reg2Node.get(u)});
            }

            // Logic for memops
            // TODO: Currently making edges to all previous memops.
            if (Arrays.asList("load", "store", "output").contains(currentOpcode)) {
                // loop through previous memory operations
                for (Object[] prevMemOp : prevMemOps) {
                    String destOp = (String) prevMemOp[1];
                    GraphNode destNode = (GraphNode) prevMemOp[0];
                    if (Arrays.asList("load", "store", "output").contains(destOp) && currentOpcode.equals("store")) {
                        // add serial edge to ensure that store does not proceed until previous operation is complete
                        GraphEdge graphEdge = new GraphEdge(destNode, "serial", typeLatency.get(currentOpcode));
                        nodeEdgeMap.get(graphNode).add(graphEdge);
                    } else if (destOp.equals("store") && Arrays.asList("load", "output").contains(currentOpcode)) {
                        // load and output need an edge to the most recent store
                        GraphEdge graphEdge = new GraphEdge(destNode, "conflict", typeLatency.get(currentOpcode));
                        nodeEdgeMap.get(graphNode).add(graphEdge);
                    } else if (destOp.equals("output") && currentOpcode.equals("output")) {
                        // serial edge for output to output
                        GraphEdge graphEdge = new GraphEdge(destNode, "serial", typeLatency.get(currentOpcode));
                        nodeEdgeMap.get(graphNode).add(graphEdge);
                    }
                }
                prevMemOps.add(new Object[]{graphNode, currentOpcode});
            }
            currNode = currNode.getNext();
            nodeNum++;
        }
    }

    private void doSerialConflict(GraphNode currGraphNode) {
        String lex = this.currNode.getLexeme();

        int opCode = this.currNode.getOpCode();

        List<GraphEdge> edges = this.nodeEdgeMap.get(currGraphNode);

        boolean currIsStore = lex.equals("store");

        if (opCode == Parser.MEMOP) {
            GraphNode prevStore = this.getLastNode("store", currIsStore);
            if (prevStore == null) {
                return;
            }
            List<GraphNode> children = edges.stream().map(GraphEdge::getDestinationNode).collect(Collectors.toList());
            if (!children.contains(prevStore)) {
                GraphEdge edge = null;
                if (currIsStore) {
                    edge = new GraphEdge(prevStore, "serial", 1);
                } else {
                    edge = new GraphEdge(prevStore, "conflict", this.typeLatency.get(prevStore.getOpCode()));
                }
                edges.add(edge);
            }
        } else if (lex.equals("output")) {
            GraphNode prevStore = this.getLastNode("store", currIsStore);
            if (prevStore != null) {
                GraphEdge edge = new GraphEdge(prevStore, "conflict", this.typeLatency.get(prevStore.getOpCode()));
                edges.add(edge);
            }
            GraphNode prevOutput = this.getLastNode("output", currIsStore);
            if (prevOutput != null) {
                GraphEdge edge = new GraphEdge(prevOutput, "serial", 1);
                edges.add(edge);
            }
        } else {
            System.err.println("Invalid lex for doSerialConflict()");
        }
    }

    private GraphNode getLastNode(String lex, boolean currIsStore) {

        IntermediateNode iterNode = this.currNode.getPrev();
        GraphNode currGraphNode = this.IR2graph.get(this.currNode);
        // get edges of current graph node
        List<GraphEdge> edges = this.nodeEdgeMap.get(currGraphNode);

        while (iterNode != this.IR.getHead()) {
            String currLex = iterNode.getLexeme();
            if (currLex.equals(lex)) {
                return this.IR2graph.get(iterNode);
            }
            if (currIsStore) {
                if (currLex.equals("output")) {
                    GraphEdge edge = new GraphEdge(IR2graph.get(iterNode), "serial", 1);
                    edges.add(edge);
                } else if (currLex.equals("load")) {
                    List<GraphNode> children = edges.stream().map(GraphEdge::getDestinationNode).collect(Collectors.toList());
                    if (!children.contains(this.IR2graph.get(iterNode))) {
                        GraphEdge edge = new GraphEdge(this.IR2graph.get(iterNode),"serial", 1);
                        edges.add(edge);
                    }
                }
            }
            currNode = currNode.getPrev();
        }
        return null;

    }
}
