package configs;

import java.util.ArrayList;
import java.util.List;
import test.Message;


public class Node {
    private String name;
    private List<Node> edges;
    private Message msg;

    public Node(String name) {
        this.name = name;
        this.edges = new ArrayList<>();
        this.msg = null;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Node> getEdges() {
        return this.edges;
    }

    public void addEdge(Node node) {
        this.edges.add(node);
    }

    public Message getMsg() {
        return this.msg;
    }

    public void setMsg(Message msg) {
        this.msg = msg;
    }

    public boolean hasCycles() {
        return hasCyclesHelper(this, new ArrayList<>());
    }

private boolean hasCyclesHelper(Node node, List<Node> visited) {
    if (visited.contains(node)) {
        return true;
    }
    visited.add(node);
    for (Node edge : node.getEdges()) {
        if (hasCyclesHelper(edge, visited)) {
            return true;
        }
    }
    visited.remove(node);
    return false;
}
}